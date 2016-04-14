package gui;

import helpers.QuarterlyReportExportHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controllers.OlderAdultNeedsTableViewController;
import controllers.PersonalCareTableViewController;
import controllers.ReactiveCareTableViewController;
import controllers.HealthPromotionTableViewController;


import core.PopUpMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The interface for previewing the querterly reports based on the dates selected.
 * @author Breanna Wilson, Jon Froese
 *
 */

public class QuarterlyReportsGUI extends Application {
	
	//NOTE: start and end dates do not affect how data is pulled from
	// the database, it is only to print it onto the exported form
	private DatePicker startDate;
	private DatePicker endDate;
	
	//Controllers for each TableView
	private OlderAdultNeedsTableViewController olderAdultNeedsTVCont;
	private PersonalCareTableViewController personalCareTVCont;
	private ReactiveCareTableViewController reactiveCareTVCont;
	
	private Button exportBtn;
	//VBox containing all other nodes
	public VBox mainVbox;
	
	public Stage stage;
	private Scene scene;
	
	
	/**
	 * Initializes the main VBox and contained nodes.
	 * @param stage The stage holding the main VBox
	 * 
	 * @author Breanna Wilson, Jon Froese
	 */
	public QuarterlyReportsGUI(Stage stage)
	{
		this.stage = stage;
		mainVbox = new VBox();
		stage.setTitle("Generate Quarterly Report");

		mainVbox.getChildren().addAll(createHeader(),
				createOlderAdultNeedsArea(), createPersonalHealthCareArea(),
				createReactiveCareArea(), createHealthPromotionArea());
		
		//When the ending date has been set or changed, update the column headers
		endDate.setOnAction(event->{
			if(endDate.getValue() != null)
			{
				olderAdultNeedsTVCont.setTotalAsOfColumn("Total As of: " + formatDate(endDate.getValue()));
				personalCareTVCont.setTotalAsOfColumn("Total As of: " + formatDate(endDate.getValue()));
				olderAdultNeedsTVCont.setTotalForLastYearColumn("Total For " + (endDate.getValue().getYear() - 1 ));
				personalCareTVCont.setTotalForLastYearColumn("Total For " + (endDate.getValue().getYear() - 1 ));
			}
		});
	}
	
    @Override
    public void start( Stage stage ) throws Exception
    {
		//Nothing is necessary to do here, as it is done in the constructor instead
    }
    
    /**
     * Creates the header for the cosmo logo, datepickers, and the export button
     * @return The VBox containing the header objects
     * 
     *  @author Breanna Wilson, Jon Froese
     */
    private VBox createHeader()
    {
    	VBox vbox = new VBox();
		// logo image size
		ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
		logo.setFitWidth(400);
		logo.setFitHeight(49);
		
		
		Label pageLbl = new Label("Create Quarterly Report");
		pageLbl.setPadding(new Insets(5,5,5,5));
		pageLbl.setFont(new Font("Arial", 24));
		
		vbox.getChildren().addAll(logo, pageLbl);
		
		//container for the datepickers and their labels
		GridPane dateContainer = new GridPane();
		
		Label startDateLbl = new Label("Start Date:");
		Label endDateLbl = new Label("End Date:");
		
		startDate = new DatePicker();
		endDate = new DatePicker();
		
		dateContainer.add(startDateLbl, 0, 0);
		dateContainer.add(startDate, 0, 1);
		dateContainer.add(endDateLbl, 1, 0);
		dateContainer.add(endDate, 1, 1);
		
		//add some spacing for the datepickers and their labels
		dateContainer.setHgap(5);
		dateContainer.setVgap(5);
		
		BorderPane bp = new BorderPane();
		bp.setLeft(dateContainer);
		
		exportBtn = new Button("Export");
		
		exportBtn.setOnAction(event->{
			exportReport();
		});
		
		bp.setRight(exportBtn);
		
		bp.setPadding(new Insets(5,5,5,5));
		
		vbox.getChildren().add(bp);
		
		vbox.setSpacing(5);
		return vbox;
    }
    
    /**
     * Formats the date to a dd-MMM-yyyy format.
     * @param date The date to be formatted
     * @return A string representing the formatted date
     */
    private String formatDate( LocalDate date )
    {
        return date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }

    
    /**
     * Creates the VBox containing the header label and tableview for
     * the adult needs table.
     * @return VBox containing adult needs label and tableview
     * 
     *  @author Breanna Wilson, Jon Froese
     */
    private VBox createOlderAdultNeedsArea()
    {
    	VBox vbox = new VBox();
    	
    	Label areaLbl = new Label("Attending to the Needs of Older Adults");
    	areaLbl.setPadding(new Insets(5,5,5,5));
		areaLbl.setFont(new Font("Arial", 24));
    	
		olderAdultNeedsTVCont = new OlderAdultNeedsTableViewController();
		
		
		
		
		
		vbox.getChildren().addAll(areaLbl, olderAdultNeedsTVCont.adultNeedsTable);
		
    	return vbox;
    }
    
    /**
     * Creates the label and tableview for the personal
     * health care area.
     * @return The VBox containing the label and tableview for
     *  participant personal health care
     *  
     *   @author Breanna Wilson, Jon Froese
     */
    private VBox createPersonalHealthCareArea()
    {
       VBox vbox = new VBox();
        
        Label areaLbl = new Label("Personal Health Maintenance/Personal Care");
        areaLbl.setPadding(new Insets(5,5,5,5));
        areaLbl.setFont(new Font("Arial", 24));
        
        personalCareTVCont = new PersonalCareTableViewController();
        
        vbox.getChildren().addAll(areaLbl, personalCareTVCont.personalCareTable);
        
        return vbox;
    }
    
    /**
     * Creates the label and tableview for the reactive care
     * area.
     * @return The VBox containing the label and tableview for the
     *  reactive care area
     *  
     *   @author Breanna Wilson, Jon Froese
     */
    private VBox createReactiveCareArea()
    {
       VBox vbox = new VBox();
        
        Label areaLbl = new Label("Reactive Care");
        areaLbl.setPadding(new Insets(5,5,5,5));
        areaLbl.setFont(new Font("Arial", 24));
        
        reactiveCareTVCont = new ReactiveCareTableViewController();
        
        vbox.getChildren().addAll(areaLbl, reactiveCareTVCont.reactiveCareTable);
        
        return vbox;
    }
    
    /**
     * Creates the label and tableview for the health promotion area.
     * @return The VBox containing the label and tableview for the 
     * health promotion area
     * 
     *  @author Breanna Wilson, Jon Froese
     */
    private VBox createHealthPromotionArea()
    {
       VBox vbox = new VBox();
        
        Label areaLbl = new Label("Health Promotion Activities and Health-Related Supplies"
        		+ " are to be entered after export.");
        areaLbl.setPadding(new Insets(5,5,5,5));
        areaLbl.setFont(new Font("Arial", 12));
        
        
        vbox.getChildren().addAll(areaLbl);
        
        return vbox;
    }
	
    /**
     * Exports the information from the Quarterly Report form into a csv file.
     * 
     * @author Breanna Wilson
     */
    private void exportReport()
    {
    	Stage stage = new Stage();
    	
    	if(startDate.getValue() == null)
    	{
    		PopUpMessage msg = new PopUpMessage("Start Date must be set.", stage);
        	msg.stage.showAndWait();
    		
    	}
    	else if(endDate.getValue() == null)
    	{
    		PopUpMessage msg = new PopUpMessage("End date must be set.", stage);
        	msg.stage.showAndWait();
    		
    	}
    	else
    	{
    		QuarterlyReportExportHelper helper = 
        			new QuarterlyReportExportHelper(personalCareTVCont.personalCareData,
        					reactiveCareTVCont.reactiveCareData, olderAdultNeedsTVCont.adultData, 
        					formatDate(endDate.getValue()), endDate.getValue().getYear() - 1);
        	helper.exportToCSV();
        	
        	
        	PopUpMessage msg = new PopUpMessage("Quarterly Report has been exported.", stage);
        	msg.stage.showAndWait();
    	}
    	
    }
}
