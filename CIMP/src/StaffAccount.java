import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.SQLException;

/**
 * 
 * Purpose: manage when users want to log in and checks to be sure that all
 * users that login have an existing login and password and that they match the
 * input given by the user
 *
 * @author team CIMP
 * @version 1.0
 */
public class StaffAccount
{
    //The database object
    private DatabaseHelper db;

    //stringProperty that will hold the username
    protected StringProperty username;
    //stringProperty that will hold the last name
    protected StringProperty lastName;
    //stringProperty that will hold the first name
    protected StringProperty firstName;
    //stringProperty that will hold the email
    protected StringProperty email;
    //stringProperty that will hold the password
    protected StringProperty password;
    //stringProperty that will hold the access level
    protected StringProperty accessLevel;

    /**
     * 
     * Purpose: perform a login attempt based on the credentials provided by the
     * user
     * 
     * @param username
     *            : the username the user provided
     * @param password
     *            : the password the user provided
     * @return either an instantiated account(basic staff, medical admin, or
     *         techincal admin) or a null account indicating an unsucessful
     *         login
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
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
        String returnedLastName = "not found";
        String returnedFirstName = "not found";
        String returnedEmail = "not found";
        String accessLevel = "-1";
        // variable to keep track of the access level

        db = new DatabaseHelper();
        db.connect();
        // query used to find the provided username that returns the username,
        // password and accesslevel
        ResultSet user = db.select(
                "UserName, lastName, firstName, email, password, accessLevel",
                "Staff", "username='" + username + "'", "");
        try
        {
            // if the user result set has values in it
            while ( user.next() )
            {
                // the username, password and accessLevel returned from the
                // database
                returnedUsername = user.getString(1);
                returnedLastName = user.getString(2);
                returnedFirstName = user.getString(3);
                returnedEmail = user.getString(4);
                returnedPassword = user.getString(5);
                accessLevel = user.getString(6);
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
                if ( Integer.parseInt(accessLevel) == 0 )
                {
                    staff = new BasicStaff(returnedUsername, returnedLastName,
                            returnedFirstName, returnedEmail, returnedPassword,
                            accessLevel);
                }
                // if the accessLevel is 1 then instantiate a Medical Admin
                else if ( Integer.parseInt(accessLevel) == 1 )
                {
                    staff = new MedicalAdministrator(returnedUsername,
                            returnedLastName, returnedFirstName, returnedEmail,
                            returnedPassword, accessLevel);
                }
                // if the accessLevel is 2 then instantiate a Technical Admin
                else if ( Integer.parseInt(accessLevel) == 2 )
                {
                    staff = new TechnicalAdministrator(returnedUsername,
                            returnedLastName, returnedFirstName, returnedEmail,
                            returnedPassword, accessLevel);
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
     * 
     */
    public boolean logout()
    {
        return false;
    }

    /**
     * 
     * Purpose: Gets the username from the stringProperty 
     * @return: username string
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public String GetUsername()
    {
        return username.get();
    }

    /**
     * 
     * Purpose: 
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public StringProperty usernameProperty()
    {
        return username;
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public String GetLastName()
    {
        return lastName.get();
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public String GetFirstName()
    {
        return firstName.get();
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public String GetEmail()
    {
        return email.get();
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public StringProperty emailProperty()
    {
        return email;
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public String GetAccessLevel()
    {
        return accessLevel.get();
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public StringProperty accessLevelProperty()
    {
        String accessStr = " ";
        if ( accessLevel.getValue().equals("0") )
        {
            accessStr = "Basic Staff";
        }
        else if ( accessLevel.getValue().equals("1") )
        {
            accessStr = "Medical Administrator";
        }
        else
        {
            accessStr = "Technical Administrator";
        }
        StringProperty access = new SimpleStringProperty(accessStr);
        return access;
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public void setUsername( String username )
    {
        this.username.set(username);
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public void setLastName( String lastName )
    {
        this.lastName.set(lastName);
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public void setFirstName( String firstName )
    {
        this.firstName.set(firstName);
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public void setEmail( String email )
    {
        this.email.set(email);
    }

    /**
     * 
     * Purpose:
     * @return
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public void setAccessLevel( String accessLevel )
    {
        this.accessLevel.set(accessLevel);
    }
}
