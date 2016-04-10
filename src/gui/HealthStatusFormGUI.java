package gui;
import helpers.HealthStatusInformationHelper;

import java.awt.Dialog.ModalExclusionType;
import java.util.ArrayList;
import java.util.List;

import object.Allergies;
import object.QueryResult;
import object.Medication;
import controllers.AllergiesTableViewController;
import controllers.MedicalConditionsTableViewController;
import controllers.ProgressNotesTableViewController;
import controllers.MedicationsTableViewController;
import core.MedicalAdministrator;
import core.PopUpCheck;
import core.PopUpMessage;
import core.StaffAccount;
import javafx.collections.ModifiableObservableListBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private Label familyPhysicianTxt;
    private TextField participantDiagnosisTxt;
    private Label physicianPhoneTxt;
    private Label dateCompletedTxt;    
    
    private Label lblMessage; 
    
    private Stage parentStage;

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
    public HealthStatusFormGUI(Tab healthStatusTab, StaffAccount loggedInUser,
            Stage parentStage)
    {
        this.parentTab = healthStatusTab;
        this.loggedInUser = loggedInUser;
        this.parentStage = parentStage;
    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     * 
     * @param cosmoId: The cosmoId for the participant
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

       lblMessage = new Label("");
		titleBox.getChildren().addAll(title, lblMessage ,btnSave);
        titleBox.setSpacing(100);
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

        Label familyPhysicianLbl = new Label("Family Physician: ");
        Label participantDiagnosisLbl = new Label("Participant Diagnosis: ");
        Label physicianPhoneLbl = new Label("Physician Phone Number: ");
        Label dateCompletedLbl = new Label("Date Completed: ");

        familyPhysicianTxt = new Label();
        participantDiagnosisTxt = new TextField();
        physicianPhoneTxt = new Label();
        dateCompletedTxt = new Label();

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
        btnSave.setOnAction(event ->{
        	lblMessage.setText("");
        	String[] info = new String[4];
        	info[0] = participantDiagnosisTxt.getText();
        	info[1] = tylenolGiven.isSelected()+"";
        	info[2] = careGiverPermission.isSelected()+"";
        	info[3] = otherInfoTxt.getText();
        	
        	boolean success = helper.saveHealthStatusInfo(info,cosmoID);
        	
        	if(success)
        	{
        	            	    
        	    assignDiagnosisInfo(cosmoId);
        	    lblMessage.setTextFill(Color.BLUE);
        	    lblMessage.setText("Save successful");
        	    
        	}
        	else
        	{
        	    lblMessage.setTextFill(Color.RED);
                lblMessage.setText("The save was unsuccesful.");
        	}
       
        	
        });
        // Following is just adding stuff to their boxes and setting some
        // spacing and alignment
        familyPhysicianHBox.getChildren().addAll(familyPhysicianLbl,
                familyPhysicianTxt);
        familyPhysicianHBox.setSpacing(125);
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

        MedicalConditionsTableViewController controller = new MedicalConditionsTableViewController(
                cosmoId);
        TableView<QueryResult> table = controller.conditionTable;

        //Add the medical condition 
        btnAddMedicalCondition
                .setOnMouseClicked(event -> {
                    ManageMedicalConditionGUI manageMedicalCondition = new ManageMedicalConditionGUI(
                            parentStage);
                    manageMedicalCondition.showAddMedicalCondition(cosmoId);
                    controller.refreshTable(cosmoId);
                });
        //Editing a medical condition
        btnEditMedicalCondition.setOnMouseClicked(event -> {
            QueryResult condition = controller.getSelectedMedicalCondition();
            if(condition==null)
            {
                Stage stage = new Stage();
                PopUpMessage popup = new PopUpMessage(
                        "Must select a medical condition to edit", stage);
                Scene scene = new Scene(popup.root, 300, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
            }
            else
            {
                ManageMedicalConditionGUI manageMedicalCondition = new ManageMedicalConditionGUI(
                        parentStage);            	
            	manageMedicalCondition.showUpdateMedicalCondition(condition, cosmoId);
            	controller.refreshTable(cosmoId);
            }
        });
        btnDeleteMedicalCondition.setOnMouseClicked(event -> {
            QueryResult condition = controller.getSelectedMedicalCondition();
            if(condition==null)
            {
                Stage stage = new Stage();
                PopUpMessage popup = new PopUpMessage(
                        "Must select a medical condition to delete", stage);
                Scene scene = new Scene(popup.root, 300, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
            }
            else
            {
                Stage stage = new Stage();
                PopUpCheck popup = new PopUpCheck(
                        "Are you sure you want to delete the selected medical condition ?",
                        stage);
                Scene scene = new Scene(popup.root, 450, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
                if(popup.runCheck())
                {
                QueryResult.deleteCondition(table.getSelectionModel()
                        .getSelectedItem(), cosmoID);
                }
                controller.refreshTable(cosmoID);
            }
        });

        if(!(this.loggedInUser instanceof MedicalAdministrator))
        {
            btnAddMedicalCondition.setVisible(false);
            btnEditMedicalCondition.setVisible(false);
            btnDeleteMedicalCondition.setVisible(false);
        }
        
        buttons.getChildren().addAll(btnAddMedicalCondition,
                btnEditMedicalCondition, btnDeleteMedicalCondition);

        controls.getChildren().addAll(buttons);

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
     * @param cosmoID The participant cosmoId which is used to pull the
     *            information from the database
     *
     * @return VBox with a label and a table view
     */
    private VBox createAllergiesInfo( String cosmoID )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label allergiesLbl = new Label("Allergies");
        allergiesLbl.setFont(Font.font(22));

        Button btnAddAllergy = new Button("Add");
        Button btnEditAllergy = new Button("Edit");
        Button btnDelAllergy = new Button("Delete");

        buttons.getChildren().addAll(btnAddAllergy, btnEditAllergy,
                btnDelAllergy);

        controls.getChildren().addAll(buttons);

        AllergiesTableViewController controller = new AllergiesTableViewController(
                cosmoID + "");
        TableView<Allergies> table = controller.allergiesTable;
        btnEditAllergy.setOnAction(event -> {
            Allergies allergy = controller.getSelectedAllergy();
            // If there is no allergy selected, then display a message
                if ( allergy == null )
                {
                    Stage stage = new Stage();
                    PopUpMessage popup = new PopUpMessage(
                            "Must select an allergy to edit", stage);
                    Scene scene = new Scene(popup.root, 300, 75);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(parentStage);
                    stage.setScene(scene);
                    stage.showAndWait();
                }
                else
                {
                    ManageAllergyGUI manageAllergies = new ManageAllergyGUI(
                            parentStage);
                    manageAllergies.showUpdateAllergy(table.getSelectionModel()
                            .getSelectedItem(), cosmoID);
                    controller.refreshTable(cosmoID);
                }
            });

        btnAddAllergy
                .setOnAction(event -> {
                    ManageAllergyGUI manageAllergies = new ManageAllergyGUI(
                            parentStage);
                    manageAllergies.showAddAllergy(cosmoID);
                    controller.refreshTable(cosmoID);
                });

        btnDelAllergy
                .setOnAction(event -> {

                    Allergies allergy = controller.getSelectedAllergy();

                    if ( allergy == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select an allergy to delete", stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        Stage stage = new Stage();
                        PopUpCheck popup = new PopUpCheck(
                                "Are you sure you want to delete the selected allergy ?",
                                stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                        if(popup.runCheck())
                        {
                        Allergies.deleteAllergy(table.getSelectionModel()
                                .getSelectedItem(), cosmoID);
                        }
                        controller.refreshTable(cosmoID);
                    }

                });
        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(485);

        if(!(this.loggedInUser instanceof MedicalAdministrator))
        {
            btnAddAllergy.setVisible(false);
            btnEditAllergy.setVisible(false);
            btnDelAllergy.setVisible(false);
        }
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
        MedicationsTableViewController controller = new MedicationsTableViewController(
                cosmoId);
        TableView<Medication> table = controller.medicationsTable;

        btnAddMedication.setOnAction(event -> {

            ManageMedicationGUI manageMedicaiton = new ManageMedicationGUI(
                    parentStage);
            manageMedicaiton.showAddMedication(cosmoId);
            controller.refreshTable(cosmoId);
        });

        //Handle the editing of a medication 
        btnEditMedication
                .setOnAction(event -> {
                    //Get the selected medication 
                	Medication med = controller.getSelectedMedication();
                    if ( med == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select an entry to edit", stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        ManageMedicationGUI manageMedication = new ManageMedicationGUI(
                                parentStage);
                        manageMedication.showUpdateMedication(cosmoId, med);
                        controller.refreshTable(cosmoId);
                    }

                });
        btnDeleteMedication.setOnAction(event->{
        	Medication med = controller.getSelectedMedication();
        	if(med == null)
        	{
                Stage stage = new Stage();
                PopUpMessage popup = new PopUpMessage(
                        "Must select a medication entry to delete", stage);
                Scene scene = new Scene(popup.root, 300, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
        	}
        	else
        	{
                Stage stage = new Stage();
                PopUpCheck popup = new PopUpCheck(
                        "Are you sure you want to delete the selected medication entry ?",
                        stage);
                Scene scene = new Scene(popup.root, 450, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
                if(popup.runCheck())
                {
                Medication.removeMedication(med.getMedicationName().get(),cosmoID);
                controller.refreshTable(cosmoID);
                }
        		
        	}
        });
        buttons.getChildren().addAll(btnAddMedication, btnEditMedication,
                btnDeleteMedication);

        controls.getChildren().addAll(buttons);

        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(450);
        if(!(this.loggedInUser instanceof MedicalAdministrator))
        {
            btnAddMedication.setVisible(false);
            btnDeleteMedication.setVisible(false);
            btnEditMedication.setVisible(false);
        }

        box.getChildren().addAll(medicationsLbl,table ,controls);

        return box;
    }
}