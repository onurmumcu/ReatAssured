package cyberTek;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.app.beans.Country;
import com.app.beans.CountryResponse;
import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiDay5PostThenGet {
  
	
	
	/*Given Content type and Accept type
	is Json When I 	post a new
	Employee with"1234" id Then Status code is 201
	And Response Json should contain Employee
	info When i send a GET request with"1234" id Then
	status code is 200 And employee JSON Response\
	Data should match the posted JSON data
	(job_id and departent_id should be in the database.)
	
	URL=
	
	http://34.223.219.142:1212/ords/hr/employees/
	*/
	
	@Test
  public void postemployeeThenGetEmployee() {

String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/employees/";
int randomId=new Random().nextInt(99999);		

Map reqEmployee=new HashMap();
reqEmployee.put("employee_id",randomId);
reqEmployee.put("first_name","PTER");
reqEmployee.put("last_name","KOLP");
reqEmployee.put("email","EM"+randomId);
reqEmployee.put("phone_number", "515.000.4567");
reqEmployee.put( "hire_date", "2013-06-17T04:00:00Z");
reqEmployee.put("job_id", "AD_PRES");
reqEmployee.put("salary", 39000);
reqEmployee.put("commission_pct", null);
reqEmployee.put("manager_id", null);
reqEmployee.put("manager_id", null);
reqEmployee.put("department_id", 90);

//given content type and accept type os Json
Response response=given().accept(ContentType.JSON)
					.and().contentType(ContentType.JSON)
					.and().body(reqEmployee)
					.when().post(url);
	
	
	assertEquals(response.statusCode(), 201);
	//now I will compare two maps one is coming from my sent JsOn, second one comes from Json I received
	Map postReqEmployee=response.body().as(Map.class);
	for(Object key : reqEmployee.keySet()) {
		System.out.println(postReqEmployee.get(key)+"<  >"+reqEmployee.get(key));//I wanted to see what is there
		assertEquals(postReqEmployee.get(key), reqEmployee.get(key));
		}
	
	//now I will send a get request to see if my employee is there 
	response = given().accept(ContentType.JSON)
			   .when().get(url+randomId);
	
	assertEquals(response.statusCode(),200);
	//deserialization taking from Json body to map
	Map getResMap = response.body().as(Map.class);
	 //looping in two different maps to compare them
	
	 for(Object key:reqEmployee.keySet()) {
		System.out.println(key + ": " +reqEmployee.get(key) + "<>" +getResMap.get(key)); //I wanted to see what I am comparing
		assertEquals(getResMap.get(key),reqEmployee.get(key));
	}
		
	}



}
