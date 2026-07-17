package libraryAPIsTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fileUtils.ReUsableMethods;
import fileUtils.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "booksData")
	public void addBook(String nameFromDataProvider, String isbnFromDataProvider, String aisleFromDataProvider, String authorFromDataProvider) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().header("Content-Type","application/json")
		.body(payload.addBook(nameFromDataProvider,isbnFromDataProvider,aisleFromDataProvider,authorFromDataProvider))
		.when().post("Library/Addbook.php")
		.then().log().body().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name = "booksData")
	public Object[][] getData(){
		//Array = collection of elements. i.e., array1[] = {1,2,3,4,5}
		//MultiDimentional Array = Collection of Arrays i.e., mulDimArray[][] = {array1,array2,array3}
		return new Object[][]{
			{"Programming in Java","bcd","0105","Sai Kiran"},
			{"Programming in Python","abc","1005","Sai"}
		};	
	}
}


