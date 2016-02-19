import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Purpose: Test the notes functionality like setting them as resolved and
 * setting them as viewed
 * 
 * @author CIMP
 * @version 1.0
 */
public class TestNotes
{

    // The note that is being added to the database
    private Note note1;

    private String noteId = "135";
    private String cosmoId = "123";
    private String createdBy = "jcorniere";
    private String description = "This is a description of a note";
    private boolean viewed = false;
    private boolean resolved = false;

    /**
     * Creates a note and add it to the database
     * 
     * @throws Exception
     */
    @Before
    public void setUp()
    {
        note1 = new Note(Integer.parseInt(noteId), Integer.parseInt(cosmoId),
                description, createdBy, new Date(), viewed, resolved);

        note1.addNote();

        // After note is added we need the last ID of the note
        NotePaneHelper nph = new NotePaneHelper();
        int lastNote = nph.getLastNote();
        // We recreate the last note with a noteId that was pulled from the
        // database after the note was added previously
        note1 = new Note(lastNote, Integer.parseInt(cosmoId), description,
                createdBy, new Date(), viewed, resolved);
    }

    /**
     * 
     * Purpose: Set the note as resolved then check the database to see if the
     * note has been resolved
     */
    @Test
    public void testNoteResolved()
    {
        NotePaneHelper nph = new NotePaneHelper();
        nph.setNoteAsResolved(note1);
        Note testNote = nph.queryNote(note1.getNoteID());
        assertTrue(testNote.getResolved().equals("true"));
    }

}