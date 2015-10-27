
public abstract class StaffAccount {
	private int staffID;		//staff members unique login ID
	private String lastName;	//the staff members last name
	private String firstName;	//the staff members first name
	private String password;	//the staff members password
	
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
	public abstract StaffAccount login(int staffID, String password);
}
