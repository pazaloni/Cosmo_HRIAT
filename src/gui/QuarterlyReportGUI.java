package gui;
import java.util.ArrayList;

import core.StaffAccount;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
/**
 * Purpose: The GUI for the statistics page to view stats as
 * well as generate quarterly reports.
 * @author CST215
 *
 */
public class QuarterlyReportGUI extends Application
{
	//the listview containing the categories for quarterly reports
	private ListView categoriesView;
	
	//the axis for the category
	private CategoryAxis xAxis = new CategoryAxis();
	
	//the axis for number of occurrences
	private NumberAxis yAxis = new NumberAxis();
	
	//the series of data
	private XYChart.Series dataSeries = new XYChart.Series();
	
	//the bar chat to display statistics
	private BarChart<String, Number> statsChart;
	
	//the main stage
	private Stage reportsMainStage;
	
	//the currently logged in staff member
	private StaffAccount loggedInStaff;
	
	//logs out the user
	private Button logout;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		reportsMainStageConstruct(stage, this.loggedInStaff);
	}
	
	public QuarterlyReportGUI() 
	{
		//do not need to do any work here
	}
	
	/**
	 * Purpose: Creates the statistics page and displays it.
	 * @param stage - the stage to display the page on
	 * @param loggedInStaff - the staff member that is currently logged in
	 */
	public void reportsMainStageConstruct(Stage stage, StaffAccount loggedInStaff)
	{
		this.loggedInStaff = loggedInStaff;
		
		reportsMainStage = stage;
		
		//the VBox containing all other displayable nodes
		VBox root = createMainVBox();
		
		reportsMainStage.setScene(new Scene(root, 875, 580));
		reportsMainStage.resizableProperty().set(true);
		reportsMainStage.show();
	}

	/**
	 * Purpose: Creates the VBox containing the list of categories
	 * to view statistics on, as well as the dates between which the
	 * records should be pulled from, and a button to generate the
	 * quarterly report.
	 * @return the VBox containing the list of categories, dates 
	 * between which the records should be pulled from, and the
	 * button to generate the quarterly report.
	 */
	private VBox createCategoriesBox()
	{
		VBox vbox = new VBox();
		categoriesView = new ListView<String>();
		
		//create a list of categories
		//TODO have this generate the categories based on
			//what is listed in the database
		ObservableList categoriesList = FXCollections.observableArrayList("Allergies",
				"Seizures", "Medications", "Vaccinations", "Heart Conditions", 
				"Lung Conditions", "Falls Over Time", "Age Groups", "Lift Usage",
				"Catheters", "Injections Given");
		
		//put the list of categories into the listview
		categoriesView.setItems(categoriesList);
		
		//set the size of the listview
		categoriesView.setMinWidth(170);
		categoriesView.setMaxWidth(170);
		categoriesView.setMinHeight(250);
		categoriesView.setMaxHeight(250);
		categoriesView.setFocusTraversable(false);
		
		Label timePeriod = new Label("Time Period");
		timePeriod.setFont(new Font("Arial", 18));
		
		vbox.setPadding(new Insets(5,5,5,5));
		
		
		Label startDateLabel = new Label("Start Date:");
		
		//this value will determine the date from which information will
			//be taken from
		DatePicker startDate = new DatePicker();
		
		Label endDateLabel = new Label("End Date:");
		
		//this value will determine the date from which information
			//will be taken until
		DatePicker endDate = new DatePicker();
		
		//generates the quarterly report
		Button generateReports = new Button("Generate Quarterly Report");
		
		generateReports.setOnAction(event -> {
			Stage stage = new Stage();
			QuarterlyReportPrevGUI gui = new QuarterlyReportPrevGUI(stage);
			gui.stage.setScene(new Scene(gui.mainVbox, 875, 580));
			gui.stage.showAndWait();
		});
		
		vbox.getChildren().addAll(categoriesView, timePeriod, startDateLabel,
				startDate, endDateLabel, endDate, generateReports);
		
		VBox.setMargin(timePeriod, new Insets(10,10,10,30));
		VBox.setMargin(generateReports, new Insets(10,10,10,5));
		
		return vbox;
		
	}
	
	/**
	 * 
	 * Purpose: Create the header area of the program.
	 * 
	 * 
	 * @return a BorderPane containing the header
	 */
	private BorderPane createHeaderPane() 
	{
		BorderPane logoAndLogin = new BorderPane();
		logoAndLogin.setPadding(new Insets(15, 12, 15, 12));
		logoAndLogin.setStyle("-fx-background-color: #FFFFFF;");

		// Logout button
		logout = new Button("Log Out");
		logout.setPrefSize(100, 20);

		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				reportsMainStage.close();
				LoginGUI test5 = new LoginGUI();
				try {
					test5.start(reportsMainStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		// logo image size
		ImageView logo = new ImageView(new Image("images/CosmoIconLong.png"));
		logo.setFitWidth(400);
		logo.setFitHeight(49);

		// set the image left and right
		logoAndLogin.setLeft(logo);
		logoAndLogin.setRight(logout);
		logoAndLogin.setFocusTraversable(false);
		return logoAndLogin;
	}
	
	/**
	 * 
	 * Purpose: Create the Tabs Pane for display
	 * 
	 * @return a TabPane object containing the
	 * tabs for the participants, forms and statistics page
	 */
	private TabPane createTabs() 
	{
		TabPane tabPane = new TabPane();

		// Create tabs names
		Tab participants = new Tab();
		Tab forms = new Tab();
		Tab stats = new Tab();

		// set text for tabs
		participants.setText("Participants");
		forms.setText("Forms");
		stats.setText("Stats");

		// set tabs to not be closable
		forms.closableProperty().set(false);
		participants.closableProperty().set(false);
		stats.closableProperty().set(false);

		// set the size of the tabs and add to the pane
		tabPane.setTabMinWidth(175);
		tabPane.getTabs().addAll(participants, forms, stats);
		
		//set initial selected tab to participants
		tabPane.getSelectionModel().select(stats);
		
		//set the changed property
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){

			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1,
					Tab mostRecentlySelectedTab) {
				if(mostRecentlySelectedTab.equals(forms))
				{
					//switch to forms GUI
				}
				else if(mostRecentlySelectedTab.equals(participants))
				{
					MedicalStaffMainPageGUI gui = new MedicalStaffMainPageGUI();
					gui.medMainPageConstruct(reportsMainStage, loggedInStaff);
				}
				
			}
			
		});
		
		tabPane.setMinHeight(29);
		tabPane.setFocusTraversable(false);
		return tabPane;
	}
	
	/**
	 * Purpose: Create the Bar Chart to display the
	 * statistical information
	 * @return BarChart the chart to display the statistics
	 */
	private BarChart createGraphBox()
	{
		//TODO pull the information displayed from the database
			//based on the seleted category
		dataSeries.getData().add(new XYChart.Data("Peanuts", 162));
		dataSeries.getData().add(new XYChart.Data("Pollen", 45));
		dataSeries.getData().add(new XYChart.Data("Gluten", 66));
		dataSeries.getData().add(new XYChart.Data("Blueberries", 12));
		dataSeries.getData().add(new XYChart.Data("Chicken", 2));
		dataSeries.getData().add(new XYChart.Data("Wheat", 290));
		dataSeries.getData().add(new XYChart.Data("Dust", 91));
		
		
		//the axis for the category
		xAxis = new CategoryAxis();
		//the axis for numbers in relation to the category
		yAxis = new NumberAxis();
		
		//TODO set these labels based on the selected category
		xAxis.setLabel("Allergy");
		yAxis.setLabel("# of participants");
		
		
		BarChart<String, Number> chart = 
				new BarChart<String, Number>(xAxis, yAxis);
		
		chart.getData().add(dataSeries);
		
		chart.legendVisibleProperty().set(false);;
		
		chart.setMaxWidth(600);
		chart.setMinWidth(600);
		
		return chart;
	}
	
	/**
	 * Purpose: Creates the VBox containing all of the other nodes
	 * on the page.
	 * @return VBox the VBox holding the bar chart, as well as
	 * categories list, datepickers, and generate reports button.
	 */
	private VBox createMainVBox()
	{
		VBox vbox = new VBox();
		
		//header
		BorderPane header = createHeaderPane();
		//tab pane
		TabPane tabs = createTabs();
		
		VBox categoriesBox = createCategoriesBox();
		
		BarChart<String, Number> dataChart = createGraphBox();
		
		HBox infoBox = new HBox();
		
		infoBox.getChildren().addAll(dataChart, categoriesBox);
		
		HBox.setMargin(categoriesBox, new Insets(5,5,5,50));
		
		vbox.getChildren().addAll(header, tabs, infoBox);
		
		return vbox;
	}
	
}
