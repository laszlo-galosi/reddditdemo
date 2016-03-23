package hu.reddit.developer.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditEntity extends BasicEntity {
    //@SerializedName("secure_media") public JsonObject secure_media; // null,

    //@SerializedName("link_flair_text") public String link_flair_text; // null,

    @SerializedName("id") public String id; // "4bjjtv",

    @SerializedName("title") public String title; // "Puppy warming its paws",

    @SerializedName("subreddit_id") public String subreddit_id; // "t5_2qh1o",

    @SerializedName("thumbnail") public String thumbnail;
    // "http://b.thumbs.redditmedia.com/tMJCXfTNx6ZZEPIuOKM2qGrLJm5VCYubLru0_uFSn-o.jpg",

    @SerializedName("domain") public String domain; // "i.imgur.com",

    @SerializedName("banned_by") public String banned_by; // null,

    @SerializedName("media_embed") public JsonObject media_embed; // {},

    @SerializedName("subreddit") public String subreddit; // "aww",

    // @SerializedName("selftext_html") public String selftext_html; // null,

    @SerializedName("selftext") public String selftext; // "",

    //@SerializedName("likes") public String likes; // null,

    //@SerializedName("suggested_sort") public String suggested_sort; // null,

    @SerializedName("user_reports") public JsonArray user_reports; // [],

    //@SerializedName("from_kind") public String from_kind; // null,
    @SerializedName("gilded") public int gilded; // 0,

    @SerializedName("archived") public boolean archived; // false,

    @SerializedName("clicked") public boolean clicked; // false,

    //@SerializedName("report_reasons") public String report_reasons; // null,

    @SerializedName("author") public String author; // "formight",

    //@SerializedName("media") public JsonObject media; // null,

    @SerializedName("score") public int score; // 6079,

    //@SerializedName("approved_by") public String approved_by; // null,

    @SerializedName("over_18") public boolean over_18; // false,

    @SerializedName("hidden") public boolean hidden; // false,

    @SerializedName("preview") public JsonObject preview; // null,

    @SerializedName("num_comments") public int num_comments; // 311,

    @SerializedName("url") public String url; // "https://i.imgur.com/pTmZRMy.jpg",

    @SerializedName("hide_score") public boolean hide_score; // false,

    @SerializedName("edited") public boolean edited; // false,

    //@SerializedName("link_flair_css_class") public String link_flair_css_class; // null,

    //@SerializedName("author_flair_css_class") public String author_flair_css_class; // null,

    @SerializedName("downs") public int downs; // 0,

    //@SerializedName("secure_media_embed") public JsonObject secure_media_embed; // {},

    @SerializedName("saved") public boolean saved; // false,

    //@SerializedName("removal_reason") public String removal_reason; // null,

    @SerializedName("post_hint") public String post_hint; // "image",

    @SerializedName("stickied") public boolean stickied; // false,

    //@SerializedName("from") public String from; // null,

    @SerializedName("is_self") public boolean is_self; // false,

    //@SerializedName("from_id") public String from_id; // null,

    @SerializedName("permalink") public String permalink;
    // "/r/aww/comments/4bjjtv/puppy_warming_its_paws/",

    @SerializedName("locked") public boolean locked; // false,

    @SerializedName("name") public String name; // "t3_4bjjtv",

    @SerializedName("created") public double created; // 1458711539.0,

    //@SerializedName("author_flair_text") public String author_flair_text; // null,

    @SerializedName("quarantine") public boolean quarantine; // false,

    @SerializedName("created_utc") public double created_utc; // 1458682739.0,

    //@SerializedName("distinguished") public String distinguished; // null,

    @SerializedName("mod_reports") public JsonArray mod_reports; // [],

    @SerializedName("visited") public boolean visited; // false,

    //@SerializedName("num_reports") public String num_reports; // null,

    @SerializedName("ups") public int ups; // 6079

    private PreviewEntity mPreview;
    private Gson gson = new Gson();
    private EntityJsonMapper<PreviewEntity> mPreviewMapper = new EntityJsonMapper<>(gson);
    private EntityJsonMapper<ImageIdEntity> mImageMapper = new EntityJsonMapper<>(gson);
    private List<ImageIdEntity> mImages;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", thumbnail='").append(thumbnail).append('\'');
        sb.append(", subreddit_id='").append(subreddit_id).append('\'');
        sb.append(", media_embed=").append(media_embed);
        sb.append(", subreddit='").append(subreddit).append('\'');
        sb.append(", selftext='").append(selftext).append('\'');
        sb.append(", author='").append(author).append('\'');
        //sb.append(", media=").append(media);
        sb.append(", score=").append(score);
        sb.append(", preview=").append(preview);
        sb.append(", num_comments=").append(num_comments);
        sb.append(", url='").append(url).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", created=").append(created);
        sb.append(", created_utc=").append(created_utc);
        sb.append('}');
        return sb.toString();
    }

    public PreviewEntity getPreview() {
        if (mPreview == null) {
            mPreview = mPreviewMapper.transformEntity(preview.toString(), PreviewEntity.class);
        }
        return mPreview;
    }

    public List<ImageIdEntity> getImages() {
        if (mImages == null && getPreview() != null) {
            mImages = mImageMapper.transformEntityCollection(getPreview().images.toString(),
                                                             ImageIdEntity[].class);
        }
        return mImages;
    }
}
