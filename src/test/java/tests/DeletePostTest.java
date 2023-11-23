package tests;

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
@Feature("Удаление поста")
public class DeletePostTest extends BaseTest {
    private int id;

    @BeforeMethod
    public void beforeMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_CREATE_POST);
        id = steps.getRowId(SQL_REQUEST_SELECT_POST_LAST);
    }

    @AfterMethod
    public void afterMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_POST_LAST);
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_POST_BY_ID + id);
    }

    @Test
    @Story("DELETE-запрос для удаления поста по указанному id")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void deletePostTest(String resource) throws SQLException {
        requestSpecification
            .when()
                .delete(POSTS_ENDPOINT + id)
            .then()
                .statusCode(200);

        ArrayList listOfDataFromDB = steps.getDataFromRowDB(resource, SQL_REQUEST_SELECT_POST_BY_ID + id);

        softAssert.assertEquals(id, listOfDataFromDB.get(0), "Значение поля id не совпадает");
        softAssert.assertEquals("trash", listOfDataFromDB.get(3), "Значение поля status в БД не соответствует знач-ю trash. Delete-запрос прошёл некорректно");
        softAssert.assertEquals("post", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению post");
        softAssert.assertAll();
    }
}
