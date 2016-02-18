import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Purpose: Test the activity log
 *
 * @author CIMP
 * @version 1.0
 */
public class TestActivityLog
{
    ActivityLog test1;
    ActivityLog test2;
    ActivityLog test3;

    private String who1;
    private String who2;
    private String who3;

    private String event1;
    private String event2;
    private String event3;

    private String timeStamp1;
    private String timeStamp2;
    private String timeStamp3;
    
    private String details;

    @Before
    public void setUp() throws Exception
    {
        who1 = "Bob Smith";
        who2 = "Jane Doe";
        who3 = "Jimmy Bob";

        event1 = "Logged in";
        event2 = "Logged out";
        event3 = "Edited Participant";

        timeStamp1 = "2/1/2016 2:23:25";
        timeStamp2 = "2/3/2016 1:20:19";
        timeStamp3 = "2/5/2016 3:20:11";
        
        details = "First Name: \"Jimmy\" -> \"Bobby\"";

        test1 = new ActivityLog(who1, timeStamp1, event1, "");
        test2 = new ActivityLog(who2, timeStamp2, event2, "");
        test3 = new ActivityLog(who3, timeStamp3, event3, details);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * 
     * Purpose:checks if the person that triggered the event was set 
     */
    @Test
    public void testGetWho()
    {
        assertTrue(test1.getWho().get().equals(who1));
        assertTrue(test2.getWho().get().equals(who2));
    }

    /**
     * 
     * Purpose: checks if the timestamps of the triggered event was set 
     */
    @Test
    public void testGetTimestamp()
    {
        assertTrue(test1.getWhen().get().equals(timeStamp1));
        assertTrue(test2.getWhen().get().equals(timeStamp2));

    }

    /**
     * 
     * Purpose: checks if the event of the triggered event was set 
     */
    @Test
    public void testGetEvent()
    {
        assertTrue(test1.getEvent().get().equals(event1));
        assertTrue(test2.getEvent().get().equals(event2));
    }
    
    /**
     * 
     * Purpose: checks if the details of the triggered event were set
     */
    @Test
    public void testGetDetails()
    {
        assertTrue(test1.getDetails().get().equals(""));
        assertTrue(test2.getDetails().get().equals(""));
        assertTrue(test3.getDetails().get().equals(details));
    }
}
