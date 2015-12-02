import javafx.application.*;

import java.sql.*;
import java.util.ArrayList;

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
		ArrayList<String> staffInfo = new ArrayList<String>();
		ResultSet rs = db.select("cosmoID, lastName, firstName, dateOfBirth, "
				+ "accessLevel", "Staff", "", "");
		String username;
		String lastName;
		String firstName;
		String email;
		String password = "";
		String accessLevel;
		try {
			while(rs.next())
			{
				username = rs.getString(1);
				System.out.println(username);
				lastName = rs.getString(2);
				firstName = rs.getString(3);
				if(rs.getString(4) == null)
				{
					email = "none";
				}
				else
				{
					email = rs.getString(4);
				}
				
				accessLevel = rs.getString(5);
				
				StaffAccount account;
				if(accessLevel == "0")
				{
					account = new BasicStaff(username, lastName, firstName, 
							email, password, accessLevel);
				}
				else if(accessLevel == "1")
				{
					account = new MedicalAdministrator(username, lastName, 
							firstName, email, password, accessLevel);
				}
				else
				{
					account = new TechnicalAdministrator(username, lastName,
							firstName, email, password, accessLevel);
				}
				
				participantData.add(account);
			}
		} catch (SQLException e) {
			System.out.println("Failed to populate Staff Table");
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
		StaffAccount account = participantTable.getSelectionModel().getSelectedItem();
		return account.GetUsername();
	}
	
	
	public void removeViewableUser(String username)
	{
		this.participantData.clear();
		this.intitializeParticipantData();
		this.participantTable.getColumns().clear();
		this.initialize();
	}
}
