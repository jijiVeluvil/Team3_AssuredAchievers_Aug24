package utilities;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;

import DietitianPojo.UserLogin;

public class DataHandler {

	//static final String path="/Users/ashwini/Desktop/UserLogin.xlsx";
	static final String path="./src/test/resources/UserDataFile.xlsx";
	
	public static String getRequestBody(String scenario)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from LoginSheet where Scenario='"+scenario+"' ");

		UserLogin login=new UserLogin(listOfRows.get(0).get("userLoginEmail"),listOfRows.get(0).get("password"));
		
		String requestBodyToReturn= new Gson().toJson(login);
		
		System.out.println("requestBody--> "+requestBodyToReturn);
		return requestBodyToReturn;
	}

	public static Map<String, String> getDataRow(String sheetName, String row)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from "+sheetName);
		return listOfRows.get(Integer.parseInt(row)-1);
	}


	public static String GetDieticianRequestBody(String scenario)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from dietician where scenario='"+scenario+"' ");

		Map<String,String> dataMap=listOfRows.get(0);
		dataMap.put("ContactNumber",RandomStringUtils.randomNumeric(10));
		dataMap.put("Email",("amDietician"+RandomStringUtils.randomNumeric(5)+"@gmail.com"));
		
		return new Gson().toJson(dataMap);
	}
	
	public static String GetPatientRequestBody(String scenario)
	{
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from patient where scenario='"+scenario+"' ");

		Map<String,String> dataMap=listOfRows.get(0);
		dataMap.put("ContactNumber",RandomStringUtils.randomNumeric(10));
		dataMap.put("Email",("amPatient"+RandomStringUtils.randomNumeric(5)+"@gmail.com"));
		
		dataMap.remove("scenario");
		dataMap.remove("ContentType");
		dataMap.remove("EndPoint");
		dataMap.remove("ExpectedCode");
		dataMap.remove("Method");
		dataMap.remove("PatientId");
		dataMap.remove("TokenName");
		
		dataMap.remove("File");
		dataMap.remove("Vitals");
		

		return new Gson().toJson(dataMap);
	}
	
	public static String Logout(String scenario) {
		ExcelReader excelReader = new ExcelReader();
		List<Map<String, String>> listOfRows= excelReader.getExcelDataWithFilloAPI(path, "Select * from LogOutSheet where scenario='"+scenario+"' ");
		Map<String,String> dataMap=listOfRows.get(0);
		return new Gson().toJson(dataMap);
		
	}
}
