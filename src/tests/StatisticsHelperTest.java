package tests;

import static org.junit.Assert.*;
import helpers.StatisticsHelper;

import org.junit.Before;
import org.junit.Test;

public class StatisticsHelperTest
{

    StatisticsHelper sh;

    String where1;
    String where2;

    String operatorEqual;
    String operatorLike;
    String operatorBetween;
    String operatorGreaterThan;
    String operatorLessThan;

    String condition1;
    String condition2;
    String condition3;
    String condition4;

    @Before
    public void setUp() throws Exception
    {
        where1 = "Allergies";
        where2 = "Seizures";

        operatorEqual = "equal";
        operatorLike = "contains";
        operatorBetween = "between";
        operatorGreaterThan = "greater than";
        operatorLessThan = "less than";

        condition1 = "Pea";
        condition2 = "Peanuts";
        condition3 = "100";
        condition4 = "5 200";
        sh = new StatisticsHelper();
    }

    @Test
    public void testGenerateSQL()
    {

        assertTrue(sh
                .generateSQL(where1, operatorEqual, condition2)
                .equals("SELECT cosmoID, allergicTo, cosmoID, allergyType, description "
                        + "FROM Allergies WHERE allergicTo = \"Peanuts\" OR cosmoID = \"Peanuts\" "
                        + "OR allergyType = \"Peanuts\" OR description = \"Peanuts\""));
        assertTrue(sh
                .generateSQL(where1, operatorLike, condition1)
                .equals("SELECT cosmoID, allergicTo, cosmoID, allergyType, "
                        + "description FROM Allergies WHERE allergicTo LIKE \"*Pea*\" "
                        + "OR cosmoID LIKE \"*Pea*\" OR allergyType LIKE "
                        + "\"*Pea*\" OR description LIKE \"*Pea*\""));
        assertTrue(sh
                .generateSQL(where2, operatorGreaterThan, condition3)
                .equals("SELECT cosmoID, seizureID, cosmoID, seizureType, "
                        + "description, frequency, duration, aftermath, "
                        + "aftermathAssistance, emergencyTreatment, seizuresLastUpdated "
                        + "FROM Seizures WHERE seizureID > 100 OR cosmoID > 100 OR seizureType "
                        + "> 100 OR description > 100 OR frequency > 100 OR duration > 100 OR aftermath "
                        + "> 100 OR aftermathAssistance > 100 OR emergencyTreatment > 100 OR seizuresLastUpdated > 100"));
        assertTrue(sh.generateSQL(where2, operatorLessThan, condition3).equals(
                "SELECT cosmoID, seizureID, cosmoID, seizureType, description, "
                + "frequency, duration, aftermath, aftermathAssistance, "
                + "emergencyTreatment, seizuresLastUpdated FROM Seizures WHERE "
                + "seizureID < 100 OR cosmoID < 100 OR seizureType < 100 OR "
                + "description < 100 OR frequency < 100 OR duration < 100 OR aftermath "
                + "< 100 OR aftermathAssistance < 100 OR emergencyTreatment < 100 OR seizuresLastUpdated < 100"));
        assertTrue(sh.generateSQL(where1, operatorBetween, condition4).equals(
                "SELECT cosmoID, allergicTo, cosmoID, allergyType, description "
                + "FROM Allergies WHERE allergicTo BETWEEN \"5\" AND \"200\" OR "
                + "cosmoID BETWEEN \"5\" AND \"200\" OR allergyType BETWEEN \"5\""
                + " AND \"200\" OR description BETWEEN \"5\" AND \"200\""));
    }

}
