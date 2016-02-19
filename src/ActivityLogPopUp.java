import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * Purpose: Displayable popup with all the activity log information
 *
 * @author Team CIMP
 * @version 1.0
 */

public class ActivityLogPopUp
{

    private BorderPane root;

    public static final int SPACING = 10;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 480;

    private Stage parentStage;

    public static final String TITLE = "Activity Log";

    private ObservableList<String> searchOptions = FXCollections
            .observableArrayList();
    private ComboBox<String> searchCriteriaBox = new ComboBox<String>();

    private ActivityLogTableViewController logController;

    private Button searchButton;
    private TextField searchCriteria;

    /**
     * 
     * Constructor for the ActivityLogPopUp class.
     */
    public ActivityLogPopUp(Stage parentStage)
    {
        this.parentStage = parentStage;
    }
   
    /**
     * 
     * Purpose: Create the acivity log with all the details needed including the
     * controls
     * 
     * @return a Pane that will look like an activity log
     */
    private BorderPane createActivityLog()
    {
        HBox controls = createControls();
        HBox tableBox = createTableBox();
        root.setTop(controls);
        root.setCenter(tableBox);
        root.setPadding(new Insets(10, 10, 10, 10));
        return root;
    }

    /**
     * 
     * Purpose: Create the HBox containing The controls
     * 
     * @return an hbox with a serchbar, a criteria to search by, and a search
     *         button
     */
    private HBox createControls()
    {
        HBox box = new HBox();

        searchButton = new Button("Search");
        searchCriteria = new TextField();

        searchOptions.addAll("Event", "Who", "When");
        searchCriteriaBox.setItems(searchOptions);

        // Setting the default value to be the first one
        searchCriteriaBox.setValue(searchOptions.get(0));

        
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(SPACING);
        box.getChildren().addAll(searchCriteriaBox, searchCriteria,
                searchButton);

        return box;
    }

    /**
     * 
     * Purpose: Create the table box at the bottom containing the tableview
     * 
     * @return
     */

    private HBox createTableBox()
    {
        @SuppressWarnings("rawtypes")
        TableView table = logController.activityLogTable;

        HBox box = new HBox();

     //   table.setMaxWidth(WIDTH - 50);
        table.setMinWidth(WIDTH - 50);
    //    table.setPrefWidth(WIDTH - 50);

        box.setPadding(new Insets(10, 10, 10, 10));
        box.getChildren().addAll(table);
        return box;
    }

    /**
     * 
     * Purpose: Assign handlers on all items on this specific gui
     */
    private void assignHandlers()
    {
        //Handler for the search button 
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            // TODO add the event to be handled here
            @Override
            public void handle( ActionEvent arg0 )
            {
                
            }
        });

        //Handler for the searchbox
        searchCriteria.setOnAction(new EventHandler<ActionEvent>()
        {
            // TODO add the event to be handled here
            @Override
            public void handle( ActionEvent arg0 )
            {

            }
        });
    }
 
    
    /**
     * 
     * Purpose: show the activity log when this method is being called.
     */
    public void showActivityLog()
    {
        root = new BorderPane();
        logController = new ActivityLogTableViewController();

        root = createActivityLog();
        //Assign handlers on the various controls
        assignHandlers();

        Stage localStage = new Stage();
        // local stage configuration
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setResizable(true);
        localStage.setScene(new Scene(root));
        localStage.setWidth(WIDTH);
        localStage.setHeight(HEIGHT);
        localStage.setTitle(TITLE);
        localStage.show();
    }
}