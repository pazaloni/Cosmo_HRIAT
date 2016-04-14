package core;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;

import com.healthmarketscience.jackcess.DatabaseBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SavedQuery
{

    // private String name;
    // private String query;

    /*
     * public SavedQuery(String name, String query) { this.name = name;
     * this.query = query;
     * 
     * }
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

            // check if there is already a medication with that name for
            // that user
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
                result = "A query with that name has already been entered.";
            }
           
        }
        return result;
    }

    public static void removeQuery( String queryName )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        db.delete("savedQuery", "QueryName= \"" + queryName + "\"");
        db.disconnect();

        
    }
    
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
