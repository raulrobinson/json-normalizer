package com.demo.commons.json;

public final class JsonStringDetector {

    private JsonStringDetector() {}

    public static boolean isJson(String s) {
        String t = s.trim();
        return (t.startsWith("{") && t.endsWith("}")) ||
                (t.startsWith("[") && t.endsWith("]"));
    }

}
