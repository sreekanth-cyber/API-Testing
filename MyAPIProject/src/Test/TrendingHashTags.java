package Test;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import Files.ProvideHashTag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TrendingHashTags {

	Properties prop;

	@Test
	public void DisplayHashtag() throws IOException
	{
		ProvideHashTag p = new ProvideHashTag();
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/search/tweets.json";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		param("q",p.provideHashtag()).
		when().
		get().
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		//System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		int count = js.get("statuses.size()");
		//System.out.println(count);
		
		int c1=0,c2=0,c3=0,c4=0;String resp;
		
		for(int i=0;i<count;i++)
		{
			String place = js.get("statuses["+i+"].user.location").toString();
			if(place.contains("India"))
			{
				c1++;
				if(c1<5)
				{
					resp = js.get("statuses["+i+"]").toString();
					System.out.println(resp);
				}
			}
			if(place.contains("Israel"))
			{
				c2++;
				if(c2<5)
				{
					resp = js.get("statuses["+i+"]").toString();
					System.out.println(resp);
				}
			}
			if(place.contains("America"))
			{
				c3++;
				if(c3<5)
				{
					resp = js.get("statuses["+i+"]").toString();
					System.out.println(resp);
				}
			}
			if(place.contains("United Kingdom"))
			{
				c4++;
				if(c4<5)
				{
					resp = js.get("statuses["+i+"]").toString();
					System.out.println(resp);
				}
			}
		}
	}
}
