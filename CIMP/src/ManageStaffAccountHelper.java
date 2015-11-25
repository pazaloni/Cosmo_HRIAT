import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageStaffAccountHelper
{

    private static final String EMPTY_FIELD = "One of your fields is empty";
    private static final String PASSWORD_NOT_SAME = "Passwords do not match";
    private static final String EMAIL_NOT_VALID = "Email is not valid";
    private static final String USERNAME_NOT_UNIQUE="Username is already taken";
    private static final String RESET_TEXT = "";

    private DatabaseHelper db;

    public ManageStaffAccountHelper()
    {
        db = new DatabaseHelper();
    }

    /**
     * 
     * Purpose: Return true or false if the user was added to the database
     * successfully
     * 
     * @param username
     *            : the username the user passed in
     * @param lastName
     *            : the lastname the user passed in
     * @param firstName
     *            : the firstName the user passed in
     * @param email
     *            : the email the user passed in
     * @param password
     *            : the password the user passed in
     * @param repeatPW
     *            : the second password the user passed in
     * @param securityLv
     *            : the the user passed in
     * 
     * @return boolean: true if the user addition was successful, false
     *         otherwise
     */
    public String addUser( String username, String lastName, String firstName,
            String email, String password, String repeatPW, String securityLv )
    {
        String result = "";

        if ( firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()
                || password.isEmpty() || repeatPW.isEmpty()
                || securityLv.isEmpty() )

        {
            result = EMPTY_FIELD;
        }
        else
        {
            // if the paswords are the same, proceed
            if ( password.equals(repeatPW) )
            {
                // if the email cotains an @ and .
                if ( email.contains("@") && email.contains(".") )
                {

                    db.connect();
                    // if the username does not exist in the database
                    if ( !usernameExists(username) )
                    {
                        String[] newUserInfo = new String[6];
                        newUserInfo[0] = username;
                        newUserInfo[1] = lastName;
                        newUserInfo[2] = firstName;
                        newUserInfo[3] = email;
                        newUserInfo[4] = password;
                        newUserInfo[5] = securityLv;
                        db.insert(newUserInfo, "Staff");
                        result = "";

                    }
                    // if the username does exist, change the warning label with
                    // the appropriate message
                    else
                    {
                        result = USERNAME_NOT_UNIQUE;
                    }
                }
                // if the email is not valid, change the warning label with the
                // appropriate message
                else
                {
                    result = EMAIL_NOT_VALID;
                }
            }
            // if the passwords do not match, change the warning label with the
            // appropriate message
            else
            {
                result = PASSWORD_NOT_SAME;
            }
        }
        return result;
    }

    /**
     * 
     * Purpose: Query the database and check if the username has been taken
     *
     * @param username
     *            : the username for the new account
     * @return boolean: true if the username exists, false otherwise
     */
    private boolean usernameExists( String username )
    {
        boolean result = false;

        // result set that we obtain form the database
        ResultSet set = db.select("UserName", "Staff", "", "");
        try
        {
            while ( set.next() && !result )
            {
                // if the username for the new user is already in the database
                // then the result is false
                if ( username.equals(set.getString(1)) )
                {
                    result = true;
                }
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void editUser( String username )
    {

    }

    public void removeUser( String username )
    {

    }
}
