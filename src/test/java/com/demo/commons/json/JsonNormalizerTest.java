package com.demo.commons.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JsonNormalizerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testNormalizeNull() {
        assertNull(JsonNormalizer.normalize(null));
    }

    @Test
    void testNormalizeMap() {
        Map<String, Object> input = Map.of("key1", "value1", "key2", 42);
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(input);
        
        assertEquals("value1", result.get("key1"));
        assertEquals(42, result.get("key2"));
    }

    @Test
    void testNormalizeList() {
        List<Object> input = List.of("string", 123, true);
        List<Object> result = (List<Object>) JsonNormalizer.normalize(input);
        
        assertEquals(3, result.size());
        assertEquals("string", result.get(0));
        assertEquals(123, result.get(1));
        assertEquals(true, result.get(2));
    }

    @Test
    void testNormalizeJsonNode() throws Exception {
        JsonNode node = MAPPER.readTree("{\"test\": \"value\"}");
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(node);
        
        assertEquals("value", result.get("test"));
    }

    @Test
    void testNormalizeJsonString() {
        String jsonString = "{\"nested\": \"value\"}";
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(jsonString);
        
        assertEquals("value", result.get("nested"));
    }

    @Test
    void testNormalizeAttributeValueString() {
        String attributeValue = "AttributeValue(\"S\":\"test\")";
        Object result = JsonNormalizer.normalize(attributeValue);
        
        assertEquals("test", result);
    }

    @Test
    void testNormalizeRegularString() {
        String input = "regular string";
        String result = (String) JsonNormalizer.normalize(input);
        
        assertEquals("regular string", result);
    }

    @Test
    void testNormalizePrimitives() {
        assertEquals(42, JsonNormalizer.normalize(42));
        assertEquals(3.14, JsonNormalizer.normalize(3.14));
        assertEquals(true, JsonNormalizer.normalize(true));
        assertEquals(false, JsonNormalizer.normalize(false));
    }

    @Test
    void testNormalizeOtherObject() {
        Object customObject = new StringBuilder("test");
        String result = (String) JsonNormalizer.normalize(customObject);
        
        assertEquals("test", result);
    }

    @Test
    void testNormalizeDynamoAttributeMap() {
        Map<String, Object> dynamoMap = Map.of("S", "stringValue");
        Object result = JsonNormalizer.normalize(dynamoMap);
        
        assertEquals("stringValue", result);
    }

    @Test
    void testNormalizeNestedStructures() {
        Map<String, Object> nested = Map.of(
            "list", List.of(1, 2, Map.of("inner", "value")),
            "map", Map.of("key", "value")
        );
        
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(nested);
        
        List<Object> resultList = (List<Object>) result.get("list");
        assertEquals(3, resultList.size());
        assertEquals(1, resultList.get(0));
        assertEquals(2, resultList.get(1));
        
        Map<String, Object> innerMap = (Map<String, Object>) resultList.get(2);
        assertEquals("value", innerMap.get("inner"));
    }

    @Test
    void testNormalizeEmptyCollections() {
        Map<?, ?> emptyMap = (Map<?, ?>) JsonNormalizer.normalize(Map.of());
        assertTrue(emptyMap.isEmpty());
        
        List<?> emptyList = (List<?>) JsonNormalizer.normalize(List.of());
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void testNormalizeJsonArray() {
        String jsonArray = "[1, 2, 3]";
        List<Object> result = (List<Object>) JsonNormalizer.normalize(jsonArray);
        
        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    void testNormalizeDynamoNumberAttribute() {
        Map<String, Object> dynamoMap = Map.of("N", "123");
        Object result = JsonNormalizer.normalize(dynamoMap);
        
        assertTrue(result instanceof Number);
        assertEquals(123L, ((Number) result).longValue());
    }

    @Test
    void testNormalizeDynamoBoolAttribute() {
        Map<String, Object> dynamoMap = Map.of("BOOL", "true");
        Object result = JsonNormalizer.normalize(dynamoMap);
        
        assertTrue(result instanceof Boolean);
        assertTrue((Boolean) result);
    }

    @Test
    void testNormalizeDynamoMapAttribute() {
        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("key", "value");
        Map<String, Object> dynamoMap = Map.of("M", innerMap);
        
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(dynamoMap);
        assertEquals("value", result.get("key"));
    }

    @Test
    void testNormalizeDynamoListAttribute() {
        List<Object> innerList = List.of("a", "b");
        Map<String, Object> dynamoMap = Map.of("L", innerList);
        
        Object result = JsonNormalizer.normalize(dynamoMap);
        assertTrue(result instanceof List);
        assertEquals(2, ((List<?>) result).size());
    }

    @Test
    void testNormalizeMapWithNonStringKeys() {
        Map<Integer, String> input = new HashMap<>();
        input.put(1, "value1");
        input.put(2, "value2");
        
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(input);
        assertEquals("value1", result.get("1"));
        assertEquals("value2", result.get("2"));
    }

    @Test
    void testNormalizeNestedJsonString() {
        String nestedJson = "{\"outer\": \"{\\\"inner\\\": \\\"value\\\"}\"}"; 
        Map<String, Object> result = (Map<String, Object>) JsonNormalizer.normalize(nestedJson);
        
        Map<String, Object> inner = (Map<String, Object>) result.get("outer");
        assertEquals("value", inner.get("inner"));
    }

    @Test
    void testNormalizeListWithNestedElements() {
        List<Object> input = new ArrayList<>();
        input.add(List.of(1, 2));
        input.add(Map.of("key", "value"));
        input.add(null);
        
        List<Object> result = (List<Object>) JsonNormalizer.normalize(input);
        assertEquals(3, result.size());
        assertNull(result.get(2));
    }

    @Test
    void testNormalizeIntegerNumber() {
        assertEquals(100, JsonNormalizer.normalize(100));
    }

    @Test
    void testNormalizeLongNumber() {
        assertEquals(100L, JsonNormalizer.normalize(100L));
    }

    @Test
    void testNormalizeFloatNumber() {
        assertEquals(1.5f, JsonNormalizer.normalize(1.5f));
    }

    @Test
    void testNormalizeDoubleNumber() {
        assertEquals(2.5, JsonNormalizer.normalize(2.5));
    }
}
