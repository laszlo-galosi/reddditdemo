package hu.reddit.developer.data;

/**
 * Created by László Gálosi on 23/03/16
 */

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import trikita.log.Log;

/**
 * This class transforms a json string to a class instance of generic type.
 * Created by László Gálosi on 20/07/15
 */
public class EntityJsonMapper<T extends BasicEntity> {

    private final Gson gson;
    private JsonSerializer<T> mJsonSerializer;

    public EntityJsonMapper(final Gson gson, final JsonParser jsonParser) {
        this.gson = gson;
        mJsonSerializer = new JsonSerializer<>(gson, jsonParser);
    }

    /**
     * Transform from valid json string to {@link BasicEntity}.
     *
     * @param jsonResponse A json representing a user profile.
     * @return the object type of T which extends {@link BasicEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json
     * structure.
     */
    public final T transformEntity(final String jsonResponse, final Class<T> entityClass)
          throws JsonSyntaxException {
        try {
            if (entityClass == RedditEntity.class) {
                return (T) RedditEntity.deserialize(jsonResponse, mJsonSerializer.getJsonParser());
            } else {
                Log.v("transformEntity", entityClass.getSimpleName()).v(jsonResponse);
                return mJsonSerializer.deserialize(jsonResponse, entityClass);
            }
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link BasicEntity}.
     *
     * @param entityListJsonResponse A json representing a collection of users.
     * @return List of {@link BasicEntity} super types.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json
     * structure.
     */
    public final List<T> transformEntityCollection(final String entityListJsonResponse,
          final Class<T> entityClass) throws JsonSyntaxException {
        try {
            Log.v("transformEntityCollection", entityClass.getSimpleName())
               .v(entityListJsonResponse);
            return mJsonSerializer.deserializeAll(entityListJsonResponse, entityClass);
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public JsonSerializer<T> getJsonSerializer() {
        return mJsonSerializer;
    }
}
