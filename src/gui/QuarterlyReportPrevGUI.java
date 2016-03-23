package gui;

import controllers.OlderAdultNeedsTableViewController;
import controllers.PersonalCareTableViewController;
import controllers.ReactiveCareTableViewController;
import controllers.HealthPromotionTableViewController;


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

public class QuarterlyReportPrevGUI extends Application {

	
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	
	private OlderAdultNeedsTableViewController olderAdultNeedsTVCont;
	private PersonalCareTableViewController personalCareTVCont;
	private ReactiveCareTableViewController reactiveCareTVCont;
	private HealthPromotionTableViewController healthPromotionTVCont;
	
	private Button exportBtn;
	
	public VBox mainVbox;
	
	public Stage stage;
	private Scene scene;
	
	DatePicker startDate;
	DatePicker endDate;
	
	public QuarterlyReportPrevGUI(Stage stage)
	{
		this.stage = stage;
		mainVbox = new VBox();
		stage.setTitle("Generate Quarterly Report");

		mainVbox.getChildren().addAll(createHeader(),
				createOlderAdultNeedsArea(), createPersonalHealthCareArea(), createReactiveCareArea(), createHealthPromotionArea());
		
	}
	
    @Override
    public void start( Stage stage ) throws Exception
    {
		
    }
    
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
		
		GridPane dateContainer = new GridPane();
		
		Label startDateLbl = new Label("Start Date:");
		Label endDateLbl = new Label("End Date:");
		
		startDate = new DatePicker();
		endDate = new DatePicker();
		
		dateContainer.add(startDateLbl, 0, 0);
		dateContainer.add(startDate, 0, 1);
		dateContainer.add(endDateLbl, 1, 0);
		dateContainer.add(endDate, 1, 1);
		
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
    
    private VBox createReactiveCareArea()
    {
       VBox vbox = new VBox();
        
        Label areaLbl = new Label("Reactive Care");
        areaLbl.setPadding(new Insets(5,5,5,5));
        areaLbl.setFont(new Font("Arial", 24));
        
        reactiveCareTVCont = new ReactiveCareTableViewController();
        
        vbox.getChildren().addAll(areaLbl);
        
        
        
        return vbox;
    }
    
    private VBox createHealthPromotionArea()
    {
       VBox vbox = new VBox();
        
        Label areaLbl = new Label("Health Promotion Activities");
        areaLbl.setPadding(new Insets(5,5,5,5));
        areaLbl.setFont(new Font("Arial", 24));
        
        healthPromotionTVCont = new HealthPromotionTableViewController();
        
        vbox.getChildren().addAll(areaLbl);
        
        
        
        return vbox;
    }
	
    private void exportReport()
    {
    	
    }
}
