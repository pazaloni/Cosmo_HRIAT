package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReactiveCare 
{
	private int year;
	private int participants;
	private int staff;
	private int total;
	public ReactiveCare(int year, int participants, int staff)
	{
		this.year = year;
		this.participants = participants;
		this.staff = staff;
		total = participants + staff;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public StringProperty getYearProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + year);
		return prop;
	}
	
	public int getParticipants()
	{
		return participants;
	}
	
	public StringProperty getParticipantsProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + participants);
		return prop;
	}
	
	public int getStaff()
	{
		return staff;
	}
	
	public StringProperty getStaffProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + staff);
		return prop;
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public StringProperty getTotalProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + total);
		return prop;
	}
}
