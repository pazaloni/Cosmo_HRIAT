
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SeizureDescriptionFormHelper
{
    private DatabaseHelper db;
    
    public SeizureDescriptionFormHelper()
    {
        db = new DatabaseHelper();
    }
    
    public String[] retieveSeizureInformation(String cosmoId)
    {
        String[] seizureStatusInfo = new String[8];
        
        String seizureType = null;
        String description = null;
        String frequency = null;
        String duration = null;
        String aftermath = null;
        String aftermathAssistance = null;
        String emergencyTreatment = null;
        Date lastUpdated = new Date(); 
        
        String medicationName = null;
        String dosage = null;
        Date timesGiven = new Date();
        
        ResultSet participantQuery = db
                .select("seizureType, description, frequency, duration, "
                        + "aftermath, aftermathAssistance, emergencyTreatment, seizuresLastUpdated",
                        "Seizures", "cosmoID=" + cosmoId, "");
        
        try
        {
            while(participantQuery.next())
            {
                seizureType = participantQuery.getString(1);
                description = participantQuery.getString(2);
                frequency = participantQuery.getString(3);
                duration = participantQuery.getString(4);
                aftermath = participantQuery.getString(5);
                aftermathAssistance = participantQuery.getString(6);
                emergencyTreatment = participantQuery.getString(7);
                lastUpdated = participantQuery.getDate(8);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out.println("Failed to query paritipant's seizure info");
        }        
        seizureStatusInfo[0] = seizureType;
        seizureStatusInfo[1] = description;
        seizureStatusInfo[2] = frequency;
        seizureStatusInfo[3] = duration;
        seizureStatusInfo[4] = aftermath;
        seizureStatusInfo[5] = aftermathAssistance;
        seizureStatusInfo[6] = emergencyTreatment;
        seizureStatusInfo[7] = lastUpdated.toString();
        
        for(int i = 0; i < seizureStatusInfo.length;i++)
        {
            if (seizureStatusInfo[i] == null)
            {
                seizureStatusInfo[i] = " ";
            }
        }
        
        return seizureStatusInfo;
    }
}
