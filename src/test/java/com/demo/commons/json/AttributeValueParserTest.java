package com.demo.commons.json;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AttributeValueParserTest {

    @Test
    void testIsDynamoAttributeMapWithStringType() {
        Map<String, Object> map = Map.of("S", "value");
        assertTrue(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithNumberType() {
        Map<String, Object> map = Map.of("N", "123");
        assertTrue(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithBoolType() {
        Map<String, Object> map = Map.of("BOOL", "true");
        assertTrue(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithMapType() {
        Map<String, Object> map = Map.of("M", Map.of());
        assertTrue(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithListType() {
        Map<String, Object> map = Map.of("L", List.of());
        assertTrue(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithInvalidType() {
        Map<String, Object> map = Map.of("INVALID", "value");
        assertFalse(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithMultipleKeys() {
        Map<String, Object> map = Map.of("S", "value", "N", "123");
        assertFalse(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testIsDynamoAttributeMapWithNonStringKey() {
        Map<Integer, Object> map = Map.of(1, "value");
        assertFalse(AttributeValueParser.isDynamoAttributeMap(map));
    }

    @Test
    void testConvertAttributeStringType() {
        Map<String, Object> attribute = Map.of("S", "testValue");
        String result = (String) AttributeValueParser.convertAttribute(attribute);
        assertEquals("testValue", result);
    }

    @Test
    void testConvertAttributeStringTypeWithNull() {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("S", null);
        assertNull(AttributeValueParser.convertAttribute(attribute));
    }

    @Test
    void testConvertAttributeNumberTypeInteger() {
        Map<String, Object> attribute = Map.of("N", "123");
        Number result = (Number) AttributeValueParser.convertAttribute(attribute);
        assertEquals(123L, result.longValue());
    }

    @Test
    void testConvertAttributeNumberTypeDouble() {
        Map<String, Object> attribute = Map.of("N", "123.45");
        Number result = (Number) AttributeValueParser.convertAttribute(attribute);
        assertEquals(123.45, result);
    }

    @Test
    void testConvertAttributeNumberTypeWithNull() {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("N", null);
        assertNull(AttributeValueParser.convertAttribute(attribute));
    }

    @Test
    void testConvertAttributeBoolTypeTrue() {
        Map<String, Object> attribute = Map.of("BOOL", "true");
        Boolean result = (Boolean) AttributeValueParser.convertAttribute(attribute);
        assertTrue(result);
    }

    @Test
    void testConvertAttributeBoolTypeFalse() {
        Map<String, Object> attribute = Map.of("BOOL", "false");
        Boolean result = (Boolean) AttributeValueParser.convertAttribute(attribute);
        assertFalse(result);
    }

    @Test
    void testConvertAttributeMapType() {
        Map<String, Object> innerMap = Map.of("key", "value");
        Map<String, Object> attribute = Map.of("M", innerMap);
        Map<String, Object> result = (Map<String, Object>) AttributeValueParser.convertAttribute(attribute);
        assertEquals("value", result.get("key"));
    }

    @Test
    void testConvertAttributeListType() {
        List<Object> innerList = List.of("a", "b");
        Map<String, Object> attribute = Map.of("L", innerList);
        List<Object> result = (List<Object>) AttributeValueParser.convertAttribute(attribute);
        assertEquals(2, result.size());
    }

    @Test
    void testConvertAttributeDefaultType() {
        Map<String, Object> attribute = Map.of("UNKNOWN", "value");
        Object result = AttributeValueParser.convertAttribute(attribute);
        assertEquals("value", result);
    }

    @Test
    void testConvertAttributeWithNonStringKey() {
        Map<Integer, Object> attribute = Map.of(1, "value");
        assertThrows(IllegalArgumentException.class, () -> 
            AttributeValueParser.convertAttribute(attribute)
        );
    }

    @Test
    void testLooksLikeAttributeValueTrue() {
        assertTrue(AttributeValueParser.looksLikeAttributeValue("AttributeValue(S=test)"));
    }

    @Test
    void testLooksLikeAttributeValueFalse() {
        assertFalse(AttributeValueParser.looksLikeAttributeValue("NotAttributeValue"));
    }

    @Test
    void testLooksLikeAttributeValueWithNull() {
        assertFalse(AttributeValueParser.looksLikeAttributeValue(null));
    }

    @Test
    void testLooksLikeAttributeValueMissingPrefix() {
        assertFalse(AttributeValueParser.looksLikeAttributeValue("S=test)"));
    }

    @Test
    void testLooksLikeAttributeValueMissingSuffix() {
        assertFalse(AttributeValueParser.looksLikeAttributeValue("AttributeValue(S=test"));
    }

    @Test
    void testParse() {
        String input = "AttributeValue(\"S\":\"test\")";
        Map<String, Object> result = (Map<String, Object>) AttributeValueParser.parse(input);
        assertEquals("test", result.get("S"));
    }
}
