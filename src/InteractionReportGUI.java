

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InteractionReportGUI extends HBox
{
	private int cosmoID;
	private HBox container;
	private ScrollPane rptScrollPane;
	private VBox mainRptBox;
	private Button editBtn;
	
	private Label participantNameText;
	private Label dateOfBirthText;
	private Label locationText;
	private Label othersInvolvedText;
	private Label dateCompletedText;
	private Label timeText;
	private Label reportingStaffText;
	
	private DatabaseHelper db;
	
	public InteractionReportGUI(int cosmoID)
	{
		this.cosmoID = cosmoID;
		db = new DatabaseHelper();
		db.connect();
		container = new HBox();
		rptScrollPane = new ScrollPane();
		mainRptBox = new VBox();
		
		mainRptBox.getChildren().add(createHeader());
		
		container.getChildren().add(mainRptBox);
	}
	
	public HBox getReport()
	{
		return container;
	}
	
	private VBox createHeader()
	{
		VBox main = new VBox();
		HBox infoBox = new HBox();
		VBox leftInfo = new VBox();
		VBox rightInfo = new VBox();
		HBox heading = new HBox();
		
		Label headingLbl = new Label("Interaction Report");
		headingLbl.setFont(new Font("Arial", 18));
		
		editBtn = new Button("Edit");
		
		editBtn.setOnAction(event -> {
			//change form to be editable
		});
		
		heading.getChildren().addAll(headingLbl, editBtn);
		
		Label participantNameLabel = new Label("Participant Name:");
		Label dateOfBirthLabel = new Label("Date of Birth:");
		Label locationLabel = new Label("Location:");
		Label othersInvolvedLabel = new Label("Others Involved:");
		Label dateCompletedLabel = new Label("Date Completed:");
		Label timeLabel = new Label("Time");
		Label reportingStaffLabel = new Label("Reporting Staff:");
		
		participantNameText = new Label();
		dateOfBirthText = new Label();
		locationText = new Label();
		othersInvolvedText = new Label();
		dateCompletedText = new Label();
		timeText = new Label();
		reportingStaffText = new Label();
		
		fetchHeaderInfo();
		
		leftInfo.getChildren().addAll(participantNameLabel, participantNameText,
				dateOfBirthLabel, dateOfBirthText, locationLabel, locationText,
				othersInvolvedLabel, othersInvolvedText);
		rightInfo.getChildren().addAll(dateCompletedLabel, dateCompletedText,
				timeLabel, timeText, reportingStaffLabel, reportingStaffText);
		infoBox.getChildren().addAll(leftInfo, rightInfo);
		
		main.getChildren().addAll(heading, infoBox);
		
		return main;
	}
	
	private void fetchHeaderInfo()
	{
		ResultSet rs = db.select("firstName, lastName, dateOfBirth", 
				"Participant", "cosmoID = " + cosmoID, "");
		
		try {
			while(rs.next())
			{
				participantNameText.setText(rs.getString(1) + " " + rs.getString(2));
				dateOfBirthText.setText(rs.getDate(3).toString());
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
