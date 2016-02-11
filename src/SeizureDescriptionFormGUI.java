import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SeizureDescriptionFormGUI
{
    public static final String FORM_TITLE = "Seizure Description Form";
    
    private ScrollPane mainContainer = new ScrollPane();
    private VBox mainBox;
    
    private Tab parentTab;

    private StaffAccount loggedInUser;

    private List<Control> editableItems = new ArrayList<Control>();
    
    private TextField seizureType;
    private TextArea description;
    private TextArea frequency;
    private TextArea duration;
    private TextArea aftermath;
    private TextArea EmergencyTreatment;
    private TextArea lastUpdated;
    
    private Button btnSave = new Button("Save");
    
    /**
     * purpose:constructor for the seizure gui
     * Constructor for the SeizureDescriptionFormGUI class.
     * @param seizureTab
     * @param loggedInUser
     */
    public SeizureDescriptionFormGUI(Tab seizureTab, StaffAccount loggedInUser)
    {
        this.parentTab = seizureTab;
        this.loggedInUser = loggedInUser;
    }
    
    
    /**
     * purpose: this returns the seizure tab that will be displayed
     * Purpose:
     * @param cosmoId
     * @return
     */
    public Tab ShowSeizureForm(String cosmoId)
    {
        Label title = new Label(FORM_TITLE);
        HBox titleBox = new HBox();
        title.setFont(new Font(22));
        
        
        mainBox = new VBox();

        if(!(this.loggedInUser instanceof MedicalAdministrator))
        {
            this.btnSave.setVisible(false);
        }
        
        titleBox.getChildren().addAll(title, btnSave);
        titleBox.setSpacing(300);
        
        mainBox.getChildren().addAll(titleBox, 
                createBasicSeizureInfo(cosmoId));
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        
        mainContainer.setContent(mainBox);
        mainContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainContainer.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainContainer.setHmax(mainBox.getWidth());
        
        this.parentTab.setContent(mainContainer);
        return parentTab;
    }
    
    private VBox createBasicSeizureInfo(String cosmoId)
    {
        VBox mainBox = new VBox();
        
        HBox updateBox = new HBox();
        VBox seizureInfoBox = new VBox();
        VBox postSeizureInfoBox = new VBox();
        
        Label dateCompletedLbl = new Label("Date Completed: ");
        Label booleanQuestionLbl = new Label("Does this individual have "
                + "a seizure disorder/epilepsy?");
        Label typeQuestionLbl = new Label("What type of seizure does this"
                + " individual have?");
        
        Label personalSeizureInfoLbl = new Label("Personal Seizure Information");
        personalSeizureInfoLbl.setFont(new Font(18));
        
        Label descriptionLbl = new Label("Description of typical occurance");
        Label frequencyLbl = new Label("Seizure frequency");
        Label durationLbl = new Label("Typical duration");
        Label treatmentLbl = new Label("Emergency Treatment/Hospital required?"
                + " Describe the situation");
        
        Label postSeizureLbl = new Label("After a seizure is over");
        postSeizureLbl.setFont(new Font(18));
        
        Label postTypicalLbl = new Label("What is the participants "
                + "typical behavior?");
        Label postAssistanceLbl = new Label("What assistance is needed?");
        
        CheckBox seizureBoolCheck = new CheckBox();
        seizureBoolCheck.setText("Yes/No (box checked if yes)");
        seizureBoolCheck.setPadding(new Insets(0,300,0,0));
        
        
        TextField dateCompletedTxt = new TextField();
        TextField typeTxt = new TextField();
        TextArea descriptionTxt = new TextArea();
        descriptionTxt.setWrapText(true);
        descriptionTxt.setPrefRowCount(3);
        TextArea frequencyTxt = new TextArea();
        frequencyTxt.setWrapText(true);
        frequencyTxt.setPrefRowCount(3);
        TextArea durationTxt = new TextArea();
        durationTxt.setWrapText(true);
        durationTxt.setPrefRowCount(3);
        TextArea treatmentTxt = new TextArea();
        treatmentTxt.setWrapText(true);
        treatmentTxt.setPrefRowCount(3);
        TextArea postTypicalTxt = new TextArea();
        treatmentTxt.setWrapText(true);
        treatmentTxt.setPrefRowCount(3);
        TextArea postAssistanceTxt = new TextArea();
        postAssistanceTxt.setWrapText(true);
        postAssistanceTxt.setPrefRowCount(3);
       
        editableItems.add(dateCompletedTxt);
        editableItems.add(typeTxt);
        editableItems.add(descriptionTxt);
        editableItems.add(frequencyTxt);
        editableItems.add(durationTxt);
        editableItems.add(treatmentTxt);
        editableItems.add(postTypicalTxt);
        editableItems.add(postAssistanceTxt);
        
        updateBox.getChildren().addAll(seizureBoolCheck, dateCompletedLbl, 
                 dateCompletedTxt);
        
        seizureInfoBox.getChildren().addAll(descriptionLbl, 
                descriptionTxt, frequencyLbl, frequencyTxt, durationLbl, 
                durationTxt, treatmentLbl, treatmentTxt);
        seizureInfoBox.setPadding(new Insets(10,20,10,20));
        
        postSeizureInfoBox.getChildren().addAll(postTypicalLbl, postTypicalTxt,
                postAssistanceLbl, postAssistanceTxt);
        postSeizureInfoBox.setPadding(new Insets(10,20,10,20));
        
        mainBox.getChildren().addAll(updateBox, personalSeizureInfoLbl,
                seizureInfoBox, postSeizureLbl, postSeizureInfoBox);
        
        
        
        
        
        return mainBox;
        
    }
    
    

}
