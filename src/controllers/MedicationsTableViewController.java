package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import core.Medication;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * Purpose: display the medications table
 * 
 * 
 * @author TEAM CIMP!
 * @version 1.0
 */
public class MedicationsTableViewController
{

    // Table
    public TableView<Medication> medicationsTable = new TableView<Medication>();

    // Table columns
    protected TableColumn<Medication, String> medicationNameColumn = new TableColumn<Medication, String>(
            "Medication");
    protected TableColumn<Medication, String> dosageColumn = new TableColumn<Medication, String>(
            "Dosage");
    protected TableColumn<Medication, String> timesGivenColumn = new TableColumn<Medication, String>(
            "Times given");
    protected TableColumn<Medication, String> reasonColumn = new TableColumn<Medication, String>(
            "Reason");

    // database helper
    private DatabaseHelper db;

    private ObservableList<Medication> medicationNameData = FXCollections
            .observableArrayList();

    /**
     * 
     * Constructor for the ActivityLogTableViewController class.
     */
    public MedicationsTableViewController(String cosmoID)
    {
        // When the activity log is instantiated, then pull all the information
        // from the database.
        retrieveMedicationData(cosmoID);
        this.initialize();
        medicationsTable.setItems(medicationNameData);
        medicationsTable.setFocusTraversable(false);
        medicationsTable.setMaxHeight(150);
    }

    /**
     * 
     * Purpose: Refresh the medications tableview
     */
    public void refreshTable( String cosmoID )
    {
        this.medicationNameData.clear();
        this.medicationsTable.getColumns().clear();
        this.retrieveMedicationData(cosmoID);
        this.initialize();
    }

    /**
     * 
     * Purpose: Query the database for all the activity-log information
     */
    private void retrieveMedicationData( String cosmoID )
    {
        db = new DatabaseHelper();

        db.connect();

        // Select all everything and order it desceding
        ResultSet medicationResults = db.select(
                "medicationName, dosage, timesGiven, reason", "Medication",
                "cosmoID=" + cosmoID, "");

        String medicationName;
        String dosage;
        String timesGiven;
        String reason;

        try
        {
            while ( medicationResults.next() )
            {
                medicationName = medicationResults.getString(1);
                dosage = medicationResults.getString(2);
                timesGiven = medicationResults.getString(3);
                reason = medicationResults.getString(4);

                Medication medication = new Medication(medicationName, dosage,
                        timesGiven, reason);

                // Add the log to the list
                medicationNameData.add(medication);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Failed to query medication informaiton");
        }

        db.disconnect();
    }

    /**
     * 
     * Purpose: to create the table and the columns
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        medicationNameColumn.setCellValueFactory(cellData -> cellData
                .getValue().getMedicationName());
        medicationNameColumn.setMinWidth(125);
        medicationNameColumn.setResizable(false);

        dosageColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getDosage());
        dosageColumn.setMinWidth(75);
        dosageColumn.setResizable(false);

        timesGivenColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getTimesGiven());
        timesGivenColumn.setMinWidth(250);
        timesGivenColumn.setResizable(false);

        reasonColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getReason());
        reasonColumn.setMinWidth(250);
        reasonColumn.setResizable(false);

        medicationsTable.getColumns().addListener(
                new ListChangeListener<Object>()
                {
                    @Override
                    public void onChanged( Change change )
                    {
                        change.next();
                        // if the column was changed
                        if ( change.wasReplaced() )
                        {
                            // clear all columns
                            medicationsTable.getColumns().clear();
                            // re-add the columns in order
                            medicationsTable.getColumns().addAll(
                                    medicationNameColumn, dosageColumn,
                                    timesGivenColumn, reasonColumn);
                        }
                    }
                });
        medicationsTable.getColumns().addAll(medicationNameColumn,
                dosageColumn, timesGivenColumn, reasonColumn);
    }

    /**
     * 
     * Purpose: Return the selected medication from the table
     * 
     * @return
     */
    public Medication getSelectedMedication()
    {
        return medicationsTable.getSelectionModel().getSelectedItem();
    }
}