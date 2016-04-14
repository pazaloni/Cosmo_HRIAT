package gui;

import core.ProgressNotes;
import core.QueryResult;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Purpose: Display the window to add and manage progress notes for participants
 *
 * @author CIMP
 * @version 1.0
 */
public class ManageProgressNoteGUI
{

    private Stage parentStage;

    private Label lblMessage;

    private GridPane mainPane;

    private DatePicker dateTime;
    private TextField participantName;
    private TextField num;

    private Label lblDateTime;
    private Label lblParticipantName;
    private Label lblNum;

    private Button btnAdd, btnCancel;

    public ManageProgressNoteGUI(Stage parentStage)
    {
        this.parentStage = parentStage;

        this.dateTime = new DatePicker();
        this.participantName = new TextField();
        this.num = new TextField();

        this.lblDateTime = new Label("Date/Time: ");
        this.lblParticipantName = new Label("Name: ");
        this.lblNum = new Label("No.: ");

        this.btnAdd = new Button("Add");
        this.btnCancel = new Button("Cancel");

        mainPane = new GridPane();
    }

    /**
     * Purpose: show the window for the addition of a new progressNote
     * 
     * @param cosmoID
     *            the participant that will be getting the new progresNote
     */
    public void showAddProgressNote( String cosmoID )

    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblDateTime, 0, 1);
        mainPane.add(lblParticipantName, 0, 2);
        mainPane.add(lblNum, 0, 3);

        mainPane.add(dateTime, 1, 1);
        mainPane.add(participantName, 1, 2);
        mainPane.add(num, 1, 3);

        dateTime.setMaxWidth(250);
        participantName.setMaxWidth(250);
        num.setMaxWidth(250);

        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(15);
        mainPane.setVgap(15);

        mainPane.setPadding(new Insets(10, 10, 10, 10));

        btnAdd.setOnAction(event -> {
            if ( dateTime.getValue() != null )
            {
                String result = ProgressNotes.createProgressNote(
                        new ProgressNotes(dateTime.getValue(), participantName
                                .getText(), num.getText()), cosmoID);
                if ( result.equals("") )
                {
                    localStage.close();
                }
                else
                {
                    lblMessage.setText(result);
                }
            }
            else
            {
                lblMessage.setText("Pick a date from the datepicker.");
            }
        });
        btnCancel.setOnAction(event -> {
            localStage.close();
        });
        Scene scene = new Scene(mainPane, 400, 300);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add a progress note");
        localStage.showAndWait();

    }

    /**
     * Purpose: Edit a progressNote for a specified participant
     * 
     * @param progressNote
     *            The progress note to be edited
     * @param cosmoID
     *            the participant that will have the progress note edited
     */
    public void showUpdateProgressNote( ProgressNotes progressNote,
            String cosmoID )
    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);

        mainPane.add(lblMessage, 0, 0, 2, 1);

        mainPane.add(lblDateTime, 0, 1);
        mainPane.add(lblParticipantName, 0, 2);
        mainPane.add(lblNum, 0, 3);

        mainPane.add(dateTime, 1, 1);
        mainPane.add(participantName, 1, 2);
        mainPane.add(num, 1, 3);

        dateTime.setMaxWidth(250);
        participantName.setMaxWidth(250);
        num.setMaxWidth(250);

        dateTime.setValue(progressNote.getLocalDateTime());
        participantName.setText(progressNote.getName().get());
        num.setText(progressNote.getNum().get());

        HBox controls = new HBox();

        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 5, 2, 1);

        mainPane.setHgap(15);
        mainPane.setVgap(15);

        mainPane.setPadding(new Insets(10, 10, 10, 10));
        btnAdd.setText("Update");
        btnAdd.setOnAction(event -> {
            ProgressNotes newProgressNote = new ProgressNotes(dateTime
                    .getValue(), participantName.getText(), num.getText());

            String result = ProgressNotes.updateProgressNote(newProgressNote,
                    progressNote, cosmoID);
            if ( result.equals("") )
            {
                localStage.close();
            }
            else
            {
                lblMessage.setText(result);
            }
        });
        btnCancel.setOnAction(event -> {
            localStage.close();
        });

        Scene scene = new Scene(mainPane, 400, 300);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Edit a Progress Note entry");
        localStage.showAndWait();
    }
}
