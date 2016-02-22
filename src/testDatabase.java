import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class testDatabase {

	//the database object
	private DatabaseHelper db;
	//the participant's cosmoID
	private String cosmoID = "123";
	//the participant's first name
	private String firstName = "jon";
	//the participant's last name
	private String lastname = "froese";
	//participants date of birth
	private String dob = "January 12, 2016";
	//participants personal health number
	private String phn = "123567891";
	//participants condition/diagnosis
	private String diagnosis = "downs syndrome";
	//the description of the condition/diagnosis
	private String diagnosisDesc = "Mild";
	//the participants address
	private String address = "344 Lane Crescent";
	//what the participant is allergic to
	private String allergen = "Peanuts";
	//the type of allergy the participant has
	private String allergyType = "Mild";
	//the description of the allergy
	private String allergyDesc = "When peanuts are ingested, "
			+ "he gets a headache as well as stomach ache.";
	//the type of seizures the participant has
	private String seizureType = "Stroke Like";
	//the description of the seizures
	private String seizureDesc = "Looks like a stroke.";
	//how often the participant has seizures
	private String frequency = "weekly";
	//how long seizures usually last
	private String duration = "5-10 minutes";
	//the aftermath of their seizures
	private String aftermath = "Headache, as well as chest pains.";
	//the medication taken for seizures
	private String seizureMeds = "xanax";
	//medication the participant is on
	private String medicationName = "Advil";
	//how much they take at a time
	private String dosage = "1";
	//how often they take that medication
	private String timesGiven = "Once a day";
	//the reson why that medication is needed
	private String reason = "Head ache";
	//type of vaccination the participant has received
	private String vacName = "Flu Shot";
	//the date they were given the vaccination
	private String dateGiven = "1/12/2016";
	//the name of another vaccination they have received
	private String vacName2 = "Stop Illness";
	//the date that vaccination was given
	private String dateGiven2 = "1/11/2016";
	//the first name of their kin
	private String kinFirstName = "Steven";
	//the last name of their kin
	private String kinLastName = "Palchinski";
	//the kin's address
	private String kinAddress = "123 Fake Real Place";
	//the city their kin lives in
	private String kinCity = "Saskatoon";
	//the province they live in
	private String kinProv = "SK";
	//their postal code
	private String kinPostalCode = "S7K P02";
	//their phone number
	private String kinPhone = "13062221234";
	//the participant's physician's first name
	private String physName = "Greg";
	//their last name
	private String physLastName = "Phil";
	//their phone number
	private String physPhone = "130697899";
	//the participant's work area
	private String workArea = "Shredding";
	//whether or not they work full time
	private String fullTime = "Yes";
	//when their work day begins
	private String AM = "8:00";
	//when it ends
	private String PM = "3:00";
	//the participant's caregivers first name
	private String careFirstName = "Marius";
	//their last name
	private String careLastName = "Leusca";
	//their phone number
	private String carePhone ="306789465";
	//the primary key for the participants seizures
	private String seizureID = "1";
	//the primary key for the participants caregiver
	private String caregiverID = "1";
	//the primary key for the participants physician
	private String physicianID = "1";
	//the primary key for the participants work information
	private String wordID = "2";
	//the primary key for the participants kin
	private String kinID = "2";
	
	
	
	@Before
	public void setUp() throws Exception 
	{
		//create the DB helper object and connect it to the database
		db = new DatabaseHelper();
		db.connect();
		
		
	}


	@Test
	public void testSelect() 
	{
		//select the participants basic information from the database, as well as
			//any foreign keys to be used to find other information in other tables
		ResultSet rs = db.select("firstName, lastName, dateOfBirth, personalHealthNumber,"
				+ "address, physicianID, workID, kinID, caregiverID", "Participant", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that all information matches the expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(firstName));
				assertTrue(rs.getString(2).equals(lastname));
				
				DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
				String date = (format.format(rs.getTimestamp(3)));
				
				assertTrue(date.equals(dob));
				assertTrue(rs.getString(4).equals(phn));
				assertTrue(rs.getString(5).equals(address));
				assertTrue(rs.getString(6).equals(physicianID));
				assertTrue(rs.getString(7).equals(wordID));
				assertTrue(rs.getString(8).equals(kinID));
				assertTrue(rs.getString(9).equals(caregiverID));
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the participants allergy information
		rs = db.select("allergicTo, allergyType, description", "Allergies", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(allergen));
				assertTrue(rs.getString(2).equals(allergyType));
				assertTrue(rs.getString(3).equals(allergyDesc));
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the participant's caregivers information
		rs = db.select("firstName, lastName, phone", "Caregiver", "caregiverID = " + caregiverID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(careFirstName));
				assertTrue(rs.getString(2).equals(careLastName));
				assertTrue(rs.getString(3).equals(carePhone));
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the condition information for the participant
		rs = db.select("conditionName, description", "Condition", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that the information matches the expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(diagnosis));
				assertTrue(rs.getString(2).equals(diagnosisDesc));
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the participants kin's information
		rs = db.select("kinID, firstName, lastName, address, city, prov, postalCode, phoneNumber", "Kin", "kinID = " + kinID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(kinID));
				assertTrue(rs.getString(2).equals(kinFirstName));
				assertTrue(rs.getString(3).equals(kinLastName));				
				assertTrue(rs.getString(4).equals(kinAddress));
				assertTrue(rs.getString(5).equals(kinCity));
				assertTrue(rs.getString(6).equals(kinProv));
				assertTrue(rs.getString(7).equals(kinPostalCode));
				assertTrue(rs.getString(8).equals(kinPhone));
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the medication(s) information for the participant
		rs = db.select("medicationName, dosage, timesGiven, reason", "Medication", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(medicationName));
				assertTrue(rs.getString(2).equals(dosage));
				assertTrue(rs.getString(3).equals(timesGiven));				
				assertTrue(rs.getString(4).equals(reason));
			
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the participants physicians information
		rs = db.select("physicianID, firstName, lastName, phone", "Physician", "physicianID = " + physicianID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(physicianID));
				assertTrue(rs.getString(2).equals(physName));
				assertTrue(rs.getString(3).equals(physLastName));		
				assertTrue(rs.getString(4).equals(physPhone));				
				
			
			}
		}
		catch(SQLException e)
		{
			
		}
		
		//select the partipants seizure information
		rs = db.select("seizureID, seizureType, description, frequency, duration, aftermath, medicationName", "Seizures", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(seizureID));
				assertTrue(rs.getString(2).equals(seizureType));
				assertTrue(rs.getString(3).equals(seizureDesc));						
				assertTrue(rs.getString(4).equals(frequency));
				assertTrue(rs.getString(5).equals(duration));
				assertTrue(rs.getString(6).equals(aftermath));
				assertTrue(rs.getString(7).equals(seizureMeds));
				
			
			}
		}
		catch(SQLException e)
		{
			
		}
		
		
		//select the vaccination information for the participant
		rs = db.select("vaccinationName", "Vaccination", "cosmoID = " + cosmoID, "");
		
		try
		{
			//check that it matches expected result
			rs.next();
			assertTrue(rs.getString(1).equals(vacName));
			rs.next();
			assertTrue(rs.getString(1).equals(vacName2));
		}
		catch(SQLException e)
		{
			
		}
		//select the participants work area information
		rs = db.select("workArea, fullTime, am, pm", "WorkDetails", "workID = " + wordID, "");
		
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				assertTrue(rs.getString(1).equals(workArea));
				assertTrue(rs.getString(2).equals(fullTime));
				assertTrue(rs.getString(3).equals(AM));
				assertTrue(rs.getString(4).equals(PM));
			}
		}
		catch(SQLException e)
		{
			
		}
	
	}
	
	/**
	 * Test that java can successfully insert into the activity log.
	 */
	@Test
	public void testActivityLogInsert()
	{
		db.activtyLogEntry("testUser", "Login", "");
		ResultSet rs = db.select("Who, Event", "ActivityLog", "Who = 'testUser'", "");
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				String test = rs.getString(2);
				assertTrue(rs.getString(1).equals("testUser"));
				
				assertTrue(test.equals("Login"));		
				
			}
		}
		catch(SQLException e)
		{
			
		}
	}
	
	/**
	 * Test that java can successfully insert into the activity log with no event.
	 */
	@Test
	public void testNoEventActivityLogInsert()
	{
		db.activtyLogEntry("testUser", "", "");
		ResultSet rs = db.select("Who, Event", "ActivityLog", "Who = 'testUser'", "");
		try
		{
			//check that it matches expected result
			while(rs.next())
			{
				String test = rs.getString(2);
				boolean test2 = (rs.getString(2).equals("Login"));
				assertTrue(rs.getString(1).equals("testUser"));
			    assertTrue(rs.getString(2).equals("Login"));
				
				
			}
		}
		catch(SQLException e)
		{
			
		}
	}
	
   /**
     * Test that java can successfully insert into the activity log.
     */
    @Test
    public void testActivityLogInsertEditedParticipant()
    {
        db.activtyLogEntry("testUser", "Edited Participant (" + cosmoID + ")", "Unit Test");
        ResultSet rs = db.select("Who, Event", "ActivityLog", "Who = 'testUser'", "");
        try
        {
            //check that it matches expected result
            while(rs.next())
            {
                String test = rs.getString(1);
                assertTrue(rs.getString(1).equals("testUser"));
                assertTrue(rs.getString(2).equals("Edited Participant (" + cosmoID + ")"));
                
            }
        }
        catch(SQLException e)
        {
            
        }
    }
	

}
