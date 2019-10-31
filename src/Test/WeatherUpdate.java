package Test;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import Files.base;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class WeatherUpdate extends base{

	Properties prop;

	@Test
	public void weatherTweet() throws IOException
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/search";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		param("q","weather,Bangalore").
		when().
		get("/tweets.json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		//System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		int count = js.get("statuses.size()");
		//System.out.println(count);
		
		String resp;
		for(int i=0;i<count;i++)
		{
			String place = js.get("statuses["+i+"].user.location").toString();
			if(place.contains("Bengaluru")||place.contains("Bangalore"))
			{
				resp = js.get("statuses["+i+"]").toString();
				System.out.println(resp);
				log.info(resp);
			}
		}
	}
}
