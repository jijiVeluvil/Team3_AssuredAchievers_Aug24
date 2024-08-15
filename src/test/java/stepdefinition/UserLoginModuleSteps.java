package stepdefinition;



import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;

public class UserLoginModuleSteps 
{
	APIFunction apiFunction;
	String Admintoken;

	@Given("User creates login  Post request with request body")
	public void user_creates_login_post_request_with_request_body() {

		apiFunction=new APIFunction(APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
				DataHandler.getRequestBody("valid_creds"), null);
		
	}

	@When("User send POST HTTP request with endpoint")
	public void user_send_post_http_request_with_endpoint() {

		apiFunction.ExecuteAPI();
		if(apiFunction.ExecuteAPI().getStatusCode()==200)
		{
			Admintoken = apiFunction.ExecuteAPI().body().path("token");
			System.out.println("Admintoken --> "+Admintoken);
		}
	}

	@Then("User recieves {int} created with response body")
	public void user_recieves_created_with_response_body(Integer respCode) {

		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
		//Assert.assertEquals(respCode, apiFunction.response.getStatusCode());


	}

	@Given("User creates login  Post request with invalid credential")
	public void user_creates_login_post_request_with_invalid_credential() {

		apiFunction=new APIFunction(APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST,DataHandler.getRequestBody("invalid_creds"), null);

	}

	@Then("User recieves {int} unauthorized")
	public void user_recieves_unauthorized(Integer respCode) {

		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
		System.out.println(apiFunction.response.getStatusLine() +" expected : unauthorized" );   //~ unauthorized
		Assert.assertEquals(respCode.intValue(), apiFunction.response.getStatusCode());

	}

	@Given("User creates login  GET request with request body")
	public void user_creates_login_get_request_with_request_body() {

		apiFunction=new APIFunction(APIConstant.USER_LOGIN_ENDPOINT, APIConstant.GET,DataHandler.getRequestBody("invalid_method"), null);

	}

	@When("User send GET HTTP request with endpoint")
	public void user_send_get_http_request_with_endpoint() {

		apiFunction.ExecuteAPI();

	}

	@Then("User recieves {int} method not allowed")
	public void user_recieves_method_not_allowed(Integer respCode) {

		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
		System.out.println(apiFunction.response.getStatusLine() +" Exp :   method not allowed");   //~  method not allowed
		Assert.assertEquals(respCode.intValue(), apiFunction.response.getStatusCode());
	}

	@When("User send POST HTTP request with invalid endpoint")
	public void user_send_post_http_request_with_invalid_endpoint() {

		apiFunction.url=APIConstant.BASE_URL+"/invalid/login/endpoint";
		apiFunction.ExecuteAPI();

	}

	@Given("User creates login  Post request with request body and invalid content type")
	public void user_creates_login_post_request_with_request_body_and_invalid_content_type() {

		apiFunction=new APIFunction(APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST,DataHandler.getRequestBody("invalid_contentType"), null);
		apiFunction.contentType="application/xml";

	}

	@Then("User recieves {int} unsupported media type")
	public void user_recieves_unsupported_media_type(Integer respCode) {

		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
		System.out.println(apiFunction.response.getStatusLine() +" expected : unsupported media type" );   //~ unsupported media typ
		Assert.assertEquals(respCode.intValue(), apiFunction.response.getStatusCode());
	}

}
