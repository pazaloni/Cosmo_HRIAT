package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.ReactiveCare;

/**
 * Creates, initializes, and refreshes the TableView for Reactive Care
 * information for Quarterly Reports.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class ReactiveCareTableViewController
{
    // the tableview containing all of the data
    public TableView<ReactiveCare> reactiveCareTable = new TableView<ReactiveCare>();

    // Columns for all of the table data
    private TableColumn<ReactiveCare, String> yearColumn = new TableColumn<ReactiveCare, String>(
            "Year");
    private TableColumn<ReactiveCare, String> participantsColumn = new TableColumn<ReactiveCare, String>(
            "Participants Involved");
    private TableColumn<ReactiveCare, String> staffColumn = new TableColumn<ReactiveCare, String>(
            "Staff Members Involved");
    private TableColumn<ReactiveCare, String> totaColumn = new TableColumn<ReactiveCare, String>(
            "Total");

    // observable list containing all data for the table
    public ObservableList<ReactiveCare> reactiveCareData = FXCollections
            .observableArrayList();

    DatabaseHelper db = new DatabaseHelper();

    /**
     * Creates and initializes the reactiveCareTable
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public ReactiveCareTableViewController()
    {
        queryReactiveCareData();
        initialize();
        reactiveCareTable.setItems(reactiveCareData);
        reactiveCareTable.setFocusTraversable(false);
        reactiveCareTable.setMaxHeight(150);
    }

    /**
     * Queries the database for statistics on reactive care, counting the number
     * of participants and staff members involved for incidents each year that
     * is stored in the database, and places the data into the tableview.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    private void queryReactiveCareData()
    {
        ResultSet rsNumParticipants = db.select(
                "COUNT(Incidents.incidentID), Year(dateOfIncident)",
                "Incidents GROUP BY (Year(dateOfIncident))", "", "");
        ResultSet rsNumStaff = null;
        int numParticipants = 0;
        int numStaff = 0;
        int year = 0;

        try
        {
            while ( rsNumParticipants != null && rsNumParticipants.next() )
            {
                if ( rsNumParticipants != null )
                {

                    numParticipants = rsNumParticipants.getInt(1);
                    year = rsNumParticipants.getInt(2);

                    rsNumStaff = db
                            .select("COUNT(injuryID)",
                                    "IncidentInjuryTypes i JOIN Incidents j ON i.incidentID = j.incidentID",
                                    "injuryID = 13 AND Year(dateOfIncident) = "
                                            + year, "");

                    if ( rsNumStaff != null && rsNumStaff.next() )
                    {
                        numStaff = rsNumStaff.getInt(1);
                    }

                }

                ReactiveCare rc = new ReactiveCare(year, numParticipants,
                        numStaff);

                reactiveCareData.add(rc);
            }

        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * Requeries the database, and resets the information in the tableview with
     * current information
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public void refreshTable()
    {
        this.reactiveCareData.clear();
        this.reactiveCareTable.getColumns().clear();
        this.queryReactiveCareData();
        this.initialize();
    }

    /**
     * Sets the information in the tableview with information pulled from the
     * data holder objects (ReactiveCare), and sets the sizing for the columns.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    @SuppressWarnings("unchecked")
    public void initialize()
    {
        yearColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getYearProperty());
        yearColumn.setMinWidth(160);
        yearColumn.setResizable(false);

        participantsColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getParticipantsProperty());
        participantsColumn.setMinWidth(250);
        participantsColumn.setResizable(false);

        staffColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getStaffProperty());
        staffColumn.setMinWidth(250);
        staffColumn.setResizable(false);

        totaColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getTotalProperty());
        totaColumn.setMinWidth(220);
        totaColumn.setResizable(false);

        reactiveCareTable.getColumns().addAll(yearColumn, participantsColumn,
                staffColumn, totaColumn);
    }

}
