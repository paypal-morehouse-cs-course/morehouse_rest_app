package org.morehouse.app;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class JUnitTestTodos {
	@BeforeClass

    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9090;
    }

	@Test
	public void testGetAllTodos() {
		get("/morehouse/restapp/todos")
		.then()
		.assertThat()
		.body("$", hasSize(greaterThan(0)));
	}
}
