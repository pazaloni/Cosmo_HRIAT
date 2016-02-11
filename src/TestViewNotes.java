import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.scene.control.TableView;
import org.junit.Before;
import org.junit.Test;

/**
 * Purpose:	To test the viewing of notes
 * 
 * @author Steven Palchinski cst209
 *
 */
public class TestViewNotes 
{
	private NoteTableViewController nTVCont;
	
	
	@Before
	public void setUp() throws Exception 
	{
		
		nTVCont = new NoteTableViewController(new TableView());
		nTVCont.initialize();
	}

	/**
	 * Purpose: Test that all notes in the database are reachable from 
	 * the list in the NoteTableViewController (nTVCont), with the
	 * noteIDs from 4-10
	 */
	@Test
	public void test() 
	{
		for(int i = 4; i <=10 ; i++)
		{
			//check that the note exists
			assertTrue(nTVCont.getNoteFromID(i + "") != null);
			
		}
	}


}
