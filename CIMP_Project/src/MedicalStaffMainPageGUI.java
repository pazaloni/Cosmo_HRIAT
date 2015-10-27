
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MedicalStaffMainPageGUI extends Application
{
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Cosmo Industries");
        
        VBox root = createMainVBox();

        stage.setScene(new Scene(root, 875 , 580));
        stage.resizableProperty().set(false);
        stage.show();  
    }
    
    
    public BorderPane createHBoxHeader() {
        BorderPane logoAndLogin = new BorderPane();
        logoAndLogin.setPadding(new Insets(15, 12, 15, 12));
       // logoAndLogin.setSpacing(10);
        logoAndLogin.setStyle("-fx-background-color: #FFFFFF;");

        Button buttonCurrent = new Button("Log Out");
        buttonCurrent.setPrefSize(100, 20);
        
        ImageView logo = new ImageView(new Image(".\\CosmoIconLong[1].png"));
        logo.setFitWidth(400);
        logo.setFitHeight(49);
        
        logoAndLogin.setLeft(logo);
        logoAndLogin.setRight(buttonCurrent);
        //logoAndLogin.getChildren().addAll(logo,buttonCurrent);

        return logoAndLogin;
    }
    
    public TabPane createHBoxTabs() {

        TabPane tabPane = new TabPane();

        //Create tabs
        Tab participants = new Tab();
        Tab forms = new Tab();
        Tab stats = new Tab();
        
        //set text for tabs
        participants.setText("Participants");
        forms.setText("Forms");
        stats.setText("Stats");
        
        //set tabs to not be closable
        forms.closableProperty().set(false);
        participants.closableProperty().set(false);
        stats.closableProperty().set(false);
        
        
        tabPane.setTabMinWidth(175);
       // tabPane.setTranslateY(-10);
        tabPane.getTabs().addAll(participants, forms, stats);
        tabPane.setStyle("-fx-background-color: #FF0000;");
        
        
        
        
       // HBox.setMargin(tabBox, new Insets(20,20,20,20));
        
        return tabPane;
    }
    
    public HBox createHBoxPreviewNotes() {
        HBox hbox = new HBox();
       // hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        BorderPane previewPane = createPreviewPane();
        HBox noteBox = createNoteBox();

        hbox.getChildren().addAll(previewPane, noteBox);
        //hbox.setTranslateY(20);

        return hbox;
    }
    
    public BorderPane createPreviewPane()
    {
        BorderPane previewPane = new BorderPane();
        
        HBox searchBar = createSearchBar();
        
        //create picture box for left side of preview pane
        VBox pictureBox = new VBox();
        ImageView previewPicture = new ImageView(new Image(".\\defaultPicture.png"));
        //previewPicture.setStyle("-fx-border-color: #000000 ; -fx-border-width: 5;");
        
        
        Button detailsButton = new Button("View Details");

        //set margins
        VBox.setMargin(previewPicture, new Insets(10,10,10,10));
        
        //add picture and button to picture box
        pictureBox.getChildren().addAll(previewPicture, detailsButton);
        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setStyle("-fx-background-color: #FFFFFF;");
        pictureBox.setAlignment(Pos.TOP_CENTER);
        
        GridPane basicInfoPane = new GridPane();
        basicInfoPane.setStyle("-fx-background-color: #FFFFFF;");
        basicInfoPane.setPadding(new Insets(10,10,0,10));
        
        //set basic labels
        Label cosmoIDLabel = new Label("CosmoID:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name: ");
        Label seizureLabel = new Label("Seizures: ");
        Label allergyLabel = new Label("Allergies: ");
        
        //set label margins
        cosmoIDLabel.setPadding(new Insets(5,5,5,5));
        firstNameLabel.setPadding(new Insets(5,5,5,5));
        lastNameLabel.setPadding(new Insets(5,5,5,5));
        seizureLabel.setPadding(new Insets(5,5,25,5));
        allergyLabel.setPadding(new Insets(5,5,50,5));
        
        //set the participant text fields
        TextField cosmoIDText = new TextField("test");
        TextField firstNameText = new TextField("test2");
        TextField lastNameText = new TextField("test3");
        TextArea seizureText = new TextArea("test4");
        TextArea allergyText = new TextArea("test5");
        
        //set text dimensions
        cosmoIDText.setMaxWidth(150);
        cosmoIDText.setMinWidth(150);
        cosmoIDText.editableProperty().set(false);
        
        firstNameText.setMaxWidth(150);
        firstNameText.setMinWidth(150);
        firstNameText.editableProperty().set(false);
        
        lastNameText.setMaxWidth(150);
        lastNameText.setMinWidth(150);
        lastNameText.editableProperty().set(false);
        
        seizureText.setMaxWidth(175);
        seizureText.setMinWidth(175);
        seizureText.setMaxHeight(40);
        seizureText.setMinHeight(40);
        seizureText.editableProperty().set(false);
        
        allergyText.setMaxWidth(175);
        allergyText.setMinWidth(175);
        allergyText.setMaxHeight(80);
        allergyText.setMinHeight(80);
        allergyText.editableProperty().set(false);
        
        //add all labels and text boxes to the gridpane
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
        
        BorderPane.setMargin(searchBar, new Insets(10,0,10,0));
        BorderPane.setMargin(pictureBox, new Insets(0,0,0,10));
        previewPane.setTop(searchBar);
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);
               
        return previewPane;
    }

    private HBox createSearchBar()
    {
      //create search bar
        HBox searchBar = new HBox();
        ComboBox<String> searchComboBox = new ComboBox<String>();
        searchComboBox.getItems().addAll(
            "Highest",
            "High",
            "Normal",
            "Low",
            "Lowest" 
        );   

        searchComboBox.setStyle("-fx-pref-width: 150;");
        searchComboBox.setPromptText(("Search By"));
        
        //create search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-pref-width: 250;");
        
       HBox.setMargin(searchComboBox, new Insets(0,10,0,10)); 
        HBox.setMargin(searchField, new Insets(0,0,4,10));
        searchBar.getChildren().addAll(searchComboBox, searchField);
        
        return searchBar;
    }

    public HBox createHBoxTable() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("Table Goes Here");
        buttonCurrent.setPrefSize(100, 20);

        hbox.getChildren().addAll(buttonCurrent);

        return hbox;
    }

    
    public VBox createMainVBox() {
        VBox vbox = new VBox();

        BorderPane header = createHBoxHeader();
        TabPane tabs = createHBoxTabs();
        HBox previewNotes = createHBoxPreviewNotes();
        HBox table = createHBoxTable();
        vbox.getChildren().addAll(header, tabs,previewNotes, table);

        return vbox;
    }
    
    public HBox createNoteBox()
    {
        HBox hbox = new HBox();
        ListView noteTitles = new ListView();
        ObservableList<String> notes = FXCollections.observableArrayList("test", "test","test","test","test",
                "test","test","test","test","test");
        noteTitles.setItems(notes);
        hbox.setPadding(new Insets(49,0,0,0));
        hbox.getChildren().add(noteTitles);
   
        
        return hbox;
        
    }
    
}
