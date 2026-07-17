package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {
	public static RequestSpecification requestSpec;
	public RequestSpecification requestSpecification() throws IOException {
		//if we failed to specify this if and running multiple tests then, only last executed test result will be logged and initial logs will be override.
       // hence this will be triggered only once and make requestSpec as static so that it will shared across all test for single run.
		if(requestSpec == null) {  
			
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON)
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.build();
			return requestSpec;
		}
		return requestSpec;
	}
	
	public ResponseSpecification responseSpecification() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder()
				.log(LogDetail.BODY)
				.build();
		return responseSpec;
	}
	
	public String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("./APICucumberFramework/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	public String getJsonPath(Response response, String key) {
		JsonPath js= new JsonPath(response.asString());
		return js.get(key).toString();
		
	}
}
