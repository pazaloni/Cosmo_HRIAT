import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StaffTableViewController
{
    // the tableview containing the information for all the staff accounts
    protected TableView<StaffAccount> staffTable = new TableView<StaffAccount>();
    // the column holding username information
    private TableColumn<StaffAccount, String> usernameColumn = new TableColumn<StaffAccount, String>(
            "Username");
    // the column holding the users last name
    private TableColumn<StaffAccount, String> lastNameColumn = new TableColumn<StaffAccount, String>(
            "Last Name");
    // the column holding the users first name
    private TableColumn<StaffAccount, String> firstNameColumn = new TableColumn<StaffAccount, String>(
            "First Name");
    // the column holding the users email
    private TableColumn<StaffAccount, String> emailColumn = new TableColumn<StaffAccount, String>(
            "Email");
    // the column holding the users accesslevel. It will be stored as integer
    // values, but displayed as a string representation
    private TableColumn<StaffAccount, String> accessLevelColumn = new TableColumn<StaffAccount, String>(
            "Access Level");
    // the data for each staff account
    public ObservableList<StaffAccount> staffData = FXCollections
            .observableArrayList();

    public StaffTableViewController()
    {
        intitializeStaffData();
        staffTable.setItems(staffData);
    }

    /**
     * Purpose: Fetches the account information from the database and then adds
     * the fetched information to observable list.
     */
    private void intitializeStaffData()
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();
        // Create an observable list that will store the individual row data
        ObservableList<String> row = FXCollections.observableArrayList();
        // The result set that will query the database to get all the users
        ResultSet rs = db.select("UserName, lastName, firstName, email, "
                + "accessLevel", "Staff", "", "");
        String username;
        String lastName;
        String firstName;
        String email;
        String password = "";
        String accessLevel;
        try
        {
            while ( rs.next() )
            {
                username = rs.getString(1);

                lastName = rs.getString(2);
                firstName = rs.getString(3);
                // if there is no email set, "none" will be displayed
                if ( rs.getString(4) == null )
                {
                    email = "none";
                }
                else
                {
                    email = rs.getString(4);
                }

                accessLevel = rs.getString(5);

                StaffAccount account;
                // if accessLevel is 0, then the user is a basicStaff
                if ( accessLevel.equals("0") )
                {
                    account = new BasicStaff(username, lastName, firstName,
                            email, password, accessLevel);
                }
                // If the accessLevel is 1, then the user is a
                // medicalAdministrator
                else if ( accessLevel.equals("1") )
                {
                    account = new MedicalAdministrator(username, lastName,
                            firstName, email, password, accessLevel);
                }
                // If the acessLevel is 2, then the user is a
                // technicalAdministrator
                else
                {
                    account = new TechnicalAdministrator(username, lastName,
                            firstName, email, password, accessLevel);
                }

                staffData.add(account);
            }
        }
        // if this fail, print the stack trace
        catch ( SQLException e )
        {

            e.printStackTrace();
        }

    }

    /**
     * Purpose: Sets up the table by setting all the column titles based on the
     * information from the observable list.
     */
    public void initialize()
    {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .usernameProperty());

        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .lastNameProperty());

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .firstNameProperty());

        emailColumn.setCellValueFactory(cellData -> cellData.getValue()
                .emailProperty());

        accessLevelColumn.setCellValueFactory(cellData -> cellData.getValue()
                .accessLevelProperty());

        staffTable.getColumns().addAll(usernameColumn, lastNameColumn,
                firstNameColumn, emailColumn, accessLevelColumn);

        staffTable.setItems(staffData);
    }

    /**
     * Purpose: To take the highlighted row of the tableview and return the
     * selected rows username to use later.
     * 
     * @return A string representing the username of the selected user from the
     *         table
     */
    public String getSelectedPK()
    {
        String result;

        StaffAccount account = staffTable.getSelectionModel().getSelectedItem();

        if ( account == null )
        {
            result = "null";
        }
        else
        {
            result = account.GetUsername();
        }
        return result;
    }

    /**
     * Purpose: This will remove the user from the table and then refresh the
     * table.
     * 
     * @param username
     *            The username that identifies which entry to remove from the
     *            table.
     */
    public void removeViewableUser( String username )
    {
        this.staffData.clear();
        this.intitializeStaffData();
        this.staffTable.getColumns().clear();
        this.initialize();
    }

    public void refreshTable()
    {
        this.staffData.clear();
        this.intitializeStaffData();
        this.staffTable.getColumns().clear();
        this.initialize();
    }
}
