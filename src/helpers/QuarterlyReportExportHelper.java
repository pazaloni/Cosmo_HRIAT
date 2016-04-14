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
 * Purpose: Used to export the quarterly report
 * infomration into a formatted csv file.
 * @author Breanna Wilson
 *
 */

public class QuarterlyReportExportHelper 
{
	//the lists of data to be put into the report
	ObservableList<PersonalCare> personalCareList;
	ObservableList<ReactiveCare> reactiveCareList;
	ObservableList<OlderAdultsNeeds> olderAdultNeedsList;
	String date;
	int prevYear;
	
	public QuarterlyReportExportHelper(ObservableList personalCareList, ObservableList reactiveCareList,
			ObservableList olderAdultNeedsList, String formattedDate, int prevYear)
	{
		this.personalCareList = personalCareList;
		this.reactiveCareList = reactiveCareList;
		this.olderAdultNeedsList = olderAdultNeedsList;
		this.date = formattedDate;
		this.prevYear = prevYear;
	}
	
	public void exportToCSV()
	{
		String exportStr = "Older Adults Needs\n" + createOlderAdultNeedsCSVString() + 
				"Personal Care\n" + createPersonalCareCSVString() + "Reactive Care\n" + 
				createReactiveCareCSVString() + "\nHealth Promotion Activities\n\nHealth Related Supplies";
		
		try {
			PrintWriter out = new PrintWriter("quarterlyReport" + this.date + ".csv");
			out.print(exportStr);
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	private String createPersonalCareCSVString()
	{
		String csvStr = ", Supports, Total Persons as of: " + date + ", Total For: " + prevYear + "\n";
		Iterator<PersonalCare> iter = personalCareList.iterator();
		PersonalCare current = null;
		while(iter.hasNext())
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
	
	private String createReactiveCareCSVString()
	{
		String csvStr = "Year, Participants Involved, Staff Members Involved, Total\n";
		Iterator<ReactiveCare> iter = reactiveCareList.iterator();
		ReactiveCare current = null;
		
		while(iter.hasNext())
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
	
	private String createOlderAdultNeedsCSVString()
	{
		String csvStr = "Age, Long Term Care Facility, Elmwood Res, "
				+ "Luther Care, Other, Total As Of: " + date + ", Total For: " + 
				prevYear + "\n";
		Iterator<OlderAdultsNeeds> iter = olderAdultNeedsList.iterator();
		OlderAdultsNeeds current = null;
		
		while(iter.hasNext())
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
