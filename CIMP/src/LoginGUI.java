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
	/* Login Elements */
	private VBox mainPane;
	private TextField userName;
	private PasswordField userPassword;
	private Button loginBtn;
	private Image logo;

	/* Static final variables */
	public static final int WIDTH = 875;
	public static final int HEIGHT = 580;
	public static final int IMAGE_WIDTH = 606;
	public static final int IMAGE_HEIGHT = 75;
	public static final int TEXTFIELD_WIDTH = 190;
	public static final int MAINPANE_SPACING = 10;
	public static final String USERNAME_PROMPT_TEXT = "Username";
	public static final String PASSWORD_PROMPT_TEXT = "Password";
	public static final String STAGE_TITLE = "Cosmopolitan Industries";
	public static final String IMAGE_PATH = "CosmoIconLong.png";
	public static final String BUTTON_LABEL = "Login";

	public static void main(String[] args)
	{
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		initializeVariables();
		// adding the elements to the mainPane
		mainPane = createBox();
		mainPane.setAlignment(Pos.CENTER);

		// main scene to be displayed
		Scene scene = new Scene(mainPane);
		// Styling the scene
		stage.setScene(scene);
		stage.setTitle("Cosmopolitan Industries");
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.setTitle(STAGE_TITLE);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Purpose: Create a login box and add the logo, username, and password
	 * textfield
	 * 
	 * @return a new box with the login elements
	 */
	private VBox createBox()
	{
		VBox box = new VBox();
		// Viewable image of the logo
		ImageView viewableLogo = new ImageView(logo);
		// Setting image dimensions
		viewableLogo.setFitWidth(IMAGE_WIDTH);
		viewableLogo.setFitHeight(IMAGE_HEIGHT);
		// Adding everyting to the mainbox
		box.getChildren()
				.addAll(viewableLogo, userName, userPassword, loginBtn);
		box.setSpacing(MAINPANE_SPACING);
		return box;
	}

	/**
	 * Initialize all of the global variables so they don't have a null pointer
	 * exception
	 */
	private void initializeVariables()
	{
		userName = new TextField();
		// Setting the placeholder text in the textfield
		userName.setPromptText(USERNAME_PROMPT_TEXT);
		// Setting the maximum width the textfield
		userName.setMaxWidth(TEXTFIELD_WIDTH);

		userPassword = new PasswordField();
		// Setting the placeholder text in the textfield
		userPassword.setPromptText(PASSWORD_PROMPT_TEXT);
		// Setting the maximum width the textfield
		userPassword.setMaxWidth(TEXTFIELD_WIDTH);
		// making the new button
		loginBtn = new Button(BUTTON_LABEL);
		// making the image for the logo to be displayed
		logo = new Image(IMAGE_PATH);
	}
}
