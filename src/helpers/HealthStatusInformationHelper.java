package helpers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Purpose: Retrieve the health status information for the specified
 * participant. Add, delete and update medical conditions, medications, and
 * allergies for the specified participant
 * 
 * @author CIMP
 * @version 1.0
 */
public class HealthStatusInformationHelper
{

    private DatabaseHelper db;

    public HealthStatusInformationHelper()
    {
        db = new DatabaseHelper();
    }

    /**
     * 
     * Purpose: search the database and find the information pertaining to a
     * participant
     * 
     * @param cosmoId participants id
     * @return array containing the health status information for the
     *         participant
     */
    public String[] retrieveHealthStatusInfo( String cosmoId )
    {
        db.connect();
        String[] healthStatusInfo = new String[7];

        String physicianId = null;
        String diagnosis = null;
        boolean tylenol = false;
        boolean permission = false;
        String lastUpdated = null;
        String otherInfo = null;
        ResultSet participantQuery = db
                .select("physicianID, diagnosis,tylenolPermission,careGiverPermissionGive, healthStatusUpdated, otherInfo",
                        "Participant", "cosmoID=" + cosmoId, "");

        try
        {
            while ( participantQuery.next() )
            {
                physicianId = participantQuery.getString(1);
                diagnosis = participantQuery.getString(2);
                tylenol = participantQuery.getBoolean(3);
                permission = participantQuery.getBoolean(4);
                lastUpdated = participantQuery.getString(5);
                otherInfo = participantQuery.getString(6);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out.println("Failed to query paritipant's physicianID");
        }

        ResultSet rs = db.select("firstName, lastName, phone", "Physician",
                "physicianID=" + physicianId, "");

        String phyisicanName = null;
        String physicianPhone = null;
        try
        {
            while ( rs.next() )
            {
                phyisicanName = rs.getString(1) + " " + rs.getString(2);
                physicianPhone = rs.getString(3);
            }
        }
        catch ( SQLException e )
        {

            e.printStackTrace();
        }
        healthStatusInfo[0] = phyisicanName;
        healthStatusInfo[1] = physicianPhone;
        healthStatusInfo[2] = diagnosis;
        healthStatusInfo[3] = "" + tylenol;
        healthStatusInfo[4] = "" + permission;
        if ( lastUpdated == null )
        {
            lastUpdated = " ";
        }
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if ( lastUpdated.length() > 5 )
        {
            try
            {
                date = inputFormatter.parse(lastUpdated);
            }
            catch ( ParseException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            SimpleDateFormat outputFormatter = new SimpleDateFormat();
            outputFormatter.applyPattern("dd-MMM-yyyy");
            
            healthStatusInfo[5] = outputFormatter.format(date);
        }

        //healthStatusInfo[5] = lastUpdated.substring(0, lastUpdated.length()-9);
        if ( otherInfo == null )
        {
            otherInfo = " ";
        }
        healthStatusInfo[6] = otherInfo + " ";
        db.disconnect();
        return healthStatusInfo;
    }

    /**
     * Purpose: Save the health status info
     * 
     * @param info the info to save
     * @param cosmoID the participant to save the health status info to 
     * @return String containing the saved information
     */
    public boolean saveHealthStatusInfo(String[] info,String cosmoID)
    {
    	db.connect();
    	
    	String updateValues[][] = new String[6][2];
    	
    	updateValues[0][0] = "cosmoID";
        updateValues[1][0] = "diagnosis";
        updateValues[2][0] = "tylenolPermission";
        updateValues[3][0] = "careGiverPermissionGive";
        updateValues[4][0] = "healthStatusUpdated";
        updateValues[5][0] = "otherInfo";
        
        updateValues[0][1] = cosmoID;
        updateValues[1][1] = info[0];
        updateValues[2][1] = info[1];
        updateValues[3][1] = info[2];        
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("dd-MMM-yyyy");
        updateValues[4][1] = formatter.format(now);         
        updateValues[5][1] = info[3];
        
        boolean success = db.update(updateValues, "Participant", cosmoID);

    	db.disconnect();    	
    	return success;
    }
    
    
}