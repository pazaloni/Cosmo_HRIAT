import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * Purpose: To display the GUI for the technical administrator
 * @author cst214,cst209,cst207
 *
 */
public class TechMainPageGUI extends Application 
{

	public static Stage techMainStage;
	
	private StaffAccount currentUser;
	//Button when clicked, will bring up the activity log
	private Button viewLog = new Button();
	
	//Button, when clicked will bring up the add user menu
	private Button addUser = new Button();
	
	//Button, when clicked will bring up window of user properties to be edited
	private Button editUser = new Button();
	
	//will log out the current technical admin
	private Button logOut = new Button("Log Out");
	
	//Once a user is highlighted, clicking this button will prompt for assurance
	//and then the removal of th user
	private Button removeUser = new Button();

	//The main windows dimensions, in pixels
	private static final int PANE_WIDTH = 875;	
	private static final int PANE_HEIGHT = 580;
	
	//The clients logo displayed at the top.
	private Image logo = new Image("images/CosmoIconLong.png");

	//heading of the page "Technical Administration"
	private Label techTitle = new Label();

	//the main container for the page.
	private VBox layoutContainer = new VBox();

	public TechMainPageGUI()
	{
		
	}
	
	public void test1(Stage stage)
	{
		techMainStage = stage;
		//The scene that displays the main layout container with the preferred
		//dimensions
		Scene scene = new Scene(layoutContainer, PANE_WIDTH, PANE_HEIGHT);

		//Vbox used to hold the main content of the page (Buttons, Table, etc)
		VBox managePane = new VBox();
		
		//the borderpane created in another method, that will hold the logout 
		//button and the clients logo.
		BorderPane headerLogin = createHeader();

		//Formatting for the heading of the page "Technical Administration" 
		Label pageName = new Label();
		pageName.setText("Technical Administration");
		pageName.setFont(new Font(30));
		pageName.setPadding(new Insets(10, 0, 0, 0));

		//The Hbox that contains the row of 4 buttons on the page
		HBox actionBox = new HBox();

		//Formating for the viewLog Button
		viewLog.setText("View Activity Log");
		viewLog.setFont(new Font(15));
		viewLog.setMinWidth(150);
		
		//Formating for the addUser Button
		addUser.setText("Add User");
		addUser.setMinWidth(150);
		addUser.setFont(new Font(15));
		
		//Formating for the editUser Button
		editUser.setText("Edit User");
		editUser.setMinWidth(150);
		editUser.setFont(new Font(15));
		
		//Formating for the removeUser Button
		removeUser.setText("Remove User");
		removeUser.setMinWidth(150);
		removeUser.setFont(new Font(15));

		//appends buttons to the action box to be displayed, and formatts the
		//actionBox
		actionBox.getChildren().addAll(viewLog, addUser, editUser, removeUser);
		actionBox.setPadding(new Insets(25, 0, 20, 0));
		actionBox.setAlignment(Pos.CENTER);
		actionBox.setSpacing(75);

		//label for the table "Manage Users", and formatting
		Label tableName = new Label();
		tableName.setText("Manage Users");
		tableName.setFont(new Font(20));

		//TableView instance to hold User records
		TableView<String> table = new TableView<String>();
		
		//Instantiation of all the table column headings (With proper formatting)
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

		//Appending column headers to the table for display
		table.getColumns().addAll(staffIDCol, userNameCol, emailCol,
				firstNameCol, lastNameCol, securityLvlCol);

		//Formatting for the managePane, as well as the appending of the pages
		//main content
		managePane.setPadding(new Insets(0, 30, 0, 30));
		managePane.getChildren().addAll(pageName, actionBox, tableName, table);
		
		//appending the two main containers to the layOut container
		layoutContainer.getChildren().addAll(headerLogin, managePane);
			

		
		//final stage preparation and titling
		techMainStage.setScene(scene);
		techMainStage.setTitle("Cosmopolitan Industries");
		//display window
		techMainStage.show();
		
		
		
	}
	
	/**
	 * Description: Prepares the window for display
	 * Param: The stage to be launched
	 * 
	 */
	public void start(Stage stage)
	{
		
		test1(stage);
		

	}

	/**
	 * Description: Prepares header for display. Also creates the log out button
	 * and logo
	 * @return The formatted and created borderpane
	 */
	public BorderPane createHeader() 
	{
		//the borderpane to return
		BorderPane logoAndLogout = new BorderPane();
		
		//set formatting for borderpane
		logoAndLogout.setPadding(new Insets(15, 12, 15, 12));		
		logoAndLogout.setStyle("-fx-background-color: #FFFFFF;");

		//new button for log out, as well as formatting
		logOut = new Button("Log Out");
		logOut.setPrefSize(100, 20);

		
		logOut.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
        		techMainStage.close();
        		LoginGUI test5 = new LoginGUI();
        		test5.start(techMainStage);
	        }
		});
		
		//ImageView that will hold and display the clients logo, and formatting
		ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
		logo.setFitWidth(400);
		logo.setFitHeight(49);
		
		

		//Insert the two children into the borderpane at their preferred sides.
		logoAndLogout.setLeft(logo);
		logoAndLogout.setRight(logOut);
	
		//return finished borderPane
		return logoAndLogout;
	}

	public static void main(String[] args) 
	{
		launch(args);
	}


	public void openlog() 
	{

	}

	public void addUser() 
	{

	}

	public void editUser(int staffID) 
	{

	}

	public void removeUser(int staffID) 
	{

	}

}
