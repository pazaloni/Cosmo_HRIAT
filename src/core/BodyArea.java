package core;

/**
 * 
 * Purpose: represent a body area for an injured participant  
 *
 * @author Team CIMP
 * @version 1.0
 */
public class BodyArea
{
    
    private String bodyArea;


    /**
     * 
     * Constructor for the BodyArea class.
     * @param bodyArea the body area name. 
     */
    public BodyArea(String bodyArea)
    {
        this.bodyArea = bodyArea;
    }

    /**
     * 
     * Purpose: get the body area
     * @return the body area
     */
    public String getBodyArea()
    {
        return bodyArea;
    }

    /**
     * 
     * Purpose: set the body area 
     * @param bodyArea the body are to be set to.
     */
    public void setBodyArea( String bodyArea )
    {
        this.bodyArea = bodyArea;
    }
    
}
