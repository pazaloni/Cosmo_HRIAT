package core;
import helpers.DatabaseHelper;
import helpers.ManageStaffAccountHelper;

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
    private DatabaseHelper db;
    private ManageStaffAccountHelper manageStaff;

    protected StringProperty username;
    protected StringProperty lastName;
    protected StringProperty firstName;
    protected StringProperty email;
    protected StringProperty password;
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
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StaffAccount login(String username, String password)
    {
        // the account that will be returned upon successful login
        StaffAccount staff = null;
        manageStaff = new ManageStaffAccountHelper();

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
       
       StaffAccount currentUser = manageStaff.queryStaff(username);
        
//        ResultSet user = db.select(
//                "UserName, lastName, firstName, email, password, accessLevel",
//                "Staff", "username='" + username + "'", "");
//        try
//        {
//            // if the user result set has values in it
//            while (user.next())
//            {
//                // the username, password and accessLevel returned from the
//                // database
//                returnedUsername = user.getString(1);
//                returnedLastName = user.getString(2);
//                returnedFirstName = user.getString(3);
//                returnedEmail = user.getString(4);
//                returnedPassword = user.getString(5);
//                accessLevel = user.getString(6);
//            }
//        }
//        catch (SQLException e1)
//        {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }

        // flag used to check if the returned user exists
        if (currentUser != null)
        {
            if (!(currentUser.GetUsername().equals(username)
                    && currentUser.GetPassword().equals(password)))
            {
                // if the returned username is null, make the staff to return
                // null aswell
                currentUser = null;
            }
        }
        return currentUser;
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
    
    
    /**
     * 
     * Purpose: To take in a username, query the database for that username and
     * if the user exists, return an object of the user
     * 
     * @param username
     *            String of user name to be queried on
     * @return StaffAccount a staffa ccount object 
     */
    public StaffAccount queryStaff(String username)
    {
        StaffAccount staffToReturn = null;
        
        ResultSet staff = db.select(
                "UserName, lastName, firstName, email, password, accessLevel",
                "Staff", "username='" + username + "'", "");
        
        String usernameOut = "";
        String lastName = "";
        String firstName = "";
        String email = "";
        String password = "";
        String accessLevel = "";
        
        try
        {
            while (staff.next())
            {
                usernameOut = staff.getString(1);
                lastName = staff.getString(2);
                firstName = staff.getString(3);
                email = staff.getString(4);
                password = staff.getString(5);
                accessLevel = staff.getString(6);
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        if (accessLevel.equals("0"))
        {
            staffToReturn = new BasicStaff(usernameOut, lastName, firstName, email, password, accessLevel);
        }
        else if (accessLevel.equals("1"))
        {
            staffToReturn = new MedicalAdministrator(usernameOut, lastName, firstName, email, password, accessLevel);
        }
        else if (accessLevel.equals("2"))
        {
            staffToReturn = new TechnicalAdministrator(usernameOut, lastName, firstName, email, password, accessLevel);
        }
        

        return staffToReturn;
    }
    

    /**
     * 
     * Purpose: Gets the username from the StringProperty
     * 
     * @return username String
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String GetUsername()
    {
        return username.get();
    }

    /**
     * 
     * Purpose: Gets the username property
     * 
     * @return: Username property StringProperty
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StringProperty usernameProperty()
    {
        return username;
    }

    /**
     * 
     * Purpose: Gets the lastName from the StringProperty
     * 
     * @return lastName String
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String GetLastName()
    {
        return lastName.get();
    }

    /**
     * 
     * Purpose: Gets the lastName property
     * 
     * @return lastName property StringProperty
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    /**
     * 
     * Purpose: Gets the firstName from the StringProperty
     * 
     * @return firstName String
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String GetFirstName()
    {
        return firstName.get();
    }

    /**
     * 
     * Purpose: Gets the firstName property
     * 
     * @return firstName property StringProperty
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    /**
     * 
     * Purpose: Gets the email from the StingProperty
     * 
     * @return email String
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String GetEmail()
    {
        return email.get();
    }

    /**
     * 
     * Purpose: Gets the email Property
     * 
     * @return email property StringProperty
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StringProperty emailProperty()
    {
        return email;
    }

    /**
     * 
     * Purpose: Gets the accesslevel from the StringProperty
     * 
     * @return accessLevel String
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public String GetAccessLevel()
    {
        return accessLevel.get();
    }
    
    public String GetPassword()
    {
        return password.get();
    }

    /**
     * 
     * Purpose: Checks the value of the accessLevel and assigns the proper
     * access level String that will be displayed in the table
     * 
     * @return accessLevel StringProperty
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public StringProperty accessLevelProperty()
    {
        String accessStr = " ";
        if (accessLevel.getValue().equals("0"))
        {
            accessStr = "Basic Staff";
        }
        else if (accessLevel.getValue().equals("1"))
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
     * Purpose: Sets the username StringProperty
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setUsername(String username)
    {
        this.username.set(username);
    }

    /**
     * 
     * Purpose: Sets the lastName StringProperty
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    /**
     * 
     * Purpose: Sets the firstName StringProperty
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setFirstName(String firstName)
    {
        this.firstName.set(firstName);
    }

    /**
     * 
     * Purpose: Sets the email StringProperty
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setEmail(String email)
    {
        this.email.set(email);
    }

    /**
     * 
     * Purpose: Sets the accessLevel StringProperty
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void setAccessLevel(String accessLevel)
    {
        this.accessLevel.set(accessLevel);
    }
}
