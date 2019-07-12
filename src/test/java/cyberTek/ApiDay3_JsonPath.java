package cyberTek;


import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;
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

public class ApiDay3_JsonPath {
	
	/*
	 * Given Accept type is JSON 
	 * When I send a GET request to REST URL:
	 * http://34.223.219.142:1212/ords/hr/regions 
	 * Then status code is 200 
	 * And Response content should be json 
	 * And 4 regions should be returned
	 * And Americas is one of the region names
	 * And 
	 */
	//validation of multiple values in response json	
	@Test
  public void testItemsCountFromReposnseBody() {
				
		given().accept(ContentType.JSON)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/regions")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("items.region_id", hasSize(4))
		.and().assertThat().body("items.region_name", hasItems("Americas", "Asia"))//hasItems take varArgs multi
		.and().assertThat().body("items.region_name", hasItem("Americas"));//here hasItem takes only one
	
		
		
		
	}


	/*
	 * Given Accept type is Json
	 * And Params are limit 100
	 * When I send get request to 
	 * http://34.223.219.142:1212/ords/hr/employee 
	 * Then status code is 200 
	 * And Response content should be json 
	 * And 100 employees data should be in json reponse body
	 */
	@Test
	public void testWithQueryParamsAndList() {
		
		given().accept(ContentType.JSON)
		.and().params("limit", 100)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees")
		.then().statusCode(200)//i can assert it like this too
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("items.employee_id", hasSize(100));		
	}

	
	
	/*
	 * Given Accept type is Json
	 * And Params are limit=100
	 * And path param is 110
	 * When I send get request to 
	 * http://34.223.219.142:1212/ords/hr/employee 
	 * Then status code is 200 
	 * And Response content should be json 
	 * And following data should be returned:
	 * "employee_id": 110,
	    "first_name": "John",
	    "last_name": "Chen",
	    "email": "JCHEN",
	 */
	
	
	@Test
	public void testWithPathParamAndList() {
		
		given().accept(ContentType.JSON)
		.and().params("limit", 100)
		.and().pathParams("employee_id", 110)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("employee_id", equalTo(110),
				"first_name", equalTo("John"),
				"last_name", equalTo("Chen"),
				"email",equalTo("JCHEN"));
				
	}

	
	@Test
	public void testWithJSonPath() {
		
		Map<String,Integer> rParamMap= new HashMap<>();
		rParamMap.put("limit", 100);	
		Response response=	given().accept(ContentType.JSON)//header
							.and().params(rParamMap)//query param
							.and().pathParams("employee_id", 177)//path param
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}");
		
		JsonPath json=response.jsonPath();//get json body and assign it to json Path object
		
		System.out.println(json.getInt("employee_id"));
		System.out.println(json.getString("last_name"));
		System.out.println(json.getInt("salary"));
		System.out.println(json.getString("job_id"));
		System.out.println(json.getString("links.href"));//this is to print all href objects in the links 
		System.out.println(json.getString("links[0].href"));//this is to print the first href object in the links 
		
		//assign all hrefs into a list of strings
		List<String> hrefs= json.getList("links.href");
		System.out.println(hrefs);
		
				
	}

	@Test
	public void testWithJSonPathWithList() {
		
		Map<String,Integer> rParamMap= new HashMap<>();
		rParamMap.put("limit", 100);	
		Response response=	given().accept(ContentType.JSON)//header
							.and().params(rParamMap)//query param
						
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees");
		assertEquals(response.statusCode(), 200);//checking status code
		
		JsonPath json=response.jsonPath();//get json body and assign it to json Path object
//		JsonPath json=new JsonPath(file.json);//here we give it a json file
//		JsonPath json=new JsonPath(response.asString());
	
		
		//get all employee ids into an arraylist
		List<Integer> empIds=json.getList("items.employee_id");
		System.out.println(empIds);
		assertEquals(empIds.size(), 100);
		//get all email and assign them to an array list
		List<Integer> empEmails=json.getList("items.email");
		assertEquals(empEmails.size(), 100);
		System.out.println(empEmails);
		
		//get all employee ids that are greater than 150
		
		List<Integer> empIdList=json.getList("items.findAll{it.employee_id>150}.employee_id");//give me th employee ids of employes whose ids are more than 150 in items and put them in a list
		System.out.println(empIdList);
		List<Integer> empSalaryList=json.getList("items.findAll{it.salary>10000}.salary");//give me the salaray of employes whose salaries are more than 10000 in items and put them in a list
		System.out.println(empSalaryList);
		System.out.println(empSalaryList.size());
		List<Integer> empSalaryLastNameList=json.getList("items.findAll{it.salary>10000}.last_name");//give me the lastname of employes whose salaries are more than 10000 in items and put them in a list
		System.out.println(empSalaryLastNameList);
		System.out.println(empSalaryLastNameList.size());
	
		
		
			
	}	
	
	
	
	
	
	
	
	
	
	
}
