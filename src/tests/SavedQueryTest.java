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
        queryName2 = "test2";
        queryNameSame = "test";
        queryNameEmpty = "";

        query1 = "SELECT * FROM Participant;";
        query2 = "SELECT * FROM Seizures;";
    }

    @Test
    public void testCreateSavedQuery()
    {
        SavedQuery.removeQuery(queryName1);
        SavedQuery.removeQuery(queryName2);
        assertTrue(SavedQuery.createSavedQuery(queryName1, query1).equals(""));
        assertTrue(SavedQuery.createSavedQuery(queryNameSame, query2).equals(
                "A query with that name has already been entered."));
        assertTrue(SavedQuery.createSavedQuery(queryNameEmpty, query2).equals(
                "You must give your query a name"));
        assertTrue(SavedQuery.createSavedQuery(queryName2, query2).equals(""));
        SavedQuery.removeQuery(queryName1);
        SavedQuery.removeQuery(queryName2);
    }

    @Test
    public void testRemoveQuery()
    {
        SavedQuery.removeQuery(queryName1);
        SavedQuery.createSavedQuery(queryName1, query1).equals("");
        
        SavedQuery.removeQuery(queryName1);
        assertTrue(SavedQuery.populateQuery(queryName1).equals(""));
        
    }

    @Test
    public void testPopulateQuery()
    {
        SavedQuery.removeQuery(queryName1);
        SavedQuery.removeQuery(queryName2);
        SavedQuery.createSavedQuery(queryName1, query2);
        SavedQuery.createSavedQuery(queryName2, query1);
        assertTrue(SavedQuery.populateQuery(queryName1).equals(query2));
        SavedQuery.removeQuery(queryName1);
        SavedQuery.removeQuery(queryName2);
    }

}
