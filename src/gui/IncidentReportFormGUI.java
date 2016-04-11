package gui;

import helpers.IncidentReportFormHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.media.EqualizerBand;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.PopUpMessage;
import core.StaffAccount;
import core.Witness;

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
    private int maxWidth = 710;
    private ComboBox<String> cboWorkAreas;
    private ComboBox<String> cboPersonReportedTo;

    private ScrollPane mainPane;
    private VBox mainBox;
    private Label errorMsg;
    private Stage parentStage, localStage;

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
    private RadioButton radOtherWorkArea, personalSupportsManager,
            personalSupportsCoordinator;

    private CheckBox noVerbalReport;

    private CheckBox chkBruise, chkScrape, chkSwelling, chkBurn, chkCut,
            chkSprain, chkInsectbite, chkImbedded, chkFall, chkFoundOnFloor,
            chkSelfAggro, chkP2PAggro, chkP2SAggro, chkVehichleIncident,
            chkPropertyDmg, chkOtherType, chkUnwitnessed;;

    private Label lblWitnessedByStaff, lblWitnessByParticipants,
            lblWitnessByOther;

    private TextField txtOther;
    private TextField txtOtherType;
    private Button btnSubmit;

    private ArrayList<CheckBox> typesOfInjuries;

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
     */
    public void showIncidentReportForm()
    {

        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));

        errorMsg = new Label();
        errorMsg.setTextFill(Color.FIREBRICK);
        errorMsg.setFont(new Font(16));

        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep.maxWidth(maxWidth);
        Separator sep1 = new Separator(Orientation.HORIZONTAL);
        sep1.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep1.maxWidth(maxWidth);
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        sep2.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep2.maxWidth(maxWidth);
        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        sep3.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep3.maxWidth(maxWidth);
        Separator sep4 = new Separator(Orientation.HORIZONTAL);
        sep4.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep4.maxWidth(maxWidth);
        Separator sep5 = new Separator(Orientation.HORIZONTAL);
        sep5.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep5.maxWidth(maxWidth);
        Label lblBodyArea = new Label(
                "Body Area Injured: (Check ALL that apply)");
        Label lblTypeofInjury = new Label(
                "Type of Injury: (Check ALL That Apply");

        lblBodyArea.setFont(new Font(16));
        lblTypeofInjury.setFont(new Font(16));
        mainBox = new VBox();
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(15));
        mainBox.setMinWidth(maxWidth + 15);
        mainBox.setMaxWidth(maxWidth + 20);
        mainBox.getChildren().addAll(formTitle, errorMsg, createHeader(), sep1,
                createRegisteredWorkArea(), sep2, createIncidentInfo(), sep3,
                lblBodyArea, createBodyAreaInjured(), sep4, lblTypeofInjury,
                createTypeOfInjury(), sep5, createMidSection(),
                createBottomBox());
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);
        mainPane.setMinWidth(maxWidth + 15);
        mainPane.setMaxWidth(maxWidth + 20);
        mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        localStage = new Stage();
        localStage.setResizable(false);
        localStage.setMaxHeight(920);
        localStage.setMinWidth(720);
        localStage.initModality(Modality.APPLICATION_MODAL);
        localStage.initOwner(parentStage);
        localStage.setScene(new Scene(mainPane));
        localStage.setTitle("New Incident Report");
        localStage.show();

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
        cboWorkAreas.getSelectionModel().select(0);
        radOtherWorkArea = new RadioButton("Other");
        radOtherWorkArea.setPadding(new Insets(0, 35, 0, 0));
        cboWorkAreas.setMinWidth(250);
        otherWorkArea.setMinWidth(195);
        otherWorkArea.setDisable(true);
        radOtherWorkArea.setOnAction(event -> {
            if ( radOtherWorkArea.isSelected() )
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

        box.getChildren().addAll(registeredWorkArea, cboWorkAreas,
                radOtherWorkArea, otherWorkArea);
        box.setSpacing(15);

        mainBox.getChildren().addAll(topBox, box);

        return mainBox;
    }

    /**
     * Purpose: creates the section of the form where the user can select
     * multiple checkboxes outlining the area of the injured body.
     *
     */
    private HBox createBodyAreaInjured()
    {
        HBox hbox = new HBox();
        VBox midBox = new VBox();

        Button addInjury = new Button("->");
        Button removeInjury = new Button("<-");

        allBodyAreas.addAll("Head ", "Neck", "Chest", "Left Shoulder",
                "Right Shoulder", "Pelvis or Hips", "Left Buttocks",
                "Right Buttocks", "Left Ankle", "Right Ankle", "Left Ear",
                "Right Ear ", "Toes", "Left Eye ", "Right Eye ",
                "left Upper Back ", "Right Upper Back ", "Left Lower Back ",
                "Right Lower Back ", "Left Foot ", "Right Foot ",
                "Right Upper Arm ", "Left Upper Arm ", "Right Lower Arm ",
                "Left Lower Arm", "Nose", "Fingers", "Right Elbow",
                "Left Elbow", "Right Wrist", "Left Wrist", "Left Hand",
                "Right Hand ", "Left Upper Leg ", "Right Upper Leg ",
                "Left Lower Leg ", "Right Lower Leg ", "Left Knee ",
                "Right Knee ", "Abdomen or Stomach");

        lsvAllBodyAreas = new ListView<String>(allBodyAreas);
        lsvInjuredBodyAreas = new ListView<String>(injuredBodyAreas);
        lsvAllBodyAreas.setMaxHeight(200);
        lsvInjuredBodyAreas.setMaxHeight(200);

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
                        // Add that item to the injured body areas
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

        typesOfInjuries = new ArrayList<CheckBox>();

        otherHBox.getChildren().addAll(
                chkOtherType = new CheckBox("Other (Specify) :"),
                txtOtherType = new TextField());

        gridPaneType.add(chkBruise = new CheckBox("Bruise or Contusion"), 0, 0);

        gridPaneType.add(chkScrape = new CheckBox("Scrape or Abrasion"), 0, 1);
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
        typesOfInjuries.add(chkBruise);
        typesOfInjuries.add(chkScrape);
        typesOfInjuries.add(chkSwelling);
        typesOfInjuries.add(chkBurn);
        typesOfInjuries.add(chkCut);
        typesOfInjuries.add(chkSprain);
        typesOfInjuries.add(chkInsectbite);
        typesOfInjuries.add(chkImbedded);
        typesOfInjuries.add(chkFall);
        typesOfInjuries.add(chkFoundOnFloor);
        typesOfInjuries.add(chkSelfAggro);
        typesOfInjuries.add(chkP2PAggro);
        typesOfInjuries.add(chkP2SAggro);
        typesOfInjuries.add(chkVehichleIncident);
        typesOfInjuries.add(chkPropertyDmg);
        typesOfInjuries.add(chkOtherType);
        txtOtherType.setDisable(true);

        chkOtherType.setOnAction(event -> {

            if ( chkOtherType.isSelected() )
            {
                txtOtherType.setDisable(false);
            }
            else
            {
                txtOtherType.setDisable(true);
            }
        });

        Label lblProtEquipment = new Label(
                "Protective Equipment Used at Time of Incident?");

        txtAProtEquip = new TextArea();
        txtAProtEquip.setWrapText(true);
        txtAProtEquip.setMaxWidth(200);
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
    private VBox createMidSection()
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
        incidentDescription.setMaxWidth(maxWidth - 20);
        incidentDescription.setWrapText(true);

        incidentFactors = new TextArea();
        incidentFactors.setMaxHeight(maxHeight);
        incidentFactors.setMaxWidth(maxWidth - 20);
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

        lblWitnessedByStaff = new Label("Witnessed by staff:");
        lblWitnessByParticipants = new Label("Witnessed by participant(s):");
        lblWitnessByOther = new Label("Witnessed by other - explain:");

        int spacing = 5;

        top.setSpacing(spacing);
        chkUnwitnessed = new CheckBox("Unwitnessed");
        chkUnwitnessed.setSelected(true);
        if ( chkUnwitnessed.isSelected() )
        {
            lblWitnessByOther.setDisable(true);
            lblWitnessByParticipants.setDisable(true);
            lblWitnessedByStaff.setDisable(true);
            staffWitness.setDisable(true);
            participantWitness.setDisable(true);
            otherWitness.setDisable(true);
        }

        chkUnwitnessed.setOnAction(event -> {
            if ( chkUnwitnessed.isSelected() )
            {
                lblWitnessByOther.setDisable(true);
                lblWitnessByParticipants.setDisable(true);
                lblWitnessedByStaff.setDisable(true);
                staffWitness.setDisable(true);
                participantWitness.setDisable(true);
                otherWitness.setDisable(true);

                staffWitness.clear();
                participantWitness.clear();
                otherWitness.clear();
            }
            else
            {
                lblWitnessByOther.setDisable(false);
                lblWitnessByParticipants.setDisable(false);
                lblWitnessedByStaff.setDisable(false);
                staffWitness.setDisable(false);
                participantWitness.setDisable(false);
                otherWitness.setDisable(false);
            }
        });

        topGrid.add(chkUnwitnessed, 0, 0);

        topGrid.add(lblWitnessedByStaff, 0, 1);
        topGrid.add(lblWitnessByParticipants, 0, 2);
        topGrid.add(lblWitnessByOther, 0, 3);

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
        txtTimeReportedVerbally.setMinWidth(320);

        Label lblDateReportedVerbally = new Label("Date reported verbally : ");
        Label lblTimeReported = new Label("Time reported: ");
        personalSupportsCoordinator = new RadioButton(
                "Personal Supports Coordinator");
        personalSupportsManager = new RadioButton("Personal Supports Manager");
        noVerbalReport = new CheckBox("No Verbal Report Given");
        noVerbalReport.setSelected(true);
        if ( noVerbalReport.isSelected() )
        {
            personalSupportsCoordinator.setDisable(true);
            personalSupportsManager.setDisable(true);
            dateVerballyReported.setDisable(true);
            txtTimeReportedVerbally.setDisable(true);
        }
        noVerbalReport.setOnAction(event -> {
            if ( noVerbalReport.isSelected() )
            {
                personalSupportsCoordinator.setDisable(true);
                personalSupportsManager.setDisable(true);
                dateVerballyReported.setDisable(true);
                txtTimeReportedVerbally.setDisable(true);
            }
            else
            {
                personalSupportsCoordinator.setDisable(false);
                personalSupportsManager.setDisable(false);
                dateVerballyReported.setDisable(false);
                txtTimeReportedVerbally.setDisable(false);
            }
        });

        group2.getToggles().addAll(personalSupportsCoordinator,
                personalSupportsManager);

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
        txtReportWrittenBy.setMinWidth(570);

        Label lblTimeReported = new Label("Time reported: ");
        Label lblReportWrittenBy = new Label("Report Written By : ");
        Label lblDateReportWritten = new Label("Date Report Written : ");

        topBox.getChildren().addAll(lblDateReportWritten, dateReportWritten,
                lblTimeReported, txtTimeReportWritten);
        topBox.setSpacing(15);
        bottomBox.getChildren().addAll(lblReportWrittenBy, txtReportWrittenBy);
        bottomBox.setSpacing(25);
        box.getChildren().addAll(topBox, bottomBox);
        box.setSpacing(10);

        return box;
    }

    /**
     * Purpose: Create the bottom part of the form
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
        sep.maxWidth(maxWidth);
        sep.maxHeight(maxWidth);
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep2.maxWidth(maxWidth);
        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        sep.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
                null)));
        sep3.maxWidth(maxWidth);
        Label lblIncident = new Label("Incident: ");
        Label lblVerbalReport = new Label("Verbal Report");

        btnSubmit = new Button("Submit");
        buttonBox.getChildren().add(btnSubmit);
        buttonBox.setAlignment(Pos.CENTER);

        btnSubmit.setOnAction(event -> {
            submitForm();
        });

        mainBox.getChildren().addAll(lblIncident, createIncidentWitness(), sep,
                createIncidentReportedTo(), sep2, lblVerbalReport,
                createVerbalReport(), sep3, createReportWritten(), buttonBox);

        mainBox.setSpacing(15);

        return mainBox;
    }

    /**
     * 
     * Purpose: Submit the form once all validation is passed.
     */
    private void submitForm()
    {
        String formMessage = validateForm();
        if ( Boolean.parseBoolean(formMessage) )
        {
            errorMsg.setText("");
            // Form is valid in here.... do all the information inserting in
            // here.

            // Check to see how many types of injures were selected.

            ArrayList<String> selectedTypesOfInjuries = new ArrayList<String>();

            for ( CheckBox chk : typesOfInjuries )
            {
                if ( chk.isSelected() )
                {
                    selectedTypesOfInjuries.add(chk.getText());
                }
            }
            // If the other type of injury is selected, we'll add it to the list
            if ( chkOtherType.isSelected() )
            {
                selectedTypesOfInjuries.add(txtOtherType.getText());
            }

            String[] selectedInjuries = selectedTypesOfInjuries
                    .toArray(new String[selectedTypesOfInjuries.size()]);

            Witness[] witnesses = new Witness[3];

            if ( !staffWitness.getText().isEmpty() )
            {
                witnesses[0] = new Witness(staffWitness.getText(), "S");
            }
            if ( !participantWitness.getText().isEmpty() )
            {
                witnesses[1] = new Witness(participantWitness.getText(), "P");
            }
            if ( !otherWitness.getText().isEmpty() )
            {
                witnesses[2] = new Witness(otherWitness.getText(), "P");
            }

            String[] injuredAreas = injuredBodyAreas
                    .toArray(new String[injuredBodyAreas.size()]);
            IncidentReportFormHelper irfh = new IncidentReportFormHelper();

            String reported = noVerbalReport.isSelected() + "";

            irfh.saveIncidentInfo(personInjured.getText(),
                    dateOfIncident.getValue(), time.getText(),
                    location.getText(), txtAProtEquip.getText(),
                    incidentDescription.getText(), incidentFactors.getText(),
                    txtPersonReportedToName.getText(),
                    dateReportToPerson.getValue(),
                    txtTimeReportedToPerson.getText(), reported,
                    dateVerballyReported.getValue(),
                    txtTimeReportedVerbally.getText(),
                    dateReportWritten.getValue(),
                    txtTimeReportWritten.getText(),
                    txtReportWrittenBy.getText(), injuredAreas,
                    selectedInjuries, witnesses);

            localStage.close();

        }
        else
        {
            // scrolls the pane all the way to the top
            mainPane.setVvalue(0);

            errorMsg.setText("You have missing required fields. Click here to see.");
            errorMsg.setOnMouseClicked(event -> {
                Stage stage = new Stage();
                stage.initOwner(parentStage);
                stage.initModality(Modality.APPLICATION_MODAL);
                PopUpMessage popup = new PopUpMessage(formMessage, stage);
                popup.stage.showAndWait();
                popup.stage.setOnCloseRequest(close -> {
                    errorMsg.setText("");
                });
            });

        }
    }

    /**
     * 
     * Purpose: Validate the incident report form
     * 
     * @return a null string if the form was valid, otherwise a string returning
     *         a message about missing data.
     */
    private String validateForm()
    {
        boolean formValid = true;
        StringBuilder errorMessage = new StringBuilder();
        if ( personInjured.getText().isEmpty() )
        {
            errorMessage.append("\tThe participant that was injured.\n");
            formValid = false;
        }
        if ( radOtherWorkArea.isSelected() )
        {
            if ( otherWorkArea.getText().isEmpty() )
            {
                errorMessage.append("\tThe other registered work area.\n");
                formValid = false;
            }
        }
        if ( dateOfIncident.getValue() == null )
        {
            errorMessage.append("\tThe date of the incident.\n");
            formValid = false;
        }
        if ( time.getText().isEmpty() )
        {
            errorMessage.append("\tThe time of the incident.\n");
            formValid = false;
        }
        if ( location.getText().isEmpty() )
        {
            errorMessage.append("\tThe exact location of incident.\n");
            formValid = false;
        }
        if ( injuredBodyAreas.isEmpty() )
        {
            errorMessage.append("\tThe body areas injured.\n");
            formValid = false;
        }
        boolean typeOfInjureySelected = false;
        for ( CheckBox chk : typesOfInjuries )
        {
            if ( chk.isSelected() )
            {
                typeOfInjureySelected = true;
            }
        }
        if ( !typeOfInjureySelected )
        {
            errorMessage.append("\tThe type of injury.\n");
            formValid = false;
        }
        if ( incidentDescription.getText().isEmpty() )
        {
            errorMessage
                    .append("\tThe description of how exactly the incident occured.\n");
            formValid = false;
        }
        if ( incidentFactors.getText().isEmpty() )
        {
            errorMessage
                    .append("\tThe factors contributing to the incident.\n");
            formValid = false;
        }
        if ( !chkUnwitnessed.isSelected() )
        {
            if ( (staffWitness.getText().isEmpty()
                    && participantWitness.getText().isEmpty() && otherWitness
                    .getText().isEmpty()) )
            {
                errorMessage.append("\tThe witnesses of the incident.\n");
                formValid = false;
            }
        }
        if ( txtPersonReportedToName.getText().isEmpty() )
        {
            errorMessage
                    .append("\tThe person's name this incident was reported to.\n");
            formValid = false;
        }
        if ( dateReportToPerson.getValue() == null )
        {
            errorMessage
                    .append("\tThe date this incident was reported to a person.\n");
            formValid = false;
        }
        if ( txtTimeReportedToPerson.getText().isEmpty() )
        {
            errorMessage
                    .append("\tThe time this incident was reported to a person.\n");
        }
        if ( !(noVerbalReport.isSelected()) )
        {
            if ( dateVerballyReported.getValue() == null )
            {
                errorMessage
                        .append("\tThe date this incident was reported verbally.\n");
                formValid = false;
            }
            if ( txtTimeReportedVerbally.getText().isEmpty() )
            {
                formValid = false;
                errorMessage
                        .append("\tThe time this incident was reported verbally.\n");
            }
        }
        if ( dateReportWritten == null )
        {
            formValid = false;
            errorMessage
                    .append("\tThe date this incident report was written.\n");
        }
        if ( txtTimeReportWritten.getText().isEmpty() )
        {
            formValid = false;
            errorMessage
                    .append("\tThe time this incident report was written.\n");
        }
        if ( txtReportWrittenBy.getText().isEmpty() )
        {
            formValid = false;
            errorMessage.append("\tThe person who wrote this report.\n");
        }
        if ( formValid == false )
        {
            errorMessage.insert(0, "You have missing required field:\n\n");
            errorMessage.append("\n\n\n\n");
        }
        else
        {
            errorMessage.append("true");
        }

        return errorMessage.toString();
    }
}
