import java.text.SimpleDateFormat;
import java.util.Date;


public class Participant
{
    //TODO add all attributes pls
    
    private int cosmoID;
    private String participantName;
    private Date informationLastUpdated;
    private String participantAddress; //TODO change to address object
    private String emergencyContactName; 
    private String emergencyContactPhone;
    
    
    //TODO very bad constructor
    /**
     * 
     * Constructor for the Participant class.
     * @param cosmoID
     * @param participantName
     * @param informationLastUpdated
     * @param participantAddress
     * @param emergencyContactName
     * @param emergencyContactPhone
     */
    public Participant(int cosmoID, String participantName, String participantAddress, 
            String emergencyContactName, String emergencyContactPhone, Date informationLastUpdated)
            {
                this.cosmoID = cosmoID;
                this.participantName = participantName;
                this.informationLastUpdated = informationLastUpdated;
                this.participantAddress = participantAddress;
                this.emergencyContactName = emergencyContactName;
                this.emergencyContactPhone = emergencyContactPhone;
        
        
            }

    //shane's nightmare
    public int getCosmoID()
    {
        return cosmoID;
    }

    public String getParticipantName()
    {
        return participantName;
    }

    public String getInformationLastUpdated()
    {
       SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY hh:mm a");
              
       return format.format(informationLastUpdated);
    }

    public String getParticipantAddress()
    {
        return participantAddress;
    }

    public String getEmergencyContactName()
    {
        return emergencyContactName;
    }

    public String getEmergencyContactPhone()
    {
        return emergencyContactPhone;
    }
    
    
    
}
