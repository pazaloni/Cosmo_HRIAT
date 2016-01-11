import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestParticipant
{
    Participant test1;
    Participant test2;
    Participant test3;
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
    
    private String descriptionOne = "This person has Autism";
    private String descriptionTwo = "This person has Cancer";
    
    @Before
    public void setUp() throws Exception
    {
    
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
    public void testGetCosmoID()
    {
    	assertTrue(test1.getCosmoID().equals("777"));
    }
    @Test
    public void testGetParticipentName()
    {
    	assertTrue(test1.getParticipantName().equals("Jeff Jones"));
    }
    @Test
    public void testGetInformationLastUpdated()
    {
    	assertTrue(test1.getInformationLastUpdated().equals("07-Jul-2007"));
    }
    @Test
    public void testGetParticipentAddress()
    {
    	assertTrue(test1.getParticipantAddress().equals("777 Heaven Dr."));
    }
    @Test
    public void testGetContactName()
    {
    	assertTrue(test1.getEmergencyContactName().equals("St. George"));
    }
    @Test
    public void testGetContactPhone()
    {
    	assertTrue(test1.getEmergencyContactPhone().equals("66987777777"));
    }
    
    public void testGetCosmoIDProperty()
    {
    	assertTrue(test2.getCosmoIDProperty().getValue().equals("666"));
    }
    @Test
    public void testGetParticipentNameProperty()
    {
    	assertTrue(test2.getParticipantNameProperty().getValue().equals("Chad Stones"));
    }
    @Test
    public void testGetInformationLastUpdatedProperty()
    {
    	assertTrue(test2.getUpdatedProperty().getValue().equals("20-Dec-2012"));
    }
    @Test
    public void testGetParticipentAddressProperty()
    {
    	
    	assertTrue(test2.getAddressProperty().getValue().equals("666 Downstairs Alley"));
    }
    @Test
    public void testGetContactNameProperty()
    {
    	assertTrue(test2.getEmergencyContactProperty().getValue().equals("Louis Cipher"));
    }
    @Test
    public void testGetContactPhoneProperty()
    {
    	assertTrue(test2.getEmergencyContactPhoneProperty().getValue().equals("666911666"));
    }
    @After
    public void tearDown() throws Exception
    {
    	test1 = null;
    	test2 = null;
    }

}

