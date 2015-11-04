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
    public static Stage MedMainStage;

    public static void main( String[] args )
    {
        launch();
    }

    /**
     * Purpose: entry point for program
     * 
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        MedMainPageConstruct(stage);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff when they have
     * successfully logged in
     * 
     * @param stage: the stage the medical staff will see
     */
    public void MedMainPageConstruct( Stage stage )
    {
        MedMainStage = stage;
        MedMainStage.setTitle("Cosmo Industries");

        VBox root = createMainVBox();

        MedMainStage.setScene(new Scene(root, 875, 580));
        MedMainStage.resizableProperty().set(false);
        MedMainStage.show();
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
        Button logOut = new Button("Log Out");
        logOut.setPrefSize(100, 20);

        logOut.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                MedMainStage.close();
                LoginGUI test5 = new LoginGUI();
                test5.start(MedMainStage);
            }
        });
        // logo image size
        ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
        logo.setFitWidth(400);
        logo.setFitHeight(49);

        // set the image left and right
        logoAndLogin.setLeft(logo);
        logoAndLogin.setRight(logOut);

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
     * 
     * @return
     */
    private BorderPane createPreviewPane()
    {
        // create border pane
        BorderPane previewPane = new BorderPane();

        // create the search bar in the preview pane
        HBox searchBar = createSearchBar();

        // create picture box for left side of preview pane
        VBox pictureBox = new VBox();
        // default preview picture
        ImageView previewPicture = new ImageView(new Image(
                "images/defaultPicture.png"));

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
        BorderPane.setMargin(searchBar, new Insets(10, 0, 10, 0));
        BorderPane.setMargin(pictureBox, new Insets(0, 0, 0, 10));
        previewPane.setTop(searchBar);
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        return previewPane;
    }

    /**
     * 
     * Purpose:Create the search bar
     * 
     * @return HBox search bar
     */
    private HBox createSearchBar()
    {
        // create search bar
        HBox searchBar = new HBox();
        ComboBox<String> searchComboBox = new ComboBox<String>();
        searchComboBox.getItems().addAll("Name", "Address", "Allergy",
                "CosmoID");

        // set width
        searchComboBox.setStyle("-fx-pref-width: 150;");
        searchComboBox.setPromptText(("Search By"));

        // create search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-pref-width: 245; -fx-pref-height: 26;");

        // search button
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(110, 20);

        // set margins
        HBox.setMargin(searchComboBox, new Insets(0, 5, 0, 10));
        HBox.setMargin(searchField, new Insets(0, 5, 0, 5));
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 5));
        searchBar.getChildren().addAll(searchComboBox, searchField,
                searchButton);

        return searchBar;
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
        ListView<String> noteTitles = new ListView<String>();
        // create list of notes
        // TODO make this automatically pull from the database of notes
        ObservableList<String> notes = FXCollections.observableArrayList(
                "Note 1", "Note 2", "Note 3", "Note 4", "Note 5", "Note 6",
                "Note 7", "Note 8", "Note 9", "Note 10", "Note 11");

        // set notes list to listview
        noteTitles.setItems(notes);
        noteTitles.setMinWidth(170);
        noteTitles.setMaxWidth(170);

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

        hbox.setPadding(new Insets(49, 0, 0, 0));
        hbox.getChildren().addAll(noteTitles, noteDisplayPane);

        return hbox;
    }

    /**
     * 
     * Purpose:Create HBox with table
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private HBox createHBoxTable()
    {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        // TableView instance to hold User records
        TableView<Participant> table = new TableView<Participant>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Instantiation of all the table column headings (With proper
        // formatting)

        // cosmoID Col
        TableColumn<Participant, String> cosmoIDCol = new TableColumn<Participant, String>(
                "Cosmo ID");
        cosmoIDCol.setMinWidth(50);
        cosmoIDCol.setResizable(false);

        // Participant Col
        TableColumn<Participant, String> participantNameCol = new TableColumn<Participant, String>(
                "Participant");
        participantNameCol.setMinWidth(175);
        participantNameCol.setResizable(false);

        // Home Address Col
        TableColumn<Participant, String> addressCol = new TableColumn<Participant, String>(
                "Home Address");
        addressCol.setMinWidth(200);
        addressCol.setResizable(false);

        // Emergency Contact name
        TableColumn<Participant, String> emergencyContactNameCol = new TableColumn<Participant, String>(
                "Emergency Contact Name");
        emergencyContactNameCol.setMinWidth(180);
        emergencyContactNameCol.setResizable(false);

        // Emergency Phone Col
        TableColumn<Participant, String> emergencyContactPhoneCol = new TableColumn<Participant, String>(
                "Emergency Phone");
        emergencyContactPhoneCol.setMinWidth(115);
        emergencyContactPhoneCol.setResizable(false);

        // Last Updated col
        TableColumn<Participant, String> lastUpdatedCol = new TableColumn<Participant, String>(
                "Last Updated");
        lastUpdatedCol.setMinWidth(135);
        lastUpdatedCol.setResizable(false);

        // Appending column headers to the table for display
        table.getColumns().addAll(cosmoIDCol, participantNameCol, addressCol,
                emergencyContactNameCol, emergencyContactPhoneCol,
                lastUpdatedCol);

        // table columns not draggable to reorder it
        table.getColumns().addListener(new ListChangeListener()
        {
            @Override
            public void onChanged( Change change )
            {
                change.next();
                if ( change.wasReplaced() )
                {
                    table.getColumns().clear();
                    table.getColumns().addAll(cosmoIDCol, participantNameCol,
                            addressCol, emergencyContactNameCol,
                            emergencyContactPhoneCol, lastUpdatedCol);
                }
            }
        });

        // TODO example, pls remove

        Date updated = new Date();

        Participant test = new Participant(0, "John Doe", "301 Highwater Pl",
                "Jane Doe", "123-456-7890", updated);
        Participant test2 = new Participant(1, "Jane Doe", "302 Highwater Pl",
                "John Doe", "123-456-7890", updated);

        // add list to columns
        ObservableList<Participant> participantList = FXCollections
                .observableArrayList(test, test2);
        // add data to columns
        cosmoIDCol.setCellValueFactory(new PropertyValueFactory<>("cosmoID"));
        participantNameCol.setCellValueFactory(new PropertyValueFactory<>(
                "participantName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>(
                "participantAddress"));
        emergencyContactNameCol.setCellValueFactory(new PropertyValueFactory<>(
                "emergencyContactName"));
        emergencyContactPhoneCol
                .setCellValueFactory(new PropertyValueFactory<>(
                        "emergencyContactPhone"));
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>(
                "informationLastUpdated"));

        // set things to participants
        table.setItems(participantList);

        hbox.getChildren().addAll(table);

        return hbox;
    }

    /**
     * 
     * Purpose: Create Main VBox
     * 
     * @return VBox main VBox
     */
    public VBox createMainVBox()
    {
        VBox vbox = new VBox();

        // header HBox
        BorderPane header = createHBoxHeader();
        // tab pane
        TabPane tabs = createTabs();
        // preview notes
        HBox previewNotes = createHBoxPreviewNotes();
        // table hbox
        HBox table = createHBoxTable();
        // add everthing to vbox
        vbox.getChildren().addAll(header, tabs, previewNotes, table);

        return vbox;
    }

}
