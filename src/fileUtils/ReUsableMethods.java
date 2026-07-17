package fileUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReUsableMethods {

	
	public static JsonPath rawToJson(String response)
	{
		JsonPath js1 =new JsonPath(response);
		return js1;
	}
	
	public static JsonPath rawToJson(Response response)
	{
		String responseStr = response.asString();
		JsonPath js1 =new JsonPath(responseStr);
		return js1;
	}
}
