package rtolik.smartactive.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonMapper {

    public static <T> T json(String files, Class<T> parsingClasses) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T jsonAd = objectMapper.readValue(files, parsingClasses);
            return parsingClasses.cast(jsonAd);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }
    public static <T> ArrayList jsons(String files, Class<T> parsingClasses) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List jsonAd = objectMapper.readValue(files, List.class);
            return ArrayList.class.cast(jsonAd);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

}