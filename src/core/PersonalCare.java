package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The data holder object for the Personal Care Tableview for the Quarterly
 * Reports preview page.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class PersonalCare
{
    private String type;
    private String supports;
    private String totalAsOf;
    private String totalForLastYear;

    /**
     * Initializes the properties for supports, total persons, and the total
     * persons for the year prior.
     * 
     * @param supports The support provided for participants
     * @param totalAsOf The total supports used up to a date.
     * @param totalForLastYear The total supports for the year before.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public PersonalCare(String type, String supports, String totalAsOf)
    {
        this.type = type;
        this.supports = supports;
        this.totalAsOf = totalAsOf;
        this.totalForLastYear = "";
    }

    public String getType()
    {
        return type;
    }

    public StringProperty getTypeProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(type);
        return prop;
    }

    /**
     * Returns the support listed.
     * 
     * @return The support listed.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public String getSupports()
    {
        return supports;
    }

    /**
     * Returns the string property for the support listed.
     * 
     * @return The string property for the support listed
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public StringProperty getSupportsProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(supports);
        return prop;
    }

    /**
     * Returns the total number of this support used as of the date entered.
     * 
     * @return The total number of this support used as of the date entered.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public String getTotalAsOf()
    {
        return totalAsOf;
    }

    /**
     * Returns the string property of the support used as of the date entered.
     * 
     * @return The string property of the support used as of the date entered.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public StringProperty getTotalAsOfProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(totalAsOf);
        return prop;
    }

    /**
     * Returns the total amount of the support used last year.
     * 
     * @return The total amount of the support used last year.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public String getTotalForLastYear()
    {
        return totalForLastYear;
    }

    /**
     * Returns the string property for the amount of the support used last year.
     * 
     * @return The string property for the amount of the support used last year.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public StringProperty getTotalForLastYearProperty()
    {
        StringProperty prop = new SimpleStringProperty();
        prop.setValue(totalForLastYear);
        return prop;
    }

}
