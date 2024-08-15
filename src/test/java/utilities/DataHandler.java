package utilities;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import DietitianPojo.UserLogin;

public class DataHandler {

	static final String path="./src/test/resources/UserLogin.xlsx";

	public static Map<String, String> getData(String scenario)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from LoginSheet where Scenario='"+scenario+"' ");

		//TODO- for now assuming one scenario - one data row

		return listOfRows.get(0);

	}
	
	public static String getRequestBody(String scenario)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from LoginSheet where Scenario='"+scenario+"' ");

		UserLogin login=new UserLogin(listOfRows.get(0).get("userLoginEmail"),listOfRows.get(0).get("password"));
		
		String requestBodyToReturn= new Gson().toJson(login);
		
		System.out.println("requestBody--> "+requestBodyToReturn);
		return requestBodyToReturn;
	}
	
	

}
