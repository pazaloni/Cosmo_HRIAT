package core;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActivityLog
{
    private StringProperty who;
    private StringProperty when;
    private StringProperty event;
    private StringProperty details;

    public ActivityLog(String who, String when, String event, String details)
    {
        this.who = new SimpleStringProperty(who);
        this.when = new SimpleStringProperty(when);
        this.event = new SimpleStringProperty(event);
        this.details = new SimpleStringProperty(details);
    }

    /**
     * 
     * Purpose: return the person that triggered the event 
     * @return
     */    
    public StringProperty getWho()
    {
        return who;
    }

    /**
     * 
     * Purpose: return the time the event happened
     * @return
     */
    public StringProperty getWhen()
    {
        return when;
    }

    /**
     * 
     * Purpose: return the event that was triggered
     * @return
     */
    public StringProperty getEvent()
    {
        return event;
    }
    
    /**
     * 
     * Purpose: return the details for the event
     * @return
     */
    public StringProperty getDetails()
    {
        return details;
    }
    
    
}
