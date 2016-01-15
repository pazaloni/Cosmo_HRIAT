import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NotePopUp
{
	Stage stage;
	BorderPane root;
	Scene scene;
	
	
	public NotePopUp(Participant participant, String creator, Stage stage)
	{
		this.stage = stage;
		this.stage.setTitle("new Note");
		//root = new BorderPane();
		scene = new Scene(createNotePopUp(participant, creator));
		this.stage.setScene(scene);
		
		//root = new BorderPane();
				
	}
	
	protected GridPane createNotePopUp(Participant participant, String creator) 
	{

		GridPane grid = new GridPane();

		// warning label
		Label lblWarning = new Label();
		lblWarning.setTextFill(Color.FIREBRICK);

		// text field labels
		Label messageLbl = new Label("Message");
		

		// the text fields
		TextArea messageTxt = new TextArea();
		

		// add the form to the grid
		grid.add(messageLbl, 0, 1);
		

		grid.add(lblWarning, 1, 0);
		grid.add(messageTxt, 1, 1);
		

		// setPadding of the grid
		grid.setPadding(new Insets(10, 10, 0, 10));

		grid.setHgap(10);

		grid.setVgap(10);

		// Adding participant event handler
		Button submitNoteBtn = new Button("Submit");
		submitNoteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//call confirmation
				
				
				
				
				// call create participant on medical administrator with the
				// text passed in
				Note newNote = new Note(participant, messageTxt.getText(), creator);
				String result = newNote.addNote();

				// if no error message is received then close this window and
				// refresh the table
				if (result.equals("")) {
					stage.close();
				}
				// if there is an error message, display it
				else {
					lblWarning.setTextFill(Color.FIREBRICK);
					lblWarning.setText(result);
					if (messageTxt.getText().isEmpty()) 
					{
						lblWarning.setText("Cannot submit a empty description.");
					}
				}
			}
		});

		

		// Add the buttons to the grid
		HBox buttonsHbox = new HBox();
		buttonsHbox.getChildren().addAll(submitNoteBtn);
		buttonsHbox.setAlignment(Pos.CENTER);
		grid.add(buttonsHbox, 1, 9);

		return grid;
	}
}
