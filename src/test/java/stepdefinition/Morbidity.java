package stepdefinition;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.DataHandler;
import utilities.Tokens;

public class Morbidity {

	APIFunction apiFunction;
	Response response;
	Map<String, String> singleDataRow;

//********************************************No auth with dietician*********************************************	
	@Given("Dietician create GET request with no auth")
	public void dietician_create_get_request_with_no_auth() {
		apiFunction = new APIFunction(null, APIConstant.GET_ALL_MORBIDITY, APIConstant.GET);
	}

	@When("Dietician send GET request")
	public void dietician_send_get_http_request_with_endpoint() {
		apiFunction.ExecuteAPI();
	}

	@Then("Dietician recieves {int} unauthorized")
	public void dietician_recieves_unauthorized(Integer int1) {

		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}

//*****************************************Patient get request************************************

	@Given("Patient create GET request")
	public void patient_create_get_request() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("PatientToken"), APIConstant.GET_ALL_MORBIDITY,
				APIConstant.GET);
	}

	@When("Patient send GET http request")
	public void patient_send_get_http_request_with_endpoint() {
		apiFunction.ExecuteAPI();
	}

	@Then("Patient recieves {int} Forbidden")
	public void patient_recieves_forbidden(Integer int1) {
		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}

//*****************************************Positive admin get***********************************************    
	@Given("admin creates a GET request")
	public void admin_creates_a_get_request() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY,
				APIConstant.GET);
	}

	@When("admin send GET request")
	public void admin_send_get_request_with_endpoint() {
		apiFunction.ExecuteAPI();

	}

	@Then("admin recieves {int} ok with details of the patient id")
	public void admin_recieves_ok_with_details_of_the_patient_id(Integer int1) {
		Assert.assertEquals(int1.intValue(), apiFunction.response.getStatusCode());
		ResponseBody body = apiFunction.response.getBody();
		System.out.println(body.prettyPrint());

	}

//****************************************Admin with invalid method***********************************	    
	@Given("admin create POST request")
	public void admin_create_post_request() {
		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY,
				APIConstant.POST);
	}

	@When("admin send POST http request")
	public void admin_send_post_http_request() {
		apiFunction.ExecuteAPI();
	}

	@Then("admin recieves {int} method not allowed")
	public void admin_recieves_method_not_allowed(Integer int1) {

		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}

//*************************************Admin invalid endpoint*******************************************	    

	@Given("admin create GET request")
	public void admin_create_get_request() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"), APIConstant.GET_ALL_MORBIDITY_INVALID,
				APIConstant.GET);
	}

	@Then("admin recieves {int} not found")
	public void admin_recieves_not_found(Integer int1) {
		System.out.println("******************************************");
		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}

//********************************************Postive with dietician****************************************
	@Given("Dietician create GET request")
	public void Dietician_create_GET_request() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"), APIConstant.GET_ALL_MORBIDITY,
				APIConstant.GET);

	}

//***************************************************Invalid method Dietician**************************************
	@Given("Dietician create POST request")
	public void dietician_create_post_request() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"), APIConstant.GET_ALL_MORBIDITY,
				APIConstant.POST);
	}

	@When("Dietician send POST http request")
	public void dietician_send_post_http_request_with_endpoint() {

		apiFunction.ExecuteAPI();
	}

	@Then("Dietician recieves {int} method not allowed")
	public void dietician_recieves_method_not_allowed(Integer int1) {
		System.out.println("*****************************************");
		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}
//********************************************Dietician invalid endpoint****************************

	@Given("Dietician create GET request with invalid endpoint")
	public void dietician_create_get_request_with_invalid_endpoint() {

		apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"), APIConstant.GET_ALL_MORBIDITY_INVALID,
				APIConstant.GET);
	}

	@Then("Dietician recieves {int} not found")
	public void dietician_recieves_not_found(Integer int1) {
		assertEquals(int1.intValue(), apiFunction.response.getStatusCode());

	}

//***********************************Get Morbidity by test name****************************************

	 @Given("Dietician creates GET request to check morbidity by {string} and {string}")
    public void dietician_creates_get_request_to_check_morbidity_by_and(String sheet, String row) {
        singleDataRow = DataHandler.getDataRowMor(sheet, row);

        apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),
        		APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),APIConstant.GET);
       
    }

    @When("Dietician sends GET HTTP request to morbidity endpoint")
    public void dietician_sends_get_http_request_to_morbidity_endpoint() {
        apiFunction.ExecuteAPI();
    }

    @Then("Dietician receives response code for morbidity request")
    public void dietician_receives_response_code_for_morbidity_request() {
        System.out.println("Expected code : " + singleDataRow.get("expectedCode") + ", Actual code : " + apiFunction.response.getStatusCode());
        Assert.assertEquals(Integer.parseInt(singleDataRow.get("expectedCode")), apiFunction.response.getStatusCode());
    }
    
//*****************************************Patient retrieving*********************************************************   
    
    @Given("Patient creates GET request to check morbidity by {string} and {string}")
    public void patient_creates_get_request_to_check_morbidity_by_and(String sheet, String row) {
    	
    	singleDataRow = DataHandler.getDataRowMor(sheet, row);
     
        apiFunction = new APIFunction(APIConstant.GET,Tokens.TokenMap.get("PatientToken"),APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),
     		  APIConstant.GET );
       
    }

    @When("Patient send GET http request with endpoint")
    public void patient_send_get_http_request_with_validendpoint() {
        apiFunction.ExecuteAPI();
    }
    
//*****************************************Admin retrieve***************************************************    

    @Given("admin create GET request  to check morbidity by {string} and {string}")
    public void admin_create_get_request_to_check_morbidity_by_and(String sheet, String row) {
    	singleDataRow = DataHandler.getDataRowMor(sheet, row);

  apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),
		  APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),APIConstant.GET);
    }

    @When("admin send http request with endpoint")
    public void admin_send_http_request_with_endpoint() {
        apiFunction.ExecuteAPI();
    }

//********************************Admin retrieve by invalid method********************************************
    
    @Given("admin create POST request to check morbidity by {string} and {string}")
    public void admin_create_POST_request_to_check_morbidity_by(String sheet,String row) {
    
    	singleDataRow = DataHandler.getDataRowMor(sheet, row);

   apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),
    APIConstant.POST);
    	
 }
    
 //*********************************************Admin with invalid testname*********************************
    @Given("admin create GET request with invalid test name by {string} and {string}")
    public void admin_create_GET_request_with_invalid_testname_by(String sheet,String row) {
    	 apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),
    			  APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),APIConstant.GET);
    	
    }
//**********************************Admin with invalid endpoint*****************************************
    
    @Given("admin create GET request with invalid endpoint")
    public void admin_create_GET_request_with_invalid_endpoint() {
    
    	apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"), APIConstant.GET_ALL_MORBIDITY_INVALID,
				APIConstant.GET);	
    	
    }
    
 //*************************************Dietician by testname*********************************************
    
    @Given("Dietician create GET request to check morbidity by {string} and {string}")
    public void dietician_creatw_get_request_to_check_morbidity_by(String sheet,String row) {
    	
    	apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),
        		APIConstant.GET_ALL_MORBIDITY + "/" + singleDataRow.get("testname"),APIConstant.GET);   	
    	
    }
}

