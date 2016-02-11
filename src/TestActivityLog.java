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

    private String who1;
    private String who2;

    private String event1;
    private String event2;

    private String timeStamp1;
    private String timeStamp2;

    @Before
    public void setUp() throws Exception
    {
        who1 = "Bob Smith";
        who2 = "Jane Doe";

        event1 = "Logged in";
        event2 = "Logged out";

        timeStamp1 = "2/1/2016 2:23:25";
        timeStamp2 = "2/3/2016 1:20:19";

        test1 = new ActivityLog(who1, timeStamp1, event1);
        test2 = new ActivityLog(who2, timeStamp2, event2);

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
     * Purpose: checks if the event of the trieggered event was set 
     */
    @Test
    public void testGetEvent()
    {
        assertTrue(test1.getEvent().get().equals(event1));
        assertTrue(test2.getEvent().get().equals(event2));
    }
}
