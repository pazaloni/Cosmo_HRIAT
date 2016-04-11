package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;
import core.PersonalCare;

/**
 * This is the controller for the personal care tableview
 * for the quarterly reports preview GUI. 
 * @author Breanna Wilson, Jon Froese
 *
 */

public class PersonalCareTableViewController 
{
	
    public TableView<PersonalCare> personalCareTable = new TableView<PersonalCare>();
    
    private TableColumn<PersonalCare, String> blankColumn = 
            new TableColumn<PersonalCare, String>("");
    private TableColumn<PersonalCare, String> supportsColumn =
            new TableColumn<PersonalCare, String>("Supports");
    private TableColumn<PersonalCare, String> totalAsOfColumn = 
            new TableColumn<PersonalCare, String>("totalPersonsAsOfPH");
    private TableColumn<PersonalCare, String> totalForLastYearColumn = 
            new TableColumn<PersonalCare, String>("totalForLastYearPH");
    
    private ObservableList<PersonalCare> personalCareData = 
            FXCollections.observableArrayList();
    
    /**
     * Queries for the personal care data from the database,
     * initializes the tableview, and puts the data in the
     * tableview.
     * 
     * @author Breanna Wilson, Jon Froese
     */
	public PersonalCareTableViewController()
	{
        queryPersonalCareData();
        initialize();
        personalCareTable.setItems(personalCareData);
        personalCareTable.setFocusTraversable(false);
        personalCareTable.setMaxHeight(150);
	}
	
	private void queryPersonalCareData()
	{
		//TODO have this query the information from the DB
	}
	
	/**
	 * Clears the tableview and observablelist,
	 * requeries for new data and places it into
	 * the tableview
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public void refreshTable()
	{
        this.personalCareData.clear();
        this.personalCareTable.getColumns().clear();
        this.queryPersonalCareData();
        this.initialize();
	}
	
	/**
	 * Initializes the tableview, and sets up the cellfactories to
	 * pull data from the data objects into the tablecolumns, and puts
	 * them into the tableview
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@SuppressWarnings("unchecked")
	public void initialize()
	{
        blankColumn.setMinWidth(130);
        blankColumn.setResizable(false);
        
        supportsColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getSupportsProperty());
        supportsColumn.setMinWidth(250);
        supportsColumn.setResizable(false);
        
        
        totalAsOfColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getTotalAsOfProperty());
        totalAsOfColumn.setMinWidth(250);
        totalAsOfColumn.setResizable(false);
        
        totalForLastYearColumn.setCellValueFactory(cellData -> cellData
                .getValue().getTotalForLastYearProperty());
        totalForLastYearColumn.setMinWidth(250);
        totalForLastYearColumn.setResizable(false);
        
        personalCareTable.getColumns().addAll(blankColumn, supportsColumn, totalAsOfColumn,
                totalForLastYearColumn);
	}
	

	
}
