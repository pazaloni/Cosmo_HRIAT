import static org.junit.Assert.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Before;
import org.junit.Test;

public class testRemoveUser
{

    private String usernameToRemove;
    private String firstNameToRemove;
    private String lastNameToRemove;
    private String emailToRemove;
    private String passwordToRemove;
    private String securityToRemove;

    private DatabaseHelper db;

    StaffAccount userToRemove;

    ManageStaffAccountHelper acctHelper;

    @Before
    public void setUp() throws Exception
    {
        db = new DatabaseHelper();

        usernameToRemove = "Link1234";
        firstNameToRemove = "Link";
        lastNameToRemove = "Hylian";
        emailToRemove = "saveZelda@ganondorf.ca";
        passwordToRemove = "Zelda";
        securityToRemove = "0";

        userToRemove = new BasicStaff(usernameToRemove, lastNameToRemove,
                firstNameToRemove, emailToRemove, passwordToRemove,
                securityToRemove);

        acctHelper = new ManageStaffAccountHelper();
    }

    @Test
    public void testRemove()
    {
        acctHelper.addUser(usernameToRemove, lastNameToRemove,
                firstNameToRemove, emailToRemove, passwordToRemove,
                passwordToRemove, securityToRemove);

        String[] userToMatch = acctHelper.queryStaff(usernameToRemove);

        assertTrue(userToMatch[0].equals(usernameToRemove));

        acctHelper.removeUser(usernameToRemove);

        String[] RemovedUser = acctHelper.queryStaff(usernameToRemove);

        // only need to check username, since it should always be unique
        assertTrue(RemovedUser[0] == null);
    }

    public void testGetUsername()
    {
        assertTrue(userToRemove.GetUsername().equals(usernameToRemove));
    }

    public void testGetLastName()
    {
        assertTrue(userToRemove.GetLastName().equals(lastNameToRemove));
    }

    public void testGetFirstName()
    {
        assertTrue(userToRemove.GetFirstName().equals(firstNameToRemove));
    }

    public void testGetEmail()
    {
        assertTrue(userToRemove.GetEmail().equals(emailToRemove));
    }

    public void testGetAccessLevel()
    {
        assertTrue(userToRemove.GetAccessLevel().equals(securityToRemove));
    }

    public void testUsernameProperty()
    {
        StringProperty username = new SimpleStringProperty(usernameToRemove);
        assertTrue(userToRemove.usernameProperty().equals(username));
    }

    public void testLastNameProperty()
    {
        StringProperty lastName = new SimpleStringProperty(lastNameToRemove);
        assertTrue(userToRemove.lastNameProperty().equals(lastName));
    }

    public void testFirstNameProperty()
    {
        StringProperty firstName = new SimpleStringProperty(firstNameToRemove);
        assertTrue(userToRemove.firstNameProperty().equals(firstName));
    }

    public void testEmailProperty()
    {
        StringProperty email = new SimpleStringProperty(emailToRemove);
        assertTrue(userToRemove.emailProperty().equals(email));
    }

    public void testAccessLevelProperty()
    {
        StringProperty accessLevel = new SimpleStringProperty(securityToRemove);
        assertTrue(userToRemove.accessLevelProperty().equals(accessLevel));
    }

    public void testSetUsername()
    {
        userToRemove.setUsername("Zelda");
        assertTrue(userToRemove.GetUsername().equals("Zelda"));
    }

    public void testSetLastName()
    {
        userToRemove.setLastName("Hyrule");
        assertTrue(userToRemove.GetLastName().equals("Hyrule"));
    }

    public void testSetFirstName()
    {
        userToRemove.setFirstName("Epona");
        assertTrue(userToRemove.GetFirstName().equals("Epona"));
    }

    public void testSetEmail()
    {
        userToRemove.setEmail("hyrule@warriors.com");
        assertTrue(userToRemove.GetFirstName().equals("hyrule@warriors.com"));
    }

    public void testSetAccessLevel()
    {
        userToRemove.setAccessLevel("1");
        assertTrue(userToRemove.GetAccessLevel().equals("1"));
    }
}
