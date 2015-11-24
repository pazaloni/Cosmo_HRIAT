import java.sql.*;

public class DatabaseHelper
{
    private final String DB_PATH = "G:/CIMPDatabase.accdb";
    private Connection conn;

    /**
     * Purpose: To establish a connection to the database
     * 
     * @throws SQLException
     */
    public void connect()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + DB_PATH);
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
     * @param columnList
     *            list of columns to be selected
     * @param tableList
     *            list of tables to select from
     * @param condition
     *            optional sql condition to select with
     * @param sort
     *            optional sql sort statement to supply
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
     * @param newRecord
     *            A 2d array of the field names and the values to be inserted
     * @param tableName
     *            The name of the table to add the record to
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
     * Purpose: To update a row in a table
     * 
     * @param values
     *            a 2d array of field names and values to change in a row
     * @param tableName
     *            the table that the row is located in
     * @param primaryKey
     *            the primary key of the table
     * @return boolean True if rows were affected, false if 0 rows were affected
     *         (failed update)
     */
    public boolean update( Array[][] values, String tableName, String primaryKey )
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
        // TODO make sure values[0][0] is correct
        updateStatement += " WHERE " + primaryKey + "='" + values[0][0] + "'";

        int rows = 0;

        try
        {
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
     * @param tableName
     *            the name of the table
     * @param condition
     *            the condition to delete on
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
            rows = s.executeUpdate(deleteStatement);
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rows != 0;
    }

}
