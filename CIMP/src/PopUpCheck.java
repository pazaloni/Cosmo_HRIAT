import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PopUpCheck
{
    // Label that will hold the message to be displayed
    Label checkMsgLbl;
    // the main container for the page
    BorderPane root;
    // boolean value that holds whether the command was succesful or not
    public boolean result;
    // the stage
    Stage stage;
    // the scene
    Scene scene;
    // instance of the yes button
    Button yesBtn;
    // instance of the no button
    Button noBtn;

    /**
     * Purpose: The main constructor for the pop up check
     * 
     * @param message   The passed in message that will be displayed on the GUI
     * @param stage     The passed in stage
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public PopUpCheck(String message, Stage stage)
    {
        this.stage = stage;
        // set the title of the window
        this.stage.setTitle("Confirm Action");
        // the border pane that will hold the contents of the page
        root = new BorderPane();
        // set the root to the scene
        scene = new Scene(root);
        this.stage.setScene(scene);

        root = new BorderPane();

        // create and set the label to display the message passed into it.
        checkMsgLbl = new Label();
        checkMsgLbl.setText(message);

        // the yes and no buttons are set
        yesBtn = new Button("Yes");
        noBtn = new Button("No");

        // set the action for the yes button
        yesBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent event )
            {
                // set the result to true
                result = true;
                // get a handle on the window
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                // close the window
                stage.close();
            }

        });

        // set the action for no button
        noBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent event )
            {
                // set the result to false
                result = false;
                // get a handle to the window
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                // close the window
                stage.close();
            }

        });

        // set all the elements to the page
        root.setCenter(checkMsgLbl);
        HBox bottom = new HBox();
        bottom.getChildren().add(yesBtn);
        bottom.getChildren().add(noBtn);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(25, 25, 25, 25));
        bottom.setSpacing(25);
        root.setBottom(bottom);
    }

    /**
     * Purpose: this will return the result the set on the button click
     * 
     * @return a boolean value saying whether the deletion was confirmed
     * 
     * @author  Breanna Wilson cst215
     *          Steven Palchinski cst209
     */
    public boolean runCheck()
    {
        return result;
    }

}
