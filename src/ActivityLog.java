import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActivityLog
{
    private StringProperty who;
    private StringProperty when;
    private StringProperty event;

    public ActivityLog(String who, String when, String event)
    {
        this.who = new SimpleStringProperty(who);
        this.when = new SimpleStringProperty(when);
        this.event = new SimpleStringProperty(event);
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
    
    
}
