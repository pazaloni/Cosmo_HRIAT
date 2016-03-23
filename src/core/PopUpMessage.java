package core;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PopUpMessage
{
    // label containing the message
    private Label messageLbl;
    // button to confirm the message
    private Button confirmBtn;
    public BorderPane root = new BorderPane();
    // Scene scene = new Scene(root);
    public Stage stage;

    /**
     * This will create the PopUpMessage. This is a pop-up box that just
     * displays a message, and contains a confirmation button to close the
     * window.
     * 
     * @param message
     *            : The message to be displayed
     * @param stage
     *            : the stage to display in.
     */
    public PopUpMessage(String message, Stage stage)
    {
        this.messageLbl = new Label(message);
        this.stage = stage;
        this.stage.setScene(new Scene(root));

        this.confirmBtn = new Button();

        this.confirmBtn.setText("OK");

        this.root.setCenter(messageLbl);

        HBox bottom = new HBox();

        bottom.getChildren().add(confirmBtn);
        bottom.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        
       
        this.root.setBottom(bottom);

        this.confirmBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                stage.close();

            }

        });

    }
}
