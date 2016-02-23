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
 * Purpose: Display a window for the new allergies
 * 
 * @author CIMP
 * @version 1.0
 */
public class ManageAllergyGUI
{
    private Stage parentStage;
    private Label lblMessage;

    private GridPane mainPane;

    private TextField allergicTo;
    private TextField allergyType;
    private TextArea allergyDescription;

    private Label lblAllergicTo;
    private Label lblAllergyType;
    private Label lblAllergyDescription;

    private Button btnAdd;
    private Button btnCancel;

    public ManageAllergyGUI(Stage parentStage)
    {
        this.parentStage = parentStage;
        this.allergicTo = new TextField();
        this.allergyType = new TextField();
        this.allergyDescription = new TextArea();
        this.allergyDescription.setMaxHeight(100);
        this.allergyDescription.setMaxWidth(200);

        this.lblAllergicTo = new Label("Allergic To:");
        this.lblAllergyType = new Label("Type:");
        this.lblAllergyDescription = new Label("Description:");

        this.btnAdd = new Button("Add");
        this.btnCancel = new Button("Cancel");

        mainPane = new GridPane();
    }

    /**
     * Purpose: show the window for new allergy
     * 
     * @param cosmoID: the participant that will be receiving the allergy     * 
     */
    public void showAddAllergy( String cosmoID )
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);
        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblAllergicTo, 0, 1);
        mainPane.add(lblAllergyType, 0, 2);
        mainPane.add(lblAllergyDescription, 0, 3);

        mainPane.add(allergicTo, 1, 1);
        mainPane.add(allergyType, 1, 2);
        mainPane.add(allergyDescription, 1, 3);

        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 4, 2, 1);

        mainPane.setHgap(10);
        mainPane.setVgap(10);

        mainPane.setPadding(new Insets(15, 15, 15, 15));

        btnAdd.setOnAction(event -> {
            String result = Allergies.createAllergy(allergicTo.getText(),
                    allergyType.getText(), allergyDescription.getText(),
                    cosmoID);
            lblMessage.setText(result);
            if ( result.equals("Success") )
            {
                localStage.close();
            }
        });
        btnCancel.setOnAction(event -> {
            localStage.close();
        });

        Scene scene = new Scene(mainPane, 330, 300);
        localStage.setScene(scene);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add an allergy");
        localStage.showAndWait();
    }

    /**
     * 
     * 
     * Purpose: Show the window to update a allergy
     * 
     * @param allergy: the allergy that will be updated
     * @param cosmoID: the participant that is getting the allergy updates
     * 
     */
    public void showUpdateAllergy( Allergies allergy, String cosmoID )
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);
        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblAllergicTo, 0, 1);
        mainPane.add(lblAllergyType, 0, 2);
        mainPane.add(lblAllergyDescription, 0, 3);

        mainPane.add(allergicTo, 1, 1);
        mainPane.add(allergyType, 1, 2);
        mainPane.add(allergyDescription, 1, 3);

        allergicTo.setText(allergy.getAllergicTo().get());
        allergyType.setText(allergy.getAllergyType().get());
        allergyDescription.setText(allergy.getDescription().get());

        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 4, 2, 1);

        mainPane.setHgap(10);
        mainPane.setVgap(10);

        // Handles the editing of the allergy
        btnAdd.setOnAction(event -> {
            String result = Allergies.updateAllergy(
                    new Allergies(allergicTo.getText(), allergyType.getText(),
                            allergyDescription.getText()), cosmoID);
            if ( result.equals("Update successfull") )
            {
                localStage.close();
            }

        });

        // Closes the stage.
        btnCancel.setOnAction(event -> {
            localStage.close();
        });

        mainPane.setPadding(new Insets(15, 15, 15, 15));

        Scene scene = new Scene(mainPane, 330, 300);
        localStage.setScene(scene);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add an allergy");
        localStage.showAndWait();
    }
}
