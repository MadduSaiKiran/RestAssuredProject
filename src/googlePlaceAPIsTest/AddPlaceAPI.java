package googlePlaceAPIsTest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import fileUtils.payload;

public class AddPlaceAPI {

	public static void main(String[] args){
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)");

	}

}
