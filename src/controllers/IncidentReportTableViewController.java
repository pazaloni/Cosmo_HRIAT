package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import helpers.DatabaseHelper;
import core.Incident;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Purpose: The controller for the incident report tableview
 * 
 * @author Steven Palchinski
 *
 */
public class IncidentReportTableViewController
{
    // the tableview for the incidents
    public TableView<Incident> incidentTable = new TableView<Incident>();

    // columns for the tableview
    private TableColumn<Incident, String> incidentIDColumn = new TableColumn<Incident, String>(
            "Incident ID");
    private TableColumn<Incident, String> cosmoIDColumn = new TableColumn<Incident, String>(
            "Cosmo ID");
    private TableColumn<Incident, String> dateOfIncidentColumn = new TableColumn<Incident, String>(
            "Date Of Incident");
    private TableColumn<Incident, String> timeOfIncidentColumn = new TableColumn<Incident, String>(
            "Time Of Incident");
    private TableColumn<Incident, String> descriptionColumn = new TableColumn<Incident, String>(
            "Description");

    // Observable list for the incident data
    public ObservableList<Incident> incidentData = FXCollections
            .observableArrayList();

    /**
     * Purpose: constructor for the IncidentTableviewController
     */
    public IncidentReportTableViewController()
    {
        // Get the data from the Incidents table
        retrieveIncidentData("", "Incidents");
        // add the data to the table
        incidentTable.setItems(incidentData);
        incidentTable.setMaxWidth(700);
        incidentTable.setMaxHeight(250);

        // set the focus to not be traversable
        incidentTable.setFocusTraversable(false);
    }

    /**
     * Purpose: To retrieve the data from the database and add each incident to
     * the incident data observable list
     * 
     * @param conditon any conditions for the search
     * @param table What table to search
     */
    private void retrieveIncidentData( String conditon, String table )
    {
        // clear the incident data
        incidentData.clear();
        // create a database helper instance
        DatabaseHelper db = new DatabaseHelper();
        // query the database to get the incident id, cosmoid, dateOfIncident,
        // timeOfIncident, and incident description
        ResultSet rs = db.select("incidentID, cosmoID, dateOfIncident, "
                + "timeOfIncident, incidentDescription", "Incidents", "", "");

        // strings to hold the values from the query
        String incidentID;
        String cosmoID;
        String dateOfIncident;
        String timeOfIncident;
        String incidentDescription;

        // Loop through the resultset and add to the incidentData
        try
        {
            while ( rs.next() )
            {
                incidentID = rs.getString(1);
                cosmoID = rs.getString(2);
                dateOfIncident = rs.getString(3);
                timeOfIncident = rs.getString(4);
                incidentDescription = rs.getString(5);

                Incident incident = new Incident(incidentID, cosmoID,
                        dateOfIncident, timeOfIncident, incidentDescription);

                incidentData.add(incident);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: Initialize the data for the table, including setting the columns
     * settings, and adding data
     */
    public void initialize()
    {
        // Set all columns headers, min widths and resizability
        incidentIDColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getReportID());
        incidentIDColumn.setMinWidth(50);
        incidentIDColumn.setResizable(false);

        cosmoIDColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getCosmoID());
        cosmoIDColumn.setMinWidth(50);
        cosmoIDColumn.setResizable(false);

        dateOfIncidentColumn.setCellValueFactory(cellData -> cellData
                .getValue().displayDateOfIncident());
        dateOfIncidentColumn.setMinWidth(100);
        dateOfIncidentColumn.setResizable(false);

        timeOfIncidentColumn.setCellValueFactory(cellData -> cellData
                .getValue().getTimeOfIncident());
        timeOfIncidentColumn.setMinWidth(100);
        timeOfIncidentColumn.setResizable(false);

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getIncidentDescription());
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setResizable(false);

        // Prevent the columns from being reordered
        incidentTable.getColumns().addListener(new ListChangeListener<Object>()
        {
            @Override
            public void onChanged( Change change )
            {
                change.next();
                if ( change.wasReplaced() )
                {
                    incidentTable.getColumns().clear();
                    incidentTable.getColumns().addAll(incidentIDColumn,
                            cosmoIDColumn, dateOfIncidentColumn,
                            timeOfIncidentColumn, descriptionColumn);
                }
            }
        });
        // Add all the columns to the table
        incidentTable.getColumns().addAll(incidentIDColumn, cosmoIDColumn,
                dateOfIncidentColumn, timeOfIncidentColumn, descriptionColumn);
    }

    /**
     * Purpose: To get the primary key from the row of the table that is
     * selected
     * 
     * @return the string result of the primary key
     */
    public String getSelectedPK()
    {
        String result;

        Incident incident = incidentTable.getSelectionModel().getSelectedItem();

        if ( incident == null )
        {
            result = "null";
        }
        else
        {
            result = incident.getIncidentID();
        }
        return result;
    }

    /**
     * Purpose: To refresh the table
     * 
     * @param condition the conditions of the query
     * @param table the table to query
     */
    public void refreshTable( String condition, String table )
    {
        this.incidentData.clear();
        this.retrieveIncidentData(condition, table);
        this.incidentTable.getColumns().clear();
        this.initialize();
    }

}
