package DietitianPojo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Patient {

	private String FirstName;
	private String LastName;
	private String ContactNumber;
	private String Email;
	private String Allergy;
	private String FoodPreference;
	private String CuisineCategory;
	private String DateOfBirth;
	
	
}

