import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class NoteTableViewController 
{
	
	protected TableView<Note> noteTable = new TableView<Note>();

	private TableColumn<Note, String> idColumn = new TableColumn<Note, String>();
	
	public ObservableList<Note> noteIDs = FXCollections.observableArrayList();
	
	public NoteTableViewController()
	{
		initializeNoteData();
		noteTable.setItems(noteIDs);
	}

	private void initializeNoteData() 
	{

		DatabaseHelper db = new DatabaseHelper();
		
		ObservableList<String> row = FXCollections.observableArrayList();
		
		ResultSet rs = db.select("*", "Notes", "not resolved", "noteID");
		
		int noteID;
		int participant;
		String createdBy;
		Date submitted;
		String description;
		Boolean viewed;
		Boolean resolved;
		try
		{
			while(rs.next())
			{
				noteID = rs.getInt(1);
				participant = rs.getInt(2);
				createdBy = rs.getString(3);
				submitted = rs.getDate(4);
				description = rs.getString(5);
				viewed = rs.getBoolean(6);
				resolved = rs.getBoolean(7);
				
				Note note;
				
				
				note = new Note(noteID, participant, description, createdBy, submitted, viewed, resolved);
				
				noteIDs.add(note);
				

			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void initialize()
	{
		
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		idColumn.setResizable(false);
		
		
		
		noteTable.getColumns().add(idColumn);
		
		noteTable.setItems(noteIDs);
		
		for (Note note : noteIDs) 
		{
			if(note.getViewed().equals("false"))
			{
				
			}
		}
	}
	
	public String getSelectedPK()
	{
		noteTable.setFocusTraversable(false);
		Note note = noteTable.getSelectionModel().getSelectedItem();
		return note.getNoteID();
	}
	
	public void refreshTable()
	{
		this.noteIDs.clear();
		this.initializeNoteData();
		this.noteTable.getColumns().clear();
		this.initialize();
	}
	
	

	private TableRow<Note> getTableRow() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}