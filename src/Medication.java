import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * Purpose: represents the medication that one pariticipant can be getting 
 * 
 *
 * @author CIMP
 * @version 1.0
 */
public class Medication
{
    private StringProperty medicationName;
    private StringProperty dosage;
    private StringProperty timesGiven;
    private StringProperty reason;

    public Medication(String medicationName, String dosage, String timesGiven, String reason)
    {
        
        this.medicationName = new SimpleStringProperty(medicationName);
        this.dosage = new SimpleStringProperty(dosage);
        this.timesGiven = new SimpleStringProperty(timesGiven);
        this.reason = new SimpleStringProperty(reason);
    }

    /**
     * 
     * Purpose: return the name of the medication that the person is taking 
     * @return
     */    
    public StringProperty getMedicationName()
    {
        return medicationName;
    }

    /**
     * 
     * Purpose: return the dosage of the medication that the person is taking
     * @return
     */
    public StringProperty getDosage()
    {
        return dosage;
    }

    /**
     * 
     * Purpose: return the times that the person is to receive the medication
     * @return
     */
    public StringProperty getTimesGiven()
    {
        return timesGiven;
    }

    /**
     * 
     * Purpose: return why the person is receiving medication
     * @return
     */
    public StringProperty getReason()
    {
        return reason;
    }
    
}
