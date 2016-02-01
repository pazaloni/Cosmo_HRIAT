import java.util.Date;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NotePopUp {
	static Stage stage;
	BorderPane root;
	protected static Stage confirmNoteStage;
	static Scene scene;
	static Label lblWarning;
	static String result = "Not yet submitted";

	/**
	 * Purpose: This will create the stage for the pop up that will be used to
	 * create a note.
	 * 
	 * @param participant
	 *            The participant that the note is being added to
	 * @param creator
	 *            The username of the currently logged in medical staff
	 * @param stage
	 *            The stage that the pop up will be generated in
	 * 
	 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
	 */
	public NotePopUp(int participant, String creator, Stage stage) {
		this.stage = stage;
		this.stage.setTitle("new Note");
		this.root.setBottom(createNotePopUp(participant, creator, stage));
	}

	/**
	 * Purpose: This will create the visual layout of the pop up window.
	 * 
	 * @param participant
	 *            The cosmoID of the current participant
	 * @param creator
	 *            The string username of the logged in medical staff
	 * @return the gridpane that will be used in the pop up window
	 * 
	 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
	 */
	protected static GridPane createNotePopUp(int participant, String creator,
			Stage parent) {

		GridPane grid = new GridPane();

		// warning label
		lblWarning = new Label();
		lblWarning.setTextFill(Color.FIREBRICK);

		// the text fields
		TextArea messageTxt = new TextArea();

		messageTxt.setWrapText(true);
		;
		// add the form to the grid

		// Add the error text to the grid
		grid.add(lblWarning, 0, 0);

		// Add the description box to the grid
		grid.add(messageTxt, 0, 1);

		grid.setHgap(10);

		grid.setVgap(10);

		// Adding participant event handler
		Button submitNoteBtn = new Button("Submit");
		submitNoteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!(messageTxt.getText().length() == 0)) {

					confirmNoteStage = new Stage();
					confirmNoteStage.setTitle("Create Participant");

					confirmNoteStage.setScene(new Scene(confirmSelect(
							"Is this the note " + "you wish to submit? \n\n ",
							messageTxt.getText(), participant, creator)));
					confirmNoteStage.initModality(Modality.APPLICATION_MODAL);
					confirmNoteStage.initOwner(stage);
					confirmNoteStage.setResizable(false);
					confirmNoteStage.showAndWait();
					// call create participant on medical administrator with the
					// text passed in
					if (result.equals("")) {
						parent.close();
					}
				} else {
					lblWarning.setTextFill(Color.FIREBRICK);
					lblWarning.setText("You need a description for your note.");
				}
			}
		});

		// Add the buttons to the grid
		HBox buttonsHbox = new HBox();
		buttonsHbox.getChildren().addAll(submitNoteBtn);
		buttonsHbox.setAlignment(Pos.CENTER);
		grid.add(buttonsHbox, 0, 2);

		// setPadding of the grid
		grid.setPadding(new Insets(10, 10, 10, 10));

		return grid;
	}

	public static VBox confirmSelect(String message, String contents,
			int participant, String creator) {
		VBox confirm = new VBox();
		// create and set the label to display the message passed into it.
		Label checkMsgLbl = new Label();
		checkMsgLbl.setText(message + contents);
		checkMsgLbl.setWrapText(true);
		// the yes and no buttons are set
		Button yesBtn = new Button("Yes");
		Button noBtn = new Button("No");
		// set the action for the yes button
		yesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Note newNote = new Note(participant, contents, creator,
						new Date(), false, false);
				result = newNote.addNote();

				// if no error message is received then close this
				// window and
				// refresh the table
				if (result.equals("")) {
					confirmNoteStage.close();
				} else {
					lblWarning.setTextFill(Color.FIREBRICK);
					lblWarning.setText(result);
				}

			}

		});

		// set the action for no button
		noBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// close the window
				confirmNoteStage.close();
			}

		});

		// set all the elements to the page
		HBox bottom = new HBox();
		bottom.getChildren().add(yesBtn);
		bottom.getChildren().add(noBtn);
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(25);
		bottom.setPadding(new Insets(25, 0, 0, 0));
		confirm.getChildren().add(checkMsgLbl);
		confirm.getChildren().add(bottom);
		confirm.setPadding(new Insets(25, 25, 25, 25));
		confirm.setMaxWidth(350);
		return confirm;
	}
}
