import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTest {

    public static final String BASE_URI = "https://reqres.in/";
    public static final String GET_USERS_URI = BASE_URI + "api/users?page=2";
    public static final String CREATE_USERS_URI = BASE_URI + "api/users";
    public static final String UPDATE_USERS_URI = BASE_URI + "api/users/1";
    public static final String DELETE_USERS_URI = BASE_URI + "api/users/1";

    @Test
    public void getListUsers() {
        RestAssured.get(GET_USERS_URI)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createUser() {
        User oleksii = new User("Oleksii", "Manual QA");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(oleksii)
                .when()
                .post(CREATE_USERS_URI)
                .then()
                .statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    public void updateUser() {
        User oleksii = new User("Oleksii", "Automation QA");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(oleksii)
                .when()
                .put(UPDATE_USERS_URI)
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void userDelete() {
        RestAssured.delete(DELETE_USERS_URI)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }

}
