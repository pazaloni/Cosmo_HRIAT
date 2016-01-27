import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Purpose: Represent the medical staff within the system
 * 
 * 
 * @author TEAM CIMP
 *
 */
public class MedicalAdministrator extends BasicStaff
{

    // private ArrayList<Note> allNotes;

    public MedicalAdministrator(String username, String lastName,
            String firstName, String email, String password, String accessLevel)
    {
        super(username, lastName, firstName, email, password, accessLevel);
    }

    /**
     * Purpose: To take in the information input in the
     * {@link MedicalStaffMainPageGUI} Add participant form and put it into the
     * database after doing proper validation.
     * 
     * @param cosmoID the cosmo id of the participant
     * @param firstName the first name of the participant
     * @param lastName the last name of the participant
     * @param birthDate the birthdate of the participant
     * @param physicianFName the first name of the physiciant of the participant
     * @param physicianLName the last name of the physiciant of the participant
     * @param healthNumber the healthnumber of the participant
     * @param phone the phone number of the participant
     * @param address the address of the participant
     * @param imagePath the path to the image representing the participant
     * 
     * @return A string indicating the result of the creation, could include
     *         errors or be empty for a successful insert.
     */
    public static String createParticipant( String cosmoID, String firstName,
            String lastName, LocalDate birthDate, String physicianFName,
            String physicianLName, String healthNumber, String phone,
            String address, String imagePath )
    {
        // initialize birth date string to an empty string
        String birthDateString = "";

        // check if birthdate is set before trying to format it so we don't
        // get a null pointer exception
        if ( birthDate != null )
        {
            birthDateString = birthDate.format(DateTimeFormatter
                    .ofPattern("dd-MMM-yyyy"));
        }

        // set the result error message to en empty string
        String result = "";

        // check to see if any of the fields are empty
        if ( cosmoID.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                || birthDateString.equals("") || physicianFName.isEmpty()
                || physicianLName.isEmpty() || healthNumber.isEmpty()
                || phone.isEmpty() || address.isEmpty() )
        {
            result = "One of your fields is empty";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            if ( imagePath.isEmpty() )
            {
                imagePath = "images/defaultImage.jpg";
            }
            // check to see if the CosmoID is a number
            if ( !cosmoID.matches("\\d+") )
            {
                result = "CosmoID must be a number";
            }
            // check to see if the cosmoID already exists in the database
            else if ( idExists(cosmoID) )
            {
                result = "That CosmoID already exists";
            }
            // check to see if the Health Number is a 9 digit number
            else if ( !healthNumber.matches("^[0-9]{9}$") )
            {
                result = "Health Number must be 9 digits";
            }
            // check to see if the Phone number is 10 digits.
            else if ( !phone.matches("^[0-9]{10}$") )
            {
                result = "Phone Number must be 10 digits";
            }
            else if( !imagePath.endsWith(".jpg"))
            {
                result = "Image is invalid";
            }
            // everything is valid
            else
            {
                boolean insertedPhysician = true;
                boolean successful = false;

                // try to find a doctor already in the database
                ResultSet physicianExists = db.select("count(*)", "physician",
                        "firstName = '" + physicianFName + "' AND lastName = '"
                                + physicianLName + "'", "");

                int recordExists = 0;

                // check if there is already a physician with that name
                try
                {
                    physicianExists.next();
                    recordExists = physicianExists.getInt(1);
                }
                catch ( SQLException e )
                {
                    e.printStackTrace();
                }

                // if there is no physician with that name
                if ( recordExists == 0 )
                {
                    String physicianValues[][] = new String[2][2];

                    physicianValues[0][0] = "firstName";
                    physicianValues[1][0] = "lastName";

                    physicianValues[0][1] = physicianFName;
                    physicianValues[1][1] = physicianLName;

                    insertedPhysician = db.insert(physicianValues, "Physician");

                }

                // there is now a physician for the participant
                if ( insertedPhysician )
                {
                    // getting physician ID for participant insert
                    ResultSet idResult = db.select("physicianID", "physician",
                            "firstName = '" + physicianFName
                                    + "' AND lastName = '" + physicianLName
                                    + "'", "");
                    String physicianID = "";

                    try
                    {
                        idResult.next();
                        physicianID = idResult.getString(1);
                    }
                    catch ( SQLException e )
                    {
                        e.printStackTrace();
                    }

                    // array of field names
                    String values[][] = new String[16][2];
                    values[0][0] = "cosmoID";
                    values[1][0] = "firstName";
                    values[2][0] = "lastName";
                    values[3][0] = "dateOfBirth";
                    values[4][0] = "personalHealthNumber";
                    values[5][0] = "phoneNum";
                    values[6][0] = "address";
                    values[7][0] = "dateUpdated";
                    values[8][0] = "agencyID";
                    values[9][0] = "chwNurseID";
                    values[10][0] = "caregiverID";
                    values[11][0] = "kinID";
                    values[12][0] = "landlordID";
                    values[13][0] = "physicianID";
                    values[14][0] = "workID";
                    values[15][0] = "imagePath";

                    // get the current date to insert into "lastUpdated"
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());

                    
                    String dbPath = saveImage(imagePath, cosmoID);

                    // array of values to insert
                    values[0][1] = cosmoID;
                    values[1][1] = firstName;
                    values[2][1] = lastName;
                    values[3][1] = birthDateString;
                    values[4][1] = healthNumber;
                    values[5][1] = phone;
                    values[6][1] = address;
                    values[7][1] = formattedDate;
                    values[8][1] = "1";
                    values[9][1] = "1";
                    values[10][1] = "1";
                    values[11][1] = "1";
                    values[12][1] = "1";
                    values[13][1] = physicianID;
                    values[14][1] = "1";
                    values[15][1] = dbPath;

                    // inserting into the database
                    successful = db.insert(values, "Participant");
                }

                // if failed on insert of physician or participant
                if ( !successful )
                {
                    result = "The insertion was not successful";
                }
            }
            db.disconnect();
        }
        return result;
    }

    /**
     * 
     Purpose:save the image into the image folder using the correct image path
     * 
     * @param imagePath path of the image chosen by the user
     * @param cosmoID the cosmo id of the particiant corresponding to the image
     * @return the path stored in the database
     */
    private static String saveImage( String imagePath, String cosmoID )
    {
        String path = imagePath;

        

        byte[] imageData;
        // This returns the path to where the jar file is stored
        String pathToSaveTo = "images/" + cosmoID + ".jpg";

        // pathToSaveTo = pathToSaveTo.replace("bin/", "");
        // pathToSaveTo = pathToSaveTo.substring(1);

        System.out.println(pathToSaveTo);
        try (FileOutputStream fos = new FileOutputStream(pathToSaveTo))
        {
            File imageToWrite = new File(path);

            imageData = Files.readAllBytes(imageToWrite.toPath());
            fos.write(imageData);

        }
        catch ( IOException e )
        {
            System.out.println("File can't be found!");
        }

        return pathToSaveTo;

    }

    /**
     * 
     * Purpose: To check if the passed in ID exists in the database
     * 
     * @param cosmoID - the ID that was input into the field
     * 
     * @return boolean - true if id exists already, false if it doesn't exist
     */
    private static boolean idExists( String cosmoID )
    {
        boolean result = false;

        // result set of all cosmoID's in the database
        DatabaseHelper db = new DatabaseHelper();
        ResultSet set = db.select("cosmoID", "Participant", "", "");
        try
        {
            while ( set.next() && !result )
            {
                // if the username for the new user is already in the database
                // then the result is false
                if ( cosmoID.equals(set.getString(1)) )
                {
                    result = true;
                }
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        return result;
    }
}
