import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Purpose: Provide all database needs to the system
 * 
 * 
 * @author TEAMP CIMP
 * @version 1.0
 */
public class DatabaseHelper
{

    private final String DB_PATH = "G:/CIMPDatabase.accdb";
    private Connection conn;

    public DatabaseHelper()
    {
        connect();
    }

    /**
     * Purpose: To establish a connection to the database
     * 
     * @throws SQLException
     */
    public void connect()
    {

        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + DB_PATH);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( ClassNotFoundException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * Purpose: close the database connection;
     */
    public void disconnect()
    {
        try
        {
            conn.close();
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[][] createArray( String tableName, String[] values )
    {
        Statement s = null;
        ResultSet rs = null;
        String[][] result = new String[values.length][values.length];

        String query = "SELECT * FROM " + tableName;
        try
        {
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            for ( int i = 0; i < values.length; i++ )
            {
                // set the field name of the array
                result[i][0] = rsmd.getColumnLabel(i + 1);

                // set the values
                result[i][1] = values[i];

            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }

    /**
     * 
     * Purpose: Perform a SELECT query on the database
     * 
     * @param columnList list of columns to be selected
     * @param tableList list of tables to select from
     * @param condition optional sql condition to select with
     * @param sort optional sql sort statement to supply
     * @return ResultSet rs The result of the SELECT query
     */
    public ResultSet select( String columnList, String tableList,
            String condition, String sort )
    {

        Statement s = null;
        ResultSet rs = null;
        String query = "SELECT " + columnList + " FROM " + tableList;

        try
        {
            s = conn.createStatement();

            // If a condition is specified, add it to the query
            if ( !condition.equals("") )
            {
                query += " WHERE " + condition;
            }

            // If a sort order is specified, add it to the query
            if ( !sort.equals("") )
            {
                query += " ORDER BY " + sort;
            }

            //System.out.println(query);
            // execute the query
            rs = s.executeQuery(query);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return the resultset
        return rs;
    }

    /**
     * 
     * Purpose: This method will add a new record to the specified table
     * 
     * @param newRecord A 2d array of the field names and the values to be
     *            inserted
     * @param tableName The name of the table to add the record to
     * @return boolean True if rows were affected, false if 0 rows were affected
     *         (failed insert)
     */
    public boolean insert( String[] values, String tableName )
    {
        String[][] newRecord = this.createArray(tableName, values);

        Statement s = null;
        String fieldList = "( ";
        String valueList = "( ";

        // add each field and value to their strings
        for ( int r = 0; r < newRecord.length; r++ )
        {
            fieldList += newRecord[r][0] + ", ";
            valueList += "'" + newRecord[r][1] + "', ";
        }

        // Trim off the final comma and add the parentheses
        fieldList = fieldList.substring(0, fieldList.length() - 2) + " )";
        valueList = valueList.substring(0, valueList.length() - 2) + " )";

        // perform the insertion

        String insertStatement = "INSERT INTO " + tableName + " " + fieldList
                + " VALUES " + valueList;

        System.out.println(insertStatement);

        int rows = 0;
        try
        {
            s = conn.createStatement();
            rows = s.executeUpdate(insertStatement);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rows != 0;
    }

    /**
     * 
     * Purpose: This method will add a new record to the specified table,
     * specifying the field names
     * 
     * @param newRecord A 2d array of the field names and the values to be
     *            inserted
     * @param tableName The name of the table to add the record to
     * @return boolean True if rows were affected, false if 0 rows were affected
     *         (failed insert)
     */
    public boolean insert( String[][] newRecord, String tableName )
    {
        Statement s = null;
        String fieldList = "( ";
        String valueList = "( ";

        // add each field and value to their strings
        for ( int r = 0; r < newRecord.length; r++ )
        {
            fieldList += newRecord[r][0] + ", ";

            if ( newRecord[r][1] != null )
            {
                valueList += "'" + newRecord[r][1] + "', ";
            }
           
            else
            {
                valueList += "" + newRecord[r][1] + ", ";
            }
        }

        // Trim off the final comma and add the parentheses
        fieldList = fieldList.substring(0, fieldList.length() - 2) + " )";
        valueList = valueList.substring(0, valueList.length() - 2) + " )";

        // perform the insertion

        String insertStatement = "INSERT INTO " + tableName + " " + fieldList
                + " VALUES " + valueList;
        System.out.println(insertStatement);

        int rows = 0;
        try
        {
            s = conn.createStatement();
            rows = s.executeUpdate(insertStatement);
            s.close();
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rows != 0;
    }

    /**
     * 
     * Purpose: To update a row in a table
     * 
     * @param values a 2d array of field names and values to change in a row
     * @param tableName the table that the row is located in
     * @param primaryKey the primary key of the table
     * @return boolean True if rows were affected, false if 0 rows were affected
     *         (failed update)
     */
    public boolean update( String[] values, String tableName, String primaryKey )
    {
        String[][] updateRecord = this.createArray(tableName, values);

        Statement s = null;

        String updateStatement = "Update " + tableName + " SET ";

        // add each field and value to their strings
        for ( int r = 0; r < values.length; r++ )
        {
            String fieldName = "" + updateRecord[r][0];
            String value = "" + updateRecord[r][1];
            // If this isn't the primary key
            if ( fieldName != primaryKey )
            {
                // Add the column and value to be changed to the query statement
                updateStatement += fieldName + "='" + value + "', ";
            }
        }

        // Remove trailing comma
        updateStatement = updateStatement.substring(0,
                updateStatement.length() - 2);

        // specify which record to update
        // TODO fix primary key
        updateStatement += " WHERE " + updateRecord[0][0] + "='" + primaryKey
                + "'";

        int rows = 0;

        try
        {
            s = conn.createStatement();
            rows = s.executeUpdate(updateStatement);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return whether update failed or not
        return rows != 0;
    }

    /**
     * 
     * Purpose: To update a row in a table
     * 
     * @param values a 2d array of field names and values to change in a row
     * @param tableName the table that the row is located in
     * @param primaryKey the primary key of the table
     * @return boolean True if rows were affected, false if 0 rows were affected
     *         (failed update)
     */
    public boolean update( String[][] values, String tableName,
            String primaryKey )
    {
        Statement s = null;

        String updateStatement = "Update " + tableName + " SET ";

        // add each field and value to their strings
        for ( int r = 0; r < values.length; r++ )
        {
            String fieldName = "" + values[r][0];
            String value = "" + values[r][1];
            // If this isn't the primary key
            if ( fieldName != primaryKey )
            {
                // Add the column and value to be changed to the query statement
                updateStatement += fieldName + "='" + value + "', ";
            }
        }

        // Remove trailing comma
        updateStatement = updateStatement.substring(0,
                updateStatement.length() - 2);

        // specify which record to update
        // TODO fix primary key
        updateStatement += " WHERE " + values[0][0] + "='" + primaryKey + "'";
        System.out.println(updateStatement);

        int rows = 0;

        try
        {
            s = conn.createStatement();
            rows = s.executeUpdate(updateStatement);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return whether update failed or not
        return rows != 0;
    }

    /**
     * 
     * Purpose: To delete from a table on a condition
     * 
     * @param tableName the name of the table
     * @param condition the condition to delete on
     * @return boolean whether the delete was successful
     */
    public boolean delete( String tableName, String condition )
    {
        Statement s = null;
        String deleteStatement = "DELETE FROM " + tableName + " WHERE "
                + condition;

        int rows = 0;

        try
        {
            s = conn.createStatement();
            rows = s.executeUpdate(deleteStatement);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        return rows != 0;
    }

    /**
     * Purpose: This will take in the result set and use it to populate the
     * Observable list, which will be used to display the rows in the tableview.
     * 
     * @param rs The result set that will be used to populate the observable
     *            list
     * @return The Observable list that will be used to generate the table
     * 
     * @author Breanna Wilson cst215 Steven Palchinski cst209
     */
    public ObservableList<String> displayRows( ResultSet rs )
    {
        ObservableList<String> rows = FXCollections.observableArrayList();

        try
        {
            ArrayList<String> staffInfo = new ArrayList<String>();

            while ( rs.next() )
            {
                for ( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ )
                {
                    staffInfo.add(rs.getString(i));
                }
                rows.addAll(staffInfo);
            }
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rows;
    }

    /**
     * A method for entering an event into the activity log.
     * 
     * @param who The user who triggered the event
     * @param date When the event happened
     * @param event What the event was
     * @param db The database to insert into
     */
    public void activtyLogEntry( String who, String event, DatabaseHelper db )
    {
        String whenString = "";

        String activityValues[][] = new String[2][2];

        activityValues[0][0] = "Who";
        activityValues[1][0] = "Event";

        activityValues[0][1] = who;
        activityValues[1][1] = event;

        db.insert(activityValues, "ActivityLog");
    }

}
