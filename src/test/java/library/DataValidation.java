package library;

import org.testng.Assert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.response.Response;

public class DataValidation {
	public static void validateRespBodyForDietModule(Response response, String expectedRespBody) {

		JsonObject expJson = JsonParser.parseString(expectedRespBody).getAsJsonObject();
		JsonObject actualJson = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
		expJson.remove("DateOfBirth");
		expJson.remove("DieticianId");
		expJson.remove("ExpectedCode");
		expJson.remove("Endpoint");
		expJson.remove("Method");
		expJson.remove("TokenName");
		expJson.remove("Scenario");
		expJson.remove("ContentType");
		actualJson.remove("DateOfBirth");
		actualJson.remove("id");
		actualJson.remove("loginPassword");

		Assert.assertEquals(expJson, actualJson);
	}

	public static void validateRespBodyForPatientModule(String expectedRespBody, Response response) {

		JsonObject actualJson = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
		JsonObject expJson = JsonParser.parseString(expectedRespBody).getAsJsonObject();
		expJson.remove("Method");
		expJson.remove("TokenName");
		expJson.remove("Scenario");
		expJson.remove("ContentType");
		expJson.remove("ExpectedCode");
		expJson.remove("Endpoint");
		expJson.remove("File");
		expJson.remove("Vitals");
		actualJson.remove("FileMorbidity");
		actualJson.remove("FileMorbidityCondition");
		actualJson.remove("Vitals");
		actualJson.remove("patientId");
		actualJson.remove("DieticianId");
		actualJson.remove("LastVisitDate");
		Assert.assertEquals(actualJson, expJson);

	}
}