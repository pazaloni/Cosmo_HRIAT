package gui;
import controllers.ProgressNotesTableViewController;
import core.MedicalAdministrator;
import core.PopUpCheck;
import core.PopUpMessage;
import core.ProgressNotes;
import core.StaffAccount;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * Purpose: Represents the progress notes form GUI, which by default pulls
 * information
 *
 * @author CIMP
 * @version 1.0
 */
public class ProgressNotesFormGUI
{
    public static final String FORM_TITLE = "Progress Notes";
    public static final int SPACING = 10;

    private String cosmoID;

    private ScrollPane mainBox;

    // The Control that this will be placed in
    private Tab parentTab;

    private StaffAccount loggedInUser;
  
    
    private Stage parentStage;




    /**
     * 
     * Constructor for the Progress Notes class.
     * 
     * @param ctrl the pane, or box that this will be placed on
     */
    public ProgressNotesFormGUI(Tab progressNotesTab, StaffAccount loggedInUser,
            Stage parentStage)
    {
        this.parentTab = progressNotesTab;
        this.loggedInUser = loggedInUser;
        this.parentStage = parentStage;
    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     * 
     * @param cosmoId: The cosmoId for the participant
     */
    public Tab showProgressNotes( String cosmoId )
    {
        this.cosmoID = cosmoId;

        Label title = new Label(FORM_TITLE);
        //HBox titleBox = new HBox();

        title.setFont(new Font(22));

        mainBox = new ScrollPane();


        
        VBox contentBox = new VBox();
        
        contentBox.getChildren().addAll(title, createProgressNote(cosmoId));
        contentBox.setSpacing(15);
        
        mainBox.setContent(contentBox);

        mainBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainBox.setHmax(contentBox.getWidth());

        mainBox.setPadding(new Insets(10, 10, 10, 10));

        this.parentTab.setContent(mainBox);

        return parentTab;
    }

    /**
     * 
     * Purpose: Create the progress notes table
     * 
     * @return: HBox containing the progress notes
     */
    private VBox createProgressNote( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();        

        Button btnAddProgressNote = new Button("Add");
        Button btnEditProgressNote = new Button("Edit");
        Button btnDeleteProgressNote = new Button("Delete");
        buttons.setPadding(new Insets(0, 0, 0, 500));
        ProgressNotesTableViewController controller = new ProgressNotesTableViewController(
                cosmoId);
        TableView<ProgressNotes> table = controller.progressTable;

        //Add the Progress Note
        btnAddProgressNote
                .setOnMouseClicked(event -> {
                    ManageProgressNoteGUI manageProgressNotes = new ManageProgressNoteGUI(
                            parentStage);
                    manageProgressNotes.showAddProgressNote(cosmoId);
                    controller.refreshTable(cosmoId);
                });
        //Editing a Progress Note
        btnEditProgressNote.setOnMouseClicked(event -> {
            ProgressNotes progressNote = controller.getSelectedPK();
            if(progressNote==null)
            {
                Stage stage = new Stage();
                PopUpMessage popup = new PopUpMessage(
                        "Must select a Progress Note to edit", stage);
                Scene scene = new Scene(popup.root, 300, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
            }
            else
            {
                ManageProgressNoteGUI manageProgressNote = new ManageProgressNoteGUI(
                        parentStage);            	
            	manageProgressNote.showUpdateProgressNote(progressNote, cosmoId);
            	controller.refreshTable(cosmoId);
            }
        });
        btnDeleteProgressNote.setOnMouseClicked(event -> {
            ProgressNotes progressNote = controller.getSelectedPK();
            if(progressNote==null)
            {
                Stage stage = new Stage();
                PopUpMessage popup = new PopUpMessage(
                        "Must select a progress note to delete", stage);
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
                        "Are you sure you want to delete the selected progress note?",
                        stage);
                Scene scene = new Scene(popup.root, 450, 75);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.showAndWait();
                if(popup.runCheck())
                {
                ProgressNotes.deleteProgressNote(table.getSelectionModel()
                        .getSelectedItem(), cosmoID);
                }
                controller.refreshTable(cosmoID);
            }
        });

        if(!(this.loggedInUser instanceof MedicalAdministrator))
        {
            btnAddProgressNote.setVisible(false);
            btnEditProgressNote.setVisible(false);
            btnDeleteProgressNote.setVisible(false);
        }
        
        buttons.getChildren().addAll(btnAddProgressNote,
                btnEditProgressNote, btnDeleteProgressNote);

        controls.getChildren().addAll(buttons);

        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(380);

        box.getChildren().addAll(controls, table);
        return box;
    }
}