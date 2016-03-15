package gui;

import java.awt.Label;
import java.util.List;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import core.StaffAccount;

public class IncidentReportFormGUI
{ 
    private static final String FORM_TITLE = "Incident Reporting Form";
    
    private Stage mainStage;
    private Stage parentStage;
    
    private TextField personInjured;
    
    private CheckBox staffInjured, participantInjured,otherInjured;
    private CheckBox LSTD,programs,pathWays,frontOffice,WRD,contracts,personalSupport,lifeEnrichment, other;
    private CheckBox chkHead,chkNeck,chkChest,chkLShoulder, chkRShoulder,chkPelvis, chkLButtocks, chkRButtocks,
        chkLAnkle, chkRAnkle, chkLEar, chkREar,chkToes, chkLEye, chkREye, chkLUpperback, chkRUpperBack, chkLLowerBack,
        chkRLowerBack, chkLFoot, chkRFoot, chkRUpperArm, chkLUpperArm, chkRLowerArm, chkLLowerArm, chkNose, chkFingers,
        chkRElbow, chkLElbow, chkRWrist, chkLWrist, chkLHand, chkRHand, chkLUpperLeg, chkRUpperLeg, chkLLowerLeg,chkRLowerLeg,
        chkLKnee, chkRKnee, chkAbdomen, chkNone, chkOther;
        
    private List<CheckBox> registeredWorkAreas;
        
    public IncidentReportFormGUI(Stage parentStage, StaffAccount loggedInUser)
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
    /**
     * Purpose: creates the section of the form where the user can select multiple checkboxes outlining the area
     * of the injured body.
     * 
     * 42 checkboxes.
     * 
     * 15   14  13
     * 
     * GRID LAYOUT
     * 
     * 
     */
    private GridPane createBodyAreaInjured()
    {
        GridPane gridPane = new GridPane();
       
        
        gridPane.add(chkHead = new CheckBox("Head"), 0, 0);
        gridPane.add(chkNeck = new CheckBox("Neck"), 0, 1);
        gridPane.add(chkChest = new CheckBox("Chest"), 0, 2);
        gridPane.add(chkLShoulder = new CheckBox("Left Shoulder"), 0, 3);
        gridPane.add(chkRShoulder = new CheckBox("Right Shoulder"), 0, 4);
        gridPane.add(chkPelvis = new CheckBox("Pelvis/Hips"), 0, 5); 
        gridPane.add(chkLButtocks = new CheckBox("Left Buttocks"), 0, 6); 
        gridPane.add(chkRButtocks = new CheckBox("Right Buttocks"), 0, 7);
        gridPane.add(chkLAnkle = new CheckBox("Left Ankle"), 0, 8); 
        gridPane.add(chkRAnkle = new CheckBox("Right Ankle"), 0, 9); 
        gridPane.add(chkLEar = new CheckBox("Left Ear"), 0, 10); 
        gridPane.add(chkREar = new CheckBox("Right ear"), 0, 11);
        gridPane.add(chkToes = new CheckBox("Toe(s)"), 0, 12); 
        gridPane.add(chkLEye = new CheckBox("Left Eye"), 0, 13);
        gridPane.add(chkREye = new CheckBox("Right Eye"), 0, 14); 
        gridPane.add(chkLUpperback = new CheckBox("left Upper Back"), 1, 0);
        gridPane.add(chkRUpperBack = new CheckBox("Right Upper Back"), 1, 1);
        gridPane.add(chkLLowerBack = new CheckBox("Left Lower Back"), 1, 2);
        gridPane.add(chkRLowerBack = new CheckBox("Right Lower Back"), 1, 3);
        gridPane.add(chkLFoot = new CheckBox("Left Foot"), 1, 4);
        gridPane.add(chkRFoot = new CheckBox("Right Foot"), 1, 5);
        gridPane.add(chkRUpperArm = new CheckBox("Right Upper Arm"), 1, 6);
        gridPane.add(chkLUpperArm = new CheckBox("Left upper Arm"), 1, 7); 
        gridPane.add(chkRLowerArm = new CheckBox("Right Lower Arm"), 1, 8);
        gridPane.add(chkLLowerArm = new CheckBox("Left Lower Arm"), 1, 9); 
        gridPane.add(chkNose = new CheckBox("Nose"), 1, 10); 
        gridPane.add(chkFingers = new CheckBox("Finger(s)"), 1, 11);
        gridPane.add(chkRElbow = new CheckBox("Right Elbow"), 1,12);
        gridPane.add(chkLElbow = new CheckBox("Left Elbow"), 1, 13);
        gridPane.add(chkRWrist = new CheckBox("Right Wrist"), 1, 14);
        gridPane.add(chkLWrist = new CheckBox("Left Wrist"), 2, 0);
        gridPane.add(chkLHand = new CheckBox("Left Hand"), 2, 1);
        gridPane.add(chkRHand = new CheckBox("Right Hand"), 2, 2);
        gridPane.add(chkLUpperLeg = new CheckBox("Left Upper Leg"), 2, 3);
        gridPane.add(chkRUpperLeg = new CheckBox("Right Upper Leg"), 2, 4); 
        gridPane.add(chkLLowerLeg = new CheckBox("Left Lower Leg"), 2, 5);
        gridPane.add(chkRLowerLeg = new CheckBox("Right Lower Leg"), 2, 6);
        gridPane.add(chkLKnee = new CheckBox("Left Knee"), 2, 7);
        gridPane.add(chkRKnee = new CheckBox("Right Knee"), 2, 8); 
        gridPane.add(chkAbdomen = new CheckBox("Abdomen/Stomach"), 2, 9); 
        gridPane.add(chkNone = new CheckBox("None"), 2, 10);
        gridPane.add(chkOther = new CheckBox("Other (Specify)"), 2, 11);
        

        return gridPane;
        
        
    }
}