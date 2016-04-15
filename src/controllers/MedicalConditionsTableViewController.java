package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import core.QueryResult;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MedicalConditionsTableViewController
{

    public TableView<QueryResult> conditionTable = new TableView<QueryResult>();

    private TableColumn<QueryResult, String> conditionColumn = new TableColumn<QueryResult, String>(
            "Medical Condition");
    private TableColumn<QueryResult, String> descriptionColumn = new TableColumn<QueryResult, String>(
            "Description");

    private ObservableList<QueryResult> conditionData = FXCollections
            .observableArrayList();

    public MedicalConditionsTableViewController(String cosmoId)
    {
        retrieveConditionData(cosmoId);
        initialize();
        conditionTable.setItems(conditionData);
        conditionTable.setFocusTraversable(false);
        conditionTable.setMaxHeight(150);
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
        ResultSet rs = db.select("conditionName, description", "Conditions",
                "cosmoID=" + cosmoId, "");

        String conditionName;
        String description;
        try
        {
            while ( rs.next() )
            {

                conditionName = rs.getString(1);
                description = rs.getString(2);

                QueryResult medicalCondition = new QueryResult(conditionName,
                        description);

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
     * Purpose: Refresh the medical condition table
     * 
     */
    public void refreshTable( String cosmoId )
    {
        this.conditionData.clear();
        this.conditionTable.getColumns().clear();
        this.retrieveConditionData(cosmoId);
        this.initialize();
    }

    /**
     * 
     * @return The selected medical condition
     */
    public QueryResult getSelectedMedicalCondition()
    {
        return this.conditionTable.getSelectionModel().getSelectedItem();
    }

    /**
     * 
     * Purpose: initialize the columns in the table and configure them
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        conditionColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getCondition());
        conditionColumn.setMinWidth(280);
        conditionColumn.setResizable(false);

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getDescripion());
        descriptionColumn.setMinWidth(420);
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
                            conditionTable.getColumns().addAll(conditionColumn,
                                    descriptionColumn);
                        }
                    }
                });
        conditionTable.getColumns().addAll(conditionColumn, descriptionColumn);
    }
}