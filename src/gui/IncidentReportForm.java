package gui;

import java.awt.Label;
import java.util.List;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.StaffAccount;

public class IncidentReportForm
{ 
    private static final String FORM_TITLE = "Incident Reporting Form";
    
    private Stage mainStage;
    private Stage parentStage;
    
    private TextField personInjured;
    
    private CheckBox staffInjured, participantInjured,otherInjured;
    private CheckBox LSTD,programs,pathWays,frontOffice,WRD,contracts,personalSupport,lifeEnrichment, other;
    
    private List<CheckBox> registeredWorkAreas;
        
    public IncidentReportForm(Stage parentStage, StaffAccount loggedInUser)
    {
        this.mainStage = new Stage();
        this.parentStage = parentStage;
    }    
    
    public void showIncidentReportForm()
    {
        
    }
    
    
    /**
     * 
     * Purpose: Create the form up to the part where it says type of injury
     */
    private void createHeader()
    {
        LSTD = new CheckBox("LSTD");
        programs = new CheckBox("Programs");
        pathWays = new CheckBox("Pathways");
        
        Label personInjured = new Label("Person Injured: ");
        Label registeredWorkArea = new Label("Registered Work Area: ");
        Label dateOfIncident = new Label("Date of incident");
        Label timeOfIncidnet = new Label("Time of incidnet");
        Label locationOfIncident = new Label("Location of incident");
        
        
        
        
    }
}