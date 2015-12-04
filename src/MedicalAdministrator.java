
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;

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

    public MedicalAdministrator(String username, String lastName, String firstName, String email,
    		String password, String accessLevel)
    {
        super(username, lastName, firstName, email, password, accessLevel);
        // connect to database, get the staff account record

        // fetch the first and last name, and assign them to
        // the firstName and lastname variables
    }
   
    public static String createParticipant(String cosmoID, String firstName, String lastName, LocalDate birthDate, 
    		String familyPhysician, String healthNumber, String phone)
    {
        //Date test = new Date(Integer.parseInt(birthDate.toString()));
        String birthDateString = birthDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        //String birthDateString = new SimpleDateFormat("yyy-mmm-dd").format(test);
        
        
    	String result = "";
		if (cosmoID.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
				|| birthDateString.isEmpty() || familyPhysician.isEmpty()
				|| healthNumber.isEmpty() || phone.isEmpty()) 
		{
			result = " one of your fields is empty";
		}
		else
		{
			
			DatabaseHelper db = new DatabaseHelper();
			db.connect();
	
			String values[][] = new String[7][2];
			values[0][0] = "cosmoID";
			values[1][0] = "firstName";
			values[2][0] = "lastName";
			values[3][0] = "dateOfBirth";
			values[4][0] = "personalHealthNumber";
			values[5][0] = "phoneNum";
			values[6][0] = "dateUpdated";
	
			if(!idExists(cosmoID))
			{
		        
		        Calendar c = Calendar.getInstance();
		    
		        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		        
		        String formattedDate = df.format(c.getTime());
				values[0][1] = cosmoID;
				values[1][1] = firstName;
				values[2][1] = lastName;
				values[3][1] = birthDateString;
				values[4][1] = healthNumber;
				values[5][1] = phone;
				values[6][1] = formattedDate;
		
				boolean successful = db.insert(values, "TemporaryParticipant");
				if(!successful)
				{
					result = "The insertion was not successful";
				}
			}
			else
			{
				result = "That ID already exists";
			}

			db.close();
		}
		return result;
	}

	private static boolean idExists(String cosmoID) {
        boolean result = false;

        // result set that we obtain form the database
        DatabaseHelper db = new DatabaseHelper();
		ResultSet set = db.select("cosmoID", "TemporaryParticipant", "", "");
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
    


