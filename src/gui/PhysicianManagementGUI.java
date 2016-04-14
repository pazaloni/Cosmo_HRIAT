package gui;

import helpers.FormatHelper;
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
import core.Physician;
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
                System.out.println("What");

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

        // action to pop up the edit form to edit a current physician
        removePhys.setOnAction((ActionEvent) -> {
            if (!(pTVCont.getSelectedPK().equals("null")))
            {
                // lblWarning.setText("");
                // manageUser(false);
            }
            else
            {

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
            String[] physInfo = new String[3];
            physInfo = pTVCont.getSelectedInfo();

            firstName.setText(physInfo[0]);
            lastName.setText(physInfo[1]);
            Phone.setText(physInfo[2]);

        }

        // used to format phone number as desired by the client (xxx)-xxx-xxxx
        FormatHelper fm = new FormatHelper();
        // on submit clicked
        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {

                String[] physInfo = new String[3];
                // gets all phys info for the selected phys
                physInfo = pTVCont.getSelectedInfo();

                String PhysID = physInfo[3];
                // if the user is not a new user (it is being edited)
                if (!newUser)
                {
                    // format phone numbers
                    String unformmattedNumber = Phone.getText();
                    String formattedPhone = fm
                            .formatPhoneNum(unformmattedNumber);
                    // error checking
                    if (formattedPhone
                            .equals("A phone number must have 10 digits."))
                    {
                        lblWarning
                                .setText("A phone number must have 10 digits.");

                    }
                    else if (firstName.getText().equals("")
                            || lastName.getText().equals(""))
                    {
                        lblWarning.setText("Both name fields are required.");
                    }
                    else
                    {
                        managePhys.editUser(firstName.getText(),
                                lastName.getText(), formattedPhone, PhysID);
                        // after editing, refresh the table to diplaty results
                        pTVCont.refreshTable();
                        // then close the window.
                        stageNewUser.close();
                    }
                }
                else
                {

                    String unformmattedNumber = Phone.getText();
                    String formattedPhone = fm
                            .formatPhoneNum(unformmattedNumber);
                    if (formattedPhone
                            .equals("A phone number must have 10 digits."))
                    {
                        lblWarning
                                .setText("A phone number must have 10 digits.");

                    }
                    else if (firstName.getText().equals("")
                            || lastName.getText().equals(""))
                    {
                        lblWarning.setText("Both name fields are required.");
                    }
                    else
                    {
                        managePhys.addUser(firstName.getText(),
                                lastName.getText(), formattedPhone);

                        pTVCont.refreshTable();
                        stageNewUser.close();
                    }

                }

            }

        });
        // add all buttons to grid form
        completionButtons.getChildren().addAll(btnReset, btnSubmit);
        completionButtons.setSpacing(10);
        completionButtons.setPadding(new Insets(0, 0, 0, 60));
        // completionButtons.setAlignment(Pos.CENTER_LEFT);
        newUserForm.add(lblFirstName, 0, 1);
        newUserForm.add(firstName, 1, 1);
        newUserForm.add(lblLastName, 0, 2);
        newUserForm.add(lastName, 1, 2);
        newUserForm.add(lblPhone, 0, 3);
        newUserForm.add(Phone, 1, 3);

        // completionButtons.setSpacing(50);
        newUserForm.add(completionButtons, 0, 9);
        newUserForm.setColumnSpan(completionButtons, 3);
        // newUserForm.add(btnReset, 0,9);
        // newUserForm.add(btnSubmit, 1,9);

        // btnReset.setAlignment(Pos.CENTER_RIGHT);
        newUserForm.add(lblWarning, 0, 4);
        // GridPane.setColumnSpan(completionButtons, 2);

        GridPane.setColumnSpan(lblWarning, 2);
        //newUserForm.setGridLinesVisible(true);
        Scene scene = new Scene(newUserForm);
        // add form to stage
        stageNewUser.setScene(scene);

        stageNewUser.setTitle("Physician Management");
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
