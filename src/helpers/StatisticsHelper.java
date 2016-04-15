package helpers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Helper for the statisticsTabGUI
 * 
 * @author cst207
 *
 */
public class StatisticsHelper
{

    /**
     * Takes the three parameters and generates the appropriate SQL code
     * 
     * @param searchFor
     *            the table you are looking in
     * @param comparison
     *            the operator you are using
     * @param condition
     *            the limiter on your query results
     * @return
     */
    public String generateSQL( String searchFor, String comparison,
            String condition )
    {
        String query = "SELECT cosmoID";
        query = "SELECT cosmoID";
        String[] conditionArray = null;
        switch ( searchFor )
        {
            case "Allergies":

                conditionArray = getTableHeadings("Allergies");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Allergies WHERE ";
                break;
            case "Seizures":

                conditionArray = getTableHeadings("Seizures");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Seizures WHERE ";
                break;
            case "Medications":

                conditionArray = getTableHeadings("Medication");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Medication WHERE ";
                break;
            case "Vaccinations":

                conditionArray = getTableHeadings("Vaccination");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Vaccination WHERE ";
                break;
            case "Conditions":

                conditionArray = getTableHeadings("Conditions");
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += ", " + conditionArray[i];
                }
                query += " FROM Conditions WHERE ";
                break;
        }
        switch ( comparison )
        {
            case "contains":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " LIKE \"*" + condition
                            + "*\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "equals":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " = \"" + condition + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "greater than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " > " + condition + "";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "less than":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i] + " < " + condition + "";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
            case "between":
                for ( int i = 0; i < conditionArray.length; i++ )
                {
                    query += conditionArray[i]
                            + " BETWEEN \""
                            + condition.substring(0, condition.indexOf(" "))
                            + "\" AND \""
                            + condition.substring(condition.indexOf(" ") + 1,
                                    condition.length()) + "\"";
                    if ( i < conditionArray.length - 1 )
                    {
                        query += " OR ";
                    }
                }
                break;
        }

        return query;

    }

    /**
     * Method for getting headings of a table from the database
     * 
     * @param tableName
     *            the table whose headings we are requesting
     * @return
     */
    private String[] getTableHeadings( String tableName )
    {
        
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            ResultSet rs = db.directSelect("Select * from " + tableName);
            String[] result = new String[0];
            try
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                result = new String[columnCount];
                for ( int i = 1; i <= result.length; i++ )
                {
                    result[i - 1] = rsmd.getColumnLabel(i);
                }
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }
            
        return result;
    }
}
