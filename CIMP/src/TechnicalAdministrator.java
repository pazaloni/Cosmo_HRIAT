import java.util.ArrayList;


public class TechnicalAdministrator extends StaffAccount{	
	

	
	public TechnicalAdministrator(int staffID, String password)
	{
		this.staffID = staffID;
		this.password = password;
		//connect to database, get the staff account record
		
		//fetch the first and last name, and assign them to
		//the firstName and lastname variables
	}
	
	public boolean addAccount(String lastName, String firstName, String password)
	{
		return false;
	}
	
	public boolean removeAccount(int staffID)
	{
		return false;
	}
	
	public boolean resetPassword(int staffID, String newPassword)
	{
		return false;
	}


}
