import java.sql.ResultSet;
import java.sql.SQLException;

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
     * @param cosmoId particiapnts id
     * @return array containg the cosmoId
     */
    public String[] retrieveHealthStatusInfo( String cosmoId )
    {
        String[] healthStatusInfo = new String[5];

        String physicianId = null;
        String diagnosis = null;
        boolean tylenol = false;
        boolean permission = false;

        ResultSet participantQuery = db
                .select("physicianID, diagnosis,tylenolPermission,careGiverPermissionGive",
                        "Participant", "cosmoID=" + cosmoId, "");

        try
        {
            while ( participantQuery.next() )
            {
                physicianId = participantQuery.getString(1);
                diagnosis = participantQuery.getString(2);
                tylenol = participantQuery.getBoolean(3);
                permission = participantQuery.getBoolean(4);
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
        System.out.println(permission);
        healthStatusInfo[4] = "" + permission;

        return healthStatusInfo;
    }
}