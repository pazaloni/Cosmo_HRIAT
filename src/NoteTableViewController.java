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
		
		ResultSet rs = db.select("noteID", "Notes", "not resolved", "noteID");
		
		String noteID;
		int cosmoID;
		String createdBy;
		String submitted;
		String description;
		Boolean viwed;
		Boolean resolved;
		try
		{
			while(rs.next())
			{
				noteID = rs.getString(1);
				//Boolean read = false;
				Note note;
				
//				if (read == false)
//				{
//					//TODO make the color of the unread notes "insert color here"
//					setStyle("-fx-background-color:mediumblue"); 
//				}
				
				//note = new Note(noteID, cosmoID, description, createdBy, submitted, viwed, resolved);
				//noteIDs.add(note);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void initialize()
	{
		idColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getIDProperty());
		idColumn.setResizable(false);
		idColumn.setCellFactory(new Callback<TableColumn<Note, String>, TableCell<Note, String>>()
				{
					@Override
					public TableCell<Note, String> call(TableColumn<Note, String> noteStringTableColumn)
					
					
					return new TableCell<Note, String>()
						{
							@Override
							protected void updateItem(String noteID, boolean read)
							{
								super.updateItem(noteID, read);
								if(!read)
								{
									currentRow.setStyle("-fx-background-color:mediumblue");
								}
							}
						}
				});
		
		noteTable.getColumns().addAll(idColumn);
		
		noteTable.setItems(noteIDs);
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
	
	

	private TableRow<Note> getTableRow() {
		// TODO Auto-generated method stub
		return null;
	}
}