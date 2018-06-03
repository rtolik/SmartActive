package rtolik.smartactive.config.websocket.utils.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rtolik.smartactive.config.websocket.utils.CustomLocalDateTimeDeserializer;
import rtolik.smartactive.config.websocket.utils.CustomLocalDateTimeSerializer;
import rtolik.smartactive.models.Category;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by danul on 07.10.2017.
 */
public class CategoryMessage {

    private Integer id;
    @JsonIgnore
    private List<Message> messages;
    private String name;
    private LocalDateTime time;

    public CategoryMessage(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.time = LocalDateTime.now();
    }

    public CategoryMessage(){}

    public Integer getId() {
        return id;
    }

    public CategoryMessage setId(Integer id) {
        this.id = id;
        return this;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public CategoryMessage setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryMessage setName(String name) {
        this.name = name;
        return this;
    }

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    public LocalDateTime getTime() {
        return time;
    }

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    public CategoryMessage setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryMessage)) return false;

        CategoryMessage that = (CategoryMessage) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
