

public abstract class StaffAccount {
	int staffID;		//staff members unique login ID
	private String lastName;	//the staff members last name
	private String firstName;	//the staff members first name
	String password;	//the staff members password
	
	/**
	 * Purpose: If the login information matches that of a staff account
	 * in the database, it will return an instance of that staff member's
	 * account, so their information and security level can be used
	 * to determine what they see. This method assumes that the information
	 * has already been validated.
	 * 
	 * @return staffAccount: the account of the user that has successfully
	 * logged in.
	 */
	public StaffAccount login(int staffID, String password)
	{
		return lookupAccount(staffID, password);
	}
	
	
	private StaffAccount lookupAccount(int staffID, String password)
	{
		//the security level, determines what class type it will be
		//0 = basic staff
		//1 = medical admin
		//2 = tech admin
		int securityLevel = 0;
		StaffAccount account = null;
		switch(securityLevel)
		{
			case 0:
			{
				account = new BasicStaff(staffID, password);
				break;
			}
			case 1:
			{
				account=  new MedicalAdministrator(staffID, password);
				break;
			}
			case 2:
			{
				account = new TechnicalAdministrator(staffID, password);
				break;
			}
		}
		
		return account;
	}
	
	/**
	 * Purpose: Allows the user to logout of the system. moves them to the
	 * loginGUI, with all of the account info reset in memory.
	 * @return if the reset of information and sending user back to loginGUI
	 * is successful, return true, else, false
	 */
	public boolean logout()
	{
		return false;
	}
}
