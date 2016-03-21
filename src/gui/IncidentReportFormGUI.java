package gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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

    private ObservableList<String> workAreas, allBodyAreas, injuredBodyAreas;

    private ListView<String> lsvAllBodyAreas, lsvInjuredBodyAreas;

    private ComboBox<String> cboWorkAreas;

    private ScrollPane mainPane;
    private VBox mainBox;

    private Stage parentStage;

    private TextField month, day, year, time, location, staffWitness,
            participantWitness, otherWitness;

    private TextField personInjured;

    private TextField otherWorkArea, locationOfPain, txtManagerName,
            txtOtherName, txtDateReportedTo, txtDateVerbal,
            txtDateReportWritten;

    private TextArea incidentDescription, incidentFactors, txtAProtEquip;

    private RadioButton am, pm;
    private RadioButton other, personalSupportsManager,
            personalSupportsCoordinator, noVerbalReport;

    private RadioButton manager, assistantManager, otherReportedTo;

    private CheckBox chkBruise, chkScrape, chkSwelling, chkBurn, chkCut,
            chkSprain, chkInsectbite, chkImbedded, chkFall, chkFoundOnFloor,
            chkSelfAggro, chkP2PAggro, chkP2SAggro, chkVehichleIncident,
            chkPropertyDmg, chkOtherType;

    private CheckBox chkUnwitnessed, chkWitnessedByStaff,
            chkWitnessByParticipants, chkWitnessByOther;

    private TextField txtOther;
    private TextField txtOtherType;
    private List<CheckBox> typesOfInjuries;

    private StaffAccount loggedInStaff;

    public IncidentReportFormGUI(Stage stage, StaffAccount loggedInStaff)
    {
        this.parentStage = stage;
        this.loggedInStaff = loggedInStaff;
        workAreas = FXCollections.observableArrayList();
        allBodyAreas = FXCollections.observableArrayList();
        injuredBodyAreas = FXCollections.observableArrayList();

    }

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
        mainBox.setMinWidth(700);
        mainBox.getChildren().addAll(formTitle, createHeader(), sep1,
                createRegisteredWorkArea(), sep2, createIncidentInfo(), sep3,
                lblBodyArea, createBodyAreaInjured(), sep4, lblTypeofInjury,
                createTypeOfInjury(), sep5, createMidSecion(),
                createBottomBox());
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);
        mainPane.setMinWidth(700);
        mainPane.setPadding(new Insets(5, 5, 5, 385));

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
        personInjured.setMinWidth(450);

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
        HBox midBox = new HBox();
        HBox bottomBox = new HBox();

        Label lblDateOfIncident = new Label("Date of Incident:");
        Label lblTimeOfIncident = new Label("Time of Incident:");
        Label lblExactLocaitonOfIncident = new Label(
                "Exact Location of Where Incident Occured:");

        month = new TextField();
        month.setPromptText("Month");
        day = new TextField();
        day.setPromptText("Day");
        year = new TextField();
        year.setPromptText("Year");
        time = new TextField();
        location = new TextField();
        location.setMinWidth(450);

        am = new RadioButton("am");
        pm = new RadioButton("pm");

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(am, pm);

        midBox.getChildren().addAll(lblTimeOfIncident, time, am, pm);
        topBox.getChildren().addAll(lblDateOfIncident, month, day, year);
        bottomBox.getChildren().addAll(lblExactLocaitonOfIncident, location);

        VBox box = new VBox();
        box.getChildren().addAll(topBox, midBox, bottomBox);
        topBox.setSpacing(10);
        bottomBox.setSpacing(10);
        midBox.setSpacing(10);
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
        registeredWorkArea.setFont(new Font(16));
        // GridPane grid = new GridPane();
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
        topBox.getChildren().add(registeredWorkArea);
        box.getChildren().addAll(cboWorkAreas, other, otherWorkArea);
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

        lsvAllBodyAreas.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle( MouseEvent click )
            {
                if ( click.getClickCount() == 2 )
                {
                    if ( lsvAllBodyAreas.getSelectionModel().getSelectedItem() != null )
                    {
                        injuredBodyAreas.add(lsvAllBodyAreas
                                .getSelectionModel().getSelectedItem());
                        lsvInjuredBodyAreas.setItems(injuredBodyAreas);
                        allBodyAreas.remove(lsvAllBodyAreas.getSelectionModel()
                                .getSelectedItem());
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
            System.out.println(injuredBodyAreas.toString());
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
     * @return an hbox containg all the required elements
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
     * Purpose :Create the bottom part of the form
     * 
     * @return a vbox containing all the required elements
     */
    private VBox createBottomBox()
    {
        VBox mainBox = new VBox();
        HBox top = new HBox();
        HBox top2 = new HBox();
        HBox top3 = new HBox();
        HBox top4 = new HBox();
        HBox top5 = new HBox();
        HBox top6 = new HBox();
        HBox top7 = new HBox();
        HBox top8 = new HBox();
        HBox top9 = new HBox();

        Label lblIncident = new Label("Incident: ");
        Label lblIncidentReportedTo = new Label("Incident reported to:");

        Label lblVerbalReport = new Label("Verbal report Given to: ");
        Label lblDateReported = new Label("Date Reported");
        Label lblDateReported2 = new Label("Date Reported");
        Label lblDateReportWritten = new Label("Date Report Written");
        Label lblReportWrittenBy = new Label("Report Written By");

        chkUnwitnessed = new CheckBox("Unwitnessed");
        chkWitnessedByStaff = new CheckBox("Witnessed by staff:");
        chkWitnessByParticipants = new CheckBox("Witnessed by participant(s):");
        chkWitnessByOther = new CheckBox("Witnessed by other - explain:");

        ToggleGroup group = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();

        manager = new RadioButton("Manager");
        assistantManager = new RadioButton("Assistant Manager");
        otherReportedTo = new RadioButton("Other (specify)");
        personalSupportsCoordinator = new RadioButton(
                "Personal Supports Coordinator");
        personalSupportsManager = new RadioButton("Personal Supports Manager");
        noVerbalReport = new RadioButton("No Verbal Report Given");

        staffWitness = new TextField();
        participantWitness = new TextField();
        otherWitness = new TextField();

        txtManagerName = new TextField();
        txtOtherName = new TextField();
        txtDateReportedTo = new TextField();
        txtDateVerbal = new TextField();
        txtDateReportWritten = new TextField();

        staffWitness.setMinWidth(450);
        participantWitness.setMinWidth(500);
        otherWitness.setMinWidth(500);
        txtManagerName.setMinWidth(350);
        txtOtherName.setMinWidth(300);
        txtDateReportedTo.setMinWidth(300);
        txtDateVerbal.setMinWidth(350);
        txtDateReportWritten.setMinWidth(350);

        group.getToggles().addAll(manager, assistantManager, otherReportedTo);
        group2.getToggles().addAll(personalSupportsCoordinator,
                personalSupportsManager, noVerbalReport);

        top.getChildren().addAll(lblIncident, chkUnwitnessed,
                chkWitnessedByStaff, staffWitness);
        top2.getChildren().addAll(chkWitnessByParticipants, participantWitness);
        top3.getChildren().addAll(chkWitnessByOther, otherWitness);
        top4.getChildren().addAll(lblIncidentReportedTo, manager,
                assistantManager, txtManagerName);
        top5.getChildren().addAll(otherReportedTo, txtOtherName,
                lblDateReported, txtDateReportedTo);
        top6.getChildren().addAll(lblVerbalReport, personalSupportsManager,
                personalSupportsCoordinator);
        top7.getChildren().addAll(noVerbalReport, lblDateReported2,
                txtDateVerbal);
        top8.getChildren().addAll(lblDateReportWritten, txtDateReportWritten);
        top9.getChildren().addAll(lblReportWrittenBy);

        int spacing = 5;

        top.setSpacing(spacing);
        top2.setSpacing(spacing);
        top3.setSpacing(spacing);
        top4.setSpacing(spacing);
        top5.setSpacing(spacing);
        top6.setSpacing(spacing);
        top7.setSpacing(spacing);
        top8.setSpacing(spacing);
        top9.setSpacing(spacing);

        mainBox.getChildren().addAll(top, top2, top3, top4, top5, top6, top7,
                top8, top9);
        mainBox.setSpacing(15);
        return mainBox;
    }
}
