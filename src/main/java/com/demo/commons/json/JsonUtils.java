package com.demo.commons.json;

import com.fasterxml.jackson.databind.*;

import java.util.Map;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {}

    public static Object parseJson(String json) {
        try {
            return MAPPER.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON", e);
        }
    }

    public static Map<String, Object> parseLooseMap(String content) {
        try {
            return MAPPER.readValue("{" + content + "}", Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid AttributeValue string", e);
        }
    }

    public static Object fromJsonNode(JsonNode node) {
        return MAPPER.convertValue(node, Object.class);
    }

}

