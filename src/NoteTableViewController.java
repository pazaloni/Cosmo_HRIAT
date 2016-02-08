
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class NoteTableViewController 
{
	//TableView that will hold the 
	protected TableView<Note> noteTable;

	private TableColumn<Note, String> idColumn = 
			new TableColumn<Note, String>("Sort");
	
	public ObservableList<Note> noteIDs = FXCollections.observableArrayList();

	private DatabaseHelper db = new DatabaseHelper();
	
	
	public NoteTableViewController(TableView noteTable)
	{
		this.noteTable = noteTable;
		initialize();
		this.noteTable.setItems(noteIDs);

		
	}

	private void initializeNoteData() 
	{

		
		
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
				
				System.out.println("Pulling info...");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	
		
		
		
	}
	
	public void initialize()
	{
		initializeNoteData();
		
		idColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getIDProperty());
		
		idColumn.setMaxWidth(170);
		idColumn.setMinWidth(170);
		
		idColumn.setCellFactory(new Callback<TableColumn<Note,String>, TableCell<Note,String>>()
		{

			@Override
			public TableCell<Note, String> call(TableColumn<Note, String> n) {
				return new TableCell<Note, String>()
				{
					@Override
					public void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if(!isEmpty())
						{
							Note note = getNoteFromID(item);
							
							if(note.getViewed().equals("true"))
							{
								setStyle("-fx-background-color: lightblue;");
							}
							
							setText(item);
						}
					}
				};
			}
			
		});
		
		idColumn.setResizable(false);
		
		
		noteTable.getColumns().addAll(idColumn);
		
		
		noteTable.setItems(noteIDs);
		
		
		
		
		noteTable.setRowFactory(tv -> {
			System.out.println("Inside row factory..");
			TableRow<Note> row = new TableRow<Note>();
			
			row.setOnMouseClicked(event -> {
				if(! row.isEmpty())
				{
					row.setStyle("-fx-background-color: lightblue;");
					setAsRead(row.getItem());
				}
			});
			
			
			
			return row;
		});
		
		
	}
	
	public String getSelectedPK()
	{
		noteTable.setFocusTraversable(false);
		Note note = noteTable.getSelectionModel().getSelectedItem();
		return note.getNoteID();
	}
	
	public Note getNoteFromID(String id)
	{
		int intID = Integer.parseInt(id);
		int index = noteIDs.indexOf(new Note(intID, 0, null, null, null, false, false));
		
		return noteIDs.get(index);
	}
	
	public void refreshTable()
	{
		this.noteIDs.clear();
		this.noteTable.getColumns().clear();
		this.initialize();
	}
	
	public void setAsRead(Note note)
	{
		String vals[][] = new String[2][2];
		vals[0][0] = "noteID";
		vals[1][0] = "viewed";
		vals[0][1] = note.getNoteID();
		vals[1][1] = "true";
		
		
		db.update(vals, "Notes", note.getNoteID());
		
		this.noteIDs.clear();
		this.noteTable.getColumns().clear();
		initialize();
		
	}
	


}