package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;

public class OlderAdultNeedsTableViewController 
{
	public TableView<OlderAdultsNeeds> adultNeedsTable = new TableView<OlderAdultsNeeds>();
	
	private TableColumn<OlderAdultsNeeds, String> ageColumn = 
			new TableColumn<OlderAdultsNeeds, String>("Age");
	private TableColumn<OlderAdultsNeeds, String> longTermCareColumn =
			new TableColumn<OlderAdultsNeeds, String>("Long Term Care Facility");
	private TableColumn<OlderAdultsNeeds, String> elmwoodColumn = 
			new TableColumn<OlderAdultsNeeds, String>("Elmwood Res");
	private TableColumn<OlderAdultsNeeds, String> lutherCareColumn = 
			new TableColumn<OlderAdultsNeeds, String>("Luther Care");
	private TableColumn<OlderAdultsNeeds, String> otherColumn = 
			new TableColumn<OlderAdultsNeeds, String>("Other");
	private TableColumn<OlderAdultsNeeds, String> totalAsOfColumn = 
			new TableColumn<OlderAdultsNeeds, String>();
	private TableColumn<OlderAdultsNeeds, String> totalForLastYearColumn = 
			new TableColumn<OlderAdultsNeeds, String>();
	
	private ObservableList<OlderAdultsNeeds> adultData = 
			FXCollections.observableArrayList();
	
	public OlderAdultNeedsTableViewController()
	{
		queryAdultsNeedsData();
		initialize();
		adultNeedsTable.setItems(adultData);
		adultNeedsTable.setFocusTraversable(false);
		adultNeedsTable.setMaxHeight(150);
	}
	
	private void queryAdultsNeedsData()
	{
		
	}
	
	public void refreshTable()
	{
		this.adultData.clear();
		this.adultNeedsTable.getColumns().clear();
		this.queryAdultsNeedsData();
		this.initialize();
	}
	
	@SuppressWarnings("unchecked")
	private void initialize()
	{
		ageColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getAgeProperty());
		longTermCareColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getLongTermCareProperty());
		elmwoodColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getElmwoodProperty());
		lutherCareColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getLutherCareProperty());
		otherColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getOtherProperty());
		totalAsOfColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getTotalAsOfProperty());
		totalForLastYearColumn.setCellValueFactory(cellData -> cellData
				.getValue().getTotalForLastYearProperty());
		
		adultNeedsTable.getColumns().addAll(ageColumn, longTermCareColumn,
				elmwoodColumn, lutherCareColumn, otherColumn, totalAsOfColumn,
				totalForLastYearColumn);
	}
	
}
