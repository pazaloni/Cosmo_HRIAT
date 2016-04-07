package controllers;

import helpers.DatabaseHelper;

import java.sql.*;

import core.BasicStaff;
import core.MedicalAdministrator;
import core.Physician;
import core.StaffAccount;
import core.TechnicalAdministrator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PhysicianTableViewController
{
    // the tableview containing the information for all the Physicians
    public TableView<Physician> PhysicianTable = new TableView<Physician>();
    // the column with the phone info
    private TableColumn<Physician, String> phoneColumn = new TableColumn<Physician, String>(
            "Phone");
    // the column holding the users last name
    private TableColumn<Physician, String> lastNameColumn = new TableColumn<Physician, String>(
            "Last Name");
    // the column holding the Physicians first name
    private TableColumn<Physician, String> firstNameColumn = new TableColumn<Physician, String>(
            "First Name");

    // the data for each staff account
    public ObservableList<Physician> PhysicianData = FXCollections
            .observableArrayList();

    // constructor used to initialize the table and settings
    public PhysicianTableViewController()
    {
        intitializePhysicianData();
        PhysicianTable.setItems(PhysicianData);
    }

    /**
     * Purpose: Fetches thePhysician information from the database and then adds
     * the fetched information to observable list.
     * 
     * @author Kyle Unick cst214
     */
    private void intitializePhysicianData()
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();
        // Create an observable list that will store the individual row data
        ObservableList<String> row = FXCollections.observableArrayList();
        // The result set that will query the database to get all the users
        ResultSet rs = db.select("physicianID, firstName, lastName, phone",
                "Physician", "", "");
        PhysicianTable.setFocusTraversable(false);

        PhysicianTable
                .setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        String lastName;
        String firstName;
        String phone;
        String PhysID;

        try
        {
            while (rs.next())
            {
                PhysID = rs.getString(1);

                firstName = rs.getString(2);

                lastName = rs.getString(3);

                phone = rs.getString(4);

                Physician physician = new Physician(PhysID, firstName,
                        lastName, phone);

                PhysicianData.add(physician);
            }
        }
        // if this fail, print the stack trace
        catch (SQLException e)
        {

            e.printStackTrace();
        }

    }

    /**
     * Purpose: Sets up the table by setting all the column titles based on the
     * information from the observable list.
     * 
     * @author Kyle Unick cst214
     */
    public void initialize()
    {
        // set column name and size settings
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getPhysPhoneProperty());
        phoneColumn.setMinWidth(200);
        phoneColumn.setResizable(false);
        // set last name column name and size settings
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getLastNameProperty());
        lastNameColumn.setMinWidth(250);
        lastNameColumn.setResizable(false);
        // setfirst name column name and size settings
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getFirstNameProperty());
        firstNameColumn.setMinWidth(250);
        firstNameColumn.setResizable(false);

        PhysicianTable.getColumns().addAll(lastNameColumn, firstNameColumn,
                phoneColumn);

        PhysicianTable.setItems(PhysicianData);
    }

    public String getSelectedPK()
    {
        String result;

        Physician physician = PhysicianTable.getSelectionModel()
                .getSelectedItem();

        if (physician == null)
        {
            result = "null";
        }
        else
        {

            result = physician.getFirstName();

        }
        return result;
    }

    /**
     * Purpose: This will remove the Physician from the table and then refresh
     * the table.
     * 
     * @param username
     *            The username that identifies which entry to remove from the
     *            table.
     * @author Kyle Unick cst214
     */
    public void removeViewableUser(String username)
    {
        this.PhysicianData.clear();
        this.intitializePhysicianData();
        this.PhysicianTable.getColumns().clear();
        this.initialize();
    }

    /**
     * Purpose: This will refresh the table when a change is made.
     * 
     * @author Kyle Unick cst214
     */
    public void refreshTable()
    {
        this.PhysicianData.clear();
        this.intitializePhysicianData();
        this.PhysicianTable.getColumns().clear();
        this.initialize();
    }
}
