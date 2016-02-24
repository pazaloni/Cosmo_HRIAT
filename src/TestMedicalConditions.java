import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * Purpose: Test the medical conditions class
 *
 * @author CIMP
 * @version 1.0
 */
public class TestMedicalConditions
{
    MedicalCondition test1;
    MedicalCondition test2;

    String condition1;
    String condition2;

    String description1;
    String description2;
    
    String cosmoID = "123";

    @Before
    public void setUp() throws Exception
    {
        condition1 = "ADHD";
        description1 = "Low functioning";

        condition2 = "OCD";
        description2 = "Has to wash hands 5 times after washing the door know";

        test1 = new MedicalCondition(condition1, description1);
        test2 = new MedicalCondition(condition2, description2);

    }
    /**
     * 
     * Purpose: checks that the conditions match
     */
    @Test
    public void testCondition()
    {
        assertTrue(test1.getCondition().get().equals(condition1));
        assertTrue(test2.getCondition().get().equals(condition2));
    }

    /**
     * 
     * Purpose: checks that the descriptions match
     */
    @Test
    public void testDescription()
    {
        assertTrue(test1.getDescripion().get().equals(description1));
        assertTrue(test2.getDescripion().get().equals(description2));
    }
    
    /**
     * Purpose: Test the ability to create a medical condition in the database 
     * 
     */
    @Test
    public void testCreateMedicalCondition()
    {
    	String result = MedicalCondition.createMedicalCondition(test1, cosmoID);
    	assertTrue(result.isEmpty());
    }

    /**
     * Purpose: Test the ability to update a medical condition in the database 
     * 
     */
    @Test
    public void testUpdateMedicalCondition()
    {
    	String result = MedicalCondition.updateMedicalCondition(test2, test1, cosmoID);
    	assertTrue(result.isEmpty());
    }
    
    /**
     * Purpose: Test the ability to delete a medical condition in the database 
     * 
     */
    @Test
    public void testDeleteMedicalCondition()
    {
    	String result = MedicalCondition.deleteCondition(test2, cosmoID);
    	assertTrue(result.equals("Deleted successfully"));
    }
}
