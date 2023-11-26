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

import java.util.ArrayList;

@Epic("Комментарий")
@Feature("Удаление комментария")
public class DeleteCommentTest extends BaseTest {
    private int id;

    @BeforeMethod
    @Parameters({"resource"})
    public void beforeMethod(String resource) {
        databaseActions.createTableRowDB(resource);
        id = databaseActions.getRowId(resource);
    }

    @AfterMethod
    @Parameters({"resource"})
    public void afterMethod(String resource) {
        databaseActions.deleteLastTableRowDB(resource);
    }

    @Test
    @Story("DELETE-запрос для удаления комментария по указанному id")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"resource"})
    public void deleteCommentTest(String resource) {
        requestSpecification
            .when()
                .delete(COMMENTS_ENDPOINT + id)
            .then()
                .statusCode(200);

        ArrayList listOfDataFromDB = databaseActions.getDataFromRowByIdDB(resource, id);

        softAssert.assertEquals(id, listOfDataFromDB.get(0), "Значение поля id не совпадает");
        softAssert.assertEquals("trash", listOfDataFromDB.get(3), "Значение поля status в БД не соответствует знач-ю trash. Delete-запрос прошёл некорректно");
        softAssert.assertEquals("comment", listOfDataFromDB.get(4), "Значение поля type в БД не соответствует значению comment");
        softAssert.assertAll();
    }
}
