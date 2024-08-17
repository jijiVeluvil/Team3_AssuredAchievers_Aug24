package stepdefinition;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import utilities.APIConstant;
import utilities.APIFunction;
import utilities.Tokens;

public class Morbidity {
	
	 APIFunction apiFunction;
	       Response response;
	   
	
	    
//****************************************************************************************//	    
	@Given("admin creates a GET request")
	public void admin_creates_a_get_request() {
		
        apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.GET_ALL_MORBIDITY,APIConstant.GET);
		}

	@When("admin send GET request with endpoint")
	public void admin_send_get_request_with_endpoint() {
		apiFunction.ExecuteAPI();
	    
	}

	@Then("admin recieves {int} ok with details of the patient id")
	public void admin_recieves_ok_with_details_of_the_patient_id(Integer int1) {
		Assert.assertEquals(int1.intValue(), apiFunction.response.getStatusCode());
		ResponseBody body = apiFunction.response.getBody();
		System.out.println(body.prettyPrint());
	    
	}
	
//*****************************************Patient get request************************8
	
	@Given("Patient create GET request")
	public void patient_create_get_request() {
		
		 apiFunction = new APIFunction(Tokens.TokenMap.get("PatientToken"),APIConstant.GET_ALL_MORBIDITY,APIConstant.GET);
		}

	@When("Patient send GET http request with endpoint")
	public void patient_send_get_http_request_with_endpoint() {
	   apiFunction.ExecuteAPI();
	}

	@Then("Patient recieves {int} Forbidden")
	public void patient_recieves_forbidden(Integer int1) {
		assertEquals(int1.intValue(),apiFunction.response.getStatusCode());
	    
	}

//********************************************Positive*********************************************	
	 @Given("Dietician create GET request with no auth")
	    public void dietician_create_get_request_with_no_auth() {
	        apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),APIConstant.GET_ALL_MORBIDITY, APIConstant.GET);
	    }

	    @When("Dietician send GET http request with endpoint")
	    public void dietician_send_get_http_request_with_endpoint() {
	         apiFunction.ExecuteAPI();  
	    }

	    @Then("Dietician recieves {int} unauthorized")
	    public void dietician_recieves_unauthorized(Integer int1) {
	    	//System.out.println(response.getStatusCode());
	    	assertEquals(int1.intValue(),apiFunction.response.getStatusCode());
	        
	    }

//****************************************Admin with invalid method***********************************	    
	    @Given("admin create POST request")
	    public void admin_create_post_request() {
	    	 apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.GET_ALL_MORBIDITY, APIConstant.POST);
	    }

	    @When("admin send POST http request with endpoint")
	    public void admin_send_post_http_request_with_endpoint() {
	    	apiFunction.ExecuteAPI();
	    }

	    @Then("admin recieves {int} method not allowed")
	    public void admin_recieves_method_not_allowed(Integer int1) {
	    	//System.out.println(response.getStatusCode());
	    	assertEquals(int1.intValue(), apiFunction.response.getStatusCode());
	    	
	    }

//*************************************Admin invalid endpoint*******************************************	    
	   //do this
	    @Given("admin create GET request")
	    public void admin_create_get_request() {
	    	
	    	 apiFunction = new APIFunction(Tokens.TokenMap.get("AdminToken"),APIConstant.GET_ALL_MORBIDITY_INVALID, APIConstant.GET);
	    }

	    @When("admin send GET http request with invalid endpoint")
	    public void admin_send_get_http_request_with_invalid_endpoint() {
	    	apiFunction.ExecuteAPI();
	    }

	    @Then("admin recieves {int} not found")
	    public void admin_recieves_not_found(Integer int1) {
	    	System.out.println("******************************************");
	    	assertEquals(int1.intValue(), apiFunction.response.getStatusCode());
	        
	    }

//********************************************Postive with dietician****************************************
	 @Given("Dietician create GET request")
	 public void Dietician_create_GET_request() {
		 
		 apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),APIConstant.GET_ALL_MORBIDITY, APIConstant.GET);
		 
	 }
	 
//***************************************************Invalid method Dietician**************************************
	 @Given("Dietician create POST request")
	 public void dietician_create_post_request() {
		 
		 apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),APIConstant.GET_ALL_MORBIDITY, APIConstant.POST);	     
	 }

	 @When("Dietician send POST http request with endpoint")
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
	    	
	    	 apiFunction = new APIFunction(Tokens.TokenMap.get("DieticianToken"),APIConstant.GET_ALL_MORBIDITY_INVALID, APIConstant.GET);
	    }
	 
	 @When("Dietician send GET http request with invalid endpoint")
	 public void dietician_send_get_http_request_with_invalid_endpoint() {
		 apiFunction.ExecuteAPI();
		 
	    
	 }

	 @Then("Dietician recieves {int} not found")
	 public void dietician_recieves_not_found(Integer int1) {
		 assertEquals(int1.intValue(), apiFunction.response.getStatusCode());
	     
	 }
	    
	    
}
