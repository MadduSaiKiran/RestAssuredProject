package serializationAndDeserializationTest;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import deserializationTestPOJOClasses.GetCourseDetails;
import deserializationTestPOJOClasses.WebAutomation;
import io.restassured.path.json.JsonPath;

public class DeserializationTest {
	
	@Test
	public void deserializationTest() {
		
		String response = 
				given()
					.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
					.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
					.formParam("grant_type", "client_credentials")
					.formParam("scope", "trust")
				.when()
					.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
					.asString(); //extract() is not used after when() bcz when only sends the req there is no response available to extract at that point
				
				System.out.println(response);
				
				JsonPath js = new JsonPath(response);
				String accessToken = js.getString("access_token");
				
//				Get Course Details API using access_token collected in above steps
//				StatuCode - 401 Unauthorized
//				Deserialization
				GetCourseDetails getCourseDetails =
				given()
					.queryParam("access_token", accessToken)
				.when()
					.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
					.as(GetCourseDetails.class);
				
				System.out.println(getCourseDetails.getLinkedIn());
				String ct = getCourseDetails.getCourses().getWebAutomation().get(0).getCourseTitle();
				int pri= getCourseDetails.getCourses().getWebAutomation().get(0).getPrice();
				System.out.println(ct+" - "+pri);
				
				String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
				ArrayList<String> actualList = new ArrayList<String>();
				List<WebAutomation> webAutomationCourses = getCourseDetails.getCourses().getWebAutomation();
				for(int i=0;i<webAutomationCourses.size();i++) {
					String courseTitle = webAutomationCourses.get(i).getCourseTitle();
					actualList.add(courseTitle);
				}
				List<String> expectedList = Arrays.asList(courseTitles); //conversion - Array to List 
				Assert.assertTrue(actualList.equals(expectedList));
	}
}
