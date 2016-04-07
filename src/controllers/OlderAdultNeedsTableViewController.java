package controllers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import helpers.DatabaseHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import core.OlderAdultsNeeds;
import core.PersonalCare;

/**
 * The controller for the Older Adults Needs tableview for the
 * Quarterly Reports Preview GUI.
 * 
 * This will take care of handling putting data into the tableview, 
 * as well as the tableview's layout.
 * 
 * @author Breanna Wilson, Jon Froese
 *
 */

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
			new TableColumn<OlderAdultsNeeds, String>("totalAsOfPH");
	private TableColumn<OlderAdultsNeeds, String> totalForLastYearColumn = 
			new TableColumn<OlderAdultsNeeds, String>("totalForLastYearPH");
	
	private ObservableList<OlderAdultsNeeds> adultData = 
			FXCollections.observableArrayList();
	
	private DatabaseHelper db = new DatabaseHelper();
	
	/**
	 * Initializes the tableview, pulls the
	 * data from the database and puts it into
	 * the tableview.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public OlderAdultNeedsTableViewController()
	{
		queryAdultsNeedsData();
		initialize();
		adultNeedsTable.setItems(adultData);
		adultNeedsTable.setFocusTraversable(false);
		adultNeedsTable.setMaxHeight(150);
	}
	
	@SuppressWarnings("deprecation")
	private void queryAdultsNeedsData()
	{
//		ResultSet rsLongTerm = db.select("COUNT(careType)", "Participant", "careType = \"Long Term Care\" AND ", "");
//		ResultSet rsElmwood = db.select("COUNT(careType)", "Participant", "careType = \"Elmwood\"", "");
//		ResultSet rsLuther = db.select("COUNT(careType)", "Participant", "careType = \"Luther\"", "");
//		ResultSet rsOther = db.select("COUNT(careType)", "Participant", "careType = \"Other\"", "");
		OlderAdultsNeeds over65 = null;
		OlderAdultsNeeds from60to65 = null;
		OlderAdultsNeeds from50to54 = null;
		OlderAdultsNeeds from55to59 = null;
		OlderAdultsNeeds from40to49 = null;
		OlderAdultsNeeds from30to39 = null;
		OlderAdultsNeeds from20to29 = null;
		
		int over65LongTermCount = 0;
		int from60to65LongTermCount = 0;
		int from55to59LongTermCount = 0;
		int from50to54LongTermCount = 0;
		int from40to49LongTermCount = 0;
		int from30to39LongTermCount = 0;
		int from20to29LongTermCount = 0;
		
		int over65ElmwoodResCount = 0;
		int from60to65ElmwoodResCount = 0;
		int from55to59ElmwoodResCount = 0;
		int from50to54ElmwoodResCount = 0;
		int from40to49ElmwoodResCount = 0;
		int from30to39ElmwoodResCount = 0;
		int from20to29ElmwoodResCount = 0;
		
		int over65LutherCareCount = 0;
		int from60to65LutherCareCount = 0;
		int from55to59LutherCareCount = 0;
		int from50to54LutherCareCount = 0;
		int from40to49LutherCareCount = 0;
		int from30to39LutherCareCount = 0;
		int from20to29LutherCareCount = 0;
		
		int over65OtherCount = 0;
		int from60to65OtherCount = 0;
		int from55to59OtherCount = 0;
		int from50to54OtherCount = 0;
		int from40to49OtherCount = 0;
		int from30to39OtherCount = 0;
		int from20to29OtherCount = 0;
		
		ResultSet rs = db.select("careType, address, dateOfBirth", "Participant", "","");
		
		
		//LocalDate current = LocalDate.of(Calendar.YEAR, Calendar.MONTH + 1, Calendar.DAY_OF_MONTH);
		Date current = new Date(Calendar.YEAR, Calendar.MONTH + 1, Calendar.DAY_OF_MONTH);
		System.out.println(current.toString());
		//LocalDate birthday;
		Date birthday;
		
		try 
		{
			while(rs != null && rs.next())
			{
				//birthday = LocalDate.of(rs.getDate(3).getYear(), 
				//		rs.getDate(3).getMonth() + 1, rs.getDate(3).getDate());
				birthday = rs.getDate(3);
				System.out.println(birthday.toString());
				
				long years = current.getYear() - birthday.getYear();
				System.out.println(years);
				
				String careType = rs.getString(1);
				
				if(years > 65)
				{
					
					if(careType != null && careType.contains("Elmwood"))
					{
						over65ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						over65LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						over65LongTermCount++;
					}
					else
					{
						over65OtherCount++;
					}
					
				}
				else if(years >= 60 && years <= 65)
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from60to65ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from60to65LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from60to65LongTermCount++;
					}
					else
					{
						from60to65OtherCount++;
					}
				}
				else if(years >= 55 && years <= 59)
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from55to59ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from55to59LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from55to59LongTermCount++;
					}
					else
					{
						from55to59OtherCount++;
					}
					
				}
				else if(years >= 50 && years <= 54)
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from50to54ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from50to54LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from50to54LongTermCount++;
					}
					else
					{
						from50to54OtherCount++;
					}
				}
				else if(years >= 40 && years <= 49)
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from40to49ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from40to49LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from40to49LongTermCount++;
					}
					else
					{
						from40to49OtherCount++;
					}
					
				}
				else if(years >= 30 && years <= 39)
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from30to39ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from30to39LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from30to39LongTermCount++;
					}
					else
					{
						from30to39OtherCount++;
					}
					
				}
				else
				{
					if(careType != null && careType.contains("Elmwood"))
					{
						from20to29ElmwoodResCount++;
					}
					else if(careType != null && careType.contains("Luther"))
					{
						from20to29LutherCareCount++;
					}
					else if(careType != null && careType.contains("Long"))
					{
						from20to29LongTermCount++;
					}
					else
					{
						from20to29OtherCount++;
					}
					
				}
				
			}
			
			over65 = new OlderAdultsNeeds(">65", over65LongTermCount,
						over65ElmwoodResCount, over65LutherCareCount, 
						over65OtherCount,0,0);
			from55to59 = new OlderAdultsNeeds("55-59", from55to59LongTermCount, 
					from55to59ElmwoodResCount, from55to59LutherCareCount, from55to59OtherCount,0, 0);
			from50to54 = new OlderAdultsNeeds("50-54", from50to54LongTermCount, 
					from50to54ElmwoodResCount, from50to54LutherCareCount, from50to54OtherCount,0, 0);
			from40to49 = new OlderAdultsNeeds("40-49", from40to49LongTermCount, from40to49ElmwoodResCount,
					from40to49LutherCareCount, from40to49OtherCount,0, 0);
			from30to39 = new OlderAdultsNeeds("30-39", from30to39LongTermCount, from30to39ElmwoodResCount,
					from30to39LutherCareCount, from30to39OtherCount,0, 0);
			from20to29 = new OlderAdultsNeeds("<29", from20to29LongTermCount, from20to29ElmwoodResCount,
					from20to29LutherCareCount, from20to29OtherCount,0, 0);
			
			adultData.addAll(over65, from55to59, from50to54, from40to49, from30to39, from20to29); 
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * Clears the tableview and the observablelist, re-queries
	 * the database, and places the fresh data into the list and
	 * then the tableview.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public void refreshTable()
	{
		this.adultData.clear();
		this.adultNeedsTable.getColumns().clear();
		this.queryAdultsNeedsData();
		this.initialize();
	}
	
	/**
	 * Initializes the tableview. The tablecolumns are
	 * set up to pull data from the data holder object, 
	 * and are put into the tableview.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	@SuppressWarnings("unchecked")
	public void initialize()
	{
		ageColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getAgeProperty());
        ageColumn.setMinWidth(80);
        ageColumn.setResizable(false);
		
        longTermCareColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getLongTermCareProperty());
        longTermCareColumn.setMinWidth(180);
        longTermCareColumn.setResizable(false);
		
		elmwoodColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getElmwoodProperty());
        elmwoodColumn.setMinWidth(140);
        elmwoodColumn.setResizable(false);
		
		lutherCareColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getLutherCareProperty());
        lutherCareColumn.setMinWidth(140);
        lutherCareColumn.setResizable(false);
		
		otherColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getOtherProperty());
        otherColumn.setMinWidth(100);
        otherColumn.setResizable(false);
		
		totalAsOfColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getTotalAsOfProperty());
        totalAsOfColumn.setMinWidth(120);
        totalAsOfColumn.setResizable(false);
		
		totalForLastYearColumn.setCellValueFactory(cellData -> cellData
				.getValue().getTotalForLastYearProperty());
        totalForLastYearColumn.setMinWidth(130);
        totalForLastYearColumn.setResizable(false);
		
		adultNeedsTable.getColumns().addAll(ageColumn, longTermCareColumn,
				elmwoodColumn, lutherCareColumn, otherColumn, totalAsOfColumn,
				totalForLastYearColumn);
	}
	
	/**
	 * Sets the label for the "Total as of <Date>" column.
	 * @param label The text for the label.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public void setTotalAsOfColumn(String label)
	{
	    totalAsOfColumn.setText(label);
	}
	
	/**
	 * Sets the label for the "Total for <Date>" column.
	 * @param label The text for the label.
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
    public void setTotalForLastYearColumn(String label)
    {
        totalForLastYearColumn.setText(label);
    }
	
}
