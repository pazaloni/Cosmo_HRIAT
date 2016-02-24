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
 * Purpose: Display the window to add and manage medical conditions for
 * participants
 *
 * @author CIMP
 * @version 1.0
 */
public class ManageMedicalConditionGUI
{

    private Stage parentStage;

    private Label lblMessage;

    private GridPane mainPane;

    private TextField conditionName;
    private TextArea conditionDescription;

    private Label lblConditionName;
    private Label lblConditionDescription;

    private Button btnAdd, btnCancel;

    public ManageMedicalConditionGUI(Stage parentStage)
    {
        this.parentStage = parentStage;

        this.conditionName = new TextField();
        this.conditionDescription = new TextArea();
        this.lblConditionDescription = new Label("Description");
        this.lblConditionName = new Label("Name");

        this.btnAdd = new Button("Add");
        this.btnCancel = new Button("Cancel");

        mainPane = new GridPane();
    }

    /**
     * Purpose: show the window for the addition of new medical condition 
     *  
     * @param cosmoID the participant that will be getting the new medical condition 
     */
    public void showAddMedicalCondition( String cosmoID )

    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblConditionName, 0, 1);
        mainPane.add(lblConditionDescription, 0, 2);

        mainPane.add(conditionName, 1, 1);
        mainPane.add(conditionDescription, 1, 2);

        conditionName.setMaxWidth(250);
        conditionDescription.setMaxWidth(250);
        
        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(15);
        mainPane.setVgap(15);

        mainPane.setPadding(new Insets(10, 10, 10, 10));

        btnAdd.setOnAction(event -> {
            String result = MedicalCondition.createMedicalCondition(
                    new MedicalCondition(conditionName.getText(),
                            conditionDescription.getText()), cosmoID);
            if ( result.equals("") )
            {
                localStage.close();
            }
            else
            {
                lblMessage.setText(result);
            }
        });

        Scene scene = new Scene(mainPane, 400, 300);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add a medical condition entry");
        localStage.showAndWait();
        
    }

    /**
     * Purpose: Edit a medical condition for a specified participant 
     * 
     * @param condition The condition to be edited 
     * @param cosmoID the participant that will have the condition edited 
     */
    public void showUpdateMedicalCondition(MedicalCondition condition ,String cosmoID)
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblConditionName, 0, 1);
        mainPane.add(lblConditionDescription, 0, 2);

        mainPane.add(conditionName, 1, 1);
        mainPane.add(conditionDescription, 1, 2);
        
        conditionName.setMaxWidth(250);
        conditionDescription.setMaxWidth(250);
        
        conditionName.setText(condition.getCondition().get());
        conditionDescription.setText(condition.getDescripion().get());
        
        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(15);
        mainPane.setVgap(15);

        mainPane.setPadding(new Insets(10, 10, 10, 10));
        btnAdd.setText("Update");
        btnAdd.setOnAction(event -> {
        	MedicalCondition newCondition = new MedicalCondition
        			(conditionName.getText(), conditionDescription.getText());
        	
            String result = MedicalCondition.updateMedicalCondition(newCondition,condition ,cosmoID);
            if ( result.equals("") )
            {
                localStage.close();
            }
            else
            {
                lblMessage.setText(result);
            }
        });
        btnCancel.setOnAction(event ->
        {
        	localStage.close();
        });

        Scene scene = new Scene(mainPane, 400, 300);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Edit a medical condition entry");
        localStage.showAndWait();
    }
}
