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
	//
	Label checkMsgLbl;
	BorderPane root;
	public boolean result;
	Stage stage;
	Scene scene;
	Button yesBtn;
	Button noBtn;


	public PopUpCheck(String message, Stage stage)
	{
		this.stage = stage;
		this.stage.setTitle("Confirm Action");
		root = new BorderPane();
		scene = new Scene(root);
		this.stage.setScene(scene);
		
		checkMsgLbl = new Label();
		checkMsgLbl.setText(message);
		
		root = new BorderPane();
		
		yesBtn = new Button("Yes");
		noBtn = new Button("No");
		
		
		yesBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				result = true;
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
			 
		});
		
		
		noBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				result = false;
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
			
		});
		
		root.setCenter(checkMsgLbl);
		HBox bottom = new HBox();
		bottom.getChildren().add(yesBtn);
		bottom.getChildren().add(noBtn);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(25,25,25,25));
		bottom.setSpacing(25);
		root.setBottom(bottom);
	}
	
	public boolean runCheck()
	{
		return result;
	}
	
	
}
