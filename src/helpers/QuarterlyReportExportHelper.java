package helpers;

import java.util.Iterator;

import core.OlderAdultsNeeds;
import core.PersonalCare;
import core.ReactiveCare;
import javafx.collections.ObservableList;

public class QuarterlyReportExportHelper 
{
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
		String exportStr = createOlderAdultNeedsCSVString() + 
				createPersonalCareCSVString() + createReactiveCareCSVString();
		
		
		
	}
	
	private String createPersonalCareCSVString()
	{
		String csvStr = ", Supports, Total Persons as of: " + date + ", Total For: " + prevYear + "\n";
		Iterator<PersonalCare> iter = personalCareList.iterator();
		PersonalCare current =iter.next();
		while(current != null)
		{
			csvStr += current.getType() + ",";
			csvStr += current.getSupports() + ",";
			csvStr += current.getTotalAsOf() + ",";
			csvStr += current.getTotalForLastYear() + "\n";
			
			current = iter.next();
		}
		
		csvStr += "\n\n";
		
		return csvStr;
	}
	
	private String createReactiveCareCSVString()
	{
		String csvStr = "Year, Participants Involved, Staff Members Involved, Total\n";
		Iterator<ReactiveCare> iter = reactiveCareList.iterator();
		ReactiveCare current = iter.next();
		
		while(current != null)
		{
			csvStr += current.getYear() + ",";
			csvStr += current.getParticipants() + ",";
			csvStr += current.getStaff() + ",";
			csvStr += current.getTotal() + "\n";
			
			current = iter.next();
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
		OlderAdultsNeeds current = iter.next();
		
		while(current != null)
		{
			csvStr += current.getAge() + ",";
			csvStr += current.getLongTermCare() + ",";
			csvStr += current.getElmwood() + ",";
			csvStr += current.getLutherCare() + ",";
			csvStr += current.getOther() + ",";
			csvStr += current.getTotalAsOf() + ",";
			csvStr += current.getTotalForLastYear() + "\n";
			
			current = iter.next();
		}
		
		csvStr += "\n\n";
		
		return null;
	}
	
}
