package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.healthmarketscience.jackcess.util.CaseInsensitiveColumnMatcher;

import controllers.ParticipantUpdateFormController;

public class TestEditParticipantUpdateForm 
{
	int cosmoID = 7542;
	ParticipantUpdateFormController controller = 
			new ParticipantUpdateFormController(cosmoID);
	
	//valid kin information
	String kinFirstName = "Bre";
	String kinLastName = "Wil";
	String kinCity = "Moose Jaw";
	String kinAddress = "123 New Ave";
	String kinPostalCode = "S7K 1K3";
	String kinHomePhone = "306-222-1234";
	String kinWorkPhone = "306-751-2209";
	
	//invalid kin information
	String iKinFirstName = "";
	String iKinLastName = "2131";
	String iKinCity = "cf31v5rvc3";
	String iKinAddress = "45vt6245t6 fghj sty h str";
	String iKinPostalCode = "l2l2l2";
	String iKinHomePhone = "3061233211";
	String iKinWorkPhone = "3331112222";
	
	//valid caregiver information
	String caregiverFirstName = "Anna";
	String caregiverLastName = "Son";
	String caregiverCity = "Elbow";
	String caregiverAddress = "321 New Place";
	String caregiverPostalCode = "S7K 4G5";
	String caregiverHomePhone = "305-999-1211";
	String caregiverWorkPhone = "800-437-9331";
	
	//invalid caregiver information
	String iCaregiverFirstName = "$%*^$&^&";
	String iCaregiverLastName = "";
	String iCaregiverAddress = "sfgnb 34893 trighr 324832";
	String iCaregiverCity = "43v5345 b3567 b&*%&*";
	String iCaregiverPostalCode = "this is a postal code";
	String iCaregiverHomePhone = "2131-123-12311231";
	String iCaregiverWorkPhone = "numbers here";
	
	//valid emergency contact information
	String emergencyContactFirstName = "John";
	String emergencyContactLastName = "Wick";
	String emergencyContactPhone = "507-222-2209";
	
	//invalid emergency contact information
	String iEmergencyContactFirstName = "";
	String iEmergencyContactLastName = "(&*(79&6*^";
	String iEmergencyContactPhone = "*(^&*68768^*&";
	
	//String to hold error messages
	String errorMsg;
	
	/**
	 * Purpose: Resets the error message for each test,
	 * to that one from the previous test does not affect the current one.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		errorMsg = "";
	}
	
	/**
	 * Purpose: Test that when valid kin information is given for a participant,
	 * it passes checks to be saved.
	 */
	@Test
	public void testValidKinInformation() 
	{
		//try to save using valid information for all fields
		errorMsg = controller.saveKinInformation(kinFirstName, kinLastName, 
				kinAddress, kinCity, kinPostalCode, kinHomePhone, kinWorkPhone);
		
		//there should be no error message, since the information was valid
		assertTrue(errorMsg.equals(""));
		
		//try to save only using required fields
		errorMsg = controller.saveKinInformation(kinFirstName, kinLastName, "",
				"", "", "", "");
		//there should be no error message
		assertTrue(errorMsg.equals(""));
	}
	
	/**
	 * Purpose: Test that when invalid kin information is given for a participant,
	 * it fails the checks for validation.
	 */
	@Test
	public void testInvalidKinInformation()
	{
		//try to save using invalid information for all fields
		errorMsg = controller.saveKinInformation(iKinFirstName, iKinLastName,
				iKinAddress, iKinCity, iKinPostalCode, iKinHomePhone, iKinWorkPhone);
		//there should be an error message
		assertFalse(errorMsg.equals(""));
		
		//clear error message
		errorMsg = "";
		
		//try to save missing required fields
		errorMsg = controller.saveKinInformation("", "", kinAddress, kinCity, kinPostalCode,
				kinHomePhone, kinWorkPhone);
		//there should be an error message
		assertFalse(errorMsg.equals(""));
		
	}	
	
	/**
	 * Purpose: Test that when valid caregiver information is given,
	 * it passes validation checks to go into the database.
	 */
	@Test
	public void testValidCaregiverInformation()
	{
		//try to save using valid information for all fields
		errorMsg = controller.saveCaregiverInformation(caregiverFirstName, caregiverLastName,
				caregiverAddress, caregiverCity, caregiverPostalCode, caregiverHomePhone, 
				caregiverWorkPhone);
		//there should not be an error message
		assertTrue(errorMsg.equals(""));
		
		//clear error message
		errorMsg = "";
		
		//try to save with only required fields
		errorMsg = controller.saveCaregiverInformation(caregiverFirstName, caregiverLastName,
				"", "", "", "", "");
		//there should not be an error message
		assertTrue(errorMsg.equals(""));
		
	}
	
	/**
	 * Purpose: Test that when invalid caregiver information is given,
	 * it does not pass the validation checks for saving.
	 */
	@Test
	public void testInvalidCaregiverInformation()
	{
		//try to save using invalid caregiver information for all fields
		errorMsg = controller.saveCaregiverInformation(iCaregiverFirstName, iCaregiverLastName, 
				iCaregiverAddress, iCaregiverCity, iCaregiverPostalCode, iCaregiverHomePhone, 
				iCaregiverWorkPhone);
		//there should be an error message
		assertFalse(errorMsg.equals(""));
		
		//clear the error message
		errorMsg = "";
		
		//try to save without the required fields
		errorMsg = controller.saveCaregiverInformation("", "", caregiverAddress, caregiverCity,
				caregiverPostalCode, caregiverHomePhone, caregiverWorkPhone);
		//there should be an error message
		assertFalse(errorMsg.equals(""));
	}
	
	/**
	 * Purpose: Test that when valid emergency contact information is given,
	 * it passes the validation checks for saving
	 */
	@Test
	public void testValidEmergencyContactInformation()
	{
		//try to save with valid information for all fields
		errorMsg = controller.saveEmergencyContactInformation(emergencyContactFirstName, 
				emergencyContactLastName, emergencyContactPhone);
		//there should be no error message
		assertTrue(errorMsg.equals(""));
	}
	
	/**
	 * Purpose: Tests that when invalid emergency contact information is given,
	 * it fails validation checks for saving
	 */
	@Test
	public void testInvalidEmergencyContactInformation()
	{
		//try to save with invalid information for all fields
		errorMsg = controller.saveEmergencyContactInformation(iEmergencyContactFirstName, 
				iEmergencyContactLastName, iEmergencyContactPhone);
		//there should be an error message
		assertFalse(errorMsg.equals(""));
		
		//clear error message
		errorMsg = "";
		
		//try to save with required fields missing
		errorMsg = controller.saveEmergencyContactInformation("", "", "");
		//there should be an error message
		assertFalse(errorMsg.equals(""));
	}

}
