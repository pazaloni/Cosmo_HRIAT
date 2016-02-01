import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class testNotes {

	// Participant used in test 1
	Participant test1;

	// Cosmo ID for the test's participant
	private String cosmoIDOne;
	// Name of the test's participant
	private String participantNameOne;
	// Timestamp for the test's participant
	private String informationLastUpdatedOne;
	// Address for the test's participant
	private String participantAddressOne;
	// Contact name for the test's participant
	private String emergencyContactNameOne;
	// Contact Phone for the test's participant
	private String emergencyContactPhoneOne;

	// username of the user adding the note
	private String basicUsername;
	// first name of the user adding the note
	private String basicFirstName;
	// last name of the user adding the note
	private String basicLastName;
	// Email address of the user adding the note
	private String basicEmail;
	// Password of the user adding the note
	private String basicPassword;
	// Access level of the user adding the note
	private String basicSecurity;
	// The user adding the note
	StaffAccount basicStaff;
	// The object that access the database
	ManageStaffAccountHelper acctHelper;
	// The note that is being added to the database
	private Note test;

	/**
	 * Creates the participant and user that will be used to run the tests
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		new DatabaseHelper();
		cosmoIDOne = "123";
		participantNameOne = "Jeff Jones";
		informationLastUpdatedOne = "07-Jul-2007";
		participantAddressOne = "777 Heaven Dr.";
		emergencyContactNameOne = "St. George";
		emergencyContactPhoneOne = "66987777777";

		basicUsername = "ahundeby";
		basicLastName = "Hundeby";
		basicFirstName = "Andrew";
		basicEmail = "andy@hundeby.com";
		basicPassword = "password";
		basicSecurity = "1";
		acctHelper = new ManageStaffAccountHelper();
		test1 = new Participant(cosmoIDOne, participantNameOne,
				participantAddressOne, emergencyContactNameOne,
				emergencyContactPhoneOne, informationLastUpdatedOne);
		basicStaff = new BasicStaff(basicUsername, basicLastName,
				basicFirstName, basicEmail, basicPassword, basicSecurity);

	}
/*
	*//**
	 * Success case that determines if a note can be added
	 *//*
	@Test
	public void testSuccess() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName,
				basicEmail, basicPassword, basicPassword, basicSecurity);

		basicStaff.login(basicUsername, basicPassword);

		test = new Note(test1, "test description", basicStaff.GetUsername(),
				new Date(), false, false);
		assertTrue(test.addNote().equals(""));

	}

	*//**
	 * Failure case that checks that a note cannot be added if there is no
	 * description
	 *//*
	@Test
	public void testFail() {
		acctHelper.addUser(basicUsername, basicLastName, basicFirstName,
				basicEmail, basicPassword, basicPassword, basicSecurity);

		basicStaff.login(basicUsername, basicPassword);
		Note test = new Note(test1, "", basicStaff.GetUsername(), new Date(),
				false, false);
		String result = test.addNote();
		System.out.println(result);
		assertFalse(result.equals(""));
	}
*/}
