import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Purpose: Display a seizure description form tab
 * 
 * @author cst205, cst207
 *
 */
public class SeizureDescriptionFormGUI
{
    public static final String FORM_TITLE = "Seizure Description Form";

    private Stage addEditSeizureMedicationStage;

    private SeizureMedicationTableViewController smTVC;
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

    private Button btnClear = new Button("Clear Seizure");
    private Button btnSave = new Button("Save Information");

    private String cosmoId;

    /**
     * purpose:constructor for the seizure gui Constructor for the
     * SeizureDescriptionFormGUI class.
     * 
     * @param seizureTab
     * @param loggedInUser
     */
    public SeizureDescriptionFormGUI(Tab seizureTab, StaffAccount loggedInUser,
            String cosmoID)
    {
        this.parentTab = seizureTab;
        this.loggedInUser = loggedInUser;
        this.cosmoId = cosmoID;
    }

    /**
     * purpose: this returns the seizure tab that will be displayed Purpose:
     * 
     * @param cosmoId
     * @return
     */
    public Tab ShowSeizureForm()
    {
        Label title = new Label(FORM_TITLE);
        HBox titleBox = new HBox();
        title.setFont(new Font(22));

        mainBox = new VBox();

        // make sure basic staff can't save
        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            this.btnSave.setVisible(false);
            this.btnClear.setVisible(false);
        }

        btnClear.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                clearSeizure();
            }
        });
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
                    lastUpdatedTxt.setText(values[7]);
                }
            }
        });
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnClear, btnSave);
        titleBox.getChildren().addAll(title, buttonBox);
        titleBox.setSpacing(250);

        mainBox.getChildren().addAll(titleBox, createBasicSeizureInfo(),
                seizureMedicationTable());
        mainBox.setPadding(new Insets(10, 10, 10, 10));

        mainContainer.setContent(mainBox);
        mainContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainContainer.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainContainer.setHmax(mainBox.getWidth());
        assignSeizureInfo();
        this.parentTab.setContent(mainContainer);
        return parentTab;
    }

    /**
     * 
     * Purpose:GUI to create the basic layout of the seizure form tab
     * 
     * 
     * @return VBox that will be displayed
     */
    private VBox createBasicSeizureInfo()
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
     */
    private void assignSeizureInfo()
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
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if ( info[7].length() > 5 )
        {
            try
            {
                date = inputFormatter.parse(info[7]);
            }
            catch ( ParseException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            SimpleDateFormat outputFormatter = new SimpleDateFormat();
            outputFormatter.applyPattern("dd-MMM-yyyy");

            lastUpdatedTxt.setText(outputFormatter.format(date));
        }
    }

    /**
     * 
     * Purpose: to take in information from the database and product a table
     * that shows all medications the participant is taking for seizures
     * 
     * @return
     */
    private VBox seizureMedicationTable()
    {
        VBox medicationBox = new VBox();
        HBox medicationHeader = new HBox(10);
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        Label medicationLbl = new Label("Current Seizure Medication(s)");
        medicationLbl.setPadding(new Insets(0, 290, 0, 0));
        medicationLbl.setFont(new Font(18));
        smTVC = new SeizureMedicationTableViewController(cosmoId);

        addBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // Open addNewParticipant Window
                addEditSeizureMedicationStage = new Stage();
                addEditSeizureMedicationStage
                        .setTitle("Add Seizure Medication");

                addEditSeizureMedicationStage.setScene(new Scene(
                        addSeizureMedicationPopUp(), 325, 200));
                addEditSeizureMedicationStage
                        .initModality(Modality.APPLICATION_MODAL);
                addEditSeizureMedicationStage
                        .initOwner(participantDetailsGUI.participantMainStage);
                addEditSeizureMedicationStage.setResizable(false);
                addEditSeizureMedicationStage.show();
            }
        });
        deleteBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                try
                {
                    removeMedication(smTVC.getSelectedPK());
                }
                catch ( Exception e1 )
                {

                    e1.printStackTrace();
                }
            }
        });
        editBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                try
                {
                    if ( smTVC.getSelectedPK() != null
                            && !smTVC.getSelectedPK().equals("null") )
                    {
                        // Open addNewParticipant Window
                        addEditSeizureMedicationStage = new Stage();
                        addEditSeizureMedicationStage
                                .setTitle("Edit Seizure Medication");

                        addEditSeizureMedicationStage.setScene(new Scene(
                                editSeizureMedicationPopUp(smTVC
                                        .getSelectedPK()), 325, 200));
                        addEditSeizureMedicationStage
                                .initModality(Modality.APPLICATION_MODAL);
                        addEditSeizureMedicationStage
                                .initOwner(participantDetailsGUI.participantMainStage);
                        addEditSeizureMedicationStage.setResizable(false);
                        addEditSeizureMedicationStage.show();
                    }

                    else
                    {
                        Stage stage = new Stage();
                        Scene scene;
                        // tell the user to select a user to delete
                        PopUpMessage messageBox = new PopUpMessage(
                                "Please select a medication to remove.", stage);

                        scene = new Scene(messageBox.root, 300, 75);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                }
                catch ( Exception e1 )
                {

                    e1.printStackTrace();
                }
            }
        });
        smTVC.seizureMedicationTable.setMaxWidth(700);
        medicationHeader.getChildren().addAll(medicationLbl, addBtn, editBtn,
                deleteBtn);
        medicationHeader.setPadding(new Insets(0, 0, 10, 0));
        medicationBox.getChildren().addAll(medicationHeader,
                smTVC.seizureMedicationTable);
        medicationBox.setPadding(new Insets(10, 10, 10, 5));

        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            addBtn.setVisible(false);
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }

        return medicationBox;
    }

    /**
     * Method for updating a medication associated with a aseizure.
     * 
     * @param medicationName : The name of the medication to be updated.
     * @return gridpane
     */
    private GridPane editSeizureMedicationPopUp( String medicationName )
    {

        //
        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label medicationNameLbl = new Label("Medication Name: ");
        Label dosageLbl = new Label("Dosage: ");
        Label timesGivenLbl = new Label("Times Given: ");

        DatabaseHelper db = new DatabaseHelper();
        ResultSet medication = db.select("dosage, timesGiven", "medication",
                "medicationName = '" + medicationName + "' AND cosmoID = '"
                        + cosmoId + "'", "");

        String dosage = "";
        String timesGiven = "";

        try
        {
            while ( medication.next() )
            {

                dosage = medication.getString(1);
                timesGiven = medication.getString(2);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        TextField medicationNameTxt = new TextField();
        TextField dosageTxt = new TextField();
        TextField timesGivenTxt = new TextField();

        medicationNameTxt.setText(medicationName);
        dosageTxt.setText(dosage);
        timesGivenTxt.setText(timesGiven);
        grid.add(medicationNameLbl, 0, 1);
        grid.add(dosageLbl, 0, 2);
        grid.add(timesGivenLbl, 0, 3);
        grid.add(lblWarning, 1, 0);
        grid.add(medicationNameTxt, 1, 1);
        grid.add(dosageTxt, 1, 2);
        grid.add(timesGivenTxt, 1, 3);

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Button editMedicationBtn = new Button("Edit");
        editMedicationBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // call create participant on medical administrator with the
                // text passed in
                String result = Medication.updateMedication(
                        medicationNameTxt.getText(), dosageTxt.getText(),
                        timesGivenTxt.getText(), "Seizure Medication", cosmoId,
                        medicationName);

                // if no error message is recieved then close this window and
                // refresh the table
                if ( result.equals("") )
                {
                    addEditSeizureMedicationStage.close();
                    smTVC.refreshTable(cosmoId);
                }
                // if there is an error message, display it
                else
                {
                    lblWarning.setTextFill(Color.FIREBRICK);
                    lblWarning.setText(result);
                }
            }
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // sets all values to default
                medicationNameTxt.setText("");
                dosageTxt.setText("");
                timesGivenTxt.setText("");

                lblWarning.setText("");

            }

        });
        HBox addHbox = new HBox();
        HBox resetHbox = new HBox();
        addHbox.getChildren().add(editMedicationBtn);
        resetHbox.getChildren().add(resetBtn);
        grid.add(resetHbox, 0, 4);
        grid.add(addHbox, 1, 4);
        return grid;

    }

    /**
     * Method for adding a medication associated with a seizure
     * 
     * @return gridpane
     */
    private GridPane addSeizureMedicationPopUp()
    {
        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label medicationNameLbl = new Label("Medication Name: ");
        Label dosageLbl = new Label("Dosage: ");
        Label timesGivenLbl = new Label("Times Given: ");

        TextField medicationNameTxt = new TextField();
        TextField dosageTxt = new TextField();
        TextField timesGivenTxt = new TextField();

        grid.add(medicationNameLbl, 0, 1);
        grid.add(dosageLbl, 0, 2);
        grid.add(timesGivenLbl, 0, 3);
        grid.add(lblWarning, 1, 0);
        grid.add(medicationNameTxt, 1, 1);
        grid.add(dosageTxt, 1, 2);
        grid.add(timesGivenTxt, 1, 3);

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Button addMedicationBtn = new Button("Add");
        addMedicationBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // call create participant on medical administrator with the
                // text passed in
                String result = Medication.createMedication(
                        medicationNameTxt.getText(), dosageTxt.getText(),
                        timesGivenTxt.getText(), "Seizure Medication", cosmoId);

                // if no error message is recieved then close this window and
                // refresh the table
                if ( result.equals("") )
                {
                    addEditSeizureMedicationStage.close();
                    smTVC.refreshTable(cosmoId);
                }
                // if there is an error message, display it
                else
                {
                    lblWarning.setTextFill(Color.FIREBRICK);
                    lblWarning.setText(result);
                }
            }
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // sets all values to default
                medicationNameTxt.setText("");
                dosageTxt.setText("");
                timesGivenTxt.setText("");

                lblWarning.setText("");

            }

        });
        HBox addHbox = new HBox();
        HBox resetHbox = new HBox();
        addHbox.getChildren().add(addMedicationBtn);
        resetHbox.getChildren().add(resetBtn);
        grid.add(resetHbox, 0, 4);
        grid.add(addHbox, 1, 4);
        return grid;
    }

    /**
     * Purpose: This will take the selected user from the table, confirm that
     * you wish to delete them, if so, will delete the selected user, then
     * refresh the table of accounts
     * 
     * @param medicationName The medication that you will remove
     * @author Niklaas Neijmeijer CST207
     */
    private void removeMedication( String medicationName )
    {
        Stage stage = new Stage();
        Scene scene;

        // if the selected username is not null
        if ( medicationName != null && medicationName != "null" )
        {
            // if the selected username is the same as the current username

            PopUpCheck checkBox = new PopUpCheck("Are you sure you want to "
                    + "delete " + medicationName + "?", stage);

            scene = new Scene(checkBox.root, 300, 75);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(participantDetailsGUI.participantMainStage);
            stage.showAndWait();

            // when the user is removed from the database
            if ( checkBox.result )
            {
                if ( Medication.removeMedication(medicationName, cosmoId) )
                {
                    // this.sTVCont.removeViewableUser(username);
                    smTVC.refreshTable(cosmoId);
                }

            }

        }
        // pop up a message saying that no user has been selected to delete
        else
        {
            // tell the user to select a user to delete
            PopUpMessage messageBox = new PopUpMessage(
                    "Please select a medication to remove.", stage);

            scene = new Scene(messageBox.root, 300, 75);
            stage.setScene(scene);
            stage.showAndWait();
        }

    }

    /**
     * Method for clearing the seizure associated with the participant
     */
    private void clearSeizure()
    {
        Stage stage = new Stage();
        Scene scene;

        PopUpCheck checkBox = new PopUpCheck("Are you sure you want to "
                + "remove this individual's seizure information?", stage);

        scene = new Scene(checkBox.root, 400, 75);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(participantDetailsGUI.participantMainStage);
        stage.showAndWait();

        // when the user is removed from the database
        if ( checkBox.result )
        {
            DatabaseHelper db = new DatabaseHelper();
            ResultSet seizureIDSet = db.select("seizureID", "seizures",
                    "cosmoID = '" + cosmoId + "'", "");

            String seizureID = "";

            // check if there is already a seizure for that participant
            try
            {
                seizureIDSet.next();
                seizureID = seizureIDSet.getString(1);
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }
            if ( seizureID.length() != 0 )
            {
                ResultSet medicationIDSet = db.select("medicationID",
                        "seizureMedication", "seizureID = '" + seizureID + "'",
                        "");
                String medicationID = "";
                try
                {
                    while ( medicationIDSet.next() )
                    {
                        medicationID = medicationIDSet.getString(1);
                        db.delete("seizureMedication", "medicationID = '"
                                + medicationID + "'");
                        db.delete("medication", "medicationID = '"
                                + medicationID + "'");
                    }
                }
                catch ( SQLException e )
                {
                    e.printStackTrace();
                }
                db.delete("seizures", "seizureID = '" + seizureID + "'");
                smTVC.refreshTable(cosmoId);
                seizureTypeTxt.setText("");
                warningLbl.setText("");
                descriptionTxt.setText("");
                frequencyTxt.setText("");
                durationTxt.setText("");
                aftermathTxt.setText("");
                aftermathAssistanceTxt.setText("");
                emergencyTreatmentTxt.setText("");
                lastUpdatedTxt.setText("");

            }
        }
    }
}
