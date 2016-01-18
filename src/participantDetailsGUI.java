import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.WatchEvent.Kind;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * Purpose: To display the main medical staff page,
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class participantDetailsGUI extends Application
{
    
    private ParticipantTableViewController pTVCont;
    
    private DatabaseHelper dbObject = new DatabaseHelper();
    
    public static Stage participantMainStage;

    private ImageView previewPicture;

    private ListView<String> noteTitleView;

    private ObservableList<String> noteTitleList;
    
    private Stage createParticipantStage;
    
    private int cosmoID;
    
    private DatabaseHelper DBObject = new DatabaseHelper();
    

    /**
     * Purpose: displays the GUI
     * 
     */
    @Override
    public void start( Stage stage ) throws Exception
    {
        participantDetailsConstruct(stage, this.cosmoID);
    }

    /**
     * 
     * Purpose: Construct the main stage for the medical staff when they have
     * successfully logged in
     * 
     * @param stage: the stage the medical staff will see
     */
    public void participantDetailsConstruct( Stage stage, int cosmoID )
    {
        dbObject.connect();
        
        this.cosmoID = cosmoID;
        
        participantMainStage = stage;
        participantMainStage.setTitle("Cosmo Industries");

        VBox root = createMainVBox();

        participantMainStage.setScene(new Scene(root, 875, 580));
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
        Tab medications = new Tab();
        Tab vaccinationDetails = new Tab();
        Tab kinDetails = new Tab();
        Tab workDetails = new Tab();
        Tab physicianInfo = new Tab();
        Tab caregiver = new Tab();
        Tab other = new Tab();

        //set body for tabs
        medications.setContent(createMedicationsTab());
        vaccinationDetails.setContent(createVaccinationDetailsTab());
        kinDetails.setContent(createKinDetailsTab());
        workDetails.setContent(createWorkDetailsTab());
        physicianInfo.setContent(createPhysicianInfoTab());
        caregiver.setContent(createCaregiverTab());
        other.setContent(createOtherTab());
        
        // set text for tabs
        medications.setText("Medications");
        vaccinationDetails.setText("Vaccination Details");
        kinDetails.setText("Kin");
        workDetails.setText("Work Details");
        physicianInfo.setText("Physician");
        caregiver.setText("Caregiver");
        other.setText("Other");

        // set tabs to not be closable
        medications.closableProperty().set(false);
        vaccinationDetails.closableProperty().set(false);
        kinDetails.closableProperty().set(false);
        workDetails.closableProperty().set(false);
        physicianInfo.closableProperty().set(false);
        caregiver.closableProperty().set(false);
        other.closableProperty().set(false);

        // set the size of the tabs and add to the pane
        tabPane.setTabMinWidth(100);
        tabPane.getTabs().addAll( medications, vaccinationDetails, kinDetails, physicianInfo,
        		workDetails, caregiver, other);
        tabPane.setMinHeight(29);

        return tabPane;
    }




	/**
     * 
     * Purpose: Create the HBox that will contain the Preview pane and the Note
     * pane
     * @return
     */
    private HBox createHBoxPreviewNotes()
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
        
        //create allergies and seizures pane
        HBox allergiesAndSeizuresPane = createAllergiesAndSeizuresInfoPane();

        // add preview pane and the allergies and seizures pane
        hbox.getChildren().addAll(previewPane, allergiesAndSeizuresPane);
        
        return hbox;
    }

    /**
     * Purpose: Create the Preview Pane
     * @return
     */
    private BorderPane createPreviewPane()
    {
        // create border pane
        BorderPane previewPane = new BorderPane();

        // create picture box for left side of preview pane
        VBox pictureBox = new VBox();
        // default preview picture
        URL url = getClass().getResource("images/defaultPicture.png");
        try
        {
            previewPicture = new ImageView(new Image(url.openStream()));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // set margins
        VBox.setMargin(previewPicture, new Insets(10, 10, 10, 10));

        // add picture and button to picture box
       
        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setStyle("-fx-background-color: #FFFFFF;");
        pictureBox.setAlignment(Pos.TOP_CENTER);
        
        //create buttons
        Button editBtn = new Button("Edit");
        Button viewDocumentsBtn = new Button("View \nAttached \nDocuments");
        Button generateFormsBtn = new Button("Generate Forms");
        
        //set sizes and padding
        editBtn.setMaxWidth(100);
        editBtn.setMinWidth(100);
        
        viewDocumentsBtn.setMaxWidth(100);
        viewDocumentsBtn.setMinWidth(100);
        viewDocumentsBtn.setMinHeight(60);
        viewDocumentsBtn.setMaxHeight(60);
        
        generateFormsBtn.setMaxWidth(100);
        generateFormsBtn.setMinWidth(100);
        
        
        

        // create basic info pane
        GridPane basicInfoPane = new GridPane();
        basicInfoPane.setStyle("-fx-background-color: #FFFFFF;");
        basicInfoPane.setPadding(new Insets(10, 10, 0, 10));
        

        // set basic labels
        Label cosmoIDLabel = new Label("CosmoID:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name: ");
        Label dobLabel = new Label("Date of Birth: ");
        Label phnLabel = new Label("PHN: ");
        Label diagnosislabel = new Label("Diagnosis: ");
        Label addressLabel = new Label("Address: ");
        
        cosmoIDLabel.setMinWidth(100);

        // set label margins
        cosmoIDLabel.setPadding(new Insets(5, 5, 5, 5));
        firstNameLabel.setPadding(new Insets(5, 5, 5, 5));
        lastNameLabel.setPadding(new Insets(5, 5, 5, 5));
        phnLabel.setPadding(new Insets(5,5,5,5));
        dobLabel.setPadding(new Insets(5,5,5,5));
        diagnosislabel.setPadding(new Insets(5,5,5,5));
        addressLabel.setPadding(new Insets(5,5,5,5));

        
        //get participant name, phn, diagnosis, and address from database
        ResultSet results = DBObject.select("firstName, lastName, dateOfBirth, personalHealthNumber, conditionName,"
        		+ "description, address, imagePath", "Participant, Condition", 
        		"cosmoID = 123", "");
        
        
        // set the participant Labels
        Label cosmoIDText = new Label();
        Label firstNameText = new Label();
        Label lastNameText = new Label();
        Label dobtext = new Label();
        Label phnText = new Label();
        Label diagnosisText = new Label();
        Label addressText = new Label();
        
        try {
			while(results.next())
			{
				System.out.println("Results: " + results.getString(1));
				cosmoIDText.setText(this.cosmoID + "");
				firstNameText.setText(results.getString(1));
				lastNameText.setText(results.getString(2));
				dobtext.setText(results.getString(3));
				phnText.setText(results.getString(4));
				diagnosisText.setText(results.getString(5) +  ", " + results.getString(6));
				addressText.setText(results.getString(7));
				
				URL path = getClass().getResource(results.getString(8));
				
				try {
					System.out.println(path.toExternalForm());
					previewPicture = new ImageView(new Image(path.openStream()));
					previewPicture.setFitWidth(122);
					previewPicture.setFitHeight(121);
					
					pictureBox.getChildren().addAll(previewPicture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        //add buttons to the previewPane
        pictureBox.getChildren().addAll(editBtn, viewDocumentsBtn, generateFormsBtn);

        cosmoIDText.setMaxWidth(150);
        cosmoIDText.setMinWidth(150);

        firstNameText.setMaxWidth(150);
        firstNameText.setMinWidth(150);

        lastNameText.setMaxWidth(150);
        lastNameText.setMinWidth(150);
        
        dobtext.setMaxWidth(150);
        dobtext.setMinWidth(150);
        
        phnText.setMaxWidth(150);
        phnText.setMinWidth(150);
        
        diagnosisText.setMaxWidth(150);
        diagnosisText.setMinWidth(150);
        
        addressText.setMaxWidth(150);
        addressText.setMinWidth(150);


        // add all labels to the gridpane
        basicInfoPane.add(cosmoIDLabel, 0, 0);
        basicInfoPane.add(firstNameLabel, 0, 1);
        basicInfoPane.add(lastNameLabel, 0, 2);
        basicInfoPane.add(dobLabel, 0, 3);
        basicInfoPane.add(phnLabel, 0, 4);
        basicInfoPane.add(diagnosislabel, 0, 5);
        basicInfoPane.add(addressLabel, 0, 6);

        basicInfoPane.add(cosmoIDText, 1, 0);
        basicInfoPane.add(firstNameText, 1, 1);
        basicInfoPane.add(lastNameText, 1, 2);
        basicInfoPane.add(dobtext,1,3);
        basicInfoPane.add(phnText, 1, 4);
        basicInfoPane.add(diagnosisText, 1, 5);
        basicInfoPane.add(addressText, 1, 6);

        // set margins
        BorderPane.setMargin(pictureBox, new Insets(10, 0, 0, 10));
        BorderPane.setMargin(basicInfoPane, new Insets(10, 0, 0, 0));
        previewPane.setLeft(pictureBox);
        previewPane.setCenter(basicInfoPane);

        return previewPane;
    }




    /**
     * 
     * Purpose: Create Main VBox
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
        HBox previewNotes = createHBoxPreviewNotes();


        // add everything to vbox
        vbox.getChildren().addAll(header, previewNotes, tabs);

        return vbox;
    }
    
    
	/**
     * 
     * Purpose: Create Note Box
     * 
     * @return HBox create note box
     */
    private HBox createAllergiesAndSeizuresInfoPane()
    {
        HBox hbox = new HBox();
       
        
        // note display pane
        VBox noteDisplayPane = new VBox();

        noteDisplayPane.setStyle("-fx-background-color: #FFFFFF;");
        noteDisplayPane.setPadding(new Insets(10, 10, 0, 10));

        
        ScrollPane allergiesDescBox = fetchAndFormatAllergyInfo();
        ScrollPane seizuresDescBox = fetchAndFormatSeizureInfo();
        
    	
    	Label allergiesLabel = new Label("Allergies:");
    	Label seizuresLabel = new Label("Seizures:");
        
        noteDisplayPane.getChildren().addAll(allergiesLabel, allergiesDescBox,
        		seizuresLabel, seizuresDescBox);
        
        // set minimum width
        noteDisplayPane.setMinWidth(265);
        //Sets the notebox's width to fit that of the parents window when it is resized
        noteDisplayPane.prefWidthProperty().bind(participantMainStage.widthProperty().divide(1.50));
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.getChildren().addAll(noteDisplayPane);

        return hbox;
    }
    
    private ScrollPane fetchAndFormatAllergyInfo()
    {
    	
    	ScrollPane scrollPane = new ScrollPane();
    	
    	VBox vbox = new VBox();
    	
    	scrollPane.setMinHeight(120);
    	vbox.setMinHeight(100);
    	
    	ResultSet rs = DBObject.select("allergicTo, allergyType, description",
    			"Allergies", "cosmoID = " + this.cosmoID, "");
    	
    	boolean hasRecords = false;
    	
    	try
    	{
    		while(rs.next())
    		{
    			hasRecords = true;
    			Label allergen = new Label("Allergen: " + rs.getString(1));
    			allergen.setWrapText(true);
    			allergen.setMinWidth(410);
    			allergen.setMaxWidth(vbox.getWidth());
    			Label type = new Label("Type: " + rs.getString(2));
    			type.setWrapText(true);
    			type.setMinWidth(410);
    			type.setMaxWidth(vbox.getWidth());
    			Label desc = new Label("Description: " + rs.getString(3) + "\n\n");
    			desc.setWrapText(true);
    			desc.setMinWidth(410);
    			desc.setMaxWidth(vbox.getWidth());
    			
    			vbox.getChildren().addAll(allergen, type, desc);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    	if(!hasRecords)
    	{
    		vbox.getChildren().add(new Label("None"));
    	}
    	
    	scrollPane.setContent(vbox);
    	scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    	scrollPane.setHmax(vbox.getWidth());
    	
    	return scrollPane;
    }
    
    private ScrollPane fetchAndFormatSeizureInfo()
    {
    	VBox vbox = new VBox();
    	ScrollPane scrollPane = new ScrollPane();
    	
    	vbox.setMinHeight(100);
    	
    	
    	
    	ResultSet rs = DBObject.select("seizureType, description, frequency, duration, aftermath, medicationName",
    			"Seizures", "cosmoID = " + this.cosmoID, "");
    	
    	boolean hasRecords = false;
    	
    	try
    	{
    		while(rs.next())
    		{
    			hasRecords = true;
    			Label seizureType = new Label("Seizure Type: " + rs.getString(1));
    			seizureType.setWrapText(true);
    			seizureType.setMinWidth(410); 
    			seizureType.setMaxWidth(vbox.getWidth());
    			Label seizureDesc = new Label("Description:  " + rs.getString(2));
    			seizureDesc.setWrapText(true);
    			seizureDesc.setMinWidth(410);
    			seizureDesc.setMaxWidth(vbox.getWidth());
    			Label seizureFreq = new Label("Frequency: " + rs.getString(3));
    			seizureFreq.setWrapText(true);
    			seizureFreq.setMinWidth(410);
    			seizureFreq.setMaxWidth(vbox.getWidth());
    			Label seizureDuration = new Label("Duration: " + rs.getString(4));
    			seizureDuration.setWrapText(true);
    			seizureDuration.setMinWidth(410);
    			seizureDuration.setMaxWidth(vbox.getWidth());
    			Label seizureAfter = new Label("Aftermath: " + rs.getString(5));
    			seizureAfter.setWrapText(true);
    			seizureAfter.setMinWidth(410);
    			seizureAfter.setMaxWidth(vbox.getWidth());
    			Label medicationName = new Label("Medication Name: " + rs.getString(6));
    			medicationName.setWrapText(true);
    			medicationName.setMinWidth(410);
    			medicationName.setMaxWidth(vbox.getWidth());
    			
    			
    			vbox.getChildren().addAll(seizureType, seizureDesc, seizureFreq, 
    					seizureDuration, seizureAfter, medicationName);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    	if(!hasRecords)
    	{
    		vbox.getChildren().add(new Label("None"));
    	}
    	
    	scrollPane.setContent(vbox);
    	scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    	scrollPane.setHmax(vbox.getWidth());
    	
    	
    	return scrollPane;
    	 
    
    }
    
    
    private ScrollPane createMedicalInfoTab()
    {
    	ScrollPane scrollPane = new ScrollPane();
    	//since all information we could put here is basically covered in
    	//other places on the GUI, maybe get rid of this? Unless you can
    	//find a use for it 
    	return scrollPane;
    }
    
    private ScrollPane createMedicationsTab()
    {
    	ScrollPane scrollPane = new ScrollPane();
    	
    	VBox vbox = new VBox();
    	
    	Label medicationName = new Label();
    	Label dosage = new Label();
    	Label timesGiven = new Label();
    	Label reason = new Label();
    	
    	ResultSet rs = DBObject.select("medicationName, dosage, timesGiven, reason",
    			"Medication", "cosmoID = " + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			medicationName.setText("Medication Name: " + rs.getString(1));
    			
    			dosage.setText("Dosage: " + rs.getString(2));
    			
    			timesGiven.setText("Times Given: " + rs.getString(3));
    			
    			reason.setText("Reason: " + rs.getString(4));
    			
    			vbox.getChildren().addAll(medicationName, dosage, timesGiven, reason);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    	scrollPane.setContent(vbox);
    	
    	return scrollPane;
    }
    
    
    private ScrollPane createVaccinationDetailsTab()
    {
    	ScrollPane scrollPane = new ScrollPane();
    	
    	VBox vbox = new VBox();
    	
    	Label vaccinationName = new Label();
    	Label dateOf = new Label();
    	
    	ResultSet rs = DBObject.select("vaccinationName, dateOFVaccination", 
    			"Vaccination", "cosmoID = " + this.cosmoID, "");
    	
    	
    	try
    	{
    		while(rs.next())
    		{
    			vaccinationName.setText("Vaccination Name: " + rs.getString(1));
    			
    			dateOf.setText("Date given: " + rs.getString(2) + "\n\n");
    			
    			vbox.getChildren().addAll(vaccinationName, dateOf);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    	
    	scrollPane.setContent(vbox);
    	
    	return scrollPane;
    }
    
    private HBox createOtherTab()
    {
    	
    	HBox hbox = new HBox();
    	
    	ResultSet rs;
    	
    	VBox agencyBox = new VBox();
    	
    	Label name = new Label();
    	Label clsd = new Label();
    	Label funding = new Label();
    	
    	agencyBox.getChildren().add(new Label("Agency Information: "));
    	
    	rs = DBObject.select("agencyName, clsd, funding", 
    			"Agency a JOIN Participant p ON a.agencyID = p.agencyID",
    			"cosmoID =" + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			name.setText("Name: " + rs.getString(1));
    			clsd.setText("CLSD: " + rs.getString(2));
    			funding.setText("Funding: " + rs.getString(3));
    			
    			agencyBox.getChildren().addAll(name, clsd, funding);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		System.out.println("Error attempting to fetch agency information for the participant");
    	}
    	
    	
    	VBox landlordBox = new VBox();
    	
    	landlordBox.getChildren().add(new Label("Landllord: "));
    	
    	Label landlordName = new Label();
    	Label landlordAddress = new Label();
    	Label landlordCity = new Label();
    	Label landlordProv = new Label();
    	Label landLordPostal = new Label();
    	Label landlordPhone = new Label();
    	
    	rs = DBObject.select("firstName, address, city, prov, postalCode, phoneNumber",
    			"Landlord l JOIN Participant p ON l.landlordID = p.landlordID", "p.cosmoID = " + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			landlordName.setText("Name: " + rs.getString(1));
    			landlordAddress.setText("Address: " + rs.getString(2));
    			landlordCity.setText("City: " + rs.getString(3));
    			landlordProv.setText("Province: " + rs.getString(4));
    			landLordPostal.setText("Postal Code: " + rs.getString(5));
    			landlordPhone.setText("Phone: " + rs.getString(6));
    			
    			landlordBox.getChildren().addAll(landlordName, landlordAddress, landlordCity,
    					landlordProv, landLordPostal, landlordPhone);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		System.out.println("Error fetching landlord information from DB");
    	}
  
    
    	hbox.getChildren().addAll(agencyBox, landlordBox);
    	
    	return hbox;
    }
    
    private VBox createKinDetailsTab() {
		
VBox kinBox = new VBox();
    	
    	Label kinName = new Label();
    	Label kinAddress = new Label();
    	Label kinCity = new Label();
    	Label kinProv = new Label();
    	Label kinPostal = new Label();
    	Label kinPhone = new Label();
    	
    	ResultSet rs = DBObject.select("firstName, lastName, address, city, prov, postalCode, phoneNumber",
    			"Kin k JOIN Participant p ON k.kinID = p.kinID", "cosmoID = " + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			kinName.setText("Name: " +  rs.getString(1) + " " + rs.getString(2));
    			kinAddress.setText("Address: " + rs.getString(3));
    			kinCity.setText("City: " + rs.getString(4));
    			kinProv.setText("Province: " + rs.getString(5));
    			kinPostal.setText("Postal Code: " + rs.getString(6));
    			kinPhone.setText("Phone: " + rs.getString(7) + "\n\n");
    			
    			kinBox.getChildren().addAll(kinName, kinAddress, kinCity, kinProv,
    					kinPostal, kinPhone);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		System.out.println("Error trying to fetch Kin information from DB.");
    	}
    	
    	
		return kinBox;
	}

	private VBox createWorkDetailsTab() {
		VBox workBox = new VBox();
    	
    	Label workArea = new Label();
    	Label workFullTime = new Label();
    	Label workAM = new Label();
    	Label workPM = new Label();
    	
    	ResultSet rs = DBObject.select("workArea, fullTime, am, pm", 
    			"WorkDetails w JOIN Participant p ON w.workID = p.workID", 
    			"cosmoID = " + this.cosmoID, "");
    	
    	
    	
    	try
    	{
    		while(rs.next())
    		{
    			workArea.setText("Work Area: " + rs.getString(1));
    			workFullTime.setText("Full Time: " + rs.getString(2));
    			workAM.setText("AM: " + rs.getString(3));
    			workPM.setText("PM: " + rs.getString(4) + "\n\n");
    			
    			workBox.getChildren().addAll(workArea, workFullTime, workAM, workPM);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		System.out.println("Error trying to fetch information for the participant's work details.");
    	}
    	
    	return workBox;
    	
	}


	private Node createPhysicianInfoTab() {
		VBox physicianBox = new VBox();
    	
    	Label physicianName = new Label();
    	Label physicianPhone = new Label();
    	
    	ResultSet rs = DBObject.select("firstName, lastName, phone", 
    			"Physician ph JOIN Participant pa ON ph.physicianID = pa.physicianID", 
    			"pa.cosmoID = " + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			physicianName.setText("Name: " + rs.getString(1) + " " + rs.getString(2));
    			physicianPhone.setText("Phone: " + rs.getString(3) + "\n\n");
    			physicianBox.getChildren().addAll(physicianName, physicianPhone);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		System.out.println("Error attempting to fetch physician information for"
    				+ " participant.");
    	}
    	
    	return physicianBox;
	}


	private VBox createCaregiverTab() {
		VBox careGiverBox = new VBox();
    	
    	Label careGiverName = new Label();
    	Label careGivenerPhone = new Label();
    	
    	careGiverBox.getChildren().add(new Label("Caregiver:"));
    	
    	ResultSet rs = DBObject.select("firstName, lastName, phone", 
    			"Caregiver c JOIN Participant p ON c.caregiverID = p.caregiverID",
    			"cosmoID = " + this.cosmoID, "");
    	
    	try
    	{
    		while(rs.next())
    		{
    			careGiverName.setText("Name: " + rs.getString(1) + " " + 
    					rs.getString(2));
    			careGivenerPhone.setText("Phone: " + rs.getString(3) + "\n\n");
    			
    			careGiverBox.getChildren().addAll(careGiverName, careGivenerPhone);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    		
    		System.out.println("Error getting caregiver information from DB.");
    	}
    	
    	return careGiverBox;
	}
}
