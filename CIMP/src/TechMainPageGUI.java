import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * Purpose: To display the GUI for the technical administrator
 * 
 * @author cst214,cst209,cst207,cst205
 *
 */
public class TechMainPageGUI extends Application
{

    public static Stage stageTech;

    // Button when clicked, will bring up the activity log
    private Button btnViewLog = new Button();

    // Button, when clicked will bring up the add user menu
    private Button btnAddUser = new Button();

    // Button, when clicked will bring up window of user properties to be edited
    private Button btnEditUser = new Button();

    // will log out the current technical admin
    private Button btnLogOut = new Button("Log Out");

    // Once a user is highlighted, clicking this button will prompt for
    // assurance
    // and then the removal of th user
    private Button btnRemoveUser = new Button();

    // The main windows dimensions, in pixels
    private static final int PANE_WIDTH = 875;
    private static final int PANE_HEIGHT = 580;

    // The clients logo displayed at the top.
    private Image imgLogo = new Image("images/CosmoIconLong.png");

    // heading of the page "Technical Administration"
    private Label lblTechTitle = new Label();

    // the main container for the page.
    private VBox vbLayoutContainer = new VBox();
    
    // the new user main page
    private Stage stageNewUser;

    public void techMainPageConstruct( Stage stage )
    {
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

        // appends buttons to the action box to be displayed, and formatts the
        // actionBox
        // set event handler to create a new user
        btnAddUser.setOnAction(( ActionEvent ) -> {
            createUser();
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

        // TableView instance to hold User records
        TableView<String> table = new TableView<String>();

        // Instantiation of all the table column headings (With proper
        // formatting)
        TableColumn staffIDCol = new TableColumn("StaffID");
        staffIDCol.setMinWidth(60);

        TableColumn userNameCol = new TableColumn("User Name");
        userNameCol.setMinWidth(175);

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(169);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(150);

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(150);

        TableColumn securityLvlCol = new TableColumn("Security Level");
        securityLvlCol.setMinWidth(100);

        // Appending column headers to the table for display
        table.getColumns().addAll(staffIDCol, userNameCol, emailCol,
                firstNameCol, lastNameCol, securityLvlCol);

        // Formatting for the managePane, as well as the appending of the pages
        // main content
        managePane.setPadding(new Insets(0, 30, 0, 30));
        managePane.getChildren().addAll(pageName, actionBox, tableName, table);

        // appending the two main containers to the layOut container
        vbLayoutContainer.getChildren().addAll(headerLogin, managePane);

        // final stage preparation and titling
        stageTech.setScene(scene);
        stageTech.setTitle("Cosmopolitan Industries");
        // display window
        stageTech.show();

    }

    /**
     * 
     * Purpose: Display a pop-up box with information to fill out for a user
     */
    private void createUser()
    {
        stageNewUser = new Stage();

        GridPane newUserForm = new GridPane();
        newUserForm.setHgap(10);
        newUserForm.setVgap(10);
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

        Label lblPassword = new Label("Password");
        lblPassword.setFont(new Font(15));
        PasswordField password = new PasswordField();

        Label lblRepeatPassword = new Label("Repeat Password");
        lblRepeatPassword.setFont(new Font(15));
        PasswordField repeatPassword = new PasswordField();

        Label lblSecurityLevel = new Label("Security Level");
        lblSecurityLevel.setFont(new Font(15));
        ObservableList<String> securityLevels = FXCollections
                .observableArrayList("Basic Staff", "Medical Administrator",
                        "Technical Administrator");
        ComboBox<String> cboSecurityLevels = new ComboBox<String>(
                securityLevels);
        cboSecurityLevels.setValue(securityLevels.get(0));

        HBox completionButtons = new HBox();

        Button btnReset = new Button("Reset");
        btnReset.minWidth(150);
        btnReset.setFont(new Font(15));

        btnReset.setOnAction(event -> {
            firstName.setText("");
            lastName.setText("");
            username.setText("");
            password.setText("");
            repeatPassword.setText("");
            cboSecurityLevels.setValue(securityLevels.get(0));

        });

        Button btnFinish = new Button("Finish");
        btnFinish.minWidth(150);
        btnFinish.setFont(new Font(15));
        
        btnFinish.setOnAction( event  -> {
//            addUser(firstName.getText(), lastName.getText(), username.getText(), 
//                    password.getText(), repeatPassword.getText(), 
//                    cboSecurityLevels.getValue());
            
            createPopUpMessage("somethin");
        });


        completionButtons.getChildren().addAll(btnReset, btnFinish);
        completionButtons.setSpacing(15);
        newUserForm.add(lblFirstName, 0, 0);
        newUserForm.add(firstName, 1, 0);
        newUserForm.add(lblLastName, 0, 1);
        newUserForm.add(lastName, 1, 1);
        newUserForm.add(lblUsername, 0, 2);
        newUserForm.add(username, 1, 2);
        newUserForm.add(lblPassword, 0, 3);
        newUserForm.add(password, 1, 3);
        newUserForm.add(lblRepeatPassword, 0, 4);
        newUserForm.add(repeatPassword, 1, 4);
        newUserForm.add(lblSecurityLevel, 0, 5);
        newUserForm.add(cboSecurityLevels, 1, 5);
        newUserForm.add(completionButtons, 1, 6);

        Scene scene = new Scene(newUserForm);

        stageNewUser.setScene(scene);

        stageNewUser.setTitle("New User");
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
    private int returnSecurityLevel( String level )
    {
        int securityLevel = 4;
        if ( level.contains("Basic Staff") )
        {
            securityLevel = 0;
        }
        else if ( level.contains("Medical Administrator") )
        {
            securityLevel = 1;
        }
        else if ( level.contains("Technical Administrator") )
        {
            securityLevel = 2;
        }
        return securityLevel;
    }

    /**
     * Description: Prepares the window for display Param: The stage to be
     * launched
     * 
     */
    public void start( Stage stage )
    {
        techMainPageConstruct(stage);
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
            public void handle( ActionEvent e )
            {
                stageTech.close();
                LoginGUI test5 = new LoginGUI();
                try
                {
                    test5.start(stageTech);
                }
                catch ( Exception e1 )
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

    public static void main( String[] args )
    {
        launch(args);
    }

    public void openlog()
    {

    }

    public void addUser(String firstName, String lastname, String username, 
            String password, String repeatPW, String securityLv )
    {
        createPopUpMessage("yeahhhhhhh baby!!!!!");
    }

    public void editUser( int staffID )
    {

    }

    public void removeUser( int staffID )
    {

    }
    
    private void createPopUpMessage(String msg)
    {
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.initOwner( stageNewUser );
        VBox vb = new VBox();
        
        Label l = new Label(msg);
       
        Button ok = new Button("OK");
        ok.setOnAction((ActionEvent)->{
            stage.close();
        });
        vb.getChildren().addAll(l, ok);
        vb.setPadding(new Insets(20, 20, 20, 20));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        stage.setResizable(false);
        Scene sc = new Scene(vb);
        stage.setScene(sc);
        stage.show();
        
    }


}
