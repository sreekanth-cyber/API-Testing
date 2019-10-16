package Files;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ProvideHashTag {

	Properties prop;

	
	public String provideHashtag() throws IOException
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/trends/place.json";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		param("id","1").
		when().
		get().
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		//System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		int count = js.get("["+0+"].trends.size()");
		//System.out.println(count);
		String temp;
		String name;
		for(int i=0;i<count;i++)
		{
			temp = js.get("["+0+"].trends["+i+"].name").toString();
			name = temp.replace("#","");
			return name;
		}
		return null;
	}
}
