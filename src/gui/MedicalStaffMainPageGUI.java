package gui;
import helpers.DatabaseHelper;
import helpers.NotePaneHelper;
import helpers.PreviewPaneHelper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthSeparatorUI;

import controllers.NoteTableViewController;
import controllers.ParticipantTableViewController;
import core.MedicalAdministrator;
import core.Note;
import core.StaffAccount;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

    private NoteTableViewController nTVCont;

    public static Stage medMainStage;

    private Button logout;

    private ComboBox<String> searchBy;

    private TextField searchField;

    private ImageView previewPicture;

    // the listview that will hold the note titles
    private ListView<String> noteTitleView;

    // the tableview used for the notes
    private TableView noteTableView;

    // the observable list used but the note tableview
    private ObservableList<Note> noteTitleList;

    private Stage createParticipantStage;

    private Stage viewParticipantDetailsStage = new Stage();

    private StaffAccount loggedInUser;

    // Preview labels for the participant
    private Label cosmoIDLbl;
    private Label firstNameLbl;
    private Label lastNameLbl;
    private Label seizureLbl;
    private Label allergyLbl;
    private Label physicianLbl;

    // Detail labels for the note details
    private Label dateInfoLabel;
    private Label staffInfoLabel;
    private Label participantInfoLabel;
    private Label subjectInfoLabel;

    private CheckBox resolvedCb;

    /**
     * Purpose: displays the GUI
     * 
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        medMainPageConstruct(stage, null);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff when they have
     * successfully logged in
     * 
     * @param stage: the stage the medical staff will see
     */
    public void medMainPageConstruct( Stage stage, StaffAccount loggedInStaff )
    {
        dbObject.connect();

        loggedInUser = loggedInStaff;
        dbObject.activtyLogEntry(loggedInStaff.GetUsername(), "Logged In", "");
        pTVCont = new ParticipantTableViewController();
        pTVCont.initialize();

        // the table view of notes
        this.noteTableView = new TableView<Note>();
        // the note table view controller instance
        nTVCont = new NoteTableViewController(noteTableView);

        medMainStage = stage;
        medMainStage.setTitle("Cosmo Industries - "
                + loggedInUser.GetUsername());

        VBox root = createMainVBox();

        medMainStage.setScene(new Scene(root, 1280, 720));
        medMainStage.resizableProperty().set(true);
        medMainStage.show();
        // Event for when stage is closed
        medMainStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle( WindowEvent we )
            {
                dbObject.activtyLogEntry(loggedInUser.GetUsername(), "Logout","");
            }
        });
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

                medMainStage.setOnCloseRequest(null);

                medMainStage.close();
                dbObject.activtyLogEntry(loggedInUser.GetUsername(), "Logout","");
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
        logoAndLogin.setFocusTraversable(false);
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
        Tab incidentForm = new Tab();
        HBox box = new HBox();

        // set text for tabs
        participants.setText("Participants");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(createHBoxPreviewNotes(),pTVCont.participantTable);
        participants.setContent(vbox);
        forms.setText("Forms");
        stats.setText("Stats");
        incidentForm.setText("Incident Reporting Form");
        
        // set tabs to not be closable
        forms.closableProperty().set(false);
        participants.closableProperty().set(false);
        stats.closableProperty().set(false);
        incidentForm.closableProperty().set(false);
        IncidentReportFormGUI irf = new IncidentReportFormGUI(medMainStage, loggedInUser);
        
        box.getChildren().add(irf.showIncidentReportForm());
        box.setAlignment(Pos.CENTER);
        incidentForm.setContent(box);
        // set the size of the tabs and add to the pane
        tabPane.setTabMinWidth(175);
       
        tabPane.getTabs().addAll(participants, forms);

        // if they are an administrator, add the stats tab
        if ( loggedInUser instanceof MedicalAdministrator )
        {
            tabPane.getTabs().addAll(stats,incidentForm);
        }

        // set initial selected tab to participants
        tabPane.getSelectionModel().select(participants);

        // set the changed property
        /*tabPane.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Tab>()
                {

                    @Override
                    public void changed( ObservableValue<? extends Tab> arg0,
                            Tab arg1, Tab mostRecentlySelectedTab )
                    {
                        if ( mostRecentlySelectedTab.equals(forms) )
                        {
                            IncidentReportFormGUI irf = new IncidentReportFormGUI(
                                    medMainStage, loggedInUser);
                            irf.showIncidentReportForm();
                        }
                        else if ( mostRecentlySelectedTab.equals(stats) )
                        {
                            QuarterlyReportGUI reportGUI = new QuarterlyReportGUI();
                            reportGUI.reportsMainStageConstruct(medMainStage,
                                    loggedInUser);
                        }

                    }

                });
*/
        tabPane.setMinHeight(29);
        tabPane.setFocusTraversable(false);
        return tabPane;
    }

    /**
     * 
     * Purpose: Create the HBox that will contain the Preview pane and the Note
     * pane
     * 
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
        hbox.setFocusTraversable(false);

        // create preview pane
        BorderPane previewPane = createPreviewPane();
        // create note box
        HBox noteBox;
        if ( loggedInUser.GetAccessLevel().equals("1") )
        {
            noteBox = createNoteBox();
        }
        else
        {
            noteBox = new HBox();
        }

        // add preview pane and note box together
        hbox.getChildren().addAll(previewPane, noteBox);

        return hbox;
    }

    /**
     * Purpose: Create the Preview Pane
     * 
     * @return
     */
    private BorderPane createPreviewPane()
    {
        // create border pane
        BorderPane previewPane = new BorderPane();

        // create picture box for left side of preview pane
        VBox pictureBox = new VBox();
        // default preview picture
        URL url = getClass().getResource("../images/defaultPicture.png");
        try
        {
            previewPicture = new ImageView(new Image(url.openStream()));
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        // details button
        Button detailsButton = new Button("View Details");

        // set margins
        VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));

        if ( loggedInUser instanceof MedicalAdministrator )
        {
            detailsButton.setOnAction(event -> {

                participantDetailsGUI detailsGUI = new participantDetailsGUI();
                // hard coded cosmo ID for testing purposes
                    detailsGUI.participantDetailsConstruct(
                            viewParticipantDetailsStage,
                            Integer.parseInt(pTVCont.getSelectedPK()),
                            loggedInUser);

                });
        }
        else
        {
            detailsButton.setOnAction(event -> {

                participantDetailsGUI detailsGUI = new participantDetailsGUI();
                // hard coded cosmo ID for testing purposes
                    detailsGUI.participantDetailsConstruct(
                            viewParticipantDetailsStage,
                            Integer.parseInt(pTVCont.getSelectedPK()),
                            loggedInUser);

                });
        }

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
        Label physicianLabel = new Label("Physician: ");

        // set label margins
        cosmoIDLabel.setPadding(new Insets(5, 5, 5, 5));
        firstNameLabel.setPadding(new Insets(5, 5, 5, 5));
        lastNameLabel.setPadding(new Insets(5, 5, 5, 5));
        physicianLabel.setPadding(new Insets(5, 5, 5, 5));

        seizureLabel.setPadding(new Insets(0, 5, 25, 5));
        allergyLabel.setPadding(new Insets(5, 5, 50, 5));

        seizureLabel.setMaxWidth(175);
        seizureLabel.setMinWidth(80);
        seizureLabel.setMaxHeight(40);
        seizureLabel.setMinHeight(65);
        seizureLabel.setWrapText(true);
        seizureLabel.setAlignment(Pos.TOP_LEFT);

        // set the participant Labels
        createPreviewLabels();

        // add all labels to the gridpane
        basicInfoPane.add(cosmoIDLabel, 0, 0);
        basicInfoPane.add(firstNameLabel, 0, 1);
        basicInfoPane.add(lastNameLabel, 0, 2);
        basicInfoPane.add(physicianLabel, 0, 3);
        basicInfoPane.add(seizureLabel, 0, 4);
        basicInfoPane.add(allergyLabel, 0, 5);

        basicInfoPane.add(cosmoIDLbl, 1, 0);
        basicInfoPane.add(firstNameLbl, 1, 1);
        basicInfoPane.add(lastNameLbl, 1, 2);
        basicInfoPane.add(physicianLbl, 1, 3);
        basicInfoPane.add(seizureLbl, 1, 4);
        basicInfoPane.add(allergyLbl, 1, 5);

        // set margins
        BorderPane.setMargin(pictureBox, new Insets(10, 0, 0, 10));
        BorderPane.setMargin(basicInfoPane, new Insets(10, 0, 0, 0));
        // previewPane.setTop(searchBar);
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        return previewPane;
    }

    /**
     * 
     * Purpose: Create the preview labes for the participant
     */
    private void createPreviewLabels()
    {

        cosmoIDLbl = new Label();
        firstNameLbl = new Label();
        lastNameLbl = new Label();
        seizureLbl = new Label();
        allergyLbl = new Label();
        physicianLbl = new Label();

        cosmoIDLbl.setMaxWidth(150);
        cosmoIDLbl.setMinWidth(150);

        firstNameLbl.setMaxWidth(150);
        firstNameLbl.setMinWidth(150);

        lastNameLbl.setMaxWidth(150);
        lastNameLbl.setMinWidth(150);

        physicianLbl.setMaxWidth(150);
        physicianLbl.setMinWidth(150);

        seizureLbl.setMaxWidth(175);
        seizureLbl.setMinWidth(175);
        seizureLbl.setMaxHeight(40);
        seizureLbl.setMinHeight(65);
        seizureLbl.setWrapText(true);
        seizureLbl.setAlignment(Pos.TOP_LEFT);

        allergyLbl.setMaxWidth(175);
        allergyLbl.setMinWidth(175);
        allergyLbl.setMaxHeight(80);
        allergyLbl.setMinHeight(80);
        allergyLbl.setWrapText(true);
        allergyLbl.setAlignment(Pos.TOP_LEFT);

        pTVCont.participantTable.setOnMousePressed(event -> {
            assignParticipantPreviewLabels(pTVCont.getSelectedPK());
        });
    }

    /**
     * 
     * Purpose: Set the text of all the preview pane labels to the currently
     * selected user
     */
    private void assignParticipantPreviewLabels( String participantID )
    {
        PreviewPaneHelper paneHelper = new PreviewPaneHelper();
        String[] currentParticipant = paneHelper
                .queryParticipant(participantID);
        cosmoIDLbl.setText(currentParticipant[0]);
        firstNameLbl.setText(currentParticipant[1]);
        lastNameLbl.setText(currentParticipant[2]);
        physicianLbl.setText(currentParticipant[3]);
        seizureLbl.setText(currentParticipant[4]);
        allergyLbl.setText(currentParticipant[5]);

        try
        {
            URL u = null;
            try
            {
                u = (this.getClass().getProtectionDomain().getCodeSource()
                        .getLocation().toURI().toURL());
            }
            catch ( URISyntaxException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String url = u.toString();

            url = url.substring(0,
                    url.length() - (url.length() - url.lastIndexOf("/")));

            url = url.replace("/bin", "");

            Image img = new Image(url + currentParticipant[6]);

            if ( !(img.isError()) )
            {
                previewPicture.setImage(img);
                previewPicture.setFitHeight(121);
                previewPicture.setFitWidth(122);
            }
            else
            {
                URL defaultURL = getClass().getResource(
                        "../images/defaultPicture.png");
                previewPicture.setImage(new Image(defaultURL.openStream()));
            }
        }
        catch ( IllegalArgumentException | IOException ie )
        {
            System.out.println("Image is invalid using default.");
        }
    }

    /**
     * 
     * Purpose:Create the search bar
     * 
     * @param
     * 
     * @return HBox search bar
     */
    private HBox createSearchBar()
    {
        // create search bar
        HBox searchBar = new HBox();
        searchBy = new ComboBox<String>();
        searchBy.getItems().addAll("Name", "Address", "Allergies", "CosmoID");
        // set width
        searchBy.setStyle("-fx-pref-width: 150;");
        searchBy.setPromptText(("Search By"));
        searchBy.setFocusTraversable(false);

        // create search field
        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-pref-width: 245; -fx-pref-height: 26;");
        searchField.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // search handler
                handleSearch();
            }

        });

        // search button
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(110, 20);
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // search handler
                handleSearch();
            }

        });

        // set margins
        HBox.setMargin(searchBy, new Insets(0, 5, 0, 10));
        HBox.setMargin(searchField, new Insets(0, 5, 0, 5));
        HBox.setMargin(searchButton, new Insets(0, 5, 0, 5));
        if ( loggedInUser instanceof MedicalAdministrator )
        {
            Button addParticipantButton = new Button("Add Participant");
            addParticipantButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle( ActionEvent e )
                {
                    // Open addNewParticipant Window
                    createParticipantStage = new Stage();
                    createParticipantStage.setTitle("Create Participant");

                    createParticipantStage.setScene(new Scene(
                            createParticipantPopUp(), 325, 425));
                    createParticipantStage
                            .initModality(Modality.APPLICATION_MODAL);
                    createParticipantStage.initOwner(medMainStage);
                    createParticipantStage.setResizable(false);
                    createParticipantStage.show();
                }
            });

            addParticipantButton.setPrefSize(200, 20);
            HBox.setMargin(addParticipantButton, new Insets(0, 5, 0, 5));
            searchBar.getChildren().addAll(searchBy, searchField, searchButton,
                    addParticipantButton);
        }
        else
        {
            searchBar.getChildren().addAll(searchBy, searchField, searchButton);
        }
        return searchBar;
    }

    /**
     * 
     * Purpose: prepare sql statement for query database
     */
    private void handleSearch()
    {
        String table = "Participant";
        String type = searchBy.getValue();
        // Checks if the search has a type, if it does not, then set type to
        // name.
        if ( type == null )
        {
            type = "Name";
        }
        String value = searchField.getText();
        String condition = "";
        // Prepares statement to check the full name of every participant if
        // name was selected
        if ( type == "Name" )
        {
            condition = "firstName + ' ' + lastName like '%" + value + "%'";
        }
        else if ( type == "Allergies" )
        {
            table = "Allergies";
            condition = value;
        }
        else
        {
            condition = type + " LIKE '%" + value + "%'";
        }
        // Checks if a value was entered, if one was then search for it, if
        // one was not then remove all the conditions on the search
        if ( value.equals("") )
        {
            pTVCont.refreshTable("", table);
        }
        else
        {
            pTVCont.refreshTable(condition, table);
        }
    }

    /**
     * 
     * Purpose: To create a pop up window to add the participant into the
     * database
     * 
     * @return a GridPane containing the form
     */
    protected GridPane createParticipantPopUp()
    {

        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label firstNameLbl = new Label("First Name");
        Label lastNameLbl = new Label("Last Name");
        Label birthdateLbl = new Label("Birthdate");
        Label physicianFNameLbl = new Label("Physician First Name");
        Label physicianLNameLbl = new Label("Physician Last Name");
        Label healthNumLbl = new Label("Health Number");
        Label phoneLbl = new Label("Phone Number");
        Label cosmoIdLbl = new Label("Cosmo ID");
        Label addressLbl = new Label("Participant Address");
        Label imageLbl = new Label("Participant Picture");

        // the text fields
        TextField firstNameTxt = new TextField();
        TextField lastNameTxt = new TextField();
        DatePicker birthDatePicker = new DatePicker();

        // Adding a click listener to make the date in thebox default to 20
        // years ago (client request)
        birthDatePicker.setOnMouseClicked(event -> {

            birthDatePicker.setValue(LocalDate.now().minusYears(20));

        });
        TextField physicianFNameTxt = new TextField();
        TextField physicianLNameTxt = new TextField();
        TextField healthNumTxt = new TextField();
        TextField phoneTxt = new TextField();
        phoneTxt.setPromptText("Ex: 3062879111");
        TextField cosmoIdTxt = new TextField();
        TextField addressTxt = new TextField();
        Button imageBrowseBtn = new Button("Browse...");
        TextField chosenPathTxt = new TextField("");

        imageBrowseBtn
                .setOnAction(event -> {
                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                            "Images", "*.jpg");
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(filter);

                    String path = "";

                    File file = fc.showOpenDialog(medMainStage);
                    if ( file == null )
                    {
                        path = "";
                    }
                    else
                    {
                        path = file.getAbsolutePath();
                    }
                    chosenPathTxt.setText(path);
                });

        // add the form to the grid
        grid.add(cosmoIdLbl, 0, 1);
        grid.add(firstNameLbl, 0, 2);
        grid.add(lastNameLbl, 0, 3);
        grid.add(birthdateLbl, 0, 4);
        grid.add(physicianFNameLbl, 0, 5);
        grid.add(physicianLNameLbl, 0, 6);
        grid.add(healthNumLbl, 0, 7);
        grid.add(phoneLbl, 0, 8);
        grid.add(addressLbl, 0, 9);
        grid.add(imageLbl, 0, 10);

        grid.add(lblWarning, 1, 0);
        grid.add(cosmoIdTxt, 1, 1);
        grid.add(firstNameTxt, 1, 2);
        grid.add(lastNameTxt, 1, 3);
        grid.add(birthDatePicker, 1, 4);
        grid.add(physicianFNameTxt, 1, 5);
        grid.add(physicianLNameTxt, 1, 6);
        grid.add(healthNumTxt, 1, 7);
        grid.add(phoneTxt, 1, 8);
        grid.add(addressTxt, 1, 9);
        grid.add(imageBrowseBtn, 1, 10);

        // setPadding of the grid
        grid.setPadding(new Insets(10, 10, 0, 10));

        grid.setHgap(10);

        grid.setVgap(10);

        // Adding participant event handler
        Button createParticipantBtn = new Button("Add");
        createParticipantBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // call create participant on medical administrator with the
                // text passed in
                String result = MedicalAdministrator.createParticipant(
                        cosmoIdTxt.getText(), firstNameTxt.getText(),
                        lastNameTxt.getText(), birthDatePicker.getValue(),
                        physicianFNameTxt.getText(),
                        physicianLNameTxt.getText(), healthNumTxt.getText(),
                        phoneTxt.getText(), addressTxt.getText(),
                        chosenPathTxt.getText());

                // if no error message is recieved then close this window and
                // refresh the table
                if ( result.equals("") )
                {
                    createParticipantStage.close();
                    pTVCont.refreshTable("", "Participant");
                    
                    //create activity log entry for creating this user
                    dbObject.activtyLogEntry(loggedInUser.GetUsername(), "Created Participant (" + cosmoIdTxt.getText() + ")",
                    "Name: \"" + firstNameTxt.getText() + " " + lastNameTxt.getText() + "\"" );

                }
                // if there is an error message, display it
                else
                {
                    lblWarning.setTextFill(Color.FIREBRICK);
                    lblWarning.setText(result);
                    if ( result.equals("Phone Number must be 10 digits") )
                    {
                        phoneTxt.setText("");
                        phoneTxt.setPromptText("Ex: 3062879111");
                    }
                }
            }
        });

        // reset the form event handler
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // sets all values to default
                cosmoIdTxt.setText("");
                firstNameTxt.setText("");
                lastNameTxt.setText("");
                birthDatePicker.setValue(null);
                physicianFNameTxt.setText("");
                physicianLNameTxt.setText("");
                healthNumTxt.setText("");
                phoneTxt.setText("");
                addressTxt.setText("");
                chosenPathTxt.setText("");
                lblWarning.setText("");

            }

        });

        // Add the buttons to the grid
        HBox buttonsHbox = new HBox();
        HBox resetHbox = new HBox();
        buttonsHbox.getChildren().addAll(createParticipantBtn);
        buttonsHbox.setAlignment(Pos.CENTER);
        resetHbox.getChildren().addAll(resetBtn);
        resetHbox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonsHbox, 1, 11);
        grid.add(resetHbox, 0, 11);

        return grid;
    }

    /**
     * 
     * Purpose: Create Note Box
     * 
     * @return HBox create note box
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    private HBox createNoteBox()
    {
        // the hbox instance
        HBox hbox = new HBox();
        // teh vbox instance
        VBox vbox = new VBox();

        // refresh the tableview
        nTVCont.refreshTable();

        // create the refresh button for the note list
        Button refreshBtn = new Button("Refresh Notes");

        // set the size of the refresh button to fit the note table's size
        refreshBtn.setMinWidth(170);
        refreshBtn.setMaxWidth(170);

        // set the refresh button to run the refresh function when clicked
        refreshBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent event )
            {

                nTVCont.refreshTable();

            }
        });

        // set the size of the tableview and make it unresizeable
        noteTableView.setMinWidth(170);
        noteTableView.setMaxWidth(170);
        noteTableView.setFocusTraversable(false);

        // add the refresh button and the note table to the vbox
        vbox.getChildren().addAll(refreshBtn, noteTableView);
        // set the alignment to center
        vbox.setAlignment(Pos.CENTER);
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

        // set the note text fields
        createNoteDetailLabels();

        // add all labels to the gridpane
        // column 0
        noteDisplayPane.add(dateLabel, 0, 0);
        noteDisplayPane.add(staffLabel, 0, 2);
        noteDisplayPane.add(participantLabel, 0, 1);
        noteDisplayPane.add(subjectLabel, 0, 3);
        noteDisplayPane.add(resolvedCb, 0, 4);
        
        // Initially the labes for notes will be empty because they haven't
        // selected a note yet
        dateInfoLabel = new Label();
        participantInfoLabel = new Label();
        staffInfoLabel = new Label();
        subjectInfoLabel = new Label();

        // column 1
        noteDisplayPane.add(dateInfoLabel, 1, 0);
        noteDisplayPane.add(subjectInfoLabel, 1, 3);
        noteDisplayPane.add(staffInfoLabel, 1, 2);
        noteDisplayPane.add(participantInfoLabel, 1, 1);

        // set minimum width
        noteDisplayPane.setMinWidth(265);
        // Sets the notebox's width to fit that of the parents window when it is
        // resized
        noteDisplayPane.prefWidthProperty().bind(
                medMainStage.widthProperty().divide(1.60));
        hbox.setPadding(new Insets(10, 0, 0, 0));
        // add the note table vbox to the hbox
        hbox.getChildren().addAll(vbox, noteDisplayPane);

        return hbox;
    }

    private void createNoteDetailLabels()
    {

        resolvedCb = new CheckBox("Resolved");

        nTVCont.noteTable.setOnMousePressed(event -> {
            assignNoteDetailLabels(nTVCont.getSelectedPK());
        });
    }

    /**
     * 
     * Purpose: Assign the information pulled from the database to the various
     * control
     * 
     * @param selectedPK the note selected
     */
    private void assignNoteDetailLabels( String selectedPK )
    {
        NotePaneHelper noteHelper = new NotePaneHelper();
        Note currentNote = noteHelper.queryNote(selectedPK);
        // Refresh table before removing
        nTVCont.refreshTable();
        if ( !nTVCont.noteIDs.isEmpty() )
        {
            dateInfoLabel.setText(currentNote.getDate());
            participantInfoLabel.setText(noteHelper
                    .getParticipantName(currentNote.getParticipant()));
            staffInfoLabel.setText(noteHelper.getStaffName(currentNote
                    .getCreatorID()));
            subjectInfoLabel.setText(currentNote.getDescription());
            resolvedCb.setSelected(Boolean.parseBoolean(currentNote
                    .getResolved()));

            resolvedCb.setOnAction(event -> {
                if ( resolvedCb.isSelected() )
                {

                    noteHelper.setNoteAsResolved(currentNote);
                    nTVCont.refreshTable();
                    dateInfoLabel.setText("");
                    staffInfoLabel.setText("");
                    participantInfoLabel.setText("");
                    subjectInfoLabel.setText("");
                    resolvedCb.setSelected(false);

                }
            });

            dateInfoLabel.setMaxWidth(150);
            dateInfoLabel.setMinWidth(150);

            staffInfoLabel.setMaxWidth(150);
            staffInfoLabel.setMinWidth(150);

            participantInfoLabel.setMaxWidth(150);
            participantInfoLabel.setMinWidth(150);

            subjectInfoLabel.setMaxWidth(150);
            subjectInfoLabel.setMinWidth(150);
            subjectInfoLabel.setWrapText(true);
        }
    }

    /**
     * 
     * Purpose: Create Main VBox
     * 
     * @param admin
     * 
     * @return VBox main VBox
     */
    private VBox createMainVBox()
    {
        VBox vbox = new VBox();

        // header HBox
        BorderPane header = createHBoxHeader();
        // tab pane
        TabPane tabs = createTabs();
        
        // Search bar
        HBox searchBar = createSearchBar();
        VBox.setMargin(searchBar, new Insets(5, 0, 5, 0));
        // preview notes
        //HBox previewNotes = createHBoxPreviewNotes();

        // add everything to vbox
        vbox.getChildren().addAll(header, searchBar, tabs 
                );

        return vbox;

    }
}
