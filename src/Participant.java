import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: Represent a participant within the system, store all the information
 * pertaining the participanti in the system
 * 
 * @author TEAM CIMP
 * @version 1.0
 */
public class Participant
{
    // TODO add all attributes pls

    private StringProperty cosmoID;
    private StringProperty participantName;
    private StringProperty informationLastUpdated;
    private StringProperty participantAddress; // TODO change to address object
    private StringProperty emergencyContactName;
    private StringProperty emergencyContactPhone;

    // TODO very bad constructor
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
     */
    public Participant(int cosmoID, String participantName,
            String participantAddress, String emergencyContactName,
            String emergencyContactPhone, Date informationLastUpdated)
    {
        this.cosmoID.set(""+cosmoID);
        this.participantName.set(participantName);
        this.informationLastUpdated.set(""+informationLastUpdated);
        this.participantAddress.set(""+participantAddress);
        this.emergencyContactName.set(""+emergencyContactName);
        this.emergencyContactPhone.set(""+emergencyContactPhone);

    }

    // shane's nightmare
    public String getCosmoID()
    {
        return cosmoID.get();
    }

    public StringProperty getCosmoIDProperty()
    {
        return cosmoID;
    }
    
    public String getParticipantName()
    {
        return participantName.get();
    }

    public StringProperty getParticipantNameProperty()
    {
        return participantName;
    }
    
    public String getInformationLastUpdated()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY hh:mm a");

        return format.format(informationLastUpdated);
    }

    public StringProperty getUpdatedProperty()
    {
        return informationLastUpdated;
    }
    
    public String getParticipantAddress()
    {
        return participantAddress.get();
    }

    public StringProperty getAddressProperty()
    {
        return participantAddress;
    }
    
    public String getEmergencyContactName()
    {
        return emergencyContactName.get();
    }

    public StringProperty getEmergencyContactProperty()
    {
        return emergencyContactName;
    }
    
    public String getEmergencyContactPhone()
    {
        return emergencyContactPhone.get();
    }
    
    public StringProperty getEmergencyContactPhoneProperty()
    {
        return emergencyContactPhone;
    }

}
