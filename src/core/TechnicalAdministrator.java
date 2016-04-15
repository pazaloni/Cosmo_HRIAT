package core;

import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * Purpose: Represent the technical administrator in the system
 * 
 * @author YOUR NAME AND CST NUMBER GO HERE
 * @version 1.0
 */
public class TechnicalAdministrator extends BasicStaff
{

    public TechnicalAdministrator(String username, String lastName,
            String firstName, String email, String password, String accessLevel)
    {
        super(username, lastName, firstName, email, password, accessLevel);
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
