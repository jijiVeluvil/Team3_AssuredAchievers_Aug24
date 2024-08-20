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


	/**
	 * APIFunction is constructor used for invalid type if pass through file
	 * @param body
	 * @param endpoint
	 * @param method
	 * @param singleDataRow
	 */
	public APIFunction(String body,String endpoint,String method,Map<String,String> singleDataRow ) {

		commonInit(body, endpoint, method, singleDataRow);

	}

	public APIFunction(String body,String endpoint,String method,Map<String,String> singleDataRow,String token ) {

		commonInit(body, endpoint, method, singleDataRow);

		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
		}
	}


	private void commonInit(String body,String endpoint,String method,Map<String,String> singleDataRow ) {

		if(singleDataRow.get("endpoint")!=null && !singleDataRow.get("endpoint").isEmpty())
			endpoint=singleDataRow.get("endpoint");

		if(singleDataRow.get("method")!=null && !singleDataRow.get("method").isEmpty())
			this.method=singleDataRow.get("method");

		if(singleDataRow.get("contentType")!=null && !singleDataRow.get("contentType").isEmpty())
			this.contentType=singleDataRow.get("contentType");
		else
			this.contentType="application/json";


		this.url=APIConstant.BASE_URL+endpoint;

		if(body!=null)
			builder.setBody(body);

		System.out.println("using request body ---> "+body);	

	}

	/**
	 * APIFunction is constructor works for get / delete / logout
	 * @param token
	 * @param uri
	 * @param method
	 */
	public APIFunction(String token,String uri,String method) {

		reuse(uri,method);

		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
		}

	}
	/**
	 * APIFunction is constructor works for post / put / login 
	 * @param token
	 * @param uri
	 * @param method
	 * @param body
	 */
	public APIFunction( String token,String uri,String method,String body) {

		reuse(uri,method);

		builder.setBody(body);

		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);

		}
	}
	/**
	 * APIFunction for patient with following parameter
	 * @param uri
	 * @param method
	 * @param token
	 * @param patientDataFieldsVo
	 */

	public APIFunction(String uri,String method, String token,DietitianPojo.PatientDataFieldsVo patientDataFieldsVo) {

		reuse(uri,method);

		builder.addMultiPart("patientInfo", patientDataFieldsVo.getPatientInfo());

		if(patientDataFieldsVo.getVitals()!=null)
			builder.addMultiPart("vitals", patientDataFieldsVo.getVitals());

		if(patientDataFieldsVo.getReportFile()!=null)
			builder.addMultiPart ("file",patientDataFieldsVo.getReportFile(),"application/pdf");


		contentType="multipart/form-data";

		if(token!=null)
		{
			builder.addHeader("Authorization", "Bearer "+token);
		}
	}


	private void reuse(String uri, String method) {

		this.url=APIConstant.BASE_URL+uri;
		this.method=method;

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

		System.out.println(" ----------------- API Call ---------------------");

		System.out.println("URL--> "+url);
		System.out.println("Method -> "+this.method);
		System.out.println("using contentType ---> "+contentType);
		System.out.println("response ---> "+response.getBody().asPrettyString());

		System.out.println(" --------------------------------------");



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