package gui;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuarterlyReportPrevGUI extends Application {

	
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	
	private TableView olderAdultNeedsTbl;
	private TableView personalHealthCareTbl;
	private TableView reactiveCareTbl;
	private TableView healthPromotionTbl;
	
	private Button exportBtn;
	
	private VBox mainVbox;
	
	public QuarterlyReportPrevGUI()
	{
		
	}
	
    @Override
    public void start( Stage stage ) throws Exception
    {
    	
    }
    
    private VBox createHeader()
    {
    	return null;
    }
    
    private VBox createOlderAdultNeedsArea()
    {
    	return null;
    }
    
    private VBox createPersonalHealthCareArea()
    {
    	return null;
    }
    
    private VBox createReactiveCareArea()
    {
    	return null;
    }
    
    private VBox createHealthPromotionArea()
    {
    	return null;
    }
	
}
