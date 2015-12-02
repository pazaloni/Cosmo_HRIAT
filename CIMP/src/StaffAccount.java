import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
        String returnedLastName = "not found";
        String returnedFirstName = "not found";
        String returnedEmail = "not found";
        String accessLevel = "-1";
        // variable to keep track of the access level


        db = new DatabaseHelper();
        db.connect();
        // query used to find the provided username that returns the username,
        // password and accesslevel
        ResultSet user = db.select("UserName, lastName, firstName, email, password, accessLevel", "Staff",
                "username='" + username + "'", "");
        try
        {
            // if the user result set has values in it
            while ( user.next() )
            {
                // the username, password and accessLevel returned from the database
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
                    staff = new MedicalAdministrator(returnedUsername, returnedLastName, 
                    		returnedFirstName, returnedEmail, returnedPassword, 
                    		accessLevel);
                }
                // if the accessLevel is 2 then instantiate a Technical Admin
                else if ( Integer.parseInt(accessLevel) == 2 )
                {
                    staff = new TechnicalAdministrator(returnedUsername, 
                    		returnedLastName, 
                    		returnedFirstName, returnedEmail, returnedPassword, 
                    		accessLevel);
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
    
    public String GetUsername()
    {
    	return username.get();
    }
    
    public StringProperty usernameProperty()
    {
    	return username;
    }
    
    public String GetLastName()
    {
    	return lastName.get();
    }
    
    public StringProperty lastNameProperty()
    {
    	return lastName;
    }
    
    public String GetFirstName()
    {
    	return firstName.get();
    }
    
    public StringProperty firstNameProperty()
    {
    	return firstName;
    }
    
    public String GetEmail()
    {
    	return email.get();
    }
    
    public StringProperty emailProperty()
    {
    	return email;
    }
    
    public String GetAccessLevel()
    {
    	return accessLevel.get();
    }
    
    public StringProperty accessLevelProperty()
    {
    	String accessStr = " ";
    	if(accessLevel.getValue().equals("0"))
    	{
    		accessStr = "Medical Staff";
    	}
    	else if(accessLevel.getValue().equals("1"))
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
    
    public void setUsername(String username)
    {
    	this.username.set(username);
    }
    
    public void setLastName(String lastName)
    {
    	this.lastName.set(lastName);
    }
    
    public void setFirstName(String firstName)
    {
    	this.firstName.set(firstName);
    }
    
    public void setEmail(String email)
    {
    	this.email.set(email);
    }
    
    public void setAccessLevel(String accessLevel)
    {
    	this.accessLevel.set(accessLevel);
    }
}
