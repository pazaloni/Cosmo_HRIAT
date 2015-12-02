import javafx.application.*;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ParticipantTableViewController 
{
	//the tableview containing the information for all the staff accounts
	protected TableView<Participant> participantTable = new TableView<Participant>();
	//
	private TableColumn<Participant, String> cosmoIDColumn = new TableColumn<Participant, String>("Cosmo ID");
	private TableColumn<Participant, String> participantNameColumn = new TableColumn<Participant, String>("Participant");
	private TableColumn<Participant, String> addressColumn = new TableColumn<Participant, String>("Home Address");
	private TableColumn<Participant, String> emergencyNameColumn = new TableColumn<Participant, String>("Emergency Contact Name");
	private TableColumn<Participant, String> emergencyPhoneColumn = new TableColumn<Participant, String>("Emergency Phone");
	private TableColumn<Participant, String> lastUpdatedColumn = new TableColumn<Participant, String>("Last Updated");
	
	public ObservableList<Participant> participantData = FXCollections.observableArrayList();
	
	public ParticipantTableViewController()
	{
		intitializeParticipantData();
		participantTable.setItems(participantData);
	}


	private void intitializeParticipantData() 
	{
		DatabaseHelper db = new DatabaseHelper();
		ObservableList<String> row = FXCollections.observableArrayList();
		ArrayList<String> participantInfo = new ArrayList<String>();
		//TODO fix to query appropriate address, emergency info
		//correct table Participant instead of TemporaryParticipant
		ResultSet rs = db.select("cosmoID, firstName, lastName, city, province,  "
				+ "informationLastUpdated", "TemporaryParticipant", "", "");
		
	    String cosmoID;
	    String firstName;
	    String lastName;
	    String participantName;
	    String participantAddress; 
	    String emergencyContactName = "";
	    String emergencyContactPhone = ""; //TODO FIX IT
	    String informationLastUpdated;
	    
		try {
			while(rs.next())
			{
				cosmoID = rs.getString(1);
				System.out.println(cosmoID);
				firstName = rs.getString(2);
				lastName = rs.getString(3);
				participantName = firstName + " " + lastName;
				participantAddress = rs.getString(4) + " " + rs.getString(5);
				
				informationLastUpdated = rs.getString(6);
				

				Participant participant = new Participant(cosmoID, participantName,
			            participantAddress, emergencyContactName,
			            emergencyContactPhone, informationLastUpdated);
				
				
				participantData.add(participant);
			}
		} catch (SQLException e) {
			System.out.println("Failed to populate Participant Table");
			e.printStackTrace();
		}
	
	}
	
	public void initialize()
	{
		cosmoIDColumn.setCellValueFactory(cellData -> cellData.getValue().getCosmoIDProperty());
		
		participantNameColumn.setCellValueFactory(cellData -> cellData.getValue().getParticipantNameProperty());
		
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
		
		emergencyNameColumn.setCellValueFactory(cellData -> cellData.getValue().getEmergencyContactProperty());
		
		emergencyPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getEmergencyContactPhoneProperty());
		
		lastUpdatedColumn.setCellValueFactory(cellData -> cellData.getValue().getUpdatedProperty());
		
		participantTable.getColumns().addAll(cosmoIDColumn, participantNameColumn, addressColumn, emergencyNameColumn, emergencyPhoneColumn, lastUpdatedColumn);
		
		participantTable.setItems(participantData);
	}

	public String getSelectedPK() 
	{
		Participant participant = participantTable.getSelectionModel().getSelectedItem();
		return participant.getCosmoID();
	}
	
	
	public void removeViewableUser(String username)
	{
		this.participantData.clear();
		this.intitializeParticipantData();
		this.participantTable.getColumns().clear();
		this.initialize();
	}
}
