
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * 
 * @author Breanna Wilson cst215 Steven Palchinski cst209
 *
 */
public class NoteTableViewController 
{
    //TableView that will hold the note information
    protected TableView<Note> noteTable;

    //table column that will hold all the note IDs
    private TableColumn<Note, String> idColumn = 
            new TableColumn<Note, String>("Notes");
    
    private TableColumn<Note, String> statusColumn = 
            new TableColumn<Note, String>("New");
    
    //that data for each note
    public ObservableList<Note> noteIDs = FXCollections.observableArrayList();

    //Instance of the database helper
    private DatabaseHelper db = new DatabaseHelper();
    
    /**
     * Purpose: The constructor for the NoteTableViewController
     * 
     * @param noteTable:    The tableView that will be used
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public NoteTableViewController(TableView noteTable)
    {
        //Takes passed in TableView
        this.noteTable = noteTable;
        //runs the initialize class
        initialize();
        //sets the note items to the TableView
        this.noteTable.setItems(noteIDs);
    }

    /**
     * Purpose: Fetches the note data from the database and then adds the 
     *          fetched data to the observable list
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    private void initializeNoteData() 
    {
        //ResultSet that will get back all of the notes that are not resolved 
        ResultSet rs = db.select("*", "Notes", "(((Notes.resolved)=False))", "noteID");

        //Integer that will store the note id
        int noteID;
        //Integer that will store the participant ID/CosmoID
        int participant;
        //Sting that will store the creators username
        String createdBy;
        //date that will store the timestamp of the notes creation
        Date submitted;
        //String that will hold the text of the note
        String description;
        //Boolean that will hold the viewed status
        Boolean viewed;
        //Boolean that will hold the resolved status
        Boolean resolved;
        
        try
        {
            //while there is still a result
            while(rs.next())
            {
                //set the note id from the results
                noteID = rs.getInt(1);
                //set the participant from the results
                participant = rs.getInt(2);
                //set the username from the results
                createdBy = rs.getString(3);
                //set the timestamp from the results
                submitted = rs.getDate(4);
                //set the note text from the results
                description = rs.getString(5);
                //set the viewed status from the results
                viewed = rs.getBoolean(6);
                //set the resolved status from the results
                resolved = rs.getBoolean(7);
                
                //create the note
                Note note;
                
                //create a new note using the previously gathered information
                note = new Note(noteID, participant, description, createdBy, submitted, viewed, resolved);
                
                //add the note to the observable list
                noteIDs.add(note);
            }
        }
        // if this fail, print the stack trace
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Purpose:  Sets up the table by setting all the column titles based on the
     * information from the observable list.
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void initialize()
    {
        //calls the initialize data function
        initializeNoteData();
        
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getIDProperty());
        
        idColumn.setMaxWidth(100);
        idColumn.setMinWidth(100);
        
        statusColumn.setCellValueFactory(cellData -> cellData.getValue()
                .viewedProperty());
        
        statusColumn.setMaxWidth(70);
        statusColumn.setMinWidth(70);       
       
        //make the column not resizeable or sortable
        idColumn.setResizable(false);
        idColumn.setSortable(false);
        idColumn.setStyle("-fx-alignment: CENTER;");
        
        //make the column not resizeable or sortable
        statusColumn.setResizable(false);
        statusColumn.setSortable(false);
        statusColumn.setStyle("-fx-alignment: CENTER;");

        //add the column to the tableview
        noteTable.getColumns().addAll(idColumn, statusColumn);
        
        //set the observable list to the tableview
        noteTable.setItems(noteIDs);
    }
    
    /**
     * Purpose: To take the highlighted row of the tableview and return the
     * selected rows username to use later.
     * 
     * @return A string representing the note of the selected note from the
     *         table
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String getSelectedPK()
    {
        noteTable.setFocusTraversable(false);
        Note note = noteTable.getSelectionModel().getSelectedItem();
        this.setAsRead(note);
        return note.getNoteID();
    }
    
    /**
     * Purpose: To get the note from the id passed in
     * 
     * @param id The string ID of the note
     * @return a empty note with the noteID of the passed in id
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public Note getNoteFromID(String id)
    {
        int intID = Integer.parseInt(id);
        int index = noteIDs.indexOf(new Note(intID, 0, null, null, null, false, false));
        return noteIDs.get(index);
    }
    
    /**
     * Purpose: This will refresh the table when a change is made.
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */

    public void refreshTable()
    {
        this.noteIDs.clear();
        this.noteTable.getColumns().clear();
        this.initialize();;
    }
    
    /**
     * Purpose: this will set the note to read once the note ID has been clicked
     * 
     * @param note  The note that has been selected
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setAsRead(Note note)
    {
        //creates a string array that will hold the values that will be used to 
        //update the note
        String vals[][] = new String[2][2];
        vals[0][0] = "noteID";
        vals[1][0] = "viewed";
        vals[0][1] = note.getNoteID();
        vals[1][1] = "true";
        
        //updates the note in the database, using the array of values
        db.update(vals, "Notes", note.getNoteID());
        
        //refresh the table
        refreshTable();     
    }
    


}