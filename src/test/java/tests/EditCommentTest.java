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

import java.util.ArrayList;

@Epic("Комментарий")
@Feature("Редактирование комментария")
public class EditCommentTest extends BaseTest {
    private String json;
    private int id;

    @BeforeMethod
    @Parameters({"resource"})
    public void beforeMethod(String resource) throws JsonProcessingException {
        databaseActions.createTableRowDB(resource);
        id = databaseActions.getRowId(resource);
        json = pojoActions.convertEditPojoToJson(resource);
    }

    @AfterMethod
    @Parameters({"resource"})
    public void afterMethod(String resource) {
        databaseActions.deleteLastTableRowDB(resource);
    }

    @Test
    @Story("POST-запрос для редактирования content у комментария по указанному id")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void editCommentTest(String resource) throws JsonProcessingException {
        requestSpecification
                .body(json)
            .when()
                .post(COMMENTS_ENDPOINT + id)
            .then()
                .statusCode(200);

        ArrayList listOfDataFromDB = databaseActions.getDataFromRowByIdDB(resource, id);

        softAssert.assertEquals(id, listOfDataFromDB.get(0), "Значение поля id не совпадает");
        softAssert.assertEquals(pojoActions.convertJsonToCommentModel(json).getContent(), listOfDataFromDB.get(2), "Значение поля content в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("comment", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению comment. Был создан не комментарий!");
        softAssert.assertAll();
    }
}
