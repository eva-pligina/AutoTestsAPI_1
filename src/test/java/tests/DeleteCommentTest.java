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

@Epic("Комментарий")
@Feature("Удаление комментария")
public class DeleteCommentTest extends BaseTest {
    private int id;

    @BeforeMethod
    public void beforeMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_CREATE_COMMENT);
        id = steps.getRowId(SQL_REQUEST_SELECT_COMMENT_LAST);
    }

    @AfterMethod
    public void afterMethod() throws SQLException {
        steps.tableRowManipulationDB(SQL_REQUEST_DELETE_COMMENT_LAST);
    }

    @Test
    @Story("DELETE-запрос для удаления комментария по указанному id")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void deleteCommentTest(String resource) throws SQLException {
        requestSpecification
            .when()
                .delete(COMMENTS_ENDPOINT + id)
            .then()
                .statusCode(200);

        ArrayList listOfDataFromDB = steps.getDataFromRowDB(resource, SQL_REQUEST_SELECT_COMMENT_BY_ID + id);

        softAssert.assertEquals(id, listOfDataFromDB.get(0), "Значение поля id не совпадает");
        softAssert.assertEquals("trash", listOfDataFromDB.get(3), "Значение поля status в БД не соответствует знач-ю trash. Delete-запрос прошёл некорректно");
        softAssert.assertEquals("comment", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению comment");
        softAssert.assertAll();
    }
}
