package stepdefinition;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DietitianPojo.PatientDataFieldsVo;
import DietitianPojo.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class UserLoginModuleSteps 
{
	APIFunction apiFunction;
	Map<String, String> singleDataRow;
	String loginjsonSchema=null;

	@Given("User creates login Post request with request body for {string} and {string}")
	public void user_creates_login_post_request_with_request_body_for_and(String sheet, String row) {

		try {
			loginjsonSchema = FileUtils.readFileToString(new File(APIConstant.LOGIN_SCHEMA_FILE), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		singleDataRow=DataHandler.getDataRow(sheet,row);

		UserLogin login=new UserLogin(singleDataRow.get("userLoginEmail"),singleDataRow.get("password"));

		String body= new Gson().toJson(login);

		apiFunction = new APIFunction(body,APIConstant.USER_LOGIN_ENDPOINT,APIConstant.POST,singleDataRow);

	}

	@When("User send POST HTTP request with endpoint")
	public void user_send_post_http_request_with_endpoint()  {

		apiFunction.ExecuteAPI();
		
	}

	@Then("User recieves response code")
	public void user_recieves_response_code() {

		if(apiFunction.response.getStatusCode()==200)
		{
			if(apiFunction.response.body().path("token")!=null && singleDataRow.get("TokenName") !=null)
			{
				System.out.println("--------- On Success Store token to token map -------------");
				//log.append("\nSaving token; Key :  "+singleDataRow.get("TokenName") +", Value : "+apiFunction.response.body().path("token"));
				System.out.println("Saving token; Key :  "+singleDataRow.get("TokenName") +", Value : "+apiFunction.response.body().path("token"));

				Tokens.TokenMap.put(singleDataRow.get("TokenName") ,apiFunction.response.body().path("token"));

				System.out.println("--------- Json Schema Validation -------------");
				apiFunction.response.then().body(JsonSchemaValidator.matchesJsonSchema(loginjsonSchema));
				
				System.out.println("--------- Logging header ------------\n");
				apiFunction.response.then().log().headers();	
				
				System.out.println("--------- Logging status -------------\n");
				apiFunction.response.then().log().status();
				
			}

		}
		
		System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
		Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());


	}

}
	


