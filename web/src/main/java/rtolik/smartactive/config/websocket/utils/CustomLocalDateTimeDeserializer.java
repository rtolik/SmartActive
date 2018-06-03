package rtolik.smartactive.config.websocket.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by danul on 04.08.2017.
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime toLocalDateParse(String date) {
        if (date == null || date.isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            String date = jsonParser.getText();
            return LocalDateTime.parse(date, dateTimeFormatter);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
