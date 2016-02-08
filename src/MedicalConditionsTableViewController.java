import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MedicalConditionsTableViewController
{

    protected TableView<MedicalCondition> conditionTable = new TableView<MedicalCondition>();

    private TableColumn<MedicalCondition, String> conditionColumn = new TableColumn<MedicalCondition, String>(
            "Medical Condition");
    private TableColumn<MedicalCondition, String> descriptionColumn = new TableColumn<MedicalCondition, String>(
            "Description");

    private ObservableList<MedicalCondition> conditionData = FXCollections
            .observableArrayList();

    public MedicalConditionsTableViewController(String cosmoId)
    {
        retrieveConditionData(cosmoId);
        initialize();
        conditionTable.setItems(conditionData);
        conditionTable.setFocusTraversable(false);
    }

    // TODO finish next method
    /**
     * 
     * Purpose: Query the database for the condition data
     */
    private void retrieveConditionData( String cosmoId )
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        
        // Select statemtn for the
        ResultSet rs = db.select("conditionName, description", "Condition",
                "cosmoID=" + cosmoId, "");

        String conditionName;
        String description;
        try
        {
            while ( rs.next() )
            {

                conditionName = rs.getString(1);
                description = rs.getString(2);

                MedicalCondition medicalCondition = new MedicalCondition(
                        conditionName, description);

                conditionData.add(medicalCondition);
            }
        }
        catch ( SQLException e )
        {           
            e.printStackTrace();
            System.out.println("Failed to populate conditions table");
        }
        db.disconnect();
    }
    /**
     * 
     * Purpose: initialize the columns in the table and configure them
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        conditionColumn.setCellValueFactory(cellData->cellData.getValue().getCondition());
        conditionColumn.setMinWidth(230);
        conditionColumn.setResizable(false);
        
        descriptionColumn.setCellValueFactory(cellData->cellData.getValue().getCondition());
        descriptionColumn.setMinWidth(370);
        descriptionColumn.setResizable(false);
        
        conditionTable.getColumns().addListener(
            new ListChangeListener<Object>()
            {
                @Override
                public void onChanged( Change change )
                {
                    change.next();
                    // if the column was changed
                    if ( change.wasReplaced() )
                    {
                        // clear all columns
                        conditionTable.getColumns().clear();
                        // re-add the columns in order
                        conditionTable.getColumns().addAll(conditionColumn, descriptionColumn);
                    }
                }
        });
        conditionTable.getColumns().addAll(conditionColumn, descriptionColumn);
    }
    
    
    
}







