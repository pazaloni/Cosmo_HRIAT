package tests;
import static org.junit.Assert.*;
import helpers.SeizureDescriptionFormHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class testSeizureDescriptionFormHelper
{
    SeizureDescriptionFormHelper shOne = new SeizureDescriptionFormHelper();

    String[] personSave = new String[8];
    String[] personRetrieve = new String[8];
    String cosmoID = "454593";

    
    @Before
    public void setUp() throws Exception
    {
    	personSave[0] = "Slightly Violent";
    	personSave[1] = "Shakes violently for the first few minutes"
                + ", calms down fairly quickly, and shakes become less "
                + "violent after first few minutes.";
        personSave[2] = "weekly";
        personSave[3] = "5-10 minutes";
        personSave[4] = "Headache, as well as chest pains. "
                + "Get to hospital immediately. The rest of this text is for "
                + "filler to check that the scrollbar is working corretly, as "
                + "well as that the text is breaking at the correct times so "
                + "that the text goes to multiple lines instead";
        personSave[5] = "keep head elevated";
        personSave[6] = "two doses of medication";
        personSave[7] = "2016-02-17";
        
    }

    @After
    public void tearDown() throws Exception
    {
        
    }
    @Test 
    public void testSaveSeizureInformation()
    {
    	assertTrue(shOne.saveSeizureInformation(personSave, cosmoID).length() == 0);
    }
    @Test
    public void testretrieveSeizureInformation()
    {
    	personRetrieve = shOne.retieveSeizureInformation(cosmoID);
        assertTrue(personRetrieve[0].equals(personSave[0]));
        assertTrue(personRetrieve[1].equals(personSave[1]));
        assertTrue(personRetrieve[2].equals(personSave[2]));
        assertTrue(personRetrieve[3].equals(personSave[3]));
        assertTrue(personRetrieve[4].equals(personSave[4]));
        assertTrue(personRetrieve[5].equals(personSave[5]));
        assertTrue(personRetrieve[6].equals(personSave[6]));
        assertTrue(personRetrieve[7].equals(personSave[7]));
    }

}
