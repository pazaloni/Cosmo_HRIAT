import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * Purpose: To display the main medical staff page,
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class participantDetailsGUI extends Application
{
    
    private ParticipantTableViewController pTVCont;
    
    private DatabaseHelper dbObject = new DatabaseHelper();
    
    public static Stage participantMainStage;

    private Button logout;

    private ImageView previewPicture;

    private ListView<String> noteTitleView;

    private ObservableList<String> noteTitleList;
    
    private Stage createParticipantStage;
    
    private VBox allergiesBox = new VBox();
    
    private VBox seizuresBox = new VBox();
    

    /**
     * Purpose: displays the GUI
     * 
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        participantDetailsConstruct(stage);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff when they have
     * successfully logged in
     * 
     * @param stage: the stage the medical staff will see
     */
    public void participantDetailsConstruct( Stage stage )
    {
        dbObject.connect();
        
        
        participantMainStage = stage;
        participantMainStage.setTitle("Cosmo Industries");

        VBox root = createMainVBox();

        participantMainStage.setScene(new Scene(root, 875, 580));
        participantMainStage.resizableProperty().set(true);
        participantMainStage.show();
    }

    /**
     * 
     * Purpose: Create the header area of the program.
     * 
     * 
     * @return a BorderPane
     */
    public BorderPane createHBoxHeader()
    {
        BorderPane logoAndLogin = new BorderPane();
        logoAndLogin.setPadding(new Insets(15, 12, 15, 12));
        logoAndLogin.setStyle("-fx-background-color: #FFFFFF;");

        // Logout button
        logout = new Button("Log Out");
        logout.setPrefSize(100, 20);

        logout.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                participantMainStage.close();
                LoginGUI test5 = new LoginGUI();
                try
                {
                    test5.start(participantMainStage);
                }
                catch ( Exception e1 )
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        // logo image size
        ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
        logo.setFitWidth(400);
        logo.setFitHeight(49);

        // set the image left and right
        logoAndLogin.setLeft(logo);
        logoAndLogin.setRight(logout);

        return logoAndLogin;
    }

    /**
     * 
     * Purpose: Create the Tabs Pane for display
     * 
     * @return a TabPane object
     */
    private TabPane createTabs()
    {
        TabPane tabPane = new TabPane();

        // Create tabs names
        Tab medicalInformation = new Tab();
        Tab medications = new Tab();
        Tab vaccinationDetails = new Tab();
        Tab other = new Tab();

        // set text for tabs
        medicalInformation.setText("Medical Information");
        medications.setText("Medications");
        vaccinationDetails.setText("Vaccination Details");
        other.setText("Other");

        // set tabs to not be closable
        medications.closableProperty().set(false);
        medicalInformation.closableProperty().set(false);
        vaccinationDetails.closableProperty().set(false);
        other.closableProperty().set(false);

        // set the size of the tabs and add to the pane
        tabPane.setTabMinWidth(175);
        tabPane.getTabs().addAll(medicalInformation, medications, vaccinationDetails, other);
        tabPane.setMinHeight(29);

        return tabPane;
    }

    /**
     * 
     * Purpose: Create the HBox that will contain the Preview pane and the Note
     * pane
     * @return
     */
    private HBox createHBoxPreviewNotes()
    {
        // create hbox and set size and padding
        HBox hbox = new HBox();
        hbox.setMinHeight(305);
        hbox.setMaxHeight(305);
        hbox.setPadding(new Insets(0, 0, 10, 0));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        // create preview pane
        BorderPane previewPane = createPreviewPane();
        
        //create allergies and seizures pane
        VBox allergiesAndSeizuresPane = createAllergiesAndSeizuresInfoPane();

        // add preview pane and the allergies and seizures pane
        hbox.getChildren().addAll(previewPane, allergiesAndSeizuresPane);
        
        return hbox;
    }

    /**
     * Purpose: Create the Preview Pane
     * @return
     */
    private BorderPane createPreviewPane()
    {
        // create border pane
        BorderPane previewPane = new BorderPane();

        // create picture box for left side of preview pane
        VBox pictureBox = new VBox();
        // default preview picture
        URL url = getClass().getResource("images/defaultPicture.png");
        try
        {
            previewPicture = new ImageView(new Image(url.openStream()));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // set margins
        VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));

        // add picture and button to picture box
        pictureBox.getChildren().addAll(previewPicture);
        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setStyle("-fx-background-color: #FFFFFF;");
        pictureBox.setAlignment(Pos.TOP_CENTER);
        
        //create buttons
        Button editBtn = new Button("Edit");
        Button viewDocumentsBtn = new Button("View \nAttached \nDocuments");
        Button generateFormsBtn = new Button("Generate Forms");
        
        //set sizes and padding
        editBtn.setMaxWidth(100);
        editBtn.setMinWidth(100);
        
        viewDocumentsBtn.setMaxWidth(100);
        viewDocumentsBtn.setMinWidth(100);
        viewDocumentsBtn.setMinHeight(60);
        viewDocumentsBtn.setMaxHeight(60);
        
        generateFormsBtn.setMaxWidth(100);
        generateFormsBtn.setMinWidth(100);
        
        //add buttons to the previewPane
        pictureBox.getChildren().addAll(editBtn, viewDocumentsBtn, generateFormsBtn);
        

        // create basic info pane
        GridPane basicInfoPane = new GridPane();
        basicInfoPane.setStyle("-fx-background-color: #FFFFFF;");
        basicInfoPane.setPadding(new Insets(10, 10, 0, 10));
        

        // set basic labels
        Label cosmoIDLabel = new Label("CosmoID:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name: ");
        Label phnLabel = new Label("PHN: ");
        Label diagnosislabel = new Label("Diagnosis: ");
        Label addressLabel = new Label("Address: ");

        // set label margins
        cosmoIDLabel.setPadding(new Insets(5, 5, 5, 5));
        firstNameLabel.setPadding(new Insets(5, 5, 5, 5));
        lastNameLabel.setPadding(new Insets(5, 5, 5, 5));
        phnLabel.setPadding(new Insets(5,5,5,5));
        diagnosislabel.setPadding(new Insets(5,5,5,5));
        addressLabel.setPadding(new Insets(5,5,5,5));

        // set the participant Labels
        Label cosmoIDText = new Label("0");
        Label firstNameText = new Label("John");
        Label lastNameText = new Label("Doe");
        Label phnText = new Label("");
        Label diagnosisText = new Label("To be determined");
        Label addressText = new Label("123 Fake Street");

        cosmoIDText.setMaxWidth(150);
        cosmoIDText.setMinWidth(150);

        firstNameText.setMaxWidth(150);
        firstNameText.setMinWidth(150);

        lastNameText.setMaxWidth(150);
        lastNameText.setMinWidth(150);
        
        phnText.setMaxWidth(150);
        phnText.setMinWidth(150);
        
        diagnosisText.setMaxWidth(150);
        diagnosisText.setMinWidth(150);
        
        addressText.setMaxWidth(150);
        addressText.setMinWidth(150);


        // add all labels to the gridpane
        basicInfoPane.add(cosmoIDLabel, 0, 0);
        basicInfoPane.add(firstNameLabel, 0, 1);
        basicInfoPane.add(lastNameLabel, 0, 2);
        basicInfoPane.add(phnLabel, 0, 3);
        basicInfoPane.add(diagnosislabel, 0, 4);
        basicInfoPane.add(addressLabel, 0, 5);

        basicInfoPane.add(cosmoIDText, 1, 0);
        basicInfoPane.add(firstNameText, 1, 1);
        basicInfoPane.add(lastNameText, 1, 2);
        basicInfoPane.add(phnText, 1, 3);
        basicInfoPane.add(diagnosisText, 1, 4);
        basicInfoPane.add(addressText, 1, 5);

        // set margins
        BorderPane.setMargin(pictureBox, new Insets(10, 0, 0, 10));
        BorderPane.setMargin(basicInfoPane, new Insets(10, 0, 0, 0));
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        return previewPane;
    }




    /**
     * 
     * Purpose: Create Main VBox
     * @return VBox main VBox
     */
    private VBox createMainVBox()
    {
        VBox vbox = new VBox();

        // header HBox
        BorderPane header = createHBoxHeader();
        // tab pane
        TabPane tabs = createTabs();
        // preview notes
        HBox previewNotes = createHBoxPreviewNotes();


        // add everything to vbox
        vbox.getChildren().addAll(header, previewNotes, tabs);

        return vbox;
    }
    
    private VBox createAllergiesAndSeizuresInfoPane()
    {
    	VBox vbox = new VBox();
    	
    	vbox.setStyle("-fx-background-color: #FFFFFF;");
    	vbox.setPadding(new Insets(10, 10, 0, 10));
    	
    	Label allergiesLabel = new Label("Allergies:");
    	Label seizuresLabel = new Label("Seizures:");
    	
    	vbox.getChildren().addAll(allergiesLabel, allergiesBox, 
    			seizuresLabel, seizuresBox);
    	
    	return vbox;
    }

}
