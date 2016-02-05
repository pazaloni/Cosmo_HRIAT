import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HealthStatusForm
{
    public static final String FORM_TITLE = "Health Status Information";

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
    public Tab showHealthStatusInfo()
    {
        mainBox = new VBox(5);
        mainBox.getChildren().add(createDiagnosisInfo());
    
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

    private HBox createDiagnosisInfo()
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
        familyPhysicianHBox.setSpacing(5);
        participantDiagnosisHBox.getChildren().addAll(participantDiagnosisLbl,
                participantDiagnosisTxt);
        participantDiagnosisHBox.setSpacing(5);
        physicianPhoneNumberHBox.getChildren().addAll(physicianPhoneLbl,
                physicianPhoneTxt);
        physicianPhoneNumberHBox.setSpacing(5);
        dateCompletedHBox.getChildren().addAll(dateCompletedLbl,
                dateCompletedTxt);
        dateCompletedHBox.setSpacing(5);

        checkBoxesHBox.getChildren().addAll(tylenolGiven, careGiverPermission);
        

        leftVBox.getChildren().addAll(familyPhysicianHBox,
                participantDiagnosisHBox, checkBoxesHBox);
        leftVBox.setSpacing(15);
        rightVBox.getChildren().addAll(physicianPhoneNumberHBox,
                dateCompletedHBox);
        rightVBox.setSpacing(15);

        
        mainBox.getChildren().addAll(leftVBox, rightVBox);
        mainBox.setSpacing(20);
        return mainBox;
    }
}
