import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicalCondition
{

    private StringProperty condition;
    private StringProperty descripion;

    public MedicalCondition(String condition, String description)
    {
        this.condition = new SimpleStringProperty(condition);
        this.descripion = new SimpleStringProperty(description);
  
    }    
    /**
     * 
     * Purpose: return the condition 
     * @return
     */
    public StringProperty getCondition()
    {
        return condition;
    }

    /**
     * 
     * Purpose: return the description of that condition 
     * @return
     */
    public StringProperty getDescripion()
    {
        return descripion;
    }
}