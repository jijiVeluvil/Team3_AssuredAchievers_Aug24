package requestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.restassured.response.Response;
import payload.PatientPayload;
import utilities.ExcelReader;

public class PatientModuleBody {
	public PatientPayload patPayload;
	Response response;
	List<Integer> expStatusCodeList = new ArrayList<>();
	public  List<PatientPayload> requestBody(String filePath, String querry) {
		List<PatientPayload> patPayLoadList = new ArrayList<>();
		ExcelReader xlreader = new ExcelReader();
		List<Map<String, String>> data = xlreader.getExcelDataWithFilloAPI(filePath,querry);
		for (Map<String, String> rowData : data) {
			patPayload = new PatientPayload();
			patPayload.setFirstName (rowData.get("FirstName"));
			patPayload.setLastName (rowData.get("lastName"));
			patPayload.setContactNumber (rowData.get("ContactNumber"));
			patPayload.setEmail (rowData.get("Email"));
			patPayload.setAllergy (rowData.get("Allergy"));
			patPayload.setFoodPreference (rowData.get("FoodPreference"));
			patPayload.setCuisineCategory (rowData.get("CuisineCategory"));
			patPayload.setDateOfBirth (rowData.get("DateOfBirth"));
			patPayLoadList.add (patPayload);
			
			//patPayload.setExpStatusCode (rowData.get("ExpStatusCode"));
			//int expCode = Integer.parseInt(patPayload.getExpStatusCode());
			int expCode = Integer.parseInt(rowData.get("ExpStatusCode"));
			expStatusCodeList.add(expCode);
			}
		return patPayLoadList;
		
	}
	
	public List<Integer> getExpStatusCode() {
		return expStatusCodeList;
	}

}
