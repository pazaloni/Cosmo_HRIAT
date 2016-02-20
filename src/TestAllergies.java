import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Purpose: Test the Allergies object 
 *
 * @author TEAM CIMP!
 * @version 1.0
 */
public class TestAllergies
{

    Allergies test1;
    Allergies test2;

    String allergicTo1;
    String allergyType1;
    String allergyDescription1;

    String allergicTo2;
    String allergyType2;
    String allergyDescription2;

    @Before
    public void setUp() throws Exception
    {
        allergyType1 = "Severe";
        allergyType2 = "Severe";

        allergicTo1 = "Peanuts";
        allergicTo2 = "Milk";

        allergyDescription1 = "When the subject sees nuts they go into an acute strees reaction";
        allergyDescription2 = "Bad gass";
        
        test1 = new Allergies(allergicTo1, allergyType1, allergyDescription1);
        test2 = new Allergies(allergicTo2, allergyType2, allergyDescription2);
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
    /**
     * 
     * Purpose: checks if the allergy type is the same 
     */
    @Test
    public void testGetAllergyType()
    {
        assertTrue(test1.getAllergyType().get().equals(allergyType1));
        assertTrue(test2.getAllergyType().get().equals(allergyType2));
    }
    
    /**
     * 
     * Purpose: checks if the allergicto is the same 
     */    
    @Test
    public void testGetAllergicTo()
    {
        assertTrue(test1.getAllergicTo().get().equals(allergicTo1));
        assertTrue(test2.getAllergicTo().get().equals(allergicTo2));
    }
    /**
     * 
     * Purpose: checks if the allergy descriptions are the same 
     */
    @Test
    public void testAllergyDescription()
    {
        assertTrue(test1.getDescription().get().equals(allergyDescription1));
        assertTrue(test2.getDescription().get().equals(allergyDescription2));
    }
    

}