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
import javafx.stage.Stage;

/**
 * 
 * Purpose: To display the main medical staff page,
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class MedicalStaffMainPageGUI extends Application
{
    
    private ParticipantTableViewController pTVCont;
    
    private DatabaseHelper dbObject = new DatabaseHelper();
    
    public static Stage medMainStage;

    private Button logout;

    private ComboBox<String> searchBy;

    private TextField searchField;

    private ImageView previewPicture;

    private ListView<String> noteTitleView;

    private ObservableList<String> noteTitleList;
    
    private Stage createParticipantStage;
    

    /**
     * Purpose: displays the GUI
     * 
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        medMainPageConstruct(stage, true);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff when they have
     * successfully logged in
     * 
     * @param stage: the stage the medical staff will see
     */
    public void medMainPageConstruct( Stage stage, boolean admin )
    {
        dbObject.connect();
        
        pTVCont = new ParticipantTableViewController();
        pTVCont.initialize();
        
        medMainStage = stage;
        medMainStage.setTitle("Cosmo Industries");

        VBox root = createMainVBox(admin);

        medMainStage.setScene(new Scene(root, 875, 580));
        medMainStage.resizableProperty().set(true);
        medMainStage.show();
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
                medMainStage.close();
                LoginGUI test5 = new LoginGUI();
                try
                {
                    test5.start(medMainStage);
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
        Tab participants = new Tab();
        Tab forms = new Tab();
        Tab stats = new Tab();

        // set text for tabs
        participants.setText("Participants");
        forms.setText("Forms");
        stats.setText("Stats");

        // set tabs to not be closable
        forms.closableProperty().set(false);
        participants.closableProperty().set(false);
        stats.closableProperty().set(false);

        // set the size of the tabs and add to the pane
        tabPane.setTabMinWidth(175);
        tabPane.getTabs().addAll(participants, forms, stats);
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
        // create note box
        HBox noteBox = createNoteBox();

        // add preview pane and note box together
        hbox.getChildren().addAll(previewPane, noteBox);

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

        // details button
        Button detailsButton = new Button("View Details");

        // set margins
        VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));

        // add picture and button to picture box
        pictureBox.getChildren().addAll(previewPicture, detailsButton);
        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setStyle("-fx-background-color: #FFFFFF;");
        pictureBox.setAlignment(Pos.TOP_CENTER);

        // create basic info pane
        GridPane basicInfoPane = new GridPane();
        basicInfoPane.setStyle("-fx-background-color: #FFFFFF;");
        basicInfoPane.setPadding(new Insets(10, 10, 0, 10));
        

        // set basic labels
        Label cosmoIDLabel = new Label("CosmoID:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name: ");
        Label seizureLabel = new Label("Seizures: ");
        Label allergyLabel = new Label("Allergies: ");

        // set label margins
        cosmoIDLabel.setPadding(new Insets(5, 5, 5, 5));
        firstNameLabel.setPadding(new Insets(5, 5, 5, 5));
        lastNameLabel.setPadding(new Insets(5, 5, 5, 5));
        seizureLabel.setPadding(new Insets(0, 5, 25, 5));
        allergyLabel.setPadding(new Insets(5, 5, 50, 5));
        
        seizureLabel.setMaxWidth(175);
        seizureLabel.setMinWidth(80);
        seizureLabel.setMaxHeight(40);
        seizureLabel.setMinHeight(65);
        seizureLabel.setWrapText(true);
        seizureLabel.setAlignment(Pos.TOP_LEFT);

        // set the participant Labels
        Label cosmoIDText = new Label("0");
        Label firstNameText = new Label("John");
        Label lastNameText = new Label("Doe");
        Label seizureText = new Label("None");
        Label allergyText = new Label("None");

        cosmoIDText.setMaxWidth(150);
        cosmoIDText.setMinWidth(150);

        firstNameText.setMaxWidth(150);
        firstNameText.setMinWidth(150);

        lastNameText.setMaxWidth(150);
        lastNameText.setMinWidth(150);

        seizureText.setMaxWidth(175);
        seizureText.setMinWidth(175);
        seizureText.setMaxHeight(40);
        seizureText.setMinHeight(65);
        seizureText.setWrapText(true);
        seizureText.setAlignment(Pos.TOP_LEFT);

        allergyText.setMaxWidth(175);
        allergyText.setMinWidth(175);
        allergyText.setMaxHeight(80);
        allergyText.setMinHeight(80);
        allergyText.setWrapText(true);
        allergyText.setAlignment(Pos.TOP_LEFT);

        // add all labels to the gridpane
        basicInfoPane.add(cosmoIDLabel, 0, 0);
        basicInfoPane.add(firstNameLabel, 0, 1);
        basicInfoPane.add(lastNameLabel, 0, 2);
        basicInfoPane.add(seizureLabel, 0, 3);
        basicInfoPane.add(allergyLabel, 0, 4);

        basicInfoPane.add(cosmoIDText, 1, 0);
        basicInfoPane.add(firstNameText, 1, 1);
        basicInfoPane.add(lastNameText, 1, 2);
        basicInfoPane.add(seizureText, 1, 3);
        basicInfoPane.add(allergyText, 1, 4);

        // set margins
        BorderPane.setMargin(pictureBox, new Insets(10, 0, 0, 10));
        BorderPane.setMargin(basicInfoPane, new Insets(10, 0, 0, 0));
        //previewPane.setTop(searchBar);
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        return previewPane;
    }

    /**
     * 
     * Purpose:Create the search bar
     * @param admin 
     * 
     * @return HBox search bar
     */
    private HBox createSearchBar(boolean admin)
    {
        // create search bar
        HBox searchBar = new HBox();
        searchBy = new ComboBox<String>();
        searchBy.getItems().addAll("Name", "Address", "Allergy",
                "CosmoID");

        // set width
        searchBy.setStyle("-fx-pref-width: 150;");
        searchBy.setPromptText(("Search By"));

        // create search field
        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-pref-width: 245; -fx-pref-height: 26;");

        // search button
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(110, 20);

        // set margins
        HBox.setMargin(searchBy, new Insets(0, 5, 0, 10));
        HBox.setMargin(searchField, new Insets(0, 5, 0, 5));
        HBox.setMargin(searchButton, new Insets(0, 5, 0, 5));
        if(admin)
        {
        	Button addParticipantButton = new Button("Add Participant");
        	addParticipantButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e ){
            	//Open addNewParticipant Window
            	createParticipantStage = new Stage();
            	createParticipantStage.setTitle("Create Participant");
            	
            	createParticipantStage.setScene(new Scene(createParticipantPopUp(), 300, 315));
            	createParticipantStage.show();
            }
            
        });
        	addParticipantButton.setPrefSize(200, 20);
        	HBox.setMargin(addParticipantButton, new Insets(0,5,0,5));
        	searchBar.getChildren().addAll(searchBy, searchField,
                    searchButton, addParticipantButton);
        }
        else
        {
        	searchBar.getChildren().addAll(searchBy, searchField,
                searchButton);
        }
        return searchBar;
    }

    /**
     * 
     * Purpose: To create a pop up window to add the participant into the database
     * @return a GridPane containing the form
     */
    protected GridPane createParticipantPopUp() {
    	
    	GridPane grid = new GridPane();
    	
    	//warning label
    	Label lblWarning = new Label();
    	lblWarning.setTextFill(Color.FIREBRICK);
        
    	//text field labels
        Label firstNameLbl = new Label("First Name");
        Label lastNameLbl = new Label("Last Name");
        Label birthdateLbl= new Label("Birthdate");
        Label familyPhysicianLbl = new Label("Family Physician");
        Label healthNumLbl = new Label("Health Number");
        Label phoneLbl = new Label("Phone Number");      
        Label cosmoIdLbl = new Label("Cosmo ID");
        
        //the text fields
        TextField firstNameTxt = new TextField();
        TextField lastNameTxt = new TextField();
        DatePicker birthDatePicker = new DatePicker();
        TextField familyPhysicianTxt = new TextField();
        TextField healthNumTxt = new TextField();
        TextField phoneTxt = new TextField();
        phoneTxt.setPromptText("Ex: 3062879111");
        
        TextField cosmoIdTxt = new TextField();
        
        //add the form to the grid
        grid.add(cosmoIdLbl, 0 , 1);
        grid.add(firstNameLbl, 0 , 2);
        grid.add(lastNameLbl, 0 , 3);
        grid.add(birthdateLbl, 0 , 4);
        grid.add(familyPhysicianLbl, 0 , 5);
        grid.add(healthNumLbl, 0 , 6);
        grid.add(phoneLbl, 0 , 7);      

        grid.add(lblWarning, 1, 0);
        grid.add(cosmoIdTxt, 1 , 1);
        grid.add(firstNameTxt, 1 , 2);
        grid.add(lastNameTxt, 1 , 3);
        grid.add(birthDatePicker, 1 , 4);
        grid.add(familyPhysicianTxt, 1 , 5);
        grid.add(healthNumTxt, 1 , 6);
        grid.add(phoneTxt, 1 , 7);

        //setPadding of the grid
        grid.setPadding(new Insets(10, 10, 0, 10));
        
        grid.setHgap(10);
        
        grid.setVgap(10);
        
        //Adding participant event handler
        Button createParticipantBtn = new Button("Add");
        createParticipantBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e ){
                //call create participant on medical administrator with the text passed in
            	String result = MedicalAdministrator.createParticipant(cosmoIdTxt.getText(), firstNameTxt.getText(), 
            	        lastNameTxt.getText(), birthDatePicker.getValue(), familyPhysicianTxt.getText(), 
            	        healthNumTxt.getText(), phoneTxt.getText() );
            	
            	//if no error message is recieved then close this window and refresh the table
                	if(result.equals(""))
                	{
                	    createParticipantStage.close();
                	    pTVCont.refreshTable();
                	}
                	//if there is an error message, display it
                	else
                	{
                        lblWarning.setTextFill(Color.FIREBRICK);
                	    lblWarning.setText(result);
                	    if(result.equals("Phone Number must be 10 digits"))
                	    {
                            phoneTxt.setText("");
                	        phoneTxt.setPromptText("Ex: 3062879111");
                	    }
                	}
            	}
            }
        );
        
        //reset the form event handler
        Button resetBtn	= new Button("Reset");
        resetBtn.setOnAction( new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                //sets all values to default
                cosmoIdTxt.setText("");
                firstNameTxt.setText("");
                lastNameTxt.setText("");
                birthDatePicker.setValue(null);
                familyPhysicianTxt.setText("");
                healthNumTxt.setText("");
                phoneTxt.setText("");
                lblWarning.setText("");
            }
    
        });
        
        //Add the buttons to the grid
        HBox buttonsHbox = new HBox();        
        HBox resetHbox = new HBox();      
        buttonsHbox.getChildren().addAll(createParticipantBtn);       
        buttonsHbox.setAlignment(Pos.CENTER);    
        resetHbox.getChildren().addAll(resetBtn);       
        resetHbox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonsHbox,1,8);      
        grid.add(resetHbox,0 ,8);
                    
		return grid;
	}

	/**
     * 
     * Purpose: Create Note Box
     * 
     * @return HBox create note box
     */
    private HBox createNoteBox()
    {
        HBox hbox = new HBox();
        noteTitleView = new ListView<String>();
        // create list of notes
        // TODO make this automatically pull from the database of notes
        noteTitleList = FXCollections.observableArrayList(
                "Note 1", "Note 2", "Note 3", "Note 4", "Note 5", "Note 6",
                "Note 7", "Note 8", "Note 9", "Note 10", "Note 11");

        // set notes list to listview
        noteTitleView.setItems(noteTitleList);
        noteTitleView.setMinWidth(170);
        noteTitleView.setMaxWidth(170);

        // note display pane
        GridPane noteDisplayPane = new GridPane();

        noteDisplayPane.setStyle("-fx-background-color: #FFFFFF;");
        noteDisplayPane.setPadding(new Insets(10, 10, 0, 10));

        // set basic labels
        Label dateLabel = new Label("Date:");
        Label staffLabel = new Label("Staff:");
        Label participantLabel = new Label("Participant: ");
        Label subjectLabel = new Label("Subject: ");

        // set label margins
        dateLabel.setPadding(new Insets(1, 5, 3, 5));
        staffLabel.setPadding(new Insets(1, 5, 3, 5));
        participantLabel.setPadding(new Insets(1, 5, 3, 5));
        subjectLabel.setPadding(new Insets(1, 5, 3, 5));

        // set the participant text fields
        Label dateInfoLabel = new Label("dateInfo");
        Label staffInfoLabel = new Label("staffInfo");
        Label participantInfoLabel = new Label("participantInfo");
        Label subjectInfoLabel = new Label("subjectInfo");

        // set padding
        dateInfoLabel.setPadding(new Insets(1, 5, 3, 5));
        staffInfoLabel.setPadding(new Insets(1, 5, 3, 5));
        participantInfoLabel.setPadding(new Insets(1, 5, 3, 5));
        subjectInfoLabel.setPadding(new Insets(1, 5, 3, 5));

        // add all labels to the gridpane
        // column 0
        noteDisplayPane.add(dateLabel, 0, 0);
        noteDisplayPane.add(staffLabel, 0, 1);
        noteDisplayPane.add(participantLabel, 0, 2);
        noteDisplayPane.add(subjectLabel, 0, 3);

        // column 1
        noteDisplayPane.add(dateInfoLabel, 1, 0);
        noteDisplayPane.add(staffInfoLabel, 1, 1);
        noteDisplayPane.add(participantInfoLabel, 1, 2);
        noteDisplayPane.add(subjectInfoLabel, 1, 3);

        // set minimum width
        noteDisplayPane.setMinWidth(265);
        //Sets the notebox's width to fit that of the parents window when it is resized
        noteDisplayPane.prefWidthProperty().bind(medMainStage.widthProperty().divide(1.60));
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.getChildren().addAll(noteTitleView, noteDisplayPane);

        return hbox;
    }

    /**
     * 
     * Purpose: Create Main VBox
     * @param admin 
     * 
     * @return VBox main VBox
     */
    private VBox createMainVBox(boolean admin)
    {
        VBox vbox = new VBox();

        // header HBox
        BorderPane header = createHBoxHeader();
        // tab pane
        TabPane tabs = createTabs();
        //Search bar
        HBox searchBar = createSearchBar(admin);
        VBox.setMargin(searchBar, new Insets(5,0,5,0));
        // preview notes
        HBox previewNotes = createHBoxPreviewNotes();


        // add everything to vbox
        vbox.getChildren().addAll(header, tabs, searchBar, previewNotes, pTVCont.participantTable);

        return vbox;
    }

}
