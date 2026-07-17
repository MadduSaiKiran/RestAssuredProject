package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.junit.Assert;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefinition extends Utils {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	public static String placeId;
	
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {
		
		
		requestSpecification = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
		
		// Response Specification
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).log(LogDetail.BODY)
				.expectContentType(ContentType.JSON).build();
		
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String apiMethod) {
		//Constructor will be called with valueOf resource you passed and initialize corresponding method value to it. 
		APIResources apiResources = APIResources.valueOf(resource);
		if (apiMethod.equalsIgnoreCase("POST"))
			response = requestSpecification.when().post(apiResources.getResource());
		else if (apiMethod.equalsIgnoreCase("GET"))
			response = requestSpecification.when().get(apiResources.getResource());
		else if (apiMethod.equalsIgnoreCase("DELETE"))
			response = requestSpecification.when().delete(apiResources.getResource());
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(int statusCode) {
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedvalue) {
		
		Assert.assertEquals(getJsonPath(response, keyValue),expectedvalue);
	}
	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {

	placeId = getJsonPath(response,"place_id");
	requestSpecification = given().spec(requestSpecification()).queryParam("place_id", placeId);
	user_calls_with_post_http_request(resource, "GET");
	Assert.assertEquals(getJsonPath(response, "name"),expectedName);
	
	}
	@Given("Delete Place Payload")
	public void delete_place_payload() throws IOException {
		requestSpecification = given().spec(requestSpecification())
				.body(data.deletePlacePayload(placeId));
	}

	
}
