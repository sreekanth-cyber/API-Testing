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

public class TrendingHashTags extends base{

	Properties prop;

	@Test
	public void DisplayHashtag() throws IOException
	{

		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI="https://api.twitter.com/1.1/trends";
		Response res=given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).	
		when().
		get("/available.json").then().assertThat().statusCode(200).contentType(ContentType.JSON).
		extract().response();
		
		String response=res.asString();
		//System.out.println(response);
		JsonPath js=new JsonPath(response);
		int count=js.get("size()");
		for(int i=0;i<count;i++)
		{
			String country=js.get("["+i+"].country").toString();
			if(country.equalsIgnoreCase(prop.getProperty("location")))
			{
				String id=js.get("["+i+"].parentid").toString();
				Response res1=given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
						param("id",id).
						when().
						get("/place.json").then().assertThat().statusCode(200).contentType(ContentType.JSON).
						extract().response();
						
						String response1=res1.asString();
						//System.out.println(response1);
						JsonPath js1=new JsonPath(response1);
						int count1=js1.get("["+0+"].trends.size()");
						for(int j=0;j<count1;j++)
						{
							String Hashtag = js1.getString("["+0+"].trends["+j+"].name");
							String actual = js1.getString("["+0+"].trends["+j+"]").toString();
							if(Hashtag.charAt(0)=='#' && j<=5)
							{
								System.out.println(actual);
								log.info(actual);
							}
						}
						break;
			}
		}
	}
}
