import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Purpose: Class to test the Participant object.
 *
 * @author Team CIMP
 * @version 1.0
 */
public class TestParticipant
{
    Participant test1;
    Participant test2;
    Participant test3;
    // MedicalAdministrator testHelper;

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
    
   

    /**
     * 
     * Purpose: Set up the data for the participant to be tested on
     * 
     * @throws Exception
     */
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

        test1 = new Participant(cosmoIDOne, participantNameOne,
                participantAddressOne, emergencyContactNameOne,
                emergencyContactPhoneOne, informationLastUpdatedOne);
        test2 = new Participant(cosmoIDTwo, participantNameTwo,
                participantAddressTwo, emergencyContactNameTwo,
                emergencyContactPhoneTwo, informationLastUpdatedTwo);

    }

    /**
     * 
     * Purpose: Checks if the cosmoID was set.
     */
    @Test
    public void testGetCosmoID()
    {
        assertTrue(test1.getCosmoID().equals("777"));
    }

    /**
     * 
     * Purpose: Checks if the name was set
     */
    @Test
    public void testGetParticipantName()
    {
        assertTrue(test1.getParticipantName().equals("Jeff Jones"));
    }

    /**
     * 
     * Purpose: Checks if the informationLastUpdated date is set.
     */
    @Test
    public void testGetInformationLastUpdated()
    {
        assertTrue(test1.getInformationLastUpdated().equals("07-Jul-2007"));
    }

    /**
     * 
     * Purpose: Checks if the participant address is set.
     */
    @Test
    public void testGetParticipantAddress()
    {
        assertTrue(test1.getParticipantAddress().equals("777 Heaven Dr."));
    }

    /**
     * 
     * Purpose: Checks if the contact name is set.
     */
    @Test
    public void testGetContactName()
    {
        assertTrue(test1.getEmergencyContactName().equals("St. George"));
    }

    /**
     * 
     * Purpose: Checks if the emergency contact phone number is set
     */
    @Test
    public void testGetContactPhone()
    {
        assertTrue(test1.getEmergencyContactPhone().equals("66987777777"));
    }

    /**
     * 
     * Purpose: Checks if the cosmoID was set
     */
    @Test
    public void testGetCosmoIDProperty()
    {
        assertTrue(test2.getCosmoIDProperty().getValue().equals("666"));
    }

    /**
     * 
     * Purpose: Checks if the participant name property is set
     */
    @Test
    public void testGetParticipantNameProperty()
    {
        assertTrue(test2.getParticipantNameProperty().getValue()
                .equals("Chad Stones"));
    }

    /**
     * 
     * Purpose: Checks if the information updated property is set
     */
    @Test
    public void testGetInformationLastUpdatedProperty()
    {
        assertTrue(test2.getUpdatedProperty().getValue().equals("20-Dec-2012"));
    }

    /**
     * 
     * Purpose: Checks if the address property is set
     */
    @Test
    public void testGetParticipentAddressProperty()
    {

        assertTrue(test2.getAddressProperty().getValue()
                .equals("666 Downstairs Alley"));
    }

    /**
     * 
     * Purpose: Checks if the contact name property is set
     */
    @Test
    public void testGetContactNameProperty()
    {
        assertTrue(test2.getEmergencyContactProperty().getValue()
                .equals("Louis Cipher"));
    }

    /**
     * 
     * Purpose: Checks if the contact phone property is set
     */
    @Test
    public void testGetContactPhoneProperty()
    {
        assertTrue(test2.getEmergencyContactPhoneProperty().getValue()
                .equals("666911666"));
    }
    
    
    /**
     * 
     * Purpose: tear down
     * 
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
        test1 = null;
        test2 = null;
    }

}
