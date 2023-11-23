package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;

@Epic("Пост")
@Feature("Создание поста")
public class CreatePostTest extends BaseTest {
    private String json;

    @BeforeMethod
    @Parameters({"resource"})
    public void beforeMethod(String resource) throws JsonProcessingException {
        json = steps.convertPojoToJson(resource);
    }

    @AfterMethod
    public void afterMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_POST_LAST);
    }

    @Test
    @Story("POST-запрос для создания поста с указанием значений полей title, content, status")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void createPostTest(String resource) throws SQLException, JsonProcessingException {
        requestSpecification
                .body(json)
            .when()
                .post(POSTS_ENDPOINT)
            .then()
                .statusCode(201);

        ArrayList listOfDataFromDB = steps.getDataFromRowDB(resource, SQL_REQUEST_SELECT_POST_LAST);

        softAssert.assertEquals(steps.convertJsonToPostModel(json).getContent(), listOfDataFromDB.get(1), "Значение поля content в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals(steps.convertJsonToPostModel(json).getTitle(), listOfDataFromDB.get(2), "Значение поля title в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals(steps.convertJsonToPostModel(json).getStatus(), listOfDataFromDB.get(3), "Значение поля status в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("post", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению post. Был создан не пост!");
        softAssert.assertAll();
    }
}
