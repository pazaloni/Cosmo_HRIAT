package tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;
import helpers.ManagePhysicianAccountHelper;

import org.junit.Test;

import core.Physician;

public class TestEditPhysician
{
    // The physician helper used to actually edit the physicians in the database
    ManagePhysicianAccountHelper managePhys = new ManagePhysicianAccountHelper();

    // db helper used to initially access the database and make select
    // statements
    DatabaseHelper db = new DatabaseHelper();

    @Test
    public void test()
    {
        db.connect();

        Physician originalPhysician = null;
        // select the physician with an id of 1
        // we will edit this exact physician and then select it again
        // to see if the changes were made to the exact same physician record
        // in the database
        ResultSet rs = db.select("physicianID, firstName, lastName, phone",
                "Physician", "physicianID = 1", "");

        String lastName = "";
        String firstName = "";
        String phone = "";
        String PhysID = "";

        // append the values from the db columns to their respected variables
        try
        {
            while ( rs.next() )
            {
                PhysID = rs.getString(1);

                firstName = rs.getString(2);

                lastName = rs.getString(3);

                phone = rs.getString(4);

            }
        }
        catch ( SQLException e )
        {

            e.printStackTrace();
        }
        // using the values pullled from the database fo rphys id =1, make a
        // physician object
        originalPhysician = new Physician(PhysID, firstName, lastName, phone);

        // change the physicians first name to something other than what it is
        firstName = "Markyd1";
        lastName = "Test1d";
        phone = "9999991995";
        // helper will pass in the changes and update the database
        managePhys.editUser(firstName, lastName, phone, PhysID);

        Physician editedPhysician = null;
        // select the exact same phys, id= 1
        ResultSet rs2 = db.select("physicianID, firstName, lastName, phone",
                "Physician", "physicianID = 1", "");

        String lastName2 = "";
        String firstName2 = "";
        String phone2 = "";
        String PhysID2 = "";

        try
        {
            while ( rs2.next() )
            {
                PhysID2 = rs2.getString(1);

                firstName2 = rs2.getString(2);

                lastName2 = rs2.getString(3);

                phone2 = rs2.getString(4);

            }
        }
        // if this fail, print the stack trace
        catch ( SQLException e )
        {

            e.printStackTrace();
        }
        // make a newphys with the updated information
        editedPhysician = new Physician(PhysID2, firstName2, lastName2, phone2);

        System.out.println("original " + originalPhysician.getFirstName());

        System.out.println("New " + editedPhysician.getFirstName());
        // if the ids are identical, then they are the same record
        if ( originalPhysician.getPhysID().equals(editedPhysician.getPhysID()) )
        {
            // if the names are the same, the test failed
            assertFalse(originalPhysician.getFirstName().equals(
                    editedPhysician.getFirstName()));
            assertFalse(originalPhysician.getLastName().equals(
                    editedPhysician.getLastName()));
            assertFalse(originalPhysician.getPhysPhone().equals(
                    editedPhysician.getPhysPhone()));

            assertTrue(editedPhysician.getFirstName().equals(firstName2));
            assertTrue(editedPhysician.getLastName().equals(lastName2));
            assertTrue(editedPhysician.getPhysPhone().equals(phone2));

            managePhys.editUser(originalPhysician.getFirstName(),
                    originalPhysician.getLastName(),
                    originalPhysician.getPhysPhone(), PhysID);

        }

    }

}
