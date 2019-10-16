package Files;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Payload {
	
	Properties prop;

	public String getUsers() throws IOException
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/users/search.json";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		param("q","Barack Obama").
		when().
		get().
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		//System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		int count = js.get("size()");
		//System.out.println(count);
		
		for(int i=0;i<count;i++)
		{
			String User = js.get("["+i+"].screen_name");
			//System.out.println(User);
			if(User.equalsIgnoreCase(prop.getProperty("User")))
			{
				return User;
			}
		}
		return null;
	}
	
	public String getFollower() throws IOException
	{
		String str1 = java.util.Calendar.getInstance().getTime().toString();  
		String date = str1.replace(" IST "," ");
		
		prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\sree\\MyAPIProject\\src\\Files\\data.properties");
		prop.load(fis);
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/followers/list.json";
		Response res = given().auth().oauth(prop.getProperty("ConsumerKey"),prop.getProperty("ConsumerSecret"),prop.getProperty("Token"),prop.getProperty("TokenSecret")).
		when().
		get().
		then().extract().response();
		
		String response = res.asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		int count = js.get("users.size()");
		System.out.println(count);
		
		for(int i=0;i<count;i++)
		{
			String Id = js.get("users["+i+"].Id").toString();
			
			String str2 = js.get("users["+i+"].created_at").toString();
			String created = str2.replace(" +0000 "," ");
			
			System.out.println(created);
			
			if(created.contains(date))
			{
				return Id;
			}
		}
		return null;
	}
}
