package core;

/**
 * 
 * Purpose: represent an injury type for a participant in the database
 * 
 * @author Team CIMP
 * @version 1.0
 */
public class InjuryType
{
    private String injuryType;

    /**
     * 
     * Constructor for the InjuryType class.
     * 
     * @param injuryType the injury name
     */
    public InjuryType(String injuryType)
    {
        this.setInjuryType(injuryType);
    }

    /**
     * 
     * Purpose: edit the injury name
     * 
     * @param injuryType
     */
    private void setInjuryType( String injuryType )
    {
        this.injuryType = injuryType;

    }

    /**
     * 
     * Purpose: get the injury name
     * 
     * @return the injury name
     */
    public String getInjuryType()
    {
        return injuryType;
    }

}
