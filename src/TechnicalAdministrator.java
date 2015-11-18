
/**
 * 
 *  Purpose: Represent the technical administrator in the system
 * @author YOUR NAME AND CST NUMBER GO HERE
 * @version 1.0
 */
public class TechnicalAdministrator extends StaffAccount
{

    public TechnicalAdministrator(String staffID, String password)
    {
        this.staffID = staffID;
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

    public boolean removeAccount( String staffID )
    {
        DatabaseHelper db = new DatabaseHelper();
        String whereClause = "username = '" + staffID +"'";
        return db.delete("Staff", whereClause);
    }

    public boolean resetPassword( String staffID, String newPassword )
    {
        return false;
    }

}
