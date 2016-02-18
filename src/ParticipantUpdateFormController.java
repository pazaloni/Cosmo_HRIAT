
public class ParticipantUpdateFormController 
{
	private int cosmoID;
	
	public ParticipantUpdateFormController(int cosmoID)
	{
		this.cosmoID = cosmoID;
	}
	
	public String[] fetchParticipantBasicInfo()
	{
		String[] participantInfo = new String[8];
		
		return participantInfo;
	}
	
	public String[] fetchKinInfo()
	{
		String[] kinInfo = new String[6];
		
		return kinInfo;
	}
	
	public String[] fetchCaregiverInfo()
	{
		String[] caregiverInfo = new String[6];
		
		return caregiverInfo;
	}
	
	public String[] fetchEmergencyContactInfo()
	{
		String[] emergencyContactInfo = new String[2];
		
		return emergencyContactInfo;
	}
	
	public void setParticipantBasicInfo(String[] participantInfo)
	{
		
	}
	
	public void setKinInfo(String[] kinInfo)
	{
		
	}
	
	public void setCaregiverInfo(String[] caregiverInfo)
	{
		
	}
	
	public void setEmergencyContactInfo(String[] emergencyContactInfo)
	{
		
	}
}
