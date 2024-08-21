package stepdefinition;

import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.APIConstant;
import library.APIFunction;
import library.DataHandler;
import library.Tokens;

public class Morbidity {
	APIFunction apiFunction = new APIFunction();;
	Map<String, String> singleDataRow;

	// Get Request to get morbidity details
	@Given("User gets morbidity details with {string},{string},{string}")
	public void user_gets_morbidity_details_with(String token, String method, String endpoint) {
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
	}

	@When("User sends HTTP request")
	public void user_sends_http_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("User recieves response status code {string} with {string} message")
	public void user_recieves_response_status_code_with_message(String statusCode, String respMsg) {
		Assert.assertEquals(Integer.parseInt(statusCode), apiFunction.response.getStatusCode());
	    Assert.assertTrue(apiFunction.response.getStatusLine().contains(respMsg));
	}

	// get morbidity by test name by admin
	@Given("Admin gets morbidity details by {string}")
	public void admin_gets_morbidity_details_by(String testName) {
		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY + "/" + testName,
				APIConstant.GET);
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
	public void dietician_gets_morbidity_details_by(String testName) {
		apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"),
				APIConstant.GET_ALL_MORBIDITY + "/" + testName, APIConstant.GET);
	}

	@When("Dietician sends HTTP get request")
	public void dietician_sends_http_get_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("Dietician recieves response status code")
	public void dietician_recieves_response_status_code() {
		Assert.assertEquals(200, apiFunction.response.getStatusCode());
	}

	// get morbidity by test name with invalid values (it is not working)
	@Given("Creates request for the user to get morbidity by testname with {string},{string},{string},{string}")
	public void creates_request_for_the_user_to_get_morbidity_by_testname_with(String token, String method,
			String endpoint, String testName) {
		if (token.equalsIgnoreCase("no auth") && method.equalsIgnoreCase("GET") && endpoint.equalsIgnoreCase("valid")
				&& testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(null, APIConstant.GET_ALL_MORBIDITY + "/Fasting Glucose", APIConstant.GET);
		} else if (token.equalsIgnoreCase("patient") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid") && testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("patBearerToken"),
					APIConstant.GET_ALL_MORBIDITY + "/Plasma Glucose", APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("POST")
				&& endpoint.equalsIgnoreCase("valid") && testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),
					APIConstant.GET_ALL_MORBIDITY + "/Average Glucose", APIConstant.POST);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid") && testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), "/morbdty" + "/T4", APIConstant.GET);
		} else if (token.equalsIgnoreCase("admin") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid") && testName.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY + "/A8",
					APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("POST")
				&& endpoint.equalsIgnoreCase("valid") && testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"),
					APIConstant.GET_ALL_MORBIDITY + "/Average Glucose", APIConstant.POST);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("invalid") && testName.equalsIgnoreCase("valid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), "/morbidty" + "/T3", APIConstant.GET);
		} else if (token.equalsIgnoreCase("dietician") && method.equalsIgnoreCase("GET")
				&& endpoint.equalsIgnoreCase("valid") && testName.equalsIgnoreCase("invalid")) {
			apiFunction = new APIFunction(Tokens.TokenMap.get("dietBearerToken"), APIConstant.GET_ALL_MORBIDITY + "/A9",
					APIConstant.GET);
		}
	}
    //Get request to get morbidity by test name with invalid values using excel (need to check)
	@Given("Creates request for the user to get morbidity by testname with {string} and {string}")
	public void creates_request_for_the_user_to_get_morbidity_by_testname_with_and(String sheet, String row) {
		singleDataRow=DataHandler.getDataRow(sheet,row);
		String endpoint = singleDataRow.get("Endpoint");
		String method = singleDataRow.get("Method");
		String token = Tokens.TokenMap.get(singleDataRow.get("TokenName"));
        apiFunction = new APIFunction(null,endpoint,method,singleDataRow,token);
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
