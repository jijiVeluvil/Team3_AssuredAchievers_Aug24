package stepdefinitions;

import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class UserLogout {
	APIFunction apiFunction;
	Map<String, String> singleDataRow;


	@Given("User creates Logout Get request with request body for {string} and {string}")
	public void user_creates_logout_get_request_with_request_body_for_and(String sheet, String row) {
		singleDataRow=DataHandler.getDataRow(sheet,row);

		System.out.println("Using token for logout ; Key :  "+singleDataRow.get("TokenName") +", Value : "+Tokens.TokenMap.get(singleDataRow.get("TokenName")));

		apiFunction = new APIFunction(null,APIConstant.USER_LOGOUT_ENDPOINT,APIConstant.GET,singleDataRow,Tokens.TokenMap.get(singleDataRow.get("TokenName")));

	}

	@When("User send POST HTTP request with logout endpoint")
	public void user_send_post_http_request_with_logout_endpoint() {
		
		apiFunction.ExecuteAPI();

	}

	@Then("User recieves response code for logout")
	public void user_recieves_response_code_for_logout() {
		
		System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());

	}


}