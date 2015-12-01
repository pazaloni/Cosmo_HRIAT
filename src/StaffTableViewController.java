import javafx.application.*;

import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StaffTableViewController 
{
	//the tableview containing the information for all the staff accounts
	protected TableView<StaffAccount> staffTable = new TableView<StaffAccount>();
	//
	private TableColumn<StaffAccount, String> usernameColumn = new TableColumn<StaffAccount, String>("Username");
	private TableColumn<StaffAccount, String> lastNameColumn = new TableColumn<StaffAccount, String>("Last Name");
	private TableColumn<StaffAccount, String> firstNameColumn = new TableColumn<StaffAccount, String>("First Name");
	private TableColumn<StaffAccount, String> emailColumn = new TableColumn<StaffAccount, String>("Email");
	private TableColumn<StaffAccount, String> accessLevelColumn = new TableColumn<StaffAccount, String>("Access Level");
	
	public ObservableList<StaffAccount> staffData = FXCollections.observableArrayList();
	
	public StaffTableViewController()
	{
		intitializeStaffData();
		staffTable.setItems(staffData);
	}


	private void intitializeStaffData() 
	{
		DatabaseHelper db = new DatabaseHelper();
		ObservableList<String> row = FXCollections.observableArrayList();
		ArrayList<String> staffInfo = new ArrayList<String>();
		ResultSet rs = db.select("username, lastName, firstName, email, "
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
				
				staffData.add(account);
			}
		} catch (SQLException e) {
			System.out.println("Failed to populate Staff Table");
			e.printStackTrace();
		}
	
	}
	
	public void initialize()
	{
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
		
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		
		accessLevelColumn.setCellValueFactory(cellData -> cellData.getValue().accessLevelProperty());
		
		staffTable.getColumns().addAll(usernameColumn, lastNameColumn, firstNameColumn, emailColumn, accessLevelColumn);
		
		staffTable.setItems(staffData);
	}

	public String getSelectedPK() 
	{
		StaffAccount account = staffTable.getSelectionModel().getSelectedItem();
		return account.GetUsername();
	}
	
	
	public void removeViewableUser(String username)
	{
		this.staffData.clear();
		this.intitializeStaffData();
		this.staffTable.getColumns().clear();
		this.initialize();
	}
}
