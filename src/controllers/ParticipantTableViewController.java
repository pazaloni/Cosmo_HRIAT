package controllers;

import helpers.DatabaseHelper;
import helpers.FormatHelper;
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import core.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Purpose: Managing the participant tableview on the MedicalStaffMainPageGUI
 * 
 * @author Team CIMP
 *
 */
public class ParticipantTableViewController
{
    // the tableview containing the information for all the staff accounts
    public TableView<Participant> participantTable = new TableView<Participant>();
    // the columns of the participant table
    private TableColumn<Participant, String> cosmoIDColumn = new TableColumn<Participant, String>(
            "Cosmo ID");
    private TableColumn<Participant, String> participantNameColumn = new TableColumn<Participant, String>(
            "Participant");
    private TableColumn<Participant, String> addressColumn = new TableColumn<Participant, String>(
            "Home Address");
    private TableColumn<Participant, String> emergencyNameColumn = new TableColumn<Participant, String>(
            "Emergency Contact Name");
    private TableColumn<Participant, String> emergencyPhoneColumn = new TableColumn<Participant, String>(
            "Emergency Phone");
    private TableColumn<Participant, String> lastUpdatedColumn = new TableColumn<Participant, String>(
            "Last Updated");
    private TableColumn<Participant, String> statusColumn = new TableColumn<Participant, String>(
            "Status");

    public ObservableList<Participant> participantData = FXCollections
            .observableArrayList();

    /**
     * Constructor for the ParticipantTableViewController class.
     * 
     * Initializes the data from the database
     * 
     * Sets the items from the database into the tableview
     */
    public ParticipantTableViewController()
    {
        retrieveParticipantData("", "Participant");
        participantTable.setItems(participantData);
        participantTable.setFocusTraversable(false);
    }

    /**
     * Method for updating currently displayed participant data
     * 
     * @param condition: The restrictions on the returned results
     * @param table: The table we are querying from
     */
    public void retrieveParticipantData( String condition, String table )
    {

        participantData.clear();
        DatabaseHelper db = new DatabaseHelper();
        // If the table is Allergies, search for all participants with that
        // Allergy
        if ( table.equals("Allergies") )
        {
            String allergy = condition;
            condition = "cosmoID = (SELECT cosmoID FROM Allergies where "
                    + "allergicTo LIKE '%" + allergy + "%')";

            table = "Participant";
        }
        ResultSet rs = db.select("cosmoID, firstName, lastName, address, "
                + "dateUpdated, participantStatus, emergencyContactID", table,
                condition,
                "participantStatus = 'Deceased', participantStatus = 'Inactive',"
                        + " participantStatus = 'Active'");

        // Strings to represent the data to be displayed.
        String cosmoID;
        String firstName;
        String lastName;
        String participantName;
        String address;

        String emergencyContactName = "";
        String emergencyContactPhone = ""; // TODO Get from database
        String informationLastUpdated;
        String participantStatus;

        try
        {
            while ( rs.next() )
            {
                // get the information from the database
                cosmoID = rs.getString(1);
                System.out.println(cosmoID);
                firstName = rs.getString(2);
                lastName = rs.getString(3);
                address = rs.getString(4);

                // concatenate name
                participantName = firstName + " " + lastName;

                // get the last time the information was updated
                informationLastUpdated = rs.getString(5);

                // /get the status of the participant
                participantStatus = rs.getString(6);

                // Query for the participant emergency contact info
                ResultSet crs = db.select("firstName, lastName, phoneNumber",
                        "EmergencyContact", "",
                        "emergencyContactID=" + rs.getString(7));

                while ( crs.next() )
                {
                    if ( crs.getString(1) == null && crs.getString(2) == null )
                    {
                        emergencyContactName = " ";
                    }
                    else
                    {
                        emergencyContactName = crs.getString(1) + " "
                                + crs.getString(2);
                    }

                    if ( crs.getString(3) == null )
                    {
                        emergencyContactPhone = " ";
                    }
                    else
                    {
                        emergencyContactPhone = crs.getString(3);
                    }

                }

                // emergencyContactPhone = displayPhoneNumber(cosmoID,
                // emergencyContactPhone);

                // / create the participant object
                Participant participant = new Participant(cosmoID,
                        participantName, address, emergencyContactName,
                        emergencyContactPhone, informationLastUpdated,
                        participantStatus);

                // add the participant into the tableview
                participantData.add(participant);
            }
        }
        catch ( SQLException e )
        {

            e.printStackTrace();
            System.out.println("Failed to populate Participant Table");
        }

    }

    /**
     * 
     * Purpose: To create the table and columns
     */
    public void initialize()
    {
        cosmoIDColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getCosmoIDProperty());
        cosmoIDColumn.setMinWidth(50);
        cosmoIDColumn.setResizable(false);

        participantNameColumn.setCellValueFactory(cellData -> cellData
                .getValue().getParticipantNameProperty());
        participantNameColumn.setMinWidth(175);
        participantNameColumn.setResizable(false);

        addressColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getAddressProperty());
        addressColumn.setMinWidth(200);
        addressColumn.setResizable(false);

        emergencyNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getEmergencyContactProperty());
        emergencyNameColumn.setMinWidth(180);
        emergencyNameColumn.setResizable(false);

        emergencyPhoneColumn.setCellValueFactory(cellData -> cellData
                .getValue().displayEmergencyContactPhoneProperty());
        emergencyPhoneColumn.setMinWidth(115);
        emergencyPhoneColumn.setResizable(false);

        lastUpdatedColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getUpdatedProperty());
        lastUpdatedColumn.setMinWidth(135);
        lastUpdatedColumn.setResizable(false);

        // /Set the properties of the new status column
        statusColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getStatusProperty());
        statusColumn.setMinWidth(75);
        statusColumn.setResizable(false);

        // make table columns not draggable to reorder it
        participantTable.getColumns().addListener(
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
                            participantTable.getColumns().clear();
                            // / re-add the columns in order
                            participantTable.getColumns().addAll(cosmoIDColumn,
                                    participantNameColumn, addressColumn,
                                    emergencyNameColumn, emergencyPhoneColumn,
                                    lastUpdatedColumn, statusColumn);
                        }
                    }
                });

        // / add the columns to the tableview
        participantTable.getColumns().addAll(cosmoIDColumn,
                participantNameColumn, addressColumn, emergencyNameColumn,
                emergencyPhoneColumn, lastUpdatedColumn, statusColumn);

        // set the data into the table
        // participantTable.setItems(participantData);
    }

    /**
     * Purpose: Get the item that is selected in the table
     * 
     * @return the ID of the row that is selected
     */
    public String getSelectedPK()
    {
        participantTable.setFocusTraversable(false);
        Participant participant = participantTable.getSelectionModel()
                .getSelectedItem();
        return participant.getCosmoID();
    }

    /**
     * Purpose: To refresh the table so other classes can call this when they
     * update the information
     */
    public void refreshTable( String condition, String table )
    {
        this.participantData.clear();
        this.retrieveParticipantData(condition, table);
        this.participantTable.getColumns().clear();
        this.initialize();
    }

    /**
     * Purpose: return a filename formatted for display.
     * 
     * @param cosmoID : The cosmoID that needs to be removed from the end of the
     *            filename
     * @return
     */
    public StringProperty displayPhoneNumber( String cosmoID, String phoneNumber )
    {
        FormatHelper fh = new FormatHelper();

        String formatedPhone = fh.formatPhoneNum(phoneNumber);

        StringProperty displayName = new SimpleStringProperty(formatedPhone);
        return displayName;
    }

}
