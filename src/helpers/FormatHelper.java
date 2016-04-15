package helpers;

/**
 * Class for formatting various fields.
 * 
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
     * 
     * @param unformattedPhoneNum The phone number to format
     * @return The formatted phone number
     */
    public String formatPhoneNum( String unformattedPhoneNum )
    {
        String result;
        // Remove all non-numeric characters from the phone number
        unformattedPhoneNum = unformattedPhoneNum.replaceAll("[^\\d]", "");
        // Check that the phone number is of the appropriate length
        if ( unformattedPhoneNum.length() == 10 )
        {
            result = "(" + unformattedPhoneNum.substring(0, 3) + ") "
                    + unformattedPhoneNum.substring(3, 6) + "-"
                    + unformattedPhoneNum.substring(6, 10);
        }
        else
        {
            result = "Phone Number must be 10 digits";
        }
        return result;
    }

    /**
     * Purpose: Formats the postal code into the format A1A 1A1.
     * 
     * @param postalCode - the inputted postal code to be formatted
     * @return - Either the formatted postal code, or an error message if
     *         unsuccessful in finding the neccessary characters.
     */
    public String formatPostalCode( String postalCode )
    {
        String result = "";

        // remove any characters that are not letters or numbers
        postalCode = postalCode.replace("[^A-Za-z0-9]", "");

        // check that it is a long enough string
        if ( postalCode == null || postalCode.equals("")
                || postalCode.length() < 6 )
        {
            result = "Postal code must contain a total of at least 6 letters and numbers.";
        }
        else
        {
            // keeps track of whether or not the last value added was a
            // number or letter
            boolean lastValueWasChar = false;
            // loop until characters have run out to search, or the postal
            // code has reached desired length
            for ( int i = 0; i < postalCode.length() && result.length() < 7; i++ )
            {
                // if the last value was a char, add the next digit
                if ( lastValueWasChar )
                {
                    // if the length of the postal code has reached 3
                    if ( result.length() == 3 )
                    {
                        // add the space
                        result += " ";
                        // need this to be false in this case,
                        // so that the first character after the
                        // space is a digit
                        lastValueWasChar = true;
                    }
                    // if the next value is a digit
                    else if ( postalCode.toUpperCase().charAt(i) >= '0'
                            && postalCode.toUpperCase().charAt(i) <= '9' )
                    {
                        // add it to the postal code
                        result += postalCode.charAt(i);
                        // switch this to false, to that
                        // a letter will be searched for
                        // next
                        lastValueWasChar = false;
                    }

                    // else, continue loop looking for a
                    // digit value
                }
                // else, continue to add a letter
                else
                {

                    // else if, check that the next value is a letter
                    if ( postalCode.toUpperCase().charAt(i) >= 'A'
                            && postalCode.toUpperCase().charAt(i) <= 'Z' )
                    {
                        // add the letter as a capital to the postal code
                        result += postalCode.toUpperCase().charAt(i);
                        // set this to true to look for a digit next
                        lastValueWasChar = true;
                    }
                    // else, continue loop looking for a letter value
                }

            }

        }

        // if the length is not 7, could not find enough
        // letters and digits to create the postal code
        if ( result.length() != 7 )
        {
            // give an error message
            result = "Postal code must contain a total of at least 6 letters and numbers.";
        }

        return result;
    }

}
