import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * Purpose: display the activity log table
 * 
 * 
 * @author TEAM CIMP!
 * @version 1.0
 */
public class ActivityLogTableViewController
{

    // Table
    protected TableView<ActivityLog> activityLogTable = new TableView<ActivityLog>();

    // Table columns
    protected TableColumn<ActivityLog, String> whoColumn = new TableColumn<ActivityLog, String>(
            "Who");
    protected TableColumn<ActivityLog, String> whenColumn = new TableColumn<ActivityLog, String>(
            "When");
    protected TableColumn<ActivityLog, String> eventColumn = new TableColumn<ActivityLog, String>(
            "Event");

    // database helper
    private DatabaseHelper db;

    private ObservableList<ActivityLog> activityLogData = FXCollections
            .observableArrayList();

    /**
     * 
     * Constructor for the ActivityLogTableViewController class.
     */
    public ActivityLogTableViewController()
    {
        // When the activity log is instantiated, then pull all the information
        // from the database.
        retrieveActiviyLogData();
        this.initialize();
        activityLogTable.setItems(activityLogData);
        activityLogTable.setFocusTraversable(false);
    }

    /**
     * 
     * Purpose: Query the database for all the activity-log information
     */
    private void retrieveActiviyLogData()
    {
        db = new DatabaseHelper();

        db.connect();

        // Select all everything and order it desceding
        ResultSet activityLogResults = db.select("*", "ActivityLog", "",
                "Timestamp DESC");

        String who;
        String when;
        String event;

        try
        {
            while ( activityLogResults.next() )
            {
                who = activityLogResults.getString(1);
                when = activityLogResults.getString(2);
                event = activityLogResults.getString(3);

                // Remove extra 0's at the end of the timestamp
                when = when.substring(0, when.length() - 7);

                ActivityLog currentLog = new ActivityLog(who, when, event);

                // Add the log to the list
                activityLogData.add(currentLog);

            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            System.out.println("Failed to query activity log informaiton");
        }

        db.disconnect();
    }

    /**
     * 
     * Purpose: to create the table and the columns
     */
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        whoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        whoColumn.setMinWidth(100);
        whoColumn.setResizable(false);

        whenColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getWhen());
        whenColumn.setMinWidth(200);
        whenColumn.setResizable(false);

        eventColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getEvent());
        eventColumn.setMinWidth(100);
        eventColumn.setResizable(false);

        activityLogTable.getColumns().addListener(
                new ListChangeListener<Object>()
                {
                    @Override
                    public void onChanged( Change change )
                    {
                        change.next();
                        // if the column was changed
                        if ( change.wasReplaced() )
                        {
                            // clear all columns
                            activityLogTable.getColumns().clear();
                            // re-add the columns in order
                            activityLogTable.getColumns().addAll(whoColumn,
                                    whenColumn, eventColumn);
                        }
                    }
                });
        activityLogTable.getColumns()
                .addAll(whoColumn, whenColumn, eventColumn);

    }

}