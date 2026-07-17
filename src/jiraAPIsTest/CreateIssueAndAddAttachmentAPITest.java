package jiraAPIsTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import fileUtils.jiraPayload;



public class CreateIssueAndAddAttachmentAPITest {
	public static void main(String args[]) {
		RestAssured.baseURI ="https://maddusaikiran196.atlassian.net/"; 
		
		//Creating the Issue 
		String createIssueResponse = 
		given()
			.header("Content-Type","application/json")
			.header("Authorization","Basic bWFkZHVzYWlraXJhbjE5NkBnbWFpbC5jb206QVRBVFQzeEZmR0YwQlQ1RUFDbkJuekI0b2VJMHpiUnNRWEdnMElPbmhwTEhNTUVHRVVhVkRqc1FZbXk1bDRDbjlFRjN3eTdDY3BneGJ2OWJ1aVVHNGdwSVEyaHVBTFRSSUR1OFB5Tkc5N3h1Slk1cjNZZGFvNjllS0xkTGJUeVlDNXZOanRrOVFHN29DU01reDM2dEpIenNtRmtOT2lhcFZ4UjVFVzZBYTdtWG5IZ2FkLXRoaDFrPTYwRTI0MDY4")
			.body(jiraPayload.getCreateIssuePayload())
		.when()
			.post("rest/api/3/issue")
		.then()
			.log().body().assertThat().statusCode(201)
			.extract().response().asString();
			
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		
		// Adding attachment
		given()
			.multiPart("file", new File("src/jiraAPIsTest/BugAttachment.png"))
			.pathParam("key", issueId)
			.header("X-Atlassian-Token","no-check")
			.header("Authorization","Basic bWFkZHVzYWlraXJhbjE5NkBnbWFpbC5jb206QVRBVFQzeEZmR0YwQlQ1RUFDbkJuekI0b2VJMHpiUnNRWEdnMElPbmhwTEhNTUVHRVVhVkRqc1FZbXk1bDRDbjlFRjN3eTdDY3BneGJ2OWJ1aVVHNGdwSVEyaHVBTFRSSUR1OFB5Tkc5N3h1Slk1cjNZZGFvNjllS0xkTGJUeVlDNXZOanRrOVFHN29DU01reDM2dEpIenNtRmtOT2lhcFZ4UjVFVzZBYTdtWG5IZ2FkLXRoaDFrPTYwRTI0MDY4")
		.when()
			.post("rest/api/3/issue/{key}/attachments")
		.then()
			.log().body().assertThat().statusCode(200);
		
	}
}
