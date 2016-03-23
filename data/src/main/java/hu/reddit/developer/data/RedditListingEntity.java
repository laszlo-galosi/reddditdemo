package hu.reddit.developer.data;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditListingEntity extends BasicEntity {
    @SerializedName("kind") public String id;
    @SerializedName("data") public JsonObject data;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditListingEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
