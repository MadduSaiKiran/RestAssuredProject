package libraryAPIsTest;

import static io.restassured.RestAssured.given;



import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import fileUtils.ReUsableMethods;

//import fileUtils.ReusableMethods;
//
//import fileUtils.payLoad;

import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;

public class HandlingStaticJsonPayLoad {

	@Test
	public void addBook() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().header("Content-Type","application/json")
		.body(GenerateStringFromResource("src/fileUtils/libraryAPIPayloadFile.json"))
		.when().post("Library/Addbook.php")
		.then().log().body().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		Assert.assertNotNull(id);
	}
	public static String GenerateStringFromResource(String path) throws IOException {
		//To convert Content of the file to String => Need to convert file to Byte -> Byte data to String  (File->Byte->String)
	    return new String(Files.readAllBytes(Paths.get(path)));

	}
	
}


