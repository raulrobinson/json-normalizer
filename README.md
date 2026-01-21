# JSON normalizer

```java
dependencies {
    implementation 'com.demo.commons:json-normalizer:1.0.0'
}
```

```java
Map<String, Object> normalized =
        JsonNormalizer.normalizeToMap(input);
```

```java
package com.demo.commons;

import com.demo.commons.json.JsonNormalizer;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonNormalizerTest {

    @Test
    void normalizeDynamoJson() {

        Map<String, Object> input = Map.of(
                "value", Map.of(
                        "M", Map.of(
                                "enabled", Map.of("BOOL", true)
                        )
                )
        );

        Map<String, Object> result =
                JsonNormalizer.normalizeToMap(input);

        assertEquals(true,
                ((Map<?, ?>) result.get("value")).get("enabled")
        );
    }
}
```