import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HealthStatusForm
{
    public static final String FORM_TITLE = "Health Status Information";
    public static final int SPACING = 10;
    private VBox mainBox;

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

    /**
     * 
     * Constructor for the HealthStatus class.
     * 
     * @param ctrl the pane, or box that this will be placed on
     */
    public HealthStatusForm(Tab healthStatusTab, StaffAccount loggedInUser)
    {
        this.parentTab = healthStatusTab;

    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     */
    public Tab showHealthStatusInfo( int cosmoId )
    {
        Label title = new Label(FORM_TITLE);
        title.setFont(new Font(22));
        mainBox = new VBox();

        mainBox.getChildren().addAll(title, createDiagnosisInfo(cosmoId),
                createMedicalConditions(cosmoId));
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        assignDiagnosisInfo(cosmoId+"");
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
     * Purpose: Create the medical conditions table
     * 
     * @return: HBox containing the medical conditions
     */
    private VBox createMedicalConditions( int cosmoId )
    {
        VBox mainBox = new VBox();

        Label medicalCondtionsLbl = new Label("Medical Conditions");
        medicalCondtionsLbl.setFont(Font.font(22));

        MedicalConditionsTableViewController medicalConditionsTable = new MedicalConditionsTableViewController(
                cosmoId + "");

        TableView<MedicalCondition> table = medicalConditionsTable.conditionTable;
        table.setMaxWidth(500);
        mainBox.getChildren().addAll(medicalCondtionsLbl, table);
        mainBox.setPadding(new Insets(10, 10, 10, 5));

        return mainBox;
    }

    /**
     * 
     * Purpose: Create the diagnositc controls
     * 
     * @return
     */
    private HBox createDiagnosisInfo( int cosmoId )
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
     * Purpose: assign diagnosis info for particiapnts
     */
    private void assignDiagnosisInfo(String cosmoId)
    {
        HealthStatusInformationHelper helper = new HealthStatusInformationHelper();
        String[] info = helper.retrieveHealthStatusInfo(cosmoId);
        familyPhysicianTxt.setText(info[0]);
        physicianPhoneTxt.setText(info[1]);
        participantDiagnosisTxt.setText(info[2]);
        tylenolGiven.setSelected(Boolean.parseBoolean(info[3]));
        careGiverPermission.setSelected(Boolean.parseBoolean(info[4]));
        
    }
}