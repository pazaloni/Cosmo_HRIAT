import java.sql.Date;
import java.sql.Time;


public class Note 
{
	private int noteID;
	private String description;
	private String creatorID;
	private String participantID;
	private long dateCreated;
	private long timeCreated;
	private boolean viewed;
	private boolean resolved;
	public String error;
	
	public Note(Participant participant, String description, String creator)
	{
		this.description = description;
		this.creatorID = creator;		
		this.participantID = participant.getCosmoID();
		dateCreated = System.currentTimeMillis();
		//stuff for formatting date
		timeCreated = System.currentTimeMillis() - dateCreated;
		//stuff for formatting time
		viewed = false;
		resolved = false;
	}
	
	public boolean addNote()
	{
		//Database stuff
		return false;		
	}
		
}
