package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;
import core.Incident;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IncidentReportTableViewController
{
    public TableView<Incident> incidentTable = new TableView<Incident>();
    
    private TableColumn<Incident, String> incidentIDColumn = new TableColumn<Incident, String>
    ("Incident ID");
    
    private TableColumn<Incident, String> cosmoIDColumn = new TableColumn<Incident, String>
    ("Cosmo ID");
    
    private TableColumn<Incident, String> dateOfIncidentColumn = new TableColumn<Incident, String>
    ("Date Of Incident");
    
    private TableColumn<Incident, String> timeOfIncidentColumn = new TableColumn<Incident, String>
    ("Time Of Incident");
    
    private TableColumn<Incident, String> descriptionColumn = new TableColumn<Incident, String>
    ("Description");
    
    public ObservableList<Incident> incidentData = FXCollections.observableArrayList();
    
    public IncidentReportTableViewController()
    {
        retrieveIncidentData("", "Incidents");
        incidentTable.setItems(incidentData);
        incidentTable.setFocusTraversable(false);
    }

    private void retrieveIncidentData( String conditon, String table )
    {
        incidentData.clear();
        DatabaseHelper db = new DatabaseHelper();
        
        ResultSet rs = db.select("incidentID, cosmoID, dateOfIncident, "
                + "timeOfIncident, incidentDescription", "Incidents", "", "");
        
        String incidentID;
        String cosmoID;
        String dateOfIncident;
        String timeOfIncident;
        String incidentDescription;
        
        try
        {
            while(rs.next())
            {
                incidentID = rs.getString(1);
                cosmoID = rs.getString(2);
                dateOfIncident = rs.getString(3);
                timeOfIncident = rs.getString(4);
                incidentDescription = rs.getString(5);
                
                Incident incident = new Incident(incidentID, cosmoID, 
                        dateOfIncident, timeOfIncident, incidentDescription);
                
                incidentData.add(incident);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    
    public void initialize()
    {
        incidentIDColumn.setCellValueFactory(cellData -> cellData.getValue().incidentIDProperty());
        incidentIDColumn.setMinWidth(50);
        incidentIDColumn.setResizable(false);
        
        cosmoIDColumn.setCellValueFactory(cellData -> cellData.getValue().cosmoIDProperty());
        cosmoIDColumn.setMinWidth(50);
        cosmoIDColumn.setResizable(false);
        
        dateOfIncidentColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfIncidentIDProperty());
        dateOfIncidentColumn.setMinWidth(100);
        dateOfIncidentColumn.setResizable(false);
        
        timeOfIncidentColumn.setCellValueFactory(cellData -> cellData.getValue().timeOfIncidentIDProperty());
        timeOfIncidentColumn.setMinWidth(100);
        timeOfIncidentColumn.setResizable(false);
        
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        descriptionColumn.setMinWidth(500);
        descriptionColumn.setResizable(false);
        
        incidentTable.getColumns().addListener(
                new ListChangeListener<Object>() {
                    @Override
                    public void onChanged(Change change){
                    change.next();
                    if(change.wasReplaced())
                    {
                        incidentTable.getColumns().clear();
                        incidentTable.getColumns().addAll(incidentIDColumn, 
                                cosmoIDColumn, dateOfIncidentColumn, 
                                timeOfIncidentColumn, descriptionColumn);
                    }
                } 
           });
        
        incidentTable.getColumns().addAll(incidentIDColumn, cosmoIDColumn, 
                dateOfIncidentColumn, timeOfIncidentColumn, descriptionColumn);
    }
    
    public String getSelectedPK()
    {
        String result;
        
        Incident incident = incidentTable.getSelectionModel().getSelectedItem();
        
        if(incident == null)
        {
            result = "null";
        }
        else
        {
            result = incident.getIncidentID();
        }
        return result;
    }
    
    public void refreshTable(String condition, String table)
    {
        this.incidentData.clear();
        this.retrieveIncidentData(condition, table);
        this.incidentTable.getColumns().clear();
        this.initialize();
    }
}
