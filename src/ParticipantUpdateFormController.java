import java.sql.ResultSet;
import java.sql.SQLException;


public class ParticipantUpdateFormController 
{
	private int cosmoID;
	private DatabaseHelper db;
	
	public ParticipantUpdateFormController(int cosmoID)
	{
		this.cosmoID = cosmoID;
		db = new DatabaseHelper();
	}
	
	public String[] fetchParticipantBasicInfo()
	{
		db.connect();
		String[] participantInfo = new String[8];
		
		ResultSet rs = db.select("firstName, lastName, address, city,"
				+ " postalCode, phoneNum, dateOfBirth, socialInsuranceNumber"
				, "Participant", "cosmoID = " + cosmoID, "");
		
		try
        {
            while(rs.next())
            {
                participantInfo[0] = rs.getString(1);
                participantInfo[1] = rs.getString(2);
                participantInfo[2] = rs.getString(3);
                participantInfo[3] = rs.getString(4);
                participantInfo[4] = rs.getString(5);
                participantInfo[5] = rs.getString(6);
                participantInfo[6] = rs.getString(7);
                participantInfo[7] = rs.getString(8);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
				
		db.disconnect();
		return participantInfo;
	}
	
	public String[] fetchKinInfo()
	{
		db.connect();
	    String[] kinInfo = new String[7];
	    ResultSet rsKinID = db.select("kinID", "Participant", "cosmoID =" + cosmoID, "");
	    String kinID = "";
	    try
        {
            while(rsKinID.next())
            {
                kinID = rsKinID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    ResultSet rs = db.select("firstName, lastName, address, city, postalCode,"
	            + "homePhoneNumber, workPhoneNumber", "Kin", "kinID =" + kinID, "");
		try
        {
            while(rs.next())
            {
                kinInfo[0] = rs.getString(1);
                kinInfo[1] = rs.getString(2);
                kinInfo[2] = rs.getString(3);
                kinInfo[3] = rs.getString(4);
                kinInfo[4] = rs.getString(5);
                kinInfo[5] = rs.getString(6);
                kinInfo[6] = rs.getString(7);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    db.disconnect();
		return kinInfo;
	}
	
	public String[] fetchCaregiverInfo()
	{
		String[] caregiverInfo = new String[7];
		db.connect();
		ResultSet rsCGID = db.select("caregiverID", "Participant", "cosmoID =" + cosmoID, "");
        String cgID = "";
        try
        {
            while(rsCGID.next())
            {
                cgID = rsCGID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ResultSet rs = db.select("firstName, lastName, address, city, postalCode,"
                + "homePhoneNumber, workPhoneNumber", "Caregiver", 
                "caregiverID =" + cgID, "");
        try
        {
            while(rs.next())
            {
                caregiverInfo[0] = rs.getString(1);
                caregiverInfo[1] = rs.getString(2);
                caregiverInfo[2] = rs.getString(3);
                caregiverInfo[3] = rs.getString(4);
                caregiverInfo[4] = rs.getString(5);
                caregiverInfo[5] = rs.getString(6);
                caregiverInfo[6] = rs.getString(7);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		db.disconnect();
		return caregiverInfo;
	}
	
	public String[] fetchEmergencyContactInfo()
	{
		String[] emergencyContactInfo = new String[3];
		db.connect();
		ResultSet rsECID = db.select("emergencyContactID", "Participant", 
		        "cosmoID =" + cosmoID, "");
        String ecID = "";
        try
        {
            while(rsECID.next())
            {
                ecID = rsECID.getString(1);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ResultSet rs = db.select("firstName, lastName, phoneNumber", 
                "EmergencyContact", "emergencyContactID =" + ecID, "");
        try
        {
            while(rs.next())
            {
                emergencyContactInfo[0] = rs.getString(1);
                emergencyContactInfo[1] = rs.getString(2);
                emergencyContactInfo[2] = rs.getString(3);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		db.disconnect();
		return emergencyContactInfo;
	}
	
}
