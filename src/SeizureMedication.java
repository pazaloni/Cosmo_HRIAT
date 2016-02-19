import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 *  Purpose: to store an object for every row in the seizure medication table
 * @author cst205, cst207
 * @version 1.0
 */
public class SeizureMedication
{
    private StringProperty medicationName;
    private StringProperty dosage;
    private StringProperty timesGiven;
    
    public SeizureMedication(String medicationName, 
            String dosage, String timesGiven)
    {
        this.medicationName = new SimpleStringProperty(medicationName);
        this.dosage = new SimpleStringProperty(dosage);
        this.timesGiven = new SimpleStringProperty(timesGiven);
                
    }    

    /**
     * 
     * Purpose: return the Medication name
     * @return
     */
    public StringProperty getMedicationName()
    {
        return medicationName;
    }

    /**
     * 
     * Purpose: return times given
     * @return
     */
    public StringProperty getTimesGiven()
    {
        return timesGiven;
    }

    /**
     * 
     * Purpose: return the dosage
     * @return
     */
    public StringProperty getDosage()
    {
        return dosage;
    }
}
