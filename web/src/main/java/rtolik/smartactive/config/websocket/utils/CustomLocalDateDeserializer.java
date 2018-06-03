package rtolik.smartactive.config.websocket.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static rtolik.smartactive.config.Constants.YYYY_MM_DD;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern(YYYY_MM_DD);


    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        try {
            String date = jsonParser.getText();
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    public static LocalDate toLocalDateParse(String date) {
        if (date == null || date.isEmpty()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }
}
