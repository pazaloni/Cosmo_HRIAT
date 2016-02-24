import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Purpose: This is the controller for accessing the database in order to populate
 *          the participant information update form with the relevant infomation 
 *          for the selected participant
 * 
 * @author Breanna Wilson cst215 Steven Palchinski cst209
 */
public class ParticipantUpdateFormController 
{
	//This will hold the passed in cosmo ID
    private int cosmoID;
    //the instance of the database
	private DatabaseHelper db;
	
	/**
	 * Purpose: The constructor for the ParticipantUpdateFormController
	 * 
	 * @param cosmoID The cosmoID passed in for the participant in question
	 * 
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public ParticipantUpdateFormController(int cosmoID)
	{
	    this.cosmoID = cosmoID;
		db = new DatabaseHelper();
	}
	
	/**
	 * Purpose:This will get the information needed for the form for the selected
	 *         participant, and return it in the form of string array
	 * 
	 * @return A String array containing all the relevant data to the participant
	 * 
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public String[] fetchParticipantBasicInfo()
	{
		//connect to the database
	    db.connect();
	    //create the array to hold the information
		String[] participantInfo = new String[8];
		
		//query the database
		ResultSet rs = db.select("firstName, lastName, address, city,"
				+ " postalCode, phoneNum, dateOfBirth, socialInsuranceNumber"
				, "Participant", "cosmoID = " + cosmoID, "");
		
		try
        {
		    //while there is still a another entry in the ResultSet, 
		    //put the string result into the string Array
            while(rs.next())
            {
                participantInfo[0] = rs.getString(1);
                participantInfo[1] = rs.getString(2);
                participantInfo[2] = rs.getString(3);
                participantInfo[3] = rs.getString(4);
                participantInfo[4] = rs.getString(5);
                participantInfo[5] = rs.getString(6);
                participantInfo[6] = rs.getString(7);
                participantInfo[7] = rs.getString(8);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		//disconnect from the database		
		db.disconnect();
		//return the array of information 
		return participantInfo;
	}
	
	/**
	 * Purpose:This will get the kin information needed for the form for the 
     *         selected participant, and return it in the form of string array
     *         
	 * @return A String array containing all the relevant data for the 
	 *         participants kin
	 *         
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public String[] fetchKinInfo()
	{
		//connect to the database
	    db.connect();
	    //create the string array to hold the information
	    String[] kinInfo = new String[7];
	    //query the participant table to get the kin ID of the selected participant
	    ResultSet rsKinID = db.select("kinID", "Participant", "cosmoID =" + cosmoID, "");
	    //set the kinID to a black string
	    String kinID = "";
	    try
        {
	        //while there is another entry in the ResultSet save the kinID
            while(rsKinID.next())
            {
                kinID = rsKinID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    //query the Kin Table using the kinID collected in the previous step
	    ResultSet rs = db.select("firstName, lastName, address, city, postalCode,"
	            + "homePhoneNumber, workPhoneNumber", "Kin", "kinID =" + kinID, "");
		try
        {
		    //While there is another entry in the ResultSet,
		    //put the string result in the array
            while(rs.next())
            {
                kinInfo[0] = rs.getString(1);
                kinInfo[1] = rs.getString(2);
                kinInfo[2] = rs.getString(3);
                kinInfo[3] = rs.getString(4);
                kinInfo[4] = rs.getString(5);
                kinInfo[5] = rs.getString(6);
                kinInfo[6] = rs.getString(7);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    //disconnect from the database
	    db.disconnect();
	    //return the string array of kin info
		return kinInfo;
	}
	
	/**
	 * Purpose:This will get the caregiver information needed for the form for 
	 *         the selected participant, and return it in the form of string array
     *         
     * @return A String array containing all the relevant data for the 
     *         participants caregiver
     *         
     * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public String[] fetchCaregiverInfo()
	{
		//create the string array to hold the caregiver data
	    String[] caregiverInfo = new String[7];
	    //connect to the database
		db.connect();
		//query the database to get the caregiver id for the selected participant
		ResultSet rsCGID = db.select("caregiverID", "Participant", "cosmoID =" + cosmoID, "");
        //set the caregiver id string to blank
		String cgID = "";
        try
        {
            //while there is another result in the ResultSet, 
            //set the caregiver ID to what was returned from the database
            while(rsCGID.next())
            {
                cgID = rsCGID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //query the Caregiver table to get the relevant information for that participant
        ResultSet rs = db.select("firstName, lastName, address, city, postalCode,"
                + "homePhoneNumber, workPhoneNumber", "Caregiver", 
                "caregiverID =" + cgID, "");
        try
        {
            //while there is another result in the ResultSet,
            //add that string to the array
            while(rs.next())
            {
                caregiverInfo[0] = rs.getString(1);
                caregiverInfo[1] = rs.getString(2);
                caregiverInfo[2] = rs.getString(3);
                caregiverInfo[3] = rs.getString(4);
                caregiverInfo[4] = rs.getString(5);
                caregiverInfo[5] = rs.getString(6);
                caregiverInfo[6] = rs.getString(7);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        //disconnect from the database
		db.disconnect();
		//return the String array of caregiver info
		return caregiverInfo;
	}
	
	/**
	 * Purpose:This will get the emergency contact information needed for the 
	 *         form for the selected participant, and return it in the form of 
	 *         string array
     *         
     * @return A String array containing all the relevant data for the 
     *         participants emergency contact
     *         
     * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public String[] fetchEmergencyContactInfo()
	{
		//create the string array to hold the emergency conatact info
	    String[] emergencyContactInfo = new String[3];
		//connect to the database
	    db.connect();
	    //query the database to get the emergency contact id for the participant
		ResultSet rsECID = db.select("emergencyContactID", "Participant", 
		        "cosmoID =" + cosmoID, "");
		//set the emergency contact string to blank
        String ecID = "";
        try
        {
            //while there is still an entry for the ResultSet, set the emergency contact string
            while(rsECID.next())
            {
                ecID = rsECID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //query the database to get the Emergency contact inforamation
        ResultSet rs = db.select("firstName, lastName, phoneNumber", 
                "EmergencyContact", "emergencyContactID =" + ecID, "");
        try
        {
            //while there is another entry in the ResultSet,
            //set the string array to string result
            while(rs.next())
            {
                emergencyContactInfo[0] = rs.getString(1);
                emergencyContactInfo[1] = rs.getString(2);
                emergencyContactInfo[2] = rs.getString(3);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        //disconnect from database
		db.disconnect();
		//return the string array of emergency contact information
		return emergencyContactInfo;
	}
	


	/**
	 * Purpose: Saves the participants basic information to the database,
	 * updating the current record
	 * @param firstName - participants first name
	 * @param lastName - participants last name
	 * @param address - participants address
	 * @param city - participants city
	 * @param postalCode - participants postal code
	 * @param phoneNumber - participants phone number
	 * @param birthDate - participants birth date
	 * @param sin - participants social insurance number
	 */
	public void saveBasicParticipantInformation(String firstName, String lastName,
			String address, String city, String postalCode, String phoneNumber,
			String birthDate, String sin)
	{
		//connect the database
		db.connect();
		
		//the values to update in the database
		String[][] values = new String[9][2];
		values[0][0] = "cosmoID";
		values[0][1] = "" + cosmoID;
		values[1][0] = "firstName";
		values[1][1] = firstName;
		values[2][0] = "lastName";
		values[2][1] = lastName;
		values[3][0] = "address";
		values[3][1] = address;
		values[4][0] = "city";
		values[4][1] = city;
		values[5][0] = "postalCode";
		values[5][1] = postalCode;
		values[6][0] = "phoneNum";
		values[6][1] = phoneNumber;
		values[7][0] = "dateOfBirth";
		values[7][1] = birthDate;
		values[8][0] = "socialInsuranceNumber";
		values[8][1] = sin;
		
		//update participant's record
		db.update(values, "Participant", values[0][1]);
		
		//free up database resources
		db.disconnect();
		
	}
	
	/**
	 * Purpose: Save the information for a kin record. If the
	 * information has changed, it is assumed to be a new person,
	 * and a new record will be created, else it will stay the same.
	 * @param firstName - the kins first name
	 * @param lastName - the kins last name
	 * @param address - the kins address
	 * @param city - the kins city
	 * @param postalCode - the kins postal code
	 * @param homePhone - the kins home phone number
	 * @param workPhone - the kins work phone number
	 */
	public void saveKinInformation(String firstName, String lastName, String address,
			String city, String postalCode, String homePhone, String workPhone)
	{
		//connect to the database
		db.connect();
		
		//the array of values to compare against
		String[][] values = new String[7][2];

		values[0][0] = "firstName";
		values[0][1] = firstName;
		values[1][0] = "lastName";
		values[1][1] = lastName;
		values[2][0] = "address";
		values[2][1] = address;
		values[3][0] = "city";
		values[3][1] = city;
		values[4][0] = "postalCode";
		values[4][1] = postalCode;
		values[5][0] = "homePhoneNumber";
		values[5][1] = homePhone;
		values[6][0] = "workPhoneNumber";
		values[6][1] = workPhone;
		
		//create the string to put into the WHERE statement for the
			//query
		String whereStmt = this.createWhereStatement(values, 7);
		
		//query the database for the kinID, if the information is
		//already there for a record in the database
		ResultSet rs = db.select("kinID", "Kin", whereStmt, "");
		
		//the information to use to update the participant record
		String[][] kinInfo = new String[2][2];
		kinInfo[0][0] = "cosmoID";
		kinInfo[0][1] = "" + cosmoID;
		kinInfo[1][0] = "kinID";
		kinInfo[1][1] = "";
		try {
			//get the kinID
			rs.next();
			
			kinInfo[1][1] = rs.getString(1);
			System.out.println("KIN ID = " + kinInfo[1][1]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//if there is a matching record
		if(!(kinInfo[1][1].equals("") || kinInfo[1][1].equals(null)))
		{
			//make sure the participant record is using it 
			db.update(kinInfo, "Participant", kinInfo[0][1]);
		}
		//else, there is no matching record
		else
		{
			//insert the new record into the database
			db.insert(values, "Kin");
			
			//create the string to put into the WHERE clause
			whereStmt = this.createWhereStatement(values, 2);
			
			//check for the kinID of the new record
			rs = db.select("kinID", "Kin", whereStmt, "");
			
			//the information to update the participant table with
			kinInfo = new String[2][2];
			kinInfo[0][0] = "cosmoID";
			kinInfo[0][1] = "" + cosmoID;
			kinInfo[1][0] = "kinID";
			kinInfo[1][1] = "";
			try {
				
				//get the kinID
				kinInfo[1][1] = rs.getString(1);
				
				
				System.out.println("KIN ID = " + kinInfo[1][1]);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(!(kinInfo[1][1].equals("") || kinInfo[1][1].equals(null)))
			{
				//update the participant record
				db.update(kinInfo, "Participant", kinInfo[0][1]);
			}
		}
		
		//disconnect from the database
		db.disconnect();
	}
	
	/**
	 * Purpose: Saves caregiver information if it has been updated,
	 * and creates a new record. Else, it will stay the same.
	 * @param firstName - caregivers first name
	 * @param lastName - caregivers last name
	 * @param address - caregivers address
	 * @param city - caregivers address
	 * @param postalCode - caregivers postal code
	 * @param homePhone - caregivers home phone number
	 * @param workPhone - caregivers work phone number
	 */
	public void saveCaregiverInformation(String firstName, String lastName,
			String address, String city, String postalCode, String homePhone, 
			String workPhone)
	{
		//connect to the database
		db.connect();
		
		//the array of values to check against in the database
		String[][] values = new String[7][2];
		
		values[0][0] = "firstName";
		values[0][1] = firstName;
		values[1][0] = "lastName";
		values[1][1] = lastName;
		values[2][0] = "address";
		values[2][1] = address;
		values[3][0] = "city";
		values[3][1] = city;
		values[4][0] = "postalCode";
		values[4][1] = postalCode;
		values[5][0] = "homePhoneNumber";
		values[5][1] = homePhone;
		values[6][0] = "workPhoneNumber";
		values[6][1] = workPhone;
		
		//create the string to put into the WHERE clause
		String whereStmt = this.createWhereStatement(values, 7);
		
		//query the database for the values given, to see if it exists
		ResultSet rs = db.select("caregiverID", "Caregiver", whereStmt, "");
		
		String caregiverID = "";
		System.out.println("Before try...");
		try {
			if(rs.next())
			{
				//get the caregiverID if it is there
				caregiverID = rs.getString(1);
				
			}
			else
			{
				caregiverID = "";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//if the caregiverID is empty/null
		if(caregiverID.equals("") || caregiverID.equals(null) || caregiverID.equals(" "))
		{
			//insert the caregiver into the database
			db.insert(values, "Caregiver");
			
			//query the database for the new caregiver's ID
			rs = db.select("caregiverID", "Caregiver", whereStmt, "");
			
			try {
				if(rs.next())
				{
					//get the caregiverID
					caregiverID = rs.getString(1);
					
					//the information to update the participant record
					String[][] caregiverInfo = new String[2][2];
					caregiverInfo[0][0] = "cosmoID";
					caregiverInfo[0][1] = "" + cosmoID;
					caregiverInfo[1][0] = "caregiverID";
					caregiverInfo[1][1] = caregiverID;
					
					//update the participant record
					db.update(caregiverInfo, "Participant", caregiverInfo[0][1]);
				}
				else
				{
					//something went wrong
					System.out.println("ERROR COULD NOT UPDATE");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		//disconnect from the database
		db.disconnect();
	}
	
	/**
	 * Purpose: If the emergency contact information has been updated,
	 * create a new emergency contact record, and update the participant
	 * record to use this emergency contact. Else, nothing changes.
	 * @param firstName - emergency contacts first name
	 * @param lastName - emergency contacts last name
	 * @param phone - emergency contacts phone number
	 */
	public void saveEmergencyContactInformation(String firstName, String lastName,
			String phone)
	{
		//connect to the database
		db.connect();
		
		//the array of values to be checked against
		String[][] values = new String[3][2];
		
		values[0][0] = "firstName";
		values[0][1] = firstName;
		values[1][0] = "lastName";
		values[1][1] = lastName;
		values[2][0] = "phoneNumber";
		values[2][1] = phone;
		
		//the string to be put into the WHERE clause
		String whereStmt = this.createWhereStatement(values, 3);
		
		//query the database for the current emergency contact
		ResultSet rs = db.select("emergencyContactID", "EmergencyContact", whereStmt, "");
		
		String ecID = "";
		
		try {
			if(rs.next())
			{
				//get the contactID
				ecID = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//if the emergency contact record does not currently exist
		if(ecID.equals("") || ecID.equals(null))
		{
			//insert the new emergency contact record into the database
			db.insert(values, "EmergencyContact");
		
			rs = db.select("emergencyContactID", "EmergencyContact", whereStmt, "");
			
			try {
				if(rs.next())
				{
					//get the emergencyContactID
					ecID = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//the array of values to be updated in the
			//participant record
			values = new String[2][2];
			
			values[0][0] = "cosmoID";
			values[0][1] = "" + cosmoID;
			values[1][0] = "emergencyContactID";
			values[1][1] = ecID;
			
			//update the participant record
			db.update(values, "Participant", values[0][1]);
		
		}
		
		//disconnect from the database
		db.disconnect();
	}
	
	/**
	 * Purpose: creates the string to put into the 
	 * WHERE clause of queries.
	 * @param values - the values to be used in the WHERE clause
	 * @param length - the length of the first dimension of the
	 * array
	 * @return - the string to put into the WHERE clause
	 */
	private String createWhereStatement(String[][] values, int length)
	{
		
		String whereStmt = "";
		
		//for each element in the first dimension
		for(int i = 0; i < length; i++)
		{
			//add the name of the value
			whereStmt += values[i][0];
			
			//if the value is null
			if(values[i][1] == null || values[i][1].equals(null))
			{
				//use IS NULL
				whereStmt += " IS NULL";
			}
			//else, value is not null
			else
			{
				//have it equal the value
				whereStmt += "=";
				whereStmt += "\"" + values[i][1] + "\"";
			}
			
			//add and onto the end of each element, except
			//for the last element
			if(i < length - 1)
			{
				whereStmt += " and ";
			}
		}
		
		return whereStmt;
	}
	
}



