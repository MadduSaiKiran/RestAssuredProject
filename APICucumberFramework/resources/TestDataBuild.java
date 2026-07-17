package resources;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class TestDataBuild {
	public AddPlace addPlacePayload(String name, String language, String address) {
		AddPlace addPlace = new AddPlace();

		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		addPlace.setLocation(location);

		addPlace.setAccuracy(50);
		addPlace.setName(name);
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress(address);

		ArrayList<String> types = new ArrayList<String>(List.of("shoe park", "shop")); 
		addPlace.setTypes(types);

		addPlace.setWebsite("http://google.com");
		addPlace.setLanguage(language);
		return addPlace;
	}
	public String deletePlacePayload(String placeId) {
		return "{\r\n"
				+ "    \"place_id\":\""+placeId+"\"\r\n"
				+ "}\r\n"
				+ "";
	}
}
