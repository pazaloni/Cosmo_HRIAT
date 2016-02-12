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
	private DatabaseHelper db;
	private VBox mainBox;
	private Button saveBtn;
	
	public PartcipantUpdateForm(int cosmoID)
	{
		this.cosmoID = cosmoID;
		mainBox = new VBox();
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
		
		TextField nameTxt = new TextField();
		TextField addressTxt = new TextField();
		TextField cityTxt = new TextField();
		TextField postalCodeTxt = new TextField();
		TextField phoneNumberTxt = new TextField();
		TextField birthdateTxt = new TextField();
		TextField sinTxt = new TextField();
		
		
		mainPane.add(nameLbl, 0, 0);
		mainPane.add(nameTxt, 1, 0);
		mainPane.add(addressLbl, 0, 1);
		mainPane.add(addressTxt, 1, 1);
		mainPane.add(cityLbl, 0, 2);
		mainPane.add(cityTxt, 1, 2);
		mainPane.add(postalCodeLbl, 0, 3);
		mainPane.add(postalCodeTxt, 1, 3);
		mainPane.add(phoneNumberLbl, 3, 0);
		mainPane.add(phoneNumberTxt, 4, 0);
		mainPane.add(birthdateLbl, 3, 1);
		mainPane.add(birthdateTxt, 4, 1);
		mainPane.add(sinLbl, 3, 2);
		mainPane.add(sinTxt, 4, 2);
		
		mainPane.setMargin(nameTxt, insets);
		mainPane.setMargin(addressTxt, insets);
		mainPane.setMargin(cityTxt, insets);
		mainPane.setMargin(postalCodeTxt, insets);
		mainPane.setMargin(birthdateTxt, insets);
		mainPane.setMargin(sinTxt, insets);
		
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
		
		TextField nameTxt = new TextField();
		TextField addressTxt = new TextField();
		TextField cityTxt = new TextField();
		TextField postalCodeTxt = new TextField();
		TextField homePhoneTxt = new TextField();
		TextField workPhoneTxt = new TextField();
		
		main.add(nameLbl, 0, 0);
		main.add(nameTxt, 1, 0);
		main.add(addressLbl, 0, 1);
		main.add(addressTxt, 1, 1);
		
		return main;
		
	}
	
	private void saveInfo()
	{
		
		
	}
	
}
