package hu.reddit.developer.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by László Gálosi on 23/03/16
 */
public class PreviewEntity extends BasicEntity {
    @SerializedName("images") public String images;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("PreviewEntity{");
        sb.append("images='").append(images).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
