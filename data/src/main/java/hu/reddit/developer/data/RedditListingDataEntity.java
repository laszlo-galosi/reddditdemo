package hu.reddit.developer.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditListingDataEntity extends BasicEntity {
    @SerializedName("modhash") public String modhash;
    @SerializedName("children") public JsonArray children;
    private List<RedditDataEntity> mChildren;
    private EntityJsonMapper<RedditDataEntity> mChildrenMapper = new EntityJsonMapper<>(new Gson());
    //@SerializedName("after") public String children;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditChildEntity{");
        sb.append("modhash='").append(modhash).append('\'');
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }

    public List<RedditDataEntity> getChildren() {
        if (mChildren == null && children.isJsonArray()) {
            mChildren = mChildrenMapper.transformEntityCollection(children.toString(),
                                                                  RedditDataEntity[].class);
        }
        return mChildren;
    }
}
