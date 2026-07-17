package googlePlaceAPIsTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import fileUtils.payload;

public class AddUpdateGetAPITest {

	public static void main(String[] args){
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response
		//Add Place -> Update place with new address -> get place to validate if new address is present 
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		System.out.println("------------------Add Place API----------------------");
		//Extract response
		String response = given().log().uri().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().body().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		//Fetch place_id
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		System.out.println("------------------Update Place API----------------------");
		//Update Place
		String newAddress = "70 winter walk, USA";
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().body().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println("------------------Get Place API----------------------");
		//Get Place
		String getPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().post("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		
	
	}

}
