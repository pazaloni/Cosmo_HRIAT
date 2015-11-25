/**
 * 
 * Purpose: Represent the technical administrator in the system
 * 
 * @author YOUR NAME AND CST NUMBER GO HERE
 * @version 1.0
 */
public class TechnicalAdministrator extends StaffAccount
{

    public TechnicalAdministrator(String username, String password)
    {
        this.username = username;
        this.password = password;
        // connect to database, get the staff account record

        // fetch the first and last name, and assign them to
        // the firstName and lastname variables
    }

    public boolean addAccount( String lastName, String firstName,
            String password )
    {
        return false;
    }

    public boolean removeAccount( int staffID )
    {
        return false;
    }

    public boolean resetPassword( int staffID, String newPassword )
    {
        return false;
    }

}
