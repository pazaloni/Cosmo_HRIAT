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

    public MedicalAdministrator(int staffID, String password)
    {
        super(staffID, password);
        // connect to database, get the staff account record

        // fetch the first and last name, and assign them to
        // the firstName and lastname variables
    }
   
    public static boolean createParticipant(String cosmoID, String firstName, String lastName, String birthDate, 
    		String familyPhysician, String healthNumber, String physicalPhone)
    {
    	DatabaseHelper db = new DatabaseHelper();
    	db.connect();
    	
    	String values[][] = new String[7][2];
    	values[0][0] = "cosmoID";
    	values[1][0] = "firstName";
    	values[2][0] = "lastName";
    	values[3][0] = "dateOfBirth";
    	values[4][0] = "gender";
    	values[5][0] = "personalHealthNumber";
    	values[6][0] = "phoneNum";

    	
    	values[0][1] = cosmoID;
    	values[1][1] = firstName;
    	values[2][1] = lastName;
    	values[3][1] = birthDate;
    	values[4][1] = familyPhysician;
    	values[5][1] = healthNumber;
    	values[6][1] = physicalPhone;


    	boolean successful = db.insert(values, "Participant");
    	
    	db.close();
    	
    	return successful;
    }
    

}
