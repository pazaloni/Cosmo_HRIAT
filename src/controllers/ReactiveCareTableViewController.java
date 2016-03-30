package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;
import core.ReactiveCare;

public class ReactiveCareTableViewController 
{
	public TableView<ReactiveCare> reactiveCareTable = new TableView<ReactiveCare>();
	
	private TableColumn<ReactiveCare, String> yearColumn = 
			new TableColumn<ReactiveCare, String>("Year");
	private TableColumn<ReactiveCare, String> participantsColumn = 
			new TableColumn<ReactiveCare, String>("Participants Involved");
	private TableColumn<ReactiveCare, String> staffColumn = 
			new TableColumn<ReactiveCare, String>("Staff Members Involved");
	private TableColumn<ReactiveCare, String> totaColumn =
			new TableColumn<ReactiveCare, String>("Total");
	
	private ObservableList<ReactiveCare> reactiveCareData =
			FXCollections.observableArrayList();
	
	public ReactiveCareTableViewController()
	{
		queryReactiveCareData();
		initialize();
		reactiveCareTable.setItems(reactiveCareData);
		reactiveCareTable.setFocusTraversable(false);
		reactiveCareTable.setMaxHeight(150);
	}
	
	private void queryReactiveCareData() 
	{
		
	}
	
	public void refreshTable()
	{
		this.reactiveCareData.clear();
		this.reactiveCareTable.getColumns().clear();
		this.queryReactiveCareData();
		this.initialize();
	}
	
	@SuppressWarnings("unchecked")
	public void initialize()
	{
		yearColumn.setCellValueFactory(cellData -> cellData.getValue().getYearProperty());
		yearColumn.setMinWidth(160);
		yearColumn.setResizable(false);
		
		participantsColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getParticipantsProperty());
		participantsColumn.setMinWidth(250);
		participantsColumn.setResizable(false);
		
		staffColumn.setCellValueFactory(cellData -> cellData.getValue().getStaffProperty());
		staffColumn.setMinWidth(250);
		staffColumn.setResizable(false);
		
		totaColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
		totaColumn.setMinWidth(220);
		totaColumn.setResizable(false);
		
		reactiveCareTable.getColumns().addAll(yearColumn, participantsColumn, 
				staffColumn, totaColumn);
	}
	
}
