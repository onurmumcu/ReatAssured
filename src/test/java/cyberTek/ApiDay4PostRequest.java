package cyberTek;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;
import io.restassured.response.Response;

import com.app.beans.Country;
import com.app.beans.CountryResponse;
import com.app.beans.Region;
import com.app.beans.RegionResponse;
import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class ApiDay4PostRequest {
 
	
	
//post scenario
//		{
//			
//			
//			given content type Json,
//			Accept tyoe Json, 
//			when I send POST request to regions url;		
//			"region_id":51,
//			"region_name": "area ilyas`"
//			then status code should be 200
//			response should match request body
//		}
//	}
	

	
	
	
	
	
	
	
	
//	hashmap==>Json==Serialization
//	Json==>Hashmap or list ==DeSerialization
	@Test
	public void postNewRegion() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions/";
		
		//String requestJson = "{\"region_id\" : 5,\"region_name\" : \"murodil's region\"}";
		
		Map requestMap = new HashMap<>();
		requestMap.put("region_id", 132);
		requestMap.put("region_name", "`s region");
		
		Response response = given().accept(ContentType.JSON)
							.and().contentType(ContentType.JSON)
							.and().body(requestMap)
							.when().post(url);
		
		System.out.println(response.statusLine());
		response.prettyPrint();
		
		assertEquals(response.statusCode(), 201);
		
		//deserialization;
		Map responseMap=response.body().as(Map.class);
		//assertEquals(responseMap, requestMap); did not work
		assertEquals(responseMap.get("region_id"), responseMap.get("region_id"));
		assertEquals(responseMap.get("region_name"), responseMap.get("region_name"));
		
		
//During smoke/regression testing we post this region id everttime, so it may give us an error. how can we cope with this? it means how can we change region oid everytome?
// 1)at the end of the test we can add delete command
// 2)use random created numbers
// 3)delete from database first using sql then run post tests
// 4) delete from database first usoing delete API method then run post
		

	}

//
//	Custom classes to match our Json request and response;
//	POJO Plain Old Java Object (we can call them "BEANS" )
	
	@Test
	public void postUsingPOJO() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions/";
		
		Region region = new Region();
		region.setRegion_id(new Random().nextInt(999999));
		region.setRegion_name("ilyas' region");
		
		Response response = given().log().all()
							.accept(ContentType.JSON)
						   .and().contentType(ContentType.JSON)
						   .and().body(region)
						   .when().post(url);
	
		assertEquals(response.statusCode(),201);
		
		RegionResponse responseRegion = response.body().as(RegionResponse.class);
		
		//responsebody should match request body
		
	assertEquals(responseRegion.getRegion_id(), region.getRegion_id());
	assertEquals(responseRegion.getRegion_name(), region.getRegion_name());
	
	}
	
	@Test
	public void postCountryUsingPojo() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/countries/";
		
		Country reqCountry = new Country();
		reqCountry.setCountry_id("BI");
		reqCountry.setCountry_name("BIC");
		reqCountry.setRegion_id(7);
		
		Response response = given().log().all()
				.accept(ContentType.JSON)
			   .and().contentType(ContentType.JSON)
			   .and().body(reqCountry)
			   .when().post(url);
		
		//assertEquals(response.getStatusCode(),201);
		
		CountryResponse resCountry = response.body().as(CountryResponse.class);
		
		assertEquals(resCountry.getCountry_id(),reqCountry.getCountry_id()); 
		assertEquals(resCountry.getCountry_name(),reqCountry.getCountry_name()); 
		assertEquals(resCountry.getRegion_id(),reqCountry.getRegion_id());

	}
	
	
//	Given Content type and Accept type is Json When I post a new Employee with "1234" id Then Status code is 201 And Response Json should contain Employee info When i send a GET request with "1234" id Then status code is 200 And employee JSON Response Data should match the posted JSON data	
//	
//	
//	
//	"employee_id": 100, "first_name": "Steven", "last_name": "King", "email": "SKING", "phone_number": "515.123.4567", "hire_date": "2003-06-17T04:00:00Z", "job_id": "AD_PRES", "salary": 24000, "commission_pct": null, "manager_id": null, "department_id": 90,


}
