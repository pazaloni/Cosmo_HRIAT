package core;
import helpers.FormatHelper;

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
public class Participant
{
    // TODO add all attributes
    private StringProperty cosmoID;
    private StringProperty participantName;
    private StringProperty informationLastUpdated;
    private StringProperty participantAddress; // TODO change to address object
    private StringProperty emergencyContactName;
    private StringProperty emergencyContactPhone;
    ///the status attribute
    private StringProperty participantStatus;

    /**
     * 
     * Constructor for the Participant class.
     * 
     * @param cosmoID
     * @param participantName
     * @param informationLastUpdated
     * @param participantAddress
     * @param emergencyContactName
     * @param emergencyContactPhone
     * @param participantStatus
     */
    public Participant(String cosmoID, String participantName,
            String participantAddress, String emergencyContactName,
            String emergencyContactPhone, String informationLastUpdated, 
            String participantStatus)
    {
        this.cosmoID = new SimpleStringProperty(cosmoID);
        this.participantName = new SimpleStringProperty(participantName);
        this.informationLastUpdated = new SimpleStringProperty(""
                + informationLastUpdated);
        this.participantAddress = new SimpleStringProperty(""
                + participantAddress);
        this.emergencyContactName = new SimpleStringProperty(""
                + emergencyContactName);
        this.emergencyContactPhone = new SimpleStringProperty(""
                + emergencyContactPhone);
        this.informationLastUpdated = new SimpleStringProperty("" 
                + informationLastUpdated);
        this.participantStatus = new SimpleStringProperty(""
                + participantStatus);
    }

    /**
     * 
     * Purpose: To return the cosmoID string
     * 
     * @return cosmoID String
     */
    public String getCosmoID()
    {
        return cosmoID.get();
    }

    /**
     * 
     * Purpose: To return the cosmoID StringProperty
     * 
     * @return cosmoID StringProperty
     */
    public StringProperty getCosmoIDProperty()
    {
        return cosmoID;
    }

    /**
     * 
     * Purpose: To return the participant name String
     * 
     * @return participantName String
     */
    public String getParticipantName()
    {
        return participantName.get();
    }

    /**
     * 
     * Purpose: To return the participantName StringProperty
     * 
     * @return participantName StringProperty
     */
    public StringProperty getParticipantNameProperty()
    {
        return participantName;
    }

    /**
     * 
     * Purpose: To return the informationLastUpdated String
     * 
     * @return informationLastUpdated String
     */
    public String getInformationLastUpdated()
    {
        informationLastUpdated.set(informationLastUpdated.get()
                .substring(0, 11));
        return informationLastUpdated.get();
    }

    /**
     * 
     * Purpose: To return the informationLastUpdated StringProperty
     * 
     * @return informationLastUpdated StringProperty
     */
    public StringProperty getUpdatedProperty()
    {
        informationLastUpdated.set(informationLastUpdated.get()
                .substring(0, 11));
        return informationLastUpdated;
    }

    /**
     * 
     * Purpose: To return the participantAddress String
     * 
     * @return participantAddress String
     */
    public String getParticipantAddress()
    {
        return participantAddress.get();
    }

    /**
     * 
     * Purpose: To return the participantAddress StringProperty
     * 
     * @return participantAddress StringProperty
     */
    public StringProperty getAddressProperty()
    {
        return participantAddress;
    }

    /**
     * 
     * Purpose: To return the emergencyContactName String
     * 
     * @return emergencyContactname String
     */
    public String getEmergencyContactName()
    {
        return emergencyContactName.get();
    }

    /**
     * 
     * Purpose: To return the emergencyContactName String Property
     * 
     * @return emergencyContactName StringProperty
     */
    public StringProperty getEmergencyContactProperty()
    {
        return emergencyContactName;
    }

    /**
     * 
     * Purpose: To return the emergencyContactPhone String
     * 
     * @return emergencyContactPhone String
     */
    public String getEmergencyContactPhone()
    {
        return emergencyContactPhone.get();
    }

    /**
     * 
     * Purpose: getEmergencyContactPhone String Property
     * 
     * @return emergencyContactPhone StringProperty
     */
    public StringProperty getEmergencyContactPhoneProperty()
    {
        return emergencyContactPhone;
    }

    public StringProperty displayEmergencyContactPhoneProperty()
    {
        
        FormatHelper fh = new FormatHelper();
        
        String formatedPhone = fh.formatPhoneNum(emergencyContactPhone.get());
        
        if (formatedPhone.equals("A phone number must have 10 digits."))
        {
            formatedPhone = " ";
        }

         StringProperty displayPhone = new SimpleStringProperty(formatedPhone);
        return displayPhone;
    }

    
    public String getStatus()
    {
        return participantStatus.get();
    }
    
    /**
     * Purpose: to return the status StringProperty
     * 
     * @return
     */
    public StringProperty getStatusProperty()
    {
        return participantStatus;
    }
}
