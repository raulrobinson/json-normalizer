package com.demo.commons.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testParseJsonWithObject() {
        Object result = JsonUtils.parseJson("{\"key\":\"value\"}");
        assertTrue(result instanceof Map);
        assertEquals("value", ((Map<?, ?>) result).get("key"));
    }

    @Test
    void testParseJsonWithArray() {
        Object result = JsonUtils.parseJson("[1,2,3]");
        assertTrue(result instanceof List);
        assertEquals(3, ((List<?>) result).size());
    }

    @Test
    void testParseJsonWithString() {
        Object result = JsonUtils.parseJson("\"test\"");
        assertEquals("test", result);
    }

    @Test
    void testParseJsonWithNumber() {
        Object result = JsonUtils.parseJson("123");
        assertEquals(123, result);
    }

    @Test
    void testParseJsonWithBoolean() {
        Object result = JsonUtils.parseJson("true");
        assertEquals(true, result);
    }

    @Test
    void testParseJsonWithNull() {
        Object result = JsonUtils.parseJson("null");
        assertNull(result);
    }

    @Test
    void testParseJsonWithInvalidJson() {
        assertThrows(RuntimeException.class, () -> JsonUtils.parseJson("invalid"));
    }

    @Test
    void testParseLooseMapValid() {
        Map<String, Object> result = JsonUtils.parseLooseMap("\"key\":\"value\"");
        assertEquals("value", result.get("key"));
    }

    @Test
    void testParseLooseMapWithMultipleKeys() {
        Map<String, Object> result = JsonUtils.parseLooseMap("\"a\":1,\"b\":2");
        assertEquals(1, result.get("a"));
        assertEquals(2, result.get("b"));
    }

    @Test
    void testParseLooseMapInvalid() {
        assertThrows(RuntimeException.class, () -> JsonUtils.parseLooseMap("invalid"));
    }

    @Test
    void testFromJsonNodeWithObject() throws Exception {
        JsonNode node = MAPPER.readTree("{\"key\":\"value\"}");
        Object result = JsonUtils.fromJsonNode(node);
        assertTrue(result instanceof Map);
        assertEquals("value", ((Map<?, ?>) result).get("key"));
    }

    @Test
    void testFromJsonNodeWithArray() throws Exception {
        JsonNode node = MAPPER.readTree("[1,2,3]");
        Object result = JsonUtils.fromJsonNode(node);
        assertTrue(result instanceof List);
        assertEquals(3, ((List<?>) result).size());
    }

    @Test
    void testFromJsonNodeWithString() throws Exception {
        JsonNode node = MAPPER.readTree("\"test\"");
        Object result = JsonUtils.fromJsonNode(node);
        assertEquals("test", result);
    }

    @Test
    void testFromJsonNodeWithNumber() throws Exception {
        JsonNode node = MAPPER.readTree("123");
        Object result = JsonUtils.fromJsonNode(node);
        assertEquals(123, result);
    }

    @Test
    void testFromJsonNodeWithBoolean() throws Exception {
        JsonNode node = MAPPER.readTree("true");
        Object result = JsonUtils.fromJsonNode(node);
        assertEquals(true, result);
    }

    @Test
    void testFromJsonNodeWithNull() throws Exception {
        JsonNode node = MAPPER.readTree("null");
        Object result = JsonUtils.fromJsonNode(node);
        assertNull(result);
    }
}
