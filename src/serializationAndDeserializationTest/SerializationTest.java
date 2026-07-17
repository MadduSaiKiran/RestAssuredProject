package serializationAndDeserializationTest;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import serializationUsingGoogleAPITestPOJOClasses.AddPlace;
import serializationUsingGoogleAPITestPOJOClasses.Location;

public class SerializationTest {
	@Test
	public void serializationTest() {
		
//		Initialize the POJO class attributes
		AddPlace addPlace = new AddPlace();
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		addPlace.setLocation(location);
		
		addPlace.setAccuracy(50);
		addPlace.setName("Frontline house");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("29, side layout, cohen 09");
		
		ArrayList<String> types = new ArrayList<String>(List.of("shoe park","shop")); //or add one by one types.add("shop")
		addPlace.setTypes(types);
		
		addPlace.setWebsite("http://google.com");
		addPlace.setLanguage("French-IN");
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().body()
			.queryParam("key","qaclick123")
			.body(addPlace)
		.when()
			.post("/maps/api/place/add/json")
		.then()
			.assertThat().statusCode(200)
			.extract().response().asString();
		System.out.println("-----------------------------------Response Body-------------------------------");
		System.out.println(response);
		Assert.assertNotNull(response);
		
	}
}
