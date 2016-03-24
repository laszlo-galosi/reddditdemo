package hu.reddit.developer.data;

import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by László Gálosi on 23/03/16
 */
public class JsonSerializer<T> {

    private final Gson gson;
    private JsonParser jsonParser;

    public static @Nullable <M extends Object> M getAsChecked(String memberName,
          final JsonObject jsonObject,
          final Class<M> returnClass) {
        if (jsonObject != null && jsonObject.has(memberName)) {
            if (returnClass == JsonObject.class && jsonObject.get(memberName).isJsonObject()) {
                return (M) jsonObject.get(memberName).getAsJsonObject();
            } else if (returnClass == JsonArray.class && jsonObject.get(memberName).isJsonArray()) {
                return (M) jsonObject.get(memberName).getAsJsonArray();
            } else if (jsonObject.get(memberName).isJsonNull()) {
                return (M) jsonObject.get(memberName).getAsJsonNull();
            } else if (jsonObject.get(memberName).isJsonPrimitive()) {
                if (returnClass == String.class) {
                    return (M) jsonObject.get(memberName).getAsString();
                } else if (returnClass == Integer.class) {
                    return (M) Integer.valueOf(jsonObject.get(memberName).getAsInt());
                } else if (returnClass == Long.class) {
                    return (M) Long.valueOf(jsonObject.get(memberName).getAsLong());
                } else if (returnClass == Float.class) {
                    return (M) Float.valueOf(jsonObject.get(memberName).getAsFloat());
                } else if (returnClass == Double.class) {
                    return (M) Double.valueOf(jsonObject.get(memberName).getAsDouble());
                }
            }
            return (M) jsonObject.get(memberName);
        }
        return null;
    }

    public JsonSerializer() {
        this(new Gson(), new JsonParser());
    }

    public JsonSerializer(final Gson gson, JsonParser parser) {
        this.gson = gson;
        this.jsonParser = parser;
    }

    /**
     * Serialize an object to Json.
     *
     * @param entity {@link BasicEntity} to serialize.
     */
    public String serialize(T entity, Class<T> entityClass) {
        String jsonString = gson.toJson(entity, entityClass);
        return jsonString;
    }

    /**
     * Serialize an object to Json.
     *
     * @param entityList {@link BasicEntity} to serialize.
     */
    public String serializeAll(List<T> entityList, Class<T> entityClass) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0, len = entityList.size(); i < len; i++) {
            jsonArray.add(gson.toJsonTree(entityList.get(i), entityClass));
        }
        String jsonString = gson.toJson(jsonArray);
        return jsonString;
    }

    public List<T> deserializeAll(String jsonString, Class<T> entityClass) {
        JsonArray jsonArray = (JsonArray) jsonParser.parse(jsonString);
        int size = jsonArray.size();
        List<T> resultList = new ArrayList<>(size);
        for (int i = 0, len = size; i < len; i++) {
            T elem = gson.fromJson(jsonArray.get(i), entityClass);
            resultList.add(elem);
        }
        return resultList;
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param jsonString A json string to deserialize.
     * @return {@link BasicEntity}
     */
    public <T> T deserialize(String jsonString, Class<T> entityClass) {
        T entity = gson.fromJson(jsonString, entityClass);
        return entity;
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param treeMap A treemap deserialize.
     * @param entityClass A json string to deserialize.
     * @return {@link BasicEntity}
     */
    public <T> T deserialize(LinkedTreeMap treeMap, Class<T> entityClass) {
        int len = treeMap.keySet().size();
        JsonObject jsonObject = new JsonObject();
        for (Object key : treeMap.keySet()) {
            Object value = treeMap.get(key);
            if (value instanceof Number) {
                jsonObject.addProperty((String) key, (Number) value);
            } else if (value instanceof Boolean) {
                jsonObject.addProperty((String) key, (Boolean) value);
            } else if (value instanceof Character) {
                jsonObject.addProperty((String) key, (Character) value);
            } else if (value instanceof String) {
                jsonObject.addProperty((String) key, (String) value);
            }
        }
        T entity = gson.fromJson(jsonObject, entityClass);
        return entity;
    }

    public boolean isJsonArray(final String jsonString) {
        try {
            JsonArray jsonArray = (JsonArray) jsonParser.parse(jsonString);
            return true;
        } catch (final Exception ex) {
            return false;
        }
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }
}
