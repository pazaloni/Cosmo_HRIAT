package tests;

import static org.junit.Assert.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.PersonalCare;

/**
 * Tests the PersonalCare data holder object, and it's getter
 * methods.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class PersonalCareTest 
{
	String supports;
	String totalAsOf;
	String totalForLastYear;
	PersonalCare pc;
	@Before
	public void setUp() throws Exception 
	{
		supports = "Daily insulin injections";
		totalAsOf = "1";
		totalForLastYear = "2";
		pc = new PersonalCare("",supports, totalAsOf, totalForLastYear);
	}
	
	/**
	 * Test the getSupports() method returns the
	 * expected String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetSupports() 
	{
		assertTrue(pc.getSupports().equals(supports));
	}

	/**
	 * Test the getSupportsProperty() method returns
	 * a StringProperty with the expected String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetSupportsProperty() 
	{
		assertTrue(supports.equals(pc.getSupportsProperty().getValue()));
	}

	/**
	 * Test the getTotalAsOf() method returns the
	 * expected String.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalAsOf() 
	{
		assertTrue(pc.getTotalAsOf().equals(totalAsOf));
	}

	
	/**
	 * Test the getTotalAsOfStringProperty() returns
	 * a StringProperty containing the expected String
	 * value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalAsOfProperty() 
	{
		assertTrue(totalAsOf.equals(pc.getTotalAsOfProperty().getValue()));
	}

	/**
	 * Test the getTotalForLastYear() method returns
	 * the expected String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalForLastYear() 
	{
		assertTrue(pc.getTotalForLastYear().equals(totalForLastYear));
	}

	/**
	 * Test the getTotalForLastYearProperty() returns
	 * a StringProperty with the expected String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalForLastYearProperty() 
	{
		assertTrue(totalForLastYear.equals(pc.getTotalForLastYearProperty().getValue()));
	}

}
