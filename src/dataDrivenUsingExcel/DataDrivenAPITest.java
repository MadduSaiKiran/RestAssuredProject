package dataDrivenUsingExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import fileUtils.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class DataDrivenAPITest {
	
	ExcelUtilDataDriven excelUtilDataDriven = new ExcelUtilDataDriven();
	
	@Test
	public void addBook() throws IOException {
		
		ArrayList<String> data = excelUtilDataDriven.getData("AddRestAssuredBook","addBookAPIData");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("name", data.get(1));
		hm.put("isbn", data.get(2));
		hm.put("aisle", data.get(3));
		hm.put("author", data.get(4));

		
		/*	HashMap<String, Object>  map2 = new HashMap<>();
		map.put("lat", "12");
		map.put("lng", "34");
		map.put("location", map2);*/
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response resp = 
				given()
					.log().body()
					.header("Content-Type", "application/json")
					.body(hm)
				.when()
					.post("/Library/Addbook.php")
				.then()
					.log().body()
					.assertThat()
					.statusCode(200)
				.extract().response();
		JsonPath js = ReUsableMethods.rawToJson(resp);
		String id = js.get("ID");
		System.out.println("ID - "+id);

	}

}
