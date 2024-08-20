package stepdefinition;



import java.io.File;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DietitianPojo.PatientDataFieldsVo;
import DietitianPojo.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class UserLoginModuleSteps 
{
	APIFunction apiFunction;
	Map<String, String> singleDataRow;

	@Given("User creates login Post request with request body for {string} and {string}")
	public void user_creates_login_post_request_with_request_body_for_and(String sheet, String row) {

		singleDataRow=DataHandler.getDataRow(sheet,row);

		UserLogin login=new UserLogin(singleDataRow.get("userLoginEmail"),singleDataRow.get("password"));

		String body= new Gson().toJson(login);

		apiFunction = new APIFunction(body,APIConstant.USER_LOGIN_ENDPOINT,APIConstant.POST,singleDataRow);

	}

	@When("User send POST HTTP request with endpoint")
	public void user_send_post_http_request_with_endpoint()  {

		ResponseOptions<Response> resp=apiFunction.ExecuteAPI();

		if(resp.getStatusCode()==200)
		{
			if(resp.body().path("token")!=null && singleDataRow.get("TokenName") !=null)
			{
				System.out.println("Saving token; Key :  "+singleDataRow.get("TokenName") +", Value : "+resp.body().path("token"));

				Tokens.TokenMap.put(singleDataRow.get("TokenName") ,resp.body().path("token"));
			}

		}

	}

	@Then("User recieves response code")
	public void user_recieves_response_code() {

		System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
		
	}

	
	@Given("User creates dietician {string} Post request with request body")
	public void user_creates_dietician_post_request_with_request_body(String testcase) {

		String dieticianRequestBody=DataHandler.GetDieticianRequestBody(testcase);

		System.out.println("dietician Request Body---->"+dieticianRequestBody);

		apiFunction=new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.POST, 
				dieticianRequestBody);

	}

	@Then("User saves the admin token")
	public void user_saves_the_admin_token() {

		Tokens.TokenMap.put("AdminToken",apiFunction.response.body().path("token"));
		System.out.println("Saving token; Key :  AdminToken, Value : "+apiFunction.response.body().path("token"));

	}

	@Then("User generates the dietician token")
	public void user_generates_the_dietician_token() {


		UserLogin user=new UserLogin( apiFunction.response.body().path("Email"),	 apiFunction.response.body().path("loginPassword"));
		APIFunction login= new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
				new Gson().toJson(user));
		ResponseOptions<Response> resp= login.ExecuteAPI();

		Tokens.TokenMap.put("DieticianToken",resp.body().path("token"));
		System.out.println("Saving token; Key :  DieticianToken , Value : "+apiFunction.response.body().path("token"));

	}


	@Then("User generates the patient token")
	public void user_generates_the_patient_token() {


		UserLogin user=new UserLogin( apiFunction.response.body().path("Email"),	"test");

		Tokens.PatientID= apiFunction.response.body().path("patientId");
		System.out.println("Tokens.PatientID= "+Tokens.PatientID);
		APIFunction login= new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
				new Gson().toJson(user));
		ResponseOptions<Response> resp= login.ExecuteAPI();


		Tokens.TokenMap.put("PatientToken",resp.body().path("token"));
		System.out.println("Saving token; Key :  PatientToken , Value : "+apiFunction.response.body().path("token"));
	}



	@Given("User creates patient {string} Post request with request body")
	public void user_creates_patient_post_request_with_request_body(String testcase) {

		String patientRequestBody=DataHandler.GetPatientRequestBody(testcase);

		System.out.println("Patient Request Body---->"+patientRequestBody);

		PatientDataFieldsVo patientInfo=new PatientDataFieldsVo();
		patientInfo.setPatientInfo(patientRequestBody);

		apiFunction=new APIFunction(APIConstant.CREATE_Patient_ENDPOINT, APIConstant.POST, 
				Tokens.TokenMap.get("DieticianToken"),patientInfo);

	}



	@Then("User checks patient testReports")
	public void user_checks_patient_test_reports() {

		APIFunction testReport= new APIFunction(Tokens.TokenMap.get("PatientToken"),APIConstant.CREATE_Patient_TestReport_ENDPOINT+"/"+Tokens.PatientID, APIConstant.GET, 
				"");
		testReport.ExecuteAPI();
		System.out.println("patient testReports response--> "+testReport.response.body().asPrettyString());

	}

	@Then("User updates the patient vitals")
	public void user_updates_patient_vitals() {

		String patientRequestBody=DataHandler.GetPatientRequestBody("patient1");

		PatientDataFieldsVo patientInfo=new PatientDataFieldsVo();
		patientInfo.setPatientInfo(patientRequestBody);

		String vitalsInfo="{"
				+ "            \"Weight\": 10.0,"
				+ "            \"Height\": 20.0,"
				+ "            \"Temperature\": 30.0,"
				+ "            \"SP\": 40,"
				+ "            \"DP\": 50"
				+ "        }";

		patientInfo.setVitals(vitalsInfo);
		patientInfo.setReportFile(new File("/Users/ashwini/Downloads/Diabetic and Hemogram Test_Thyrocare lab.pdf.pdf"));

		apiFunction=new APIFunction(APIConstant.UPDATE_Patient_ENDPOINT+"/"+Tokens.PatientID, APIConstant.PUT, 
				Tokens.TokenMap.get("DieticianToken"),patientInfo);

		apiFunction.ExecuteAPI();
		System.out.println(" updatePatient response--> "+apiFunction.response.body().asPrettyString());

	}



	@When("User extract the fileId")
	public void user_extract_the_file_id() {


		String respBody=apiFunction.response.body().asString();

		JsonObject obj = JsonParser.parseString(respBody).getAsJsonObject();

		Set<String> allFileids=obj.get("FileMorbidity").getAsJsonObject().keySet();

		for (String fileId : allFileids) {

			System.out.println("got file id "+fileId);
		}

		Tokens.allFileids=allFileids;
	}

	@Then("user checks the patient by fileId")
	public void user_checks_the_patient_by_file_id() {


		for (String fileId : Tokens.allFileids) {

			System.out.println("checking report for file id "+fileId);

			APIFunction	checkReport=new APIFunction(Tokens.TokenMap.get("DieticianToken"),APIConstant.viewFile_endpoint+"/"+fileId, APIConstant.GET);
			checkReport.ExecuteAPI();

			System.out.println("dietician checkReport/viewfile response status--> "+checkReport.response.getStatusCode());

		}
	}


	//--------- for testing complete flow --------

	@Given("User creates login  Post request with request body")
	public void user_creates_login_post_request_with_request_body() {

		apiFunction=new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
				DataHandler.getRequestBody("valid_creds"));

	}

	@Then("User recieves {int} created with response body")
	public void user_recieves_created_with_response_body(Integer respCode) {

		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
		//Assert.assertEquals(respCode, apiFunction.response.getStatusCode());
	}

}

