package com.demo.commons.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonStringDetectorTest {

    @Test
    void testIsJsonWithObject() {
        assertTrue(JsonStringDetector.isJson("{\"key\":\"value\"}"));
    }

    @Test
    void testIsJsonWithArray() {
        assertTrue(JsonStringDetector.isJson("[1,2,3]"));
    }

    @Test
    void testIsJsonWithEmptyObject() {
        assertTrue(JsonStringDetector.isJson("{}"));
    }

    @Test
    void testIsJsonWithEmptyArray() {
        assertTrue(JsonStringDetector.isJson("[]"));
    }

    @Test
    void testIsJsonWithWhitespace() {
        assertTrue(JsonStringDetector.isJson("  {\"key\":\"value\"}  "));
        assertTrue(JsonStringDetector.isJson("  [1,2,3]  "));
    }

    @Test
    void testIsJsonWithRegularString() {
        assertFalse(JsonStringDetector.isJson("regular string"));
    }

    @Test
    void testIsJsonWithMismatchedBrackets() {
        assertFalse(JsonStringDetector.isJson("{key]"));
        assertFalse(JsonStringDetector.isJson("[key}"));
    }

    @Test
    void testIsJsonWithOnlyOpenBracket() {
        assertFalse(JsonStringDetector.isJson("{"));
        assertFalse(JsonStringDetector.isJson("["));
    }

    @Test
    void testIsJsonWithOnlyCloseBracket() {
        assertFalse(JsonStringDetector.isJson("}"));
        assertFalse(JsonStringDetector.isJson("]"));
    }

    @Test
    void testIsJsonWithEmptyString() {
        assertFalse(JsonStringDetector.isJson(""));
    }
}
