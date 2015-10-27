import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TechMainPageGUI extends Application {

	private Button viewLog = new Button();
	private Button addUser = new Button();
	private Button editUser = new Button();
	private Button logOut = new Button("Log Out");
	private Button removeUser = new Button();

	private int paneWidth = 875;
	private int paneHeight = 580;
	private Image logo = new Image("images/CosmoIconLong.png");

	private Label techTitle = new Label();

	
	private VBox layoutContainer = new VBox();

	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(layoutContainer, paneWidth, paneHeight);

		VBox managePane = new VBox();
		BorderPane headerLogin = createHeader();
		
		//managePane.getChildren().addAll(headerLogin);
		Label pageName = new Label();
		pageName.setText("Technical Administration");
		pageName.setFont(new Font(30));
		pageName.setPadding(new Insets(10,0,0,0));
		
		HBox actionBox = new HBox();
		
		viewLog.setText("View Activity Log");
		viewLog.setFont(new Font(15));
		viewLog.setMinWidth(150);
		addUser.setText("Add User");
		addUser.setMinWidth(150);
		addUser.setFont(new Font(15));
		editUser.setText("Edit User");
		editUser.setMinWidth(150);
		editUser.setFont(new Font(15));
		removeUser.setText("Remove User");
		removeUser.setMinWidth(150);
		removeUser.setFont(new Font(15));
				
		actionBox.getChildren().addAll(viewLog, addUser, editUser, removeUser);
		actionBox.setPadding(new Insets(25,0,20,0));
		actionBox.setAlignment(Pos.CENTER);
		actionBox.setSpacing(75);
		
		Label tableName = new Label();
		tableName.setText("Manage Users");
		tableName.setFont(new Font(20));

				
		TableView<String> table = new TableView<String>();
		//<StaffAccounts>
		 
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
				
		table.getColumns().addAll(staffIDCol,userNameCol, emailCol, firstNameCol, lastNameCol,securityLvlCol);

		managePane.setPadding(new Insets(0,30,0,30));
		managePane.getChildren().addAll(pageName, actionBox, tableName, table);
		layoutContainer.getChildren().addAll(headerLogin, managePane);
		stage.setScene(scene);
		stage.setTitle("Cosmopolitan Industries");
		stage.show();

	}

	public BorderPane createHeader() {
		BorderPane logoAndLogin = new BorderPane();
		logoAndLogin.setPadding(new Insets(15, 12, 15, 12));
		// logoAndLogin.setSpacing(10);
		logoAndLogin.setStyle("-fx-background-color: #FFFFFF;");

		Button buttonCurrent = new Button("Log Out");
		buttonCurrent.setPrefSize(100, 20);

		ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));	
		logo.setFitWidth(400);
		logo.setFitHeight(49);

		logoAndLogin.setLeft(logo);
		logoAndLogin.setRight(buttonCurrent);
		// logoAndLogin.getChildren().addAll(logo,buttonCurrent);

		return logoAndLogin;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void openlog() {

	}

	public void addUser() {

	}

	public void editUser(int staffID) {

	}

	public void removeUser(int staffID) {

	}

}
