import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * Purpose: display the allergies table
 * 
 * 
 * @author TEAM CIMP!
 * @version 1.0
 */
public class AllergiesTableViewController
{

    // Table
    protected TableView<Allergies> allergiesTable = new TableView<Allergies>();

    // Table columns
    protected TableColumn<Allergies, String> allergyTypeColumn = new TableColumn<Allergies, String>(
            "AllergyType");
    protected TableColumn<Allergies, String> allergicToColumn = new TableColumn<Allergies, String>(
            "Caused By");
    protected TableColumn<Allergies, String> descriptionColumn = new TableColumn<Allergies, String>(
            "Reaction Description");

    // database helper
    private DatabaseHelper db;

    private ObservableList<Allergies> allergyData = FXCollections
            .observableArrayList();

    /**
     * 
     * Constructor for the AllergiesTableViewController class.
     */
    public AllergiesTableViewController(String cosmoID)
    {
        // When the activity log is instantiated, then pull all the information
        // from the database.
        retrieveAllergyData(cosmoID);
        this.initialize();
        allergiesTable.setMaxHeight(150);
        allergiesTable.setItems(allergyData);
        allergiesTable.setFocusTraversable(false);
    }

    /**
     * 
     * Purpose: Query the database for all the participant's allergies
     */
    private void retrieveAllergyData( String cosmoID )
    {
        db = new DatabaseHelper();

        db.connect();

        // Select all everything
        ResultSet allergyResults = db.select(
                "allergyType, allergicTo, description", "Allergies", "cosmoID="
                        + cosmoID, "");

        String allergyType;
        String allergicTo;
        String description;

        try
        {
            while ( allergyResults.next() )
            {
                allergyType = allergyResults.getString(1);
                allergicTo = allergyResults.getString(2);
                description = allergyResults.getString(3);

                // Remove extra 0's at the end of the timestamp
                // dosage = dosage.substring(0, dosage.length() - 7);

                Allergies allergies = new Allergies(allergicTo, allergyType,
                        description);

                // Add the log to the list
                allergyData.add(allergies);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Failed to query allergy informaiton");
        }

        db.disconnect();
    }

    /**
     * 
     * Purpose: to create the table and the columns
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        allergyTypeColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getAllergyType());
        allergyTypeColumn.setMinWidth(75);
        allergyTypeColumn.setResizable(false);

        allergicToColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getAllergicTo());
        allergicToColumn.setMinWidth(150);
        allergicToColumn.setResizable(false);

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getDescription());
        descriptionColumn.setMinWidth(475);
        descriptionColumn.setResizable(false);

        allergiesTable.getColumns().addListener(
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
                            allergiesTable.getColumns().clear();
                            // re-add the columns in order
                            allergiesTable.getColumns().addAll(
                                    allergyTypeColumn, allergicToColumn,
                                    descriptionColumn);
                        }
                    }
                });
        allergiesTable.getColumns().addAll(allergyTypeColumn, allergicToColumn,
                descriptionColumn);

    }

}