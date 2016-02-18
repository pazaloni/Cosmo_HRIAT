import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class testParticipantUpdateForm {

	private DatabaseHelper db;
	private ParticipantUpdateFormController pudc;
	
	@Before
	public void setUp() throws Exception 
	{
		db = new DatabaseHelper();
		db.connect();
		pudc = new ParticipantUpdateFormController(123);
	}

	@After
	public void tearDown() throws Exception 
	{
		db.disconnect();
	}

	@Test
	public void test() 
	{
		
	}

}
