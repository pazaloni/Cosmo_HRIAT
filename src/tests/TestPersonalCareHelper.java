package tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.PersonalCareHelper;

import org.junit.Before;
import org.junit.Test;

public class TestPersonalCareHelper
{
    PersonalCareHelper pcOne = new PersonalCareHelper();
    public final Boolean[] trueFalse = {true, false};
    Boolean[] personSave = new Boolean[11];
    String[] personRetrieve = new String[13];
    String assistance = "Require total full lift"; 
    String cosmoID = "531";
    String lastUpdated;
    @Before
    public void setUp() throws Exception
    {
        
        for(int i = 0 ; i < personSave.length; i++)
        {
            personSave[i] = trueFalse[(int) Math.floor(Math.random()*2)];
        }
        
        Date now = new Date();        
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("dd-MMM-yyyy");
        lastUpdated = formatter.format(now);
    }

    @Test
    public void testRetrievePersonalCareInformation()
    {
        assertTrue(pcOne.savePersonalCareInformation(personSave, assistance, lastUpdated, cosmoID));
    }

    @Test
    public void testSavePersonalCareInformation()
    {
        personRetrieve = pcOne.retrievePersonalCareInformation(cosmoID);
        assertTrue(personRetrieve[0].equals(personSave[0]));
        assertTrue(personRetrieve[1].equals(personSave[1]));
        assertTrue(personRetrieve[2].equals(personSave[2]));
        assertTrue(personRetrieve[3].equals(personSave[3]));
        assertTrue(personRetrieve[4].equals(personSave[4]));
        assertTrue(personRetrieve[5].equals(personSave[5]));
        assertTrue(personRetrieve[6].equals(personSave[6]));
        assertTrue(personRetrieve[7].equals(personSave[7]));
        assertTrue(personRetrieve[8].equals(personSave[8]));
        assertTrue(personRetrieve[9].equals(personSave[9]));
        assertTrue(personRetrieve[10].equals(personSave[10]));
        assertTrue(personRetrieve[11].equals(personSave[11]));
        assertTrue(personRetrieve[12].equals(assistance));
        assertTrue(personRetrieve[13].equals(lastUpdated));
    }

}
