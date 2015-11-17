import java.sql.*;

public class DatabaseHelper
{
    private final String DB_PATH = "G:/CIMPDatabase.accdb";
    public Connection conn;

    /**
     * Purpose: To establish a connection to the database
     * @throws SQLException
     */
    public void connect() throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:ucanaccess://" + DB_PATH);
    }

    public String select() throws SQLException
    {
        Statement s = null;
        ResultSet rs = null;
        String result = null;

        s = conn.createStatement();

        rs = s.executeQuery("SELECT [UserName] FROM [Staff]");

        while (rs.next())
        {
            result += (rs.getString(1));
        }

        return result;
    }
}
