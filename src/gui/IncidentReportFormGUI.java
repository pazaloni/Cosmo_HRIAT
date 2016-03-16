package gui;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.StaffAccount;

public class IncidentReportFormGUI
{
    private static final String FORM_TITLE = "Incident Reporting Form";

    private ScrollPane mainPane;
    private VBox mainBox;

    private Stage parentStage;

    private TextField personInjured;
    private TextField otherWorkArea;

    private RadioButton staffInjured, participantInjured, otherInjured, am, pm;
    private RadioButton LSTD, programs, pathWays, frontOffice, WRD, contracts,
            personalSupports, lifeEnrichment, other;
    
    private CheckBox chkHead,chkNeck,chkChest,chkLShoulder, chkRShoulder,chkPelvis, chkLButtocks, chkRButtocks,
    chkLAnkle, chkRAnkle, chkLEar, chkREar,chkToes, chkLEye, chkREye, chkLUpperback, chkRUpperBack, chkLLowerBack,
    chkRLowerBack, chkLFoot, chkRFoot, chkRUpperArm, chkLUpperArm, chkRLowerArm, chkLLowerArm, chkNose, chkFingers,
    chkRElbow, chkLElbow, chkRWrist, chkLWrist, chkLHand, chkRHand, chkLUpperLeg, chkRUpperLeg, chkLLowerLeg,chkRLowerLeg,
    chkLKnee, chkRKnee, chkAbdomen, chkNone, chkOther;

    private TextField txtOther;
    private List<CheckBox> typesOfInjuries;

    public IncidentReportFormGUI(Stage parentStage, StaffAccount loggedInUser)
    {
        
        this.parentStage = parentStage;
    }

    public void showIncidentReportForm()
    {
        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));
        mainBox = new VBox();
        mainBox.getChildren().addAll(formTitle, createHeader(),
                createRegisteredWorkArea(), createBodyAreaInjured());
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);

        parentStage.setScene(new Scene(mainPane));
        parentStage.initOwner(parentStage);
        parentStage.initModality(Modality.APPLICATION_MODAL);

    }

    /**
     * 
     * Purpose: Create the header info containing person injured.
     * 
     * return box HBox containing required nodes
     */
    private HBox createHeader()
    {
        HBox box = new HBox();

        staffInjured = new RadioButton("Staff");
        participantInjured = new RadioButton("Participant Injured");
        otherInjured = new RadioButton("Other");

        personInjured = new TextField();

        am = new RadioButton("am");
        pm = new RadioButton("pm");

        Label lblpersonInjured = new Label("Person Injured: ");

        box.getChildren().addAll(lblpersonInjured, personInjured, staffInjured,
                participantInjured, otherInjured);

        return box;
    }

    /**
     * 
     * Purpose: Create a grid-pane with the check-boxes for the different work
     * areas
     * 
     * @return gridpane with checkboxes
     */
    private GridPane createRegisteredWorkArea()
    {
        Label registeredWorkArea = new Label("Registered Work Area: ");
        registeredWorkArea.setFont(new Font(20));
        GridPane grid = new GridPane();

        ToggleGroup group = new ToggleGroup();

        otherWorkArea = new TextField();
        otherWorkArea.setDisable(true);

        LSTD = new RadioButton("LSTD");
        programs = new RadioButton("Programs");
        pathWays = new RadioButton("Pathways");
        frontOffice = new RadioButton("Front Office");
        WRD = new RadioButton("WRD");
        contracts = new RadioButton("Contracts");
        personalSupports = new RadioButton("Personal Supports");
        lifeEnrichment = new RadioButton("Life Enrichment");
        other = new RadioButton("Other");

        group.getToggles().addAll(LSTD, programs, pathWays, frontOffice, WRD,
                contracts, personalSupports, lifeEnrichment, other);

        grid.add(LSTD, 0, 0);
        grid.add(programs, 0, 1);
        grid.add(pathWays, 0, 2);
        grid.add(frontOffice, 0, 3);
        grid.add(WRD, 1, 0);
        grid.add(contracts, 1, 1);
        grid.add(personalSupports, 1, 2);
        grid.add(lifeEnrichment, 1, 3);
        grid.add(other, 2, 0);
        grid.add(otherWorkArea, 2, 1);

        lifeEnrichment.setOnAction(event -> {
            if ( lifeEnrichment.isSelected() )
            {
                otherWorkArea.setDisable(false);
            }
            else
            {
                otherWorkArea.setDisable(true);
            }
        });
        return grid;
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
        gridPane.add(chkOther = new CheckBox("Other (Specify):"), 0, 14);
        gridPane.add(chkLEye = new CheckBox("Left Eye"), 0, 13);
        gridPane.add(chkREye = new CheckBox("Right Eye"), 1, 1); 
        gridPane.add(chkLUpperback = new CheckBox("left Upper Back"), 1, 2);
        gridPane.add(chkRUpperBack = new CheckBox("Right Upper Back"), 1, 3);
        gridPane.add(chkLLowerBack = new CheckBox("Left Lower Back"), 1, 4);
        gridPane.add(chkRLowerBack = new CheckBox("Right Lower Back"), 1, 5);
        gridPane.add(chkLFoot = new CheckBox("Left Foot"), 1, 6);
        gridPane.add(chkRFoot = new CheckBox("Right Foot"), 1, 7);
        gridPane.add(chkRUpperArm = new CheckBox("Right Upper Arm"), 1, 8);
        gridPane.add(chkLUpperArm = new CheckBox("Left upper Arm"), 1, 9); 
        gridPane.add(chkRLowerArm = new CheckBox("Right Lower Arm"), 1, 10);
        gridPane.add(chkLLowerArm = new CheckBox("Left Lower Arm"), 1, 11); 
        gridPane.add(chkNose = new CheckBox("Nose"), 1, 0); 
        gridPane.add(chkFingers = new CheckBox("Finger(s)"), 2, 1);
        gridPane.add(chkRElbow = new CheckBox("Right Elbow"), 1,12);
        gridPane.add(chkLElbow = new CheckBox("Left Elbow"), 2, 0);
        gridPane.add(chkRWrist = new CheckBox("Right Wrist"), 1, 13);
        gridPane.add(chkLWrist = new CheckBox("Left Wrist"), 2, 11);
        gridPane.add(chkLHand = new CheckBox("Left Hand"), 2, 12);
        gridPane.add(chkRHand = new CheckBox("Right Hand"), 2, 2);
        gridPane.add(chkLUpperLeg = new CheckBox("Left Upper Leg"), 2, 3);
        gridPane.add(chkRUpperLeg = new CheckBox("Right Upper Leg"), 2, 4); 
        gridPane.add(chkLLowerLeg = new CheckBox("Left Lower Leg"), 2, 5);
        gridPane.add(chkRLowerLeg = new CheckBox("Right Lower Leg"), 2, 6);
        gridPane.add(chkLKnee = new CheckBox("Left Knee"), 2, 7);
        gridPane.add(chkRKnee = new CheckBox("Right Knee"), 2, 8); 
        gridPane.add(chkAbdomen = new CheckBox("Abdomen/Stomach"), 2, 9); 
        gridPane.add(chkNone = new CheckBox("None"), 2, 10);
        gridPane.add(txtOther = new TextField(), 1,14);
       
        gridPane.setColumnSpan(txtOther, 2);
        
        gridPane.setHgap(12);
        gridPane.setVgap(3);

        return gridPane;
        
        
    }
}
