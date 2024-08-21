
package DieticianPojo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dietician {

	private String ContactNumber;
	private String DateOfBirth;
	private String Education;
	private String Email;
	private String Firstname;
	private String HospitalCity;
	private String HospitalName;
	private String HospitalPincode;
	private String HospitalStreet;
	private String Lastname;	
}