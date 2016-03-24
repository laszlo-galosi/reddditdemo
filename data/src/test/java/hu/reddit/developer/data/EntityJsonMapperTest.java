package hu.reddit.developer.data;

import com.google.gson.JsonArray;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;

/**
 * Created by László Gálosi on 23/03/16
 */
public class EntityJsonMapperTest extends TestCase {

    public static String SAMPLE_JSON_CHILD;
    public static String SAMPLE_JSON_ROOT;

    public static String SAMPLE_JSON_REDDIT;

    static {
        SAMPLE_JSON_CHILD = "{\"modhash\": \"\", \"children\": "
          + "[{\"kind\": \"t3\", \"data\": {\"domain\": \"i.imgur.com\", \"banned_by\": null, "
          + "\"media_embed\": {}, \"subreddit\": \"aww\", \"selftext_html\": null, "
          + "\"selftext\": \"\", \"likes\": null, \"suggested_sort\": null, \"user_reports\": "
          + "[], \"secure_media\": null, \"link_flair_text\": null, \"id\": \"4bjjtv\", "
          + "\"from_kind\": null, \"gilded\": 0, \"archived\": false, \"clicked\": false, "
          + "\"report_reasons\": null, \"author\": \"formight\", \"media\": null, \"score\": "
          + "6079, \"approved_by\": null, \"over_18\": false, \"hidden\": false, \"preview\": "
          + "{\"images\": [{\"source\": {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s"
          + ".jpg?s=d61a3f9e869c01d615714d47e58918a9\", \"width\": 600, \"height\": 355}, "
          + "\"resolutions\": [{\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=fa79fe7aec789b41e4db5f15ff431e51\","
          + " \"width\": 108, \"height\": 63}, {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=57bba0de90472b7c5a39d4c62e03a389\","
          + " \"width\": 216, \"height\": 127}, {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=08091559d6d9dc2f3d3f46edf533123b\","
          + " \"width\": 320, \"height\": 189}], \"variants\": {}, \"id\": "
          + "\"ymbmc3Ia7d6CeS7PaYNT0RslcICovVNXXOlnh1Uh_HU\"}]}, \"num_comments\": 311, "
          + "\"thumbnail\": \"http://b.thumbs.redditmedia"
          + ".com/tMJCXfTNx6ZZEPIuOKM2qGrLJm5VCYubLru0_uFSn-o.jpg\", \"subreddit_id\": "
          + "\"t5_2qh1o\", \"hide_score\": false, \"edited\": false, \"link_flair_css_class\": "
          + "null, \"author_flair_css_class\": null, \"downs\": 0, \"secure_media_embed\": {}, "
          + "\"saved\": false, \"removal_reason\": null, \"post_hint\": \"image\", "
          + "\"stickied\": false, \"from\": null, \"is_self\": false, \"from_id\": null, "
          + "\"permalink\": \"/r/aww/comments/4bjjtv/puppy_warming_its_paws/\", \"locked\": "
          + "false, \"name\": \"t3_4bjjtv\", \"created\": 1458711539.0, \"url\": \"https://i"
          + ".imgur.com/pTmZRMy.jpg\", \"author_flair_text\": null, \"quarantine\": false, "
          + "\"title\": \"Puppy warming its paws\", \"created_utc\": 1458682739.0, "
          + "\"distinguished\": null, \"mod_reports\": [], \"visited\": false, \"num_reports\":"
          + " null, \"ups\": 6079}}], \"after\": \"t3_4biscb\", \"before\": null}";

        SAMPLE_JSON_ROOT = "{\"kind\": \"Listing\", \"data\": "
          + "{\"modhash\": \"\", \"children\": [{\"kind\": \"t3\", \"data\": {\"domain\": \"i"
          + ".imgur.com\", \"banned_by\": null, \"media_embed\": {}, \"subreddit\": \"aww\", "
          + "\"selftext_html\": null, \"selftext\": \"\", \"likes\": null, \"suggested_sort\": "
          + "null, \"user_reports\": [], \"secure_media\": null, \"link_flair_text\": null, "
          + "\"id\": \"4bjjtv\", \"from_kind\": null, \"gilded\": 0, \"archived\": false, "
          + "\"clicked\": false, \"report_reasons\": null, \"author\": \"formight\", \"media\": "
          + "null, \"score\": 6079, \"approved_by\": null, \"over_18\": false, \"hidden\": false,"
          + " \"preview\": {\"images\": [{\"source\": {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s"
          + ".jpg?s=d61a3f9e869c01d615714d47e58918a9\", \"width\": 600, \"height\": 355}, "
          + "\"resolutions\": [{\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=fa79fe7aec789b41e4db5f15ff431e51\", "
          + "\"width\": 108, \"height\": 63}, {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=57bba0de90472b7c5a39d4c62e03a389\", "
          + "\"width\": 216, \"height\": 127}, {\"url\": \"https://i.redditmedia"
          + ".com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=08091559d6d9dc2f3d3f46edf533123b\", "
          + "\"width\": 320, \"height\": 189}], \"variants\": {}, \"id\": "
          + "\"ymbmc3Ia7d6CeS7PaYNT0RslcICovVNXXOlnh1Uh_HU\"}]}, \"num_comments\": 311, "
          + "\"thumbnail\": \"http://b.thumbs.redditmedia"
          + ".com/tMJCXfTNx6ZZEPIuOKM2qGrLJm5VCYubLru0_uFSn-o.jpg\", \"subreddit_id\": "
          + "\"t5_2qh1o\", \"hide_score\": false, \"edited\": false, \"link_flair_css_class\": "
          + "null, \"author_flair_css_class\": null, \"downs\": 0, \"secure_media_embed\": {}, "
          + "\"saved\": false, \"removal_reason\": null, \"post_hint\": \"image\", \"stickied\": "
          + "false, \"from\": null, \"is_self\": false, \"from_id\": null, \"permalink\": "
          + "\"/r/aww/comments/4bjjtv/puppy_warming_its_paws/\", \"locked\": false, \"name\": "
          + "\"t3_4bjjtv\", \"created\": 1458711539.0, \"url\": \"https://i.imgur.com/pTmZRMy"
          + ".jpg\", \"author_flair_text\": null, \"quarantine\": false, \"title\": \"Puppy "
          + "warming its paws\", \"created_utc\": 1458682739.0, \"distinguished\": null, "
          + "\"mod_reports\": [], \"visited\": false, \"num_reports\": null, \"ups\": 6079}}, "
          + "{\"kind\": \"t3\", \"data\": {\"domain\": \"imgur.com\", \"banned_by\": null, "
          + "\"media_embed\": {\"content\": \"&lt;iframe class=\\\"embedly-embed\\\" "
          + "src=\\\"//cdn.embedly.com/widgets/media.html?src=%2F%2Fimgur"
          + ".com%2Fa%2FDRHKN%2Fembed&amp;url=http%3A%2F%2Fimgur.com%2Fa%2FDRHKN&amp;"
          + "image=http%3A%2F%2Fi.imgur.com%2FLoBOOUb.jpg%3Ffb&amp;"
          + "key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=text%2Fhtml&amp;schema=imgur\\\" "
          + "width=\\\"550\\\" height=\\\"550\\\" scrolling=\\\"no\\\" frameborder=\\\"0\\\" "
          + "allowfullscreen&gt;&lt;/iframe&gt;\", \"width\": 550, \"scrolling\": false, "
          + "\"height\": 550}, \"subreddit\": \"aww\", \"selftext_html\": null, \"selftext\": "
          + "\"\", \"likes\": null, \"suggested_sort\": null, \"user_reports\": [], "
          + "\"secure_media\": {\"oembed\": {\"provider_url\": \"http://imgur.com\", "
          + "\"description\": \"Imgur: The most awesome images on the Internet.\", \"title\": "
          + "\"Shasta's Pack\", \"type\": \"rich\", \"thumbnail_width\": 480, \"height\": 550, "
          + "\"width\": 550, \"html\": \"&lt;iframe class=\\\"embedly-embed\\\" "
          + "src=\\\"https://cdn.embedly.com/widgets/media.html?src=%2F%2Fimgur"
          + ".com%2Fa%2FDRHKN%2Fembed&amp;url=http%3A%2F%2Fimgur.com%2Fa%2FDRHKN&amp;"
          + "image=http%3A%2F%2Fi.imgur.com%2FLoBOOUb.jpg%3Ffb&amp;"
          + "key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=text%2Fhtml&amp;schema=imgur\\\" "
          + "width=\\\"550\\\" height=\\\"550\\\" scrolling=\\\"no\\\" frameborder=\\\"0\\\" "
          + "allowfullscreen&gt;&lt;/iframe&gt;\", \"version\": \"1.0\", \"provider_name\": "
          + "\"Imgur\", \"thumbnail_url\": \"https://i.embed.ly/1/image?url=http%3A%2F%2Fi.imgur"
          + ".com%2FLoBOOUb.jpg%3Ffb&amp;key=b1e305db91cf4aa5a86b732cc9fffceb\", "
          + "\"thumbnail_height\": 270}, \"type\": \"imgur.com\"}, \"link_flair_text\": null, "
          + "\"id\": \"4bjewv\", \"from_kind\": null, \"gilded\": 0, \"archived\": false, "
          + "\"clicked\": false, \"report_reasons\": null, \"author\": \"seanlee2013\", "
          + "\"media\": {\"oembed\": {\"provider_url\": \"http://imgur.com\", \"description\": "
          + "\"Imgur: The most awesome images on the Internet.\", \"title\": \"Shasta's Pack\", "
          + "\"type\": \"rich\", \"thumbnail_width\": 480, \"height\": 550, \"width\": 550, "
          + "\"html\": \"&lt;iframe class=\\\"embedly-embed\\\" src=\\\"//cdn.embedly"
          + ".com/widgets/media.html?src=%2F%2Fimgur.com%2Fa%2FDRHKN%2Fembed&amp;"
          + "url=http%3A%2F%2Fimgur.com%2Fa%2FDRHKN&amp;image=http%3A%2F%2Fi.imgur.com%2FLoBOOUb"
          + ".jpg%3Ffb&amp;key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=text%2Fhtml&amp;"
          + "schema=imgur\\\" width=\\\"550\\\" height=\\\"550\\\" scrolling=\\\"no\\\" "
          + "frameborder=\\\"0\\\" allowfullscreen&gt;&lt;/iframe&gt;\", \"version\": \"1.0\", "
          + "\"provider_name\": \"Imgur\", \"thumbnail_url\": \"http://i.imgur.com/LoBOOUb"
          + ".jpg?fb\", \"thumbnail_height\": 270}, \"type\": \"imgur.com\"}, \"score\": 4328, "
          + "\"approved_by\": null, \"over_18\": false, \"hidden\": false, \"preview\": "
          + "{\"images\": [{\"source\": {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4"
          + ".jpg?s=a1c76c31c7a237baab107f1614f76355\", \"width\": 1600, \"height\": 1200}, "
          + "\"resolutions\": [{\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=244c95c67127e4fc1e6e91a1f1db9e24\", "
          + "\"width\": 108, \"height\": 81}, {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=cafd0fc7beb89df7def8f9015c93ad7c\", "
          + "\"width\": 216, \"height\": 162}, {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=1f3b6d0ce9a5cccebfc455296a091172\", "
          + "\"width\": 320, \"height\": 240}, {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;s=3ca455d7c6d5ff1e93c3096187d641ef\", "
          + "\"width\": 640, \"height\": 480}, {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=960&amp;s=adb273f12b835fc2c8f98e5fb0a8cb75\", "
          + "\"width\": 960, \"height\": 720}, {\"url\": \"https://i.redditmedia"
          + ".com/Yh4Y0K1GK-YLlUgYIsjRoHmyRzV3oXl2Sr2k9Rfg_Y4.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=1080&amp;s=6e87fd2ec890aab7357ce615570feb3a\", "
          + "\"width\": 1080, \"height\": 810}], \"variants\": {}, \"id\": "
          + "\"uCixHyQSOv5tpzI3-f0K90uhJ52LFXHuyTkUWN_735Q\"}]}, \"num_comments\": 172, "
          + "\"thumbnail\": \"http://a.thumbs.redditmedia"
          + ".com/EU-d6BuRWeKAyQsCaak4w9F4QspIGCp0hUJIg-Juoa8.jpg\", \"subreddit_id\": "
          + "\"t5_2qh1o\", \"hide_score\": false, \"edited\": false, \"link_flair_css_class\": "
          + "null, \"author_flair_css_class\": null, \"downs\": 0, \"secure_media_embed\": "
          + "{\"content\": \"&lt;iframe class=\\\"embedly-embed\\\" src=\\\"https://cdn.embedly"
          + ".com/widgets/media.html?src=%2F%2Fimgur.com%2Fa%2FDRHKN%2Fembed&amp;"
          + "url=http%3A%2F%2Fimgur.com%2Fa%2FDRHKN&amp;image=http%3A%2F%2Fi.imgur.com%2FLoBOOUb"
          + ".jpg%3Ffb&amp;key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=text%2Fhtml&amp;"
          + "schema=imgur\\\" width=\\\"550\\\" height=\\\"550\\\" scrolling=\\\"no\\\" "
          + "frameborder=\\\"0\\\" allowfullscreen&gt;&lt;/iframe&gt;\", \"width\": 550, "
          + "\"scrolling\": false, \"height\": 550}, \"saved\": false, \"removal_reason\": null, "
          + "\"post_hint\": \"link\", \"stickied\": false, \"from\": null, \"is_self\": false, "
          + "\"from_id\": null, \"permalink\": "
          + "\"/r/aww/comments/4bjewv/our_dog_walker_takes_some_pretty_cool_pictures_of/\", "
          + "\"locked\": false, \"name\": \"t3_4bjewv\", \"created\": 1458709711.0, \"url\": "
          + "\"http://imgur.com/a/DRHKN\", \"author_flair_text\": null, \"quarantine\": false, "
          + "\"title\": \"Our dog walker takes some pretty cool pictures of his \\\"pack\\\" "
          + "throughout the day.\", \"created_utc\": 1458680911.0, \"distinguished\": null, "
          + "\"mod_reports\": [], \"visited\": false, \"num_reports\": null, \"ups\": 4328}}, "
          + "{\"kind\": \"t3\", \"data\": {\"domain\": \"i.imgur.com\", \"banned_by\": null, "
          + "\"media_embed\": {\"content\": \"&lt;iframe class=\\\"embedly-embed\\\" "
          + "src=\\\"//cdn.embedly.com/widgets/media.html?src=https%3A%2F%2Fi.imgur.com%2FlnRnDPk"
          + ".mp4&amp;src_secure=1&amp;url=http%3A%2F%2Fi.imgur.com%2FlnRnDPk.gifv&amp;"
          + "image=https%3A%2F%2Fi.imgur.com%2FlnRnDPkh.jpg&amp;"
          + "key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=video%2Fmp4&amp;schema=imgur\\\" "
          + "width=\\\"426\\\" height=\\\"426\\\" scrolling=\\\"no\\\" frameborder=\\\"0\\\" "
          + "allowfullscreen&gt;&lt;/iframe&gt;\", \"width\": 426, \"scrolling\": false, "
          + "\"height\": 426}, \"subreddit\": \"aww\", \"selftext_html\": null, \"selftext\": "
          + "\"\", \"likes\": null, \"suggested_sort\": null, \"user_reports\": [], "
          + "\"secure_media\": {\"type\": \"i.imgur.com\", \"oembed\": {\"provider_url\": "
          + "\"http://i.imgur.com\", \"description\": \"Imgur: The most awesome images on the "
          + "Internet.\", \"title\": \"I just love it snow much!\", \"thumbnail_width\": 426, "
          + "\"height\": 426, \"width\": 426, \"html\": \"&lt;iframe class=\\\"embedly-embed\\\" "
          + "src=\\\"https://cdn.embedly.com/widgets/media.html?src=https%3A%2F%2Fi.imgur"
          + ".com%2FlnRnDPk.mp4&amp;src_secure=1&amp;url=http%3A%2F%2Fi.imgur.com%2FlnRnDPk"
          + ".gifv&amp;image=https%3A%2F%2Fi.imgur.com%2FlnRnDPkh.jpg&amp;"
          + "key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=video%2Fmp4&amp;schema=imgur\\\" "
          + "width=\\\"426\\\" height=\\\"426\\\" scrolling=\\\"no\\\" frameborder=\\\"0\\\" "
          + "allowfullscreen&gt;&lt;/iframe&gt;\", \"version\": \"1.0\", \"provider_name\": "
          + "\"Imgur\", \"thumbnail_url\": \"https://i.embed.ly/1/image?url=https%3A%2F%2Fi.imgur"
          + ".com%2FlnRnDPkh.jpg&amp;key=b1e305db91cf4aa5a86b732cc9fffceb\", \"type\": \"video\","
          + " \"thumbnail_height\": 426}}, \"link_flair_text\": null, \"id\": \"4bi8wi\", "
          + "\"from_kind\": null, \"gilded\": 0, \"archived\": false, \"clicked\": false, "
          + "\"report_reasons\": null, \"author\": \"natsdorf\", \"media\": {\"type\": \"i.imgur"
          + ".com\", \"oembed\": {\"provider_url\": \"http://i.imgur.com\", \"description\": "
          + "\"Imgur: The most awesome images on the Internet.\", \"title\": \"I just love it "
          + "snow much!\", \"thumbnail_width\": 426, \"height\": 426, \"width\": 426, \"html\": "
          + "\"&lt;iframe class=\\\"embedly-embed\\\" src=\\\"//cdn.embedly.com/widgets/media"
          + ".html?src=https%3A%2F%2Fi.imgur.com%2FlnRnDPk.mp4&amp;src_secure=1&amp;"
          + "url=http%3A%2F%2Fi.imgur.com%2FlnRnDPk.gifv&amp;image=https%3A%2F%2Fi.imgur"
          + ".com%2FlnRnDPkh.jpg&amp;key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;"
          + "type=video%2Fmp4&amp;schema=imgur\\\" width=\\\"426\\\" height=\\\"426\\\" "
          + "scrolling=\\\"no\\\" frameborder=\\\"0\\\" allowfullscreen&gt;&lt;/iframe&gt;\", "
          + "\"version\": \"1.0\", \"provider_name\": \"Imgur\", \"thumbnail_url\": \"https://i"
          + ".imgur.com/lnRnDPkh.jpg\", \"type\": \"video\", \"thumbnail_height\": 426}}, "
          + "\"score\": 5801, \"approved_by\": null, \"over_18\": false, \"hidden\": false, "
          + "\"preview\": {\"images\": [{\"source\": {\"url\": \"https://i.redditmedia"
          + ".com/OOhb7CW4s3eVdwwgaL3W620UtIWrK3_HgO9xcKrvbRc"
          + ".jpg?s=e30f7c0b690f686f80c65f7d92393c89\", \"width\": 426, \"height\": 426}, "
          + "\"resolutions\": [{\"url\": \"https://i.redditmedia"
          + ".com/OOhb7CW4s3eVdwwgaL3W620UtIWrK3_HgO9xcKrvbRc.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=c1cf1f1d2065358fdf83cc1c9a2352c8\", "
          + "\"width\": 108, \"height\": 108}, {\"url\": \"https://i.redditmedia"
          + ".com/OOhb7CW4s3eVdwwgaL3W620UtIWrK3_HgO9xcKrvbRc.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=e0de624a18df07e5b71a3bb75e16dcb5\", "
          + "\"width\": 216, \"height\": 216}, {\"url\": \"https://i.redditmedia"
          + ".com/OOhb7CW4s3eVdwwgaL3W620UtIWrK3_HgO9xcKrvbRc.jpg?fit=crop&amp;"
          + "crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=1b5f413c49b822b5e74ece2cb38575c5\", "
          + "\"width\": 320, \"height\": 320}], \"variants\": {}, \"id\": "
          + "\"m2QSp50H1PWhak0hFCdlYW0-YxcYvwyOEo2o4mWIt9I\"}]}, \"num_comments\": 255, "
          + "\"thumbnail\": \"http://a.thumbs.redditmedia"
          + ".com/cPnREC3xemfE0RjR2Lt8yC-spBlR0PmdoEL6b3yxVj4.jpg\", \"subreddit_id\": "
          + "\"t5_2qh1o\", \"hide_score\": false, \"edited\": false, \"link_flair_css_class\": "
          + "null, \"author_flair_css_class\": null, \"downs\": 0, \"secure_media_embed\": "
          + "{\"content\": \"&lt;iframe class=\\\"embedly-embed\\\" src=\\\"https://cdn.embedly"
          + ".com/widgets/media.html?src=https%3A%2F%2Fi.imgur.com%2FlnRnDPk.mp4&amp;"
          + "src_secure=1&amp;url=http%3A%2F%2Fi.imgur.com%2FlnRnDPk.gifv&amp;"
          + "image=https%3A%2F%2Fi.imgur.com%2FlnRnDPkh.jpg&amp;"
          + "key=2aa3c4d5f3de4f5b9120b660ad850dc9&amp;type=video%2Fmp4&amp;schema=imgur\\\" "
          + "width=\\\"426\\\" height=\\\"426\\\" scrolling=\\\"no\\\" frameborder=\\\"0\\\" "
          + "allowfullscreen&gt;&lt;/iframe&gt;\", \"width\": 426, \"scrolling\": false, "
          + "\"height\": 426}, \"saved\": false, \"removal_reason\": null, \"post_hint\": "
          + "\"rich:video\", \"stickied\": false, \"from\": null, \"is_self\": false, "
          + "\"from_id\": null, \"permalink\": "
          + "\"/r/aww/comments/4bi8wi/i_just_love_it_snow_much/\", \"locked\": false, \"name\": "
          + "\"t3_4bi8wi\", \"created\": 1458694373.0, \"url\": \"http://i.imgur.com/lnRnDPk"
          + ".gifv\", \"author_flair_text\": null, \"quarantine\": false, \"title\": \"I just "
          + "love it snow much!\", \"created_utc\": 1458665573.0, \"distinguished\": null, "
          + "\"mod_reports\": [], \"visited\": false, \"num_reports\": null, \"ups\": 5801}} ], "
          + "\"after\": \"t3_4biscb\", \"before\": null}}";

        SAMPLE_JSON_REDDIT = "{\"domain\":\"imgur.com\",\"banned_by\":null,\"media_embed\":{},"
              + "\"subreddit\":\"aww\",\"selftext_html\":null,\"selftext\":\"\",\"likes\":null,"
              + "\"suggested_sort\":null,\"user_reports\":[],\"secure_media\":null,"
              + "\"link_flair_text\":null,\"id\":\"4bm6go\",\"from_kind\":null,\"gilded\":0,"
              + "\"archived\":false,\"clicked\":false,\"report_reasons\":null,"
              + "\"author\":\"slugsongs\",\"media\":null,\"score\":5996,\"approved_by\":null,"
              + "\"over_18\":false,\"hidden\":false,"
              + "\"preview\":{\"images\":[{\"source\":{\"url\":\"https://i.redditmedia"
              + ".com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?s=12d4d98acab9916ca9436f092f71f327\",\"width\":2448,\"height\":3264},"
              + "\"resolutions\":[{\"url\":\"https://i.redditmedia"
              + ".com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk.jpg?fit=crop&amp;"
              + "crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;"
              + "s=eaa700b71d9da87e5652a633d4ee4d83\",\"width\":108,\"height\":144},"
              + "{\"url\":\"https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;"
              + "s=da9f86be72f0efb99d149588ebabbe03\",\"width\":216,\"height\":288},"
              + "{\"url\":\"https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;"
              + "s=fa6da87937d9f13875ff30443522183d\",\"width\":320,\"height\":426},"
              + "{\"url\":\"https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;"
              + "s=bd9e62d268e017df7f79015e46b3dbd0\",\"width\":640,\"height\":853},"
              + "{\"url\":\"https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=960&amp;"
              + "s=e9e6d5a87acd5f00a345929c638dcad3\",\"width\":960,\"height\":1280},"
              + "{\"url\":\"https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
              + ".jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=1080&amp;"
              + "s=a19a9abf4a63c49e862846939f773e8b\",\"width\":1080,\"height\":1440}],"
              + "\"variants\":{},\"id\":\"h8tpwHG-WrNIpB5YHKS1_BCN426KXhur2HHU8KYFEq4\"}]},"
              + "\"num_comments\":812,\"thumbnail\":\"http://b.thumbs.redditmedia"
              + ".com/6_-Sbz0sQLJiCAb1NENnEgYYVIftCX3pUxpHGxj8jVc.jpg\","
              + "\"subreddit_id\":\"t5_2qh1o\",\"hide_score\":false,\"edited\":false,"
              + "\"link_flair_css_class\":null,\"author_flair_css_class\":null,\"downs\":0,"
              + "\"secure_media_embed\":{},\"saved\":false,\"removal_reason\":null,"
              + "\"post_hint\":\"link\",\"stickied\":false,\"from\":null,\"is_self\":false,"
              + "\"from_id\":null,"
              + "\"permalink\":\"/r/aww/comments/4bm6go"
              + "/i_had_to_chop_up_a_load_of_wood_he_wouldnt_leave/\",\"locked\":false,"
              + "\"name\":\"t3_4bm6go\",\"created\":1458763583.0,\"url\":\"http://imgur"
              + ".com/5nJNGTL\",\"author_flair_text\":null,\"quarantine\":false,\"title\":\"I had"
              + " to chop up a load of wood, he wouldn't leave my side but I think we've found a "
              + "solution...\",\"created_utc\":1458734783.0,\"distinguished\":null,"
              + "\"mod_reports\":[],\"visited\":false,\"num_reports\":null,\"ups\":5996}";
    }

    @Override public void setUp() throws Exception {
        super.setUp();
    }

    @Test public void test_TransformListingEntity_HappyCase() throws Exception {

        EntityJsonMapper<RedditDataEntity> dataMapper =
              EntityJsonMapperFactory.getInstance().create(RedditDataEntity.class);
        RedditDataEntity rootEntity =
              dataMapper.transformEntity(SAMPLE_JSON_ROOT, RedditDataEntity.class);

        RedditListingDataEntity listingDataEntity =
              (RedditListingDataEntity) rootEntity.getData(RedditListingDataEntity.class);
        assertNotNull(listingDataEntity);
        assertThat(listingDataEntity, isA(RedditListingDataEntity.class));
        assertThat(listingDataEntity.modhash, is(""));
        assertNotNull(listingDataEntity.children);
        List<RedditDataEntity> children = listingDataEntity.getChildren();
        assertNotNull(children);
        assertThat(children.size(), is(3));

        RedditEntity redditEntity = (RedditEntity) children.get(0).getData(RedditEntity.class);
        assertNotNull(redditEntity);
        assertThat(redditEntity.id, is("4bjjtv"));
        assertThat(redditEntity.title, is("Puppy warming its paws"));
        assertThat(redditEntity.name, is("t3_4bjjtv"));
        assertThat(redditEntity.created, is(1458711539.0));
        assertThat(redditEntity.thumbnail,
                   is("http://b.thumbs.redditmedia"
                            + ".com/tMJCXfTNx6ZZEPIuOKM2qGrLJm5VCYubLru0_uFSn-o.jpg"));
        assertNotNull(redditEntity.preview);
        List<ImageDataEntity> images = redditEntity.getImageData();
        assertNotNull(images);
        assertThat(images.size(), is(1));

        ImageDataEntity imageId = images.get(0);
        assertNotNull(imageId.source);

        ImageEntity sourceImage = imageId.getSourceImage();
        assertThat(sourceImage.url,
                   is("https://i.redditmedia.com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s"
                            + ".jpg?s=d61a3f9e869c01d615714d47e58918a9"));
        assertThat(sourceImage.width, is(600));
        assertThat(sourceImage.height, is(355));

        List<ImageEntity> resolutions = imageId.getResolutions();
        assertNotNull(resolutions);
        assertThat(resolutions.size(), is(3));

        EntityJsonMapper<RedditRootEntity>
              entityJsonMapper =
              EntityJsonMapperFactory.getInstance().create(RedditRootEntity.class);
        RedditRootEntity listingEntity =
              entityJsonMapper.transformEntity(SAMPLE_JSON_ROOT, RedditRootEntity.class);
        assertNotNull(listingEntity);
    }

    @Test public void test_TransformChildEntity() throws Exception {
        EntityJsonMapper<RedditListingDataEntity> entityJsonMapper =
              EntityJsonMapperFactory.getInstance().create(RedditListingDataEntity.class);
        RedditListingDataEntity childEntity =
              entityJsonMapper.transformEntity(SAMPLE_JSON_CHILD, RedditListingDataEntity.class);
        assertNotNull(childEntity);
        assertThat(childEntity, isA(RedditListingDataEntity.class));
        assertThat(childEntity.modhash, is(""));
        assertNotNull(childEntity.children);
        assertThat(childEntity.children, isA(JsonArray.class));
        List<RedditDataEntity> children = childEntity.getChildren();
        assertNotNull(children);
        assertThat(children.size(), is(1));

        RedditEntity redditEntity = (RedditEntity) children.get(0).getData(RedditEntity.class);
        assertNotNull(redditEntity);
        assertThat(redditEntity.id, is("4bjjtv"));
        assertThat(redditEntity.title, is("Puppy warming its paws"));
        assertThat(redditEntity.name, is("t3_4bjjtv"));
        assertThat(redditEntity.created, is(1458711539.0));
        assertThat(redditEntity.thumbnail,
                   is("http://b.thumbs.redditmedia"
                            + ".com/tMJCXfTNx6ZZEPIuOKM2qGrLJm5VCYubLru0_uFSn-o.jpg"));
        assertNotNull(redditEntity.preview);
        List<ImageDataEntity> images = redditEntity.getImageData();
        assertNotNull(images);
        assertThat(images.size(), is(1));

        ImageDataEntity imageId = images.get(0);
        assertNotNull(imageId.source);

        ImageEntity sourceImage = imageId.getSourceImage();
        assertThat(sourceImage.url,
                   is("https://i.redditmedia.com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s"
                            + ".jpg?s=d61a3f9e869c01d615714d47e58918a9"));
        assertThat(sourceImage.width, is(600));
        assertThat(sourceImage.height, is(355));

        List<ImageEntity> resolutions = imageId.getResolutions();
        assertNotNull(resolutions);
        assertThat(resolutions.size(), is(3));
    }

    @Test public void test_TransformRedditEntity_HappyCase()
          throws Exception {

        RedditEntity redditEntity =
              (RedditEntity) EntityJsonMapperFactory
                    .getInstance()
                    .create(RedditEntity.class)
                    .transformEntity(SAMPLE_JSON_REDDIT, RedditEntity.class);
        assertNotNull(redditEntity);
        assertThat(redditEntity.id, is("4bm6go"));
        assertThat(redditEntity.title,
                   is("I had to chop up a load of wood, he wouldn't leave my side but I think "
                            + "we've found a solution..."));
        assertThat(redditEntity.name, is("t3_4bm6go"));
        assertThat(redditEntity.created, is(1458763583.0));
        assertThat(redditEntity.thumbnail,
                   is("http://b.thumbs.redditmedia"
                            + ".com/6_-Sbz0sQLJiCAb1NENnEgYYVIftCX3pUxpHGxj8jVc.jpg"));
        assertNotNull(redditEntity.preview);
        List<ImageDataEntity> images = redditEntity.getImageData();
        assertNotNull(images);
        assertThat(images.size(), is(1));

        ImageDataEntity imageId = images.get(0);
        assertNotNull(imageId.source);

        ImageEntity sourceImage = imageId.getSourceImage();
        assertThat(sourceImage.url,
                   is("https://i.redditmedia.com/UCVCuwFyQfpX8d6KnKJLyL0C-UO8M_4O2opQbtnWPKk"
                            + ".jpg?s=12d4d98acab9916ca9436f092f71f327"));
        assertThat(sourceImage.width, is(2448));
        assertThat(sourceImage.height, is(3264));

        List<ImageEntity> resolutions = imageId.getResolutions();
        assertNotNull(resolutions);
        assertThat(resolutions.size(), is(6));
    }
}
