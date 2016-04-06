package core;

public class Witness
{
    private String witnessName;

    /*
     * P - Participant, S - Staff, O - Other
     */
    private char witnessType;

    public Witness(String witnessName, char witnessType)
    {
        this.witnessName = witnessName;
        this.witnessType = witnessType;
    }

    /**
     * 
     * Purpose: get the witness' name
     * 
     * @return the witness' name
     */
    public String getWitnessName()
    {
        return witnessName;
    }

    /**
     * 
     * Purpose: edit the witness name
     * 
     * @param witnessName the new witness name
     */
    public void setWitnessName( String witnessName )
    {
        this.witnessName = witnessName;
    }

    /**
     * 
     * Purpose: get the witness' type
     * 
     * @return witness' type
     */
    public char getWitnessType()
    {
        return witnessType;
    }

}