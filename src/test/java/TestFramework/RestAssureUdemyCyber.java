package TestFramework;


import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;


import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import com.aventstack.extentreports.gherkin.model.Given;

import io.restassured.response.Response;

public class RestAssureUdemyCyber {

	@Test
	public  void ilkDeneme() {
		
		HashMap<String, Object>  map = new HashMap<>();
		map.put("name", "onur");
		map.put("isbn", "badem");
		map.put("aisle", "melis");
		map.put("author", "oso");
		

		RestAssured.baseURI="http://216.10.245.166";
		Response resp=given().
				header("Content-Type","application/json").
		body(map).
		when().
		post("/Library/Addbook.php").
		then().assertThat().statusCode(200).
		extract().response();
		
	JsonPath json=resp.jsonPath();
	
	//System.out.println(resp.asString());
	//System.out.println(json.prettyPrint());
	
	System.out.println(json.getString("name"));
	System.out.println(json.getString("isbn"));
	
	
	
	}

}
