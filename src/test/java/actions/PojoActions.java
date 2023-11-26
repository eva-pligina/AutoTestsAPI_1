package actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import model.CommentModel;
import model.PostModel;

public class PojoActions {
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

    @Step("Десериализация JSON в объект PostModel")
    public PostModel convertJsonToPostModel(String jsonObject) throws JsonProcessingException {
        return objectMapper.readValue(jsonObject, PostModel.class);
    }

    @Step("Десериализация JSON в объект CommentModel")
    public CommentModel convertJsonToCommentModel(String jsonObject) throws JsonProcessingException {
        return objectMapper.readValue(jsonObject, CommentModel.class);
    }
}
