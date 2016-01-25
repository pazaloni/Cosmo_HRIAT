import java.util.Date;

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
	
	/**
	 * Purpose:	This will create the stage for the pop up that will be used to 
	 * 			create a note.
	 * 
	 * @param participant	The participant that the note is being added to
	 * @param creator		The username of the currently logged in medical staff
	 * @param stage			The stage that the pop up will be generated in
	 * 
	 * @author  Niklaas Neijmeijer cst207
     *          Steven Palchinski cst209
	 */
	public NotePopUp(int participant, String creator, Stage stage)
	{
		this.stage = stage;
		this.stage.setTitle("new Note");
		this.root.setBottom(createNotePopUp(participant, creator));
	}
	
	/**
	 * Purpose: This will create the visual layout of the pop up window.
	 * 
	 * @param participant	The cosmoID of the current participant
	 * @param creator		The string username of the logged in medical staff
	 * @return	the gridpane that will be used in the pop up window
	 * 
	 * @author  Niklaas Neijmeijer cst207
     *          Steven Palchinski cst209
	 */
	protected GridPane createNotePopUp(int participant, String creator) 
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
		
		//Add the error text to the grid
		grid.add(lblWarning, 1, 0);
		
		//Add the description box to the grid
		grid.add(messageTxt, 1, 1);
		

		// setPadding of the grid
		grid.setPadding(new Insets(10, 10, 0, 10));

		grid.setHgap(10);

		grid.setVgap(10);

		// Adding participant event handler
		Button submitNoteBtn = new Button("Submit");
		submitNoteBtn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				//call confirmation
        		PopUpMessage messageBox = new PopUpMessage("Is this the note "
        				+ "you wish to submit?  " + messageTxt.getText(), stage);
            	
            	scene = new Scene(messageBox.root, 300, 75);
            	stage.setScene(scene);
            	stage.showAndWait();
            	
				// call create participant on medical administrator with the
				// text passed in
				Note newNote = new Note( participant, messageTxt.getText(), creator, new Date(), false, false);
				String result = newNote.addNote();

				// if no error message is received then close this window and
				// refresh the table
				if (result.equals("")) 
				{
					stage.close();
				}
				// if there is an error message, display it
				else {
					lblWarning.setTextFill(Color.FIREBRICK);
					lblWarning.setText(result);
				}
			}
		});

		

		//Add the buttons to the grid
		HBox buttonsHbox = new HBox();
		buttonsHbox.getChildren().addAll(submitNoteBtn);
		buttonsHbox.setAlignment(Pos.CENTER);
		grid.add(buttonsHbox, 1, 9);

		return grid;
	}
}
