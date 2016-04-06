package core;

import helpers.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ScanForms
{

    private StringProperty dateSaved;
    private StringProperty description;
    private StringProperty fileName;

    public ScanForms(LocalDate dateSaved, String description, String fileName)
    {
        this.dateSaved = new SimpleStringProperty(dateSaved.toString());
        this.description = new SimpleStringProperty(description);
        this.fileName = new SimpleStringProperty(fileName);

    }

    /**
     * 
     * Purpose: return the date form was saved
     * 
     * @return
     */
    public StringProperty getDateSaved()
    {       
        return dateSaved;
    }

    /**
     * 
     * Purpose: return the description
     * 
     * @return
     */
    public StringProperty getDescription()
    {
        return description;
    }

    /**
     * 
     * Purpose: return the fileName
     * 
     * @return
     */
    public StringProperty getFileName()
    {
        return fileName;
    }
    
    public StringProperty displayFileName(String cosmoID)
    {
        String nameString = fileName.get();
        nameString = nameString.substring(nameString.lastIndexOf("/")+cosmoID.length()+1, nameString.length());
        StringProperty displayName = new SimpleStringProperty(nameString);
        return displayName;
    }
    /**
     * Method for display properly formatted date value.
     * @return a properly formatted date string
     */
    public StringProperty displayDateSaved()
    {
        
        DateFormat dToSFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat sToDFormat = new SimpleDateFormat("yyyy-MM-dd");
        String unformatedString = dateSaved.get();
        Date unformatedDate;
        String formattedString; 
        try
        {
            unformatedDate = sToDFormat.parse(unformatedString);
            formattedString = dToSFormat.format(unformatedDate);
        }
        catch ( ParseException e )
        {
            formattedString = dateSaved.toString();
        }
        
        StringProperty formattedDateTime = new SimpleStringProperty(formattedString);
        return formattedDateTime;
    }
    /**
     * Purpose to return the date as a localDate
     * 
     * @return the date converted to a localdate
     */
    public LocalDate getLocalDateTime()
    {
        LocalDate localDateTime = null;
        String dateTime = this.dateSaved.getValue();
        int yearEnd = dateTime.indexOf('-');
        int year = Integer.parseInt(dateTime.substring(0, yearEnd));
        dateTime = dateTime.substring(yearEnd + 1);
        int monthEnd = dateTime.indexOf('-');
        int month = Integer.parseInt(dateTime.substring(0, monthEnd));
        dateTime = dateTime.substring(monthEnd + 1);
        int day = Integer.parseInt(dateTime.substring(0, 2));
        localDateTime = LocalDate.of(year, month, day);
        return localDateTime;
    }

    /**
     * 
     * Purpose: Add a scanned form for a participant in the database
     * 
     * @param form
     *            note to add
     * @param cosmoID
     *            the participant that will be receiving the form
     * @return The result of the insertion in the database
     */
    public static String createImage( ScanForms form, String cosmoID )
    {
        String result = "";

        if ( form.dateSaved.get().isEmpty() || form.description.get().isEmpty()
                || form.fileName.get().isEmpty() )
        {
            result = "You are missing required fields";
        }
        else if(form.description.get().length() > 250)
        {
            result = "The description has a maximum size of 250 characters.";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;

            String scanFormValues[] = new String[4];
            String fileName = saveImage(form.fileName.get(), cosmoID);
            scanFormValues[0] = fileName;
            scanFormValues[1] = cosmoID;
            scanFormValues[2] = form.getDateSaved().get();            
            scanFormValues[3] = form.getDescription().get();
            

            success = db.insert(scanFormValues, "ScanForms");

            if ( !success )
            {
                result = "An image with that name already exists.";
            }
            else
            {
                result = "";
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
     * @param cosmoID the cosmo id of the participant corresponding to the image
     * @return the path stored in the database
     */
    private static String saveImage( String imagePath, String cosmoID )
    {
        String path = imagePath;

        byte[] imageData;
        //trim image path
        String imageName = imagePath.substring(imagePath.lastIndexOf("\\")+1,imagePath.length()-4);
        // This returns the path to where the jar file is stored
        String pathToSaveTo = "./images/scannedImages/" + cosmoID + imageName + ".jpg";
       
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
        
        //Remove the dot to store them in properly in the database
        pathToSaveTo = pathToSaveTo.replace("./images/", "/images/");
        System.out.println("Path to save to "+ pathToSaveTo);
        return pathToSaveTo;

    }
    
    /**
     * 
     * Purpose: Remove a scanned form for a participant from the database
     * 
     * @param note
     *            the note to remove
     * @param cosmoID
     *            the participant that will have the note removed
     * @return a string containing the status of the deletion
     */
    public static String deleteImage( ScanForms form, String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        boolean success = false;
        
        success = db.delete("ScanForms", "fileName='"
                + form.getFileName().get() + "'" + "AND cosmoID='" + cosmoID
                + "'");
                
        if ( success )
        {
            result = "Deleted successfully";
            
            URL u = null;
            try
            {

                    u = (form.getClass().getProtectionDomain().getCodeSource()
                            .getLocation().toURI().toURL());

            }
            catch ( URISyntaxException e )
            {
                e.printStackTrace();
            }
            catch ( MalformedURLException e )
            {
                e.printStackTrace();
            }

            String url = u.toString();

            url = url.substring(0,
                    url.length() - (url.length() - url.lastIndexOf("/")));

            url = url.replace("/bin", "");
                
                String pathToDelete = form.getFileName().get().substring(1, form.getFileName().get().length());
                File imageToDelete = new File(pathToDelete);

                if(imageToDelete.delete())
                {
                    
                }
                else
                {
                    result = "The image file needs to be manually deleted.";
                }
            //Generate error message if file deletion did not occur.
        }
        else
        {
            result = "Deletion did not occur";
        }
        db.disconnect();
        return result;
    }

}