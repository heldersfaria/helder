package com.hiring.helder.utils;

import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.hiring.helder.constants.StringConstants.DATE_FORMAT;
import static com.hiring.helder.utils.Formatador.converterLocalDateTtoJsonElement;
import static com.hiring.helder.utils.Formatador.converterStringToLocalDate;


public final class TestUtils {


    public static final Gson GSON = new GsonBuilder().setDateFormat(DATE_FORMAT).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return converterStringToLocalDate(json.getAsString());
        }
    }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return converterLocalDateTtoJsonElement(src);
        }

    }).create();


    public String readFromResources(String fileName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            byte[] encoded = Files.readAllBytes(Paths.get(classLoader.getResource(fileName).getPath()));
            return new String(encoded, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public <T> T readFromResources(Class<T> entity, String resource) {
        String jsonObject = readFromResources(resource);
        return GSON.fromJson(jsonObject, entity);
    }

    public <T> T readFromResourcesFromPayload(Class<T> entity, String payload) {
        return GSON.fromJson(payload, entity);
    }

    public <T> String readFromEntity(T entity) {
        return GSON.toJson(entity);
    }
}