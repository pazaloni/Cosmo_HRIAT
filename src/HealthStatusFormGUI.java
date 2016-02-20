import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * Purpose: Represents the health status form GUI, which by default pulls
 * information
 *
 * @author CIMP
 * @version 1.0
 */
public class HealthStatusFormGUI
{
    public static final String FORM_TITLE = "Health Status Information";
    public static final int SPACING = 10;

    private String cosmoID;

    private ScrollPane mainBox;

    // The Control that this will be placed in
    private Tab parentTab;

    private StaffAccount loggedInUser;

    private List<Control> editableItems = new ArrayList<Control>();

    private TextField familyPhysicianTxt;
    private TextField participantDiagnosisTxt;
    private TextField physicianPhoneTxt;
    private TextField dateCompletedTxt;

    private CheckBox tylenolGiven;
    private CheckBox careGiverPermission;
    private TextArea otherInfoTxt;

    private Button btnSave = new Button("Save");

    HealthStatusInformationHelper helper = new HealthStatusInformationHelper();

    /**
     * 
     * Constructor for the HealthStatus class.
     * 
     * @param ctrl the pane, or box that this will be placed on
     */
    public HealthStatusFormGUI(Tab healthStatusTab, StaffAccount loggedInUser)
    {
        this.parentTab = healthStatusTab;
        this.loggedInUser = loggedInUser;
    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     * 
     * @param cosmoId: The cosmoId for the paritcipant
     */
    public Tab showHealthStatusInfo( String cosmoId )
    {
        this.cosmoID = cosmoId;

        Label title = new Label(FORM_TITLE);
        HBox titleBox = new HBox();

        title.setFont(new Font(22));

        mainBox = new ScrollPane();

        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            this.btnSave.setVisible(false);
        }

        titleBox.getChildren().addAll(title, btnSave);
        titleBox.setSpacing(300);
        VBox contentBox = new VBox();

        btnSave.setMinWidth(150);

        Label otherLbl = new Label("Other Information");
        otherLbl.setFont(Font.font(22));
        otherInfoTxt = new TextArea();
        otherInfoTxt.setMaxWidth(700);
        otherInfoTxt.setMaxHeight(150);

        contentBox.getChildren().addAll(titleBox, createDiagnosisInfo(cosmoId),
                createMedicalConditions(cosmoId), createAllergiesInfo(cosmoId),
                createMedicationInfo(cosmoId), otherLbl, otherInfoTxt);
        contentBox.setSpacing(15);

        mainBox.setContent(contentBox);

        mainBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainBox.setHmax(contentBox.getWidth());

        mainBox.setPadding(new Insets(10, 10, 10, 10));

        assignDiagnosisInfo(cosmoId + "");
        this.parentTab.setContent(mainBox);

        return parentTab;
    }

    /**
     * 
     * Purpose: this method will make everything that can be editable editable
     */
    public void editHealthStatusInfo()
    {

    }

    /**
     * 
     * Purpose: Create the diagnosis controls
     * 
     * @return
     */
    private HBox createDiagnosisInfo( String cosmoId )
    {
        HBox mainBox = new HBox();
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();

        HBox familyPhysicianHBox = new HBox();

        HBox participantDiagnosisHBox = new HBox();
        HBox physicianPhoneNumberHBox = new HBox();
        HBox dateCompletedHBox = new HBox();
        HBox checkBoxesHBox = new HBox();

        Label familyPhysicianLbl = new Label("Family Phyisician: ");
        Label participantDiagnosisLbl = new Label("Participant Diagnosis: ");
        Label physicianPhoneLbl = new Label("Phyisician Phone Number: ");
        Label dateCompletedLbl = new Label("Date Completed: ");

        familyPhysicianTxt = new TextField();
        participantDiagnosisTxt = new TextField();
        physicianPhoneTxt = new TextField();
        dateCompletedTxt = new TextField();

        tylenolGiven = new CheckBox();
        tylenolGiven.setText("Tylenol Given");
        careGiverPermission = new CheckBox();
        careGiverPermission.setText("Caregivers Permission given");

        editableItems.add(careGiverPermission);
        editableItems.add(tylenolGiven);
        editableItems.add(dateCompletedTxt);
        editableItems.add(physicianPhoneTxt);
        editableItems.add(participantDiagnosisTxt);
        editableItems.add(familyPhysicianTxt);

        // Following is just adding stuff to their boxes and setting some
        // spacing and alignment
        familyPhysicianHBox.getChildren().addAll(familyPhysicianLbl,
                familyPhysicianTxt);
        familyPhysicianHBox.setSpacing(SPACING);
        familyPhysicianHBox.setAlignment(Pos.CENTER_RIGHT);
        participantDiagnosisHBox.getChildren().addAll(participantDiagnosisLbl,
                participantDiagnosisTxt);
        participantDiagnosisHBox.setSpacing(SPACING);
        participantDiagnosisHBox.setAlignment(Pos.CENTER_RIGHT);
        physicianPhoneNumberHBox.getChildren().addAll(physicianPhoneLbl,
                physicianPhoneTxt);
        physicianPhoneNumberHBox.setSpacing(SPACING);
        physicianPhoneNumberHBox.setAlignment(Pos.CENTER_RIGHT);
        dateCompletedHBox.getChildren().addAll(dateCompletedLbl,
                dateCompletedTxt);
        dateCompletedHBox.setSpacing(SPACING);
        dateCompletedHBox.setAlignment(Pos.CENTER_RIGHT);
        checkBoxesHBox.getChildren().addAll(tylenolGiven, careGiverPermission);
        checkBoxesHBox.setSpacing(SPACING);

        leftVBox.getChildren().addAll(familyPhysicianHBox,
                participantDiagnosisHBox, checkBoxesHBox);
        leftVBox.setSpacing(SPACING);
        rightVBox.getChildren().addAll(physicianPhoneNumberHBox,
                dateCompletedHBox);
        rightVBox.setSpacing(SPACING);
        mainBox.getChildren().addAll(leftVBox, rightVBox);
        mainBox.setSpacing(SPACING * 2);

        return mainBox;
    }

    /**
     * 
     * Purpose: assign diagnosis info for participants
     * 
     * @param cosmoId the participant to assign the diagnosis information for
     */
    private void assignDiagnosisInfo( String cosmoId )
    {

        String[] info = helper.retrieveHealthStatusInfo(cosmoId);
        familyPhysicianTxt.setText(info[0]);
        physicianPhoneTxt.setText(info[1]);
        participantDiagnosisTxt.setText(info[2]);
        tylenolGiven.setSelected(Boolean.parseBoolean(info[3]));
        careGiverPermission.setSelected(Boolean.parseBoolean(info[4]));
        info[5] = info[5].substring(0, info[5].length() - 7);
        dateCompletedTxt.setText(info[5]);
        otherInfoTxt.setText(info[6]);

    }

    /**
     * 
     * Purpose: Create the medical conditions table
     * 
     * @return: HBox containing the medical conditions
     */
    private VBox createMedicalConditions( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label medicalCondtionsLbl = new Label("Medical Conditions");
        medicalCondtionsLbl.setFont(Font.font(22));

        Button btnAddMedicalCondition = new Button("Add");
        Button btnEditMedicalCondition = new Button("Edit");
        Button btnDeleteMedicalCondition = new Button("Delete");
        Button btnFinish = new Button("Finish");

        btnAddMedicalCondition.setOnMouseClicked(event -> {
            btnFinish.setDisable(false);
            btnEditMedicalCondition.setDisable(true);
            btnDeleteMedicalCondition.setDisable(true);
        });

        btnEditMedicalCondition.setOnMouseClicked(event -> {
            btnFinish.setDisable(false);
            btnAddMedicalCondition.setDisable(true);
            btnDeleteMedicalCondition.setDisable(true);
        });

        btnDeleteMedicalCondition.setOnMouseClicked(event -> {
            btnFinish.setDisable(false);
            btnAddMedicalCondition.setDisable(true);
            btnDeleteMedicalCondition.setDisable(true);
        });
        btnFinish.setOnMouseClicked(event ->{
            btnFinish.setDisable(true);
            btnAddMedicalCondition.setDisable(false);
            btnEditMedicalCondition.setDisable(false);
            btnDeleteMedicalCondition.setDisable(false);
        });

        buttons.getChildren().addAll(btnAddMedicalCondition,
                btnEditMedicalCondition, btnDeleteMedicalCondition,btnFinish);

        controls.getChildren().addAll(buttons);
        TableView<MedicalCondition> table = new MedicalConditionsTableViewController(
                cosmoId).conditionTable;
        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(380);

        box.getChildren().addAll(medicalCondtionsLbl, table, controls);
        return box;
    }

    /**
     * 
     * Purpose: Create the allergies info for the participant
     * 
     * @param cosmoId The participant cosmoId which is used to pull the
     *            information from the database
     *
     * @return VBox with a label and a table view
     */
    private VBox createAllergiesInfo( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label allergiesLbl = new Label("Allergies");
        allergiesLbl.setFont(Font.font(22));

        Button btnAddAllergy = new Button("Add");
        Button btnEditAllergy = new Button("Edit");
        Button btnDeleteAllergy = new Button("Delete");

        buttons.getChildren().addAll(btnAddAllergy, btnEditAllergy,
                btnDeleteAllergy);

        controls.getChildren().addAll(buttons);

        TableView<Allergies> table = new AllergiesTableViewController(cosmoId
                + "").allergiesTable;
        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(485);

        box.getChildren().addAll(allergiesLbl, table, controls);
        return box;
    }

    /**
     * 
     * Purpose: Create the medications info for the participant
     * 
     * @param cosmoId The participant cosmoId which is used to pull the
     *            information from the database
     *
     * @return VBox with a label and a table view
     */
    private VBox createMedicationInfo( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label medicationsLbl = new Label("Medications");
        medicationsLbl.setFont(Font.font(22));

        Button btnAddMedication = new Button("Add");
        Button btnEditMedication = new Button("Edit");
        Button btnDeleteMedication = new Button("Delete");

        buttons.getChildren().addAll(btnAddMedication, btnEditMedication,
                btnDeleteMedication);

        controls.getChildren().addAll(buttons);

        TableView<Medication> table = new MedicationsTableViewController(
                cosmoId).medicationsTable;
        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(450);

        box.getChildren().addAll(medicationsLbl, table, controls);
        return box;
    }
}