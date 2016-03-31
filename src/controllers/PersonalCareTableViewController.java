package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;
import core.PersonalCare;

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
		
	}
	
	public void refreshTable()
	{

	}
	
	@SuppressWarnings("unchecked")
	public void initialize()
	{

	}
	

	
}
