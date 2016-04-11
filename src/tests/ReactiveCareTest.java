package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.ReactiveCare;

/**
 * Tests the ReactiveCare data holder class
 * and it's getter methods.
 *
 * @author Breanna Wilson, Jon Froese
 *
 */

public class ReactiveCareTest {
	
	private int year;
	private int participants;
	private int staff;
	private int total;
	private ReactiveCare rc;
	
	@Before
	public void setUp() throws Exception 
	{
		year = 2000;
		participants = 196;
		staff = 38;
		total = participants + staff;
		rc = new ReactiveCare(year, participants, staff);
	}

	/**
	 * Tests the getYear() method returns the
	 * expected year value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetYear()
	{
		assertTrue(rc.getYear() == year);
	}

	/**
	 * Tests the getYearProperty() returns
	 * a StringProperty containing the expected
	 * String year value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetYearProperty()
	{
		assertTrue(rc.getYearProperty().getValue().equals("" + year));
	}

	/**
	 * Tests the getParticipants() method returns
	 * the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetParticipants() 
	{
		assertTrue(rc.getParticipants() == participants);
	}

	/**
	 * Tests the getParticipantsProperty() method
	 * returns a StringProperty containing the expected
	 * value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetParticipantsProperty() 
	{
		assertTrue(rc.getParticipantsProperty().getValue().equals("" + participants));
	}

	/**
	 * Tests the getStaff() method returns
	 * the expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetStaff() 
	{
		assertTrue(rc.getStaff() == staff);
	}

	/**
	 * Tests get getStaffProperty() method returns
	 * a StringProperty with the expected String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetStaffProperty() 
	{
		assertTrue(rc.getStaffProperty().getValue().equals("" + staff));
	}

	/**
	 * Tests the getTotal() method returns the
	 * expected value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotal() 
	{
		assertTrue(rc.getTotal() == total);
	}

	/**
	 * Tests get getTotalProperty() returns
	 * a StringProperty with the expected 
	 * String value.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@Test
	public void testGetTotalProperty()
	{
		assertTrue(rc.getTotalProperty().getValue().equals("" + total));
	}

}
