package core;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;

import com.healthmarketscience.jackcess.DatabaseBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Object for saving queries
 * @author cst207
 *
 */
public class SavedQuery
{
    /**
     * method for adding a new query to the database
     * @param queryName the name of the query to be added
     * @param query the contents of the query to be added
     * @return
     */
    public static String createSavedQuery( String queryName, String query )
    {
        String result = "";
        if ( queryName.isEmpty() )
        {
            result = "You must give your query a name";
        }
        else
        {
            
            DatabaseHelper db = new DatabaseHelper();
            
            ResultSet queryExists = db.select("count(*)",
                    "savedQuery", "queryName = '" + queryName + "'", "");

            int recordExists = 0;

            try
            {
                queryExists.next();
                recordExists = queryExists.getInt(1);
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }
            if ( recordExists == 0 )
            {
                String[] values = new String[] { queryName, query };
                db.insert(values, "SavedQuery");
            }
            else
            {
                result = "That name already exists.";
            }
           
        }
        return result;
    }

    /**
     * Method to remove queries from the database
     * @param queryName the name of the query to remove
     */
    public static void removeQuery( String queryName )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        db.delete("savedQuery", "QueryName= \"" + queryName + "\"");
        db.disconnect();

        
    }
    /**
     * method for getting the contents of queries form the database  
     * @param queryName the name of the query to get the contents of 
     * @return
     */
    public static String populateQuery( String queryName )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.select("query", "SavedQuery", "queryName ='"
                + queryName + "'", "");
        try
        {
            rs.next();
            result = rs.getString(1);

        }
        catch ( SQLException e )
        {

        }
        return result;
    }
}
