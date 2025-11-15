package org.example.inventoryKeyValueStore;

import java.util.HashMap;
import java.util.Map;

public class ValueObject {

    private final Map<String, Object> attributes;

    public ValueObject(Map<String, Object> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Object> e : attributes.entrySet()) {
            sb.append(e.getKey()).append(":").append(e.getValue()).append(",");
        }
        return sb.toString();
    }
}
