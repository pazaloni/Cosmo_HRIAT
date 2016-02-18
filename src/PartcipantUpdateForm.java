import javafx.geometry.Insets;
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


public class PartcipantUpdateForm extends ScrollPane
{
	private int cosmoID;
	private VBox mainBox;
	public Button saveBtn;
	private ParticipantUpdateFormController controller;
	
	public TextField participantNameTxt = new TextField();
	public TextField participantAddressTxt = new TextField();
	public TextField participantCityTxt = new TextField();
	public TextField participantPostalCodeTxt = new TextField();
	public TextField participantPhoneNumberTxt = new TextField();
	public TextField participantBirthdateTxt = new TextField();
	public TextField participantSinTxt = new TextField();
	
	public TextField kinNameTxt = new TextField();
	public TextField kinAddressTxt = new TextField();
	public TextField kinCityTxt = new TextField();
	public TextField kinPostalCodeTxt = new TextField();
	public TextField kinHomePhoneTxt = new TextField();
	public TextField kinWorkPhoneTxt = new TextField();
	
	public PartcipantUpdateForm(int cosmoID)
	{
		this.cosmoID = cosmoID;
		mainBox = new VBox();
		controller = new ParticipantUpdateFormController(cosmoID);
		
		Separator line1 = new Separator();
		line1.minWidth(1000);
		
		
		mainBox.getChildren().addAll(createHeader(), createParticipantInfoArea(),
				line1);
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
		
		Label nameLbl = new Label("Participant Name:");
		Label addressLbl = new Label("Participant Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneNumberLbl = new Label("Participant Phone Number:");
		Label birthdateLbl = new Label("Birth Date:");
		Label sinLbl = new Label("SIN:");
		
		Insets insets = new Insets(5,5,5,5);
		
		nameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneNumberLbl.setPadding(insets);
		birthdateLbl.setPadding(insets);
		sinLbl.setPadding(insets);
		
		participantNameTxt = new TextField();
		participantAddressTxt = new TextField();
		participantCityTxt = new TextField();
		participantPostalCodeTxt = new TextField();
		participantPhoneNumberTxt = new TextField();
		participantBirthdateTxt = new TextField();
		participantSinTxt = new TextField();
		
		
		mainPane.add(nameLbl, 0, 0);
		mainPane.add(participantNameTxt, 1, 0);
		mainPane.add(addressLbl, 0, 1);
		mainPane.add(participantAddressTxt, 1, 1);
		mainPane.add(cityLbl, 0, 2);
		mainPane.add(participantCityTxt, 1, 2);
		mainPane.add(postalCodeLbl, 0, 3);
		mainPane.add(participantPostalCodeTxt, 1, 3);
		mainPane.add(phoneNumberLbl, 3, 0);
		mainPane.add(participantPhoneNumberTxt, 4, 0);
		mainPane.add(birthdateLbl, 3, 1);
		mainPane.add(participantBirthdateTxt, 4, 1);
		mainPane.add(sinLbl, 3, 2);
		mainPane.add(participantSinTxt, 4, 2);
		
		mainPane.setMargin(participantNameTxt, insets);
		mainPane.setMargin(participantAddressTxt, insets);
		mainPane.setMargin(participantCityTxt, insets);
		mainPane.setMargin(participantPostalCodeTxt, insets);
		mainPane.setMargin(participantBirthdateTxt, insets);
		mainPane.setMargin(participantSinTxt, insets);
		
		mainPane.setPadding(new Insets(5,5,5,5));
		
		return mainPane;
	}

	
	private GridPane createKinInfoArea()
	{
		GridPane main = new GridPane();
		
		Label nameLbl = new Label("Next of Kin Name:");
		Label addressLbl = new Label("Next of Kin Address:");
		Label cityLbl = new Label("City:");
		Label postalCodeLbl = new Label("Postal Code:");
		Label phoneLbl = new Label("Next of kin Phone Number:");
		Label homePhoneLbl = new Label("Home:");
		Label workPhoneLbl = new Label("Work:");
		
		Insets insets = new Insets(5,5,5,5);
		
		nameLbl.setPadding(insets);
		addressLbl.setPadding(insets);
		cityLbl.setPadding(insets);
		postalCodeLbl.setPadding(insets);
		phoneLbl.setPadding(insets);
		homePhoneLbl.setPadding(insets);
		workPhoneLbl.setPadding(insets);
		
		kinNameTxt = new TextField();
		kinAddressTxt = new TextField();
		kinCityTxt = new TextField();
		kinPostalCodeTxt = new TextField();
		kinHomePhoneTxt = new TextField();
		kinWorkPhoneTxt = new TextField();
		
		main.add(nameLbl, 0, 0);
		main.add(kinNameTxt, 1, 0);
		main.add(addressLbl, 0, 1);
		main.add(kinAddressTxt, 1, 1);
		main.add(cityLbl, 0, 2);
		main.add(kinCityTxt, 1, 2);
		main.add(postalCodeLbl, 0, 3);
		main.add(kinPostalCodeTxt, 1, 3);
		main.add(phoneLbl, 2, 0);
		main.add(homePhoneLbl, 2, 1);
		main.add(kinHomePhoneTxt, 3, 1);
		main.add(workPhoneLbl, 2, 2);
		main.add(kinWorkPhoneTxt, 3, 2);
		
		return main;
		
	}
	
	private void saveInfo()
	{
		
		
	}
	
}
