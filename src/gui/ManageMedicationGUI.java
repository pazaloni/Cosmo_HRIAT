package gui;
import object.Medication;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * Purpose: Display the windows for adding and updating Medication for
 * participants
 * 
 * @author CIMP
 * @version 1.0
 */
public class ManageMedicationGUI
{

    private Stage parentStage;

    private Label lblMessage;

    private GridPane mainPane;

    private TextField medicationName;
    private TextField medicationDosage;
    private TextField timesGiven;
    private TextArea reason;

    private Label lblMedicationName;
    private Label lblMedicationDosage;
    private Label lblTimesGiven;
    private Label lblReason;

    private Button btnAdd, btnCancel;

    public ManageMedicationGUI(Stage parentStage)
    {
        this.parentStage = parentStage;
        this.medicationName = new TextField();
        this.medicationDosage = new TextField();
        this.timesGiven = new TextField();
        this.reason = new TextArea();

        this.lblMedicationDosage = new Label("Dosage:");
        this.lblMedicationName = new Label("Name:");
        this.lblReason = new Label("Reason:");
        this.lblTimesGiven = new Label("Times Given");

        this.btnAdd = new Button("Add");
        this.btnCancel = new Button("Cancel");

        mainPane = new GridPane();
    }

    /**
     * 
     * Purpose: show the window for the new medication entry
     * 
     * @param cosmoID the participant that will be receiving the medication
     */
    public void showAddMedication( String cosmoID )
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblMedicationName, 0, 1);
        mainPane.add(lblMedicationDosage, 0, 2);
        mainPane.add(lblTimesGiven, 0, 3);
        mainPane.add(lblReason, 0, 4);

        mainPane.add(medicationName, 1, 1);
        mainPane.add(medicationDosage, 1, 2);
        mainPane.add(timesGiven, 1, 3);
        mainPane.add(reason, 1, 4);
        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(10);
        mainPane.setVgap(10);

        mainPane.setPadding(new Insets(10, 10, 10, 10));

        btnAdd.setOnAction(event -> {
            String result = Medication.createMedication(
                    medicationName.getText(), medicationDosage.getText(),
                    timesGiven.getText(), reason.getText(), cosmoID);
            if ( result.equals("") )
            {
                localStage.close();
            }
            else
            {
                lblMessage.setText(result);
            }
        });
        btnCancel.setOnAction(event -> {
            localStage.close();
        });
        Scene scene = new Scene(mainPane, 600, 450);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add a medication entry");
        localStage.showAndWait();
    }

    /**
     * 
     * Purpose: update a medication entry for a participant
     * 
     * @param cosmoID the participant that will have the medication entry
     *            updated
     * @param med The medication that will be displayed to be edited.
     */
    public void showUpdateMedication( String cosmoID, Medication med )
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblMedicationName, 0, 1);
        mainPane.add(lblMedicationDosage, 0, 2);
        mainPane.add(lblTimesGiven, 0, 3);
        mainPane.add(lblReason, 0, 4);

        mainPane.add(medicationName, 1, 1);
        mainPane.add(medicationDosage, 1, 2);
        mainPane.add(timesGiven, 1, 3);
        mainPane.add(reason, 1, 4);

        //Set the values to 
        medicationName.setText(med.getMedicationName().get());
        medicationDosage.setText(med.getDosage().get());
        timesGiven.setText(med.getTimesGiven().get());
        reason.setText(med.getReason().get());
        
        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(10);
        mainPane.setVgap(10);

        mainPane.setPadding(new Insets(10, 10, 10, 10));
        btnAdd.setText("Update");
        btnAdd.setOnAction(event -> {
            String result = Medication.updateMedication(
                    medicationName.getText(), medicationDosage.getText(),
                    timesGiven.getText(), reason.getText(), cosmoID,
                    med.getMedicationName().get());
            if ( result.equals("") )
            {
                localStage.close();
            }
            else
            {
                lblMessage.setText(result);
            }
        });
        //If they want to click cancel for some reason, close the window
        btnCancel.setOnAction(event->{
            localStage.close();
        });

        Scene scene = new Scene(mainPane, 600, 450);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Update a medication entry");
        localStage.showAndWait();

    }
}
