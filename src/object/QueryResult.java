package object;
import helpers.DatabaseHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: Represent a medical condition in the database, also have the ability
 * to add, update, delete medical conditions
 * 
 * 
 * @author CIMP
 * @version 1.0
 */
public class QueryResult
{

    private StringProperty condition;
    private StringProperty description;

    public QueryResult(String condition, String description)
    {
        this.condition = new SimpleStringProperty(condition);
        this.description = new SimpleStringProperty(description);

    }

    /**
     * 
     * Purpose: return the condition
     * 
     * @return
     */
    public StringProperty getCondition()
    {
        return condition;
    }

    /**
     * 
     * Purpose: return the description of that condition
     * 
     * @return
     */
    public StringProperty getDescripion()
    {
        return description;
    }

    /**
     * 
     * Purpose: ADd a medical condition for a participant in the database
     * 
     * @param condition condition to add
     * @param cosmoID the participant that will be receiving the condition
     * @return The result of the insertion in the database
     */
    public static String createMedicalCondition( QueryResult condition,
            String cosmoID )
    {
        String result = "";

        if ( condition.condition.get().isEmpty()
                || condition.description.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;

            String medicalConditionValues[] = new String[3];

            medicalConditionValues[0] = condition.getCondition().get();
            medicalConditionValues[1] = cosmoID;
            medicalConditionValues[2] = condition.getDescripion().get();

            success = db.insert(medicalConditionValues, "Conditions");

            if ( !success )
            {
                result = "The insertion was not successful";
            }
            else
            {
                result = "";
            }

            db.disconnect();
        }
        return result;
    }

    /**
     * 
     * Purpose: Update a medical condition for a participant
     * 
     * @param newCondition the condition that will be new in the database
     * @param oldCondition the condition that will be changed
     * @param cosmoID the participant that will have the condition changed
     * @return a string containing the status of the update
     */
    public static String updateMedicalCondition( QueryResult newCondition,
            QueryResult oldCondition, String cosmoID )
    {
        String result = "";

        if ( newCondition.condition.get().isEmpty()
                || newCondition.description.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;
            String updateValues[][] = new String[2][2];
            updateValues[0][0] = "conditionName";
            updateValues[1][0] = "description";

            updateValues[0][1] = newCondition.getCondition().get();
            updateValues[1][1] = newCondition.getDescripion().get();

            success = db.update(updateValues, "Conditions", oldCondition
                    .getCondition().get() + "'AND cosmoID=" + "'" + cosmoID);

            if ( !success )
            {
                result = "Update not successful";
            }
            else
            {
                result = "";
            }
            db.disconnect();
        }

        return result;
    }

    /**
     * 
     * Purpose: Remove a condition for a participant from the database
     * 
     * @param condition the condition to remove
     * @param cosmoID the participant that will have the condition removed
     * @return a string containing the status of the deletion
     */
    public static String deleteCondition( QueryResult condition,
            String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        boolean success = false;
        success = db.delete("Conditions", "conditionName='"
                + condition.getCondition().get() + "'" + "AND cosmoID='"
                + cosmoID + "'");
        if ( success )
        {
            result = "Deleted successfully";
        }
        else
        {
            result = "Deletion did not occur";
        }
        db.disconnect();
        return result;
    }

}