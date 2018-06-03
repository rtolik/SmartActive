package rtolik.smartactive.config.websocket.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static rtolik.smartactive.config.Constants.YYYY_MM_DD_HH_MM;

/**
 * Created by danul on 04.08.2017.
 */
public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {



    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");



    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)  throws IOException {
        jsonGenerator.writeString(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM).format(localDateTime));
    }
}
