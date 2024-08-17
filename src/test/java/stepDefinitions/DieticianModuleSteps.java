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
//	APIFunction apiFunction;
//	Map<String, String> singleDataRow;
//	public static Response response;
//
//	//public static String Admin_token="";
////	public static String deiticianToken="";
////	public static int dieticianID;
////	public static String DieticianID = String.valueOf(dieticianID);
////	public static String dieticianPassWord;
//	
//	@Given("User creates dietician  {string} Post request with request body")
//	public void user_creates_dietician_post_request_with_request_body(String testcase) {
//		String dieticianRequestBody=DataHandler.GetDieticianRequestBody(testcase);
//
//		System.out.println("dietician Request Body---->"+dieticianRequestBody);
//
//		apiFunction=new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.POST, 
//				dieticianRequestBody);
//
//	}
//
//	@Then("Admin generates Dietician Token")
//	public void admin_generates_dietician_token() {
//		UserLogin user=new UserLogin( apiFunction.response.body().path("Email"),	 apiFunction.response.body().path("loginPassword"));
//		APIFunction login= new APIFunction(null,APIConstant.USER_LOGIN_ENDPOINT, APIConstant.POST, 
//				new Gson().toJson(user));
//		ResponseOptions<Response> resp= login.ExecuteAPI();
//
//		Tokens.TokenMap.put("DieticianToken",resp.body().path("token"));
//		System.out.println("Saving token; Key :  DieticianToken , Value : "+apiFunction.response.body().path("token"));
//	   
//	}
//	@When("Admin send POST http request with endpoint")
//	public void Admin_send_post_http_request_with_endpoint()  {
//
//		ResponseOptions<Response> resp=apiFunction.ExecuteAPI();
//
//		if(resp.getStatusCode()==201)
//		{
//			if(resp.body().path("token")!=null && singleDataRow.get("TokenName") !=null)
//			{
//				System.out.println("Saving token; Key :  "+singleDataRow.get("TokenName") +", Value : "+resp.body().path("token"));
//
//				Tokens.TokenMap.put(singleDataRow.get("TokenName") ,resp.body().path("token"));
//				
//			}
//		}
//	}
//	@Then("User saves the dietician token")
//	public void user_saves_the_admin_token() {
//
//		Tokens.TokenMap.put("DieticianToken1",apiFunction.response.body().path("token"));
//		System.out.println("Saving token; Key :  DieticianToken1, Value : "+apiFunction.response.body().path("token"));
//
//	}

//		@Then("User recieves response code")
//		public void user_recieves_response_code() {
//
//			System.out.println("Expected code : "+singleDataRow.get("expectedCode")+", Actual code : "+apiFunction.response.getStatusCode());
//			Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
//			
//		}


	
	
//	@Given("Admin sets authorization to bearer token in DieticianModule")
//	public void admin_sets_authorization_to_bearer_token_in_dietician_module() {
//		RestassuredExtension.requestWithAuth(UserLoginModuleSteps.Admintoken);
//	}
	
	//----------------old--------------
//	@Given("Admin creates POST request with valid data.")
//	public void admin_creates_post_request_with_valid_data_mandatory_and_additional_details() {
//		String dieticianRequestBody=DataHandler.GetDieticianRequestBody(testcase);
//
//		System.out.println("dietician Request Body---->"+dieticianRequestBody);
//
//		apiFunction=new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.POST, 
//				dieticianRequestBody);

		
//		apiFunction=new APIFunction(APIConstant.CREATE_DIETICIAN_ENDPOINT, APIConstant.POST, 
//				DataHandler.getDataDietician("dietician1"),UserLoginModuleSteps.Admintoken);
//		//UserLoginModuleSteps.Admintoken
	    
	

//	@Then("Admin recieves {int} created and with response body.")
//	public void admin_recieves_created_and_with_response_body_auto_created_dietician_id_and_login_password(Integer respCode) {
//		System.out.println("Expected code : "+respCode+", Actual code : "+apiFunction.response.getStatusCode());
//		//Assert.assertEquals(respCode, apiFunction.response.getStatusCode());
//		//response.then().statusCode(respCode);
//	}

	
	

//	@When("Admin send POST http request with endpoint")
//	public void admin_send_post_http_request_with_endpoint() {
//		apiFunction.ExecuteAPI();
//		//System.out.println(apiFunction.ExecuteAPI());
//		if(apiFunction.ExecuteAPI().getStatusCode()==201)
//		{
//			dieticianID = apiFunction.ExecuteAPI().body().path("id");
//			System.out.println("dieticianID --> "+dieticianID);
//			
//			dieticianPassWord = apiFunction.ExecuteAPI().body().path("loginPassword");
//			System.out.println("dieticianPassWord --> "+dieticianPassWord);
//		}
	    
	    
	

	

	
	


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
//    @When("Admin creates Post Https method  with valid endpoint for login dietician.")
//	public void admin_creates_post_https_method_with_valid_endpoint_for_login_dietician() {
//       
//        // Validate the response
//    	response = RestAssuredExtension.PostOpsWithBody(APIConstant.USER_LOGIN_ENDPOINT, data);
//    }
//
//
//
//
//
////	@When("Admin creates Post Https method  with valid endpoint for login dietician.")
////	public void admin_creates_post_https_method_with_valid_endpoint_for_login_dietician() {
////		response = RestAssuredExtension.PostOpsWithBody(ConstantFilePaths.USER_LOGIN_ENDPOINT, data);
////		System.out.println("login:" +response);
////	}
////  
//   @Then("Admin User receives the bearer token for login dietician.")
//   public void admin_user_receives_the_bearer_token_for_login_dietician() {
//	   response.getBody().jsonPath().get("token");
//		
//		Admin_token = response.body().path("token");
//		System.out.println("Token:    "+Admin_token);
//      
//   }
//}
//   






