import javafx.application.*;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ParticipantTableViewController
{
    // the tableview containing the information for all the staff accounts
    protected TableView<Participant> participantTable = new TableView<Participant>();
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
        initializeParticipantData();
        participantTable.setItems(participantData);
    }

    private void initializeParticipantData()
    {
        DatabaseHelper db = new DatabaseHelper();
        ObservableList<String> row = FXCollections.observableArrayList();
        ArrayList<String> participantInfo = new ArrayList<String>();
        // TODO fix to query appropriate address, emergency info
        // correct table Participant instead of Participant
        ResultSet rs = db.select("cosmoID, firstName, lastName, address, "
                + "dateUpdated", "Participant", "", "");

        // Strings to represent the TODO fix it
        String cosmoID;
        String firstName;
        String lastName;
        String participantName;

        // address
        String address;

        String emergencyContactName = "";
        String emergencyContactPhone = ""; // TODO Get from database
        String informationLastUpdated;

        try
        {
            while (rs.next())
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

                // create the participant object
                Participant participant = new Participant(cosmoID,
                        participantName, address, emergencyContactName,
                        emergencyContactPhone, informationLastUpdated);

                // add the participant into the tableview
                participantData.add(participant);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to populate Participant Table");
            e.printStackTrace();
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
                .getValue().getEmergencyContactPhoneProperty());
        emergencyPhoneColumn.setMinWidth(115);
        emergencyPhoneColumn.setResizable(false);

        // lastUpdatedColumn.setCellValueFactory(cellData ->
        // cellData.getValue().getUpdatedProperty());
        lastUpdatedColumn.setMinWidth(135);
        lastUpdatedColumn.setResizable(false);

        // make table columns not draggable to reorder it
        participantTable.getColumns().addListener(
                new ListChangeListener<Object>()
                {
                    @Override
                    public void onChanged(Change change)
                    {
                        change.next();
                        // if the column was changed
                        if (change.wasReplaced())
                        {
                            // clear all columns
                            participantTable.getColumns().clear();
                            // re-add the columns in order
                            participantTable.getColumns().addAll(cosmoIDColumn,
                                    participantNameColumn, addressColumn,
                                    emergencyNameColumn, emergencyPhoneColumn,
                                    lastUpdatedColumn);
                        }
                    }
                });

        // add the columns to the tableview
        participantTable.getColumns().addAll(cosmoIDColumn,
                participantNameColumn, addressColumn, emergencyNameColumn,
                emergencyPhoneColumn, lastUpdatedColumn);

        // set the data into the table
        participantTable.setItems(participantData);
    }

    /**
     * Purpose: Get the item that is selected in the table
     * 
     * @return the ID of the row that is selected
     */
    public String getSelectedPK()
    {
        Participant participant = participantTable.getSelectionModel()
                .getSelectedItem();
        return participant.getCosmoID();
    }

    /**
     * Purpose: To refresh the table so other classes can call this when they
     * update the information
     */
    public void refreshTable()
    {
        this.participantData.clear();
        this.initializeParticipantData();
        this.participantTable.getColumns().clear();
        this.initialize();
    }

}
