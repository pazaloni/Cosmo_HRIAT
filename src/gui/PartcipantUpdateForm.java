package gui;
import java.awt.Color;



import controllers.ParticipantUpdateFormController;
import core.MedicalAdministrator;
import core.PopUpMessage;
import core.StaffAccount;
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
import javafx.scene.text.TextAlignment;import javafx.stage.Stage;


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
	
	//The TextFields for the Kin Information
	private TextField kinFirstNameTxt = new TextField();
	private TextField kinLastNameTxt = new TextField();
	private TextField kinAddressTxt = new TextField();
	private TextField kinCityTxt = new TextField();
	private TextField kinPostalCodeTxt = new TextField();
	private TextField kinHomePhoneTxt = new TextField();
	private TextField kinWorkPhoneTxt = new TextField();
	
	//The TextFields for the Caregiver Information
	private TextField caregiverFirstNameTxt = new TextField();
	private TextField caregiverLastNameTxt = new TextField();
	private TextField caregiverAddressTxt = new TextField();
	private TextField caregiverCityTxt = new TextField();
	private TextField caregiverPostalCodeTxt = new TextField();
	private TextField caregiverHomePhoneTxt = new TextField();
	private TextField caregiverWorkPhoneTxt = new TextField();
	
	//The TextFields for the Emergency Contact Information
	private TextField emergencyContactFirstName = new TextField();
	private TextField emergencyContactLastName = new TextField();
	private TextField emergencyContactPhone = new TextField();
	
	private StaffAccount loggedInUser;
	
	private Label saveMessage;
	
	/**
	 * Purpose: Creates the form, populated with information 
	 * related to the participant.
	 * 
	 * @param cosmoID The primary key for the participant
	 * to get information for.
	 *@author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public PartcipantUpdateForm(int cosmoID, StaffAccount loggedInUser)
	{
		this.cosmoID = cosmoID;
		this.loggedInUser = loggedInUser;
		//the VBox containing all other viewable nodes
		mainBox = new VBox();
		//the controller used to get information from the database
		controller = new ParticipantUpdateFormController(cosmoID);
		
		//the line separators to separate the sections of the form visually
		Separator line2 = new Separator();
		line2.minWidth(1500);
		Separator line3 = new Separator();
		line3.minWidth(1500);
		
		//label for the Kin Information Area of the form
		Label kinInfoAreaHeader = new Label("Kin Information:");
		kinInfoAreaHeader.setFont(new Font("Arial", 16));
		
		//label for the Caregiver Information Area of the form
		Label caregiverInfoAreaHeader = new Label("Caregiver Information:");
		caregiverInfoAreaHeader.setFont(new Font("Arial", 16));
		
		//label for the Emergency Contact Information Area of the form
		Label emergencyContactInfoHeader = new Label("Emergency Contact Information:");
		emergencyContactInfoHeader.setFont(new Font("Arial", 16));
		
		//add all viewable nodes to the main VBox
		mainBox.getChildren().addAll(createHeader(), kinInfoAreaHeader,
				createKinInfoArea(), line2, caregiverInfoAreaHeader,
				createCaregiverInfoArea(), line3, emergencyContactInfoHeader,
				createEmergencyContactArea());
		
		//populate the textboxes with all relevant information from the database
		this.fillKinText();
		this.fillCaregiverText();
		this.fillEmergencyText();
	}
	
	/**
	 * Purpose: Returns the form to be viewed
	 * @return the ScrollPane containing the main VBox
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	public ScrollPane getForm()
	{
		ScrollPane main = new ScrollPane();
		//put main VBox inside ScrollPane so there will be a 
		//scrollbar available for the form
		main.setContent(mainBox);
		return main;
	}
	
	/**
	 * Purpose: Create the header for the form.
	 * @return the HBox containing the heading label and
	 * the save button.
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private HBox createHeader()
	{
		HBox hbox = new HBox();
		
		Label heading = new Label("Participant Information Update Form");
		heading.setFont(new Font("Arial", 22));
		
		saveMessage = new Label();
		
		saveBtn = new Button("Save");
		
		saveBtn.setOnAction(event -> {
			saveInfo();
		});
		
		
		
		hbox.getChildren().add(heading);
		
		if(loggedInUser instanceof MedicalAdministrator)
		{
		    hbox.getChildren().addAll(saveMessage, saveBtn);
		}
		
		//set spacing between save button and heading
		hbox.setMargin(heading, new Insets(5, 170,5,280));
		
		return hbox;
	}
	
	

	/**
	 * Purpose: Create the area on the form containing
	 * the kin information.
	 * @return the GridPane containing the labels and 
	 * textfields to be populated with the kin's 
	 * information.
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private GridPane createKinInfoArea()
	{
		//GridPane containing the kin labels and textfields
		GridPane main = new GridPane();
		
		//all labels for kin information
		Label firstNameLbl = new Label("First Name:");
		Label lastNameLbl = new Label("Last Name:");
		Label addressLbl = new Label("Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneLbl = new Label("Phone Number:");
		Label homePhoneLbl = new Label("Home:");
		Label workPhoneLbl = new Label("Work:");
		
		//insets for the padding and margins
		Insets insets = new Insets(5,5,5,5);
		
		//set padding for all labels
		firstNameLbl.setPadding(insets);
		lastNameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneLbl.setPadding(insets);
		homePhoneLbl.setPadding(insets);
		workPhoneLbl.setPadding(insets);
		
		//add all the labels and textfields for the kin
		//into the gridpane
		
		//first column
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
		//second column
		main.add(phoneLbl, 2, 0);
		main.add(homePhoneLbl, 2, 1);
		main.add(kinHomePhoneTxt, 3, 1);
		main.add(workPhoneLbl, 2, 2);
		main.add(kinWorkPhoneTxt, 3, 2);
		
		//set margins for all the textfields
		main.setMargin(kinFirstNameTxt, insets);
		main.setMargin(kinLastNameTxt, insets);
		main.setMargin(kinAddressTxt, insets);
		main.setMargin(kinPostalCodeTxt, insets);
		main.setMargin(kinHomePhoneTxt, insets);
		main.setMargin(kinWorkPhoneTxt, insets);
		main.setMargin(kinCityTxt, insets);
		
		//center the gridpane
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
		
	}
	
	/**
	 * Purpose: Create the area on the form for caregiver
	 * information.
	 * @return the GridPane containing the labels and textfields
	 * for the caregivers information
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private GridPane createCaregiverInfoArea()
	{
		//the GridPane containing the labels and textfields for
		//the caregivers information
		GridPane main = new GridPane();
		
		//all labels for caregiver information
		Label firstNameLbl = new Label("First Name:");
		Label lastNameLbl = new Label("Last Name");
		Label addressLbl = new Label("Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneLbl = new Label("Phone Number:");
		Label homePhoneLbl = new Label("Home:");
		Label workPhoneLbl = new Label("Work:"); 
		
		//the insets for padding and margins
		Insets insets = new Insets(5,5,5,5);
		
		//set the padding for all labels
		firstNameLbl.setPadding(insets);
		lastNameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneLbl.setPadding(insets);
		homePhoneLbl.setPadding(insets);
		workPhoneLbl.setPadding(insets);
		
		//add all of the labels and textfields to 
		//the gridpane
		
		//first column
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
		//second column
		main.add(phoneLbl, 2, 0);
		main.add(homePhoneLbl, 2, 1);
		main.add(caregiverHomePhoneTxt, 3, 1);
		main.add(workPhoneLbl, 2, 2);
		main.add(caregiverWorkPhoneTxt, 3, 2);
		
		//set margins for all of the textfields
		main.setMargin(caregiverFirstNameTxt, insets);
		main.setMargin(caregiverLastNameTxt, insets);
		main.setMargin(caregiverAddressTxt, insets);
		main.setMargin(caregiverCityTxt, insets);
		main.setMargin(caregiverPostalCodeTxt, insets);
		main.setMargin(caregiverHomePhoneTxt, insets);
		main.setMargin(caregiverWorkPhoneTxt, insets);
		
		//center the main gridpane
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
	}
	
	/**
	 * Purpose: Creates the area of the form containing
	 * the Emergency Contact Information.
	 * @return the GridPane containing the labels
	 * and textfields for the emergency contact information
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private GridPane createEmergencyContactArea()
	{
		//the gridpane containing the labels and 
		//textfields for emergency contact information
		GridPane main = new GridPane();
		
		//the labels for emergency contact information
		Label name = new Label("First Name:");
		Label lastName = new Label("Last Name");
		Label phone = new Label("Phone Number:");
		
		//insets for padding and margins
		Insets insets = new Insets(5,5,5,5);
	
		//set padding for all labels
		name.setPadding(insets);
		lastName.setPadding(insets);
		phone.setPadding(insets);
		
		//add labels and textfields to the main gridpane
		
		//first column
		main.add(name, 0, 0);
		main.add(emergencyContactFirstName, 1, 0);
		main.add(lastName, 0, 1);
		main.add(emergencyContactLastName, 1, 1);
		//second column
		main.add(phone, 2, 0);
		main.add(emergencyContactPhone, 3, 0);
		
		//set margins for the textfields
		main.setMargin(emergencyContactFirstName, insets);
		main.setMargin(emergencyContactPhone, insets);
		main.setMargin(emergencyContactLastName, insets);
		
		//center the gridpane
		main.alignmentProperty().set(Pos.CENTER);
		
		return main;
	}

	
	/**
	 * Purpose: Calls the ParticipantUpdateFormController
	 * to get all of the kin's information from the database
	 * as required for this form, and puts the information into
	 * the correlating textfields.
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private void fillKinText()
	{
		//get the kin's information
		String[] kinInfo = this.controller.fetchKinInfo();
		
		//put the information into the textfields
		kinFirstNameTxt.setText(kinInfo[0]);
		kinLastNameTxt.setText(kinInfo[1]);
		kinAddressTxt.setText(kinInfo[2]);
		kinCityTxt.setText(kinInfo[3]);
		kinPostalCodeTxt.setText(kinInfo[4]);
		kinHomePhoneTxt.setText(kinInfo[5]);
		kinWorkPhoneTxt.setText(kinInfo[6]);
	}
	
	/**
	 * Purpose: Calls the ParticipantUpdateFormController
	 * to get all of the caregiver's information from the
	 * databse as required for this form, and puts the information
	 * into the correlating textfields.
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private void fillCaregiverText()
	{
		//get the caregivers information
		String[] cgInfo = this.controller.fetchCaregiverInfo();
		
		//puts information into the textfields
		caregiverFirstNameTxt.setText(cgInfo[0]);
		caregiverLastNameTxt.setText(cgInfo[1]);
		caregiverAddressTxt.setText(cgInfo[2]);
		caregiverCityTxt.setText(cgInfo[3]);
		caregiverPostalCodeTxt.setText(cgInfo[4]);
		caregiverHomePhoneTxt.setText(cgInfo[5]);
		caregiverWorkPhoneTxt.setText(cgInfo[6]);
	}
	
	/**
	 * Purpose: Calls the ParticipantUdpateFormController
	 * to get all of the Emergency Contact's information from
	 * the database as required for the form, and puts the 
	 * information into the correlating textfields.
	 * @author Breanna Wilson cst215 Steven Palchinski cst209
	 */
	private void fillEmergencyText()
	{
		//gets the Emergency Contact's information
		String[] eInfo = this.controller.fetchEmergencyContactInfo();
		
		//puts information into the textfields
		emergencyContactFirstName.setText(eInfo[0]);
		emergencyContactLastName.setText(eInfo[1]);
		emergencyContactPhone.setText(eInfo[2]);
	}
	
	
	/**
	 * Purpose: Saves the information in the form to the database. If the information
	 * 	given is invalid, the information will not be saved. A message will display 
	 * 	for both success and failure.
	 * @author Breanna Wilson CST215
	 */
	private void saveInfo()
	{
		String errorMsg = "";
		
		errorMsg += controller.saveKinInformation(kinFirstNameTxt.getText(), kinLastNameTxt.getText(),
				kinAddressTxt.getText(), kinCityTxt.getText(), kinPostalCodeTxt.getText(),
				kinHomePhoneTxt.getText(), kinWorkPhoneTxt.getText());
		
		errorMsg += controller.saveCaregiverInformation(caregiverFirstNameTxt.getText(),
				caregiverLastNameTxt.getText(), caregiverAddressTxt.getText(),
				caregiverCityTxt.getText(), caregiverPostalCodeTxt.getText(),
				caregiverHomePhoneTxt.getText(), caregiverWorkPhoneTxt.getText());
		
		errorMsg += controller.saveEmergencyContactInformation(emergencyContactFirstName.getText(),
				emergencyContactLastName.getText(), emergencyContactPhone.getText());
		
		//if there were no errors, display success
		if(errorMsg.equals(null) || errorMsg.isEmpty())
		{
			saveMessage.setStyle("-fx-text-fill: blue");
			saveMessage.setText("Save successful.");
		}
		//else, display error message(s)
		else
		{
			saveMessage.setStyle("-fx-text-fill: blue");
			saveMessage.setText("Save unsuccessful. Click for details.");
			Stage stage = new Stage();
			PopUpMessage popUp = new PopUpMessage(errorMsg, stage);
			
			saveMessage.setOnMouseClicked(event -> {
				popUp.stage.showAndWait();
			});
		}

	}
	
}
