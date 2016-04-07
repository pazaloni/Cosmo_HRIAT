package core;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: Represent a participant within the system, store all the information
 * pertaining the participant in the system
 * 
 * @author TEAM CIMP
 * @version 1.0
 */
public class Physician
{
    // TODO COMMENT
    private StringProperty physicianID;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty phone;

    /**
     * 
     * Constructor for the Physician class.
     * 
     */
    public Physician(String physicianID, String firstName, String lastName,
            String phone)
    {
        this.physicianID = new SimpleStringProperty(physicianID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);

    }

    /**
     * 
     * Purpose: To return the cosmoID string
     * 
     * @return cosmoID String
     */
    public String getPhysID()
    {
        return physicianID.get();
    }

    /**
     * 
     * Purpose: To return the cosmoID StringProperty
     * 
     * @return cosmoID StringProperty
     */
    public StringProperty getCosmoIDProperty()
    {
        return physicianID;
    }

    /**
     * 
     * Purpose: To return the participant name String
     * 
     * @return participantName String
     */
    public String getFirstName()
    {
        return firstName.get();
    }

    /**
     * 
     * Purpose: To return the participant name String
     * 
     * @return participantName String
     */
    public String getLastName()
    {
        return lastName.get();
    }

    /**
     * 
     * Purpose: To return the participantName StringProperty
     * 
     * @return participantName StringProperty
     */
    public StringProperty getFirstNameProperty()
    {
        return firstName;
    }

    /**
     * 
     * Purpose: To return the participantName StringProperty
     * 
     * @return participantName StringProperty
     */
    public StringProperty getLastNameProperty()
    {
        return lastName;
    }

    /**
     * 
     * Purpose: To return the informationLastUpdated StringProperty
     * 
     * @return informationLastUpdated StringProperty
     */
    public String getPhysPhone()
    {
        return phone.get();
    }

    /**
     * 
     * Purpose: To return the informationLastUpdated StringProperty
     * 
     * @return informationLastUpdated StringProperty
     */
    public StringProperty getPhysPhoneProperty()
    {
        return phone;
    }

}
