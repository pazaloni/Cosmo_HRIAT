package gui;

import helpers.DatabaseHelper;
import helpers.HealthStatusInformationHelper;

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

import object.Allergies;
import object.QueryResult;
import object.Medication;
import object.SavedQuery;
import controllers.AllergiesTableViewController;
import controllers.MedicalConditionsTableViewController;
import controllers.ProgressNotesTableViewController;
import controllers.MedicationsTableViewController;
import core.MedicalAdministrator;
import core.PopUpCheck;
import core.PopUpMessage;
import core.StaffAccount;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * Purpose: Represents the health status form GUI, which by default pulls
 * information
 *
 * @author CIMP
 * @version 1.0
 */
public class StatisticsTabGUI
{
    public static final String FORM_TITLE = "Health Status Information";
    public static final int SPACING = 10;

    private String cosmoID;

    private HBox mainBox;

    // The Control that this will be placed in
    private Tab parentTab;

    private StaffAccount loggedInUser;

    private Stage parentStage;

    Label queryParticipantsLbl;

    VBox savedQueryBox;

    ListView<String> savedQueryList;

    private Button btnSave = new Button("Save");

    HealthStatusInformationHelper helper = new HealthStatusInformationHelper();

    /**
     * 
     * Constructor for the HealthStatus class.
     * 
     * @param ctrl
     *            the pane, or box that this will be placed on
     */
    public StatisticsTabGUI(Tab statsTab, StaffAccount loggedInUser,
            Stage parentStage)
    {
        this.parentTab = statsTab;
        this.loggedInUser = loggedInUser;
        this.parentStage = parentStage;
    }

    /**
     * 
     * Purpose: This method will make everything and display it.
     * 
     * @param cosmoId
     *            : The cosmoId for the participant
     */
    public Tab showStatistics()
    {
        mainBox = new HBox(50);

        VBox generateBox = new VBox(20);

        Label categoriesLbl = new Label("Categories: ");
        categoriesLbl.setFont(new Font(16));
        ListView<String> fieldList = new ListView<String>();
        ObservableList<String> objects = FXCollections.observableArrayList(
                "Allergies", "Seizures", "Medications", "Vaccinations",
                "Conditions", "Falls over time", "Age Groups", "Lift Usage",
                "Catheters", "Injections given");
        fieldList.setItems(objects);
        fieldList.setMaxHeight(255);
        Label comparisonLbl = new Label("Comparison: ");
        ComboBox<String> comparisonCmbo = new ComboBox<String>();
        ObservableList<String> comparisons = FXCollections.observableArrayList(
                "contains", "equals", "greater than", "less than", "between");
        comparisonCmbo.setItems(comparisons);
        comparisonCmbo.setValue("contains");
        Label conditionLbl = new Label("Condition: ");
        TextField conditionTxt = new TextField();
        Button generateQryBtn = new Button("Generate Query");

        Button genQuartRpt = new Button("Generate Quarterly Reports");
        genQuartRpt.setOnAction(event -> {
            Stage stage = new Stage();
            QuarterlyReportsGUI reportGUI = new QuarterlyReportsGUI(stage);
            stage.setScene(new Scene(reportGUI.mainVbox));
            stage.showAndWait();
        });
        generateBox.getChildren().addAll(categoriesLbl, fieldList,
                comparisonLbl, comparisonCmbo, conditionLbl, conditionTxt,
                generateQryBtn, genQuartRpt);

        VBox queryBox = new VBox(40);
        Label sqlLbl = new Label("SQL Query");
        sqlLbl.setFont(new Font(16));
        TextArea sqlTxtArea = new TextArea();
        sqlTxtArea.setWrapText(true);

        HBox queryBtnBox = new HBox(20);

        Button qryBtn = new Button("Query");
        qryBtn.setOnAction(event -> {
            getStatistics(sqlTxtArea.getText());
        });
        Button saveQryBtn = new Button("Save Query");
        queryBtnBox.getChildren().addAll(qryBtn, saveQryBtn);

        generateQryBtn.setOnAction(event -> {
            String query = generateSQL(fieldList.getSelectionModel()
                    .getSelectedItem(), comparisonCmbo.getSelectionModel()
                    .getSelectedItem(), conditionTxt.getText());
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
        savedQueryBox = new VBox();
        savedQueryBox.getChildren()
                .addAll(loadQueryLbl, createSavedQueryList());
        VBox loadQueryBox = new VBox(20);
        Button loadQueryBtn = new Button("Load Query");
        loadQueryBtn.setOnAction(event -> {
            String queryName = savedQueryList.getSelectionModel()
                    .getSelectedItem();
            sqlTxtArea.setText(populateQuery(queryName));
        });
        Button deleteQueryBtn = new Button("Delete Query");
        deleteQueryBtn.setOnAction(event -> {
            String queryName = savedQueryList.getSelectionModel()
                    .getSelectedItem();
            deleteQuery(queryName);
            savedQueryBox.getChildren().set(1, createSavedQueryList());
        });
        loadQueryBox.getChildren().addAll(loadQueryBtn, deleteQueryBtn);
        mainBox.getChildren().addAll(generateBox, queryBox, savedQueryBox,
                loadQueryBox);
        mainBox.setPadding(new Insets(20));
        parentTab.setContent(mainBox);
        return parentTab;
    }

    private void deleteQuery( String queryName )
    {
        Stage stage = new Stage();
        Scene scene;

        if ( queryName != null && queryName != "null" )
        {

            PopUpCheck checkBox = new PopUpCheck("Are you sure you want to "
                    + "delete the query '" + queryName + "'?", stage);

            scene = new Scene(checkBox.root, 300, 75);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.parentStage);
            stage.showAndWait();

            // when the user is removed from the database
            if ( checkBox.result )
            {
                SavedQuery.removeQuery(queryName);
            }

        }
        // pop up a message saying that no user has been selected to delete
        else
        {
            // tell the user to select a user to delete
            PopUpMessage messageBox = new PopUpMessage("Please select a query"
                    + " to remove.", stage);

            messageBox.stage.showAndWait();
        }

    }

    private void saveQuery( String text )
    {
        Stage saveQueryStage = new Stage();

        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label queryNameLbl = new Label("What do you want to name this query? ");
        queryNameLbl.setFont(new Font(16));

        TextField queryNameTxt = new TextField();

        grid.add(queryNameLbl, 0, 0, 2, 1);
        // grid.add(lblWarning, 1, 0);
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
                // call create participant on medical administrator with the
                // text passed in
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
        grid.add(addHbox, 1, 3);
        saveQueryStage.setScene(new Scene(grid, 325, 200));
        saveQueryStage.initModality(Modality.APPLICATION_MODAL);
        saveQueryStage.initOwner(this.parentStage);
        saveQueryStage.setResizable(false);
        saveQueryStage.showAndWait();
    }

    private String populateQuery( String queryName )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.select("query", "SavedQuery", "queryName ='"
                + queryName + "'", "");
        try
        {
            rs.next();
            result = rs.getString(1);

        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        savedQueryList.setItems(objects);
        db.disconnect();
        return savedQueryList;
    }

    private String generateSQL( String searchFor, String comparison,
            String condition )
    {
        String query = "SELECT cosmoID";
        String[] conditionArray = null;
        switch ( searchFor )
        {
            case "Allergies":

                conditionArray = getTableHeadings("Allergies");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Allergies WHERE ";
                break;
            case "Seizures":

                conditionArray = getTableHeadings("Seizures");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Seizures WHERE ";
                break;
            case "Medications":

                conditionArray = getTableHeadings("Medications");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Medication WHERE ";
                break;
            case "Vaccinations":

                conditionArray = getTableHeadings("Vaccinations");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Vaccination WHERE ";
                break;
            case "Conditions":

                conditionArray = getTableHeadings("Conditions");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Conditions WHERE ";
                break;
            case "Falls over time":
                // TODO
                query += "?????";
                break;
            case "Age Groups":
                // TODO
                query += "?????";
                break;
            case "Lift Usage":
                // TODO
                query += "?????";
                break;
            case "Catheters":
                // TODO
                query += "?????";
                break;
            case "Injections given":
                // TODO
                query += "?????";
                break;
        }
        switch ( comparison )
        {
            case "contains":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " LIKE \"*" + condition
                            + "*\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "equals":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " = \"" + condition + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "greater than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " > \"" + condition + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "less than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " < \"" + condition + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "between":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i]
                            + " BETWEEN \""
                            + condition.substring(0, condition.indexOf(" "))
                            + "\" AND \""
                            + condition.substring(condition.indexOf(" ")+1,
                                    condition.length()) + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
        }

        return query;

    }

    private String[] getTableHeadings( String queryName )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.directSelect("Select * from " + queryName);
        String[] result = new String[0];
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            result = new String[columnCount];
            for ( int i = 1; i <= result.length; i++ )
            {
                result[i - 1] = rsmd.getColumnLabel(i);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        return result;
    }

    private void getStatistics( String queryString )
    {
        String result = "";
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
            result = "Number of participants found: " + count;
        }
        else
        {
            result = "Your query must beign with SELECT and end with ';'";
        }
        queryParticipantsLbl.setText(result);
    }

}