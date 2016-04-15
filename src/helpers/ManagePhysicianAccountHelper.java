package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import core.BasicStaff;
import core.MedicalAdministrator;
import core.Physician;
import core.StaffAccount;
import core.TechnicalAdministrator;

/**
 * 
 * Purpose: Class to manage the adding, editing and removal of a staff accounts
 * and to do all of these to the database
 * 
 *
 * @author team CIMP
 * @version 1.0
 */
public class ManagePhysicianAccountHelper
{
    // displayed when a field is empty
    private static final String EMPTY_FIELD = "One or more of your fields is empty";

    private DatabaseHelper db;

    /**
     * purpose: Constructor for ManageStaffAccountHelper which creates and
     * instance of the DataBaseHelper Constructor for the
     * ManageStaffAccountHelper class.
     */
    public ManagePhysicianAccountHelper()
    {
        db = new DatabaseHelper();
    }

    /**
     * 
     * Purpose: Return true or false if the user was added to the database
     * successfully
     * 
     * @param phone
     *            : the phone number the user passed in
     * @param lastName
     *            : the lastname the user passed in
     * @param firstName
     *            : the firstName the user passed in
     * 
     * 
     * @return boolean: true if the user addition was successful, false
     *         otherwise
     */
    public String addUser(String firstName, String lastName, String phone)
    {
        String result = "";

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty())

        {
            result = EMPTY_FIELD;
        }
        else
        {

            db.connect();
            // 2d array to hold all the column names and data
            String[][] newUserInfo = new String[4][2];
            // column names
            newUserInfo[0][0] = "physicianID";
            newUserInfo[1][0] = "firstName";
            newUserInfo[2][0] = "lastName";
            newUserInfo[3][0] = "phone";
            // actual data for the columns, phone is left out because it is an
            // auto number
            newUserInfo[1][1] = firstName;
            newUserInfo[2][1] = lastName;
            newUserInfo[3][1] = phone;

            // insert the data into the database
            db.insert(newUserInfo, "Physician");
            result = "";

        }
        return result;
    }

    /**
     * 
     * Purpose: edit an existing user in the database
     * 
     * @param phone
     *            : the phone number the user passed in
     * @param lastName
     *            : the lastname the user passed in
     * @param firstName
     *            : the firstName the user passes in
     * @param physID
     *            : the primary key used to grab the correct record to edit
     */
    public String editUser(String firstName, String lastName, String phone,
            String physID)
    {
        String result = "";

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty())
        {
            result = EMPTY_FIELD;
        }
        else
        {
            db.connect();

            String[] newUserInfo = new String[4];
            newUserInfo[0] = physID;
            newUserInfo[1] = firstName;
            newUserInfo[2] = lastName;
            newUserInfo[3] = phone;

            db.update(newUserInfo, "Physician", physID);
            result = "";

        }
        return result;
    }

    /**
     * Purpose: This will take the selected user from the table, confirm that
     * you wish to delete them, if so, will delete the selected user, then
     * refresh the table of accounts
     * 
     * @param username
     *            : The user that you will remove
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public boolean removeUser(String username)
    {
        return this.db.delete("Staff", "UserName = \"" + username + "\"");
    }

    /**
     * 
     * Purpose: To take in a physician ID, query the database for that ID and if
     * the physician exists, return an object of the physician
     * 
     * @param username
     *            String of user name to be queried on
     * @return StaffAccount a staff account object
     */
    public Physician queryStaff(String ID)
    {
        Physician staffToReturn = null;

        ResultSet rs = db.select("physicianID, firstName, lastName, phone",
                "Physician", "", "");

        String lastName = "";
        String firstName = "";
        String phone = "";
        String PhysID = "";

        try
        {
            while (rs.next())
            {
                PhysID = rs.getString(1);

                firstName = rs.getString(2);

                lastName = rs.getString(3);

                phone = rs.getString(4);

            }
        }
        // if this fail, print the stack trace
        catch (SQLException e)
        {

            e.printStackTrace();
        }

        staffToReturn = new Physician(PhysID, firstName, lastName, phone);

        return staffToReturn;
    }
}
