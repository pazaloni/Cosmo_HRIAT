import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPreviewPaneHelper
{

    DatabaseHelper db = new DatabaseHelper();

    MedicalAdministrator testHelper;

    private String cosmoID;
    private String participantFirstName;
    private String participantLastName;
    private LocalDate participantBirthdate;
    private String physicianFName;
    private String physicianLName;
    private String healthNumber;
    private String phoneOne;
    private String addressOne;

    @Before
    public void setUp() throws Exception
    {
        String date1 = "07-07-2007";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        cosmoID = "111";
        participantFirstName = "bob";
        participantLastName = "smith";
        participantBirthdate = LocalDate.parse(date1, formatter);
        physicianFName = "John";
        physicianLName = "Doe";
        healthNumber = "111111111";
        phoneOne = "1111111111";
        addressOne = "123 Fake Street";

        MedicalAdministrator.createParticipant(cosmoID, participantFirstName,
                participantFirstName, participantBirthdate, physicianFName,
                physicianLName, healthNumber, phoneOne, addressOne);
    }

    @After
    public void tearDown() throws Exception
    {

    }

    /**
     * 
     * Purpose: Test that the first name of the participant selected is the same
     * as the one that is being dispalyed
     * 
     */
    @Test
    public void testFirstName()
    {
        PreviewPaneHelper helper = new PreviewPaneHelper();

        String firstName = helper.queryParticipant(cosmoID)[1];

        assertTrue(firstName.equals(participantFirstName));

    }

    /**
     * 
     * Purpose: Test that the last name of the participant selected is the same
     * as the one that is being dispalyed
     * 
     */
    @Test
    public void testLastName()
    {
        PreviewPaneHelper helper = new PreviewPaneHelper();

        String lastName = helper.queryParticipant(cosmoID)[2];
        System.out.println(lastName + " -------------------------------------------");
        assertTrue(lastName.equals(participantLastName));

    }

    /**
     * Purpose: Test that the phyisician name of the participant selected is the
     * same as the one that is being dispalyed
     */
    @Test
    public void testPhyisicanName()
    {
        PreviewPaneHelper helper = new PreviewPaneHelper();

        String name = helper.queryParticipant(cosmoID)[3];
        System.out.println(name + "-------------------------------------------");
        assertTrue(name.equals(physicianFName + " " + physicianLName));

    }

    /**
     * Purpose: Test that the cosmoID of the participant selected is the
     * same as the one that is being dispalyed
     */
    @Test
    public void testCosmoID()
    {
        PreviewPaneHelper helper = new PreviewPaneHelper();

        String id = helper.queryParticipant(cosmoID)[0];
        System.out.println(id + "-------------------------------------------");
        assertTrue(id.equals(cosmoID));
    }

}