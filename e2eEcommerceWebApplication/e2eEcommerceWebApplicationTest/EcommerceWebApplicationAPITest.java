package e2eEcommerceWebApplicationTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import POJOClasses.AddProductResponse;
import POJOClasses.CreateOrderRequest;
import POJOClasses.CreateOrderResponse;
import POJOClasses.DeleteProductResponse;
import POJOClasses.LoginRequest;
import POJOClasses.LoginResponse;
import POJOClasses.Orders;
import POJOClasses.ViewOrderDetailsResponse;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class EcommerceWebApplicationAPITest {
	
	@Test
	public void e2eEcommerceWebApplicationAPITest() {
		ResponseSpecification responseBaseSpec = new ResponseSpecBuilder().log(LogDetail.BODY).build();
		//Login
		System.out.println("----------------Login-----------------------");
		
		RequestSpecification request = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.build();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("automationtester@test.com");
		loginRequest.setUserPassword("Test@123");
		
		RequestSpecification requestLoginSpec = given().spec(request).body(loginRequest);
	
		LoginResponse loginResponse =requestLoginSpec.when().post("/api/ecom/auth/login")
		.then().spec(responseBaseSpec).extract().response().as(LoginResponse.class);
		
		String loginToken = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		//Add Product
		System.out.println("----------------Add Product-----------------------");
		RequestSpecification addProductBaseRequest = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginToken)
				.build();
		
		RequestSpecification addProductReqSpec = given().spec(addProductBaseRequest)
		.param("productName", "Rolex - Watch")
		.param("productAddedBy", userId)
		.param("productCategory", "Fashion")
		.param("productSubCategory", "Stainless Steel")
		.param("productPrice","11499")
		.param("productDescription", "Rolex - Submariner 40mm | stainless steel | Men | Black")
		.param("productFor", "Men")
		.multiPart("productImage", new File("./Images/productImage.png"));
		
		AddProductResponse addProductResponse = addProductReqSpec.when().post("/api/ecom/product/add-product")
		.then().spec(responseBaseSpec).extract().response().as(AddProductResponse.class);
		
		String productId = addProductResponse.getProductId();
		
		//Create Order
		System.out.println("----------------Create Order-----------------------");
		RequestSpecification createOrderBaseRequest = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginToken)
				.setContentType(ContentType.JSON)
				.build();
		Orders orders = new Orders();
		orders.setCountry("India");
		orders.setProductOrderedId(productId);
		
		ArrayList<Orders> ordersList = new ArrayList<Orders>();
		ordersList.add(orders);
		
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setOrders(ordersList);
		
		RequestSpecification createOrderReqSpec = given().spec(createOrderBaseRequest).body(createOrderRequest);
		
		CreateOrderResponse createOrderResponse = createOrderReqSpec.when().post("/api/ecom/order/create-order")
		.then().spec(responseBaseSpec).assertThat().statusCode(201)
		.extract().response().as(CreateOrderResponse.class);
		
		Assert.assertEquals(createOrderResponse.getMessage(),"Order Placed Successfully");
		String order_Id = createOrderResponse.getOrders().get(0);
		
		//View Order Details
		System.out.println("----------------View Order Details-----------------------");
		RequestSpecification viewOrderBaseRequest = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginToken)
				.addQueryParam("id", order_Id) 
				.build();
		ViewOrderDetailsResponse viewOrderDetailsResponse = given().spec(viewOrderBaseRequest)
				.when().get("/api/ecom/order/get-orders-details")
				.then().spec(responseBaseSpec).assertThat().statusCode(200)
				.extract().response().as(ViewOrderDetailsResponse.class);
		
		Assert.assertEquals(viewOrderDetailsResponse.getMessage(), "Orders fetched for customer Successfully");
		
		//Delete Product
		System.out.println("----------------Delete Product-----------------------");
		RequestSpecification deleteProductBaseRequest = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginToken)
				.addPathParam("productId_pathParam", productId) 
				.build();
		DeleteProductResponse deleteProductResponse = given().spec(deleteProductBaseRequest)
				.when().delete("/api/ecom/product/delete-product/{productId_pathParam}")
				.then().spec(responseBaseSpec).assertThat().statusCode(200)
				.extract().response().as(DeleteProductResponse.class);
		
		Assert.assertEquals(deleteProductResponse.getMessage(),"Product Deleted Successfully");
	}

}
