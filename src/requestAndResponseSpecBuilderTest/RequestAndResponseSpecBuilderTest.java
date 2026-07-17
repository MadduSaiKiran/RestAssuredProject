package requestAndResponseSpecBuilderTest;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serializationUsingGoogleAPITestPOJOClasses.AddPlace;
import serializationUsingGoogleAPITestPOJOClasses.Location;

public class RequestAndResponseSpecBuilderTest {
	@Test
	public void requestAndResponseSpecBuilderTest() {
		
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
		
		//Request Specification
		RequestSpecification request = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
		 		.addQueryParam("key", "qaclick123")
		 		.setContentType(ContentType.JSON)
		 		.build();
		
		//Response Specification
		ResponseSpecification response = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.log(LogDetail.BODY)
				.expectContentType(ContentType.JSON)
				.build();
		
		
		RequestSpecification requestSpecification = given().spec(request)
			.body(addPlace);
		
		String responseString = requestSpecification.when()
			.post("/maps/api/place/add/json")
		.then()
			.spec(response)
			.extract().response().asString();
		
		System.out.println("-----------------------------------Response Body-------------------------------");
		System.out.println(responseString);
		
		Assert.assertNotNull(responseString);
		
	}

}
