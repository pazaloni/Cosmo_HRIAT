package gui;

import helpers.SeizureDescriptionFormHelper;

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
        Label lastUpdatedTxt = new Label();
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
                String[] values = new String[optionsArray.length + 1];
                for ( int i = 0; i < 7; i++ )
                {
                    values[i] = optionsArray[i].getText();
                }
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat();
                formatter.applyPattern("dd-MMM-yyyy");
                values[7] = formatter.format(now);

                lastUpdatedTxt.setText(values[7]);
            }

        });
        
        updateBox.getChildren().addAll(dateCompletedLbl, lastUpdatedTxt);
        titleBox.getChildren().addAll(title, btnSave);
        titleBox.setSpacing(250);
        HBox insulinBox = new HBox();

        Label insulinLbl = new Label("Daily Insulin");

        CheckBox insulinChk = new CheckBox();

        optionsArray[0] = insulinChk;

        insulinBox.getChildren().addAll(insulinLbl, insulinChk);

        insulinBox.setSpacing(SPACING);

        HBox depoBox = new HBox();

        Label depoLbl = new Label("Injections: Depo Every 3 Months");

        CheckBox depoChk = new CheckBox();

        optionsArray[1] = depoChk;

        depoBox.getChildren().addAll(depoLbl, depoChk);

        depoBox.setSpacing(SPACING);

        HBox bTwelveBox = new HBox();

        Label bTwelveLbl = new Label("Injections: Monthly B12");

        CheckBox bTwelveChk = new CheckBox();

        optionsArray[2] = bTwelveChk;
        bTwelveBox.getChildren().addAll(bTwelveLbl, bTwelveChk);

        bTwelveBox.setSpacing(SPACING);

        HBox suprapubicBox = new HBox();

        Label suprapubicLbl = new Label("Monthly Suprapubic Catherter Change");

        CheckBox suprapubicChk = new CheckBox();

        optionsArray[3] = suprapubicChk;

        suprapubicBox.getChildren().addAll(suprapubicLbl, suprapubicChk);

        suprapubicBox.setSpacing(SPACING);

        HBox dressingBox = new HBox();

        Label dressingLbl = new Label("Dressings");

        CheckBox dressingChk = new CheckBox();

        optionsArray[4] = dressingChk;

        dressingBox.getChildren().addAll(dressingLbl, dressingChk);

        dressingBox.setSpacing(SPACING);

        HBox cobanBox = new HBox();

        Label cobanLbl = new Label("Coban Wraps");

        CheckBox cobanChk = new CheckBox();

        optionsArray[5] = cobanChk;

        cobanBox.getChildren().addAll(cobanLbl, cobanChk);

        cobanBox.setSpacing(SPACING);

        HBox ostomyBox = new HBox();

        Label ostomyLbl = new Label("Colostomy/Illiostomy Care");

        CheckBox ostomyChk = new CheckBox();

        optionsArray[6] = ostomyChk;

        ostomyBox.getChildren().addAll(ostomyLbl, ostomyChk);

        ostomyBox.setSpacing(SPACING);

        HBox catheterBox = new HBox();

        Label catheterLbl = new Label("Catheter");

        CheckBox catheterChk = new CheckBox();

        optionsArray[7] = catheterChk;

        catheterBox.getChildren().addAll(catheterLbl, catheterChk);

        catheterBox.setSpacing(SPACING);

        HBox trachBox = new HBox();

        Label trachLbl = new Label("Tracheotomy");

        CheckBox trachChk = new CheckBox();

        optionsArray[8] = trachChk;

        trachBox.getChildren().addAll(trachLbl, trachChk);

        trachBox.setSpacing(SPACING);

        HBox feedBox = new HBox();

        Label feedLbl = new Label("Tube Feeds");

        CheckBox feedChk = new CheckBox();

        optionsArray[9] = feedChk;

        feedBox.getChildren().addAll(feedLbl, feedChk);

        feedBox.setSpacing(SPACING);

        HBox dietBox = new HBox();

        Label dietLbl = new Label("Diet(Food prep & feeding");

        CheckBox dietChk = new CheckBox();

        optionsArray[10] = dietChk;

        dietBox.getChildren().addAll(dietLbl, dietChk);
        dietBox.setSpacing(SPACING);
        HBox assistanceBox = new HBox();

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
                bTwelveBox, dressingBox, cobanBox, ostomyBox, catheterBox,
                trachBox, feedBox, dietBox, assistanceBox);
        contentBox.setSpacing(15);

        mainBox.setContent(contentBox);

        mainBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainBox.setHmax(contentBox.getWidth());

        mainBox.setPadding(new Insets(10, 10, 10, 10));

        this.parentTab.setContent(mainBox);

        return parentTab;
    }

}