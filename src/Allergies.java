import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Allergies
{
    private StringProperty allergicTo;
    private StringProperty allergyType;
    private StringProperty description;
    

    public Allergies(String allergicTo, String allergyType, String description)
    {
        this.allergicTo = new SimpleStringProperty(allergicTo);
        this.allergyType = new SimpleStringProperty(allergyType);        
        this.description = new SimpleStringProperty(description);
    }

    /**
     * 
     * Purpose: return the name of the persons allergies
     * @return
     */    
    public StringProperty getAllergicTo()
    {
        return allergicTo;
    }

    /**
     * 
     * Purpose: return the dosage of the medication that the person is taking
     * @return
     */
    public StringProperty getAllergyType()
    {
        return allergyType;
    }

    /**
     * 
     * Purpose: return the times that the person is to receive the medication
     * @return
     */
    public StringProperty getDescription()
    {
        return description;
    }


    
}
