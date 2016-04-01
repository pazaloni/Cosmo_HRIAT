package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data-holder class for the OlderAdultNeedsController. This is used
 * to hold data to put into the Older Adult Needs TableView in the
 * QuarterlyReportsGUI.
 *  @author Breanna Wilson, Jon Froese
 *
 */

public class OlderAdultsNeeds 
{
	private String age;
	private int longTermCare;
	private int elmwood;
	private int lutherCare;
	private int other;
	private int totalAsOf;
	private int totalForLastYear;
	
	/**
	 * Initializes the data values to be put into the TableView.
	 * @param age The age group of participants
	 * @param longTermCare The number of participants in the age group to be in
	 *  a long term care facility.
	 * @param elmwood The number of participants in the age group to be living
	 * 	at elmwood.
	 * @param lutherCare The number of participants in the age group to be living
	 *  at luther care.
	 * @param other The number of participants in the age group living elsewhere.
	 * @param totalAsOf The total participants in this age group as of the current date.
	 * @param totalForLastYear The total participants in this age group last year
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public OlderAdultsNeeds(String age, int longTermCare, int elmwood, int lutherCare, int other,
			int totalAsOf, int totalForLastYear)
	{
		this.age = age;
		this.longTermCare = longTermCare;
		this.elmwood = elmwood;
		this.lutherCare = lutherCare;
		this.other = other;
		this.totalAsOf = totalAsOf;
		this.totalForLastYear = totalForLastYear;
	}
	
	/**
	 * Returns the age group.
	 * @return The age group.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public String getAge()
	{
		return age;
	}
	
	/**
	 * Returns a string property for the age group.
	 * @return The age group string property
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getAgeProperty()
	{
		StringProperty ageProp = new SimpleStringProperty();
		ageProp.setValue(age);
		return ageProp;
	}
	
	/**
	 * Returns the number of participants in long term care.
	 * @return Number of participants in long term care.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getLongTermCare()
	{
		return longTermCare;
	}
	
	/**
	 * Returns a string property of participants in long term
	 * care.
	 * @return The string property for participants in long term care.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getLongTermCareProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + longTermCare);
		return prop;
	}
	
	/**
	 * Returns the number of participants living at elmwood.
	 * @return The number of participants living at elmwood.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getElmwood()
	{
		return elmwood;
	}
	
	/**
	 * Returns the string property for the number
	 * of participants living at elmwood
	 * @return The string property for the number
	 * of participants living at elmwood
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getElmwoodProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + elmwood);
		return prop;
	}
	
	/**
	 * Returns the number of participants in luther
	 * care.
	 * @return The number of participants in luther
	 * care.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getLutherCare()
	{
		return lutherCare;
	}
	
	/**
	 * Returns the string property for the number
	 * of participants in luther care.
	 * @return The string property for the number
	 * of participants in luther care.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getLutherCareProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + lutherCare);
		return prop;
	}
	
	/**
	 * Returns the number of participants living elsewhere.
	 * @return The number of participants living elsewhere.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getOther()
	{
		return other;
	}
	
	/**
	 * Returns the string property for the number of
	 * participants living elsewhere
	 * @return The string property for the number of
	 * participants living elsewhere.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getOtherProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + other);
		return prop;
	}
	
	/**
	 * Returns the total number of participants as of
	 * the current date.
	 * @return The total number of participants as of
	 * the current date.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getTotalAsOf()
	{
		return totalAsOf;
	}
	
	/**
	 * Returns the string property for the total number
	 * of participants as of the current date.
	 * @return The string property for the total number
	 * of participants as of the current date.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public StringProperty getTotalAsOfProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + totalAsOf);
		return prop;
	}
	
	/**
	 * Returns the total number of participants for last year.
	 * @return The total number of participants for last year.
	 * 
	 *  @author Breanna Wilson, Jon Froese
	 */
	public int getTotalForLastYear()
	{
		return totalForLastYear;
	}
	
	/**
	 * Returns the string property for the total number of
	 * participants for last year.
	 * @return The string property for the total number of
	 * participants for last year.
	 */
	public StringProperty getTotalForLastYearProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.set("" + totalForLastYear);
		return prop;
	}
}
