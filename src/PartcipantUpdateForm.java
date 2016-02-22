import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Purpose: This class will display the editable participant update form with 
 *          data from the database
 * 
 * @author Breanna Wilson cst215 Steven Palchinski cst209
 */
public class PartcipantUpdateForm extends ScrollPane
{
	//int to hold the cosmoId
    private int cosmoID;
    //the main vbox
	private VBox mainBox;
	//the save button for the form
	public Button saveBtn;
	//the controller instance
	private ParticipantUpdateFormController controller;
	
	//The TextFields for the participant information
	public TextField participantFirstNameTxt = new TextField();
	public TextField participantLastNameTxt = new TextField();
	public TextField participantAddressTxt = new TextField();
	public TextField participantCityTxt = new TextField();
	public TextField participantPostalCodeTxt = new TextField();
	public TextField participantPhoneNumberTxt = new TextField();
	public TextField participantBirthdateTxt = new TextField();
	public TextField participantSinTxt = new TextField();
	
	//The TextFields for the Kin Information
	public TextField kinFirstNameTxt = new TextField();
	public TextField kinLastNameTxt = new TextField();
	public TextField kinAddressTxt = new TextField();
	public TextField kinCityTxt = new TextField();
	public TextField kinPostalCodeTxt = new TextField();
	public TextField kinHomePhoneTxt = new TextField();
	public TextField kinWorkPhoneTxt = new TextField();
	
	//The TextFields for the Caregiver Information
	public TextField caregiverFirstNameTxt = new TextField();
	public TextField caregiverLastNameTxt = new TextField();
	public TextField caregiverAddressTxt = new TextField();
	public TextField caregiverCityTxt = new TextField();
	public TextField caregiverPostalCodeTxt = new TextField();
	public TextField caregiverHomePhoneTxt = new TextField();
	public TextField caregiverWorkPhoneTxt = new TextField();
	
	//The TextFields for the Emergency Contact Information
	public TextField emergencyContactFirstName = new TextField();
	public TextField emergencyContactLastName = new TextField();
	public TextField emergencyContactPhone = new TextField();
	
	/**
	 * Purpose: 
	 * @param cosmoID
	 */
	public PartcipantUpdateForm(int cosmoID)
	{
		this.cosmoID = cosmoID;
		mainBox = new VBox();
		controller = new ParticipantUpdateFormController(cosmoID);
		
		Separator line1 = new Separator();
		line1.minWidth(1500);
		Separator line2 = new Separator();
		line2.minWidth(1500);
		Separator line3 = new Separator();
		line3.minWidth(1500);
		
		Label participantInfoAreaHeader = new Label("Participant Information:");
		participantInfoAreaHeader.setFont(new Font("Arial", 16));
		
		Label kinInfoAreaHeader = new Label("Kin Information:");
		kinInfoAreaHeader.setFont(new Font("Arial", 16));
		
		Label caregiverInfoAreaHeader = new Label("Caregiver Information:");
		caregiverInfoAreaHeader.setFont(new Font("Arial", 16));
		
		Label emergencyContactInfoHeader = new Label("Emergency Contact Information:");
		emergencyContactInfoHeader.setFont(new Font("Arial", 16));
		
		mainBox.getChildren().addAll(createHeader(), participantInfoAreaHeader,
				createParticipantInfoArea(), line1, kinInfoAreaHeader,
				createKinInfoArea(), line2, caregiverInfoAreaHeader,
				createCaregiverInfoArea(), line3, emergencyContactInfoHeader,
				createEmergencyContactArea());
		
		this.fillParticipantText();
		this.fillKinText();
		this.fillCaregiverText();
		this.fillEmergencyText();
	}
	
	public ScrollPane getForm()
	{
		ScrollPane main = new ScrollPane();
		main.setContent(mainBox);
		return main;
	}
	
	private HBox createHeader()
	{
		HBox hbox = new HBox();
		
		Label heading = new Label("Participant Information Update Form");
		heading.setFont(new Font("Arial", 22));
		
		saveBtn = new Button("Save");
		
		saveBtn.setOnAction(event -> {
			saveInfo();
		});
		
		
		
		hbox.getChildren().addAll(heading, saveBtn);
		
		hbox.setMargin(heading, new Insets(5, 170,5,280));
		
		return hbox;
	}
	
	private GridPane createParticipantInfoArea()
	{
		GridPane mainPane = new GridPane();
		
		/*
		 * ADD PHN FIELD HERE
		 * 
		 */
		
		Label firstNameLbl = new Label("First Name:");
		Label lastNameLbl = new Label("Last Name:");
		Label addressLbl = new Label("Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneNumberLbl = new Label("Phone Number:");
		Label birthdateLbl = new Label("Birth Date:");
		Label sinLbl = new Label("SIN:");
		
		Insets insets = new Insets(5,5,5,5);
		
		firstNameLbl.setPadding(insets);
		lastNameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneNumberLbl.setPadding(insets);
		birthdateLbl.setPadding(insets);
		sinLbl.setPadding(insets);
		
		
		
		mainPane.add(firstNameLbl, 0, 0);
		mainPane.add(participantFirstNameTxt, 1, 0);
		mainPane.add(lastNameLbl, 0, 1);
		mainPane.add(participantLastNameTxt, 1, 1);
		mainPane.add(addressLbl, 0, 2);
		mainPane.add(participantAddressTxt, 1, 2);
		mainPane.add(cityLbl, 0, 3);
		mainPane.add(participantCityTxt, 1, 3);
		mainPane.add(postalCodeLbl, 0, 4);
		mainPane.add(participantPostalCodeTxt, 1, 4);
		mainPane.add(phoneNumberLbl, 3, 0);
		mainPane.add(participantPhoneNumberTxt, 4, 0);
		mainPane.add(birthdateLbl, 3, 1);
		mainPane.add(participantBirthdateTxt, 4, 1);
		mainPane.add(sinLbl, 3, 2);
		mainPane.add(participantSinTxt, 4, 2);
		
		mainPane.setMargin(participantFirstNameTxt, insets);
		mainPane.setMargin(participantLastNameTxt, insets);
		mainPane.setMargin(participantAddressTxt, insets);
		mainPane.setMargin(participantCityTxt, insets);
		mainPane.setMargin(participantPostalCodeTxt, insets);
		mainPane.setMargin(participantBirthdateTxt, insets);
		mainPane.setMargin(participantSinTxt, insets);
		mainPane.setMargin(participantPhoneNumberTxt, insets);
		
		mainPane.setPadding(new Insets(5,5,5,5));
		
		mainPane.alignmentProperty().set(Pos.CENTER);
		
		return mainPane;
	}

	
	private GridPane createKinInfoArea()
	{
		GridPane main = new GridPane();
		
		Label firstNameLbl = new Label("First Name:");
		Label lastNameLbl = new Label("Last Name:");
		Label addressLbl = new Label("Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneLbl = new Label("Phone Number:");
		Label homePhoneLbl = new Label("Home:");
		Label workPhoneLbl = new Label("Work:");
		
		Insets insets = new Insets(5,5,5,5);
		
		firstNameLbl.setPadding(insets);
		lastNameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneLbl.setPadding(insets);
		homePhoneLbl.setPadding(insets);
		workPhoneLbl.setPadding(insets);
		
		main.add(firstNameLbl, 0, 0);
		main.add(kinFirstNameTxt, 1, 0);
		main.add(lastNameLbl, 0, 1);
		main.add(kinLastNameTxt, 1, 1);
		main.add(addressLbl, 0, 2);
		main.add(kinAddressTxt, 1, 2);
		main.add(cityLbl, 0, 3);
		main.add(kinCityTxt, 1, 3);
		main.add(postalCodeLbl, 0, 4);
		main.add(kinPostalCodeTxt, 1, 4);
		main.add(phoneLbl, 2, 0);
		main.add(homePhoneLbl, 2, 1);
		main.add(kinHomePhoneTxt, 3, 1);
		main.add(workPhoneLbl, 2, 2);
		main.add(kinWorkPhoneTxt, 3, 2);
		
		main.setMargin(kinFirstNameTxt, insets);
		main.setMargin(kinLastNameTxt, insets);
		main.setMargin(kinAddressTxt, insets);
		main.setMargin(kinPostalCodeTxt, insets);
		main.setMargin(kinHomePhoneTxt, insets);
		main.setMargin(kinWorkPhoneTxt, insets);
		main.setMargin(kinCityTxt, insets);
		
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
		
	}
	
	private GridPane createCaregiverInfoArea()
	{
		GridPane main = new GridPane();
		
		Label firstNameLbl = new Label("First Name:");
		Label lastNameLbl = new Label("Last Name");
		Label addressLbl = new Label("Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneLbl = new Label("Phone Number:");
		Label homePhoneLbl = new Label("Home:");
		Label workPhoneLbl = new Label("Work:"); 
		
		Insets insets = new Insets(5,5,5,5);
		
		firstNameLbl.setPadding(insets);
		lastNameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneLbl.setPadding(insets);
		homePhoneLbl.setPadding(insets);
		workPhoneLbl.setPadding(insets);
		
		main.add(firstNameLbl, 0, 0);
		main.add(caregiverFirstNameTxt, 1, 0);
		main.add(lastNameLbl, 0, 1);
		main.add(caregiverLastNameTxt, 1, 1);
		main.add(addressLbl, 0, 2);
		main.add(caregiverAddressTxt, 1, 2);
		main.add(cityLbl, 0, 3);
		main.add(caregiverCityTxt, 1, 3);
		main.add(postalCodeLbl, 0, 4);
		main.add(caregiverPostalCodeTxt, 1, 4);
		main.add(phoneLbl, 2, 0);
		main.add(homePhoneLbl, 2, 1);
		main.add(caregiverHomePhoneTxt, 3, 1);
		main.add(workPhoneLbl, 2, 2);
		main.add(caregiverWorkPhoneTxt, 3, 2);
		
		main.setMargin(caregiverFirstNameTxt, insets);
		main.setMargin(caregiverLastNameTxt, insets);
		main.setMargin(caregiverAddressTxt, insets);
		main.setMargin(caregiverCityTxt, insets);
		main.setMargin(caregiverPostalCodeTxt, insets);
		main.setMargin(caregiverHomePhoneTxt, insets);
		main.setMargin(caregiverWorkPhoneTxt, insets);
		
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
	}
	
	private GridPane createEmergencyContactArea()
	{
		GridPane main = new GridPane();
		
		Label name = new Label("First Name:");
		Label lastName = new Label("Last Name");
		Label phone = new Label("Phone Number:");
		
		Insets insets = new Insets(5,5,5,5);
	
		name.setPadding(insets);
		lastName.setPadding(insets);
		phone.setPadding(insets);
		
		main.add(name, 0, 0);
		main.add(emergencyContactFirstName, 1, 0);
		main.add(lastName, 0, 1);
		main.add(emergencyContactLastName, 1, 1);
		main.add(phone, 2, 0);
		main.add(emergencyContactPhone, 3, 0);
		
		main.setMargin(emergencyContactFirstName, insets);
		main.setMargin(emergencyContactPhone, insets);
		main.setMargin(emergencyContactLastName, insets);
		
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
	}
	
	private void fillParticipantText()
	{
		String[] participantInfo = this.controller.fetchParticipantBasicInfo();
		
		participantFirstNameTxt.setText(participantInfo[0]);
		participantLastNameTxt.setText(participantInfo[1]);
		participantAddressTxt.setText(participantInfo[2]);
		participantCityTxt.setText(participantInfo[3]);
		participantPostalCodeTxt.setText(participantInfo[4]);
		participantPhoneNumberTxt.setText(participantInfo[5]);
		participantBirthdateTxt.setText(participantInfo[6]);
		participantSinTxt.setText(participantInfo[7]);
		
	}
	
	private void fillKinText()
	{
		String[] kinInfo = this.controller.fetchKinInfo();
		
		kinFirstNameTxt.setText(kinInfo[0]);
		kinLastNameTxt.setText(kinInfo[1]);
		kinAddressTxt.setText(kinInfo[2]);
		kinCityTxt.setText(kinInfo[3]);
		kinPostalCodeTxt.setText(kinInfo[4]);
		kinHomePhoneTxt.setText(kinInfo[5]);
		kinWorkPhoneTxt.setText(kinInfo[6]);
	}
	
	private void fillCaregiverText()
	{
		String[] cgInfo = this.controller.fetchCaregiverInfo();
		
		caregiverFirstNameTxt.setText(cgInfo[0]);
		caregiverLastNameTxt.setText(cgInfo[1]);
		caregiverAddressTxt.setText(cgInfo[2]);
		caregiverCityTxt.setText(cgInfo[3]);
		caregiverPostalCodeTxt.setText(cgInfo[4]);
		caregiverHomePhoneTxt.setText(cgInfo[5]);
		caregiverWorkPhoneTxt.setText(cgInfo[6]);
	}
	
	private void fillEmergencyText()
	{
		String[] eInfo = this.controller.fetchEmergencyContactInfo();
		
		emergencyContactFirstName.setText(eInfo[0]);
		emergencyContactLastName.setText(eInfo[1]);
		emergencyContactPhone.setText(eInfo[2]);
	}
	
	private void saveInfo()
	{
		//TODO have this save the information entered into
			// the database
		
	}
	
}
