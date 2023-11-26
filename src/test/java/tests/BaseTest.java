package tests;

import helpers.PropertyProvider;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;
import actions.DatabaseActions;
import actions.PojoActions;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final String COMMENTS_ENDPOINT = "/index.php?rest_route=/wp/v2/comments/";
    final String POSTS_ENDPOINT = "/index.php?rest_route=/wp/v2/posts/";
    PojoActions pojoActions = new PojoActions();
    DatabaseActions databaseActions = new DatabaseActions();
    SoftAssert softAssert = new SoftAssert();

    protected final RequestSpecification requestSpecification = given()
            .auth()
            .preemptive()
            .basic(PropertyProvider.getInstance().getProperty("wordpress.login"), PropertyProvider.getInstance().getProperty("wordpress.password"))
            .baseUri(PropertyProvider.getInstance().getProperty("base.url"))
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
}
