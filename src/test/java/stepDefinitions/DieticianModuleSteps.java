package stepDefinitions;

import java.util.Map;

import org.testng.Assert;

import com.google.gson.Gson;

import DieticianPojo.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
	       String dietReqBody = DataHandler.GetDieticianRequestBody(sheet);
		   apiFunction=new APIFunction(dietReqBody,APIConstant.CREATE_DIETICIAN_ENDPOINT,
				   APIConstant.POST,singleDataRow,Tokens.TokenMap.get("AdminToken") );

		}

		@When("Admin send POST HTTP request with endpoint")
		public void admin_send_post_http_request_with_endpoint() {
			ResponseOptions<Response> resp=apiFunction.ExecuteAPI();
			
			if (resp.getStatusCode()==201) {
				System.out.println(singleDataRow.get("DieticianID")+" "+ resp.body().path("id"));
				Tokens.dietIdMap.put(singleDataRow.get("DieticianID"), Integer.toString(resp.body().path("id")));
				
				System.out.println(singleDataRow.get("DieticianLogin")+" "+ resp.body().path("Email"));
				Tokens.dietIdMap.put(singleDataRow.get("DieticianLogin"), resp.body().path("Email"));
				
				System.out.println(singleDataRow.get("DieticianPwd")+" "+ resp.body().path("loginPassword"));
				Tokens.dietIdMap.put(singleDataRow.get("DieticianPwd"), resp.body().path("loginPassword"));
			}
		    
		}
		
		@Then("Admin recieves response code")
		public void admin_recieves_response_code() {
			System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
			Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
			
			
;
		}
		@Then("Admin generates Dietician Token")
		public void admin_generates_dietician_token() {
			//Create Dietician Token 
			
			String deiticianLoginId = Tokens.dietIdMap.get(singleDataRow.get("DieticianLogin"));
			String deiticianLoginPwd = Tokens.dietIdMap.get(singleDataRow.get("DieticianPwd"));
			
			System.out.println(deiticianLoginId);
			System.out.println(deiticianLoginPwd);
			
			UserLogin deiticianLogin = new UserLogin(deiticianLoginId ,	 deiticianLoginPwd);
			APIFunction login= new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
					new Gson().toJson(deiticianLogin));
			ResponseOptions<Response> resp= login.ExecuteAPI();
			
			//Saving Token

			Tokens.dietIdMap.put("dietBearerToken",resp.body().path("token"));
			System.out.println("Saving token; Key :  DieticianToken , Value : "+resp.body().path("token"));
		    
		}

//		@Then("Admin saves the dietician token")
//		public void admin_saves_the_dietician_token() {
//			Tokens.dietIdMap.put("dietIdMap",apiFunction.response.body().path("token"));
//			String dieticianToken = apiFunction.response.body().path("token");
//			System.out.println("Saving token; Key :  dietIdMap, Value : "+dieticianToken);
//		}

	}

	

	
	


	//private Response response;
//   private String token;
//   Map<String,String> data = new HashMap<String,String>();
//	public static ResponseOptions<Response> response;
//	
//	RequestSpecification request;
//	ValidatableResponse valid_resp;
//	public static String Admin_token="";
//
//
//    @Given("Admin creates POST Request  with valid credentials for login dietician.")
//	public void admin_creates_post_request_with_valid_credentials_for_login_dietician() {
//    	  try {
//    		  String postAPIRequestBody = FileUtils.readFileToString(new File(APIConstant.POST_API_USERLOGIN_REQUESTBODY),"UTF-8");
//    		  System.out.println(postAPIRequestBody);
//			RestAssured
//			     .given()
//			         .contentType(ContentType.JSON)
//			         .body(postAPIRequestBody)
//			         .baseUri(APIConstant.BASE_URL+"/login")
//			       .when()
//			        .post()
//			       .then()
//			           .assertThat()
//			           .statusCode(200)
//			.extract()
//			    .response();
//			
////			String adminToken = response.jsonPath().getString("token");
////			System.out.println(adminToken);
//			//String AdminToken =JsonPath.read(response.body().asString(),"$.token");
//			         
//			          
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//    }
//
//   
//   
//
//






