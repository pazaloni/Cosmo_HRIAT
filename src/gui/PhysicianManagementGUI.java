package gui;

import helpers.ManagePhysicianAccountHelper;
import controllers.PhysicianTableViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.PopUpMessage;

/**
 * Purpose: represent the physician management table and controls
 * 
 * @author CIMP
 * @version 1.0
 */
public class PhysicianManagementGUI
{

    // helper class used to manage physicians
    // implemented in the next story*****
    private ManagePhysicianAccountHelper managePhys = new ManagePhysicianAccountHelper();

    // The stage
    public static Stage stageTech;

    // Button, used to finish adding a physician to database
    private Button btnSubmit = new Button("Submit");

    // label that will be displayed when there will be an error
    public static Label lblWarning = new Label();

    // the new user main page
    private Stage stageNewUser;

    // title of the form displayed at the top
    private static final String FORM_TITLE = "Physician Management";
    // main scroll pain to hold all of the objects
    private ScrollPane mainPane;

    // the three controls used to manipulate the data contained in the table
    private Button addPhys;
    private Button removePhys;
    private Button editPhys;

    // controller used to initialize and display the physician table
    private PhysicianTableViewController pTVCont;
    // the three containers used to hold all of the form controlls and the table
    // on the page
    private VBox mainBox;
    private HBox buttonBox;
    private VBox titleBox;

    /**
     * 
     * Constructor for the PhysicianMnagementGUI class.
     * 
     * 
     */
    public PhysicianManagementGUI()
    {

    }

    /**
     * 
     * Purpose: Method that will return the entirety of the form
     * 
     * @return a scroll pane with all the required controls
     */
    public ScrollPane showPhysicianManagementForm()
    {
        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));
        mainBox = new VBox();
        titleBox = new VBox();
        buttonBox = new HBox();
        pTVCont = new PhysicianTableViewController();
        pTVCont.initialize();

        pTVCont.PhysicianTable.minWidth(800);

        addPhys = new Button("Add");
        removePhys = new Button("Delete");
        editPhys = new Button("Edit");

        addPhys.setOnAction((ActionEvent) -> {
            lblWarning.setText("");
            manageUser(true);
        });
        // action to pop up the edit form to edit a current physician
        editPhys.setOnAction((ActionEvent) -> {
            if (!(pTVCont.getSelectedPK().equals("null")))
            {
                lblWarning.setText("");
                manageUser(false);
            }
            else
            {
                Stage popUpStage = new Stage();

                // pop up a message saying that you cannot delete the current
                // user
                PopUpMessage messageBox = new PopUpMessage("No user selected",
                        popUpStage);

                Scene popScene = new Scene(messageBox.root, 300, 75);
                popUpStage.setScene(popScene);
                popUpStage.showAndWait();

            }

        });

        // adds buttons to the button container for placement formatting
        buttonBox.getChildren().addAll(addPhys, editPhys, removePhys);
        // box used to hold the title at the top of the form
        titleBox.getChildren().addAll(formTitle);
        // width and formatting to look nice
        mainBox.setSpacing(10);
        buttonBox.setMaxWidth(700);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(10);
        mainBox.setMinWidth(900);
        // adding all child boxes to the main parent
        mainBox.getChildren().addAll(titleBox, buttonBox,
                pTVCont.PhysicianTable);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        // set final parent box to scroll pane
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);
        mainPane.setMinWidth(700);
        return mainPane;

    }

    /**
     * Manageuser used to deter,mine wether the user selected on the table is to
     * be deleted or added, new user is a boolean value used to determine this
     * 
     * @param newUser
     */
    private void manageUser(boolean newUser)
    {
        stageNewUser = new Stage();

        GridPane newUserForm = new GridPane();
        newUserForm.setHgap(10);
        newUserForm.setVgap(15);
        newUserForm.setPadding(new Insets(15));
        // first name field and formatting
        Label lblFirstName = new Label("First Name");
        lblFirstName.setFont(new Font(15));
        TextField firstName = new TextField();
        // Last name field and formatting
        Label lblLastName = new Label("Last Name");
        lblLastName.setFont(new Font(15));
        TextField lastName = new TextField();
        // last name field and formatting
        Label lblPhone = new Label("Phone Number");
        lblPhone.setFont(new Font(15));
        TextField Phone = new TextField();

        // warning for when info is not correct. //not this story
        lblWarning.setFont(new Font(15));
        lblWarning.setStyle("-fx-text-fill: red");

        HBox completionButtons = new HBox();
        // used to reset all fields
        Button btnReset = new Button("Reset");

        btnReset.minWidth(150);
        btnReset.setFont(new Font(15));
        // clears all fields
        btnReset.setOnAction(event -> {
            firstName.setText("");
            lastName.setText("");
            Phone.setText("");

        });

        btnSubmit.minWidth(150);
        btnSubmit.setFont(new Font(15));
        if (!newUser)
        {
            // StaffAccount selectedStaff = managePhys.queryStaff(sTVCont
            // .getSelectedPK());
            // firstName.setText(selectedStaff.GetFirstName());
            // Phone.setDisable(true);
            // lastName.setText(selectedStaff.GetLastName());
            // Phone.setText(selectedStaff.GetUsername());
            // email.setText(selectedStaff.GetEmail());
            // password.setText(selectedStaff.GetPassword());
            // repeatPassword.setText(selectedStaff.GetPassword());
            // cboSecurityLevels.setValue(securityLevels.get(Integer
            // .parseInt(selectedStaff.GetAccessLevel())));
        }
        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {

                // if (newUser)
                // {
                // String result = managePhys.addUser(username.getText(),
                // lastName.getText(), firstName.getText(),
                // email.getText(), password.getText(),
                // repeatPassword.getText(),
                // returnSecurityLevel(cboSecurityLevels.getValue()));
                //
                // if (result.equals(""))
                // {
                // stageNewUser.close();
                // sTVCont.refreshTable();
                // }
                // else
                // {
                // lblWarning.setText(result);
                // }
                // }

                // else
                // {
                // String updateResult = managePhys.editUser(
                // username.getText(), lastName.getText(),
                // firstName.getText(), email.getText(),
                // password.getText(), repeatPassword.getText(),
                // returnSecurityLevel(cboSecurityLevels.getValue()));
                // if (updateResult.equals(""))
                // {
                // stageNewUser.close();
                // sTVCont.refreshTable();
                // }
                // else
                // {
                // lblWarning.setText(updateResult);
                // }
                // }
                // sTVCont.refreshTable();

            }

        });
        // add all buttons to grid form
        completionButtons.getChildren().addAll(btnReset, btnSubmit);
        completionButtons.setSpacing(10);
        newUserForm.add(lblFirstName, 0, 1);
        newUserForm.add(firstName, 1, 1);
        newUserForm.add(lblLastName, 0, 2);
        newUserForm.add(lastName, 1, 2);
        newUserForm.add(lblPhone, 0, 3);
        newUserForm.add(Phone, 1, 3);

        newUserForm.add(completionButtons, 1, 9);
        newUserForm.add(lblWarning, 0, 0, 2, 1);

        Scene scene = new Scene(newUserForm);
        // add form to stage
        stageNewUser.setScene(scene);

        stageNewUser.setTitle("Account Registration");
        // set the modality
        stageNewUser.initModality(Modality.WINDOW_MODAL);
        // set the parent of the this stage to the 'techstage' so the modality
        // will
        // work because it needs a parent to know which stage to disable access
        stageNewUser.initOwner(stageTech);
        // make the window not be resizable
        stageNewUser.setResizable(false);
        // display window
        stageNewUser.show();

    }

}
