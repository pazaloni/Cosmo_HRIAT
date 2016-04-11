package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;
import core.Incident;
import javafx.collections.FXCollections;
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
        initializeIncidentData();
        incidentTable.setItems(incidentData);
    }

    private void initializeIncidentData()
    {
        DatabaseHelper db = new DatabaseHelper();
        
        ObservableList<String> row = FXCollections.observableArrayList();
        
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
        
        cosmoIDColumn.setCellValueFactory(cellData -> cellData.getValue().cosmoIDProperty());
        
        dateOfIncidentColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfIncidentIDProperty());
        
        timeOfIncidentColumn.setCellValueFactory(cellData -> cellData.getValue().timeOfIncidentIDProperty());
        
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        
        incidentTable.getColumns().addAll(incidentIDColumn, cosmoIDColumn, 
                dateOfIncidentColumn, timeOfIncidentColumn, descriptionColumn);
        
        incidentTable.setItems(incidentData);
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
}
