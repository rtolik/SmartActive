package rtolik.smartactive.config.websocket.utils.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rtolik.smartactive.config.websocket.utils.CustomLocalDateTimeDeserializer;
import rtolik.smartactive.config.websocket.utils.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * Created by danul on 07.10.2017.
 */
public class Message {

    private String message;
    private String userName;

    private LocalDateTime date;

    private CategoryMessage categoryMessage;

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Message setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public CategoryMessage getCategoryMessage() {
        return categoryMessage;
    }

    public Message setCategoryMessage(CategoryMessage categoryMessage) {
        this.categoryMessage = categoryMessage;
        return this;
    }

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    public LocalDateTime getDate() {
        return date;
    }

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    public Message setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

}
