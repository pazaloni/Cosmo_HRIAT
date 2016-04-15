package helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;

import core.OlderAdultsNeeds;
import core.PersonalCare;
import core.ReactiveCare;
import javafx.collections.ObservableList;

/**
 * Purpose: Used to export the quarterly report infomration into a formatted csv
 * file.
 * 
 * @author Breanna Wilson
 *
 */

public class QuarterlyReportExportHelper
{
    // the lists of data to be put into the report
    private ObservableList<PersonalCare> personalCareList;
    private ObservableList<ReactiveCare> reactiveCareList;
    private ObservableList<OlderAdultsNeeds> olderAdultNeedsList;
    private String date;
    private int prevYear;

    /**
     * Initializes the values for all of the lists and the dates needed to
     * export onto the quarterly report.
     * 
     * @param personalCareList An observablelist of personal care objects to
     *            populate the personal care table,
     * @param reactiveCareList An observablelist of reactive care objects to
     *            populate the personal care table
     * @param olderAdultNeedsList An observablelist of olderAdultNeeds objects
     *            to populate the older adults needs table
     * @param formattedDate A string representing the formatted date
     * @param prevYear The previous year
     * 
     * @author Breanna Wilson
     */
    public QuarterlyReportExportHelper(ObservableList personalCareList,
            ObservableList reactiveCareList,
            ObservableList olderAdultNeedsList, String formattedDate,
            int prevYear)
    {
        this.personalCareList = personalCareList;
        this.reactiveCareList = reactiveCareList;
        this.olderAdultNeedsList = olderAdultNeedsList;
        this.date = formattedDate;
        this.prevYear = prevYear;
    }

    /**
     * Writes the information given into tables in a csv file
     * 
     * @author Breanna Wilson
     */
    public void exportToCSV()
    {
        String exportStr = "Older Adults Needs\n"
                + createOlderAdultNeedsCSVString() + "Personal Care\n"
                + createPersonalCareCSVString() + "Reactive Care\n"
                + createReactiveCareCSVString()
                + "\nHealth Promotion Activities\n\nHealth Related Supplies";

        try
        {
            PrintWriter out = new PrintWriter("quarterlyReport" + this.date
                    + ".csv");
            out.print(exportStr);
            out.close();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * Takes the information from the personalCare observablelist, and formats
     * it into a table using the information given
     * 
     * @return A string representing the table of information in a comma
     *         delimited format
     * 
     * @author Breanna Wilson
     */
    public String createPersonalCareCSVString()
    {
        String csvStr = ", Supports, Total Persons as of: " + date
                + ", Total For: " + prevYear + "\n";
        Iterator<PersonalCare> iter = personalCareList.iterator();
        PersonalCare current = null;
        while ( iter.hasNext() )
        {
            current = iter.next();

            csvStr += current.getType() + ",";
            csvStr += current.getSupports() + ",";
            csvStr += current.getTotalAsOf() + ",";
            csvStr += current.getTotalForLastYear() + "\n";

        }

        csvStr += "\n\n";

        return csvStr;
    }

    /**
     * Takes the information from the reactiveCare observablelist, and formats
     * it into a table using the information given
     * 
     * @return A string representing the table of information in a comma
     *         delimited format
     * 
     * @author Breanna Wilson
     */
    public String createReactiveCareCSVString()
    {
        String csvStr = "Year, Participants Involved, Staff Members Involved, Total\n";
        Iterator<ReactiveCare> iter = reactiveCareList.iterator();
        ReactiveCare current = null;

        while ( iter.hasNext() )
        {
            current = iter.next();

            csvStr += current.getYear() + ",";
            csvStr += current.getParticipants() + ",";
            csvStr += current.getStaff() + ",";
            csvStr += current.getTotal() + "\n";
        }

        csvStr += "\n\n";

        return csvStr;
    }

    /**
     * Takes the information from the OlderAdultNeeds observablelist, and
     * formats it into a table using the information given
     *
     * @return A string representing the table of information given in a comma
     *         delimited format
     * 
     * @author Breanna Wilson
     */
    public String createOlderAdultNeedsCSVString()
    {
        String csvStr = "Age, Long Term Care Facility, Elmwood Res, "
                + "Luther Care, Other, Total As Of: " + date + ", Total For: "
                + prevYear + "\n";
        Iterator<OlderAdultsNeeds> iter = olderAdultNeedsList.iterator();
        OlderAdultsNeeds current = null;

        while ( iter.hasNext() )
        {
            current = iter.next();

            csvStr += current.getAge() + ",";
            csvStr += current.getLongTermCare() + ",";
            csvStr += current.getElmwood() + ",";
            csvStr += current.getLutherCare() + ",";
            csvStr += current.getOther() + ",";
            csvStr += current.getTotalAsOf() + ",";
            csvStr += current.getTotalForLastYear() + "\n";
        }

        csvStr += "\n\n";

        return csvStr;
    }

}
