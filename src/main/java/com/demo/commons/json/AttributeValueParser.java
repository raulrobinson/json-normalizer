package com.demo.commons.json;

import java.util.Map;
import java.util.Set;

public final class AttributeValueParser {

    private AttributeValueParser() {}

    public static boolean isDynamoAttributeMap(Map<?, ?> map) {
        return map.size() == 1 &&
                map.keySet().iterator().next() instanceof String &&
                Set.of("S", "N", "BOOL", "M", "L")
                        .contains((String) map.keySet().iterator().next());
    }

    public static Object convertAttribute(Map<?, ?> attribute) {

        Object key = attribute.keySet().iterator().next();
        Object rawValue = attribute.values().iterator().next();

        if (!(key instanceof String type)) {
            throw new IllegalArgumentException("Invalid AttributeValue key: " + key);
        }

        return switch (type) {
            case "S" -> rawValue == null ? null : rawValue.toString();
            case "N" -> parseNumber(rawValue);
            case "BOOL" -> Boolean.parseBoolean(rawValue.toString());
            case "M", "L" -> JsonNormalizer.normalize(rawValue);
            default -> rawValue;
        };
    }

    private static Number parseNumber(Object raw) {
        if (raw == null) return null;
        String n = raw.toString();
        return n.contains(".")
                ? Double.parseDouble(n)
                : Long.parseLong(n);
    }

    public static boolean looksLikeAttributeValue(String s) {
        return s != null &&
                s.startsWith("AttributeValue(") &&
                s.endsWith(")");
    }

    public static Object parse(String s) {
        String cleaned = s
                .replace("AttributeValue(", "")
                .replace(")", "")
                .replace("=", ":");

        return JsonUtils.parseLooseMap(cleaned);
    }
}
