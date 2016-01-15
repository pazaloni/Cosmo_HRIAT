import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;


public class Note 
{
	private int noteID;
	private String description;
	private String creatorID;
	private String participantID;
	private Date dateCreated;
	private boolean viewed;
	private boolean resolved;
	
	
	/**
	 * Purpose:	The constructor for the note
	 * 
	 * @param participant	The participant that the note will be attached to
	 * @param description	The note that the medical staff will leave
	 * @param creator		The username of the medical staff
	 * 
	 * @author  Niklaas Neijmeijer cst207
     *          Steven Palchinski cst209
	 */
	public Note(Participant participant, String description, String creator)
	{
		this.description = description;
		this.creatorID = creator;		
		this.participantID = participant.getCosmoID();
		dateCreated = new Date();
		//stuff for formatting date
		viewed = false;
		resolved = false;
	}
	
	/**
	 * Purpose:	This will add a note to the database, taking the cosmoID from 
	 * 			the participant, the creator from the currently logged in staff 
	 * 			member, the date and time the note was submitted, the note 
	 * 			description as entered by the medical staff.  If there are any 
	 * 			errors, a string result describing the error will be returned 
	 * 
	 * @return	A string that will submit a error message explaining what error 
	 * 			has occurred.
	 * 
	 * @author  Niklaas Neijmeijer cst207
     *          Steven Palchinski cst209
	 */
	public String addNote()
	{
		String result = "";
		//Database stuff
		DatabaseHelper db = new DatabaseHelper();
        db.connect();
        String values[][] = new String[4][2];
        values[0][0] = "cosmoID";
        values[1][0] = "createdBy";
        values[2][0] = "submitted";
        values[3][0] = "description";
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss"); 
        values[0][1] = this.participantID;
        values[1][1] = this.creatorID;
        values[2][1] = df.format(dateCreated);
        values[3][1] = description;
        if(!description.equals(""))
        {
        	boolean successful = db.insert(values, "Notes");
        	if(!successful)
            {
                result = "The insertion was not successful";
            }
        }
        else
        {
        	result = "Cannot create a note with a blank description.";
        }
		return result;		
	}
		
}
