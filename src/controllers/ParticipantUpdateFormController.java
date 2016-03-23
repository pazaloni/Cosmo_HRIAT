package controllers;
import helpers.DatabaseHelper;
import helpers.FormatHelper;

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
	
	private String kinFirstName;
	private String kinLastName;
	private String kinAddress;
	private String kinCity;
	private String kinPostalCode;
	private String kinHomePhone;
	private String kinWorkPhone;
	
	private String caregiverFirstName;
	private String caregiverLastName;
	private String caregiverAddress;
	private String caregiverCity;
	private String caregiverPostalCode;
	private String caregiverHomePhone;
	private String caregiverWorkPhone;
	
	private String emergencyContactPhoneNumber;
	
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
				+ " postalCode, phoneNumber, personalHealthNumber, socialInsuranceNumber"
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
	 * @return - a String containing any error messages produced during
	 * 	data validation process.
	 * @author Breanna Wilson CST215
	 */
	public String saveKinInformation(String firstName, String lastName, String address,
			String city, String postalCode, String homePhone, String workPhone)
	{
		kinFirstName = firstName;
		kinLastName = lastName;
		kinAddress = address;
		kinCity = city;
		kinPostalCode = postalCode;
		kinHomePhone = homePhone;
		kinWorkPhone = workPhone;
		
		String msg = "";
		//check if the info given is valid
		msg = validateKinInformation(firstName,
				lastName, address, city, postalCode, homePhone, workPhone);
		
		//connect to the database
		db.connect();
		
		//the array of values to compare against
		String[][] values = new String[7][2];

		values[0][0] = "firstName";
		values[0][1] = kinFirstName;
		values[1][0] = "lastName";
		values[1][1] = kinLastName;
		values[2][0] = "address";
		values[2][1] = kinAddress;
		values[3][0] = "city";
		values[3][1] = kinCity;
		values[4][0] = "postalCode";
		values[4][1] = kinPostalCode;
		values[5][0] = "homePhoneNumber";
		values[5][1] = kinHomePhone;
		values[6][0] = "workPhoneNumber";
		values[6][1] = kinWorkPhone;
		
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
			if(rs.next())
			{
				kinInfo[1][1] = rs.getString(1);
			}
			else
			{
				kinInfo[1][1] = "";
			}
			
			
			System.out.println("KIN ID = " + kinInfo[1][1]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		//if there is a matching record
		if(!(kinInfo[1][1].equals("") || kinInfo[1][1].equals(null)))
		{
			
			//make sure the participant record is using it if the data is valid
			if(msg.equals(null) || msg.equals(""))
			{
				db.update(kinInfo, "Participant", kinInfo[0][1]);
			}
			
		}
		//else, there is no matching record
		else
		{
			
			//if the information given is valid
			if(msg.equals(null) || msg.equals(""))
			{
				//insert the new record into the database
				db.insert(values, "Kin");
				
				//create the string to put into the WHERE clause
				whereStmt = this.createWhereStatement(values, 7);
				
				//check for the kinID of the new record
				rs = db.select("kinID", "Kin", whereStmt, "");
				
				//the information to update the participant table with
				kinInfo = new String[2][2];
				kinInfo[0][0] = "cosmoID";
				kinInfo[0][1] = "" + cosmoID;
				kinInfo[1][0] = "kinID";
				kinInfo[1][1] = "";
				try {
					rs.next();
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
		}
		
		//disconnect from the database
		db.disconnect();
		
		//return any error messages
		return msg;
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
	 * @return - a String containing any error messages produced during
	 * 	data validation process.
	 * @author Breanna Wilson CST215
	 */
	public String saveCaregiverInformation(String firstName, String lastName,
			String address, String city, String postalCode, String homePhone, 
			String workPhone)
	{
		caregiverFirstName = firstName;
		caregiverLastName = lastName;
		caregiverAddress = address;
		caregiverCity = city;
		caregiverPostalCode = postalCode;
		caregiverHomePhone = homePhone;
		caregiverWorkPhone = workPhone;

		
		//connect to the database
		db.connect();
		String msg = "";
		//the array of values to check against in the database
		String[][] values = new String[7][2];
		
		values[0][0] = "firstName";
		values[0][1] = caregiverFirstName;
		values[1][0] = "lastName";
		values[1][1] = caregiverLastName;
		values[2][0] = "address";
		values[2][1] = caregiverAddress;
		values[3][0] = "city";
		values[3][1] = caregiverCity;
		values[4][0] = "postalCode";
		values[4][1] = caregiverPostalCode;
		values[5][0] = "homePhoneNumber";
		values[5][1] = caregiverHomePhone;
		values[6][0] = "workPhoneNumber";
		values[6][1] = caregiverWorkPhone;
		
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
			msg = validateCaregiverInformation(firstName, 
					lastName, address, city, postalCode, homePhone, workPhone);
			
			if(msg.equals(null) || msg.equals(""))
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
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
		
		
		}
		
		//disconnect from the database
		db.disconnect();
				
		return msg;
	}
	
	/**
	 * Purpose: If the emergency contact information has been updated,
	 * create a new emergency contact record, and update the participant
	 * record to use this emergency contact. Else, nothing changes.
	 * @param firstName - emergency contacts first name
	 * @param lastName - emergency contacts last name
	 * @param phone - emergency contacts phone number
	 * @return - a String containing any error messages produced during
	 * 	data validation process.
	 * @author Breanna Wilson CST215
	 */
	public String saveEmergencyContactInformation(String firstName, String lastName,
			String phone)
	{
		//connect to the database
		db.connect();
		String msg = "";
		emergencyContactPhoneNumber = phone;
		//the array of values to be checked against
		String[][] values = new String[3][2];
		
		values[0][0] = "firstName";
		values[0][1] = firstName;
		values[1][0] = "lastName";
		values[1][1] = lastName;
		values[2][0] = "phoneNumber";
		values[2][1] = emergencyContactPhoneNumber;
		
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
		if(ecID.equals(null) || ecID.equals(""))
		{
			msg = validateEmergencyContactInforamtion(firstName, lastName, phone);
			
			if(msg.equals(null) || msg.equals(""))
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
		
		}
		
		//disconnect from the database
		db.disconnect();
		
		return msg;
	}
	
	/**
	 * Purpose: creates the string to put into the 
	 * WHERE clause of queries.
	 * @param values - the values to be used in the WHERE clause
	 * @param length - the length of the first dimension of the
	 * array
	 * @return - the string to put into the WHERE clause
	 * @author Breanna Wilson CST215
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
	
	/**
	 * Purpose: Will validate that a phone number
	 * is in the format(s):
	 * XXX-XXX-XXXX
	 * XXX-XXXX
	 * X-XXX-XXX-XXXX
	 * @param phone - the phone number to be validated
	 * @return - true if valid, else false
	 * @author Breanna Wilson CST215
	 */
	private String validatePhoneNumber(String phone)
	{
		FormatHelper formatter = new FormatHelper();
		return formatter.formatPhoneNum(phone);
	}
	
	/**
	 * Purpose: Will validate that a postal code
	 * is in the proper format A1A 1A1.
	 * @param postalCode - the postal code to be validated
	 * @return - true if valid, else false
	 * @author Breanna Wilson CST215
	 */
	private String validatePostalCode(String postalCode)
	{
		FormatHelper formatter = new FormatHelper();
		return formatter.formatPostalCode(postalCode);
	}
	
	/**
	 * Purpose: Will validate that an address is in the format:
	 * 111 Fake St
	 * @param address - the address to be validated
	 * @return - true if valid, else false.
	 * @author Breanna Wilson CST215
	 */
	private boolean validateAddress(String address)
	{
		return address == null || address.isEmpty() || 
				address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}
	
	/**
	 * Purpose: Will validate that a city name contains
	 * only letters and whitespace characters.
	 * @param city - City name to be validated
	 * @return - true if valid, else false
	 * @author Breanna Wilson CST215
	 */
	private boolean validateCity(String city)
	{
		return city == null || city.isEmpty() ||
				city.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
	}
	
	/**
	 * Purpose: Will validate that a name only contains
	 * letters, dashes, and periods.
	 * @param name - The name to be validated
	 * @return - True if valid, else false
	 * @author Breanna Wilson CST215
	 */
	private boolean validateName(String name)
	{
		System.out.println("Name check result: " + (name.matches("^[a-zA-Z \\-\\.\\']*$") && 
				!(name.equals(""))));
		return !(name == null) && name.matches("^[a-zA-Z \\-\\.\\']*$") && 
				!(name.equals(""));
	}
	
	/**
	 * Purpose: Validate all fields for kin information
	 * that they are in the correct format.
	 * @param firstName - Kins first name
	 * @param lastName - Kins last name
	 * @param address - Kins address
	 * @param city - Kins city
	 * @param postalCode - Kins postal code
	 * @param homePhone - Kins home phone number
	 * @param workPhone - Kins work phone number
	 * @return - String with all error messages. If the string
	 * is empty, all fields are valid.
	 * @author Breanna Wilson CST215
	 */
	private String validateKinInformation(String firstName, String lastName, String address,
			String city, String postalCode, String homePhone, String workPhone)
	{
		String message = "";
		
		//if there is no first or last name, kin information is invalid.
		if(((firstName == null || firstName.equals("")) ||
				(lastName == null || lastName.equals(""))))
		{
			message += "Kin must have first and last name.\n";
		}
		else if(!(firstName == null || firstName.equals("")) &&
				!(lastName == null || lastName.equals("")) ||
				!(address == null || address.equals("")) ||
				!(city == null || city.equals("")) ||
				!(postalCode == null || postalCode.equals("")) ||
				!(homePhone == null || homePhone.equals("")) ||
				!(workPhone == null || workPhone.equals("")))
		{
			//check first name is valid
			if(!validateName(firstName))
			{
				message += "Kin first name cannot contain numbers or special characters.\n";
			}
			
			//check last name is valid
			if(!validateName(lastName))
			{
				message += "Kin last name cannot contain numbers or special characters.\n";
			}
			
			//check address is valid
			if(!validateAddress(address))
			{
				message += "Kin address is invalid.\n";
			}
			
			//check city is valid
			if(!validateCity(city))
			{
				message += "Kin city in invalid.\n";
			}
			
			//check postal code is valid
			
			if(postalCode == null || postalCode.equals(""))
			{
				kinPostalCode = "";
			}
			else if(validatePostalCode(postalCode).length() == 7)
			{
				kinPostalCode = validatePostalCode(postalCode);
			}
			else
			{
				message += "Kin postal code is invalid. " + 
						validatePostalCode(postalCode) + "\n";
			}
			
			//check home phone number is valid
			if(homePhone == null || homePhone.equals(""))
			{
				kinHomePhone = "";
			}
			else if(validatePhoneNumber(homePhone).length() == 14)
			{
				kinHomePhone = validatePhoneNumber(homePhone);
			}
			else
			{
				message += "Kin home phone number is invalid. " + 
						validatePhoneNumber(homePhone) + "\n";
			}
			
			//check work phone number is valid
			if(workPhone == null || workPhone.equals(""))
			{
				kinWorkPhone = "";
			}
			else if(validatePhoneNumber(workPhone).length() == 14)
			{
				kinWorkPhone = validatePhoneNumber(workPhone);
			}
			else
			{
				message += validatePhoneNumber(workPhone);
			}
		}
		
		
		return message;
	}
	
	/**
	 * Purpose: Validate that all caregiver information 
	 * is in the correct format.
	 * @param firstName - the first name of the caregiver
	 * @param lastName - the last name of the caregiver
	 * @param address - the address of the caregiver
	 * @param city - the city for the caregiver
	 * @param postalCode - the caregivers postal code
	 * @param homePhone - the caregivers home phone number
	 * @param workPhone - the caregivers work phone number
	 * @return - Any error messages. Will be empty if there
	 * are no errors.
	 * @author Breanna Wilson CST215
	 */
	private String validateCaregiverInformation(String firstName, String lastName,
			String address, String city, String postalCode, String homePhone, 
			String workPhone)
	{
		String message = "";
		
		//if the first or last name fields are empty, entire caregiver area
		//is invalid. 
		
		if((firstName == null || firstName.equals("")) || lastName.equals(null) ||
				lastName.equals(""))
		{
			message += "Caregiver first and last name must be provided.\n";
		}
		else if(!(firstName == null || firstName.equals("")) &&
				!(lastName == null || lastName.equals("")) ||
				!(address == null || address.equals("")) ||
				!(city == null || address.equals("")) ||
				!(postalCode == null || postalCode.equals("")) ||
				!(homePhone == null || homePhone.equals("")) ||
				!(workPhone == null || workPhone.equals("")))
		{
			if(! validateName(firstName))
			{
				message += "Caregiver first name cannot contain numbers or special characters.\n";
			}
			
			if(!validateName(lastName))
			{
				message += "Caregiver last name cannot contain numbers or special characters.\n";
			}
			
			if(!validateAddress(address))
			{
				message += "Caregiver address is invalid.\n";
			}
			
			if(!validateCity(city))
			{
				message += "Caregiver city is invalid.\n";
			}
			
			if(postalCode == null || postalCode.equals(""))
			{
				caregiverPostalCode = "";
			}
			else if(validatePostalCode(postalCode).length() == 7)
			{
				caregiverPostalCode = validatePostalCode(postalCode);
			}
			else
			{
				message += "Caregiver postal code is invalid. " + 
						validatePostalCode(postalCode) + "\n";
			}
			
			if(homePhone == null || homePhone.equals(""))
			{
				homePhone = "";
			}
			else if(validatePhoneNumber(homePhone).length() == 14)
			{
				caregiverHomePhone = validatePhoneNumber(homePhone);
			}
			else
			{
				message += "Caregiver home phone number is invalid. " + 
						validatePhoneNumber(homePhone) + "\n";
			}
			
			if(workPhone == null || workPhone.equals(""))
			{
				workPhone = "";
			}
			else if(validatePhoneNumber(workPhone).length() == 14)
			{
				caregiverWorkPhone = validatePhoneNumber(workPhone);
			}
			else
			{
				message += "Caregiver work phone number is invalid. " + 
						validatePhoneNumber(workPhone) + "\n";
			}
		}
		
		return message;
	}
	
	/**
	 * Purpose: Validates the emergency contact information, and that
	 * it is in the correct format.
	 * @param firstName - the first name of the emergency contact
	 * @param lastName - the last name of the emergency contact
	 * @param phone - phone number for the emergency contact
	 * @return - Any error messages. Will be empty if there
	 * are no errors.
	 * @author Breanna Wilson CST215
	 */
	private String validateEmergencyContactInforamtion(String firstName, String lastName,
			String phone)
	{
		String message = "";
		
		if(firstName == null || firstName.equals("") || lastName == null
				|| lastName.equals("")|| phone == null || phone.equals(""))
		{
			message += "All fields for Emergency Contact information must be filled in.\n";
		}
		//else all fields are filled out
		else
		{
			//check each individual field
			
			if(!validateName(firstName))
			{
				message += "Emergency contact first name cannot"
						+ " contain numbers or special characters.\n";
			}
			
			if(!validateName(lastName))
			{
				message += "Emergency contact last name cannot" +
						" contain numbers or special characters.\n";
			}
			
			if(phone == null || phone.equals(""))
			{
				phone = "";
			}
			else if(validatePhoneNumber(phone).length() == 14)
			{
				emergencyContactPhoneNumber = validatePhoneNumber(phone);
			}
			else
			{
 				message += "Emergency contact phone number is invalid. " + 
 						validatePhoneNumber(phone) + "\n";
			}
		}
		
		return message;
	}
	
}




