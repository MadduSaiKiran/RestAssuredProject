package practiceComplexJsonParse;

import org.testng.Assert;

import fileUtils.payload;
import io.restassured.path.json.JsonPath;

/*
1. Print No of courses returned by API
2. Print Purchase Amount
3. Print Title of the first course
4. Print All course titles and their respective Prices
5. Print no of copies sold by RPA Course
6. Verify if Sum of all Course prices matches with Purchase Amount
*/

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.coursePriceMockResponse());
		
		int courseCount = js.getInt("courses.size()"); // size() is applicable for array elements only
		System.out.println("1. No of courses returned by API - "+courseCount);
		
		int coursePurchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println("2. Course Purchase Amount - "+coursePurchaseAmt);
		
		String courseFirstTitle = js.getString("courses[0].title");
		System.out.println("3. Title of the first course - "+courseFirstTitle);
		
		System.out.println("4. Print All course titles and their respective Prices");
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.getString("courses["+i+"].title"); // we can also use get instead of getString, get is generic for string, int..
			int coursePrice= js.getInt("courses["+i+"].price");
			System.out.println("Title - "+courseTitle +" | Price - "+coursePrice);
		}
		
		System.out.println("5. Print no of copies sold by RPA Course");
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.getString("courses["+i+"].title"); // we can also use get instead of getString, get is generic for string, int..
			if(courseTitle.equals("RPA")) {
				int courseCopies= js.getInt("courses["+i+"].copies");
				System.out.println("Title - "+courseTitle +" | Copies - "+courseCopies);
				break;
			}	
		}
		
		System.out.println("6. Verify if Sum of all Course prices matches with Purchase Amount");
		int actaulCourseSum=0;
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.getString("courses["+i+"].title"); // we can also use get instead of getString, get is generic for string, int..
			int coursePrice= js.getInt("courses["+i+"].price");
			int courseCopies= js.getInt("courses["+i+"].copies");
			System.out.println("Title - "+courseTitle+" | price*copies - "+(coursePrice*courseCopies));
			actaulCourseSum = actaulCourseSum + (coursePrice*courseCopies);
		}
		System.out.println("Total - "+actaulCourseSum);
		Assert.assertEquals(actaulCourseSum, coursePurchaseAmt);
	
	
	
	}

}
