import javafx.beans.property.SimpleStringProperty;


/**
 * Purpose: Represent the basic staff within the system
 * 
 * @author TEAM CIMP
 * 
 */
public class BasicStaff extends StaffAccount
{

    // private ArrayList<Note> allNotes;


    public BasicStaff(String username, String lastName, String firstName, String email,
    		String password, String accessLevel)
    {
        this.username = new SimpleStringProperty( username);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.email = new SimpleStringProperty( email);
        this.password = new SimpleStringProperty(password);
        this.accessLevel = new SimpleStringProperty(accessLevel);
    }

}
