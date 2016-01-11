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
	
	private String basicUsername;
    private String basicFirstName;
    private String basicLastName;
    private String basicEmail;
    private String basicPassword;
    private String basicEmailSecurity;
    StaffAccount basicStaff;
    
    ManageStaffAccountHelper acctHelper;
    
    private MedicalAdministrator jcorniere;
    private BasicStaff tdoctor;
    private BasicStaff faker;
	@Before
	public void setUp() throws Exception {
    	cosmoIDOne = "777";
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

        
        test1 = new Participant(cosmoIDOne, participantNameOne, participantAddressOne, emergencyContactNameOne, emergencyContactPhoneOne, informationLastUpdatedOne);
        test2 = new Participant(cosmoIDTwo, participantNameTwo, participantAddressTwo, emergencyContactNameTwo, emergencyContactPhoneTwo, informationLastUpdatedTwo);
        
	}

	@Test
	public void testSuccess() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName, basicEmail, 
				basicPassword, basicPassword, basicEmailSecurity);
    	
    	basicStaff.login(basicUsername, basicPassword);
		fail("Not yet implemented");
		Note test = new Note(test1,"test description", tdoctor.GetUsername() );
		assertTrue(test.error.length() == 0);
	}

	public void testFail() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName, basicEmail, 
				basicPassword, basicPassword, basicEmailSecurity);
    	
    	basicStaff.login(basicUsername, basicPassword);
		fail("Not yet implemented");
		Note test = new Note(test1,"", tdoctor.GetUsername() );
		assertFalse(test.error.length() == 0);
	}
}
