package stepdefinition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import library.APIConstant;
import library.APIFunction;
import library.DataHandler;
import library.Tokens;

public class MorbidityModuleSteps {
	APIFunction apiFunction = new APIFunction();;
	Map<String, String> singleDataRow;
    static String getAllMorbiditySchema = null;
    
	// Get Request to get morbidity details

	@Given("User gets morbidity details with {string},{string},{string}")
	public void user_gets_morbidity_details_with(String token, String method, String endpoint)
			throws FileNotFoundException {
		
		try {
			getAllMorbiditySchema = FileUtils.readFileToString(new File(APIConstant.MORBIDITY_SCHEMA_FILE), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileOutputStream file = new FileOutputStream("Team3_RestAssured_log.log");
		PrintStream stream = new PrintStream(file, true);

		if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, APIConstant.GET_ALL_MORBIDITY, APIConstant.GET);
		} else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"), APIConstant.GET_ALL_MORBIDITY,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("POST")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY,
					APIConstant.POST);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), "/morbidty", APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), APIConstant.GET_ALL_MORBIDITY,
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("POST")
				&& endpoint.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), APIConstant.GET_ALL_MORBIDITY,
					APIConstant.POST);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), "/morbidty", APIConstant.GET);
		}

		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("User sends HTTP request")
	public void user_sends_http_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("User recieves response status code {string} with {string} message")
	public void user_recieves_response_status_code_with_message(String statusCode, String respMsg) {
		Assert.assertEquals(Integer.parseInt(statusCode), apiFunction.response.getStatusCode());
		Assert.assertTrue(apiFunction.response.getStatusLine().contains(respMsg));
		
		if (apiFunction.response.getStatusCode() == 200) {
			apiFunction.response.then().body(JsonSchemaValidator.matchesJsonSchema(getAllMorbiditySchema));
		}
	}

	// get morbidity by test name by admin

	@Given("Admin gets morbidity details by {string}")
	public void admin_gets_morbidity_details_by(String testName) throws FileNotFoundException {

		FileOutputStream file = new FileOutputStream("Team3_RestAssured_log.log");
		PrintStream stream = new PrintStream(file, true);

		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY + "/" + testName,
				APIConstant.GET);

		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("Admin sends HTTP get request")
	public void admin_sends_http_get_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("Admin recieves response status code")
	public void admin_recieves_response_status_code() {
		Assert.assertEquals(200, apiFunction.response.getStatusCode());
	}

	// get morbidity by test name by dietician

	@Given("Dietician gets morbidity details by {string}")
	public void dietician_gets_morbidity_details_by(String testName) throws FileNotFoundException {

		FileOutputStream file = new FileOutputStream("Team3_RestAssured_log.log");
		PrintStream stream = new PrintStream(file, true);

		apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"),
				APIConstant.GET_ALL_MORBIDITY + "/" + testName, APIConstant.GET);

		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("Dietician sends HTTP get request")
	public void dietician_sends_http_get_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("Dietician recieves response status code")
	public void dietician_recieves_response_status_code() {
		Assert.assertEquals(200, apiFunction.response.getStatusCode());
	}

	// Get request to get morbidity by test name with invalid values using excel

	@Given("Creates request for the user to get morbidity by testname with {string} and {string}")
	public void creates_request_for_the_user_to_get_morbidity_by_testname_with_and(String sheet, String row)
			throws FileNotFoundException {

		FileOutputStream file = new FileOutputStream("Team3_RestAssured_log.log");
		PrintStream stream = new PrintStream(file, true);

		singleDataRow = DataHandler.getDataRow(sheet, row);
		String endpoint = singleDataRow.get("Endpoint");
		String method = singleDataRow.get("Method");
		String token = Tokens.TokenMap.get(singleDataRow.get("TokenName"));
		apiFunction = new APIFunction(null, endpoint, method, singleDataRow, token);

		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("User sends HTTP get request")
	public void user_sends_http_get_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("User recieves response status code for getting morbidity by testname")
	public void user_recieves_response_status_code_for_getting_morbidity_by_testname() {
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("ExpectedCode")), apiFunction.response.getStatusCode());
	}

}
