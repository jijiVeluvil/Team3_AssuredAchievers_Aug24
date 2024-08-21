package stepdefinitions;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.google.gson.Gson;

import DieticianPojo.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class DieticianModuleSteps {
	APIFunction apiFunction;
	Map<String, String> singleDataRow;
	private static String dietEmailForDietRole;
	private static String dietPswdForDietRole;
	String dieticianSchema=null;

	@Given("Admin creates dietician with request body for {string} and {string}")
	public void admin_creates_dietician_with_request_body_for_and(String sheet, String row) throws FileNotFoundException {
		
		try {
			dieticianSchema = FileUtils.readFileToString(new File (APIConstant.DIETICIAN_SCHEMA_FILE),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true);
		
		singleDataRow = DataHandler.getDataRow(sheet, row);
		String dietReqBody = DataHandler.GetDieticianRequestBody(sheet, row);
		apiFunction = new APIFunction(dietReqBody, APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.POST,
				singleDataRow, Tokens.TokenMap.get("AdminToken"));
		
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));

	}

	@When("Admin sends HTTP request with endpoint")
	public void admin_sends_http_request_with_endpoint() {
		ResponseOptions<Response> resp = apiFunction.ExecuteAPI();

		if (resp.getStatusCode() == 201 && singleDataRow.get("Scenario").equalsIgnoreCase("create_valid_dieticianId")) {
			
			dietEmailForDietRole = apiFunction.response.body().path("Email");
			dietPswdForDietRole = apiFunction.response.body().path("loginPassword");
			
			System.out.println(singleDataRow.get("DieticianID") + " " + resp.body().path("id"));
			Tokens.dietIdMap.put(singleDataRow.get("DieticianId"), Integer.toString(resp.body().path("id")));
			
			
		apiFunction.response.then().body(JsonSchemaValidator.matchesJsonSchema(dieticianSchema));
		apiFunction.response.then().log().body(true);
		}

	}

	@Then("Admin recieves response code")
	public void admin_recieves_response_code() {
		
		System.out.println("Expected code : " + singleDataRow.get("expectedCode") + ", Actual code : "
				+ apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("ExpectedCode")), apiFunction.response.getStatusCode());
		
		if (apiFunction.response.getStatusCode() == 201) {
			apiFunction.response.then().body(JsonSchemaValidator.matchesJsonSchema(dieticianSchema));
		} 

	}

	// Create Dietician Token
	@Given("Dietician creates login Post request with request body")
	public void dietician_creates_login_post_request_with_request_body() throws FileNotFoundException {
		
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true);
		
		UserLogin user = new UserLogin(dietEmailForDietRole, dietPswdForDietRole);
		apiFunction = new APIFunction(null, APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, new Gson().toJson(user));
		
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("Dietician send POST HTTP request with endpoint")
	public void dietician_send_post_http_request_with_endpoint() {
		ResponseOptions<Response> resp = apiFunction.ExecuteAPI();

		if (resp.getStatusCode() == 200) {
			System.out.println("Saving token; Key : " + " Value :" + resp.body().path("token"));
			Tokens.TokenMap.put("dietBearerToken", resp.body().path("token"));
			//Extra added
			Tokens.TokenMap.put("dietcianEmailId", resp.body().path("Email"));
			Tokens.TokenMap.put("dieticianPassword", resp.body().path("lonpassword"));
		}
	}

	@Then("Dietician recieves login response code")
	public void dietician_recieves_login_response_code() {
		Assert.assertEquals(200, apiFunction.response.getStatusCode());
	}

	// Update Dietician
	@Given("Admin updates dietician details with request body for {string} and {string}")
	public void admin_updates_dietician_details_with_request_body_for_and(String sheet, String row) throws FileNotFoundException {
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true);
		
		singleDataRow = DataHandler.getDataRow(sheet, row);
		String dietReqBody = DataHandler.GetDieticianRequestBody(sheet, row);
		String dietIDForCRUD = Tokens.dietIdMap.get("dietIdForCRUD");
		String endpoint = APIConstant.CREATE_DIETICIAN_ENDPOINT + "/" + dietIDForCRUD;
		System.out.println("Endpoint is " + endpoint);
		apiFunction = new APIFunction(dietReqBody, endpoint, APIConstant.PUT, singleDataRow,
				Tokens.TokenMap.get("AdminToken"));
		
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));

	}

	// Get Request to get all dieticians
	@Given("Admin retrieves all dietician details with {string},{string},{string}")
	public void admin_retrieves_all_dietician_details_with(String token, String method, String endpoint) throws FileNotFoundException {
		
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true);
		
		if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.CREATE_DIETICIAN_ENDPOINT,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("PUT")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.CREATE_DIETICIAN_ENDPOINT,
					APIConstant.PUT);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), "/dietici", APIConstant.GET);
		}
		 else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
					&& endpoint.equalsIgnoreCase("valid")) {
				apiFunction = new APIFunction(Tokens.dietIdMap.get("dietBearerToken"), APIConstant.CREATE_DIETICIAN_ENDPOINT,
						APIConstant.GET);
	}
		 else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET")
					&& endpoint.equalsIgnoreCase("valid")) {
				apiFunction = new APIFunction(Tokens.patIdMap.get("patBearerToken"), APIConstant.CREATE_DIETICIAN_ENDPOINT,
						APIConstant.GET);
				
				RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
				RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}
	}

	@When("Admin sends HTTP request")
	public void admin_sends_http_request() {
		//APIFunction apiFunction=new APIFunction();
		apiFunction.ExecuteAPI();
	}

	@Then("Admin recieves response status code {string} with {string} message")
	public void admin_recieves_response_status_code_with_message(String statusCode, String res) {

       
        
		System.out.println("response code is " + apiFunction.response.getStatusCode());
		System.out.println("Response status line of " + apiFunction.response.getStatusCode() + " is "
				+ apiFunction.response.getStatusLine());
		Assert.assertEquals(Integer.parseInt(statusCode), apiFunction.response.getStatusCode());
		// Assert.assertTrue(apiFunction.response.getStatusLine().contains(msg))
	}

	@Given("Admin retrieves Dietician details with {string},{string},{string},{string}")
	public void admin_retrieves_dietician_details_with(String token, String method, String id, String endpoint) throws FileNotFoundException {
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true); 
		
		String dietIDForCRUD = Tokens.dietIdMap.get("dietIdForCRUD");
		String endpointOfUrl1 = APIConstant.CREATE_DIETICIAN_ENDPOINT + "/" + dietIDForCRUD;
		String dietIdForDietRole = Tokens.dietIdMap.get("dietIdForDietRole");
		String endpointOfUrl2 = APIConstant.CREATE_DIETICIAN_ENDPOINT + "/" + dietIdForDietRole;

		if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("PUT") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.PUT);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("invalidId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl2 + "3", APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), "/dietican/" + dietIDForCRUD,
					APIConstant.GET);
		}
		else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.dietIdMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.GET);
		}
		else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.patIdMap.get("patBearerToken"), endpointOfUrl1, APIConstant.GET);
			
			
		}
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
		
	}
	
	
	//Delete Request
	
	@Given("Admin deletes dietician Id with {string},{string},{string},{string}")
	public void admin_deletes_dietician_id_with(String token, String method, String id, String endpoint) throws FileNotFoundException {
		
		FileOutputStream file = new FileOutputStream("request_log.text");
		PrintStream stream = new PrintStream(file, true);
		
		String dietIDForCRUD = Tokens.dietIdMap.get("dietIdForCRUD");
		String dietIdForDietRole = Tokens.dietIdMap.get("dietIdForDietRole");
		String endpointOfUrl1 = APIConstant.CREATE_DIETICIAN_ENDPOINT + "/" + dietIDForCRUD;;
		String endpointOfUrl2 = APIConstant.CREATE_DIETICIAN_ENDPOINT + "/" + dietIdForDietRole;
		
		if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("DELETE") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.DELETE);
		} 
		else if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("DELETE")
				&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, endpointOfUrl1, APIConstant.DELETE);
		}
	 else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("PUT")
			&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("valid")) {
		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1, APIConstant.PUT);
	}
	 else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("DELETE")
			&& id.equalsIgnoreCase("invalidId") && endpoint.equalsIgnoreCase("valid")) {
		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), endpointOfUrl1 + "3",
				APIConstant.DELETE);
	
	}
	 else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("DELETE")
			&& id.equalsIgnoreCase("validId") && endpoint.equalsIgnoreCase("invalid")) {
		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), "/dieticin/" + dietIDForCRUD,
				APIConstant.DELETE);
	}
	 else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("DELETE") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.dietIdMap.get("dietBearerToken"), endpointOfUrl1, APIConstant.DELETE);
		} 
	 else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("DELETE") && id.equalsIgnoreCase("validId")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.patIdMap.get("patBearerToken"), endpointOfUrl1, APIConstant.DELETE);
			
			
		} 
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

}


