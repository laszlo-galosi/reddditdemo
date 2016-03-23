package hu.reddit.developer.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditDataEntity<T extends BasicEntity> extends BasicEntity {
    @SerializedName("kind") public String id;
    @SerializedName("data") public JsonObject data;
    private T mData;
    private EntityJsonMapper<T> mDataMapper = new EntityJsonMapper<>(new Gson());

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditDataEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    public T getData(final Class<T> entityClass) {
        if (mData == null) {
            mData = mDataMapper.transformEntity(data.toString(), entityClass);
        }
        return mData;
    }
}
