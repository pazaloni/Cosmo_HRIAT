package tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.PersonalCareHelper;

import org.junit.Before;
import org.junit.Test;

/**
 * A test for the PersonalCareHelper class.
 * 
 * @author cst207
 *
 */
public class TestPersonalCareHelper
{
    PersonalCareHelper pcOne = new PersonalCareHelper();
    public final Boolean[] trueFalse = { true, false };
    Boolean[] personSave = new Boolean[11];
    String[] personRetrieve = new String[13];
    String assistance = "Require total full lift";
    String assistance2 = "";
    String cosmoID = "531";
    String lastUpdated;

    @Before
    public void setUp() throws Exception
    {

        for ( int i = 0; i < personSave.length; i++ )
        {
            personSave[i] = trueFalse[(int) Math.floor(Math.random() * 2)];
        }

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("dd-MMM-yyyy");
        lastUpdated = formatter.format(now);
    }

    /**
     * Test that the correct information is retrieved
     */
    @Test
    public void testRetrievePersonalCareInformation()
    {
        assertTrue(pcOne.savePersonalCareInformation(personSave, assistance,
                lastUpdated, cosmoID));
    }

    /**
     * Test that information saved properly
     */
    @Test
    public void testSavePersonalCareInformation()
    {
        pcOne.savePersonalCareInformation(personSave, assistance, lastUpdated,
                cosmoID);
        personRetrieve = pcOne.retrievePersonalCareInformation(cosmoID);
        assertTrue(Boolean.parseBoolean(personRetrieve[0]) == (personSave[0]));
        assertTrue(Boolean.parseBoolean(personRetrieve[1]) == (personSave[1]));
        assertTrue(Boolean.parseBoolean(personRetrieve[2]) == (personSave[2]));
        assertTrue(Boolean.parseBoolean(personRetrieve[3]) == (personSave[3]));
        assertTrue(Boolean.parseBoolean(personRetrieve[4]) == (personSave[4]));
        assertTrue(Boolean.parseBoolean(personRetrieve[5]) == (personSave[5]));
        assertTrue(Boolean.parseBoolean(personRetrieve[6]) == (personSave[6]));
        assertTrue(Boolean.parseBoolean(personRetrieve[7]) == (personSave[7]));
        assertTrue(Boolean.parseBoolean(personRetrieve[8]) == (personSave[8]));
        assertTrue(Boolean.parseBoolean(personRetrieve[9]) == (personSave[9]));
        assertTrue(Boolean.parseBoolean(personRetrieve[10]) == (personSave[10]));
        assertTrue(personRetrieve[11].equals(assistance));
    }

    /**
     * Test that information saved properly if assistance is left blank
     */
    @Test
    public void testSavePersonalCareInformationNoAssistance()
    {
        pcOne.savePersonalCareInformation(personSave, assistance2, lastUpdated,
                cosmoID);
        personRetrieve = pcOne.retrievePersonalCareInformation(cosmoID);
        assertTrue(Boolean.parseBoolean(personRetrieve[0]) == (personSave[0]));
        assertTrue(Boolean.parseBoolean(personRetrieve[1]) == (personSave[1]));
        assertTrue(Boolean.parseBoolean(personRetrieve[2]) == (personSave[2]));
        assertTrue(Boolean.parseBoolean(personRetrieve[3]) == (personSave[3]));
        assertTrue(Boolean.parseBoolean(personRetrieve[4]) == (personSave[4]));
        assertTrue(Boolean.parseBoolean(personRetrieve[5]) == (personSave[5]));
        assertTrue(Boolean.parseBoolean(personRetrieve[6]) == (personSave[6]));
        assertTrue(Boolean.parseBoolean(personRetrieve[7]) == (personSave[7]));
        assertTrue(Boolean.parseBoolean(personRetrieve[8]) == (personSave[8]));
        assertTrue(Boolean.parseBoolean(personRetrieve[9]) == (personSave[9]));
        assertTrue(Boolean.parseBoolean(personRetrieve[10]) == (personSave[10]));
        assertTrue(personRetrieve[11].equals(assistance2));
    }

}
