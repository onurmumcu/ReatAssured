package cyberTek;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.ws.Response;

import org.apache.poi.openxml4j.opc.internal.ContentType;
//import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class APIDay1HrGetReq {
 
	
	
	@Test
  public void simpleGet() {
	when().get("http://34.223.219.142:1212/ords/hr/employees")
	.then().statusCode(200);
	
	}
	@Test
	 public void printResponse() {
		when()
		.get("http://34.223.219.142:1212/ords/hr/countries");
		//.andReturn().body().prettyPrint();
	}

	@Test
	 public void getWithHeaders() {
		
		with().accept(ContentType.JSON)
		.when()
		.get("http://34.223.219.142:1212/ords/hr/countries/US")
		.then().statusCode(200);
			
	}

	@Test
	 public void negativeGet() {
//		
//		with().accept(ContentType.JSON)
//		.when()
//		.get("http://34.223.219.142:1212/ords/hr/employees/1234")
//		.then().statusCode(404);
//	
	Response response=when().get("http://34.223.219.142:1212/ords/hr/employees/1234");
	assertEquals(response.statusCode(), 404);
	assertTrue(response.asString().contains("Not Found"));
	response.prettyPrint();
	}

	@Test
	 public void verifyContentType() {
		given().accept(ContentType.JSON)
		.when()
				
		.get("http://34.223.219.142:1212/ords/hr/employees/100")
				
		.then().assertThat().statusCode(200)
		.and().contentType(ContentType.JSON);
	
		}

	@Test
	 public void verifyFirstName() throws URISyntaxException {
		URI uri=new URI("http://34.223.219.142:1212/ords/hr/employees/100");
		
		given().accept(ContentType.JSON)
		.when().get(uri)
		.then().assertThat().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("first_name", equalTo("Steven"))//this was Mathers.equalTo then We imported static Matchers so we can call directly.
		.and().assertThat().body("employee_id", equalTo(100));

	}

	
	
	
	
	
	
	
}
