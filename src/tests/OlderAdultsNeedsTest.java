package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.OlderAdultsNeeds;

/**
 * Tests the OlderAdultsNeeds data holder class
 * and it's getter methods.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class OlderAdultsNeedsTest 
{

	private String age;
	private int longTermCare;
	private int elmwood;
	private int lutherCare;
	private int other;
	private int totalAsOf;
	private int totalForLastYear;
	private OlderAdultsNeeds oan;
	@Before
	public void setUp() throws Exception 
	{
		age = ">65";
		longTermCare = 3;
		elmwood = 21;
		lutherCare = 1;
		other = 21;
		totalAsOf = 49;
		totalForLastYear = 46;
		oan = new OlderAdultsNeeds(age, longTermCare, 
				elmwood, lutherCare, other, totalAsOf, 
				totalForLastYear);
	}

	/**
	 * Test the getAge() method returns the expected
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetAge() 
	{
		assertTrue(age.equals(oan.getAge()));
	}

	/**
	 * Test the getAgeProperty() returns
	 * a StringProperty with the expected
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetAgeProperty()
	{
		assertTrue(oan.getAgeProperty().getValue().equals("" + age));
	}

	/**
	 * Tests the getLongTermCare() method returns
	 * the same value as expected.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetLongTermCare() 
	{
		assertTrue(longTermCare == oan.getLongTermCare());
	}

	/**
	 * Tests the getLongTermCareProperty() method
	 * returns the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetLongTermCareProperty()
	{
		assertTrue(oan.getLongTermCareProperty().getValue().equals("" + longTermCare));
	}

	/**
	 * Test the getElmwood() method returns the expected
	 * value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetElmwood()
	{
		assertTrue(elmwood == oan.getElmwood());
	}

	/**
	 * Tests the getElmwoodProperty() method
	 * returns the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetElmwoodProperty() 
	{
		 assertTrue(oan.getElmwoodProperty().getValue().equals("" + elmwood));
	}

	/**
	 * Tests the getLutherCare() method 
	 * returns the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetLutherCare() 
	{
		assertTrue(lutherCare == oan.getLutherCare());
	}

	/**
	 * Tests the getLutherCareProperty() method
	 * returns a StringProperty with the expected
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetLutherCareProperty()
	{
		assertTrue(oan.getLutherCareProperty().getValue().equals("" + lutherCare));
	}

	/**
	 * Tests the getOther() method returns
	 * the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetOther() 
	{
		assertTrue(oan.getOther() == other);
	}

	/**
	 * Tests the getOtherProperty() returns a
	 * StringProperty containing the expected
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetOtherProperty() 
	{
		assertTrue(oan.getOtherProperty().getValue().equals("" + other));
	}

	/**
	 * Tests the getTotalAsOf() method returns
	 * the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalAsOf() 
	{
		assertTrue(oan.getTotalAsOf() == totalAsOf);
	}

	/**
	 * Tests the getTotalAsOfProperty() method
	 * returns a StringProperty with the expected
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalAsOfProperty() 
	{
		assertTrue(oan.getTotalAsOfProperty().getValue().equals("" + totalAsOf));
	}

	/**
	 * Tests the getTotalForLastYear() method
	 * returns the expected value
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalForLastYear() 
	{
		assertTrue(oan.getTotalForLastYear() == totalForLastYear);
	}

	/**
	 * Tests the getTotalForLastYearProperty()
	 * method returns a StringProperty with the expected
	 * string value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalForLastYearProperty() 
	{
		assertTrue(oan.getTotalForLastYearProperty().getValue().equals("" + totalForLastYear));
	}

}
