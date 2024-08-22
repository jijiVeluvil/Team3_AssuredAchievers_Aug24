package stepdefinition;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import library.APIConstant;
import library.APIFunction;
import library.DataHandler;
import library.Tokens;

public class UserLogout {
	APIFunction apiFunction;
	Map<String, String> singleDataRow;

	@Given("User creates Logout Get request with request body for {string} and {string}")
	public void user_creates_logout_get_request_with_request_body_for_and(String sheet, String row)
			throws FileNotFoundException {

		FileOutputStream file = new FileOutputStream("Team3_RestAssured_log.log");
		PrintStream stream = new PrintStream(file, true);

		singleDataRow = DataHandler.getDataRow(sheet, row);

		apiFunction = new APIFunction(null, APIConstant.USER_LOGOUT_ENDPOINT, APIConstant.GET, singleDataRow,
				Tokens.TokenMap.get(singleDataRow.get("TokenName")));

		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(stream));
	}

	@When("User send POST HTTP request with logout endpoint")
	public void user_send_post_http_request_with_logout_endpoint() {

		apiFunction.ExecuteAPI();

	}

	@Then("User recieves response code for logout")
	public void user_recieves_response_code_for_logout() {

		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
		System.out.println("\nLogging status -->");
		apiFunction.response.then().log().status();
		if (apiFunction.response.getStatusCode() == 200) {
			String contentType = "text/plain;charset=UTF-8";
			System.out.println("Response Content Type -->" + apiFunction.response.contentType());
			Assert.assertEquals(contentType, apiFunction.response.contentType());
		} else {
			System.out.println("Error-->" + apiFunction.response.body().path("error"));
			String contentType = "application/json";
			System.out.println("Response Content Type-->" + apiFunction.response.contentType());
			Assert.assertEquals(contentType, apiFunction.response.contentType());

		}

	}

}