package helpers;
/**
 * Class for formatting various fields.
 * @author cst207
 *
 */
public class FormatHelper
{
    public FormatHelper()
    {
        
    }
    /**
     * A method for formatting a phone number to the client's standard
     * @param unformattedPhoneNum The phone number to format
     * @return The formatted phone number
     */
    public String formatPhoneNum(String unformattedPhoneNum)
    {
        String result;
        //Remove all non-numeric characters from the phone number
        unformattedPhoneNum = unformattedPhoneNum.replaceAll("[^\\d]", "");
        //Check that the phone number is of the appropriate length
        if(unformattedPhoneNum.length() == 10)
        {
        result = "(" + unformattedPhoneNum.substring(0, 3) + ") "
                + unformattedPhoneNum.substring(3, 6) + "-"
                + unformattedPhoneNum.substring(6, 10);
        }
        else
        {
            result = "A phone number must have 10 digits.";
        }
        return result;
    }
    
    
}
