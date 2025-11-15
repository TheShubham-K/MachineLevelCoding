package org.example.inventoryKeyValueStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KeyValueStore {

    public final Map<String, ValueObject> store = new HashMap<>();
    public final Map<String, Class<?>> attributeTypeRegistry = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Map<String, Object> get(String key) {

        lock.readLock().lock();
        try {
            if(store.containsKey(key)) {
                return store.get(key).getAttributes();
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<String> search(String attributeKey, String attributeValue) {

        lock.readLock().lock();
        try {
            List<String> matchingKeys = new ArrayList<>();
            for(Map.Entry<String, ValueObject> entry : store.entrySet()) {
                Object value = entry.getValue().getAttributes().get(attributeKey);
                if(value != null && attributeValue.equals(value.toString())) {
                    matchingKeys.add(entry.getKey());
                }
            }
            return matchingKeys;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(String key, List<Pair<String, String>> listOfAttributePairs) {

        lock.writeLock().lock();
        try{
            Map<String, Object> attributes = new HashMap<>();
            for(Pair<String, String> pair : listOfAttributePairs) {

                String attributeKey = pair.getK();
                String attributeValue = pair.getV();

                attributes.put(attributeKey, determineTypeOfAttributeValue(attributeKey, attributeValue));
            }
            store.put(key, new ValueObject(attributes));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void delete(String key) {
        lock.writeLock().lock();
        try {
            store.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<String> keys() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(store.keySet());
        } finally {
            lock.readLock().unlock();
        }
    }

    public Object determineTypeOfAttributeValue(String attributeKey, String attributeValue) {

        Object attributeTypeValue = getAttributeTypeValue(attributeValue);

        Class<?> currentClass = attributeTypeValue.getClass();
        Class<?> insertedClass = attributeTypeRegistry.putIfAbsent(attributeKey, currentClass);

        if(insertedClass != null && !insertedClass.equals(currentClass)) {

            throw new IllegalArgumentException("Attribute key: " + attributeKey+" "+
                    "is conflict as already different type indexed for this. Expected is: "+insertedClass.getSimpleName()+ " But got: "+currentClass.getSimpleName());
        }
        return attributeTypeValue;
    }

    private static Object getAttributeTypeValue(String attributeValue) {
        Object attributeTypeValue;

        if(attributeValue.matches("-?\\d+")) {
            attributeTypeValue = Integer.parseInt(attributeValue);
        } else if(attributeValue.matches("-?\\d+\\.d+")) {
            attributeTypeValue = Double.parseDouble(attributeValue);
        } else if("true".equalsIgnoreCase(attributeValue) || "false".equalsIgnoreCase(attributeValue)) {
            attributeTypeValue = Boolean.parseBoolean(attributeValue);
        } else {
            attributeTypeValue = attributeValue;
        }
        return attributeTypeValue;
    }
}