package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	String kinPostalCode = "S0P A3K";
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
	
	@Before
	public void setUp() throws Exception 
	{
		errorMsg = "";
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testValidKinInformation() 
	{
		
	}
	
	@Test
	public void testInvalidKinInformation()
	{
		
	}
	
	@Test
	public void testValidCaregiverInformation()
	{
		
	}
	
	@Test
	public void testInvalidCaregiverInformation()
	{
		
	}
	
	@Test
	public void testValidEmergencyContactInformation()
	{
		
	}
	
	@Test
	public void testInvalidEmergencyContactInformation()
	{
		
	}

}
