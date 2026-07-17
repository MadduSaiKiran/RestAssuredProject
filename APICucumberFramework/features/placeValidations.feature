Feature: Validating place API

@AddPlaceAPI @Smoke
Scenario Outline: Verify place added successfully using AddPlace API
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "addPlaceAPI" with "POST" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And Verify place_id created maps to "<name>" using "getPlaceAPI"
Examples:
	| name  | language | address   |
	| Ram   | English  | Hyderabad |
#	| Raju  | Telugu   | Bengaluru |

@DeletePlaceAPI @Smoke
Scenario: Verify if Delete Place API is working as expected or not.
	Given Delete Place Payload
	When user calls "deletePlaceAPI" with "DELETE" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	