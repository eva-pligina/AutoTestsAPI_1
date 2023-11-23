package tests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;
import steps.Steps;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final String COMMENTS_ENDPOINT = "/index.php?rest_route=/wp/v2/comments/";
    final String POSTS_ENDPOINT = "/index.php?rest_route=/wp/v2/posts/";
    final String SQL_REQUEST_CREATE_POST = "INSERT INTO wp_posts (post_date, post_date_gmt, post_content, post_title, post_excerpt, to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered) VALUES (NOW(), NOW(), 'Описание', 'Пост', '', '', '', NOW(), NOW(), '');";
    final String SQL_REQUEST_CREATE_COMMENT = "INSERT INTO wp_comments (comment_author, comment_date, comment_date_gmt, comment_content) VALUES ('Eva.Pligina', NOW(), NOW(), 'Text');";
    final String SQL_REQUEST_DELETE_POST_LAST = "DELETE FROM wp_posts ORDER BY ID DESC LIMIT 1;";
    final String SQL_REQUEST_DELETE_COMMENT_LAST = "DELETE FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;";
    final String SQL_REQUEST_DELETE_POST_BY_ID = "DELETE FROM wp_posts WHERE ID = ";
    final String SQL_REQUEST_SELECT_POST_LAST = "SELECT * FROM wp_posts ORDER BY ID DESC LIMIT 1;";
    final String SQL_REQUEST_SELECT_COMMENT_LAST = "SELECT * FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;";
    final String SQL_REQUEST_SELECT_POST_BY_ID = "SELECT * FROM wp_posts WHERE ID = ";
    final String SQL_REQUEST_SELECT_COMMENT_BY_ID = "SELECT * FROM wp_comments WHERE comment_ID = ";
    Steps steps = new Steps();
    SoftAssert softAssert = new SoftAssert();

    protected final RequestSpecification requestSpecification = given()
            .auth()
            .preemptive()
            .basic("Eva.Pligina", "1234554321-Test-sdet-chinchil")
            .baseUri("http://localhost:8000")
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
}
