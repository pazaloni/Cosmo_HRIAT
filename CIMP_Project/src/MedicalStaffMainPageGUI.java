
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        //logoAndLogin.setSpacing(10);
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
    
    public HBox createHBoxTabs() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("Tabs Go Here");
        buttonCurrent.setPrefSize(100, 20);

        hbox.getChildren().addAll(buttonCurrent);

        return hbox;
    }
    
    public HBox createHBoxPreviewNotes() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("PreviewNotes Here");
        buttonCurrent.setPrefSize(100, 20);

        hbox.getChildren().addAll(buttonCurrent);

        return hbox;
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
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        BorderPane header = createHBoxHeader();
        HBox tabs = createHBoxTabs();
        HBox previewNotes = createHBoxPreviewNotes();
        HBox table = createHBoxTable();
        vbox.getChildren().addAll(header, tabs, previewNotes, table);

        return vbox;
    }
    
}
