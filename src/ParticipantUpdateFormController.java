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
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //disconnect from database
		db.disconnect();
		//return the string array of emergency contact information
		return emergencyContactInfo;
	}
	
	public void saveBasicParticipantInformation(String firstName, String lastName,
			String address, String city, String postalCode, String phoneNumber,
			String birthDate, String sin)
	{
		
		db.connect();
		
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
		
		db.update(values, "Participant", values[0][1]);
		
		db.disconnect();
		
	}
	
	public void saveKinInformation(String firstName, String lastName, String address,
			String city, String postalCode, String homePhone, String workPhone)
	{
		db.connect();
		
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
		//db.insert(values, "Kin");

		String whereStmt = this.createWhereStatement(values);
		
		ResultSet rs = db.select("kinID", "Kin", whereStmt, "");
		
		String[][] kinInfo = new String[2][2];
		kinInfo[0][0] = "cosmoID";
		kinInfo[0][1] = "" + cosmoID;
		kinInfo[1][0] = "kinID";
		kinInfo[1][1] = "";
		try {
			rs.next();
			
			kinInfo[1][1] = rs.getString(1);
			System.out.println("KIN ID = " + kinInfo[1][1]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!(kinInfo[1][1].equals("") || kinInfo[1][1].equals(null)))
		{
			db.update(kinInfo, "Participant", kinInfo[0][1]);
		}
		else
		{
			db.insert(values, "Kin");
			
			whereStmt = this.createWhereStatement(values);
			
			rs = db.select("kinID", "Kin", whereStmt, "");
			
			kinInfo = new String[2][2];
			kinInfo[0][0] = "cosmoID";
			kinInfo[0][1] = "" + cosmoID;
			kinInfo[1][0] = "kinID";
			kinInfo[1][1] = "";
			try {
				
				
				kinInfo[1][1] = rs.getString(1);
				
				
				System.out.println("KIN ID = " + kinInfo[1][1]);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(!(kinInfo[1][1].equals("") || kinInfo[1][1].equals(null)))
			{
				db.update(kinInfo, "Participant", kinInfo[0][1]);
			}
		}
		
		db.disconnect();
	}
	
	public void saveCaregiverInformation(String firstName, String lastName,
			String address, String city, String postalCode, String homePhone, 
			String workPhone)
	{
		db.connect();
		
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
		
		String whereStmt = this.createWhereStatement(values);
		
		ResultSet rs = db.select("caregiverID", "Caregiver", whereStmt, "");
		
		String caregiverID = "";
		
		try {
			rs.next();
			if(rs.next())
			{
				caregiverID = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(caregiverID.equals("") || caregiverID.equals(null))
		{
			db.insert(values, "Caregiver");
		}
		else
		{
			rs = db.select("caregiverID", "Participant", whereStmt, "");
			
			try {
				System.out.println("Inside try block...");
				if(rs.next())
				{
					caregiverID = rs.getString(1);
				}
				
				System.out.println("CAREGIVERID = " + caregiverID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			String[][] caregiverInfo = new String[2][2];
			caregiverInfo[0][0] = "cosmoID";
			caregiverInfo[0][1] = "" + cosmoID;
			caregiverInfo[1][0] = "caregiverID";
			caregiverInfo[1][1] = caregiverID;
			
			db.update(caregiverInfo, "Participant", caregiverInfo[0][1]);
		}
		
		db.disconnect();
	}
	
	public void saveEmergencyContactInformation(String firstName, String lastName,
			String phone)
	{
		db.connect();
		
		String[][] values = new String[3][2];
		
		values[0][0] = "firstName";
		values[0][1] = firstName;
		values[1][0] = "lastName";
		values[1][1] = lastName;
		values[2][0] = "phoneNumber";
		values[2][1] = phone;
		
		String whereStmt = this.createWhereStatement(values);
		
		ResultSet rs = db.select("emergencyContactID", "EmergencyContact", whereStmt, "");
		
		String ecID = "";
		
		try {
			rs.next();
			ecID = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ecID.equals("") || ecID.equals(null))
		{
			db.insert(values, "EmergencyContact");
		}
		else
		{
			values = new String[4][2];
			
			values[0][0] = "emergencyContactID";
			values[0][1] = ecID;
			values[1][0] = "firstName";
			values[1][1] = firstName;
			values[2][0] = "lastName";
			values[2][1] = lastName;
			values[3][0] = "phoneNumber";
			values[3][1] = phone;
			
			db.update(values, "EmergencyContact", ecID);
		}
		
		
		db.disconnect();
	}
	
	
	private String createWhereStatement(String[][] values)
	{
		
		String whereStmt = "";
		
		for(int i = 0; i < 7; i++)
		{
			whereStmt += values[i][0];
			whereStmt += "=";
			whereStmt += "\"" + values[i][1] + "\"";
			if(i < 6)
			{
				whereStmt += " and ";
			}
		}
		
		return whereStmt;
	}
	
}
