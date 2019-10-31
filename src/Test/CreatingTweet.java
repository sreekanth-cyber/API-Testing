package Test;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import Files.base;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreatingTweet extends base{

	Properties prop;

	@Test
	public void createTweet() throws IOException
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		queryParam("status","I am learning API testing using RestAssured Java #Qualitest").
		when().
		post("/update.json").
		then().assertThat().statusCode(200).and().extract().response();
		
		String response = res.asString();
		System.out.println(response);
		log.info(response);
	}
}
