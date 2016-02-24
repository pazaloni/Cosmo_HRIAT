import java.sql.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Purpose: To display the GUI for the technical administrator
 * 
 * @author cst214,cst209,cst207,cst205
 *
 */
public class TechMainPageGUI extends Application
{

    // helper class used to manage accounts
    private ManageStaffAccountHelper manageStaff = new ManageStaffAccountHelper();

    // Controller used to fill the tableview
    private StaffTableViewController sTVCont;
    
    // Class used to fill the activity log and to create and manage the controls
    private ActivityLogPopUp activityLog;

    // Instance of the database helper
    private DatabaseHelper dbObject = new DatabaseHelper();

    // The stage
    public static Stage stageTech;

    // Button when clicked, will bring up the activity log
    private Button btnViewLog = new Button();

    // Button, when clicked will bring up the add user menu
    private Button btnAddUser = new Button();

    // Button, when clicked will bring up window of user properties to be edited
    private Button btnEditUser = new Button();

    // Button, used to finish adding a staff account to database
    private Button btnSubmit = new Button("Submit");

    // will log out the current technical admin
    private Button btnLogOut = new Button("Log Out");

    // Once a user is highlighted, clicking this button will prompt for
    // assurance
    // and then the removal of th user
    private Button btnRemoveUser = new Button();

    // The main windows dimensions, in pixels
    private static final int PANE_WIDTH = 875;
    private static final int PANE_HEIGHT = 580;

    private static final boolean NEW_STAFF = true;

    private static final boolean EDIT_STAFF = false;
    // The clients logo displayed at the top.
    private Image imgLogo = new Image("images/CosmoIconLong.png");

    // heading of the page "Technical Administration"
    private Label lblTechTitle = new Label();

    // label that will be displayed when there will be an error
    public static Label lblWarning = new Label();

    // the main container for the page.
    private VBox vbLayoutContainer = new VBox();

    // the new user main page
    private Stage stageNewUser;
    
    //Tech Administrator object that holds logged in user
    private TechnicalAdministrator loggedInAdmin;

    public void techMainPageConstruct(Stage stage, StaffAccount loggedInStaff)
    {

        loggedInAdmin = (TechnicalAdministrator) loggedInStaff;
        // open the database connection
        dbObject.connect();
        dbObject.activtyLogEntry(loggedInStaff.GetUsername(), "Logged In", dbObject);
        // create a staff table view controller and initialize it
        sTVCont = new StaffTableViewController();
        sTVCont.initialize();

        stageTech = stage;
        // The scene that displays the main layout container with the preferred
        // dimensions
        Scene scene = new Scene(vbLayoutContainer, PANE_WIDTH, PANE_HEIGHT);

        // Vbox used to hold the main content of the page (Buttons, Table, etc)
        VBox managePane = new VBox();

        // the borderpane created in another method, that will hold the logout
        // button and the clients logo.
        BorderPane headerLogin = createHeader();

        // Formatting for the heading of the page "Technical Administration"
        Label pageName = new Label();
        pageName.setText("Technical Administration");
        pageName.setFont(new Font(30));
        pageName.setPadding(new Insets(10, 0, 0, 0));

        // The Hbox that contains the row of 4 buttons on the page
        HBox actionBox = new HBox();

        // Formating for the viewLog Button
        btnViewLog.setText("View Activity Log");
        btnViewLog.setFont(new Font(15));
        btnViewLog.setMinWidth(150);

        // Formating for the addUser Button
        btnAddUser.setText("Add User");
        btnAddUser.setMinWidth(150);
        btnAddUser.setFont(new Font(15));

        // Formating for the editUser Button
        btnEditUser.setText("Edit User");
        btnEditUser.setMinWidth(150);
        btnEditUser.setFont(new Font(15));

        // Formating for the removeUser Button
        btnRemoveUser.setText("Remove User");
        btnRemoveUser.setMinWidth(150);
        btnRemoveUser.setFont(new Font(15));

        // when the remove user is clicked, the selected account from the table
        // list will run the removeUser method using its primary key
        btnRemoveUser.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                try
                {
                    removeUser(sTVCont.getSelectedPK());
                }
                catch (Exception e1)
                {

                    e1.printStackTrace();
                }
            }
        });

        // appends buttons to the action box to be displayed, and formatts the
        // actionBox
        // set event handler to create a new user
        btnAddUser.setOnAction((ActionEvent) -> {
            lblWarning.setText("");
            manageUser(NEW_STAFF);
        });

        btnEditUser.setOnAction((ActionEvent) -> {
            if (!(sTVCont.getSelectedPK().equals("null")))
            {
                lblWarning.setText("");
                manageUser(EDIT_STAFF);
            }
            else
            {
                Stage popUpStage = new Stage();
                
                //pop up a message saying that you cannot delete the current user
                PopUpMessage messageBox = new PopUpMessage("No user selected", popUpStage);
                
                Scene popScene = new Scene(messageBox.root, 300, 75);
                popUpStage.setScene(popScene);
                popUpStage.showAndWait();
                
            }

        });
        
        btnViewLog.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle( ActionEvent arg0 )
            {
                activityLog = new ActivityLogPopUp(stageTech);         
                activityLog.showActivityLog();
            }
            
        });
        
        actionBox.getChildren().addAll(btnViewLog, btnAddUser, btnEditUser,
                btnRemoveUser);
        actionBox.setPadding(new Insets(25, 0, 20, 0));
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setSpacing(75);

        // label for the table "Manage Users", and formatting
        Label tableName = new Label();
        tableName.setText("Manage Users");
        tableName.setFont(new Font(20));
        // Formatting for the managePane, as well as the appending of the pages

        // main content
        managePane.setPadding(new Insets(0, 30, 0, 30));
        managePane.getChildren().addAll(pageName, actionBox, tableName,
                sTVCont.staffTable);

        // appending the two main containers to the layOut container
        vbLayoutContainer.getChildren().addAll(headerLogin, managePane);

        // final stage preparation and titling
        stageTech.setScene(scene);
        stageTech.setTitle("Cosmo Industries - " + loggedInAdmin.GetUsername());
        // display window
        stageTech.show();
        
        stageTech.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                dbObject.activtyLogEntry(loggedInAdmin.GetUsername(), "Logout", dbObject);
            }
        });
    }

    /**
     * 
     * Purpose:Change the warning based on what the user's input failed to
     * validated
     * 
     * @param warning
     */
    public static void changeWarning(String warning)
    {
        lblWarning.setText(warning);
    }

    /**
     * 
     * Purpose: Display a pop-up box with information to fill out for a user
     * 
     * @param newUser - If true creates a user, if false edits the user
     */
    private void manageUser(boolean newUser)
    {
        stageNewUser = new Stage();

        GridPane newUserForm = new GridPane();
        newUserForm.setHgap(10);
        newUserForm.setVgap(15);
        newUserForm.setPadding(new Insets(15));

        Label lblFirstName = new Label("First Name");
        lblFirstName.setFont(new Font(15));
        TextField firstName = new TextField();

        Label lblLastName = new Label("Last Name");
        lblLastName.setFont(new Font(15));
        TextField lastName = new TextField();

        Label lblUsername = new Label("Username");
        lblUsername.setFont(new Font(15));
        TextField username = new TextField();
        
        //checkbox to show/hide passwords
        CheckBox showPassword = new CheckBox();
        Label lblShowPassword = new Label("Show Password");
        HBox showPasswordBox = new HBox();
        showPasswordBox.getChildren().addAll(showPassword, lblShowPassword);
        
        Label lblPassword = new Label("Password");
        lblPassword.setFont(new Font(15));
        PasswordField password = new PasswordField();
        
        //password field in textfield form, set not visible by default
        TextField passwordText = new TextField();
        passwordText.setManaged(false);
        passwordText.setVisible(false);
        
        //bind password to checkbox
        //show text field when selected
        passwordText.managedProperty().bind(showPassword.selectedProperty());
        passwordText.visibleProperty().bind(showPassword.selectedProperty());
        //show password field when not selected
        password.managedProperty().bind(showPassword.selectedProperty().not());
        password.visibleProperty().bind(showPassword.selectedProperty().not());
        
        //bind the values bidirectionally
        passwordText.textProperty().bindBidirectional(password.textProperty());
        
        
        Label lblEmail = new Label("Email");
        lblEmail.setFont(new Font(15));
        TextField email = new TextField();

        Label lblRepeatPassword = new Label("Repeat Password");
        lblRepeatPassword.setFont(new Font(15));
        PasswordField repeatPassword = new PasswordField();
        
        //password field in textfield form, set not visible by default
        TextField repeatPasswordText = new TextField();
        repeatPasswordText.setManaged(false);
        repeatPasswordText.setVisible(false);
        
        //bind password to checkbox
        //show text field when selected
        repeatPasswordText.managedProperty().bind(showPassword.selectedProperty());
        repeatPasswordText.visibleProperty().bind(showPassword.selectedProperty());
        //show password field when not selected
        repeatPassword.managedProperty().bind(showPassword.selectedProperty().not());
        repeatPassword.visibleProperty().bind(showPassword.selectedProperty().not());
        
        //bind the values bidirectionally
        repeatPasswordText.textProperty().bindBidirectional(repeatPassword.textProperty());
        
        
        Label lblSecurityLevel = new Label("Security Level");
        lblSecurityLevel.setFont(new Font(15));
        ObservableList<String> securityLevels = FXCollections
                .observableArrayList("Basic Staff", "Medical Administrator",
                        "Technical Administrator");
        ComboBox<String> cboSecurityLevels = new ComboBox<String>(
                securityLevels);
        cboSecurityLevels.setValue(securityLevels.get(0));

        lblWarning.setFont(new Font(15));
        lblWarning.setStyle("-fx-text-fill: red");

        HBox completionButtons = new HBox();

        Button btnReset = new Button("Reset");

        btnReset.minWidth(150);
        btnReset.setFont(new Font(15));

        btnReset.setOnAction(event -> {
            firstName.setText("");
            lastName.setText("");
            username.setText("");
            email.setText("");
            password.setText("");
            repeatPassword.setText("");
            lblWarning.setText("");
            cboSecurityLevels.setValue(securityLevels.get(0));
        });

        btnSubmit.minWidth(150);
        btnSubmit.setFont(new Font(15));
        if (!newUser)
        {
            StaffAccount selectedStaff = manageStaff.queryStaff(sTVCont
                    .getSelectedPK());
            firstName.setText(selectedStaff.GetFirstName());
            username.setDisable(true);
            lastName.setText(selectedStaff.GetLastName());
            username.setText(selectedStaff.GetUsername());
            email.setText(selectedStaff.GetEmail());
            password.setText(selectedStaff.GetPassword());
            repeatPassword.setText(selectedStaff.GetPassword());
            cboSecurityLevels.setValue(securityLevels.get(Integer
                    .parseInt(selectedStaff.GetAccessLevel())));
        }
        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {

                if (newUser)
                {
                    String result = manageStaff.addUser(username.getText(),
                            lastName.getText(), firstName.getText(),
                            email.getText(), password.getText(),
                            repeatPassword.getText(),
                            returnSecurityLevel(cboSecurityLevels.getValue()));

                    if (result.equals(""))
                    {
                        stageNewUser.close();
                        sTVCont.refreshTable();
                    }
                    else
                    {
                        lblWarning.setText(result);
                    }
                }

                else
                {
                    String updateResult = manageStaff.editUser(
                            username.getText(), lastName.getText(),
                            firstName.getText(), email.getText(),
                            password.getText(), repeatPassword.getText(),
                            returnSecurityLevel(cboSecurityLevels.getValue()));
                    if (updateResult.equals(""))
                    {
                        stageNewUser.close();
                        sTVCont.refreshTable();
                    }
                    else
                    {
                        lblWarning.setText(updateResult);
                    }
                }
                sTVCont.refreshTable();

            }

        });

        completionButtons.getChildren().addAll(btnReset, btnSubmit);
        completionButtons.setSpacing(10);
        newUserForm.add(lblUsername, 0, 1);
        newUserForm.add(username, 1, 1);
        newUserForm.add(lblLastName, 0, 2);
        newUserForm.add(lastName, 1, 2);
        newUserForm.add(lblFirstName, 0, 3);
        newUserForm.add(firstName, 1, 3);
        newUserForm.add(lblEmail, 0, 4);
        newUserForm.add(email, 1, 4);
        newUserForm.add(lblPassword, 0, 5);
        
        newUserForm.add(password, 1, 5);
        newUserForm.add(passwordText, 1, 5);
        newUserForm.add(lblRepeatPassword, 0, 6);
        newUserForm.add(repeatPassword, 1, 6); 
        newUserForm.add(repeatPasswordText,1,6);
        newUserForm.add(showPasswordBox,1,7);  // added showPassword checkbox
        newUserForm.add(lblSecurityLevel, 0, 8);
        newUserForm.add(cboSecurityLevels, 1, 8);
        newUserForm.add(completionButtons, 1, 9);
        newUserForm.add(lblWarning, 0, 0, 2, 1);

        Scene scene = new Scene(newUserForm);

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

    /**
     * 
     * Purpose: Return a specific number based on the level string
     * 
     * @param level
     *            the level that the admin selected in the combobox
     * @return Return a security level based on the item selected in the text
     *         box
     */
    private String returnSecurityLevel(String level)
    {
        String securityLevel = "4";
        if (level.contains("Basic Staff"))
        {
            securityLevel = "0";
        }
        else if (level.contains("Medical Administrator"))
        {
            securityLevel = "1";
        }
        else if (level.contains("Technical Administrator"))
        {
            securityLevel = "2";
        }
        return securityLevel;
    }

    /**
     * Description: Prepares the window for display Param: The stage to be
     * launched
     * 
     */
    public void start(Stage stage)
    {
        techMainPageConstruct(stage, null);
    }

    /**
     * Description: Prepares header for display. Also creates the log out button
     * and logo
     * 
     * @return The formatted and created borderpane
     */
    public BorderPane createHeader()
    {
        // the borderpane to return
        BorderPane logoAndLogout = new BorderPane();

        // set formatting for borderpane
        logoAndLogout.setPadding(new Insets(15, 12, 15, 12));
        logoAndLogout.setStyle("-fx-background-color: #FFFFFF;");

        // new button for log out, as well as formatting
        btnLogOut = new Button("Log Out");
        btnLogOut.setPrefSize(100, 20);

        btnLogOut.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
            	stageTech.setOnCloseRequest(null);
                stageTech.close();
                dbObject.activtyLogEntry(loggedInAdmin.GetUsername(), "Logout", dbObject);
                LoginGUI test5 = new LoginGUI();
                try
                {
                    test5.start(stageTech);
                }
                catch (Exception e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        // ImageView that will hold and display the clients logo, and formatting
        ImageView logo = new ImageView(imgLogo);
        logo.setFitWidth(400);
        logo.setFitHeight(49);

        // Insert the two children into the borderpane at their preferred sides.
        logoAndLogout.setLeft(logo);
        logoAndLogout.setRight(btnLogOut);

        // return finished borderPane
        return logoAndLogout;
    }

    /**
     * Purpose: This will take the selected user from the table, confirm that
     * you wish to delete them, if so, will delete the selected user, then
     * refresh the table of accounts
     * 
     * @param username
     *            The user that you will remove
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public void removeUser(String username)
    {
        Stage stage = new Stage();
        Scene scene;

    	//if the selected username is not null 
        if(username != null && username != "null")
        {
            //if the selected username is the same as the current username 
        	if(username.equals(loggedInAdmin.GetUsername()))
            {
            	//pop up a message saying that you cannot delete the current user
        		PopUpMessage messageBox = new PopUpMessage("You cannot delete "
            			+ "current user.", stage);
            	
            	scene = new Scene(messageBox.root, 300, 75);
            	stage.setScene(scene);
            	stage.showAndWait();
            }
        	//else pop up a message asking to confirm deleting the selected user
            else
            {
            	PopUpCheck checkBox = new PopUpCheck("Are you sure you want to "
                        + "delete " + username + "?", stage);
                
                scene = new Scene(checkBox.root, 300, 75);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(stageTech);
                stage.showAndWait();

                // when the user is removed from the database
                if (checkBox.result)
                {
                        if( manageStaff.removeUser(username) )
                        {
                            // this.sTVCont.removeViewableUser(username);
                            this.sTVCont.refreshTable();
                        }
                   
                } 
            }
        }
        //pop up a message saying that no user has been selected to delete
        else
        {
            // tell the user to select a user to delete
            PopUpMessage messageBox = new PopUpMessage("Please select a user"
                    + " to remove.", stage);

            scene = new Scene(messageBox.root, 300, 75);
            stage.setScene(scene);
            stage.showAndWait();
        }

    }
    
    
    /**
     * 
     * Purpose: create and display the activity log as a popup.
     * 
     */
    private void constructActivityLog()
    {
        Stage activityLogStage = new Stage();
        
        //Making this a pop-up that will be obtain focus.
        activityLogStage.initModality(Modality.WINDOW_MODAL);
        activityLogStage.initOwner(stageTech);
        
        
                
    } 
    
}
