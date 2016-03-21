package gui;

import helpers.PersonalCareHelper;
import helpers.SeizureDescriptionFormHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import controllers.ProgressNotesTableViewController;
import core.MedicalAdministrator;
import core.PopUpCheck;
import core.PopUpMessage;
import core.ProgressNotes;
import core.StaffAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * Purpose: Represents the progress notes form GUI, which by default pulls
 * information
 *
 * @author Niklaas Neijmeijer cst207 Steven Palchinski cst209
 * @version 1.0
 */
public class PersonalCareGUI
{
    //the heading for the page
    public static final String FORM_TITLE = "Personal Care";
    //the spacing for each of the hBoxes 
    public static final int SPACING = 10;

    //the current participants cosmoID number
    private String cosmoID;

    //the scrollbox that will hold the content
    private ScrollPane mainBox;

    // The Control that this will be placed in
    private Tab parentTab;

    //the logged in user
    private StaffAccount loggedInUser;

    //the parent Stage
    private Stage parentStage;

    //The label for displaying when the form was last updated.
    private Label lastUpdatedTxt;
    
    //the save button
    private Button btnSave = new Button("Save Information");

    //Array to hold all editable checkboxes in the tab
    private CheckBox[] optionsArray = new CheckBox[11];

    private ComboBox<String> assistanceCBOBox;

    
    /**
     * Constructor for the Personal Care class.
     * 
     * @param personalCareTab the current tab
     * @param loggedInUser the logged in user
     * @param parentStage the window this is displayed in
     */
    public PersonalCareGUI(Tab personalCareTab, StaffAccount loggedInUser,
            Stage parentStage)
    {
        this.parentTab = personalCareTab;
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
    public Tab showPersonalCare( String cosmoId )
    {
        this.cosmoID = cosmoId;

        Label title = new Label(FORM_TITLE);
        HBox titleBox = new HBox();
        title.setFont(new Font(22));

        mainBox = new ScrollPane();
        VBox contentBox = new VBox();
        BorderPane subHeaderBox = new BorderPane();
        HBox updateBox = new HBox();
        Label dateCompletedLbl = new Label("Date Completed: ");
        lastUpdatedTxt = new Label();
        // make sure basic staff can't save
        if ( !(this.loggedInUser instanceof MedicalAdministrator) )
        {
            this.btnSave.setVisible(false);
        }

        btnSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {

                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat();
                formatter.applyPattern("dd-MMM-yyyy");
                String nowString = formatter.format(now);
                
                PersonalCareHelper helper = new PersonalCareHelper();
                Boolean[] valArray = new Boolean[11];
                for(int i = 0 ; i < optionsArray.length; i++)
                {
                    valArray[i] = optionsArray[i].selectedProperty().getValue();
                }
                helper.savePersonalCareInformation(valArray, assistanceCBOBox.getValue(),nowString, cosmoId);

                lastUpdatedTxt.setText(nowString);
            }

        });
        
        updateBox.getChildren().addAll(dateCompletedLbl, lastUpdatedTxt);
        titleBox.getChildren().addAll(title, btnSave);
        titleBox.setSpacing(250);
        HBox insulinBox = new HBox();

        CheckBox insulinChk = new CheckBox("Daily Insulin");

        optionsArray[0] = insulinChk;

        insulinBox.getChildren().addAll(insulinChk);

        insulinBox.setSpacing(SPACING);

        HBox depoBox = new HBox();

        CheckBox depoChk = new CheckBox("Injections: Depo Every 3 Months");

        optionsArray[1] = depoChk;

        depoBox.getChildren().addAll(depoChk );

        depoBox.setSpacing(SPACING);

        HBox bTwelveBox = new HBox();

        CheckBox bTwelveChk = new CheckBox("Injections: Monthly B12");

        optionsArray[2] = bTwelveChk;
        bTwelveBox.getChildren().addAll(bTwelveChk);

        bTwelveBox.setSpacing(SPACING);

        HBox suprapubicBox = new HBox();

        CheckBox suprapubicChk = new CheckBox("Monthly Suprapubic Catherter Change");

        optionsArray[3] = suprapubicChk;

        suprapubicBox.getChildren().addAll(suprapubicChk);

        suprapubicBox.setSpacing(SPACING);

        HBox dressingBox = new HBox();

        CheckBox dressingChk = new CheckBox("Dressings");

        optionsArray[4] = dressingChk;

        dressingBox.getChildren().addAll(dressingChk);

        dressingBox.setSpacing(SPACING);

        HBox cobanBox = new HBox();

        CheckBox cobanChk = new CheckBox("Coban Wraps");

        optionsArray[5] = cobanChk;

        cobanBox.getChildren().addAll(cobanChk);

        cobanBox.setSpacing(SPACING);

        HBox ostomyBox = new HBox();

        CheckBox ostomyChk = new CheckBox("Colostomy/Illiostomy Care");

        optionsArray[6] = ostomyChk;

        ostomyBox.getChildren().addAll(ostomyChk);

        ostomyBox.setSpacing(SPACING);

        HBox catheterBox = new HBox();

        CheckBox catheterChk = new CheckBox("Catheter");

        optionsArray[7] = catheterChk;

        catheterBox.getChildren().addAll(catheterChk);

        catheterBox.setSpacing(SPACING);

        HBox trachBox = new HBox();

        CheckBox trachChk = new CheckBox("Tracheotomy");

        optionsArray[8] = trachChk;

        trachBox.getChildren().addAll(trachChk);

        trachBox.setSpacing(SPACING);

        HBox feedBox = new HBox();        

        CheckBox feedChk = new CheckBox("Tube Feeds");

        optionsArray[9] = feedChk;

        feedBox.getChildren().addAll(feedChk);

        feedBox.setSpacing(SPACING);

        HBox dietBox = new HBox();

        CheckBox dietChk = new CheckBox("Diet(Food prep & feeding");

        optionsArray[10] = dietChk;

        dietBox.getChildren().addAll(dietChk);
        dietBox.setSpacing(SPACING);
        VBox assistanceBox = new VBox();

        Label assistanceLabel = new Label("Assistance");

        //observable list to store the values that are displayed in the combo box
        ObservableList<String> assistanceList = FXCollections
                .observableArrayList("", "Require total full lift",
                        "Require sit/stand lift", "Two staff assist",
                        "One staff assist");

        assistanceCBOBox = new ComboBox<String>(assistanceList);

        assistanceBox.getChildren().addAll(assistanceLabel, assistanceCBOBox);

        assistanceBox.setSpacing(SPACING);
        
        subHeaderBox.setRight(updateBox);
        
        contentBox.getChildren().addAll(titleBox, subHeaderBox, insulinBox, depoBox,
                bTwelveBox, suprapubicBox, dressingBox, cobanBox, ostomyBox, catheterBox,
                trachBox, feedBox, dietBox, assistanceBox);
        contentBox.setSpacing(15);

        mainBox.setContent(contentBox);

        mainBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainBox.setHmax(contentBox.getWidth());

        mainBox.setPadding(new Insets(10, 10, 10, 10));

        assignSeizureInfo();
        
        this.parentTab.setContent(mainBox);

        return parentTab;
    }

    /**
     * 
     * Purpose: take in information from the database and display it in the
     * proper fields, fields will be empty if the participant does not have
     * seizures
     * 
     */
    private void assignSeizureInfo()
    {
        PersonalCareHelper helper = new PersonalCareHelper();
        String[] info = helper.retrievePersonalCareInformation(cosmoID);
        for( int i = 0 ; i < optionsArray.length ; i++)
        {
            optionsArray[i].setSelected(Boolean.parseBoolean(info[i]));
        }
        
        assistanceCBOBox.setValue(info[info.length-2]);
        
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if ( info[info.length-1].length() > 5 )
        {
            try
            {
                date = inputFormatter.parse(info[info.length-1]);
            }
            catch ( ParseException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            SimpleDateFormat outputFormatter = new SimpleDateFormat();
            outputFormatter.applyPattern("dd-MMM-yyyy");

            lastUpdatedTxt.setText(outputFormatter.format(date));
        }
    }
    
}