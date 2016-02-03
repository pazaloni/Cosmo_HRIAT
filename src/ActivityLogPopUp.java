import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    private Stage parentStage;

    /**
     * 
     * Constructor for the ActivityLogPopUp class.
     */
    public ActivityLogPopUp(Stage parentStage)
    {
        this.parentStage = parentStage;
        root = new BorderPane();
        
        
    }
    
    
    public BorderPane createActivityLog()
    {
        HBox controls = new HBox();        
        HBox tableBox = new HBox();
        
        
        
        root.setTop(controls);
        root.setCenter(tableBox);
        return root;
    }
    
    
    

}
