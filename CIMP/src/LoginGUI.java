import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private TextField txtUserName;
	private PasswordField pfUserPassword;
	private Button loginBtn;
	private Image logo;

	public static Stage loginStage;

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
	public static final String IMAGE_PATH = "images/CosmoIconLong.png";
	public static final String BUTTON_LABEL = "Login";
	public static StaffAccount[] users = new StaffAccount[3];
	
	public static void main(String[] args)
	{
		BasicStaff jeff = new BasicStaff(0, "jill");
		users[0] = jeff;
		TechnicalAdministrator kevin = new TechnicalAdministrator(1, "Bryant");
		users[1] = kevin;
		BasicStaff haar = new BasicStaff(2, "miranda");
		users[2] = haar;
		launch();
	}

	@Override
	public void start(Stage stage) 
	{
		
		loginStage=stage;
		initializeVariables();
		// adding the elements to the mainPane
		mainPane = createBox();
		mainPane.setAlignment(Pos.CENTER);

		// main scene to be displayed
		Scene scene = new Scene(mainPane);
		// Styling the scene
		loginStage.setScene(scene);
		loginStage.setTitle("Cosmopolitan Industries");
		loginStage.setWidth(WIDTH);
		loginStage.setHeight(HEIGHT);
		loginStage.setTitle(STAGE_TITLE);
		loginStage.setResizable(false);
		loginStage.show();
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
				.addAll(viewableLogo, txtUserName, pfUserPassword, loginBtn);
		box.setSpacing(MAINPANE_SPACING);
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        String username = txtUserName.getText();
		        String password = pfUserPassword.getText();
		        int i = 0;
		        boolean success = false;
		        if(username.length() > 0 && password.length() > 0)
		        {
		        	while(i<users.length && !success)
			        {	
			        	
			        	if(users[i].login(Integer.parseInt(username), password))
			        	{
			        		if(users[i] instanceof TechnicalAdministrator)
			        		{
			        		success = true;
			        		System.out.println("success");
			        		loginStage.close();
			        		TechMainPageGUI test5 = new TechMainPageGUI();
			        		test5.TechMainPageConstruct(loginStage);
			        		}
			        		else if(users[i] instanceof BasicStaff ||
			        				users[i] instanceof MedicalAdministrator)
			        		{
			        			success = true;
				        		System.out.println("success");
				        		loginStage.close();
				        		MedicalStaffMainPageGUI medStaffGUI = new MedicalStaffMainPageGUI();
				        		medStaffGUI.MedMainPageConstruct(loginStage);
			        		}
			        	}
			        	i++;
			        }	
		        }
		        	    
		        if(success == false)
		        {
		        	txtUserName.setText("");
		        	pfUserPassword.setText("");
		        	txtUserName.setPromptText("Incorrect Username or Password");
		        }
	        }
		});
		return box;
	}

	/**
	 * Initialize all of the global variables so they don't have a null pointer
	 * exception
	 */
	private void initializeVariables()
	{
		txtUserName = new TextField();
		// Setting the placeholder text in the textfield
		txtUserName.setPromptText(USERNAME_PROMPT_TEXT);
		// Setting the maximum width the textfield
		txtUserName.setMaxWidth(TEXTFIELD_WIDTH);

		pfUserPassword = new PasswordField();
		// Setting the placeholder text in the textfield
		pfUserPassword.setPromptText(PASSWORD_PROMPT_TEXT);
		// Setting the maximum width the textfield
		pfUserPassword.setMaxWidth(TEXTFIELD_WIDTH);
		// making the new button
		loginBtn = new Button(BUTTON_LABEL);
		// making the image for the logo to be displayed
		logo = new Image(IMAGE_PATH);
	}
	
	
	public boolean login(String username, String password)
	{
		boolean successful = false;
		
		
		
		return successful;
	}
}
