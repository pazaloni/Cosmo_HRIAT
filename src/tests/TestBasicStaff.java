package tests;
import static org.junit.Assert.*;

import java.time.LocalDate;

import javafx.collections.ObservableList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.BasicStaff;
import core.MedicalAdministrator;


public class TestBasicStaff
{
    private  BasicStaff basicstaff;
    
    private String cosmoID;
    private String participantFirstName;
    private String participantLastName;
    private LocalDate birthDate;
    private String physicianFName;
    private String physicianLName;
    private String participantHealthNumber;
    private String participantPhone;
    private String participantAddress;
    private String participantAllergy;
    private String participantCareType;
    
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String firstPassword;
    private String accessLevel;
    
    
    
    private BasicStaff test;

    @Before
    public void setUp() throws Exception
    {
        username = "lebowskyD";
        firstName = "dude";
        lastName = "lebowsky";
        email = "dude@awesome.com";
        firstPassword = "password";

        accessLevel = "1";
        
        basicstaff = new BasicStaff(username, firstName, lastName, 
                email, firstPassword, accessLevel);

        cosmoID = "1111";
        participantFirstName = "John";
        participantLastName = "Smith";
        birthDate = LocalDate.of(1990, 1, 12);
        physicianFName = "John";
        physicianLName = "Doctor";
        participantHealthNumber = "123123123";
        participantPhone = "1231231231";
        participantAddress = "123 Fake Street";
        participantAllergy = "Peanuts";
        participantCareType = "Luther";

    }
    /**
     * st041
     * 
     * Purpose:Tests to see if the search function is working
     * 
     * @throws Exception
     */

    /**
     * Purpose: Test searching the participant database by name
     * 
     * @author - Jonathan Froese CST203 - Niklaas Neijmeijer CST207
     */
    @Test
    public void testSearchName()
    {
        MedicalAdministrator.createParticipant(cosmoID, participantFirstName, participantLastName, 
                 birthDate, physicianFName, physicianLName, participantHealthNumber, 
                 participantPhone, participantAddress, participantCareType, "");
         
    //    ObservableList<Participant> participantData = basicstaff.search
         //       (BasicStaff.SearchType.NAME, participantFirstName);
        
    //    assertTrue(participantData.get(0).getParticipantName().contains(participantFirstName));
  
    }
    /**
     * Purpose: Test searching the participant database by address
     * 
     * @author - Jonathan Froese CST203 - Niklaas Neijmeijer CST207
     */
    
    @Test
    public void testSearchAddress()
    {
         MedicalAdministrator.createParticipant(cosmoID, participantFirstName, participantLastName, 
                 birthDate, physicianFName, physicianLName, participantHealthNumber, 
                 participantPhone, participantAddress, participantCareType, "");
         
       // ObservableList<Participant> participantData = basicstaff.search
        //        (BasicStaff.SearchType.ADDRESS, participantAddress);
      
      //  assertTrue(participantData.get(0).getParticipantAddress().contains(participantAddress));
  
    }
    /**
     * Purpose: Test searching the participant database by Allergy
     * 
     * @author - Jonathan Froese CST203 - Niklaas Neijmeijer CST207
     */
    
    @Test
    public void testSearchAllergy()
    {
         MedicalAdministrator.createParticipant(cosmoID, participantFirstName, participantLastName, 
                 birthDate, physicianFName, physicianLName, participantHealthNumber, 
                 participantPhone, participantAddress ,participantCareType, "");
         
      //  ObservableList<Participant> participantData = 
        //        basicstaff.search(BasicStaff.SearchType.ALLERGY, participantAllergy);
        
      //  assertTrue(participantData.get(0).getParticipantName().contains(participantAllergy));
  
    }
    
    /**
     * Purpose: Test searching the participant database by cosmoID
     * 
     * @author - Jonathan Froese CST203 - Niklaas Neijmeijer CST207
     */
    
    
    @Test
    public void testSearchCosmoID()
    {
         MedicalAdministrator.createParticipant(cosmoID, participantFirstName, participantLastName, 
                 birthDate, physicianFName, physicianLName, participantHealthNumber, 
                 participantPhone, participantAddress, participantCareType, "");
         
      //  ObservableList<Participant> participantData = basicstaff.search(BasicStaff.SearchType.NAME, cosmoID);
      //  
  
    }
    
    
    @After
    public void tearDown() throws Exception
    {
    }




}
