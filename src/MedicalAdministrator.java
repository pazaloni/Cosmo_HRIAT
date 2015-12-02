
import java.sql.Date;

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
   
    public static boolean createParticipant(String cosmoID, String firstName, String lastName, String birthDate, 
    		String familyPhysician, String healthNumber, String phone)
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
//    	values[6][0] = "physicianID";
//    	values[7][0] = "agencyID";
//    	values[8][0] = "kinID";
//    	values[9][0] = "caregiverID";
//    	values[10][0] = "landlordID";
//    	values[11][0] = "chwNurseID";
//    	values[12][0] = "workID";

    	
    	values[0][1] = cosmoID;
    	values[1][1] = firstName;
    	values[2][1] = lastName;
    	values[3][1] = birthDate;
    	values[4][1] = healthNumber;
    	values[5][1] = phone;


    	boolean successful = db.insert(values, "TemporaryParticipant");
    	
    	db.close();
    	
    	return successful;
    }
}
    


