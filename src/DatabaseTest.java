import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest
{

    public static void main(String[] args)
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        System.out.println("select: ");
        ResultSet rs = db.select("UserName, lastName, firstName", "Staff", "",
                "");
        try
        {
            while (rs.next())
            {
                System.out.println(rs.getString(1) + ", " + rs.getString(2)
                        + ", " + rs.getString(3));
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("insert:");
        String[] values = new String[6];

        values[0] = "testUser3";
        values[1] = "user2";
        values[2] = "test2";
        values[3] = "testUser@test.test2";
        values[4] = "password2";
        values[5] = "2";

        // db.insert(values, "Staff");

        String[][] update = new String[1][2];

        update[0][0] = "lastName";
        update[0][1] = "poop";

        db.update(update, "Staff", "testUser2");

    }

}
