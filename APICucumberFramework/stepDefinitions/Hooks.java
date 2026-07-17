package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	StepDefinition stepDef = new StepDefinition();
	@Before("@DeletePlaceAPI")
	public void beforeScenario() throws IOException{
		//Execute this code only when placeId is null.
		//Write an code that will give placeId
		
		if (StepDefinition.placeId == null) {
			stepDef.add_place_payload("Kiran", "French", "Europe");
			stepDef.user_calls_with_post_http_request("addPlaceAPI", "POST");
			stepDef.verify_place_Id_created_maps_to_using("Kiran", "getPlaceAPI");
		}
	}
	
	@After()
	public void loggingSuccessfulResponseOnConsole() {
		
		if(stepDef.response != null) {
			stepDef.response.then().log().body();
		}
		
	}

}
