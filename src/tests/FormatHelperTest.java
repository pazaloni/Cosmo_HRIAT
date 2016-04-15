package tests;

import static org.junit.Assert.*;
import helpers.FormatHelper;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the FormatHelper Object
 * 
 * @author cst209
 *
 */
public class FormatHelperTest
{
    FormatHelper testHelper;
    String test1;
    String test2;
    String test3;
    String test4;
    String test5;

    @Before
    public void setUp() throws Exception
    {
        testHelper = new FormatHelper();
        test1 = "1234567890";
        test2 = "123456789";
        test3 = "(123)456-7890";
        test4 = "12f3f4f5fh6k7k8k9l0";
        test5 = "123456789011";
    }

    /**
     * Method for testing phone number format
     */
    @Test
    public void testFormatPhoneNum()
    {
        assertTrue(testHelper.formatPhoneNum(test1).equals("(123) 456-7890"));
        assertFalse(testHelper.formatPhoneNum(test2).equals("(123) 456-7890"));
        assertTrue(testHelper.formatPhoneNum(test3).equals("(123) 456-7890"));
        assertTrue(testHelper.formatPhoneNum(test4).equals("(123) 456-7890"));
        assertFalse(testHelper.formatPhoneNum(test5).equals("(123) 456-7890"));
    }

}
