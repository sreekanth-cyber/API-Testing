package Test;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WelcomeMessage {

	Properties prop;

	@Test
	public void newMessage() throws IOException
	{
		Payload p =new Payload();
		
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/direct_messages";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		header("Content-Type","application/json").
		body("{\r\n" + 
				"  \"welcome_message\" : {\r\n" + 
				"    \"name\": \"simple_welcome-message 01\",\r\n" + 
				"    \"message_data\": {\r\n" + 
				"      \"text\": \"Welcome!\",\"id\" : \""+p.getFollower()+"\"\"\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}").
		when().
		post("/welcome_messages/new.json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		System.out.println(response);
		
	}
}
