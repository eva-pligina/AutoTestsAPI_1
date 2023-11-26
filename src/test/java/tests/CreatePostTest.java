package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import model.PostModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

@Epic("Пост")
@Feature("Создание поста")
public class CreatePostTest extends BaseTest {
    private String json;

    @BeforeMethod
    @Parameters({"resource"})
    public void beforeMethod(String resource) throws JsonProcessingException {
        json = pojoActions.convertPojoToJson(resource);
    }

    @AfterMethod
    @Parameters({"resource"})
    public void afterMethod(String resource) {
        databaseActions.deleteLastTableRowDB(resource);
    }

    @Test
    @Story("POST-запрос для создания поста с указанием значений полей title, content, status")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void createPostTest(String resource) throws JsonProcessingException {
        requestSpecification
                .body(json)
            .when()
                .post(POSTS_ENDPOINT)
            .then()
                .statusCode(201);

        ArrayList listOfDataFromDB = databaseActions.getDataFromLastRowDB(resource);
        PostModel postModel = pojoActions.convertJsonToPostModel(json);

        softAssert.assertEquals(postModel.getContent(), listOfDataFromDB.get(1), "Значение поля content в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals(postModel.getTitle(), listOfDataFromDB.get(2), "Значение поля title в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals(postModel.getStatus(), listOfDataFromDB.get(3), "Значение поля status в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("post", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению post. Был создан не пост!");
        softAssert.assertAll();
    }
}
