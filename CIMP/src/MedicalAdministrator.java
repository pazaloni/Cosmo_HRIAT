/**
 * Purpose: Represent the medical staff within the system
 * 
 * 
 * @author TEAM CIMP
 *
 */
public class MedicalAdministrator extends BasicStaff
{
    public MedicalAdministrator(String username, String lastName,
            String firstName, String email, String password, String accessLevel)
    {
        super(username, lastName, firstName, email, password, accessLevel);
    }
}
