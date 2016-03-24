package hu.reddit.developer.data;

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
    //private EntityJsonMapper<RedditDataEntity>
    //      mChildrenMapper = EntityJsonMapperFactory.getInstance().create(RedditDataEntity.class);
    //@SerializedName("after") public String children;



    public List<RedditDataEntity> getChildren() {
        if (mChildren == null && children.isJsonArray()) {
            mChildren = EntityJsonMapperFactory
                  .getInstance()
                  .create(RedditDataEntity.class)
                  .transformEntityCollection(children.toString(), RedditDataEntity.class);
        }
        return mChildren;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditListingDataEntity{");
        sb.append("children=").append(children);
        sb.append(", modhash='").append(modhash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
