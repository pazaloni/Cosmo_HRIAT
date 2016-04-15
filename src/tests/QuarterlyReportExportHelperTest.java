package tests;

import static org.junit.Assert.*;
import helpers.QuarterlyReportExportHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.OlderAdultsNeeds;
import core.PersonalCare;
import core.ReactiveCare;

public class QuarterlyReportExportHelperTest
{

    // The expected strings to be output for export
    String personalCare;
    String reactiveCare;
    String olderAdultNeeds;

    QuarterlyReportExportHelper exportHelper;

    @Before
    public void setUp() throws Exception
    {
        personalCare = ", Supports, Total Persons as of: 22-Apr-2016, Total For: 2015\n"
                + "Medical,Daily Insulin Injections,2,\n\n\n";
        reactiveCare = "Year, Participants Involved, Staff Members Involved, Total\n"
                + "2016,2,1,3\n\n\n";
        olderAdultNeeds = "Age, Long Term Care Facility, Elmwood Res, Luther Care, Other, "
                + "Total As Of: 22-Apr-2016, Total For: 2015\n>65,0,1,1,0,2,0\n\n\n";

        // the observablelists to be processed for export
        ObservableList personalCareList = FXCollections.observableArrayList();
        PersonalCare p = new PersonalCare("Medical",
                "Daily Insulin Injections", "2");
        personalCareList.add(p);

        ObservableList reactiveCareList = FXCollections.observableArrayList();
        ReactiveCare r = new ReactiveCare(2016, 2, 1);
        reactiveCareList.add(r);

        ObservableList olderAdultNeedsList = FXCollections
                .observableArrayList();
        OlderAdultsNeeds o = new OlderAdultsNeeds(">65", 0, 1, 1, 0, 2, 0);
        olderAdultNeedsList.add(o);

        exportHelper = new QuarterlyReportExportHelper(personalCareList,
                reactiveCareList, olderAdultNeedsList, "22-Apr-2016", 2015);
    }

    /**
     * Purpose: Test the createPersonalCareCSVString() method, and that it
     * exports a string as expected given the correct information.
     */
    @Test
    public void testCreatePersonalCareCSVString()
    {
        assertTrue(exportHelper.createPersonalCareCSVString().equals(
                personalCare));
    }

    /**
     * Purpose: Test the createReactiveCareCSVString() method, and that it
     * exports a string as expected given the correct information.
     */
    @Test
    public void testCreateReactiveCareCSVString()
    {
        assertTrue(exportHelper.createReactiveCareCSVString().equals(
                reactiveCare));
    }

    /**
     * Purpose: Test the createOlderAdultNeedsCSVString() method, and that it
     * exports a string as expected given the correct information.
     */
    @Test
    public void testCreateOlderAdultNeedsCSVString()
    {
        assertTrue(exportHelper.createOlderAdultNeedsCSVString().equals(
                olderAdultNeeds));
    }

}
