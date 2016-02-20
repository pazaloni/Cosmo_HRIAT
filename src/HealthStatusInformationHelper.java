import java.sql.ResultSet;
import java.sql.SQLException;

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
        healthStatusInfo[5] = lastUpdated;
        if ( otherInfo == null )
        {
            otherInfo = " ";
        }
        healthStatusInfo[6] = otherInfo + " ";

        return healthStatusInfo;
    }

    /**
     * 
     * Purpose: Add a medical condition for the specified participant.
     * 
     * @param cosmoID the participant will have the condition added.
     * @param md the medical condition for the participant.
     * @return true if the new medical condition was added, false otherwise.
     */
    public boolean addMedicalCondition( String cosmoID, MedicalCondition md )
    {
        String[] newMedicalCondition = new String[3];
        newMedicalCondition[0] = md.getCondition().get();
        newMedicalCondition[1] = cosmoID;
        newMedicalCondition[2] = md.getDescripion().get();
        boolean result = db.insert(newMedicalCondition, "Condition");
        if ( result )
        {
            System.err.println("Added medical condition");
        }
        else
        {
            System.err.println("Made an oopsie");
        }
        return result;
    }

    /**
     * 
     * Purpose: delete a medical condition for the specified participant.
     * 
     * @param cosmoID the participant that will have the condition removed.
     * @param md the medical condition that will be removed.
     * @return true if the medical condition was deleted, false otherwise.
     */
    public boolean deleteMedicalCondition( String cosmoID, MedicalCondition md )
    {
        String[] deleteMedicalCondition = new String[3];
        deleteMedicalCondition[0] = md.getCondition().get();
        deleteMedicalCondition[1] = cosmoID;
        deleteMedicalCondition[2] = md.getDescripion().get();
        boolean result = db.delete("Condition", "((Condition.conditionName)='"
                + md.getCondition() + "') AND ((Condition.cosmoID)='" + cosmoID
                + "'));");
        if ( result )
        {
            System.err.println("Added medical condition");
        }
        else
        {
            System.err.println("Made an oopsie");
        }
        return result;
    }

    /**
     * 
     * Purpose: Update a medical condition for the specified participant.
     * 
     * @param cosmoID the participant that will have the condition updated.
     * @param md the medical condition to change.
     * @return true if the medical condition was updated, false otherwise.
     */
    public boolean updateMedicalConditions( String cosmoID, MedicalCondition md )
    {
        boolean result = false;
        // TODO change database to be able to change the medical conditions

        return result;
    }

    /**
     * 
     * Purpose: Add an allergy for the specified participant.
     * 
     * @param cosmoID the participant will have the allergy added.
     * @param allergy the allergy for the participant.
     * @return true if the new allergy was added, false otherwise.
     */
    public boolean addAllergy( String cosmoID, Allergies allergy )
    {
        boolean result = false;

        return result;
    }

    /**
     * 
     * Purpose: Delete an allergy for the specified participant.
     * 
     * @param cosmoID the participant that will have the allergy removed.
     * @param allergy the allergy that will be removed.
     * @return true if the allergy was deleted, false otherwise.
     */
    public boolean deleteAllergy( String cosmoID, Allergies allergy )
    {
        boolean result = false;

        return result;
    }

    /**
     * 
     * Purpose: Update an allergy for the specified participant.
     * 
     * @param cosmoID the participant that will have the allergy updated.
     * @param allergy the allergy to change
     * @return true if the allergy was updated, false otherwise.
     */
    public boolean updateAllergy( String cosmoID, Allergies allergy )
    {
        boolean result = false;

        return result;
    }

    /**
     * 
     * Purpose: Add a medication entry for the specified participant.
     * 
     * @param cosmoID the participant that will have the medication added.
     * @param medicaiton the medication for the participant.
     * @return true if the new medication was added, false otherwise.
     */
    public boolean addMedication( String cosmoID, Medication medicaiton )
    {
        boolean result = false;

        return result;
    }

    /**
     * 
     * Purpose: Delete a medication entry for the specified participant.
     * 
     * @param cosmoID the participant that will have the medication removed.
     * @param medicaiton the medication that will be removed.
     * @return true if the medication was deleted, false otherwise
     */
    public boolean deleteMedication( String cosmoID, Medication medicaiton )
    {
        boolean result = false;

        return result;
    }

    /**
     * 
     * Purpose: Updated a medication entry for the specified participant.
     * 
     * @param cosmoID the participant that will have the medication updated.
     * @param medication the medication to change
     * @return true if the medication was updated, false otherwise.
     */
    public boolean updateMedication( String cosmoID, Medication medicaiton )
    {
        boolean result = false;

        return result;
    }

}
