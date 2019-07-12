package cyberTek;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.app.utilities.ConfigurationReader;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ApiDay3_Gson {
 
	
	
	@Test
	public void testWithJsonToHashMap() {
		Response response=given().accept(ContentType.JSON)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/120");
		Map<String, String> map= response.as(HashMap.class);//as method is used to convert the files from java to json and json to java.
	
		System.out.println(map.keySet());
		System.out.println(map.values());
		assertEquals(map.get("employee_id"), 120);
		assertEquals(map.get("job_id"), "AC_MGR");
	}

	@Test
	public void convertJsonToListOfMaps() {
		Response response=given().accept(ContentType.JSON)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/departments");
		
		//convert response that contains departyment info into list of maps.
		//we can use List<Map<String,String>>
		
		//List<Map<String,String>> listOfMaps= response.as(ArrayList.class);
		List<Map> listOfMaps= response.jsonPath().getList("items", Map.class);
		System.out.println(listOfMaps.get(0));
			
	//assert that first department name is Administration

	assertEquals(listOfMaps.get(0).get("department_name"), "Administration");
	
	
	
	}



} 
