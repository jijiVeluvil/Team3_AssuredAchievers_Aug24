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

public class PatientModuleSteps {
	APIFunction apiFunction;
	Map<String, String> singleDataRow;
	String patientId;
	String patientEmail;
	String patientFileId;
	String patientPassword = "test";
	String patientToken;

	@Given("Dietician creates patient Post request with {string},{string},{string} and request body")
	public void dietician_creates_patient_post_request_with_and_request_body(String sheetName, String scenario,
			String row) {
		singleDataRow = DataHandler.getDataRow(sheetName, row);
		String patientRequestBody = DataHandler.GetPatientRequestBody(scenario);

		System.out.println("Patient Request Body---->" + patientRequestBody);

		PatientDataFieldsVo patientInfo = new PatientDataFieldsVo();
		String filePath = ".\\src\\test\\resources\\filePath.pdf";
		File patientFile = new File(filePath);
		patientInfo.setReportFile(patientFile);
		patientInfo.setPatientInfo(patientRequestBody);
		// System.out.println("patient file === "+ patientInfo.getReportFile());
		// System.out.println("token name:"+singleDataRow.get("TokenName"));
		// System.out.println("Tokenmap == "+Tokens.TokenMap);
		apiFunction = new APIFunction(singleDataRow.get("EndPoint"), singleDataRow.get("Method"),
				Tokens.TokenMap.get("DieticianToken1"), patientInfo);

	}

	@When("Dietician sends POST request with endpoint")
	public void dietician_sends_post_request_with_endpoint() {
		ResponseOptions<Response> resp = apiFunction.ExecuteAPI();
		System.out.println("patient response--> " + resp.body().asPrettyString());
		if (resp.getStatusCode() == 201) {
			// setting the patient id,email and fileId to Patient map
			patientId = singleDataRow.get("PatientId");
			patientEmail = singleDataRow.get("Email");
			patientFileId = patientId + "_FileId";
			Tokens.PatientMap.put(patientId, Integer.toString(resp.body().path("patientId")));
			Tokens.PatientMap.put(patientEmail, resp.body().path("Email"));
			Tokens.PatientMap.put(patientFileId, resp.body().path("fileId"));
		}
	}

	@Then("Dietician recieves response code")
	public void dietician_recieves_response_code() {
		System.out.println("Expected code : " + singleDataRow.get("expectedCode") + ", Actual code : "
				+ apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
	}

	@Then("Dietician generates Patient Token")
	public void admin_generates_dietician_token_and_saves_Dietician_Token() {

		if (apiFunction.response.getStatusCode() == 201) {
			String patientUserName = Tokens.PatientMap.get(patientEmail);

			System.out.println("patientUserName == " + patientUserName);
			System.out.println("paaaaatient file id ====" + Tokens.PatientMap.get(patientFileId));
			System.out.println("paaaaatient id ====" + Tokens.PatientMap.get(patientId));
			// Generating token for Patient login
			UserLogin patientLoginCredentials = new UserLogin(patientUserName, patientPassword);
			APIFunction patientLogin = new APIFunction(null, APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST,
					new Gson().toJson(patientLoginCredentials));
			ResponseOptions<Response> resp = patientLogin.ExecuteAPI();
			patientToken = resp.body().path("token");
			System.out.println("patient token = " + patientToken);
			// Saving the token to TokenMap
			Tokens.TokenMap.put("PatientToken", patientToken);

			// Saving Token to PatientMap
			Tokens.PatientMap.put("PatientToken", patientToken);
			System.out.println("Generated patient token.... Key :  PatientToken , Value : "
					+ Tokens.PatientMap.get("PatientToken"));

			// System.out.println("patient token = "+patientToken);
		}
	}
}
