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
        boolean successful = db.insert(values, "Notes");
        if(!successful)
        {
            result = "The insertion was not successful";
        }
		return result;		
	}
		
}
