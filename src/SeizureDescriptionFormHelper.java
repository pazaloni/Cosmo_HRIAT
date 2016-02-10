
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
        String[] SeizureStatusInfo = new String[7];
        
        String seizureType = null;
        String description = null;
        String frequency = null;
        String duration = null;
        String aftermath = null;
        String emergencyTreatment = null;
        Date lastUpdated = new Date(); 
        
        String medicationName = null;
        String dosage = null;
        Date timesGiven = new Date();
        
        ResultSet participantQuery = db
                .select("seizureType, description, frequency, duration, "
                        + "aftermath, emergencyTreatment, seizureLastUpdated",
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
                emergencyTreatment = participantQuery.getString(7);
                lastUpdated = participantQuery.getDate(8);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out.println("Failed to query paritipant's seizure info");
        }
        
        ResultSet medicationQuery = db
                .select("medicationName, dosage, timesGiven",
                        "Medication", "cosmoID=" + cosmoId, " AND reason=seizure");
        
        try
        {
            while(medicationQuery.next())
            {
                medicationName = medicationQuery.getString(1);
                dosage = medicationQuery.getString(2);
                timesGiven = medicationQuery.getDate(3);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

        }
        
        
        
        
        
        return SeizureStatusInfo;
    }
}
