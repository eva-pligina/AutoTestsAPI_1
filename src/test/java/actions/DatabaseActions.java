package actions;

import helpers.PropertyProvider;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseActions {
    final String SQL_REQUEST_CREATE_POST = "INSERT INTO wp_posts (post_date, post_date_gmt, post_content, post_title, post_excerpt, to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered) VALUES (NOW(), NOW(), 'Описание', 'Пост', '', '', '', NOW(), NOW(), '');";
    final String SQL_REQUEST_CREATE_COMMENT = "INSERT INTO wp_comments (comment_author, comment_date, comment_date_gmt, comment_content) VALUES ('Eva.Pligina', NOW(), NOW(), 'Text');";
    final String SQL_REQUEST_DELETE_POST_LAST = "DELETE FROM wp_posts ORDER BY ID DESC LIMIT 1;";
    final String SQL_REQUEST_DELETE_COMMENT_LAST = "DELETE FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;";
    final String SQL_REQUEST_DELETE_POST_BY_ID = "DELETE FROM wp_posts WHERE ID = ?";
    final String SQL_REQUEST_SELECT_POST_LAST = "SELECT * FROM wp_posts ORDER BY ID DESC LIMIT 1;";
    final String SQL_REQUEST_SELECT_COMMENT_LAST = "SELECT * FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;";
    final String SQL_REQUEST_SELECT_POST_BY_ID = "SELECT * FROM wp_posts WHERE ID = ?";
    final String SQL_REQUEST_SELECT_COMMENT_BY_ID = "SELECT * FROM wp_comments WHERE comment_ID = ?";

    @Step("Подключение к БД")
    public Connection connectionDB() throws SQLException {
        return DriverManager.getConnection(
                PropertyProvider.getInstance().getProperty("jdbc.url"),
                PropertyProvider.getInstance().getProperty("db.user"), PropertyProvider.getInstance().getProperty("db.password"));
    }

    @Step("Запоминание id строки таблицы БД при обращении к ней по sql-запросу")
    public int getRowId(String resource) {
        int id = 0;
        ResultSet result;

        try (Connection connection = connectionDB();
             Statement statement = connection.createStatement()) {

            switch (resource) {
                case "post":
                    result = statement.executeQuery(SQL_REQUEST_SELECT_POST_LAST);
                    result.next();
                    id = result.getInt(1);
                    break;
                case "comment":
                    result = statement.executeQuery(SQL_REQUEST_SELECT_COMMENT_LAST);
                    result.next();
                    id = result.getInt(1);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Step("Запоминание определенных данных из строки с id = {id} определенной таблицы БД (рассматривается ресурс Wordpress: {resource})")
    public ArrayList getDataFromRowByIdDB(String resource, int id) {
        ArrayList list = new ArrayList();
        try (Connection connection = connectionDB();
             PreparedStatement statement = createPreparedStatementForSelectRequest(connection, resource, id);
             ResultSet rs = statement.executeQuery()) {

            rs.next();
            list = getListData(resource,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Step("Подготовка sql-запроса (выбор строки) с помощью класса PreparedStatement для его дальнейшего выполнения")
    private PreparedStatement createPreparedStatementForSelectRequest(Connection con, String resource, int id) throws SQLException {
        PreparedStatement ps = null;
        switch (resource) {
            case "post":
                ps = con.prepareStatement(SQL_REQUEST_SELECT_POST_BY_ID);
                ps.setInt(1, id);
                break;
            case "comment":
                ps = con.prepareStatement(SQL_REQUEST_SELECT_COMMENT_BY_ID);
                ps.setInt(1, id);
                break;
        }
        return ps;
    }

    @Step("Запоминание данных из определенных ячеек строки таблицы БД")
    private ArrayList getListData(String resource, ResultSet result) throws SQLException {
        ArrayList list = new ArrayList();
        switch (resource) {
            case "post":
                list.add(result.getInt(1));
                list.add(result.getString(5));
                list.add(result.getString(6));
                list.add(result.getString(8));
                list.add(result.getString(21));
                break;
            case "comment":
                list.add(result.getInt(1));
                list.add(result.getString(2));
                list.add(result.getString(9));
                list.add(result.getString(11));
                list.add(result.getString(13));
                break;
        }
        return list;
    }

    @Step("Запоминание определенных данных из последней строки определенной таблицы БД (рассматривается ресурс Wordpress: {resource})")
    public ArrayList getDataFromLastRowDB(String resource) {
        ArrayList list = new ArrayList();
        ResultSet result;
        try (Connection connection = connectionDB();
             Statement statement = connection.createStatement()) {

            switch (resource) {
                case "post":
                    result = statement.executeQuery(SQL_REQUEST_SELECT_POST_LAST);
                    result.next();
                    list = getListData(resource, result);
                    break;
                case "comment":
                    result = statement.executeQuery(SQL_REQUEST_SELECT_COMMENT_LAST);
                    result.next();
                    list = getListData(resource, result);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Step("Создание строки таблицы БД ресурса Wordpress {resource}")
    public void createTableRowDB(String resource) {
        try (Connection connection = connectionDB();
             Statement statement = connection.createStatement()) {
            switch (resource) {
                case "post":
                    statement.executeUpdate(SQL_REQUEST_CREATE_POST);
                    break;
                case "comment":
                    statement.executeUpdate(SQL_REQUEST_CREATE_COMMENT);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Step("Удаление последней строки таблицы БД ресурса Wordpress {resource}")
    public void deleteLastTableRowDB(String resource) {
        try (Connection connection = connectionDB();
             Statement statement = connection.createStatement()) {

            switch (resource) {
                case "post":
                    statement.executeUpdate(SQL_REQUEST_DELETE_POST_LAST);
                    break;
                case "comment":
                    statement.executeUpdate(SQL_REQUEST_DELETE_COMMENT_LAST);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Step("Удаление строки с id = {id} таблицы БД ресурса Wordpress {resource}")
    public void deleteTableRowByIdDB(String resource, int id) {

        try (Connection connection = connectionDB();
             PreparedStatement statement = createPreparedStatementForDeleteRequest(connection, resource, id)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Step("Подготовка sql-запроса (удаление строки) с помощью класса PreparedStatement для его дальнейшего выполнения")
    private PreparedStatement createPreparedStatementForDeleteRequest(Connection con, String resource, int id) throws SQLException {
        PreparedStatement ps = null;
        switch (resource) {
            case "post":
                ps = con.prepareStatement(SQL_REQUEST_DELETE_POST_BY_ID);
                break;
            case "comment":
                //тут можно написать код для comment-ресурса (если необходимо)
                break;
        }
        ps.setInt(1, id);
        return ps;
    }
}
