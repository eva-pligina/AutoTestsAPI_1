package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import model.CommentModel;
import model.PostModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Steps {
    ObjectMapper objectMapper = new ObjectMapper();

    @Step("Создание объекта ресурса Wordpress \"{resource}\" и его сериализация в JSON")
    public String convertPojoToJson(String resource) throws JsonProcessingException {
        switch (resource) {
            case "post":
                PostModel post = PostModel.builder().title("Title").content("Description").status("publish").build();
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);
            case "comment":
                CommentModel comment = CommentModel.builder().content("Content").status("approved").post("1").build();
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(comment);
        }
        return "";
    }

    @Step("Изменение объекта ресурса Wordpress \"{resource}\" и его сериализация в JSON")
    public String convertEditPojoToJson(String resource) throws JsonProcessingException {
        switch (resource) {
            case "post":
                PostModel post = PostModel.builder().title("Edit Title").build();
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);
            case "comment":
                CommentModel comment = CommentModel.builder().content("Edit content").build();
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(comment);
        }
        return "";
    }

    @Step("Манипуляция с таблицей БД с помощью SQL-запроса: \"{sqlRequest}\" ")
    public void tableRowManipulationDB(String sqlRequest) throws SQLException {
        Connection connection  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/wordpress",
                "wordpress", "wordpress");
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlRequest);
        connection.close();
    }

    @Step("Запоминание id строки таблицы БД при обращении к ней по sql-запросу")
    public int getRowId(String sqlRequest) throws SQLException {
        Connection connection  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/wordpress",
                "wordpress", "wordpress");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlRequest);
        result.next();
        int id = result.getInt(1);
        connection.close();
        return id;
    }

    @Step("Запоминание определенных данных из строки определенной таблицы БД (рассматривается ресурс Wordpress: {resource})")
    public ArrayList getDataFromRowDB(String resource, String sqlRequest) throws SQLException {
        ArrayList list = new ArrayList();
        Connection connection  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/wordpress",
                "wordpress", "wordpress");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlRequest);
        result.next();

        switch (resource) {
            case "post":
                list.add(result.getInt(1));
                list.add(result.getString(5));
                list.add(result.getString(6));
                list.add(result.getString(8));
                list.add(result.getString(21));
            case "comment":
                list.add(result.getInt(1));
                list.add(result.getString(2));
                list.add(result.getString(9));
                list.add(result.getString(11));
                list.add(result.getString(13));
        }
        connection.close();
        return list;
    }

    @Step("Десериализация JSON в объект PostModel")
    public PostModel convertJsonToPostModel(String jsonObject) throws JsonProcessingException {
        return objectMapper.readValue(jsonObject, PostModel.class);
    }

    @Step("Десериализация JSON в объект CommentModel")
    public CommentModel convertJsonToCommentModel(String jsonObject) throws JsonProcessingException {
        return objectMapper.readValue(jsonObject, CommentModel.class);
    }
}
