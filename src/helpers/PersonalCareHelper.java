package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javafx.scene.control.CheckBox;

public class PersonalCareHelper
{

    private DatabaseHelper db;

    public PersonalCareHelper()
    {
        db = new DatabaseHelper();
    }

    public String[] retrievePersonalCareInformation( String cosmoID )
    {
        String[] personalCareInfo = new String[13];

        String insulin = null;
        String depo = null;
        String bTwelve = null;
        String suprapubic = null;
        String dressing = null;
        String coban = null;
        String ostomy = null;
        String catheter = null;
        String trach = null;
        String feed = null;
        String diet = null;
        String assistance = null;
        Date lastUpdated = new Date();
        lastUpdated = null;

        ResultSet rs = db
                .select("dailyInsulin, depoInjection, b12Injection, "
                        + "suprapubicCatheter, dressings, cobanWraps, ostomy, catheter, "
                        + "tracheotomy, tubeFeeds, dietProvided, assistanceRequired, "
                        + "personalCareUpdated", "Participant", "cosmoID ="
                        + cosmoID, "");

        try
        {
            while ( rs.next() )
            {
                insulin = rs.getString(1);
                depo = rs.getString(2);
                bTwelve = rs.getString(3);
                suprapubic = rs.getString(4);
                dressing = rs.getString(5);
                coban = rs.getString(6);
                ostomy = rs.getString(7);
                catheter = rs.getString(8);
                trach = rs.getString(9);
                feed = rs.getString(10);
                diet = rs.getString(11);
                assistance = rs.getString(12);
                lastUpdated = rs.getDate(13);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out
                    .println("Failed to query paritipant's personal care info");
        }

        personalCareInfo[0] = insulin;
        personalCareInfo[1] = depo;
        personalCareInfo[2] = bTwelve;
        personalCareInfo[3] = suprapubic;
        personalCareInfo[4] = dressing;
        personalCareInfo[5] = coban;
        personalCareInfo[6] = ostomy;
        personalCareInfo[7] = catheter;
        personalCareInfo[8] = trach;
        personalCareInfo[9] = feed;
        personalCareInfo[10] = diet;
        personalCareInfo[11] = assistance;

        // making sure participant does not have an date if no seizure
        try
        {
            personalCareInfo[12] = lastUpdated.toString();
        }
        catch ( NullPointerException e )
        {
            personalCareInfo[12] = " ";
        }

        for ( int i = 0; i < personalCareInfo.length; i++ )
        {
            // any null fields filled in with empty strings
            if ( personalCareInfo[i] == null )
            {
                personalCareInfo[i] = " ";
            }
        }
        return personalCareInfo;
    }

    public boolean savePersonalCareInformation( Boolean[] valArray,
            String value, String when, String cosmoId )
    {
        String result = "";
        db.connect();

        String personalCareInformation[][] = new String[14][2];

        personalCareInformation[0][0] = "cosmoID";
        personalCareInformation[1][0] = "dailyInsulin";
        personalCareInformation[2][0] = "depoInjection";
        personalCareInformation[3][0] = "b12Injection";
        personalCareInformation[4][0] = "suprapubicCatheter";
        personalCareInformation[5][0] = "dressings";
        personalCareInformation[6][0] = "cobanWraps";
        personalCareInformation[7][0] = "ostomy";
        personalCareInformation[8][0] = "catheter";
        personalCareInformation[9][0] = "tracheotomy";
        personalCareInformation[10][0] = "tubeFeeds";
        personalCareInformation[11][0] = "dietProvided";
        personalCareInformation[12][0] = "assistanceRequired";
        personalCareInformation[13][0] = "personalCareUpdated";

        personalCareInformation[0][1] = cosmoId;
        personalCareInformation[1][1] = valArray[0] + "";
        personalCareInformation[2][1] = valArray[1] + "";
        personalCareInformation[3][1] = valArray[2] + "";
        personalCareInformation[4][1] = valArray[3] + "";
        personalCareInformation[5][1] = valArray[4] + "";
        personalCareInformation[6][1] = valArray[5] + "";
        personalCareInformation[7][1] = valArray[6] + "";
        personalCareInformation[8][1] = valArray[7] + "";
        personalCareInformation[9][1] = valArray[8] + "";
        personalCareInformation[10][1] = valArray[9] + "";
        personalCareInformation[11][1] = valArray[10] + "";
        personalCareInformation[12][1] = value;
        personalCareInformation[13][1] = when;

        boolean success = db.update(personalCareInformation, "Participant",
                cosmoId);

        db.disconnect();

        return success;
    }
}
