package gui;

import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private TextField month, day, year, time, location;

    private TextField personInjured;
    private TextField otherWorkArea;

    private RadioButton staffInjured, participantInjured, otherInjured, am, pm;
    private RadioButton LSTD, programs, pathWays, frontOffice, WRD, contracts,
            personalSupports, lifeEnrichment, other;

    private CheckBox chkHead, chkNeck, chkChest, chkLShoulder, chkRShoulder,
            chkPelvis, chkLButtocks, chkRButtocks, chkLAnkle, chkRAnkle,
            chkLEar, chkREar, chkToes, chkLEye, chkREye, chkLUpperback,
            chkRUpperBack, chkLLowerBack, chkRLowerBack, chkLFoot, chkRFoot,
            chkRUpperArm, chkLUpperArm, chkRLowerArm, chkLLowerArm, chkNose,
            chkFingers, chkRElbow, chkLElbow, chkRWrist, chkLWrist, chkLHand,
            chkRHand, chkLUpperLeg, chkRUpperLeg, chkLLowerLeg, chkRLowerLeg,
            chkLKnee, chkRKnee, chkAbdomen, chkNone, chkOther;

    private TextField txtOther;
    private List<CheckBox> typesOfInjuries;

    private StaffAccount loggedInStaff;

    public IncidentReportFormGUI(Stage stage, StaffAccount loggedInStaff)
    {
        this.parentStage = stage;
        this.loggedInStaff = loggedInStaff;
    }

    public ScrollPane showIncidentReportForm()
    {
        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));

        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Separator sep1 = new Separator(Orientation.HORIZONTAL);
        sep1.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        sep2.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        sep3.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Separator sep4 = new Separator(Orientation.HORIZONTAL);
        sep4.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        
        
        Label lblBodyArea = new Label(
                "Body Area Injured: (check ALL that apply)");
        lblBodyArea.setFont(new Font(16));
        mainBox = new VBox();
        mainBox.setSpacing(10);
        mainBox.setMinWidth(700);
        mainBox.getChildren().addAll(formTitle, createHeader(),sep1,
                createRegisteredWorkArea(),sep2, createIncidentInfo(),sep3, lblBodyArea,
                createBodyAreaInjured(),sep4);
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);
        mainPane.setMinWidth(700);

        return mainPane;

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
        personInjured.setMinWidth(250);
        Label lblpersonInjured = new Label("Person Injured: ");

        box.getChildren().addAll(lblpersonInjured, personInjured, staffInjured,
                participantInjured, otherInjured);

        return box;
    }

    /**
     * Purpose: create the nodes required for filling out the incident info
     * 
     * @return a vbox containing the required nodes
     */
    private VBox createIncidentInfo()
    {

        HBox topBox = new HBox();
        HBox bottomBox = new HBox();

        Label lblDateOfIncident = new Label("Date of Incident:");
        Label lblTimeOfIncident = new Label("Time of Incident:");
        Label lblExactLocaitonOfIncident = new Label(
                "Exact Location of Where Incident Occured:");

        month = new TextField();
        day = new TextField();
        year = new TextField();
        time = new TextField();
        location = new TextField();

        am = new RadioButton("am");
        pm = new RadioButton("pm");

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(am, pm);

        topBox.getChildren().addAll(lblDateOfIncident, month, day, year,
                lblTimeOfIncident, am, pm);
        bottomBox.getChildren().addAll(lblExactLocaitonOfIncident, location);

        VBox box = new VBox();
        box.getChildren().addAll(topBox, bottomBox);
        topBox.setSpacing(10);
        bottomBox.setSpacing(10);

        box.setSpacing(5);
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
        registeredWorkArea.setFont(new Font(16));
        GridPane grid = new GridPane();

        ToggleGroup group = new ToggleGroup();

        otherWorkArea = new TextField();

        LSTD = new RadioButton("LSTD");
        programs = new RadioButton("Programs");
        pathWays = new RadioButton("Pathways");
        frontOffice = new RadioButton("Front Office");
        WRD = new RadioButton("WRD");
        contracts = new RadioButton("Contracts");
        personalSupports = new RadioButton("Personal Supports");
        lifeEnrichment = new RadioButton("Life Enrichment");
        other = new RadioButton("Other :");

        group.getToggles().addAll(LSTD, programs, pathWays, frontOffice, WRD,
                contracts, personalSupports, lifeEnrichment, other);
        grid.add(registeredWorkArea, 0, 0);
        grid.add(LSTD, 1, 1);
        grid.add(programs, 1, 2);
        grid.add(pathWays, 2, 1);
        grid.add(frontOffice, 3, 2);
        grid.add(WRD, 3, 1);
        grid.add(contracts, 2, 2);
        grid.add(personalSupports, 4, 1);
        grid.add(lifeEnrichment, 4, 2);
        grid.add(other, 1, 3);
        grid.add(otherWorkArea, 2, 3);

        HBox hbox = new HBox();

        txtOther = new TextField();
        hbox.getChildren().addAll(other, otherWorkArea);
        txtOther.setMinWidth(4000);

        grid.add(hbox, 1, 3);
        GridPane.setColumnSpan(hbox, 4);

        GridPane.setColumnSpan(otherWorkArea, 2);

        grid.setHgap(40);
        return grid;
    }

    /**
     * Purpose: creates the section of the form where the user can select
     * multiple checkboxes outlining the area of the injured body.
     * 
     * 42 checkboxes.
     * 
     * 15 14 13
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
        gridPane.add(chkRElbow = new CheckBox("Right Elbow"), 1, 12);
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

        HBox hbox = new HBox();
        chkOther = new CheckBox("Other (Specify):");
        txtOther = new TextField();
        hbox.getChildren().addAll(chkOther, txtOther);
        txtOther.setMinWidth(400);

        gridPane.add(hbox, 0, 14);
        GridPane.setColumnSpan(hbox, 3);

        gridPane.setHgap(200);
        gridPane.setVgap(3);
        gridPane.setMinWidth(700);
        return gridPane;

    }
}
