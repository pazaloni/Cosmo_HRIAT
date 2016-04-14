package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.SavedQuery;

public class SavedQueryTest
{
    String queryName1;
    String queryName2;
    String queryNameSame;
    String queryNameEmpty;

    String query1;
    String query2;

    @Before
    public void setUp() throws Exception
    {
        queryName1 = "test";
        queryName1 = "test2";
        queryNameSame = "test";
        queryNameEmpty = "";

        query1 = "SELECT * FROM Participant;";
        query2 = "SELECT * FROM Seizures;";
    }

    @Test
    public void testCreateSavedQuery()
    {
        assertTrue(SavedQuery.createSavedQuery(queryName1, query1).equals(""));
        assertTrue(SavedQuery.createSavedQuery(queryNameSame, query2).equals(
                "A query with that name has already been entered."));
        assertTrue(SavedQuery.createSavedQuery(queryNameEmpty, query2).equals(
                "You must give your query a name;"));
        assertTrue(SavedQuery.createSavedQuery(queryName2, query2).equals(""));
        SavedQuery.removeQuery(queryName1);
    }

    @Test
    public void testRemoveQuery()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testPopulateQuery()
    {
        fail("Not yet implemented");
    }

}
