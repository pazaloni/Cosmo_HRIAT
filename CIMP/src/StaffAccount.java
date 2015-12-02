import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public abstract class StaffAccount
{
	protected StringProperty username;
	protected StringProperty lastName;
	protected StringProperty firstName;
	protected StringProperty email;
	protected StringProperty password;
	protected StringProperty accessLevel;

    
    /**
     * Purpose: If the login information matches that of a staff account in the
     * database, it will return an instance of that staff member's account, so
     * their information and security level can be used to determine what they
     * see. This method assumes that the information has already been validated.
     * 
     * @return staffAccount: the account of the user that has successfully
     *         logged in.
     */
    public boolean login( String staffID, String password )
    {
        boolean result = false;
        if ( this.username.equals( staffID) && this.password.equals(password) )
        {
            result = true;
        }
        return result;
    }
    /* Flaging for possible removal
    private StaffAccount lookupAccount( int staffID, String password )
    {
        // the security level, determines what class type it will be
        // 0 = basic staff
        // 1 = medical admin
        // 2 = tech admin
        int securityLevel = 0;
        StaffAccount account = null;
        switch ( securityLevel )
        {
            case 0:
            {
                account = new BasicStaff(staffID, password);
                break;
            }
            case 1:
            {
                account = new MedicalAdministrator(staffID, password);
                break;
            }
            case 2:
            {
                account = new TechnicalAdministrator(staffID, password);
                break;
            }
        }

        return account;
    }	
	*/
    
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
    	System.out.println(accessLevel);
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
