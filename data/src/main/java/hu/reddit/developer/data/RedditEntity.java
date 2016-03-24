package hu.reddit.developer.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RedditEntity extends BasicEntity {

    public String id; // "4bjjtv",

    public String title; // "Puppy warming its paws",

    public String subreddit_id; // "t5_2qh1o",

    public String thumbnail;

    public String subreddit; // "aww",

    public String author; // "formight",

    public String selftext; // "",

    public JsonObject preview;

    public int num_comments; // 311,

    public int score; // 6079,

    public String url; // "https://i.imgur.com/pTmZRMy.jpg",

    public String name; // "t3_4bjjtv",

    public double created; // 1458711539.0,

    public double created_utc; // 1458682739.0,

    //public String from_kind; // null,
    //public String domain; // "i.imgur.com",
    //public String banned_by; // null,
    //public JsonObject media_embed; // {},
    // public String selftext_html; // null,
    //public JsonObject secure_media; // null,
    //public String link_flair_text; // null,
    //public String likes; // null,
    //public String suggested_sort; // null,
    //public JsonArray user_reports; // [],
    //public int gilded; // 0,
    //public boolean archived; // false,
    //public boolean clicked; // false,
    //public String report_reasons; // null,
    //public JsonObject media; // null,
    //public String approved_by; // null,
    //public boolean over_18; // false,
    //public boolean hidden; // false,
    //public boolean hide_score; // false,
    //public boolean edited; // false,
    //public String link_flair_css_class; // null,
    //public String author_flair_css_class; // null,
    //public int downs; // 0,
    //public JsonObject secure_media_embed; // {},
    //public boolean saved; // false,
    //public String removal_reason; // null,
    //public String post_hint; // "image",
    //public boolean stickied; // false,
    //public String from; // null,
    //public boolean is_self; // false,
    //public String from_id; // null,
    //public String permalink;// "/r/aww/comments/4bjjtv/puppy_warming_its_paws/",
    //public boolean locked; // false,
    //public String author_flair_text; // null,
    //public boolean quarantine; // false,
    //public String distinguished; // null,
    //public JsonArray mod_reports; // [],
    //public boolean visited; // false,
    //public String num_reports; // null,
    //public int ups; // 6079

    private PreviewEntity mPreview;
    private List<ImageDataEntity> mImages;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RedditEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", thumbnail='").append(thumbnail).append('\'');
        sb.append(", subreddit_id='").append(subreddit_id).append('\'');
        //sb.append(", media_embed=").append(media_embed);
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

    public static RedditEntity deserialize(final String jsonString, final JsonParser jsonParser) {
        RedditEntity entity = new RedditEntity();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);

        entity.id = JsonSerializer.getAsChecked("id", jsonObject, String.class);
        entity.title = JsonSerializer.getAsChecked("title", jsonObject, String.class);
        entity.subreddit_id = JsonSerializer.getAsChecked("subreddit_id", jsonObject, String.class);
        entity.thumbnail = JsonSerializer.getAsChecked("thumbnail", jsonObject, String.class);
        entity.subreddit = JsonSerializer.getAsChecked("subreddit", jsonObject, String.class);
        entity.author = JsonSerializer.getAsChecked("author", jsonObject, String.class);
        entity.selftext = JsonSerializer.getAsChecked("selftext", jsonObject, String.class);
        entity.preview = JsonSerializer.getAsChecked("preview", jsonObject, JsonObject.class);
        entity.num_comments =
              JsonSerializer.getAsChecked("num_comments", jsonObject, Integer.class);
        entity.score = JsonSerializer.getAsChecked("score", jsonObject, Integer.class);
        entity.url = JsonSerializer.getAsChecked("url", jsonObject, String.class);
        entity.name = JsonSerializer.getAsChecked("name", jsonObject, String.class);
        entity.created = JsonSerializer.getAsChecked("created", jsonObject, Double.class);
        entity.created_utc = JsonSerializer.getAsChecked("created_utc", jsonObject, Double.class);

        return entity;
    }

    public PreviewEntity getPreview() {
        if (mPreview == null) {
            mPreview = (PreviewEntity) EntityJsonMapperFactory
                  .getInstance().create(PreviewEntity.class)
                  .transformEntity(preview.toString(), PreviewEntity.class);
        }
        return mPreview;
    }

    public List<ImageDataEntity> getImageData() {
        if (mImages == null && preview.has("images")) {
            JsonElement imageData = preview.get("images");
            mImages = EntityJsonMapperFactory
                  .getInstance().create(ImageDataEntity.class)
                  .transformEntityCollection(imageData.toString(), ImageDataEntity.class);
        }
        return mImages;
    }
}
