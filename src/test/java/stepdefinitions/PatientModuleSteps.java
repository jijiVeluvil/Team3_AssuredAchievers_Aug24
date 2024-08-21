package stepdefinitions;

import java.io.File;
import java.util.Map;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DieticianPojo.PatientDataFieldsVo;
import DieticianPojo.UserLogin;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class PatientModuleSteps {

	APIFunction apiFunction;
	Map<String, String> singleDataRow;
	private static String patEmailForPatRole = "";
	ResponseOptions<Response> resp;

	// Create Patient
	@Given("Dietician creates patient with request body for {string} and {string}")
	public void dietician_creates_patient_with_request_body_for_and(String sheet, String row) {
		singleDataRow = DataHandler.getDataRow(sheet, row);
		String patientRequestBody = DataHandler.GetPatientRequestBody(sheet, row);

		// System.out.println("Patient Request Body---->"+patientRequestBody);

		PatientDataFieldsVo patientInfo = new PatientDataFieldsVo();
		patientInfo.setPatientInfo(patientRequestBody);
		patientInfo
				.setReportFile(new File("/C:/Users/renji/Downloads/Diabetic and Hemogram Test_Thyrocare lab.pdf.pdf"));

		apiFunction = new APIFunction(APIConstant.CREATE_Patient_ENDPOINT, APIConstant.POST,
				Tokens.TokenMap.get("dietBearerToken"), patientInfo, singleDataRow);

	}

	@When("Dietician sends HTTP request with endpoint")
	public void dietician_sends_http_request_with_endpoint() {
		ResponseOptions<Response> resp = apiFunction.ExecuteAPI();
		if (resp.getStatusCode() == 201 && singleDataRow.get("Scenario").equalsIgnoreCase("create_valid_patientId")) {
			System.out.println(singleDataRow.get("PatientId") + " " + resp.body().path("patientId"));
			patEmailForPatRole = apiFunction.response.body().path("Email");
			System.out.println("Patient email is " + patEmailForPatRole);
			Tokens.patIdMap.put(singleDataRow.get("PatientId"), Integer.toString(resp.body().path("patientId")));
			String respBody = apiFunction.response.body().asString();
			JsonObject obj = JsonParser.parseString(respBody).getAsJsonObject();
			String patFileId = obj.get("FileMorbidity").getAsJsonObject().keySet().iterator().next();
			System.out.println("Patient FileId is " + patFileId);
			Tokens.patFileIdMap.put(singleDataRow.get("File"), patFileId);

		}

	}

	@Then("Dietician recieves response code")
	public void dietician_recieves_response_code() {

		System.out.println("Expected code : " + singleDataRow.get("ExpectedCode") + ", Actual code : "
				+ apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("ExpectedCode")), apiFunction.response.getStatusCode());
	}

	// Create Patient Login
	@Given("Patient creates login Post request with request body")
	public void patient_creates_login_post_request_with_request_body() {
		System.out.println("PatEmail for login " + patEmailForPatRole);
		UserLogin login = new UserLogin(patEmailForPatRole, "test");
		String body = new Gson().toJson(login);
		System.out.println("Patient login is " + body);
		apiFunction = new APIFunction(null, APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, body);
	}

	@When("Patient send POST HTTP request with endpoint")
	public void patient_send_post_http_request_with_endpoint() {
		ResponseOptions<Response> resp = apiFunction.ExecuteAPI();

		if (resp.getStatusCode() == 200) {
			System.out.println("Saving token; Key : " + " Value :" + resp.body().path("token"));
			Tokens.TokenMap.put("patBearerToken", resp.body().path("token"));
		}

	}

	@Then("Patient recieves response code")
	public void patient_recieves_response_code() {
		Assert.assertEquals(200, apiFunction.response.getStatusCode());
	}

	// Update Patient
	@Given("Dietician updates patient details with request body for {string} and {string}")
	public void dietician_updates_patient_details_with_request_body_for_and(String sheet, String row) {
		singleDataRow = DataHandler.getDataRow(sheet, row);
		String patientRequestBody = DataHandler.GetPatientRequestBody(sheet, row);

		// System.out.println("Patient Request Body---->"+patientRequestBody);

		PatientDataFieldsVo patientInfo = new PatientDataFieldsVo();
		patientInfo.setPatientInfo(patientRequestBody);
		if (singleDataRow.get("File") != null || !singleDataRow.get("File").isEmpty()) {
			patientInfo.setReportFile(
					new File("/C:/Users/renji/Downloads/Diabetic and Hemogram Test_Thyrocare lab.pdf.pdf"));
		}
		String patIDForCRUD = Tokens.patIdMap.get("patIdForCRUD");
		String endPoint = APIConstant.CREATE_Patient_ENDPOINT + "/" + patIDForCRUD;
		System.out.println("Endpoint of update patient is " + endPoint);
		apiFunction = new APIFunction(endPoint, APIConstant.PUT, Tokens.TokenMap.get("dietBearerToken"), patientInfo,
				singleDataRow);
	}

	// Update Patient report
	@Given("Dietician updates patient report and vitals with request body for {string} and {string}")
	public void dietician_updates_patient_report_and_vitals_with_request_body_for_and(String sheet, String row) {

		String patIDForCRUD = Tokens.patIdMap.get("patIdForCRUD");
		String patId_NoReport = Tokens.patIdMap.get("patIdWithOutReport");
		String endpoint;
		singleDataRow = DataHandler.getDataRow(sheet, row);
		String patientRequestBody = DataHandler.GetPatientRequestBody(sheet, row);
		PatientDataFieldsVo patientInfo = new PatientDataFieldsVo();
		patientInfo.setPatientInfo(patientRequestBody);
		if (singleDataRow.get("File") != null || !singleDataRow.get("File").isEmpty()) {
			patientInfo.setReportFile(
					new File("/C:/Users/renji/Downloads/Diabetic and Hemogram Test_Thyrocare lab.pdf.pdf"));
		}
		String vitalsInfo = "{" + "            \"Weight\": 10.0," + "            \"Height\": 20.0,"
				+ "            \"Temperature\": 30.0," + "            \"SP\": 40," + "            \"DP\": 50"
				+ "        }";
		if (singleDataRow.get("Vitals") != null || !singleDataRow.get("Vitals").isEmpty()) {
			patientInfo.setVitals(vitalsInfo);
		}
		if (singleDataRow.get("Scenario").equalsIgnoreCase("update_vitals_only_for_pat_no_report")) {
			endpoint = APIConstant.UPDATE_Patient_Report_ENDPOINT + "/" + patId_NoReport;
		} else {
			endpoint = APIConstant.UPDATE_Patient_Report_ENDPOINT + "/" + patIDForCRUD;
		}
		System.out.println("Endpoint of patient report is " + endpoint);
		apiFunction = new APIFunction(endpoint, APIConstant.PUT, Tokens.TokenMap.get("dietBearerToken"), patientInfo,
				singleDataRow);
	}

	// GET Request to get all patient
	@Given("Dietician retrieves all patient details with {string},{string},{string}")
	public void dietician_retrieves_all_patient_details_with(String token, String method, String endpoint) {
		if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), APIConstant.CREATE_Patient_ENDPOINT,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.CREATE_Patient_ENDPOINT,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, APIConstant.CREATE_Patient_ENDPOINT, APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), APIConstant.CREATE_Patient_ENDPOINT,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("PUT")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), APIConstant.CREATE_Patient_ENDPOINT,
					APIConstant.PUT);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), "/patiet", APIConstant.GET);
		}
	}

	@When("Dietician sends HTTP request")
	public void dietician_sends_http_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("Dietician recieves response status code {string} with {string} message")
	public void dietician_recieves_response_status_code_with_message(String statusCode, String respMsg) {
		System.out.println("response code is " + apiFunction.response.getStatusCode());
		System.out.println("Response status line of " + apiFunction.response.getStatusCode() + " is "
				+ apiFunction.response.getStatusLine());
		Assert.assertEquals(Integer.parseInt(statusCode), apiFunction.response.getStatusCode());
		// Assert.assertTrue(apiFunction.response.getStatusLine().contains(msg));
	}
    
	//Get request to get morbidity details
	@Given("Dietician retrieves morbidity details with {string},{string},{string},{string}")
	public void dietician_retrieves_morbidity_details_with(String token, String method, String id, String endpoint) {
		String patIDForCRUD = Tokens.patIdMap.get("patIdForCRUD");
		String endpointOfUrl1 = APIConstant.GET_Patient_TestReport_ENDPOINT + "/" + patIDForCRUD;
		String patIDForPatRole = Tokens.patIdMap.get("patIdForPatRole");
		String endpointOfUrl2 = APIConstant.GET_Patient_TestReport_ENDPOINT + "/" + patIDForPatRole;

		if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), endpointOfUrl2, APIConstant.GET);
		} else if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("POST")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.POST);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& id.equalsIgnoreCase("invalidId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), endpointOfUrl2 + "3", APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), "/patient/testReport/" + patIDForCRUD,
					APIConstant.GET);
		}
	}

	// Get request to get patient file
	@Given("Dietician gets patient file with {string},{string},{string},{string}")
	public void dietician_gets_patient_file_with(String token, String method, String id, String endpoint) {
		String FileIDForDietRole = Tokens.patFileIdMap.get("FileIdForDietRole");
		String FileIdForPatRole = Tokens.patFileIdMap.get("FileIdForPatRole");
		String endpointOfUrl1 = APIConstant.ViewFile_ENDPOINT + "/" + FileIDForDietRole;
		String endpointOfUrl2 = APIConstant.ViewFile_ENDPOINT + "/" + FileIdForPatRole;

		if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), endpointOfUrl2, APIConstant.GET);
		} else if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("POST")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.POST);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& id.equalsIgnoreCase("invalidId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1 + "55",
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"),
					"/patient/testReport/viewFile/" + FileIDForDietRole, APIConstant.GET);
		}
	}

	// Delete Request

	@Given("Dietician deletes patient Id with {string},{string},{string},{string}")
	public void dietician_deletes_patient_id_with(String token, String method, String id, String endpoint) {
		String patIDForCRUD = Tokens.patIdMap.get("patIdForCRUD");
		String patIDForPatRole = Tokens.patIdMap.get("patIdForPatRole");
		String endpointOfUrl1 = APIConstant.CREATE_Patient_ENDPOINT + "/" + patIDForCRUD;
		String endpointOfUrl2 = APIConstant.CREATE_Patient_ENDPOINT + "/" + patIDForPatRole;

		if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("DELETE") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), endpointOfUrl1, APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, endpointOfUrl1, APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("POST")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.POST);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("invalidId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl1 + "3",
					APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), "/patiet/" + patIDForCRUD,
					APIConstant.DELETE);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId1") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), endpointOfUrl2, APIConstant.DELETE);
		}
	}

}
