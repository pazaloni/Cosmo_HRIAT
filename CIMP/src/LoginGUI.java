import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginGUI extends Application
{
	private VBox mainPane;
	private TextField userName;
	private PasswordField userPassword;
	private Button loginBtn;
	private Image logo;

	public static void main(String[] args)
	{
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		initializeVariables();
		mainPane = createBox();
		Scene scene = new Scene(mainPane);
		mainPane.setAlignment(Pos.CENTER);
		stage.setScene(scene);
		stage.setTitle("Cosmopolitan Industries");

		stage.show();
	}

	/**
	 * Purpose: Create a login
	 * 
	 * @return
	 */
	private VBox createBox()
	{
		VBox box = new VBox();
		ImageView viewableLogo = new ImageView(logo);
		viewableLogo.maxWidth(500);
		viewableLogo.setFitWidth(500);
		viewableLogo.setFitHeight(250);
		box.getChildren()
				.addAll(viewableLogo, userName, userPassword, loginBtn);
		return box;

	}

	/**
	 * Initialize all of the global variables so they don't have a null pointer
	 * exception
	 */
	private void initializeVariables()
	{
		userName = new TextField();
		userPassword = new PasswordField();
		loginBtn = new Button("Login");
		logo = new Image("CosmoIconLong.png");

	}
}
