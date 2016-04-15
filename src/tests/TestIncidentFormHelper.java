package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import helpers.IncidentReportFormHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Witness;

public class TestIncidentFormHelper
{

    IncidentReportFormHelper irfh;

    String cosmoID;
    LocalDate dateOfIncident;
    String timeOfIncident;
    String locationOfIncident;
    String protectiveEquipment;
    String incidentDescription;
    String contributingFactors;
    String reportedTo;
    LocalDate dateReported;
    String timeReported;
    String verballyReported;
    LocalDate verbalReportDate;
    String verbalReportTime;
    LocalDate dateReportWritten;
    String timeReportWritten;
    String reportedWrittenBy;
    String[] injuredBodyAreas;
    String[] typesOfInjuries;
    ArrayList<Witness> witnesses;

    @Before
    public void setUp() throws Exception
    {

        cosmoID = "123";
        dateOfIncident = LocalDate.now();
        timeOfIncident = "12:20";
        locationOfIncident = "Lunch room";
        protectiveEquipment = "none";
        incidentDescription = "Microwave fell on toes";
        contributingFactors = "microwave to close to edge of counter";
        reportedTo = "Marla Klaassen";
        dateReported = LocalDate.now();
        timeReported = "2:20";
        verballyReported = "Hank Snow";
        verbalReportDate = LocalDate.now();
        verbalReportTime = "4:20";
        dateReportWritten = LocalDate.now();
        timeReportWritten = "5:20";
        reportedWrittenBy = "Hank Snow";
        injuredBodyAreas = new String[3];
        injuredBodyAreas[0] = "Chest";
        injuredBodyAreas[1] = "Head";
        injuredBodyAreas[2] = "Fingers";
        typesOfInjuries = new String[1];
        typesOfInjuries[0] = "Burn";
        witnesses = new ArrayList<Witness>();
        witnesses.add(new Witness("Ron New", "S"));
    }

    @Test
    public void testSaveIncidentReport()
    {
        irfh = new IncidentReportFormHelper();

        boolean result = irfh
                .saveIncidentInfo(cosmoID, dateOfIncident, timeOfIncident,
                        locationOfIncident, protectiveEquipment,
                        incidentDescription, contributingFactors, reportedTo,
                        dateReported, timeReported, verballyReported,
                        verbalReportDate, verbalReportTime, dateReportWritten,
                        timeReportWritten, reportedWrittenBy, injuredBodyAreas,
                        typesOfInjuries, witnesses);

        assertTrue(result);
    }

}
