import javafx.beans.property.SimpleStringProperty;


/**
 * 
 *  Purpose: Represent the technical administrator in the system
 * @author YOUR NAME AND CST NUMBER GO HERE
 * @version 1.0
 */
public class TechnicalAdministrator extends StaffAccount
{


    public TechnicalAdministrator(String username, String lastName, String firstName, String email,
    		String password, String accessLevel)
    {
        this.username = new SimpleStringProperty( username);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.email = new SimpleStringProperty( email);
        this.password = new SimpleStringProperty(password);
        this.accessLevel = new SimpleStringProperty(accessLevel);
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
