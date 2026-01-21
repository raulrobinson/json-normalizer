package com.demo.commons.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

public final class JsonNormalizer {

    private JsonNormalizer() {}

    public static Object normalize(Object value) {

        switch (value) {

            // --- Null ---
            case null -> {
                return null;
            }

            // --- Map ---
            case Map<?, ?> map -> {
                return normalizeMap(map);
            }

            // --- List ---
            case List<?> list -> {
                return normalizeList(list);
            }

            // --- JsonNode ---
            case JsonNode node -> {
                return JsonUtils.fromJsonNode(node);
            }

            // --- String ---
            case String str -> {

                // JSON embebido
                if (JsonStringDetector.isJson(str)) {
                    return normalize(JsonUtils.parseJson(str));
                }

                // AttributeValue(M={...})
                if (AttributeValueParser.looksLikeAttributeValue(str)) {
                    return normalize(AttributeValueParser.parse(str));
                }

                return str;
            }
            default -> {}
        }

        // --- Primitivos ---
        if (value instanceof Number || value instanceof Boolean) {
            return value;
        }

        return value.toString();
    }

    private static Map<String, Object> normalizeMap(Map<?, ?> input) {

        // Detecta estructura DynamoDB
        if (AttributeValueParser.isDynamoAttributeMap(input)) {
            return (Map<String, Object>) AttributeValueParser.convertAttribute(input);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        input.forEach((k, v) -> result.put(k.toString(), normalize(v)));
        return result;
    }

    private static List<Object> normalizeList(List<?> input) {
        List<Object> result = new ArrayList<>();
        input.forEach(v -> result.add(normalize(v)));
        return result;
    }

}
