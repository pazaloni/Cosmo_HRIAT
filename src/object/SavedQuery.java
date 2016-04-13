package object;

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
    public static String createSavedQuery( String name, String query )
    {
        String result = "";
        if ( name.isEmpty() )
        {
            result = "You must give your query a name";
        }
        else
        {
            
            DatabaseHelper db = new DatabaseHelper();
            /*
            ResultSet queryExists = db.select("count(*)",
                    "medication", "medicationName = '" + medicationName
                            + "' AND cosmoID = '" + cosmoID + "'", "");

            int recordExists = 0;

            // check if there is already a medication with that name for
            // that user
            try
            {
                medicationExists.next();
                recordExists = medicationExists.getInt(1);
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }
            // If no medication with that name exists, then insert the
            // medication
            if ( recordExists == 0 )
            {
            String[] values = new String[] { name, query };
            
            
            db.update(values, "SavedQuery", "queryName");
            
           */
        }
        return result;
    }
}
