package gui;

import helpers.DatabaseHelper;
import helpers.FormatHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import core.MedicalAdministrator;
import core.NotePopUp;
import core.StaffAccount;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * 
 * Purpose: To display the detailed information for a specific participant.
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class participantDetailsGUI extends Application
{
    // the main stage for the GUI
    public static Stage participantMainStage;

    // window used for editing main frame
    private Stage mainEditWindow;

    // the picture of the participant
    private ImageView previewPicture;

    // the selected participant's cosmo ID
    private int cosmoID;

    // the database helper object that allows querying of the database
    private DatabaseHelper DBObject = new DatabaseHelper();

    private StaffAccount loggedInUser;

    private static final int PREVIEW_TEXT_WIDTH = 150;
    private static final int ALLERGY_AND_SEIZURE_MIN_WITH = 410;

    private Label cosmoIDText = new Label();
    private Label firstNameText = new Label();
    private Label lastNameText = new Label();
    private Label dayOfBirthText = new Label();
    private Label healthNumText = new Label();
    private Label addressText = new Label();
    private Label phoneNumberText = new Label();
    private Label cityText = new Label();
    private Label postalText = new Label();
    private Label sinText = new Label();
    private Label careText = new Label();
    // /new label to represent the participant's status within the system
    private Label statusText = new Label();

    private Stage createNoteStage;

    /**
     * Purpose: displays the GUI
     * 
     * @param: Stage: the stage the GUI will be displayed on
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        participantDetailsConstruct(stage, this.cosmoID, loggedInUser);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff once they have
     * selected a participant to view
     * 
     * @param stage : the stage the medical staff will see
     */
    public void participantDetailsConstruct( Stage stage, int cosmoID,
            StaffAccount loggedInUser )
    {
        this.loggedInUser = loggedInUser;
        DBObject.connect();

        this.cosmoID = cosmoID;

        participantMainStage = new Stage();
        participantMainStage.setTitle("Cosmo Industries");

        VBox root = createMainVBox();
        participantMainStage.setScene(new Scene(root, 1420, 800));
        participantMainStage.initModality(Modality.APPLICATION_MODAL);
        participantMainStage.initOwner(stage);
        participantMainStage.resizableProperty().set(true);
        participantMainStage.show();
    }

    /**
     * 
     * Purpose: Create the header area of the program.
     * 
     * 
     * @return a BorderPane
     */
    public BorderPane createHBoxHeader()
    {
        BorderPane logoAndLogin = new BorderPane();
        logoAndLogin.setPadding(new Insets(15, 12, 15, 12));
        logoAndLogin.setStyle("-fx-background-color: #FFFFFF;");

        // logo image size
        ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
        logo.setFitWidth(400);
        logo.setFitHeight(49);

        // set the image left and right
        logoAndLogin.setLeft(logo);

        return logoAndLogin;
    }

    /**
     * 
     * Purpose: Create the Tabs Pane for display
     * 
     * @return a TabPane object
     */
    private TabPane createTabs()
    {
        TabPane tabPane = new TabPane();

        // Create tabs names
        Tab participantUpdate = new Tab();
        Tab healthStatus = new Tab();
        Tab seizureDescription = new Tab();
        Tab progressNotes = new Tab();
        Tab personalCare = new Tab();
        Tab scannedForms = new Tab();

        // set body for tabs
        HealthStatusFormGUI hsf = new HealthStatusFormGUI(healthStatus,
                loggedInUser, participantMainStage);
        healthStatus.setContent(hsf.showHealthStatusInfo(cosmoID + "")
                .getContent());

        // create the seizure tab
        SeizureDescriptionFormGUI sDescForm = new SeizureDescriptionFormGUI(
                seizureDescription, loggedInUser, cosmoID + "");
        seizureDescription.setContent(sDescForm.ShowSeizureForm().getContent());

        // create the Participant Update Tab
        PartcipantUpdateForm puf = new PartcipantUpdateForm(cosmoID,
                loggedInUser);
        participantUpdate.setContent(puf.getForm());

        // Create the Progress Notes Tab
        ProgressNotesFormGUI pnf = new ProgressNotesFormGUI(progressNotes,
                loggedInUser, participantMainStage);
        progressNotes.setContent(pnf.showProgressNotes(cosmoID + "")
                .getContent());

        // Create the Personal Care Tab
        PersonalCareGUI pc = new PersonalCareGUI(personalCare, loggedInUser,
                participantMainStage);
        personalCare.setContent(pc.showPersonalCare(cosmoID + "").getContent());
        //
        // //Create the Scanned Forms Tab
        ScanFormsGUI sfg = new ScanFormsGUI(scannedForms, loggedInUser,
                participantMainStage);
        scannedForms
                .setContent(sfg.showScannedForms(cosmoID + "").getContent());

        // set text for tabs
        healthStatus.setText("Health Status");
        participantUpdate.setText("Participant Update");
        seizureDescription.setText("Seizure Description");
        progressNotes.setText("Progress Notes");
        personalCare.setText("Personal Care");
        scannedForms.setText("Scanned Documents");

        // set tabs to not be closable
        healthStatus.closableProperty().set(false);
        participantUpdate.closableProperty().set(false);
        seizureDescription.closableProperty().set(false);
        progressNotes.closableProperty().set(false);
        personalCare.closableProperty().set(false);
        scannedForms.closableProperty().set(false);

        // set the size of the tabs and add to the pane
        tabPane.setTabMinWidth(100);
        tabPane.getTabs().addAll(healthStatus, participantUpdate,
                seizureDescription, progressNotes, personalCare, scannedForms);
        tabPane.setMinHeight(29);

        return tabPane;
    }

    /**
     * 
     * Purpose: Create the HBox that will contain the Preview pane and the Note
     * pane
     * 
     * @return HBox: the container for the preview details of the participants
     *         basic information
     */
    private HBox createHBoxPreviewDetails()
    {
        // create hbox and set size and padding
        HBox hbox = new HBox();
        hbox.setMinHeight(305);
        hbox.setMaxHeight(305);
        hbox.setPadding(new Insets(0, 0, 10, 0));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        // create preview pane
        BorderPane previewPane = createPreviewPane();

        // create allergies and seizures pane
        HBox allergiesAndSeizuresPane = createAllergiesAndSeizuresInfoPane();

        // add preview pane and the allergies and seizures pane
        hbox.getChildren().addAll(previewPane, allergiesAndSeizuresPane);

        return hbox;
    }

    /**
     * Purpose: Create the Preview Pane
     * 
     * @return BorderPane: create the preview pane for the basic information
     */
    private BorderPane createPreviewPane()
    {
        // create border pane
        BorderPane previewPane = new BorderPane();

        // create picture box for left side of preview pane
        VBox pictureBox = new VBox(3);
        // default preview picture
        // URL url = getClass().getResource("../images/defaultPicture.png");
        previewPicture = new ImageView(new Image("images/defaultPicture.png"));

        // set margins
        VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));

        // add picture and button to picture box

        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setStyle("-fx-background-color: #FFFFFF;");
        pictureBox.setAlignment(Pos.TOP_CENTER);

        // /Add the status to the picture box
        // /create the hbox to store status information
        HBox statusBox = new HBox();
        // /get the current status for the current participant
        ResultSet statusResults = DBObject.select("participantStatus",
                "Participant", "cosmoID =" + this.cosmoID, "");
        // /Label for the new status field
        Label statusLabel = new Label("Status:");
        // /set the text to the current status
        String statusString = "";
        try
        {
            while ( statusResults.next() )
            {
                statusString = statusResults.getString(1);
            }
        }
        catch ( SQLException e1 )
        {
            e1.printStackTrace();
        }
        statusText.setText(statusString);

        // /set the padding for the status label and text
        statusText.setPadding(new Insets(5, 5, 5, 5));
        statusLabel.setPadding(new Insets(5, 5, 5, 5));

        // /add the label and text to the status box
        statusBox.getChildren().addAll(statusLabel, statusText);
        statusBox.setPadding(new Insets(0, 0, 0, 15));

        // create buttons
        Button editBtn = new Button();
        // Button viewDocumentsBtn = new Button("View \nAttached \nDocuments");
        // Button generateFormsBtn = new Button("Generate Forms");
        Button createNoteBtn = new Button("Create Note");

        createNoteBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // search handler
                createNoteStage = new Stage();
                createNoteStage.setTitle("Create Note");

                createNoteStage.setScene(new Scene(NotePopUp.createNotePopUp(
                        cosmoID, loggedInUser.GetUsername(), createNoteStage),
                        300, 200));
                createNoteStage.initModality(Modality.APPLICATION_MODAL);
                createNoteStage.initOwner(participantMainStage);
                createNoteStage.setResizable(false);
                createNoteStage.showAndWait();
            }

        });
        // set sizes and padding
        editBtn.setMaxWidth(30);
        editBtn.setMinWidth(30);
        editBtn.setMaxHeight(25);
        editBtn.setMaxHeight(25);
        editBtn.setGraphic(new ImageView(new Image("images/edit.png")));

        /*
         * viewDocumentsBtn.setMaxWidth(100); viewDocumentsBtn.setMinWidth(100);
         * viewDocumentsBtn.setMinHeight(60); viewDocumentsBtn.setMaxHeight(60);
         * 
         * generateFormsBtn.setMaxWidth(100); generateFormsBtn.setMinWidth(100);
         */

        createNoteBtn.setMaxWidth(100);
        createNoteBtn.setMinWidth(100);

        // create basic info pane
        GridPane basicInfoPane = new GridPane();
        basicInfoPane.setStyle("-fx-background-color: #FFFFFF;");
        basicInfoPane.setPadding(new Insets(10, 10, 0, 10));

        // set basic labels
        Label cosmoIDLabel = new Label("CosmoID:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name: ");
        Label dayOfBirthLabel = new Label("Date of Birth: ");
        Label phnLabel = new Label("PHN: ");
        Label addressLabel = new Label("Address: ");
        Label cityLabel = new Label("City: ");
        Label postalLabel = new Label("Postal Code: ");
        Label phoneLabel = new Label("Phone Number: ");
        Label sinLabel = new Label("SIN: ");
        Label careLabel = new Label("Care Type: ");

        // use width to made container large enough
        cosmoIDLabel.setMinWidth(100);

        // set label margins
        cosmoIDLabel.setPadding(new Insets(5, 5, 5, 5));
        // /margins for the new status label
        statusLabel.setPadding(new Insets(5, 5, 5, 5));
        firstNameLabel.setPadding(new Insets(5, 5, 5, 5));
        lastNameLabel.setPadding(new Insets(5, 5, 5, 5));
        phnLabel.setPadding(new Insets(5, 5, 5, 5));
        dayOfBirthLabel.setPadding(new Insets(5, 5, 5, 5));
        addressLabel.setPadding(new Insets(5, 5, 5, 5));
        cityLabel.setPadding(new Insets(5, 5, 5, 5));
        postalLabel.setPadding(new Insets(5, 5, 5, 5));
        phoneLabel.setPadding(new Insets(5, 5, 5, 5));
        sinLabel.setPadding(new Insets(5, 5, 5, 5));
        careLabel.setPadding(new Insets(5, 5, 5, 5));
        // / get participant name, phn, diagnosis, and address from database
        ResultSet results = DBObject
                .select("firstName, lastName, dateOfBirth, personalHealthNumber, conditionName,"
                        + "description, address, imagePath, phoneNumber, city, postalCode, socialInsuranceNumber, careType",
                        "Participant p LEFT OUTER JOIN Conditions c ON p.cosmoID = c.cosmoID",
                        "cosmoID =" + this.cosmoID, "");

        try
        {
            // while there are more results

            while ( results.next() )
            {
                // get the participants basic information from the databases
                System.out.println("Results: " + results.getString(1));
                cosmoIDText.setText(this.cosmoID + "");
                firstNameText.setText(results.getString(1));
                lastNameText.setText(results.getString(2));

                DateFormat format = new SimpleDateFormat("dd-MMM-YYYY");

                dayOfBirthText.setText(format.format(results.getTimestamp(3)));
                healthNumText.setText(results.getString(4) + "");
                addressText.setText(results.getString(7) + "");
                phoneNumberText.setText(results.getString(9) + "");
                cityText.setText(results.getString(10) + "");
                postalText.setText(results.getString(11) + "");
                sinText.setText(results.getString(12) + "");
                careText.setText(results.getString(13) + "");
                URL u = null;
                try
                {
                    u = (this.getClass().getProtectionDomain().getCodeSource()
                            .getLocation().toURI().toURL());
                }
                catch ( URISyntaxException | MalformedURLException e )
                {
                    e.printStackTrace();
                }

                String urlPic = u.toString();

                urlPic = urlPic.substring(0, urlPic.length()
                        - (urlPic.length() - urlPic.lastIndexOf("/")));

                // set margins
                VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));
                urlPic = urlPic.replace("/bin", "");

                Image img = new Image(urlPic + results.getString(8));

                System.out.println(urlPic + results.getString(8));

                previewPicture = new ImageView(img);
                previewPicture.setFitWidth(122);
                previewPicture.setFitHeight(121);

                pictureBox.getChildren().addAll(previewPicture);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        pictureBox.getChildren().addAll(statusBox, createNoteBtn);

        cosmoIDText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        cosmoIDText.setMinWidth(PREVIEW_TEXT_WIDTH);

        firstNameText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        firstNameText.setMinWidth(PREVIEW_TEXT_WIDTH);

        lastNameText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        lastNameText.setMinWidth(PREVIEW_TEXT_WIDTH);

        dayOfBirthText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        dayOfBirthText.setMinWidth(PREVIEW_TEXT_WIDTH);

        healthNumText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        healthNumText.setMinWidth(PREVIEW_TEXT_WIDTH);

        addressText.setMaxWidth(PREVIEW_TEXT_WIDTH);
        addressText.setMinWidth(PREVIEW_TEXT_WIDTH);

        // add all labels to the gridpane
        basicInfoPane.add(cosmoIDLabel, 0, 0);
        basicInfoPane.add(firstNameLabel, 0, 2);
        basicInfoPane.add(lastNameLabel, 0, 3);
        basicInfoPane.add(dayOfBirthLabel, 0, 4);
        basicInfoPane.add(phnLabel, 0, 5);
        basicInfoPane.add(addressLabel, 0, 6);
        basicInfoPane.add(phoneLabel, 0, 7);
        basicInfoPane.add(cityLabel, 0, 8);
        basicInfoPane.add(postalLabel, 0, 9);
        basicInfoPane.add(sinLabel, 0, 10);
        basicInfoPane.add(careLabel, 0, 11);

        basicInfoPane.add(cosmoIDText, 1, 0);
        basicInfoPane.add(firstNameText, 1, 2);
        basicInfoPane.add(lastNameText, 1, 3);
        basicInfoPane.add(dayOfBirthText, 1, 4);
        basicInfoPane.add(healthNumText, 1, 5);
        basicInfoPane.add(addressText, 1, 6);
        basicInfoPane.add(phoneNumberText, 1, 7);
        basicInfoPane.add(cityText, 1, 8);
        basicInfoPane.add(postalText, 1, 9);
        basicInfoPane.add(sinText, 1, 10);
        basicInfoPane.add(careText, 1, 11);

        // add buttons to the previewPane
        if ( loggedInUser instanceof MedicalAdministrator )
        {
            basicInfoPane.add(editBtn, 2, 0);
            // pictureBox.getChildren().add(editBtn);
        }

        // set margins
        BorderPane.setMargin(pictureBox, new Insets(10, 0, 0, 10));
        BorderPane.setMargin(basicInfoPane, new Insets(10, 0, 0, 0));
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        editBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // Open addNewParticipant Window
                mainEditWindow = new Stage();
                mainEditWindow.setTitle("Edit Participant");

                mainEditWindow.setScene(new Scene(editParticipantPopUp(), 290,
                        440));
                mainEditWindow.initModality(Modality.APPLICATION_MODAL);
                mainEditWindow.initOwner(participantMainStage);
                mainEditWindow.setResizable(false);
                mainEditWindow.show();
            }

        });

        return previewPane;
    }

    /**
     * 
     * Purpose: Create Main VBox
     * 
     * @return VBox main VBox
     */
    private VBox createMainVBox()
    {
        VBox vbox = new VBox();

        // header HBox
        BorderPane header = createHBoxHeader();
        // tab pane
        TabPane tabs = createTabs();
        // preview notes
        HBox previewNotes = createHBoxPreviewDetails();

        // add everything to vbox
        vbox.getChildren().addAll(header, previewNotes, tabs);

        return vbox;
    }

    /**
     * 
     * Purpose: Create the pane containing the information for a participants
     * allergies and seizures
     * 
     * @return HBox allergies and seizures information pane
     */
    private HBox createAllergiesAndSeizuresInfoPane()
    {
        HBox hbox = new HBox();

        // allergies and seizures display pane
        VBox allergiesAndSeizuresPane = new VBox();

        allergiesAndSeizuresPane.setStyle("-fx-background-color: #FFFFFF;");
        allergiesAndSeizuresPane.setPadding(new Insets(10, 10, 0, 10));

        ScrollPane allergiesDescBox = fetchAndFormatAllergyInfo();
        ScrollPane seizuresDescBox = fetchAndFormatSeizureInfo();

        Label allergiesLabel = new Label("Allergies:");
        Label seizuresLabel = new Label("Seizures:");

        allergiesAndSeizuresPane.getChildren().addAll(allergiesLabel,
                allergiesDescBox, seizuresLabel, seizuresDescBox);

        // set minimum width
        allergiesAndSeizuresPane.setMinWidth(265);
        // Sets the notebox's width to fit that of the parents window when it is
        // resized
        allergiesAndSeizuresPane.prefWidthProperty().bind(
                participantMainStage.widthProperty().divide(1.50));
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.getChildren().addAll(allergiesAndSeizuresPane);

        return hbox;
    }

    /**
     * Purpose: queries the database for the participants allergy information,
     * and puts it into the scrollpane to be viewed.
     * 
     * @return ScrollPane the allergy pane
     */
    private ScrollPane fetchAndFormatAllergyInfo()
    {

        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();

        scrollPane.setMinHeight(120);
        vbox.setMinHeight(100);

        // finds all allergy information related to the participant
        ResultSet rs = DBObject.select("allergicTo, allergyType, description",
                "Allergies", "cosmoID = " + this.cosmoID, "");

        // whether records matching have been found
        boolean hasRecords = false;

        try
        {
            // while there are more results
            while ( rs.next() )
            {
                hasRecords = true;
                // get information from result and display it in the scrollpane
                Label allergen = new Label("Allergen: " + rs.getString(1));
                allergen.setWrapText(true);
                allergen.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                allergen.setMaxWidth(vbox.getWidth());
                Label type = new Label("Type: " + rs.getString(2));
                type.setWrapText(true);
                type.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                type.setMaxWidth(vbox.getWidth());
                Label desc = new Label("Description: " + rs.getString(3)
                        + "\n\n");
                desc.setWrapText(true);
                desc.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                desc.setMaxWidth(vbox.getWidth());

                vbox.getChildren().addAll(allergen, type, desc);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        // if no records were found, display that there were no records
        if ( !hasRecords )
        {
            vbox.getChildren().add(new Label("None"));
        }

        scrollPane.setContent(vbox);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setHmax(vbox.getWidth());

        return scrollPane;
    }

    /**
     * Purpose: Uses the database to fetch the required seizure information
     * about the participants and then display the data
     * 
     * @return scrollPane - the pane holding all of the participants seizure
     *         info in labels
     */
    private ScrollPane fetchAndFormatSeizureInfo()
    {
        VBox vbox = new VBox();

        ScrollPane scrollPane = new ScrollPane();

        // maintain the size of the vbox
        vbox.setMinHeight(100);

        // select statement responsible for fetching the required seizure
        // information
        ResultSet rs = DBObject.select(
                "seizureType, description, frequency, duration, aftermath",
                "Seizures", "cosmoID = " + this.cosmoID, "");

        // Whether or not there are any record to be returned
        boolean hasRecords = false;

        try
        {
            while ( rs.next() )
            {
                hasRecords = true;
                Label seizureType = new Label("Seizure Type: "
                        + rs.getString(1));
                seizureType.setWrapText(true);
                seizureType.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                seizureType.setMaxWidth(vbox.getWidth());
                Label seizureDesc = new Label("Description:  "
                        + rs.getString(2));
                seizureDesc.setWrapText(true);
                seizureDesc.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                seizureDesc.setMaxWidth(vbox.getWidth());
                Label seizureFreq = new Label("Frequency: " + rs.getString(3));
                seizureFreq.setWrapText(true);
                seizureFreq.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                seizureFreq.setMaxWidth(vbox.getWidth());
                Label seizureDuration = new Label("Duration: "
                        + rs.getString(4));
                seizureDuration.setWrapText(true);
                seizureDuration.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                seizureDuration.setMaxWidth(vbox.getWidth());
                Label seizureAfter = new Label("Aftermath: " + rs.getString(5));
                seizureAfter.setWrapText(true);
                seizureAfter.setMinWidth(ALLERGY_AND_SEIZURE_MIN_WITH);
                seizureAfter.setMaxWidth(vbox.getWidth());

                // add the labels to the parent container
                vbox.getChildren().addAll(seizureType, seizureDesc,
                        seizureFreq, seizureDuration, seizureAfter);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        if ( !hasRecords )
        {
            // if no record are found display "None"
            vbox.getChildren().add(new Label("None"));
        }
        // attach the vbox to its parent container (scroll pane)
        scrollPane.setContent(vbox);
        // remove Horizontal scroll bar
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        // add vertical scroll bar
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        // maintain width of the scroll pane
        scrollPane.setHmax(vbox.getWidth());

        return scrollPane;

    }

    /**
     * Purpose: Creates the "medications" tab which will hold the medications
     * information for the specified participant
     * 
     * @return the scroll pane containing the required medication information
     */
    private ScrollPane createMedicationsTab()
    {
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();

        Label medicationName = new Label();
        Label dosage = new Label();
        Label timesGiven = new Label();
        Label reason = new Label();

        // select statement responsible for fetching the required medication
        // information
        ResultSet rs = DBObject.select(
                "medicationName, dosage, timesGiven, reason", "Medication",
                "cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                medicationName.setText("Medication Name: " + rs.getString(1));

                dosage.setText("Dosage: " + rs.getString(2));

                timesGiven.setText("Times Given: " + rs.getString(3));

                reason.setText("Reason: " + rs.getString(4));

                // add the labels to the parent Vbox container
                vbox.getChildren().addAll(medicationName, dosage, timesGiven,
                        reason);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        // set the vbox content to its parent container (scroll pane)
        scrollPane.setContent(vbox);

        return scrollPane;
    }

    /**
     * Purpose: creates the tab that contains the information about vaccinations
     * 
     * @return scrollPane holding all of the specified participants vaccination
     *         information
     */
    private ScrollPane createVaccinationDetailsTab()
    {
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();

        // select statement responsible for fetching the required vaccination
        // information
        ResultSet rs = DBObject.select("vaccinationName, dateOFVaccination",
                "Vaccination", "cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                // add the vaccination details in the form of a label to the
                // parent container (scrollPane)
                vbox.getChildren().addAll(
                        new Label("Vaccination Name: " + rs.getString(1)),
                        new Label("Date given: " + rs.getString(2) + "\n\n"));
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        scrollPane.setContent(vbox);

        return scrollPane;
    }

    /**
     * Purpose: Creates the "other" tab which will hold information such as
     * agency and landlord information
     * 
     * @return hbox holding all of the "other" information in the form of
     *         labels.
     */
    private HBox createOtherTab()
    {

        HBox hbox = new HBox();

        ResultSet rs;

        VBox agencyBox = new VBox();

        Label name = new Label();
        Label clsd = new Label();
        Label funding = new Label();

        agencyBox.getChildren().add(new Label("Agency Information: "));

        // select statement responsible for fetching the required agency and
        // landlord information
        rs = DBObject.select("agencyName, clsd, funding",
                "Agency a JOIN Participant p ON a.agencyID = p.agencyID",
                "cosmoID =" + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                name.setText("Name: " + rs.getString(1));
                clsd.setText("CLSD: " + rs.getString(2));
                funding.setText("Funding: " + rs.getString(3));
                // add the labels to the parent container (hbox)
                agencyBox.getChildren().addAll(name, clsd, funding);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out
                    .println("Error attempting to fetch agency information for the participant");
        }

        VBox landlordBox = new VBox();

        landlordBox.getChildren().add(new Label("Landllord: "));

        Label landlordName = new Label();
        Label landlordAddress = new Label();
        Label landlordCity = new Label();
        Label landlordProv = new Label();
        Label landLordPostal = new Label();
        Label landlordPhone = new Label();

        // select statement responsible for fetching the required landlord
        // information
        rs = DBObject.select(
                "firstName, address, city, prov, postalCode, phoneNumber",
                "Landlord l JOIN Participant p ON l.landlordID = p.landlordID",
                "p.cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                landlordName.setText("Name: " + rs.getString(1));
                landlordAddress.setText("Address: " + rs.getString(2));
                landlordCity.setText("City: " + rs.getString(3));
                landlordProv.setText("Province: " + rs.getString(4));
                landLordPostal.setText("Postal Code: " + rs.getString(5));
                landlordPhone.setText("Phone: " + rs.getString(6));
                // add the landlord information labels to the hbox
                landlordBox.getChildren().addAll(landlordName, landlordAddress,
                        landlordCity, landlordProv, landLordPostal,
                        landlordPhone);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Error fetching landlord information from DB");
        }

        // add the final landlord and angecy text boxes to the hbox.
        hbox.getChildren().addAll(agencyBox, landlordBox);

        return hbox;
    }

    /**
     * Purpose: Create the labels which will hold the specified "kin"
     * information for the selected participant
     * 
     * @return kinBox
     */
    private VBox createKinDetailsTab()
    {

        VBox kinBox = new VBox();

        Label kinName = new Label();
        Label kinAddress = new Label();
        Label kinCity = new Label();
        Label kinProv = new Label();
        Label kinPostal = new Label();
        Label kinPhone = new Label();

        // select statement responsible for fetching the required kin
        // information
        ResultSet rs = DBObject
                .select("firstName, lastName, address, city, prov, postalCode, homePhoneNumber",
                        "Kin k JOIN Participant p ON k.kinID = p.kinID",
                        "cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                kinName.setText("Name: " + rs.getString(1) + " "
                        + rs.getString(2));
                kinAddress.setText("Address: " + rs.getString(3));
                kinCity.setText("City: " + rs.getString(4));
                kinProv.setText("Province: " + rs.getString(5));
                kinPostal.setText("Postal Code: " + rs.getString(6));
                kinPhone.setText("Phone: " + rs.getString(7) + "\n\n");
                // add all kin information in the form of labels to the parent
                // kinBox
                kinBox.getChildren().addAll(kinName, kinAddress, kinCity,
                        kinProv, kinPostal, kinPhone);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out
                    .println("Error trying to fetch Kin information from DB.");
        }

        return kinBox;
    }

    /**
     * Purpose: Create all of the labels which will store the work details
     * information
     * 
     * @return workBox
     */
    private VBox createWorkDetailsTab()
    {
        VBox workBox = new VBox();

        Label workArea = new Label();
        Label workFullTime = new Label();
        Label workAM = new Label();
        Label workPM = new Label();
        // select statement responsible for fetching the required work details
        // information
        ResultSet rs = DBObject.select("workArea, fullTime, am, pm",
                "WorkDetails w JOIN Participant p ON w.workID = p.workID",
                "cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                workArea.setText("Work Area: " + rs.getString(1));
                workFullTime.setText("Full Time: " + rs.getString(2));
                workAM.setText("AM: " + rs.getString(3));
                workPM.setText("PM: " + rs.getString(4) + "\n\n");

                // add the labels which will be holding the required work
                // details
                // information to the parent container (workBox)
                workBox.getChildren().addAll(workArea, workFullTime, workAM,
                        workPM);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out
                    .println("Error trying to fetch information for the participant's work details.");
        }

        return workBox;

    }

    /**
     * Purpose: Create the container which will hold all of the physician
     * information in the form of labels
     * 
     * @return PhysicianBox
     */
    private VBox createPhysicianInfoTab()
    {
        VBox physicianBox = new VBox();

        Label physicianName = new Label();
        Label physicianPhone = new Label();

        // select statement responsible for fetching the required physician
        // information
        ResultSet rs = DBObject
                .select("firstName, lastName, phone",
                        "Physician ph JOIN Participant pa ON ph.physicianID = pa.physicianID",
                        "pa.cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                physicianName.setText("Name: " + rs.getString(1) + " "
                        + rs.getString(2));
                physicianPhone.setText("Phone: " + rs.getString(3) + "\n\n");

                // add the required physician information to the parent
                // container
                physicianBox.getChildren()
                        .addAll(physicianName, physicianPhone);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out
                    .println("Error attempting to fetch physician information for"
                            + " participant.");
        }

        return physicianBox;
    }

    /**
     * Purpose: create the box which will hold all of the specified caregiver
     * information for the selected participant
     * 
     * @return: careGiver
     */
    private VBox createCaregiverTab()
    {
        VBox careGiverBox = new VBox();

        Label careGiverName = new Label();
        Label careGivenerPhone = new Label();

        careGiverBox.getChildren().add(new Label("Caregiver:"));
        // select statement responsible for fetching the required careGiver
        // information
        ResultSet rs = DBObject
                .select("firstName, lastName, homePhoneNumber",
                        "Caregiver c JOIN Participant p ON c.caregiverID = p.caregiverID",
                        "cosmoID = " + this.cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                careGiverName.setText("Name: " + rs.getString(1) + " "
                        + rs.getString(2));
                careGivenerPhone.setText("Phone: " + rs.getString(3) + "\n\n");
                // Add labels to the parent container.
                careGiverBox.getChildren().addAll(careGiverName,
                        careGivenerPhone);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out.println("Error getting caregiver information from DB.");
        }

        return careGiverBox;
    }

    /**
     * Purpose: To create a pop up window that allows the user to edit a
     * participants basic information
     * 
     * @return GridPane
     */
    private GridPane editParticipantPopUp()
    {

        GridPane grid = new GridPane();

        // warning label
        Label lblWarning = new Label();
        lblWarning.setTextFill(Color.FIREBRICK);

        // text field labels
        Label firstNameLbl = new Label("First Name");
        Label lastNameLbl = new Label("Last Name");
        Label birthdateLbl = new Label("Date Of Birth");
        Label healthNumLbl = new Label("PHN");
        Label addressLbl = new Label("Address");
        Label phoneNumLbl = new Label("Phone Number");
        Label cityLbl = new Label("City");
        Label postalCodeLbl = new Label("Postal Code");
        Label sinLbl = new Label("SIN");
        Label careLbl = new Label("Care Type");
        // /Label to display the participant status
        Label statusLabel = new Label("Status:");

        // the text fields
        TextField firstNameTxt = new TextField(firstNameText.getText());

        TextField lastNameTxt = new TextField(lastNameText.getText());
        DatePicker birthDatePicker = new DatePicker();

        ResultSet results = DBObject.select("dateOfBirth", "Participant",
                "cosmoID =" + this.cosmoID, "");
        String birthDay = "";
        try
        {
            // while there are more results

            while ( results.next() )
            {
                birthDay = results.getString(1);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        // convert the dobtext into a localdate
        int yearEnd = birthDay.indexOf('-');
        int year = Integer.parseInt(birthDay.substring(0, yearEnd));
        birthDay = birthDay.substring(yearEnd + 1);
        int monthEnd = birthDay.indexOf('-');
        int month = Integer.parseInt(birthDay.substring(0, monthEnd));
        birthDay = birthDay.substring(monthEnd + 1);
        int day = Integer.parseInt(birthDay.substring(0, 2));
        LocalDate ld = LocalDate.of(year, month, day);

        // change the pattern of the birthDatePicker to dd-MMM-yyyy
        String pattern = "dd-MMM-yyyy";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>()
        {
            DateTimeFormatter dateFormatter = DateTimeFormatter
                    .ofPattern(pattern);

            @Override
            public String toString( LocalDate date )
            {
                if ( date != null )
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString( String string )
            {
                if ( string != null && !string.isEmpty() )
                {
                    return LocalDate.parse(string, dateFormatter);
                }
                else
                {
                    return null;
                }
            }
        };
        birthDatePicker.setConverter(converter);
        birthDatePicker.setPromptText(pattern.toLowerCase());

        // set the value of the birth date picker
        birthDatePicker.setValue(ld);

        // /The combo box to select the participant status.(Active, Inactive,
        // Deceased)
        ObservableList<String> status = FXCollections.observableArrayList(
                "Active", "Inactive", "Deceased");
        final ComboBox<String> statusCombo = new ComboBox<String>(status);

        // set the health num
        TextField healthNumTxt = new TextField(healthNumText.getText());

        // address text
        TextField addressTxt = new TextField(addressText.getText());

        // Phone Number text
        TextField phoneNumTxt = new TextField(phoneNumberText.getText());

        // City text
        TextField cityTxt = new TextField(cityText.getText());

        // Postal Text
        TextField postalTxt = new TextField(postalText.getText());

        // SIN Text
        TextField sinTxt = new TextField(sinText.getText());

        // care type
        TextField careTxt = new TextField(careText.getText());

        // /set the combobox value to the current status
        statusCombo.setValue(statusText.getText());

        // add the form to the grid
        grid.add(firstNameLbl, 0, 1);
        grid.add(lastNameLbl, 0, 2);
        grid.add(birthdateLbl, 0, 3);
        grid.add(healthNumLbl, 0, 4);
        grid.add(addressLbl, 0, 5);
        grid.add(phoneNumLbl, 0, 6);
        grid.add(cityLbl, 0, 7);
        grid.add(postalCodeLbl, 0, 8);
        grid.add(sinLbl, 0, 9);
        grid.add(careLbl, 0, 10);
        // /Add the status label to the gui
        grid.add(statusLabel, 0, 11);

        grid.add(lblWarning, 1, 0);

        grid.add(firstNameTxt, 1, 1);
        grid.add(lastNameTxt, 1, 2);
        grid.add(birthDatePicker, 1, 3);
        grid.add(healthNumTxt, 1, 4);
        grid.add(addressTxt, 1, 5);
        grid.add(phoneNumTxt, 1, 6);
        grid.add(cityTxt, 1, 7);
        grid.add(postalTxt, 1, 8);
        grid.add(sinTxt, 1, 9);
        grid.add(careTxt, 1, 10);
        // /add the combobox to the GUI
        grid.add(statusCombo, 1, 11);

        // setPadding of the grid
        grid.setPadding(new Insets(10, 10, 0, 10));
        grid.setHgap(5);
        grid.setVgap(10);

        // Adding participant event handler
        Button editParticipantBtn = new Button("Save");
        editParticipantBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent e )
            {
                // call create participant on medical administrator with the
                // text passed in
                String result = MedicalAdministrator.editParticipant(
                        cosmoIDText.getText(), firstNameTxt.getText(),
                        lastNameTxt.getText(), birthDatePicker.getValue(),
                        healthNumTxt.getText(), addressTxt.getText(),
                        phoneNumTxt.getText(), cityTxt.getText(),
                        postalTxt.getText(), sinTxt.getText(),
                        careTxt.getText(), statusCombo.getValue().toString());

                // if no error message is recieved then close this window and
                // refresh the table
                if ( result.equals("") )
                {
                    mainEditWindow.close();

                    // set the pattern of the date coming in
                    LocalDate date = birthDatePicker.getValue();
                    String birthDateString = date.format(DateTimeFormatter
                            .ofPattern("dd-MM-yyyy"));

                    // check for changed data
                    checkForChanges(firstNameText, firstNameTxt.getText(),
                            "First Name", cosmoIDText.getText());

                    checkForChanges(lastNameText, lastNameTxt.getText(),
                            "Last Name", cosmoIDText.getText());

                    checkForChanges(dayOfBirthText, birthDateString,
                            "Birth Date", cosmoIDText.getText());

                    checkForChanges(healthNumText, healthNumTxt.getText(),
                            "Health Number", cosmoIDText.getText());

                    checkForChanges(addressText, addressTxt.getText(),
                            "Address", cosmoIDText.getText());

                    checkForChanges(phoneNumberText, phoneNumTxt.getText(),
                            "Phone Number", cosmoIDText.getText());

                    FormatHelper fh = new FormatHelper();
                    phoneNumTxt.setText(fh.formatPhoneNum(phoneNumberText
                            .getText()));

                    checkForChanges(cityText, cityTxt.getText(), "City",
                            cosmoIDText.getText());

                    checkForChanges(postalText, postalTxt.getText(),
                            "Postal Code", cosmoIDText.getText());

                    checkForChanges(sinText, sinTxt.getText(), "SIN",
                            cosmoIDText.getText());

                    checkForChanges(careText, careTxt.getText(), "Care Type",
                            cosmoIDText.getText());

                    checkForChanges(statusText, statusCombo.getValue()
                            .toString(), "Status", cosmoIDText.getText());

                }
                // if there is an error message, display it
                else
                {
                    lblWarning.setTextFill(Color.FIREBRICK);
                    lblWarning.setText(result);
                }

            }
        });

        // reset the form event handler
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle( ActionEvent arg0 )
            {
                // sets all values to default

                firstNameTxt.setText("");
                lastNameTxt.setText("");
                birthDatePicker.setValue(null);
                healthNumTxt.setText("");
                addressTxt.setText("");
                phoneNumTxt.setText("");
                cityTxt.setText("");
                postalTxt.setText("");
                sinTxt.setText("");
                careTxt.setText("");
                lblWarning.setText("");
            }

        });

        // Add the buttons to the grid
        HBox buttonsHbox = new HBox();
        HBox resetHbox = new HBox();
        buttonsHbox.getChildren().addAll(editParticipantBtn);
        buttonsHbox.setAlignment(Pos.CENTER);
        resetHbox.getChildren().addAll(resetBtn);
        resetHbox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonsHbox, 1, 12);
        grid.add(resetHbox, 0, 12);

        return grid;
    }

    /**
     * 
     * Purpose: To check if two values have changed and if so, update relevant
     * label on GUI and insert activity log entry into database
     * 
     * @param original - the original label that is to be checked against
     * @param changed - the submitted information
     * @param label - the label for the activity log entry if required
     * @param cosmoID - the cosmo ID of the participant being edited
     * 
     */
    private void checkForChanges( Label original, String changed, String label,
            String cosmoID )
    {
        String originalString = original.getText();
        if ( !changed.equals(originalString) )
        {
            DBObject.activtyLogEntry(loggedInUser.GetUsername(),
                    "Edited Participant (" + cosmoID + ")", label + ": \""
                            + originalString + "\" → \"" + changed + "\"");
            original.setText(changed);
        }
    }

}
