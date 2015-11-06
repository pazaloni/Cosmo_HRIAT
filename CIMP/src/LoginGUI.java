
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

/**
 * Purpose: Create the first page the user sees when they launch the program
 * 
 * @author TEAM CIMP
 * 
 */
public class LoginGUI extends Application
{
    /* Login Elements */
    private VBox vbMainPane;
    private TextField txtUserName;
    private PasswordField pfUserPassword;
    private Button btnLogin;
    private Image imgLogo;

    public static Stage stageLogin;

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
    //Hard coded array for testing purposes, do not add to diagrams.
    public static StaffAccount[] users = new StaffAccount[3];

    public static void main( String[] args )
    {
        // Following are test staff that we used to test our login system
        BasicStaff jeff = new BasicStaff(55501, "jill");
        users[0] = jeff;
        TechnicalAdministrator kevin = new TechnicalAdministrator(24561,
                "Bryant");
        users[1] = kevin;
        BasicStaff haar = new MedicalAdministrator(91023, "miranda");
        users[2] = haar;
        launch();
    }

    @Override
    public void start( Stage stage )
    {

        stageLogin = stage;
        initializeVariables();
        // adding the elements to the mainPane
        vbMainPane = createBox();
        vbMainPane.setAlignment(Pos.CENTER);

        // main scene to be displayed
        Scene scene = new Scene(vbMainPane);
        // Styling the scene
        stageLogin.setScene(scene);
        stageLogin.setTitle("Cosmopolitan Industries");
        stageLogin.setWidth(WIDTH);
        stageLogin.setHeight(HEIGHT);
        stageLogin.setTitle(STAGE_TITLE);
        stageLogin.setResizable(false);
        stageLogin.show();
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
        ImageView viewableLogo = new ImageView(imgLogo);
        // Setting image dimensions
        viewableLogo.setFitWidth(IMAGE_WIDTH);
        viewableLogo.setFitHeight(IMAGE_HEIGHT);
        // Adding everyting to the mainbox
        box.getChildren().addAll(viewableLogo, txtUserName, pfUserPassword,
                btnLogin);
        box.setSpacing(MAINPANE_SPACING);
        btnLogin.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                String username = txtUserName.getText();
                String password = pfUserPassword.getText();
                int i = 0;
                boolean success = false;
                // Checks the lenght of the textfileds
                if ( username.length() > 0 && password.length() > 0 )
                {
                    while ( i < users.length && !success )
                    {
                        // If the password and username are correct
                        if ( users[i].login(Integer.parseInt(username),
                                password) )
                        {
                            // If the logged in user is a technical
                            // administrator, show the tech admin main page
                            if ( users[i] instanceof TechnicalAdministrator )
                            {
                                success = true;
                                System.out.println("success");
                                stageLogin.close();
                                TechMainPageGUI techMainGui = new TechMainPageGUI();
                                techMainGui.techMainPageConstruct(stageLogin);
                            }
                            // Otherwise they are medical staff
                            else if ( users[i] instanceof BasicStaff
                                    || users[i] instanceof MedicalAdministrator )
                            {
                                success = true;
                                System.out.println("success");
                                stageLogin.close();
                                MedicalStaffMainPageGUI medStaffGUI = new MedicalStaffMainPageGUI();
                                medStaffGUI.medMainPageConstruct(stageLogin);
                            }
                        }
                        i++;
                    }
                }

                // If they didn't successfully login a incorrect username or
                // password will be displayed
                if ( success == false )
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
        btnLogin = new Button(BUTTON_LABEL);
        // making the image for the logo to be displayed
        imgLogo = new Image(IMAGE_PATH);
    }

}
