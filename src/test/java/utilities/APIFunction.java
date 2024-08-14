package utilities;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

public class APIFunction {

	RequestSpecBuilder builder=new RequestSpecBuilder();
	public String method;
	public String url;
	public ResponseOptions<Response> response;
	
	public String contentType;
	
	public APIFunction(String uri,String method,String token) {

		reuse(uri,method);
	
		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
		}
		
	}

	public APIFunction(String uri,String method,String body, String token) {

		reuse(uri,method);

		builder.setBody(body);
		
		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
			
		}
	}
	
	public APIFunction(String uri,String method,Map<String,String> body, String token) {

		reuse(uri,method);
		
		builder.setBody(body);

		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
		}
	}



	private void reuse(String uri, String method) {

		this.url=APIConstant.BASE_URL+uri;
		this.method=method;

		System.out.println("URL--> "+url);
		System.out.println("Method -> "+this.method);

	}


	/**
	 * ExecuteAPI to execute the API for POST,GET,DELETE,PUT
	 * @return ResponseOptions<Response>
	 */
	public ResponseOptions<Response> ExecuteAPI()
	{
		RequestSpecification requestSpecification=builder.build();
		RequestSpecification request=RestAssured.given();
		
		if(contentType==null||contentType.isBlank())
			contentType="application/json";
		
		System.out.println("using contentType ---> "+contentType);
		
		request.contentType(contentType);
		
		request.spec(requestSpecification);

		if(this.method.equalsIgnoreCase(APIConstant.POST))
			response= request.post(this.url);
		else if(this.method.equalsIgnoreCase(APIConstant.DELETE))
			response= request.delete(this.url);
		else if(this.method.equalsIgnoreCase(APIConstant.GET))
			response= request.get(this.url);
		else if(this.method.equalsIgnoreCase(APIConstant.PUT))
			response= request.put(this.url);

		return response;
	}

	/**
	 * Executing API with query params being passed as the input of it
	 * @param queryPath
	 * @return ResponseOptions<Response>
	 */
	public ResponseOptions<Response> ExecuteWithQueryParam(Map<String,String> queryPath){
		builder.addQueryParams(queryPath);
		return ExecuteAPI();
	}


}
