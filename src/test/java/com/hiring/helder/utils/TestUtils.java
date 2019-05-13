package com.hiring.helder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hiring.helder.constants.StringConstants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public final class TestUtils {


    public static final Gson GSON = new GsonBuilder().setDateFormat(StringConstants.DATE_FORMAT).create();


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
}