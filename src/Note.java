import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note 
{
	// The unique ID of the note
	private int noteID;
	// the description attached to the note
	private String description;
	// The ID of the user who created the note
	private String creatorID;
	// The ID of the participant the note was attached to
	private int participantID;
	// The date/time that the note was created at
	private Date dateCreated;
	// Whether or not the note has been looked at by a medical administrator
	private boolean viewed;
	// Whether or not a medical administrator has marked the note as resolved
	private boolean resolved;

	/**
	 * Purpose: The constructor for the note
	 * 
	 * @param noteID
	 *            The unique id of the note
	 * @param participant
	 *            The participant that the note will be attached to
	 * @param description
	 *            The note that the medical staff will leave
	 * @param creator
	 *            The username of the medical staff
	 * @param dateCreated
	 *            The date that the note was created
	 * @param viewed
	 *            If the note has been seen by the medical administrator
	 * @param resolved
	 *            If the note has been marked as resolved by the medical
	 *            administrator
	 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
	 */
	public Note(int noteID, int participantID, String description,
			String creator, Date dateCreated, boolean viewed, boolean resolved) 
	{
		this.noteID = noteID;
		this.description = description;
		this.creatorID = creator;
		this.participantID = participantID;
		this.dateCreated = dateCreated;
		this.viewed = viewed;
		this.resolved = resolved;
	}

	/**
	 * Purpose: The overloaded constructor for note
	 * 
	 * @param participant
	 *            The participant that the note will be attached to
	 * @param description
	 *            The note that the medical staff will leave
	 * @param creator
	 *            The username of the medical staff
	 * @param dateCreated
	 *            The date that the note was created
	 * @param viewed
	 *            If the note has been seen by the medical administrator
	 * @param resolved
	 *            If the note has been marked as resolved by the medical
	 *            administrator
	 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
	 */
	public Note(int participant, String description, String creator,
			Date dateCreated, boolean viewed, boolean resolved) 
	{
		this.description = description;
		this.creatorID = creator;
		this.participantID = participant;
		this.dateCreated = dateCreated;
		this.viewed = viewed;
		this.resolved = resolved;
	}

	/**
	 * Purpose: This will add a note to the database, taking the cosmoID from
	 * the participant, the creator from the currently logged in staff member,
	 * the date and time the note was submitted, the note description as entered
	 * by the medical staff. If there are any errors, a string result describing
	 * the error will be returned
	 * 
	 * @return A string that will submit a error message explaining what error
	 *         has occurred.
	 * 
	 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
	 */
	public String addNote() 
	{
		// The string that stores the result of the insert statement
		String result = "";
		DatabaseHelper db = new DatabaseHelper();
		db.connect();
		// The array that contains the values to be inserted into the
		String values[][] = new String[4][2];
		values[0][0] = "cosmoID";
		values[1][0] = "createdBy";
		values[2][0] = "submitted";
		values[3][0] = "description";
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		values[0][1] = this.participantID + "";
		values[1][1] = this.creatorID;
		values[2][1] = df.format(dateCreated);
		values[3][1] = description;
		//An if statement that confirms if the description has been filled out.
		if (!description.equals("")) 
		{
			boolean successful = db.insert(values, "Notes");
			//A generic error if the cause of the failure is unknown
			if (!successful) {
				result = "The insertion was not successful";
			}
		} 
		//Else if the description is not filled out. 	
		else 
		{
			//A blank description error is returned
			result = "Cannot create a note with a blank description.";
		}
		return result;
	}

	public StringProperty getIDProperty() 
	{
		StringProperty p = new SimpleStringProperty();
		p.set(noteID + "");
		return p;
	}

	public String getNoteID() 
	{
		return noteID + "";
	}

	
	public String toString()
	{
		return getNoteID();
		
	}
	
	public StringProperty getViewedProperty()
	{
		StringProperty p = new SimpleStringProperty();
		p.set(viewed + "");
		return p;
	}
	
	public String getViewed()
	{
		return viewed + "";
	}

	
	public void setAsViewed()
	{
			
		viewed = true;
	}

}
