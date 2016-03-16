package tests;
import static org.junit.Assert.*;
import helpers.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.ParticipantUpdateFormController;

/**
 * Test the saving of information for the information update form.
 * @author CST215
 *
 */
public class testSaveInformationUpdate {

	private DatabaseHelper db;
	private ParticipantUpdateFormController pudc;
	
	//the information we will be pulling for the participant:
	private String pFirstName = "bob";
	private String pLastName = "smith";
	private String pAddress = "123 Fake Street";
	private String pCity = "Saskatoon";
	private String pPostalCode = "A1A 1A1";
	private String pPhone = "1111111111";
	private String pBirthDate = "2007-07-07 00:00:00.000000";
	private String pSin = "222222222";
	
	//the information we will be pulling for the kin:
	private String kFirstName = "Steven";
	private String kLastName = "Palchinski";
	private String kAddress = "123 Fake Real Place";
	private String kCity = "Saskatoon";
	private String kPostalCode = "S7K P02";
	private String kHomePhone = "13062221234";
	private String kWorkPhone = "1231112222";
	
	//the information we will be pulling for the Caregiver:
	private String cFirstName = "Maruis";
	private String cLastName = "Leusca";
	private String cAddress = "33 North Street";
	private String cCity = "Edmonton";
	private String cPostalCode = "B2B 2B2";
	private String cHomePhone = "1112223333";
	private String cWorkPhone = "9998887777";
	
	//the emergency contact information we will be pulling:
	private String ecFirstName = "Breanna";
	private String ecLastName = "Wilson";
	private String ecPhone = "3213214567";
	
	@Before
	public void setUp() throws Exception 
	{
		db = new DatabaseHelper();
		db.connect();
		pudc = new ParticipantUpdateFormController(111);
		

	}

	@After
	public void tearDown() throws Exception 
	{
		db.disconnect();
	}

	/**
	 * Purpose: Test the ability to save information to the database
	 * and check that it contains the same values entered.
	 */
	@Test
	public void test() 
	{	
		//save the caregiver information
		pudc.saveCaregiverInformation(cFirstName, cLastName, cAddress, cCity,
				cPostalCode, cHomePhone, cWorkPhone);
		
		//save the emergency contact information
		pudc.saveEmergencyContactInformation(ecFirstName, ecLastName, ecPhone);
		
		//save the kin information
		pudc.saveKinInformation(kFirstName, kLastName, kAddress, kCity, 
				kPostalCode, kHomePhone, kWorkPhone);
		
		
		//get the participants basic information
		String[] participantInfo = pudc.fetchParticipantBasicInfo();
		
		//check that it is as expected
		assertTrue(participantInfo[0].equals(pFirstName));
		assertTrue(participantInfo[1].equals(pLastName));
		assertTrue(participantInfo[2].equals(pAddress));
		assertTrue(participantInfo[3].equals(pCity));
		assertTrue(participantInfo[4].equals(pPostalCode));
		assertTrue(participantInfo[5].equals(pPhone));
		assertTrue(participantInfo[6].equals(pBirthDate));
		assertTrue(participantInfo[7].equals(pSin));
		
		//get the kin information
		String [] kinInfo = pudc.fetchKinInfo();
		
		//check that kin info is as expected
		assertTrue(kinInfo[0].equals(kFirstName));
		assertTrue(kinInfo[1].equals(kLastName));
		assertTrue(kinInfo[2].equals(kAddress));
		assertTrue(kinInfo[3].equals(kCity));
		assertTrue(kinInfo[4].equals(kPostalCode));
		assertTrue(kinInfo[5].equals(kHomePhone));
		assertTrue(kinInfo[6].equals(kWorkPhone));
		
		//get the caregiver information
		String[] caregiverInfo = pudc.fetchCaregiverInfo();
		
		//check that the information is as expected
		assertTrue(caregiverInfo[0].equals(cFirstName));
		assertTrue(caregiverInfo[1].equals(cLastName));
		assertTrue(caregiverInfo[2].equals(cAddress));
		assertTrue(caregiverInfo[3].equals(cCity));
		assertTrue(caregiverInfo[4].equals(cPostalCode));
		assertTrue(caregiverInfo[5].equals(cHomePhone));
		assertTrue(caregiverInfo[6].equals(cWorkPhone));
		
		//get the emergency contact information
		String[] ecInfo = pudc.fetchEmergencyContactInfo();
		
		//check that the emergency contact information is as expected 
		assertTrue(ecInfo[0].equals(ecFirstName));
		assertTrue(ecInfo[1].equals(ecLastName));
		assertTrue(ecInfo[2].equals(ecPhone));
	}

}
