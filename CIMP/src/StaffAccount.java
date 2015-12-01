import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 *  Purpose: manage when users want to log in and checks to be sure that all
 *  users that login have an existing login and password and that they match the
 *  input given by the user
 *
 * @author team CIMP
 * @version 1.0
 */
public class StaffAccount
{
    private DatabaseHelper db;

    protected String username; // staff members unique login ID
    protected String lastName; // the staff members last name
    protected String firstName; // the staff members first name
    protected String password; // the staff members password

 

    /**
     * 
     * Purpose: perform a login attempt based on the credentials provided by the
     * user
     * 
     * @param username : the username the user provided
     * @param password : the password the user provided
     * @return either an instantiated account(basic staff, medical admin, or
     *         techincal admin) or a null account indicating an unsucessful
     *         login
     */
    public StaffAccount login( String username, String password )
    {
        // the account that will be returned upon successful login
        StaffAccount staff = null;
        
        // These are set as not found because when there is nothing returned
        // form the database they are assigned nothing so they remain
        // uninstantiated
        String returnedUsername = "not found";
        String returnedPassword = "not found";
        // variable to keep track of the access level
        int accessLevel = -1;

        db = new DatabaseHelper();
        db.connect();
        // query used to find the provided username that returns the username,
        // password and accesslevel
        ResultSet user = db.select("UserName, password, accessLevel", "Staff",
                "username='" + username + "'", "");
        try
        {
            // if the user result set has values in it
            while ( user.next() )
            {
                // the username, password and accessLevel returned from the database
                returnedUsername = user.getString(1);
                returnedPassword = user.getString(2);
                accessLevel = Integer.parseInt(user.getString(3));
            }
        }
        catch ( SQLException e1 )
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // flag used to check if the returned username isn't empty
        if ( !returnedUsername.equals("not found") )
        {
            if ( returnedUsername.equals(username)
                    && returnedPassword.equals(password) )
            {
                // if the accessLevel is 0 then instantiate a Basic staff
                if ( accessLevel == 0 )
                {
                    staff = new BasicStaff(returnedUsername, returnedPassword);
                }
                // if the accessLevel is 1 then instantiate a Medical Admin
                else if ( accessLevel == 1 )
                {
                    staff = new MedicalAdministrator(returnedUsername,
                            returnedPassword);
                }
                // if the accessLevel is 2 then instantiate a Technical Admin
                else if ( accessLevel == 2 )
                {
                    staff = new TechnicalAdministrator(returnedUsername,
                            returnedPassword);
                }
            }
            else
            {
                // if the returned username is null, make the staff to return
                // null aswell
                staff = null;
            }
        }
        return staff;
    }

    /**
     * Purpose: Allows the user to logout of the system. moves them to the
     * loginGUI, with all of the account info reset in memory.
     * 
     * @return if the reset of information and sending user back to loginGUI is
     *         successful, return true, else, false
     */
    public boolean logout()
    {
        return false;
    }
}
