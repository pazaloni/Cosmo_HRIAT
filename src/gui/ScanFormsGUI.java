package gui;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controllers.ScanFormsTableViewController;
import core.MedicalAdministrator;
import core.PopUpCheck;
import core.PopUpMessage;
import core.ScanForms;
import core.StaffAccount;

public class ScanFormsGUI
{

public static final String FORM_TITLE = "Scanned Documents";
public static final int SPACING = 10;

private String cosmoID;

private ScrollPane mainBox;

// The Control that this will be placed in
private Tab parentTab;

private StaffAccount loggedInUser;


private Stage parentStage;

private TextArea descriptionArea;


/**
 * 
 * Constructor for the Scanned forms class.
 * 
 * @param ctrl the pane, or box that this will be placed on
 */
public ScanFormsGUI(Tab scanFormsTab, StaffAccount loggedInUser,
        Stage parentStage)
{
    this.parentTab = scanFormsTab;
    this.loggedInUser = loggedInUser;
    this.parentStage = parentStage;
}

/**
 * 
 * Purpose: This method will make everything and display it.
 * 
 * @param cosmoId: The cosmoId for the participant
 */
public Tab showScannedForms( String cosmoId )
{
    this.cosmoID = cosmoId;

    Label title = new Label(FORM_TITLE);
    //HBox titleBox = new HBox();

    title.setFont(new Font(22));

    mainBox = new ScrollPane();

    descriptionArea = new TextArea();
    descriptionArea.setEditable(false);
    descriptionArea.setWrapText(true);
    
    Label descriptionLbl = new Label("Description:");
    descriptionLbl.setFont(new Font(18));
    HBox contentBox = new HBox();
    VBox descriptionBox = new VBox();
    VBox tableBox = new VBox();
    descriptionArea.setMaxWidth(230);
    descriptionArea.setMinWidth(230);
    descriptionArea.setMaxHeight(150);
    tableBox.getChildren().addAll(title, createScannedForm(cosmoId));
    tableBox.setSpacing(15);
    descriptionBox.getChildren().addAll(descriptionLbl, descriptionArea);
    contentBox.getChildren().addAll(tableBox, descriptionBox);
    mainBox.setContent(contentBox);

    mainBox.setHbarPolicy(ScrollBarPolicy.NEVER);
    mainBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    mainBox.setHmax(tableBox.getWidth());

    descriptionBox.setPadding(new Insets(55, 10, 10, 50));
    mainBox.setPadding(new Insets(10, 10, 10, 10));

    this.parentTab.setContent(mainBox);

    return parentTab;
}

/**
 * 
 * Purpose: Create the scanned form table
 * 
 * @return: VBox containing the scanned forms
 */
private VBox createScannedForm( String cosmoId )
{
    VBox box = new VBox();
    HBox controls = new HBox();
    HBox buttons = new HBox();        

    Button btnAddImage = new Button("Add Image");
    Button btnViewImage = new Button("View Image");
    Button btnDeleteImage = new Button("Delete Image");
    buttons.setPadding(new Insets(0, 0, 10, 50));
    ScanFormsTableViewController controller = new ScanFormsTableViewController(
            cosmoId);
    TableView<ScanForms> table = controller.scanTable;

    //Add the scanned form
    btnAddImage
            .setOnMouseClicked(event -> {
                ManageScanFormsGUI manageScanForms = new ManageScanFormsGUI(
                        parentStage);
                manageScanForms.showAddImage(cosmoId);
                controller.refreshTable(cosmoId);
            });
    //viewing a scanned form
    btnViewImage.setOnMouseClicked(event -> {
        ScanForms scannedForm = controller.getSelectedPK();
        if(scannedForm==null)
        {
            Stage stage = new Stage();
            PopUpMessage popup = new PopUpMessage(
                    "Must select a form to view", parentStage);
            Scene scene = new Scene(popup.root, 300, 75);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parentStage);
            stage.setScene(scene);
            stage.showAndWait();
        }
        else
        {
            ManageScanFormsGUI manageScannedForms = new ManageScanFormsGUI(
                    parentStage);               
            manageScannedForms.showScannedForm(scannedForm, cosmoId);
            controller.refreshTable(cosmoId);
        }
    });
    //deleting a scanned image
    btnDeleteImage.setOnMouseClicked(event -> {
        ScanForms scannedForm = controller.getSelectedPK();
        if(scannedForm==null)
        {
            Stage stage = new Stage();
            PopUpMessage popup = new PopUpMessage(
                    "Must select a form to delete", stage);
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
                    "Are you sure you want to delete the selected form?",
                    stage);
            Scene scene = new Scene(popup.root, 450, 75);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parentStage);
            stage.setScene(scene);
            stage.showAndWait();
            if(popup.runCheck())
            {
            ScanForms.deleteImage(table.getSelectionModel()
                    .getSelectedItem(), cosmoID);
            descriptionArea.setText("");
            }
            controller.refreshTable(cosmoID);
        }
    });

    if(!(this.loggedInUser instanceof MedicalAdministrator))
    {
        btnAddImage.setVisible(false);
        btnViewImage.setVisible(false);
        btnDeleteImage.setVisible(false);
    }
    
    buttons.getChildren().addAll(btnAddImage,
            btnViewImage, btnDeleteImage);

    controls.getChildren().addAll(buttons);

    table.setMaxWidth(300);

    buttons.setSpacing(5);
    controls.setSpacing(380);

    controller.scanTable.setOnMousePressed(event -> {
        displayDescription(controller.getSelectedPK());
    });
    
    box.getChildren().addAll(controls, table);
    return box;
}

private void displayDescription( ScanForms selectedPK )
{
    
    descriptionArea.setText(selectedPK.getDescription().get());
    
}


}