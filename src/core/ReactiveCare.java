package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data Holder object for the reactive care information for the Quarterly Reports.
 * Represents a row of data for the Reactive Care TableView.
 * 
 *  @author Breanna Wilson, Jon Froese
 *
 */

public class ReactiveCare 
{
	private int year;
	private int participants;
	private int staff;
	private int total;
	
	/**
	 * Initializes the values for the year, participants, staff,
	 * and total properties. Total is calculated from the number
	 * of staff members and participants.
	 * @param year The year the data is from
	 * @param participants The number of participants involved
	 * @param staff The number of staff members involved
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public ReactiveCare(int year, int participants, int staff)
	{
		this.year = year;
		this.participants = participants;
		this.staff = staff;
		total = participants + staff;
	}
	
	/**
	 * Returns the year the data is from.
	 * @return The year the data is from.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * Returns the string property for the year 
	 * the data is from.
	 * @return The string property for the year
	 * the data is from.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getYearProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + year);
		return prop;
	}
	
	/**
	 * Returns the number of participants involved.
	 * @return The number of participants involved.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public int getParticipants()
	{
		return participants;
	}
	/**
	 * Returns the string property for the number
	 * of participants involved
	 * @return The string property for the number
	 * of participants involved
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	
	public StringProperty getParticipantsProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + participants);
		return prop;
	}
	
	/**
	 * Returns the number of staff members involved
	 * @return The number of staff members involved
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public int getStaff()
	{
		return staff;
	}
	
	/**
	 * Returns the string property for the number
	 * of staff members involved
	 * @return The string property for the number
	 * of staff members involved
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getStaffProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + staff);
		return prop;
	}
	
	/**
	 * Returns the total number of staff members
	 * and participants involved.
	 * @return The total number of staff members 
	 * and participants involved.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public int getTotal()
	{
		return total;
	}
	
	/**
	 * Returns the string property for the total number
	 * of staff members and participants involved.
	 * @return The string property for the total number
	 * of staff members and participants involved.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getTotalProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + total);
		return prop;
	}
}
