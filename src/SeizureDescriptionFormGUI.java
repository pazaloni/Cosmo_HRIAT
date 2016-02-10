import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView.EditEvent;
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
    
    private VBox mainBox;
    
    private Tab parentTab;

    private StaffAccount loggedInUser;

    private List<Control> editableItems = new ArrayList<Control>();
    
    private TextField seizureType;
    private TextField description;
    private TextField frequency;
    private TextField duration;
    private TextField aftermath;
    private TextField EmergencyTreatment;
    private TextField lastUpdated;
    
    /**
     * purpose:constructor for the seizure gui
     * Constructor for the SeizureDescriptionFormGUI class.
     * @param seizureTab
     * @param loggedInUser
     */
    public SeizureDescriptionFormGUI(Tab seizureTab, StaffAccount loggedInUser)
    {
        this.parentTab = seizureTab;
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
        title.setFont(new Font(22));
        title.setPadding(new Insets(0,0,10,0));
        mainBox = new VBox();

        mainBox.getChildren().addAll(title, 
                createBasicSeizureInfo(cosmoId));
        mainBox.setPadding(new Insets(10, 10, 10, 10));

        this.parentTab.setContent(mainBox);
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
        
        Button saveButton = new Button("Save");
        
        TextField dateCompletedTxt = new TextField();
        TextField typeTxt = new TextField();
        TextField descriptionTxt = new TextField();
        TextField frequencyTxt = new TextField();
        TextField durationTxt = new TextField();
        TextField treatmentTxt = new TextField();
        TextField postTypicalTxt = new TextField();
        TextField postAssistanceTxt = new TextField();
       
        editableItems.add(dateCompletedTxt);
        editableItems.add(typeTxt);
        editableItems.add(descriptionTxt);
        editableItems.add(frequencyTxt);
        editableItems.add(durationTxt);
        editableItems.add(treatmentTxt);
        editableItems.add(postTypicalTxt);
        editableItems.add(postAssistanceTxt);
        
        updateBox.getChildren().addAll(dateCompletedLbl, 
                dateCompletedTxt, saveButton);
        
        seizureInfoBox.getChildren().addAll(seizureBoolCheck, descriptionLbl, 
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
