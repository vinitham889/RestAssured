package simpleBook;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class SimpleBookTest {
	public static String baseURL = "https://simple-books-api.glitch.me";
	public String token;
	public String id;
	
	@Test(priority =1)
	public void authenticate() {
		
	
	RestAssured.baseURI=baseURL;
	String requestbody = "{\n"
			+"   \"clientName\": \"vini91\",\r\n"
			+ " \"clientEmail\":\"vini1924@example.com\"\r\n"
			+ "}";
	Response res = given()
			.header("Content-Type","application/json")
			.body(requestbody)
			
			.when()
			.post("/api-clients/")
			
			.then()
			.assertThat().statusCode(201).extract().response();
	String jsonresponse = res.asString();
	System.out.println(jsonresponse);
	JsonPath jp = new JsonPath(jsonresponse);
	token = jp.get("accessToken");
	
	
	
	}
	@Test(priority =2)
	public void submitanOrder()
	{
		RestAssured.baseURI=baseURL;
		String requestBody = "{\r\n"
				+ "  \"bookId\": 1,\r\n"
				+ "  \"customerName\": \"Johnhh\"\r\n"
				+ "}";
		Response res = given()
				.header("Content-Type","application/json")
				.header("Authorization","Bearer "+token)
				.body(requestBody)
				.when()
				.post("/orders")
				
				.then()
				.assertThat().statusCode(201).extract().response();
		String jsonresponse = res.asString();
		System.out.println(jsonresponse);
		JsonPath jp = new JsonPath(jsonresponse);
		id = jp.get("orderId");
		System.out.println(id);
		
		

	}
	@Test(priority =3)
	public void getAllOrder() {
		RestAssured.baseURI=baseURL;
		Response res = given()
				.header("Content-Type","application/json")
				.header("Authorization","Bearer "+token)
				.when()
				.get("/orders")
				.then()
				.assertThat().statusCode(200).extract().response();
		}
	@Test(priority =4)
	public void updateAnOrder()
	{
		RestAssured.baseURI=baseURL;
		String requestBody = "{\n"
				+" \"customerName\":\"Vini\"\r\n"
				+"}";
		Response res = given()
				.header("Content-Type","application/json")
				.header("Authorization","Bearer "+token)
				.body(requestBody)
				.when()
				.patch("/orders/"+id)
				.then()
				.assertThat().statusCode(204).extract().response();
		

	}
	@Test(priority =5)
	public void delete()
	{
		RestAssured.baseURI=baseURL;
		given()
		.header("Content-Type","application/json")
		.header("Authorization","Bearer "+token)
		.when()
		.delete("/orders/"+id)
		.then()
		.assertThat().statusCode(204);
		

	}
	
			
	
	
	

}
