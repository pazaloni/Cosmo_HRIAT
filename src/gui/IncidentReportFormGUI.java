package gui;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.StaffAccount;

public class IncidentReportFormGUI
{
    private static final String FORM_TITLE = "Incident Reporting Form";

    private ScrollPane mainPane;
    private VBox mainBox;

    private Stage parentStage;

    private TextField personInjured;
    private TextField otherWorkArea;

    private RadioButton staffInjured, participantInjured, otherInjured, am, pm;
    private RadioButton LSTD, programs, pathWays, frontOffice, WRD, contracts,
            personalSupports, lifeEnrichment, other;

    private List<CheckBox> typesOfInjuries;

    public IncidentReportFormGUI(Stage parentStage, StaffAccount loggedInUser)
    {
        
        this.parentStage = parentStage;
    }

    public void showIncidentReportForm()
    {
        Label formTitle = new Label(FORM_TITLE);
        formTitle.setFont(new Font(22));
        mainBox = new VBox();
        mainBox.getChildren().addAll(formTitle, createHeader(),
                createRegisteredWorkArea());
        mainPane = new ScrollPane();
        mainPane.setContent(mainBox);

        parentStage.setScene(new Scene(mainPane));
        parentStage.initOwner(parentStage);
        parentStage.initModality(Modality.APPLICATION_MODAL);

    }

    /**
     * 
     * Purpose: Create the header info containing person injured.
     * 
     * return box HBox containing required nodes
     */
    private HBox createHeader()
    {
        HBox box = new HBox();

        staffInjured = new RadioButton("Staff");
        participantInjured = new RadioButton("Participant Injured");
        otherInjured = new RadioButton("Other");

        personInjured = new TextField();

        am = new RadioButton("am");
        pm = new RadioButton("pm");

        Label lblpersonInjured = new Label("Person Injured: ");

        box.getChildren().addAll(lblpersonInjured, personInjured, staffInjured,
                participantInjured, otherInjured);

        return box;
    }

    /**
     * 
     * Purpose: Create a grid-pane with the check-boxes for the different work
     * areas
     * 
     * @return gridpane with checkboxes
     */
    private GridPane createRegisteredWorkArea()
    {
        Label registeredWorkArea = new Label("Registered Work Area: ");
        registeredWorkArea.setFont(new Font(20));
        GridPane grid = new GridPane();

        ToggleGroup group = new ToggleGroup();

        otherWorkArea = new TextField();
        otherWorkArea.setDisable(true);

        LSTD = new RadioButton("LSTD");
        programs = new RadioButton("Programs");
        pathWays = new RadioButton("Pathways");
        frontOffice = new RadioButton("Front Office");
        WRD = new RadioButton("WRD");
        contracts = new RadioButton("Contracts");
        personalSupports = new RadioButton("Personal Supports");
        lifeEnrichment = new RadioButton("Life Enrichment");
        other = new RadioButton("Other");

        group.getToggles().addAll(LSTD, programs, pathWays, frontOffice, WRD,
                contracts, personalSupports, lifeEnrichment, other);

        grid.add(LSTD, 0, 0);
        grid.add(programs, 0, 1);
        grid.add(pathWays, 0, 2);
        grid.add(frontOffice, 0, 3);
        grid.add(WRD, 1, 0);
        grid.add(contracts, 1, 1);
        grid.add(personalSupports, 1, 2);
        grid.add(lifeEnrichment, 1, 3);
        grid.add(other, 2, 0);
        grid.add(otherWorkArea, 2, 1);

        lifeEnrichment.setOnAction(event -> {
            if ( lifeEnrichment.isSelected() )
            {
                otherWorkArea.setDisable(false);
            }
            else
            {
                otherWorkArea.setDisable(true);
            }
        });
        return grid;
    }
}
