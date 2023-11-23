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
@Feature("Редактирование поста")
public class EditPostTest extends BaseTest {
    private String json;
    private int id;

    @BeforeMethod
    @Parameters({"resource"})
    public void beforeMethod(String resource) throws SQLException, JsonProcessingException {
        steps.tableRowManipulationDB(SQL_REQUEST_CREATE_POST);
        id = steps.getRowId(SQL_REQUEST_SELECT_POST_LAST);
        json = steps.convertEditPojoToJson(resource);
    }

    @AfterMethod
    public void afterMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_POST_LAST);
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_POST_BY_ID + id);
    }

    @Test
    @Story("POST-запрос для редактирования title у поста по указанному id")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void editPostTest(String resource) throws SQLException, JsonProcessingException {
        requestSpecification
                .body(json)
            .when()
                .post(POSTS_ENDPOINT + id)
            .then()
                .statusCode(200);

        ArrayList listOfDataFromDB = steps.getDataFromRowDB(resource, SQL_REQUEST_SELECT_POST_BY_ID + id);

        softAssert.assertEquals(id, listOfDataFromDB.get(0), "Значение поля id не совпадает");
        softAssert.assertEquals(steps.convertJsonToPostModel(json).getTitle(), listOfDataFromDB.get(2), "Значение поля title в БД не соответствует указанному в post-запросе");
        softAssert.assertEquals("post", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению post. Был создан не пост!");
        softAssert.assertAll();
    }
}
