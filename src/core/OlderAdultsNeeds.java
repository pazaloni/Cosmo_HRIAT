package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OlderAdultsNeeds 
{
	private String age;
	private int longTermCare;
	private int elmwood;
	private int lutherCare;
	private int other;
	private int totalAsOf;
	private int totalForLastYear;
	
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
	
	public String getAge()
	{
		return age;
	}
	
	public StringProperty getAgeProperty()
	{
		StringProperty ageProp = new SimpleStringProperty();
		ageProp.setValue(age);
		return ageProp;
	}
	
	public int getLongTermCare()
	{
		return longTermCare;
	}
	
	public StringProperty getLongTermCareProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + longTermCare);
		return prop;
	}
	
	public int getElmwood()
	{
		return elmwood;
	}
	
	public StringProperty getElmwoodProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + elmwood);
		return prop;
	}
	
	public int getLutherCare()
	{
		return lutherCare;
	}
	
	public StringProperty getLutherCareProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + lutherCare);
		return prop;
	}
	
	public int getOther()
	{
		return other;
	}
	
	public StringProperty getOtherProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + other);
		return prop;
	}
	
	public int getTotalAsOf()
	{
		return totalAsOf;
	}
	
	public StringProperty getTotalAsOfProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.setValue("" + totalAsOf);
		return prop;
	}
	
	public int getTotalForLastYear()
	{
		return totalForLastYear;
	}
	
	public StringProperty getTotalForLastYearProperty()
	{
		StringProperty prop = new SimpleStringProperty();
		prop.set("" + totalForLastYear);
		return prop;
	}
}
