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
        generateBox.getChildren().addAll(categoriesLbl, fieldList, comparisonLbl, comparisonCmbo,
                conditionLbl, conditionTxt, generateQryBtn, genQuartRpt);

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
            createSavedQueryList();
        });
        
        Label totalParticipantsLbl = new Label();
        queryParticipantsLbl = new Label();
        queryBox.getChildren().addAll(sqlLbl, sqlTxtArea, queryBtnBox,
                totalParticipantsLbl, queryParticipantsLbl);
        VBox loadQueryBox = new VBox(20);
        Button loadQueryBtn = new Button("Load Query");
        loadQueryBtn.setOnAction(event -> {
            String queryName = savedQueryList.getSelectionModel().getSelectedItem();
            sqlTxtArea.setText(populateQuery(queryName));
        });
        
        loadQueryBox.getChildren().addAll(loadQueryBtn);
        mainBox.getChildren().addAll(generateBox, queryBox, createSavedQueryList(), loadQueryBox);
        mainBox.setPadding(new Insets(20));
        parentTab.setContent(mainBox);
        return parentTab;
    }

    private void saveQuery( String text )
    {
        Stage saveQueryStage = new Stage();        


        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label queryNameLbl = new Label("What do you want to name this query?: ");
        queryNameLbl.setFont(new Font(18));
        
        TextField queryNameTxt = new TextField();

        grid.add(queryNameLbl, 0, 1,1,2);
        grid.add(lblWarning, 1, 0);
        grid.add(queryNameTxt, 0, 2,1,2);

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
                        queryNameTxt.getText(), text );

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
        HBox resetHbox = new HBox();
        addHbox.getChildren().add(addQueryBtn);
        grid.add(resetHbox, 0, 4);
        grid.add(addHbox, 1, 4);
        saveQueryStage.setScene(new Scene(
                grid, 325, 200));
        saveQueryStage
                .initModality(Modality.APPLICATION_MODAL);
        saveQueryStage
                .initOwner(this.parentStage);
        saveQueryStage.setResizable(false);
        saveQueryStage.show();
    }

    private String populateQuery( String queryName )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.select("query", "SavedQuery","queryName ='" + queryName + "'","");
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
            while(rs.next())
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
                    query += conditionArray[i] + " LIKE '*" + condition + "*'";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "equals":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " = '" + condition + "'";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "greater than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " > '" + condition + "'";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "less than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " < '" + condition + "'";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "between":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " = '" + condition + "'";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
        }
        // query = searchFor + " " + comparison + " " +condition;

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
                        .length()) )

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

    /*
     * private TableView<QueryResult> createResultTable() {
     * QueryResultTableViewController controller = new
     * QueryResultTableViewController( "5"); TableView<QueryResult> table =
     * controller.conditionTable; table. table.setMaxWidth(700); return table; }
     *//**
     * 
     * Purpose: Create the medical conditions table
     * 
     * @return: HBox containing the medical conditions
     */
    private VBox createMedicalConditions( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label medicalCondtionsLbl = new Label("Medical Conditions");
        medicalCondtionsLbl.setFont(Font.font(22));

        Button btnAddMedicalCondition = new Button("Add");
        Button btnEditMedicalCondition = new Button("Edit");
        Button btnDeleteMedicalCondition = new Button("Delete");

        MedicalConditionsTableViewController controller = new MedicalConditionsTableViewController(
                cosmoId);
        TableView<QueryResult> table = controller.conditionTable;

        // Add the medical condition
        btnAddMedicalCondition
                .setOnMouseClicked(event -> {
                    ManageMedicalConditionGUI manageMedicalCondition = new ManageMedicalConditionGUI(
                            parentStage);
                    manageMedicalCondition.showAddMedicalCondition(cosmoId);
                    controller.refreshTable(cosmoId);
                });
        // Editing a medical condition
        btnEditMedicalCondition
                .setOnMouseClicked(event -> {
                    QueryResult condition = controller
                            .getSelectedMedicalCondition();
                    if ( condition == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select a medical condition to edit",
                                stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        ManageMedicalConditionGUI manageMedicalCondition = new ManageMedicalConditionGUI(
                                parentStage);
                        manageMedicalCondition.showUpdateMedicalCondition(
                                condition, cosmoId);
                        controller.refreshTable(cosmoId);
                    }
                });
        btnDeleteMedicalCondition
                .setOnMouseClicked(event -> {
                    QueryResult condition = controller
                            .getSelectedMedicalCondition();
                    if ( condition == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select a medical condition to delete",
                                stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        Stage stage = new Stage();
                        PopUpCheck popup = new PopUpCheck(
                                "Are you sure you want to delete the selected medical condition ?",
                                stage);
                        Scene scene = new Scene(popup.root, 450, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                        if ( popup.runCheck() )
                        {
                            QueryResult.deleteCondition(table
                                    .getSelectionModel().getSelectedItem(),
                                    cosmoID);
                        }
                        controller.refreshTable(cosmoID);
                    }
                });

        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            btnAddMedicalCondition.setVisible(false);
            btnEditMedicalCondition.setVisible(false);
            btnDeleteMedicalCondition.setVisible(false);
        }

        buttons.getChildren().addAll(btnAddMedicalCondition,
                btnEditMedicalCondition, btnDeleteMedicalCondition);

        controls.getChildren().addAll(buttons);

        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(380);

        box.getChildren().addAll(medicalCondtionsLbl, table, controls);
        return box;
    }

    /**
     * 
     * Purpose: Create the allergies info for the participant
     * 
     * @param cosmoID
     *            The participant cosmoId which is used to pull the information
     *            from the database
     *
     * @return VBox with a label and a table view
     */
    private VBox createAllergiesInfo( String cosmoID )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label allergiesLbl = new Label("Allergies");
        allergiesLbl.setFont(Font.font(22));

        Button btnAddAllergy = new Button("Add");
        Button btnEditAllergy = new Button("Edit");
        Button btnDelAllergy = new Button("Delete");

        buttons.getChildren().addAll(btnAddAllergy, btnEditAllergy,
                btnDelAllergy);

        controls.getChildren().addAll(buttons);

        AllergiesTableViewController controller = new AllergiesTableViewController(
                cosmoID + "");
        TableView<Allergies> table = controller.allergiesTable;
        btnEditAllergy.setOnAction(event -> {
            Allergies allergy = controller.getSelectedAllergy();
            // If there is no allergy selected, then display a message
                if ( allergy == null )
                {
                    Stage stage = new Stage();
                    PopUpMessage popup = new PopUpMessage(
                            "Must select an allergy to edit", stage);
                    Scene scene = new Scene(popup.root, 300, 75);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(parentStage);
                    stage.setScene(scene);
                    stage.showAndWait();
                }
                else
                {
                    ManageAllergyGUI manageAllergies = new ManageAllergyGUI(
                            parentStage);
                    manageAllergies.showUpdateAllergy(table.getSelectionModel()
                            .getSelectedItem(), cosmoID);
                    controller.refreshTable(cosmoID);
                }
            });

        btnAddAllergy
                .setOnAction(event -> {
                    ManageAllergyGUI manageAllergies = new ManageAllergyGUI(
                            parentStage);
                    manageAllergies.showAddAllergy(cosmoID);
                    controller.refreshTable(cosmoID);
                });

        btnDelAllergy
                .setOnAction(event -> {

                    Allergies allergy = controller.getSelectedAllergy();

                    if ( allergy == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select an allergy to delete", stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        Stage stage = new Stage();
                        PopUpCheck popup = new PopUpCheck(
                                "Are you sure you want to delete the selected allergy ?",
                                stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                        if ( popup.runCheck() )
                        {
                            Allergies.deleteAllergy(table.getSelectionModel()
                                    .getSelectedItem(), cosmoID);
                        }
                        controller.refreshTable(cosmoID);
                    }

                });
        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(485);

        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            btnAddAllergy.setVisible(false);
            btnEditAllergy.setVisible(false);
            btnDelAllergy.setVisible(false);
        }
        box.getChildren().addAll(allergiesLbl, table, controls);
        return box;
    }

    /**
     * 
     * Purpose: Create the medications info for the participant
     * 
     * @param cosmoId
     *            The participant cosmoId which is used to pull the information
     *            from the database
     *
     * @return VBox with a label and a table view
     */
    private VBox createMedicationInfo( String cosmoId )
    {
        VBox box = new VBox();
        HBox controls = new HBox();
        HBox buttons = new HBox();

        Label medicationsLbl = new Label("Medications");
        medicationsLbl.setFont(Font.font(22));

        Button btnAddMedication = new Button("Add");
        Button btnEditMedication = new Button("Edit");
        Button btnDeleteMedication = new Button("Delete");
        MedicationsTableViewController controller = new MedicationsTableViewController(
                cosmoId);
        TableView<Medication> table = controller.medicationsTable;

        btnAddMedication.setOnAction(event -> {

            ManageMedicationGUI manageMedicaiton = new ManageMedicationGUI(
                    parentStage);
            manageMedicaiton.showAddMedication(cosmoId);
            controller.refreshTable(cosmoId);
        });

        // Handle the editing of a medication
        btnEditMedication
                .setOnAction(event -> {
                    // Get the selected medication
                    Medication med = controller.getSelectedMedication();
                    if ( med == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select an entry to edit", stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        ManageMedicationGUI manageMedication = new ManageMedicationGUI(
                                parentStage);
                        manageMedication.showUpdateMedication(cosmoId, med);
                        controller.refreshTable(cosmoId);
                    }

                });
        btnDeleteMedication
                .setOnAction(event -> {
                    Medication med = controller.getSelectedMedication();
                    if ( med == null )
                    {
                        Stage stage = new Stage();
                        PopUpMessage popup = new PopUpMessage(
                                "Must select a medication entry to delete",
                                stage);
                        Scene scene = new Scene(popup.root, 300, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                    }
                    else
                    {
                        Stage stage = new Stage();
                        PopUpCheck popup = new PopUpCheck(
                                "Are you sure you want to delete the selected medication entry ?",
                                stage);
                        Scene scene = new Scene(popup.root, 450, 75);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parentStage);
                        stage.setScene(scene);
                        stage.showAndWait();
                        if ( popup.runCheck() )
                        {
                            Medication.removeMedication(med.getMedicationName()
                                    .get(), cosmoID);
                            controller.refreshTable(cosmoID);
                        }

                    }
                });
        buttons.getChildren().addAll(btnAddMedication, btnEditMedication,
                btnDeleteMedication);

        controls.getChildren().addAll(buttons);

        table.setMaxWidth(700);

        buttons.setSpacing(5);
        controls.setSpacing(450);
        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            btnAddMedication.setVisible(false);
            btnDeleteMedication.setVisible(false);
            btnEditMedication.setVisible(false);
        }

        box.getChildren().addAll(medicationsLbl, table, controls);

        return box;
    }
}