package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import core.MedicalCondition;
import core.ProgressNotes;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Purpose: Represents the progress notes table
 * 
 * @author cst207
 *
 */
public class ProgressNotesTableViewController
{

    public TableView<ProgressNotes> progressTable = new TableView<ProgressNotes>();

    private TableColumn<ProgressNotes, String> dateTimeColumn = new TableColumn<ProgressNotes, String>(
            "Date/Time");
    private TableColumn<ProgressNotes, String> nameColumn = new TableColumn<ProgressNotes, String>(
            "Name");
    private TableColumn<ProgressNotes, String> numColumn = new TableColumn<ProgressNotes, String>(
            "No.");
    private ObservableList<ProgressNotes> progressData = FXCollections
            .observableArrayList();

    public ProgressNotesTableViewController(String cosmoId)
    {
        retrieveProgressNotesData(cosmoId);
        initialize();
        progressTable.setItems(progressData);
        progressTable.setFocusTraversable(false);
        progressTable.setMaxHeight(150);
    }

    /**
     * 
     * Purpose: Query the database for the progress notes data
     * 
     * @parameter cosmoId: The cosmoId of the participant whose data is being
     *            retrieved.
     */
    private void retrieveProgressNotesData( String cosmoId )
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();

        db.connect();

        // Select statement for the
        ResultSet rs = db.select("dateTime, participantName, num",
                "ProgressNotes", "cosmoID=" + cosmoId, "");

        LocalDate dateTime;
        String participantName;
        String num;
        try
        {
            while ( rs.next() )
            {
                String progressDate = "";
                progressDate = rs.getString(1);
                // convert the dobtext into a localdate
                int yearEnd = progressDate.indexOf('-');
                int year = Integer.parseInt(progressDate.substring(0, yearEnd));
                progressDate = progressDate.substring(yearEnd + 1);
                int monthEnd = progressDate.indexOf('-');
                int month = Integer.parseInt(progressDate.substring(0, monthEnd));
                progressDate = progressDate.substring(monthEnd + 1);
                int day = Integer.parseInt(progressDate.substring(0, 2));
                dateTime = LocalDate.of(year, month, day);                
                participantName = rs.getString(2);
                num = rs.getString(3);

                ProgressNotes progressNote = new ProgressNotes(dateTime,
                        participantName, num);

                progressData.add(progressNote);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Failed to populate progress notse table");
        }
        db.disconnect();
    }

    /**
     * Purpose: Refresh the progressNotes table
     * 
     */
    public void refreshTable( String cosmoId )
    {
        this.progressData.clear();
        this.progressTable.getColumns().clear();
        this.retrieveProgressNotesData(cosmoId);
        this.initialize();
    }

    /**
     * 
     * @return The selected progress note
     */
    public ProgressNotes getSelectedPK()
    {
        return this.progressTable.getSelectionModel().getSelectedItem();
    }

    /**
     * 
     * Purpose: initialize the columns in the table and configure them
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getDateTime());
        dateTimeColumn.setMinWidth(100);
        dateTimeColumn.setResizable(false);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getName());
        nameColumn.setMinWidth(440);
        nameColumn.setResizable(false);

        numColumn.setCellValueFactory(cellData -> cellData.getValue().getNum());
        numColumn.setMinWidth(100);
        numColumn.setResizable(false);

        progressTable.getColumns().addListener(new ListChangeListener<Object>()
        {
            @Override
            public void onChanged( Change change )
            {
                change.next();
                // if the column was changed
                if ( change.wasReplaced() )
                {
                    // clear all columns
                    progressTable.getColumns().clear();
                    // re-add the columns in order
                    progressTable.getColumns().addAll(dateTimeColumn,
                            nameColumn, numColumn);
                }
            }
        });
        progressTable.getColumns()
                .addAll(dateTimeColumn, nameColumn, numColumn);
    }
}
