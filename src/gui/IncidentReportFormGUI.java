package gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import core.StaffAccount;

/**
 * Purpose: represent the incident report form
 * 
 * @author CIMP
 * @version 1.0
 */
public class IncidentReportFormGUI
{
    private static final String FORM_TITLE = "Incident Reporting Form";

    private ObservableList<String> workAreas, allBodyAreas, injuredBodyAreas,
            personReportedTo;

    private ListView<String> lsvAllBodyAreas, lsvInjuredBodyAreas;

    private ComboBox<String> cboWorkAreas;
    private ComboBox<String> cboPersonReportedTo;

    private ScrollPane mainPane;
    private VBox mainBox;

    private Stage parentStage;

    private TextField time, location, staffWitness, participantWitness,
            otherWitness;

    private TextField personInjured;

    private DatePicker dateOfIncident, dateReportToPerson,
            dateVerballyReported, dateReportWritten;

    private TextField otherWorkArea, locationOfPain, txtPersonReportedToName,
            txtTimeReportedToPerson, txtTimeReportedVerbally,
            txtTimeReportWritten, txtReportWrittenBy;

    private TextArea incidentDescription, incidentFactors, txtAProtEquip;

    private RadioButton am, pm;
    private RadioButton other, personalSupportsManager,
            personalSupportsCoordinator, noVerbalReport;

    private CheckBox chkBruise, chkScrape, chkSwelling, chkBurn, chkCut,
            chkSprain, chkInsectbite, chkImbedded, chkFall, chkFoundOnFloor,
            chkSelfAggro, chkP2PAggro, chkP2SAggro, chkVehichleIncident,
            chkPropertyDmg, chkOtherType;

    private RadioButton radUnwitnessed;

    private CheckBox chkWitnessedByStaff, chkWitnessByParticipants,
            chkWitnessByOther;

    private TextField txtOther;
    private TextField txtOtherType;
    private Button btnSubmit;

    private List<CheckBox> typesOfInjuries;

    private StaffAccount loggedInStaff;

    /**
     * 
     * Constructor for the IncidentReportFormGUI class.
     * 
     * @param stage the parent stage of this form
     * @param loggedInStaff the staff that was logged when this form was made
     */
    public IncidentReportFormGUI(Stage stage, StaffAccount loggedInStaff)
    {
        this.parentStage = stage;
        this.loggedInStaff = loggedInStaff;
        workAreas = FXCollections.observableArrayList();
        allBodyAreas = FXCollections.observableArrayList();
        injuredBodyAreas = FXCollections.observableArrayList();

    }

    /**
     * 
     * Purpose: Method that will return the entirety of the form
     * 
     * @return a scroll pane with all the required controls
     */
    public ScrollPane showIncidentReportForm()
    {
        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));

        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        Separator sep1 = new Separator(Orientation.HORIZONTAL);
        sep1.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        sep2.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        sep3.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        Separator sep4 = new Separator(Orientation.HORIZONTAL);
        sep4.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        Separator sep5 = new Separator(Orientation.HORIZONTAL);
        sep5.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));

        Label lblBodyArea = new Label(
                "Body Area Injured: (Check ALL that apply)");
        Label lblTypeofInjury = new Label(
                "Type of Injury: (Check ALL That Apply");

        lblBodyArea.setFont(new Font(16));
        lblTypeofInjury.setFont(new Font(16));
        mainBox = new VBox();
        mainBox.setSpacing(10);
        mainBox.setMaxWidth(700);
        mainBox.getChildren().addAll(formTitle, createHeader(), sep1,
                createRegisteredWorkArea(), sep2, createIncidentInfo(), sep3,
                lblBodyArea, createBodyAreaInjured(), sep4, lblTypeofInjury,
                createTypeOfInjury(), sep5, createMidSecion(),
                createBottomBox());

        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);
        mainPane.setMinWidth(700);
        mainPane.setMaxWidth(720);

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

        Label lblpersonInjured = new Label("Participant Injured: ");

        personInjured = new TextField();
        personInjured.setMinWidth(580);
        box.setMaxWidth(750);
        box.setSpacing(15);
        box.getChildren().addAll(lblpersonInjured, personInjured);

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

        lblDateOfIncident.setPadding(new Insets(0, 40, 0, 0));
        dateOfIncident = new DatePicker();
        dateOfIncident.setMinWidth(250);
        time = new TextField();
        time.setMinWidth(190);
        lblTimeOfIncident.setPadding(new Insets(0, 10, 0, 0));
        location = new TextField();
        location.setMinWidth(300);
        lblExactLocaitonOfIncident.setPadding(new Insets(0, 163, 0, 0));

        am = new RadioButton("am");
        pm = new RadioButton("pm");

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(am, pm);

        topBox.getChildren().addAll(lblDateOfIncident, dateOfIncident,
                lblTimeOfIncident, time);
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
    private VBox createRegisteredWorkArea()
    {
        Label registeredWorkArea = new Label("Registered Work Area: ");
        VBox mainBox = new VBox();

        HBox box = new HBox();
        HBox topBox = new HBox();

        otherWorkArea = new TextField();
        workAreas.add("LSTD");
        workAreas.add("Path Ways");
        workAreas.add("WRD");
        workAreas.add("Person Supports");
        workAreas.add("Programs");
        workAreas.add("Contracts");
        workAreas.add("Front Office");
        workAreas.add("Life Enrichment");
        cboWorkAreas = new ComboBox<>(workAreas);

        other = new RadioButton("Other");
        other.setPadding(new Insets(0, 35, 0, 0));
        cboWorkAreas.setMinWidth(250);
        otherWorkArea.setMinWidth(195);
        otherWorkArea.setDisable(true);
        other.setOnAction(event -> {
            if ( other.isSelected() )
            {
                otherWorkArea.setDisable(false);
                cboWorkAreas.setDisable(true);
            }
            else
            {
                otherWorkArea.setDisable(true);
                cboWorkAreas.setDisable(false);
            }
        });

        box.getChildren().addAll(registeredWorkArea, cboWorkAreas, other,
                otherWorkArea);
        box.setSpacing(15);

        mainBox.getChildren().addAll(topBox, box);

        return mainBox;
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
    private HBox createBodyAreaInjured()
    {
        HBox hbox = new HBox();
        VBox midBox = new VBox();

        Button addInjury = new Button("->");
        Button removeInjury = new Button("<-");

        allBodyAreas.addAll("Head ", "Neck", "Chest", "Left Shoulder",
                "Right Shoulder", "Pelvis/Hips", "Left Buttocks",
                "Right Buttocks", "Left Ankle", "Right Ankle", "Left Ear",
                "Right ear ", "Toes", "Left Eye ", "Right Eye ",
                "left Upper Back ", "Right Upper Back ", "Left Lower Back ",
                "Right Lower Back ", "Left Foot ", "Right Foot ",
                "Right Upper Arm ", "Left upper Arm ", "Right Lower Arm ",
                "Left Lower Arm", "Nose", "Fingers", "Right Elbow",
                "Left Elbow", "Right Wrist", "Left Wrist", "Left Hand",
                "Right Hand ", "Left Upper Leg ", "Right Upper Leg ",
                "Left Lower Leg ", "Right Lower Leg ", "Left Knee ",
                "Right Knee ", "Abdomen/Stomach");

        lsvAllBodyAreas = new ListView<String>(allBodyAreas);
        lsvInjuredBodyAreas = new ListView<String>(injuredBodyAreas);
        FXCollections.sort(allBodyAreas);
        FXCollections.sort(injuredBodyAreas);

        // Handler when they click on the listview that has all the body areas
        // Handles the double click event
        lsvAllBodyAreas.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle( MouseEvent click )
            {
                // If it a double click then
                if ( click.getClickCount() == 2 )
                {
                    // If they clicked on existing item then
                    if ( lsvAllBodyAreas.getSelectionModel().getSelectedItem() != null )
                    {
                        // Add that item to the injuredbody areas
                        injuredBodyAreas.add(lsvAllBodyAreas
                                .getSelectionModel().getSelectedItem());
                        // set the items for the injured body areas
                        lsvInjuredBodyAreas.setItems(injuredBodyAreas);
                        // remove the selected one from all body areas
                        allBodyAreas.remove(lsvAllBodyAreas.getSelectionModel()
                                .getSelectedItem());
                        // set the items for all the body areas
                        lsvAllBodyAreas.setItems(allBodyAreas);
                    }
                }

            }

        });
        lsvInjuredBodyAreas.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle( MouseEvent click )
            {
                if ( click.getClickCount() == 2 )
                {
                    if ( lsvInjuredBodyAreas.getSelectionModel()
                            .getSelectedItem() != null )
                    {
                        allBodyAreas.add(lsvInjuredBodyAreas
                                .getSelectionModel().getSelectedItem());
                        lsvAllBodyAreas.setItems(allBodyAreas);
                        injuredBodyAreas.remove(lsvInjuredBodyAreas
                                .getSelectionModel().getSelectedItem());
                        lsvInjuredBodyAreas.setItems(injuredBodyAreas);
                        FXCollections.sort(allBodyAreas);
                    }
                }

            }

        });

        addInjury.setOnAction(event -> {
            if ( lsvAllBodyAreas.getSelectionModel().getSelectedItem() != null )
            {
                injuredBodyAreas.add(lsvAllBodyAreas.getSelectionModel()
                        .getSelectedItem());
                lsvInjuredBodyAreas.setItems(injuredBodyAreas);
                allBodyAreas.remove(lsvAllBodyAreas.getSelectionModel()
                        .getSelectedItem());
                lsvAllBodyAreas.setItems(allBodyAreas);
            }

        });
        removeInjury
                .setOnAction(event -> {
                    if ( lsvInjuredBodyAreas.getSelectionModel()
                            .getSelectedItem() != null )
                    {
                        allBodyAreas.add(lsvInjuredBodyAreas
                                .getSelectionModel().getSelectedItem());
                        lsvAllBodyAreas.setItems(allBodyAreas);
                        injuredBodyAreas.remove(lsvInjuredBodyAreas
                                .getSelectionModel().getSelectedItem());
                        lsvInjuredBodyAreas.setItems(injuredBodyAreas);
                        FXCollections.sort(allBodyAreas);
                    }
                });

        midBox.getChildren().addAll(addInjury, removeInjury);
        midBox.setSpacing(30);
        hbox.getChildren().addAll(lsvAllBodyAreas, midBox, lsvInjuredBodyAreas);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Purpose: Create the area for the type of injury
     * 
     * @return an hbox containing all the required elements
     */
    private HBox createTypeOfInjury()
    {

        HBox finalHbox = new HBox();

        GridPane gridPaneType = new GridPane();
        HBox otherHBox = new HBox();
        otherHBox.getChildren().addAll(
                chkOtherType = new CheckBox("Other (Specify) :"),
                txtOtherType = new TextField());
        gridPaneType.add(chkBruise = new CheckBox("Bruise/Contution"), 0, 0);
        gridPaneType.add(chkScrape = new CheckBox("Scrape/Abrasion"), 0, 1);
        gridPaneType.add(chkSwelling = new CheckBox("Swelling"), 0, 2);
        gridPaneType.add(chkBurn = new CheckBox("Burn"), 0, 3);
        gridPaneType.add(chkCut = new CheckBox("Cut/Laceration"), 0, 4);
        gridPaneType.add(chkSprain = new CheckBox("Sprain/Strain (Suspected)"),
                0, 5);
        gridPaneType.add(chkInsectbite = new CheckBox("Insect Bite/Sting"), 0,
                6);
        gridPaneType.add(chkImbedded = new CheckBox("Imbedded object"), 0, 7);
        gridPaneType.add(chkFall = new CheckBox("Fall"), 1, 0);
        gridPaneType
                .add(chkFoundOnFloor = new CheckBox("Found on floor"), 1, 1);
        gridPaneType.add(chkSelfAggro = new CheckBox("Self aggression"), 1, 2);
        gridPaneType.add(chkP2PAggro = new CheckBox(
                "Participant to participant agression"), 1, 3);
        gridPaneType.add(chkP2SAggro = new CheckBox(
                "Participant to staff agression"), 1, 4);
        gridPaneType.add(
                chkVehichleIncident = new CheckBox("Vehicle accident"), 1, 5);
        gridPaneType
                .add(chkPropertyDmg = new CheckBox("Property damage"), 1, 6);
        gridPaneType.add(otherHBox, 0, 8);

        Label lblProtEquipment = new Label(
                "Protective Equipment Used at Time of Incident?");
        lblProtEquipment.setFont(new Font(16));
        txtAProtEquip = new TextArea();
        txtAProtEquip.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(lblProtEquipment, txtAProtEquip);

        finalHbox.getChildren().addAll(gridPaneType, vbox);
        gridPaneType.setHgap(40);
        gridPaneType.setVgap(3);
        finalHbox.setSpacing(15);
        GridPane.setColumnSpan(otherHBox, 2);
        return finalHbox;
    }

    /**
     * Purpose: Create the section for location of pain, description of how
     * incident occurred, and factors contributing to incident
     * 
     * @return a VBox containing all the required elements
     */
    private VBox createMidSecion()
    {
        VBox mainBox = new VBox();
        HBox topBox = new HBox();
        Font font = new Font(15);

        int maxHeight = 55;

        Label lblIncidentDescription = new Label(
                "Description of exactly how incident occured");
        lblIncidentDescription.setFont(font);
        Label lblIncidentFactors = new Label(
                "Factors contributing to the incident");
        lblIncidentFactors.setFont(font);

        incidentDescription = new TextArea();
        incidentDescription.setMaxHeight(maxHeight);
        incidentDescription.setWrapText(true);

        incidentFactors = new TextArea();
        incidentFactors.setMaxHeight(maxHeight);
        incidentFactors.setWrapText(true);

        topBox.setSpacing(15);

        mainBox.getChildren().addAll(topBox, lblIncidentDescription,
                incidentDescription, lblIncidentFactors, incidentFactors);

        mainBox.setSpacing(15);
        return mainBox;

    }

    /**
     * 
     * Purpose: Create the are where the incident witnesses will be filled out
     * 
     * @return a Vbox with a radiobutton, 3 checkboxes and 3 textfields
     */
    private VBox createIncidentWitness()
    {
        VBox top = new VBox();

        GridPane topGrid = new GridPane();

        staffWitness = new TextField();
        participantWitness = new TextField();
        otherWitness = new TextField();

        chkWitnessedByStaff = new CheckBox("Witnessed by staff:");
        chkWitnessByParticipants = new CheckBox("Witnessed by participant(s):");
        chkWitnessByOther = new CheckBox("Witnessed by other - explain:");

        int spacing = 5;

        top.setSpacing(spacing);
        radUnwitnessed = new RadioButton("Unwitnessed");
        radUnwitnessed.setOnAction(event -> {
            if ( radUnwitnessed.isSelected() )
            {
                chkWitnessByOther.setDisable(true);
                chkWitnessByParticipants.setDisable(true);
                chkWitnessedByStaff.setDisable(true);
                staffWitness.setDisable(true);
                participantWitness.setDisable(true);
                otherWitness.setDisable(true);

                chkWitnessByOther.setSelected(false);
                chkWitnessByParticipants.setSelected(false);
                chkWitnessedByStaff.setSelected(false);
                staffWitness.clear();
                participantWitness.clear();
                otherWitness.clear();
            }
            else
            {
                chkWitnessByOther.setDisable(false);
                chkWitnessByParticipants.setDisable(false);
                chkWitnessedByStaff.setDisable(false);
                staffWitness.setDisable(false);
                participantWitness.setDisable(false);
                otherWitness.setDisable(false);
            }
        });

        topGrid.add(radUnwitnessed, 0, 0);

        topGrid.add(chkWitnessedByStaff, 0, 1);
        topGrid.add(chkWitnessByParticipants, 0, 2);
        topGrid.add(chkWitnessByOther, 0, 3);

        topGrid.add(staffWitness, 1, 1);
        topGrid.add(participantWitness, 1, 2);
        topGrid.add(otherWitness, 1, 3);

        topGrid.setPadding(new Insets(0, 0, 0, 50));
        topGrid.setHgap(60);
        topGrid.setVgap(5);

        staffWitness.setMinWidth(410);

        top.getChildren().add(topGrid);
        return top;
    }

    /**
     * 
     * Purpose: create the area where the incident reported to will be filled
     * out
     * 
     * @return a vbox with 3 radio buttons, 4 textfields, and a date picker
     */
    private HBox createIncidentReportedTo()
    {
        HBox top = new HBox();

        VBox left = new VBox();
        left.setSpacing(25);
        VBox midLeft = new VBox();
        midLeft.setSpacing(15);
        VBox midRight = new VBox();
        midRight.setSpacing(25);
        VBox right = new VBox();
        right.setSpacing(15);
        personReportedTo = FXCollections.observableArrayList();
        txtPersonReportedToName = new TextField();
        txtPersonReportedToName.setMinWidth(320);

        txtTimeReportedToPerson = new TextField();
        txtTimeReportedToPerson.setMinWidth(320);
        personReportedTo.add("Manager");
        personReportedTo.add("Assistant Manager");
        personReportedTo.add("Other");

        cboPersonReportedTo = new ComboBox<String>(personReportedTo);
        cboPersonReportedTo.getSelectionModel().clearAndSelect(0);
        cboPersonReportedTo.setMaxWidth(140);

        dateReportToPerson = new DatePicker();
        dateReportToPerson.setMaxWidth(140);

        Label lblName = new Label("Name: ");
        Label lblIncidentReportedTo = new Label("Incident Reported To");
        Label lblDateReported = new Label("Date reported: ");
        Label lblTimeReported = new Label("Time reported: ");

        left.getChildren().addAll(lblIncidentReportedTo, lblDateReported);
        midLeft.getChildren().addAll(cboPersonReportedTo, dateReportToPerson);
        midRight.getChildren().addAll(lblName, lblTimeReported);
        right.getChildren().addAll(txtPersonReportedToName,
                txtTimeReportedToPerson);

        top.getChildren().addAll(left, midLeft, midRight, right);
        top.setSpacing(15);
        return top;
    }

    /**
     * 
     * Purpose: create the portion of the form where they enter verbal reports
     * 
     * @return a node containing the required elements
     */
    private VBox createVerbalReport()
    {
        VBox box = new VBox();
        HBox bottomBox = new HBox();

        VBox innerBox = new VBox();

        GridPane topGrid = new GridPane();
        ToggleGroup group2 = new ToggleGroup();

        dateVerballyReported = new DatePicker();
        txtTimeReportedVerbally = new TextField();

        dateVerballyReported.setMaxWidth(140);
        txtTimeReportedVerbally.setMinWidth(310);

        Label lblDateReportedVerbally = new Label("Date reported verbally : ");
        Label lblTimeReported = new Label("Time reported: ");
        personalSupportsCoordinator = new RadioButton(
                "Personal Supports Coordinator");
        personalSupportsManager = new RadioButton("Personal Supports Manager");
        noVerbalReport = new RadioButton("No Verbal Report Given");
        group2.getToggles().addAll(personalSupportsCoordinator,
                personalSupportsManager, noVerbalReport);

        topGrid.add(noVerbalReport, 0, 0);
        topGrid.add(personalSupportsManager, 0, 1);
        topGrid.add(personalSupportsCoordinator, 0, 2);
        topGrid.setPadding(new Insets(5, 5, 5, 50));
        topGrid.setVgap(10);

        bottomBox.getChildren().addAll(lblDateReportedVerbally,
                dateVerballyReported, lblTimeReported, txtTimeReportedVerbally);
        bottomBox.setSpacing(10);
        innerBox.getChildren().addAll(topGrid);
        box.getChildren().addAll(innerBox, bottomBox);

        return box;
    }

    /**
     * 
     * Purpose: create the portion of the form where they enter the date the
     * report was written
     * 
     * @return a node containing the required elements
     */
    private VBox createReportWritten()
    {
        HBox topBox = new HBox();
        HBox bottomBox = new HBox();
        VBox box = new VBox();
        
        dateReportWritten = new DatePicker();
        dateReportWritten.setMaxWidth(145);
        
        txtTimeReportWritten = new TextField();
        txtTimeReportWritten.setMinWidth(310);
        
        txtReportWrittenBy = new TextField();
        txtReportWrittenBy.setMinWidth(565);     
        
        Label lblTimeReported = new Label("Time reported: ");
        Label lblReportWrittenBy = new Label("Report Written By : ");
        Label lblDateReportWritten = new Label("Date Report Written : ");

        topBox.getChildren().addAll(lblDateReportWritten, dateReportWritten,
                lblTimeReported, txtTimeReportWritten);
        topBox.setSpacing(15);
        bottomBox.getChildren().addAll(lblReportWrittenBy,txtReportWrittenBy);
        bottomBox.setSpacing(25);
        box.getChildren().addAll(topBox,bottomBox);
        box.setSpacing(10);
        
        return box;
    }

    /**
     * Purpose :Create the bottom part of the form
     * 
     * @return a vbox containing all the required elements
     */
    private VBox createBottomBox()
    {
        VBox mainBox = new VBox();
        VBox buttonBox = new VBox();

        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));

        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));

        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        
        Label lblIncident = new Label("Incident: ");
        Label lblVerbalReport = new Label("Verbal Report");

        btnSubmit = new Button("Submit");
        buttonBox.getChildren().add(btnSubmit);
        buttonBox.setAlignment(Pos.CENTER);

        mainBox.getChildren().addAll(lblIncident, createIncidentWitness(), sep,
                createIncidentReportedTo(), sep2, lblVerbalReport,
                createVerbalReport(), sep3,createReportWritten(),buttonBox);

        mainBox.setSpacing(15);

        return mainBox;
    }
}