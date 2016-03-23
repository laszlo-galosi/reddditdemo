package hu.reddit.developer.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by László Gálosi on 23/03/16
 */
public class ImageIdEntity extends BasicEntity {
    @SerializedName("id") public String id;
    @SerializedName("source") public JsonElement source;
    @SerializedName("resolutions") public JsonArray resolutions;

    private EntityJsonMapper<ImageEntity> mImageMapper = new EntityJsonMapper<>(new Gson());
    private ImageEntity mSourceImage;
    private List<ImageEntity> mResolutions;

    public ImageEntity getSourceImage() {
        if (mSourceImage == null) {
            mSourceImage = mImageMapper.transformEntity(source.toString(), ImageEntity.class);
        }
        return mSourceImage;
    }

    public List<ImageEntity> getResolutions() {
        if (mResolutions == null) {
            mResolutions = mImageMapper.transformEntityCollection(resolutions.toString(),
                                                                  ImageEntity[].class);
        }
        return mResolutions;
    }
}
