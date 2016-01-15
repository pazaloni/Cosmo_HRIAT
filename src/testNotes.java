import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class testNotes {

    Participant test1;
    Participant test2;
    //MedicalAdministrator testHelper;

    private String cosmoIDOne;
    private String participantNameOne;
    private String informationLastUpdatedOne;
    private String participantAddressOne;
    private String emergencyContactNameOne;
    private String emergencyContactPhoneOne;   
    
    private String cosmoIDTwo;
    private String participantNameTwo;
    private String informationLastUpdatedTwo;
    private String participantAddressTwo;
    private String emergencyContactNameTwo;
    private String emergencyContactPhoneTwo;    
    
    private Note noteOne;
    private Note noteTwo;
	
    private DatabaseHelper db;
    
	private String basicUsername;
    private String basicFirstName;
    private String basicLastName;
    private String basicEmail;
    private String basicPassword;
    private String basicSecurity;
    StaffAccount basicStaff;
    
    ManageStaffAccountHelper acctHelper;
    
    private MedicalAdministrator jcorniere;
    private BasicStaff tdoctor;
    private BasicStaff faker;
    private Note test;
	@Before
	public void setUp() throws Exception {
		db = new DatabaseHelper();
    	cosmoIDOne = "123";
    	participantNameOne = "Jeff Jones";
    	informationLastUpdatedOne = "07-Jul-2007";
    	participantAddressOne = "777 Heaven Dr.";
    	emergencyContactNameOne = "St. George";
    	emergencyContactPhoneOne = "66987777777";

    	cosmoIDTwo = "666";
    	participantNameTwo = "Chad Stones";
    	informationLastUpdatedTwo = "20-Dec-2012";
    	participantAddressTwo = "666 Downstairs Alley";
    	emergencyContactNameTwo = "Louis Cipher";
    	emergencyContactPhoneTwo = "666911666";

    	basicUsername = "ahundeby";
    	basicLastName = "Hundeby";
    	basicFirstName = "Andrew";
    	basicEmail = "andy@hundeby.com";
    	basicPassword = "password";
    	basicSecurity = "1";
    	acctHelper = new ManageStaffAccountHelper();
        test1 = new Participant(cosmoIDOne, participantNameOne, participantAddressOne, emergencyContactNameOne, emergencyContactPhoneOne, informationLastUpdatedOne);
        test2 = new Participant(cosmoIDTwo, participantNameTwo, participantAddressTwo, emergencyContactNameTwo, emergencyContactPhoneTwo, informationLastUpdatedTwo);
        basicStaff = new BasicStaff(basicUsername, basicLastName, basicFirstName, basicEmail, basicPassword, basicSecurity);
        
	}

	@Test
	public void testSuccess() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName, basicEmail, 
				basicPassword, basicPassword, basicSecurity);
    	
    	basicStaff.login(basicUsername, basicPassword);
    	
    	test = new Note(test1,"test description", basicStaff.GetUsername() );
		assertTrue(test.addNote().equals(""));
		
	}
	@Test
	public void testFail() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName, basicEmail, 
				basicPassword, basicPassword, basicSecurity);
    	
    	basicStaff.login(basicUsername, basicPassword);
		Note test = new Note(test1,"", basicStaff.GetUsername() );
		
		assertFalse(test.addNote().equals(""));
	}
}
