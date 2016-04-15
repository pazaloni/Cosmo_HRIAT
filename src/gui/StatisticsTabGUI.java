package gui;

import helpers.DatabaseHelper;
import helpers.HealthStatusInformationHelper;
import helpers.StatisticsHelper;

import java.awt.Dialog.ModalExclusionType;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import controllers.AllergiesTableViewController;
import controllers.MedicalConditionsTableViewController;
import controllers.ProgressNotesTableViewController;
import controllers.MedicationsTableViewController;
import core.Allergies;
import core.MedicalAdministrator;
import core.Medication;
import core.PopUpCheck;
import core.PopUpMessage;
import core.QueryResult;
import core.SavedQuery;
import core.StaffAccount;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * Purpose: Represents the statistics tab GUI information
 *
 * @author CIMP
 * @version 1.0
 */
public class StatisticsTabGUI
{
    public static final int SPACING = 10;

    private HBox mainBox;

    private Tab parentTab;

    private Stage parentStage;

    Label queryParticipantsLbl;

    VBox savedQueryBox;

    ListView<String> savedQueryList;

    ListView<String> fieldList;

    HealthStatusInformationHelper helper = new HealthStatusInformationHelper();

    /**
     * Constructor for the statistics class
     * 
     * @param statsTab the tab that this is inserted into
     * @param parentStage the stage that tab is in
     */
    public StatisticsTabGUI(Tab statsTab, Stage parentStage)
    {
        this.parentTab = statsTab;
        this.parentStage = parentStage;
    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     * 
     */
    public Tab showStatistics()
    {
        mainBox = new HBox(75);

        VBox generateBox = new VBox(20);

        Label categoriesLbl = new Label("Categories: ");
        categoriesLbl.setFont(new Font(16));
        fieldList = new ListView<String>();
        ObservableList<String> objects = FXCollections.observableArrayList(
                "Allergies", "Seizures", "Medications", "Vaccinations",
                "Conditions");
        fieldList.setItems(objects);
        fieldList.setMaxHeight(118);
        Label comparisonLbl = new Label("Comparison: ");
        ComboBox<String> comparisonCmbo = new ComboBox<String>();
        ObservableList<String> comparisons = FXCollections.observableArrayList(
                "contains", "equals", "greater than", "less than", "between");
        comparisonCmbo.setItems(comparisons);
        comparisonCmbo.setValue("contains");
        Label conditionLbl = new Label("Condition: ");
        TextField conditionTxt = new TextField();
        Button generateQryBtn = new Button("Generate Query");

        generateBox.getChildren().addAll(categoriesLbl, fieldList,
                comparisonLbl, comparisonCmbo, conditionLbl, conditionTxt,
                generateQryBtn);

        VBox queryBox = new VBox(40);
        Label sqlLbl = new Label("SQL Query");
        sqlLbl.setFont(new Font(16));
        TextArea sqlTxtArea = new TextArea();
        sqlTxtArea.setWrapText(true);

        HBox queryBtnBox = new HBox(20);

        Button qryBtn = new Button("Execute Query");
        qryBtn.setOnAction(event -> {
            getStatistics(sqlTxtArea.getText());
        });
        Button saveQryBtn = new Button("Save Query");
        queryBtnBox.getChildren().addAll(qryBtn, saveQryBtn);
        StatisticsHelper sh = new StatisticsHelper();
        generateQryBtn.setOnAction(event -> {
            String query = "";
            if ( !(fieldList.getSelectionModel().getSelectedItem() == null) )
            {
                query = sh.generateSQL(fieldList.getSelectionModel()
                        .getSelectedItem(), comparisonCmbo.getSelectionModel()
                        .getSelectedItem(), conditionTxt.getText());
            }
            else
            {
                query = "Choose a field from the categories list.";
            }
            sqlTxtArea.setText(query);
        });

        saveQryBtn.setOnAction(event -> {
            saveQuery(sqlTxtArea.getText());
            savedQueryBox.getChildren().set(1, createSavedQueryList());
        });

        Label totalParticipantsLbl = new Label();
        queryParticipantsLbl = new Label();
        queryBox.getChildren().addAll(sqlLbl, sqlTxtArea, queryBtnBox,
                totalParticipantsLbl, queryParticipantsLbl);
        Label loadQueryLbl = new Label("Saved Queries:");
        loadQueryLbl.setFont(new Font(16));
        savedQueryBox = new VBox(20);
        savedQueryBox.getChildren()
                .addAll(loadQueryLbl, createSavedQueryList());
        VBox loadQueryBox = new VBox(10);
        loadQueryBox.setPadding(new Insets(45, 0, 0, -60));
        Button loadQueryBtn = new Button("Load Query");
        loadQueryBtn.setMinWidth(100);
        loadQueryBtn.setOnAction(event -> {
            String queryName = savedQueryList.getSelectionModel()
                    .getSelectedItem();
            sqlTxtArea.setText(SavedQuery.populateQuery(queryName));
        });
        Button deleteQueryBtn = new Button("Delete Query");
        deleteQueryBtn.setMinWidth(100);
        deleteQueryBtn.setOnAction(event -> {
            String queryName = savedQueryList.getSelectionModel()
                    .getSelectedItem();
            deleteQuery(queryName);
            savedQueryBox.getChildren().set(1, createSavedQueryList());
        });
        Button genQuartRpt = new Button("Generate Quarterly Reports");
        VBox.setMargin(genQuartRpt, new Insets(305, 0, 0, 0));
        genQuartRpt.setOnAction(event -> {
            Stage stage = new Stage();
            QuarterlyReportsGUI reportGUI = new QuarterlyReportsGUI(stage);
            stage.setScene(new Scene(reportGUI.mainVbox));
            stage.showAndWait();
        });
        loadQueryBox.getChildren().addAll(loadQueryBtn, deleteQueryBtn,
                genQuartRpt);
        mainBox.getChildren().addAll(generateBox, queryBox, savedQueryBox,
                loadQueryBox);
        mainBox.setPadding(new Insets(20));
        parentTab.setContent(mainBox);
        return parentTab;
    }

    /**
     * method for deleting a saved query
     * 
     * @param queryName the name of the query to be deleted
     */
    private void deleteQuery( String queryName )
    {
        Stage stage = new Stage();
        Scene scene;

        if ( queryName != null && queryName != "null" )
        {

            PopUpCheck checkBox = new PopUpCheck("Are you sure you want to "
                    + "delete the query: \n\n'" + queryName + "'", stage);

            scene = new Scene(checkBox.root, 300, 120);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.parentStage);
            stage.showAndWait();

            if ( checkBox.result )
            {
                SavedQuery.removeQuery(queryName);
            }

        }
        else
        {
            PopUpMessage messageBox = new PopUpMessage("Please select a query"
                    + " to remove.", stage);

            messageBox.stage.showAndWait();
        }

    }

    /**
     * Method for saving a query to the database
     * 
     * @param text the sql of the query to save
     */
    private void saveQuery( String text )
    {
        Stage saveQueryStage = new Stage();

        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label queryNameLbl = new Label("What do you want to name this query? ");
        queryNameLbl.setFont(new Font(14));

        TextField queryNameTxt = new TextField();

        grid.add(queryNameLbl, 0, 0, 2, 1);
        grid.add(queryNameTxt, 0, 1, 2, 1);

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Button addQueryBtn = new Button("Add");
        addQueryBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                String result = SavedQuery.createSavedQuery(
                        queryNameTxt.getText(), text);

                // if no error message is recieved then close this window and
                // refresh the table
                if ( result.equals("") )
                {
                    saveQueryStage.close();
                }
                // if there is an error message, display it
                else
                {
                    lblWarning.setTextFill(Color.FIREBRICK);
                    lblWarning.setText(result);
                }
            }
        });

        HBox addHbox = new HBox();
        addHbox.getChildren().add(addQueryBtn);
        grid.add(lblWarning, 1, 2);
        grid.add(addQueryBtn, 1, 3);
        GridPane.setHalignment(addQueryBtn, HPos.CENTER);
        grid.setPadding(new Insets(20, 0, 0, 20));
        saveQueryStage.setScene(new Scene(grid, 275, 150));
        saveQueryStage.initModality(Modality.APPLICATION_MODAL);
        saveQueryStage.initOwner(this.parentStage);
        saveQueryStage.setResizable(false);
        saveQueryStage.showAndWait();
    }

    /**
     * A list that contains every query name in the database
     * 
     * @return
     */
    private ListView<String> createSavedQueryList()
    {
        savedQueryList = new ListView<>();
        ObservableList<String> objects = FXCollections.observableArrayList();
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.select("queryName", "SavedQuery", "", "");
        try
        {
            while ( rs.next() )
            {
                objects.add(rs.getString(1));
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        savedQueryList.setItems(objects);
        db.disconnect();
        return savedQueryList;
    }

    /**
     * Method for generating statistics from the sql field.
     * 
     * @param queryString The sql string that needs to be queryed with.
     */
    private void getStatistics( String queryString )
    {
        String result = "";
        // Check to ensure that the sql is valid
        if ( queryString.toUpperCase().indexOf("SELECT") == 0
                && (queryString.indexOf(";") == -1 || queryString.indexOf(";") == queryString
                        .length() - 1) )

        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            ResultSet rs = db.directSelect(queryString);
            int count = 0;
            try
            {
                PrintWriter writer = new PrintWriter("Statistic.csv", "UTF-8");
                try
                {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    String titleString = "";
                    for ( int i = 1; i <= rsmd.getColumnCount(); i++ )
                    {
                        titleString += rsmd.getColumnLabel(i);
                        if ( i < rsmd.getColumnCount() )
                        {
                            titleString += ", ";
                        }
                    }
                    writer.println(titleString);
                    while ( rs.next() )
                    {
                        count++;

                        String contentString = "";
                        for ( int i = 1; i <= rsmd.getColumnCount(); i++ )
                        {
                            contentString += rs.getString(i);
                            if ( i < rsmd.getColumnCount() )
                            {
                                contentString += ", ";
                            }
                        }
                        writer.println(contentString);
                    }
                }
                catch ( SQLException e )
                {
                    e.printStackTrace();
                }
                writer.close();
            }
            catch ( FileNotFoundException | UnsupportedEncodingException e1 )
            {
                e1.printStackTrace();
            }
            result = "Statistics.csv has been made \nNumber of participants found: "
                    + count;
        }
        else
        {
            result = "Your query must beign with SELECT and end with ';'";
        }
        queryParticipantsLbl.setText(result);
    }

}