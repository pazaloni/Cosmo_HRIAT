import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * 
 *  Purpose: Method to implement the seizureMedication table.
 *
 * @author CST205, CST207
 * @version 1.0
 */
public class SeizureMedicationTableViewController
{

    protected TableView<SeizureMedication> seizureMedicationTable = 
            new TableView<SeizureMedication>();

    private TableColumn<SeizureMedication, String> medicationNameColumn =
            new TableColumn<SeizureMedication, String>(
            "Medication Name");
    private TableColumn<SeizureMedication, String> dosageColumn = 
            new TableColumn<SeizureMedication, String>(
            "Dosage");

    private TableColumn<SeizureMedication, String> timesGivenColumn = 
            new TableColumn<SeizureMedication, String>(
            "Times Given");
    
    private ObservableList<SeizureMedication> seizureMedicationData = 
            FXCollections.observableArrayList();

    public SeizureMedicationTableViewController(String cosmoId)
    {
        retrieveSeizureMedicationData(cosmoId);
        initialize();
        seizureMedicationTable.setItems(seizureMedicationData);
        seizureMedicationTable.setFocusTraversable(false);
    }

    /**
     * 
     * Purpose: Query the database for the seizure medication data
     */
    private void retrieveSeizureMedicationData( String cosmoId )
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        
        // Select statement to get the seizure id
        ResultSet rs = db.select("seizureID", "Seizures",
                "cosmoID=" + cosmoId, "");
        String seizureID = "";
        try
        {
            while ( rs.next() )
            {

                seizureID = rs.getString(1);
            }
        }
        catch ( SQLException e )
        {           
            e.printStackTrace();
            System.out.println("Failed to populate conditions table");
        }

        if (!seizureID.equals(""))
        {
        	//Select statement to get the medication id.
            ResultSet sm = db.select("medicationID", "SeizureMedication",
                "seizureID=" + seizureID, "");
            String medicationID;
            
            try
            {
                while ( sm.next() )
                {
    
                    medicationID = sm.getString(1);
                    //Select statement to get the medication for the seizure
                    rs = db.select("medicationName, dosage, timesGiven", 
                            "Medication", "MedicationID=" + medicationID, "");
                    
                    String medicationName;
                    String dosage;
                    String timesGiven;
                    try
                    {
                        while ( rs.next() )
                        {
    
                            medicationName = rs.getString(1);
                            dosage = rs.getString(2);
                            timesGiven = rs.getString(3);
                            
                            timesGiven = timesGiven.substring(0, 
                                    timesGiven.length() - 7);
                            
                            SeizureMedication SeizureMedication = new 
                                    SeizureMedication(medicationName, dosage, 
                                            timesGiven);
    
                            seizureMedicationData.add(SeizureMedication);
                        }
                    }
                    catch ( SQLException e )
                    {           
                        e.printStackTrace();
                        System.out.println("Failed to populate table");
                    }
                }
            }
            catch ( SQLException e )
            {           
                e.printStackTrace();
                System.out.println("Failed to populate conditions table");
            }
        
        }
        else
        {
            
            SeizureMedication sm = new SeizureMedication("", "", "");
            seizureMedicationData.add(sm);
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
        medicationNameColumn.setCellValueFactory(
                cellData->cellData.getValue().getMedicationName());
        medicationNameColumn.setMinWidth(400);
        medicationNameColumn.setResizable(false);
        
        dosageColumn.setCellValueFactory(
                cellData->cellData.getValue().getDosage());
        dosageColumn.setMinWidth(150);
        dosageColumn.setResizable(false);
        
        timesGivenColumn.setCellValueFactory(
                cellData->cellData.getValue().getTimesGiven());
        timesGivenColumn.setMinWidth(150);
        timesGivenColumn.setResizable(false);
        
        
        
        seizureMedicationTable.getColumns().addListener(
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
                        seizureMedicationTable.getColumns().clear();
                        // re-add the columns in order
                        seizureMedicationTable.getColumns().addAll(
                                medicationNameColumn, dosageColumn, 
                                timesGivenColumn);
                    }
                }
        });
        seizureMedicationTable.getColumns().addAll(medicationNameColumn, 
                dosageColumn, timesGivenColumn);
    }
    
    
    
}
