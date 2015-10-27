import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TechMainPageGUI extends Application{

	
	private Button viewLog = new Button();
	private Button addUser = new Button();
	private Button editUser = new Button();
	private Button logOut = new Button("Log Out");
	private Button removeUser = new Button();
	
	
	private int paneWidth = 875;
	private int paneHeight = 580;
	private Image logo = new Image("images/cosmo.png");
	
	private Label techTitle = new Label();
	
	private BorderPane managePane = new BorderPane();
	
	public void start(Stage stage) throws Exception
	{
		Scene scene = new Scene(managePane, paneWidth, paneHeight);
				
		HBox hbox = addHBox();
				
		managePane.setTop(hbox);
			
		stage.setScene(scene);
		stage.setTitle("Cosmopolitan Industries");
		stage.show();

	}

	

	public HBox addHBox() 
	{
		HBox hbox = new HBox();
		
		hbox.setPadding( new Insets(10,10,10,10));
			
		hbox.setAlignment(Pos.CENTER_LEFT);
		
		ImageView iLogo = new ImageView();
		
		iLogo.setImage(logo);
		
		logOut.setTranslateX(641);
		
		hbox.getChildren().addAll(iLogo,logOut);
		
		
		return hbox;
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
