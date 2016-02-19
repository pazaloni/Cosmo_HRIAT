import java.sql.ResultSet;


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
		String[] participantInfo = new String[9];
		
		ResultSet rs = db.select("firstName, lastName, city,"
				+ " postalCode, phoneNum, dateOfBirth, personalHealthNumber"
				, "Participant", "cosmoID = " + cosmoID, "");
		
		
		
		
		db.disconnect();
		return participantInfo;
	}
	
	public String[] fetchKinInfo()
	{
		String[] kinInfo = new String[7];
		
		return kinInfo;
	}
	
	public String[] fetchCaregiverInfo()
	{
		String[] caregiverInfo = new String[7];
		
		return caregiverInfo;
	}
	
	public String[] fetchEmergencyContactInfo()
	{
		String[] emergencyContactInfo = new String[2];
		
		return emergencyContactInfo;
	}
	
}
