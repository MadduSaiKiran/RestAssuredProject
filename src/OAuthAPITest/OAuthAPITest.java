package OAuthAPITest;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuthAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//AuthorizationServer Post Request - to get token Id
		// StatusCode - 200 Ok
		
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
		
//		Get Course Details API using access_token collected in above steps
//		StatuCode - 401 Unauthorized
		
		String response2 =
		given()
			.queryParam("access_token", accessToken)
		.when()
			.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
			.asString();
		
		System.out.println(response2);
	}

}
