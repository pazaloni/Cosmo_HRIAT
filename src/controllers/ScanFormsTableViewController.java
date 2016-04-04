package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.ScanForms;
import core.ScanForms;

public class ScanFormsTableViewController
{

    public TableView<ScanForms> scanTable = new TableView<ScanForms>();

    private TableColumn<ScanForms, String> dateSavedColumn = new TableColumn<ScanForms, String>(
            "Date saved");
    private TableColumn<ScanForms, String> descriptionColumn = new TableColumn<ScanForms, String>(
            "description");
    private TableColumn<ScanForms, String> fileNameColumn = new TableColumn<ScanForms, String>(
            "file name");
    private ObservableList<ScanForms> scannedData = FXCollections
            .observableArrayList();

    public ScanFormsTableViewController(String cosmoId)
    {
        retrieveScanFormsData(cosmoId);
        initialize();
        scanTable.setItems(scannedData);
        scanTable.setFocusTraversable(false);
        scanTable.setMaxHeight(150);
    }

    /**
     * 
     * Purpose: Query the database for the scanned image data
     * 
     * @parameter cosmoId: The cosmoId of the participant whose data is being
     *            retrieved.
     */
    private void retrieveScanFormsData( String cosmoId )
    {
        // Create an instance of the database helper
        DatabaseHelper db = new DatabaseHelper();

        db.connect();

        // Select statement for the
        ResultSet rs = db.select("dateSaved, description, fileName",
                "ScanForms", "cosmoID=" + cosmoId, "");

        LocalDate dateSaved;
        String description;
        String fileName;
        try
        {
            while ( rs.next() )
            {
                String scannedDate = "";
                scannedDate = rs.getString(1);
                // convert the dobtext into a localdate
                int yearEnd = scannedDate.indexOf('-');
                int year = Integer.parseInt(scannedDate.substring(0, yearEnd));
                scannedDate = scannedDate.substring(yearEnd + 1);
                int monthEnd = scannedDate.indexOf('-');
                int month = Integer.parseInt(scannedDate.substring(0, monthEnd));
                scannedDate = scannedDate.substring(monthEnd + 1);
                int day = Integer.parseInt(scannedDate.substring(0, 2));
                
                dateSaved = LocalDate.of(year, month, day);                
                description = rs.getString(2);
                fileName = rs.getString(3);

                ScanForms scannedForm = new ScanForms(dateSaved,
                        description, fileName);

                scannedData.add(scannedForm);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Failed to populate scanned form table");
        }
        db.disconnect();
    }

    /**
     * Purpose: Refresh the ScanForms table
     * 
     */
    public void refreshTable( String cosmoId )
    {
        this.scannedData.clear();
        this.scanTable.getColumns().clear();
        this.retrieveScanFormsData(cosmoId);
        this.initialize();
    }

    /**
     * 
     * @return The selected scanned form
     */
    public ScanForms getSelectedPK()
    {
        return this.scanTable.getSelectionModel().getSelectedItem();
    }

    /**
     * 
     * Purpose: initialize the columns in the table and configure them
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        
        dateSavedColumn.setCellValueFactory(cellData -> cellData.getValue()
                .displayDateSaved());
        dateSavedColumn.setMinWidth(100);
        dateSavedColumn.setResizable(false);

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getDescription());
        descriptionColumn.setMinWidth(440);
        descriptionColumn.setResizable(false);

        fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFileName());
        fileNameColumn.setMinWidth(100);
        fileNameColumn.setResizable(false);

        scanTable.getColumns().addListener(new ListChangeListener<Object>()
        {
            @Override
            public void onChanged( Change change )
            {
                change.next();
                // if the column was changed
                if ( change.wasReplaced() )
                {
                    // clear all columns
                    scanTable.getColumns().clear();
                    // re-add the columns in order
                    scanTable.getColumns().addAll(dateSavedColumn,
                            descriptionColumn, fileNameColumn);
                }
            }
        });
        scanTable.getColumns()
                .addAll(dateSavedColumn, descriptionColumn, fileNameColumn);
    }
}

