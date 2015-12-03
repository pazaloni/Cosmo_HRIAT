
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
   
    public static String createParticipant(String cosmoID, String firstName, String lastName, String birthDate, 
    		String familyPhysician, String healthNumber, String phone)
    {
    	String result = "";
		if (cosmoID.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
				|| birthDate.isEmpty() || familyPhysician.isEmpty()
				|| healthNumber.isEmpty() || phone.isEmpty()) 
		{
			result = " one of your fields is empty";
		}
		else
		{
			
			DatabaseHelper db = new DatabaseHelper();
			db.connect();
	
			String values[][] = new String[6][2];
			values[0][0] = "cosmoID";
			values[1][0] = "firstName";
			values[2][0] = "lastName";
			values[3][0] = "dateOfBirth";
			values[4][0] = "personalHealthNumber";
			values[5][0] = "phoneNum";
	
			if(!idExists(cosmoID))
			{
				values[0][1] = cosmoID;
				values[1][1] = firstName;
				values[2][1] = lastName;
				values[3][1] = birthDate;
				values[4][1] = healthNumber;
				values[5][1] = phone;
		
				boolean successful = db.insert(values, "TemporaryParticipant");
				if(!successful)
				{
					result = "The insertion was not successful";
				}
				else
				{
					result = "The insertion was successful";
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
		ResultSet set = db.select("UserName", "Staff", "", "");
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
    


