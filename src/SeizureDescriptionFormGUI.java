import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Purpose: Display a seizure description form tab
 * 
 * @author cst205, cst207
 *
 */
public class SeizureDescriptionFormGUI
{
    public static final String FORM_TITLE = "Seizure Description Form";

    private ScrollPane mainContainer = new ScrollPane();
    private VBox mainBox;

    private Tab parentTab;

    private StaffAccount loggedInUser;

    private List<TextInputControl> editableItems = new ArrayList<TextInputControl>();
    private Label warningLbl;

    private TextField seizureTypeTxt;
    private TextArea descriptionTxt;
    private TextArea frequencyTxt;
    private TextArea durationTxt;
    private TextArea aftermathTxt;
    private TextArea emergencyTreatmentTxt;
    private Label lastUpdatedTxt;
    private TextArea aftermathAssistanceTxt;

    private Button btnSave = new Button("Save Information");

    /**
     * purpose:constructor for the seizure gui Constructor for the
     * SeizureDescriptionFormGUI class.
     * 
     * @param seizureTab
     * @param loggedInUser
     */
    public SeizureDescriptionFormGUI(Tab seizureTab, StaffAccount loggedInUser)
    {
        this.parentTab = seizureTab;
        this.loggedInUser = loggedInUser;
    }

    /**
     * purpose: this returns the seizure tab that will be displayed Purpose:
     * 
     * @param cosmoId
     * @return
     */
    public Tab ShowSeizureForm( String cosmoId )
    {
        Label title = new Label(FORM_TITLE);
        HBox titleBox = new HBox();
        title.setFont(new Font(22));

        mainBox = new VBox();

        // make sure basic staff can't save
        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            this.btnSave.setVisible(false);
        }
        btnSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                String[] values = new String[editableItems.size() + 1];
                for ( int i = 0; i < 7; i++ )
                {
                    values[i] = editableItems.get(i).getText();
                }
                if ( values[0].equals(" ") || values[0].length() == 0 )
                {
                    warningLbl.setTextFill(Color.FIREBRICK);
                    warningLbl
                            .setText("Please enter a seizure type before saving.");
                }
                else
                {
                    warningLbl.setTextFill(Color.BLUE);
                    warningLbl.setText("Save successful");
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat();
                    formatter.applyPattern("dd-MMM-yyyy");
                    values[7] = formatter.format(now);

                    SeizureDescriptionFormHelper helper = new SeizureDescriptionFormHelper();
                    helper.saveSeizureInformation(values, cosmoId);
                }
            }
        });
        titleBox.getChildren().addAll(title, btnSave);
        titleBox.setSpacing(340);

        mainBox.getChildren().addAll(titleBox, createBasicSeizureInfo(cosmoId),
                seizureMedicationTable(cosmoId));
        mainBox.setPadding(new Insets(10, 10, 10, 10));

        mainContainer.setContent(mainBox);
        mainContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainContainer.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainContainer.setHmax(mainBox.getWidth());
        assignSeizureInfo(cosmoId);
        this.parentTab.setContent(mainContainer);
        return parentTab;
    }

    /**
     * 
     * Purpose:GUI to create the basic layout of the seizure form tab
     * 
     * @param cosmoId
     *            of the participant in question
     * @return VBox that will be displayed
     */
    private VBox createBasicSeizureInfo( String cosmoId )
    {
        VBox mainBox = new VBox();

        HBox updateBox = new HBox();
        BorderPane subHeaderBox = new BorderPane();
        VBox seizureInfoBox = new VBox();
        VBox postSeizureInfoBox = new VBox();

        Label dateCompletedLbl = new Label("Date Completed: ");
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

        lastUpdatedTxt = new Label();
        warningLbl = new Label();
        warningLbl.setPadding(new Insets(0, 0, 0, 25));
        seizureTypeTxt = new TextField();
        descriptionTxt = new TextArea();
        descriptionTxt.setWrapText(true);
        descriptionTxt.setPrefRowCount(3);
        frequencyTxt = new TextArea();
        frequencyTxt.setWrapText(true);
        frequencyTxt.setPrefRowCount(3);
        durationTxt = new TextArea();
        durationTxt.setWrapText(true);
        durationTxt.setPrefRowCount(3);
        emergencyTreatmentTxt = new TextArea();
        emergencyTreatmentTxt.setWrapText(true);
        emergencyTreatmentTxt.setPrefRowCount(3);
        aftermathTxt = new TextArea();
        aftermathTxt.setWrapText(true);
        aftermathTxt.setPrefRowCount(3);
        aftermathAssistanceTxt = new TextArea();
        aftermathAssistanceTxt.setWrapText(true);
        aftermathAssistanceTxt.setPrefRowCount(3);

        editableItems.add(seizureTypeTxt);
        editableItems.add(descriptionTxt);
        editableItems.add(frequencyTxt);
        editableItems.add(durationTxt);
        editableItems.add(aftermathTxt);
        editableItems.add(aftermathAssistanceTxt);
        editableItems.add(emergencyTreatmentTxt);

        updateBox.getChildren().addAll(dateCompletedLbl, lastUpdatedTxt);
        subHeaderBox.setLeft(warningLbl);
        subHeaderBox.setRight(updateBox);
        updateBox.setPadding(new Insets(0, 30, 0, 0));
        seizureInfoBox.getChildren().addAll(typeQuestionLbl, seizureTypeTxt,
                descriptionLbl, descriptionTxt, frequencyLbl, frequencyTxt,
                durationLbl, durationTxt, treatmentLbl, emergencyTreatmentTxt);
        seizureInfoBox.setPadding(new Insets(10, 20, 10, 20));

        postSeizureInfoBox.getChildren().addAll(postTypicalLbl, aftermathTxt,
                postAssistanceLbl, aftermathAssistanceTxt);
        postSeizureInfoBox.setPadding(new Insets(10, 20, 10, 20));

        mainBox.getChildren().addAll(subHeaderBox, personalSeizureInfoLbl,
                seizureInfoBox, postSeizureLbl, postSeizureInfoBox);

        return mainBox;

    }

    /**
     * 
     * Purpose: take in information from the database and display it in the
     * proper fields, fields will be empty if the participant does not have
     * seizures
     * 
     * @param cosmoId
     *            of the participant
     */
    private void assignSeizureInfo( String cosmoId )
    {
        SeizureDescriptionFormHelper helper = new SeizureDescriptionFormHelper();
        String[] info = helper.retieveSeizureInformation(cosmoId);
        seizureTypeTxt.setText(info[0]);
        descriptionTxt.setText(info[1]);
        frequencyTxt.setText(info[2]);
        durationTxt.setText(info[3]);
        aftermathTxt.setText(info[4]);
        aftermathAssistanceTxt.setText(info[5]);
        emergencyTreatmentTxt.setText(info[6]);
        lastUpdatedTxt.setText(info[7]);

    }

    /**
     * 
     * Purpose: to take in information from the database and product a table
     * that shows all medications the participant is taking for seizures
     * 
     * @param cosmoId
     * @return
     */
    private VBox seizureMedicationTable( String cosmoId )
    {
        VBox medicationBox = new VBox();
        HBox medicationHeader = new HBox();
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");                
        Label medicationLbl = new Label("Current Seizure Medication(s)");
        medicationLbl.setPadding(new Insets(0,330,0,0));
        medicationLbl.setFont(new Font(18));
        TableView<SeizureMedication> table = new SeizureMedicationTableViewController(
                cosmoId).seizureMedicationTable;

        table.setMaxWidth(700);
        medicationHeader.getChildren().addAll(medicationLbl, addBtn, editBtn,
                deleteBtn);
        medicationHeader.setPadding(new Insets(0,0,10,0));
        medicationBox.getChildren().addAll(medicationHeader, table);
        medicationBox.setPadding(new Insets(10, 10, 10, 5));

        return medicationBox;
    }

}
