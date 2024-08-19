package stepdefinitions;

import java.util.Map;

import org.junit.Assert;

import com.google.gson.Gson;

import DieticianPojo.UserLogin;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class DieticianModuleSteps {
	APIFunction apiFunction;
	Map<String, String> singleDataRow;

	
	@Given("Admin creates dietician Post request with request body for {string} and {string}")
	public void admin_creates_dietician_post_request_with_request_body_for_and(String sheet, String row) {
	   singleDataRow=DataHandler.getDataRow(sheet,row);
       String dietReqBody = DataHandler.GetDieticianRequestBody(sheet, row);
	   apiFunction=new APIFunction(dietReqBody,APIConstant.CREATE_DIETICIAN_ENDPOINT,
			   APIConstant.POST,singleDataRow,Tokens.TokenMap.get("AdminToken") );

	}

	@When("Admin send POST HTTP request with endpoint")
	public void admin_send_post_http_request_with_endpoint() {
		ResponseOptions<Response> resp=apiFunction.ExecuteAPI();
		
		if (resp.getStatusCode()==201) {
			System.out.println(singleDataRow.get("DieticianID")+" "+ resp.body().path("id"));
			Tokens.dietIdMap.put(singleDataRow.get("DieticianID"), Integer.toString(resp.body().path("id")));
		}
	    
	}
	
	@Then("Admin recieves response code")
	public void admin_recieves_response_code() {
		System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
		
		//Create Dietician Token
		UserLogin user=new UserLogin( apiFunction.response.body().path("Email"),	 apiFunction.response.body().path("loginPassword"));
		APIFunction login= new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
				new Gson().toJson(user));
		ResponseOptions<Response> resp= login.ExecuteAPI();

		Tokens.TokenMap.put("dietBearerToken",resp.body().path("token"));
		System.out.println("Saving token; Key :  DieticianToken , Value : "+Tokens.TokenMap.get("dietBearerToken"));
	}


}
