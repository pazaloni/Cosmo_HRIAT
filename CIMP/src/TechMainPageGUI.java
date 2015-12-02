import java.sql.*;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Purpose: To display the GUI for the technical administrator
 * 
 * @author cst214,cst209,cst207
 *
 */
public class TechMainPageGUI extends Application
{
	//Controller used to fill the tableview
	private StaffTableViewController sTVCont;

	//Instance of the database helper 
	private DatabaseHelper dbObject = new DatabaseHelper();
	
	//The stage
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

    public void techMainPageConstruct( Stage stage )
    {
    	//open the database connection
    	dbObject.connect();
    	
    	//create a staff table view controller and initialize it
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
        
        //when the remove user is clicked, the selected account from the table 
        //list will run the removeUser method using its primary key
        btnRemoveUser.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
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
        actionBox.getChildren().addAll(btnViewLog, btnAddUser, btnEditUser, btnRemoveUser);
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
        managePane.getChildren().addAll(pageName, actionBox, tableName, sTVCont.staffTable);

        // appending the two main containers to the layOut container
        vbLayoutContainer.getChildren().addAll(headerLogin, managePane);

        // final stage preparation and titling
        stageTech.setScene(scene);
        stageTech.setTitle("Cosmopolitan Industries");
        // display window
        stageTech.show();

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

    public static void main( String[] args )
    {
        launch(args);
    }

    public void openlog()
    {

    }

    public void addUser()
    {

    }

    public void editUser( String staffID )
    {

    }

    /**
     * Purpose:	This will take the selected user from the table, confirm that 
     * 			you wish to delete them, if so, will delete the selected user, 
     * 			then refresh the table of accounts
     * @param staffID	The user that you will remove
     */
    public void removeUser( String staffID )
    {
    	Stage stage = new Stage();
    	PopUpCheck checkBox = new PopUpCheck("Are you sure you want to delete "
    			+ staffID + "?", stage);
    	
		Scene scene = new Scene(checkBox.root, 300, 75);
    	stage.setScene(scene);
    	stage.showAndWait();
    
    	if(checkBox.result)
    	{
    		this.dbObject.delete("Staff", "UserName = \"" + staffID + "\"");
    		this.sTVCont.removeViewableUser(staffID);
    	}
	}

}
