package hu.reddit.developer.data;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditDataEntity<T extends BasicEntity> extends BasicEntity {
    @SerializedName("kind") public String kind;
    @SerializedName("data") public JsonObject data;
    private T mData;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditDataEntity{");
        sb.append("kind='").append(kind).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    public T getData(final Class<T> dataClass) {
        if (mData == null) {
            EntityJsonMapper<T> entityJsonMapper =
                  EntityJsonMapperFactory.getInstance().create(dataClass);
            mData = entityJsonMapper.transformEntity(data.toString(), dataClass);
        }
        return mData;
    }
}
