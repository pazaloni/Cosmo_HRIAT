package controllers;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.omg.CORBA.Environment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;
import core.PersonalCare;

/**
 * This is the controller for the personal care tableview for the quarterly
 * reports preview GUI.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class PersonalCareTableViewController
{

    public TableView<PersonalCare> personalCareTable = new TableView<PersonalCare>();

    private TableColumn<PersonalCare, String> typeColumn = new TableColumn<PersonalCare, String>(
            "Categories");
    private TableColumn<PersonalCare, String> supportsColumn = new TableColumn<PersonalCare, String>(
            "Supports");
    private TableColumn<PersonalCare, String> totalAsOfColumn = new TableColumn<PersonalCare, String>(
            "totalPersonsAsOfPH");
    private TableColumn<PersonalCare, String> totalForLastYearColumn = new TableColumn<PersonalCare, String>(
            "totalForLastYearPH");

    public ObservableList<PersonalCare> personalCareData = FXCollections
            .observableArrayList();

    DatabaseHelper db = new DatabaseHelper();

    /**
     * Queries for the personal care data from the database, initializes the
     * tableview, and puts the data in the tableview.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public PersonalCareTableViewController()
    {
        queryPersonalCareData();
        initialize();
        personalCareTable.setItems(personalCareData);
        personalCareTable.setFocusTraversable(false);
        personalCareTable.setMaxHeight(150);
    }

    /**
     * Sets the label for the "Total as of <Date>" column.
     * 
     * @param label The text for the label.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public void setTotalAsOfColumn( String label )
    {
        totalAsOfColumn.setText(label);
    }

    /**
     * Sets the label for the "Total for <Date>" column.
     * 
     * @param label The text for the label.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public void setTotalForLastYearColumn( String label )
    {
        totalForLastYearColumn.setText(label);
    }

    /**
     * Queries the database for personal care data, and sorts into the different
     * categories for each support type, totalling the amount used for the year.
     * 
     * @author Breanna Wilson, Jon Froese
     */
    private void queryPersonalCareData()
    {
        db.connect();

        ResultSet rs = db.select("COUNT(dailyInsulin)", "Participant",
                "dailyInsulin = TRUE", "");
        PersonalCare dailyInsulin = null;

        try
        {
            if ( rs.next() )
            {
                dailyInsulin = new PersonalCare("Medical",
                        "Daily Insulin Injections", rs.getString(1));
                personalCareData.add(dailyInsulin);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(depoInjection)", "Participant",
                "depoInjection = TRUE", "");
        PersonalCare depoInjection = null;

        try
        {
            if ( rs.next() )
            {
                depoInjection = new PersonalCare("Medical",
                        "Injections - Depo every 3 months", rs.getString(1));
                personalCareData.add(depoInjection);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(b12Injection)", "Participant",
                "b12Injection = TRUE", "");
        PersonalCare b12Injection = null;

        try
        {
            if ( rs.next() )
            {
                b12Injection = new PersonalCare("Medical",
                        "Injections - Monthly - B12", rs.getString(1));
                personalCareData.add(b12Injection);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(suprapubicCatheter)", "Participant",
                "suprapubicCatheter = TRUE", "");
        PersonalCare suprapublicCatheter = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                suprapublicCatheter = new PersonalCare("Medical",
                        "Monthly Suprapubic Catheter Change", rs.getString(1));
                personalCareData.add(suprapublicCatheter);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(dressings)", "Participant", "dressings = TRUE",
                "");
        PersonalCare dressings = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                dressings = new PersonalCare("Medical", "Dressings",
                        rs.getString(1));
                personalCareData.add(dressings);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(cobanWraps)", "Participant", "cobanWraps = TRUE",
                "");
        PersonalCare cobanWraps = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                cobanWraps = new PersonalCare("Medical", "Coban Wraps",
                        rs.getString(1));
                personalCareData.add(cobanWraps);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        // OTHER SUPPORTS

        rs = db.select("COUNT(ostomy)", "Participant", "ostomy = TRUE", "");
        PersonalCare ostomy = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                ostomy = new PersonalCare("Other Supports",
                        "Colostomy/Illiostomy Care", rs.getString(1));
                personalCareData.add(ostomy);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(catheter)", "Participant", "catheter = TRUE", "");
        PersonalCare catheter = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                catheter = new PersonalCare("Other Supports", "Catheters",
                        rs.getString(1));
                personalCareData.add(catheter);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(tracheotomy)", "Participant",
                "tracheotomy = TRUE", "");
        PersonalCare tracheotomy = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                tracheotomy = new PersonalCare("Other Supports", "Tracheotomy",
                        rs.getString(1));
                personalCareData.add(tracheotomy);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(tubeFeeds)", "Participant", "tubeFeeds = TRUE",
                "");
        PersonalCare tubeFeeds = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                tubeFeeds = new PersonalCare("Other Supports", "Tube Feeds",
                        rs.getString(1));
                personalCareData.add(tubeFeeds);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(category)", "Allergies", "category = \"Food\"",
                "");
        PersonalCare foodAllergy = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                foodAllergy = new PersonalCare(
                        "Medical Conditions or Concerns", "Food Allergies",
                        rs.getString(1));
                personalCareData.add(foodAllergy);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(category)", "Allergies", "category = \"Drug\"",
                "");
        PersonalCare drugAllergy = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                drugAllergy = new PersonalCare(
                        "Medical Conditions or Concerns", "Drug Allergies",
                        rs.getString(1));
                personalCareData.add(drugAllergy);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(category)", "Allergies",
                "category = \"Environmental\"", "");
        PersonalCare enivroAllergy = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                enivroAllergy = new PersonalCare(
                        "Medical Conditions or Concerns",
                        "Environmental Allergies", rs.getString(1));
                personalCareData.add(enivroAllergy);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(conditionName)", "Conditions",
                "conditionName = \"Diabetes\""
                        + " OR conditionName = \"Diabetic\"", "");
        PersonalCare diabetes = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                diabetes = new PersonalCare("Medical Conditions or Concerns",
                        "Diabetics", rs.getString(1));
                personalCareData.add(diabetes);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(dietProvided)", "Participant",
                "dietProvided = TRUE", "");
        PersonalCare diet = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                diet = new PersonalCare("Medical Conditions or Concerns",
                        "Diet (food prep and feeding)", rs.getString(1));
                personalCareData.add(diet);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(conditionName)", "Conditions", "conditionName ="
                + " \"epileptic\" OR conditionName = \"epilepsy\"", "");
        PersonalCare epileptics = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                epileptics = new PersonalCare("Medical Conditions or Concerns",
                        "Epileptics", rs.getString(1));
                personalCareData.add(epileptics);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(transfersLifts)", "Participant",
                "transfersLifts LIKE \"full\"", "");
        PersonalCare liftsFull = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                liftsFull = new PersonalCare("Personal Care",
                        "Require total full lifts", rs.getString(1));
                personalCareData.add(liftsFull);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(transfersLifts)", "Participant",
                "transfersLifts LIKE \"sit\" OR"
                        + " transfersLifts LIKE \"stand\"", "");
        PersonalCare liftsOther = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                liftsOther = new PersonalCare("Personal Care",
                        "Require sit/stand lift", rs.getString(1));
                personalCareData.add(liftsOther);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(assistanceRequired)", "Participant",
                "assistanceRequired LIKE \"2\"", "");
        PersonalCare twoStaff = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                twoStaff = new PersonalCare("Personal Care", "2 staff assist",
                        rs.getString(1));
                personalCareData.add(twoStaff);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        rs = db.select("COUNT(assistanceRequired)", "Participant",
                "assistanceRequired LIKE \"1\"", "");
        PersonalCare oneStaff = null;

        try
        {
            if ( rs != null && rs.next() )
            {
                oneStaff = new PersonalCare("Personal Care", "1 staff assist",
                        rs.getString(1));
                personalCareData.add(oneStaff);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();
    }

    /**
     * Clears the tableview and observablelist, requeries for new data and
     * places it into the tableview
     * 
     * @author Breanna Wilson, Jon Froese
     */
    public void refreshTable()
    {
        this.personalCareData.clear();
        this.personalCareTable.getColumns().clear();
        this.queryPersonalCareData();
        this.initialize();
    }

    /**
     * Initializes the tableview, and sets up the cellfactories to pull data
     * from the data objects into the tablecolumns, and puts them into the
     * tableview
     * 
     * @author Breanna Wilson, Jon Froese
     */
    @SuppressWarnings("unchecked")
    public void initialize()
    {
        typeColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getTypeProperty());
        typeColumn.setMinWidth(250);
        typeColumn.setResizable(false);

        supportsColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getSupportsProperty());
        supportsColumn.setMinWidth(250);
        supportsColumn.setResizable(false);

        totalAsOfColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getTotalAsOfProperty());
        totalAsOfColumn.setMinWidth(150);
        totalAsOfColumn.setResizable(false);

        totalForLastYearColumn.setCellValueFactory(cellData -> cellData
                .getValue().getTotalForLastYearProperty());
        totalForLastYearColumn.setMinWidth(150);
        totalForLastYearColumn.setResizable(false);

        personalCareTable.getColumns().addAll(typeColumn, supportsColumn,
                totalAsOfColumn, totalForLastYearColumn);
    }

}
