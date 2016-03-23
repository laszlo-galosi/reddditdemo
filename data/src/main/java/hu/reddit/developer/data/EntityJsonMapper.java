package hu.reddit.developer.data;

/**
 * Created by László Gálosi on 23/03/16
 */

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * This class transforms a json string to a class instance of generic type.
 * Created by László Gálosi on 20/07/15
 */
public class EntityJsonMapper<T extends BasicEntity> {

    private Gson gson;

    public EntityJsonMapper(final Gson gson) {
        this.gson = gson;
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
            return this.gson.fromJson(jsonResponse, entityClass);
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
          final Class<T[]> entityClass) throws JsonSyntaxException {
        try {
            final T[] entityCollection = this.gson.fromJson(entityListJsonResponse, entityClass);
            return Arrays.asList(entityCollection);
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
