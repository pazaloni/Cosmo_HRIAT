import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class testSeizureDescriptionFormHelper
{
    SeizureDescriptionFormHelper shOne = new SeizureDescriptionFormHelper();

    String[] personOne = new String[8];

    
    @Before
    public void setUp() throws Exception
    {
        personOne = shOne.retieveSeizureInformation("123");
    }

    @After
    public void tearDown() throws Exception
    {
        
    }

    @Test
    public void testretrieveSeizureInformation()
    {
        assertTrue(personOne[0].equals("Slightly Violent"));
        assertTrue(personOne[1].equals("Shakes violently for the first few minutes"
                + ", calms down fairly quickly, and shakes become less "
                + "violent after first few minutes."));
        assertTrue(personOne[2].equals("weekly"));
        assertTrue(personOne[3].equals("5-10 minutes"));
        assertTrue(personOne[4].equals("Headache, as well as chest pains. "
                + "Get to hospital immediately. The rest of this text is for "
                + "filler to check that the scrollbar is working corretly, as "
                + "well as that the text is breaking at the correct times so "
                + "that the text goes to multiple lines instead"));
        assertTrue(personOne[5].equals("keep head elevated"));
        assertTrue(personOne[6].equals("two doses of medication"));
        assertTrue(personOne[7].equals("2016-02-17"));
    }

}
