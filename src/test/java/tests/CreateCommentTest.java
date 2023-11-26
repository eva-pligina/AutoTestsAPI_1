package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import model.CommentModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

@Epic("Комментарий")
@Feature("Создание комментария")
public class CreateCommentTest extends BaseTest {
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
    @Story("POST-запрос для создания комментария с указанием значений полей content, status, post")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void createCommentTest(String resource) throws JsonProcessingException {
        requestSpecification
                .body(json)
            .when()
                .post(COMMENTS_ENDPOINT)
            .then()
                .statusCode(201);

        ArrayList listOfDataFromDB = databaseActions.getDataFromLastRowDB(resource);
        CommentModel commentModel = pojoActions.convertJsonToCommentModel(json);

        softAssert.assertEquals(commentModel.getPost(), listOfDataFromDB.get(1), "Значение поля post в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals(commentModel.getContent(), listOfDataFromDB.get(2), "Значение поля content в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("1", listOfDataFromDB.get(3), "Значение поля status в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("comment", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению comment. Был создан не комментарий!");
        softAssert.assertAll();
    }
}
