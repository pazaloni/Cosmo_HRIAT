package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonalCare
{
    private String supports;
    private String totalAsOf;
    private String totalForLastYear;
    
    public PersonalCare(String supports, String totalAsOf, String totalForLastYear)
    {
        this.supports = supports;
        this.totalAsOf = totalAsOf;
        this.totalForLastYear = totalForLastYear;
    }
    
    public String getSupports()
    {
        return supports;
    }
    
    public StringProperty getSupportsProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(supports);
        return prop;
    }
    public String getTotalAsOf()
    {
        return totalAsOf;
    }
    
    public StringProperty getTotalAsOfProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(totalAsOf);
        return prop;
    }
    
    public String getTotalForLastYear()
    {
        return totalForLastYear;
    }
    
    public StringProperty getTotalForLastYearProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(totalForLastYear);
        return prop;
    }
    
}
