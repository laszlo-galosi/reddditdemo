package hu.reddit.developer.redditdemo.network;

import hu.reddit.developer.data.BasicEntity;
import hu.reddit.developer.data.RedditDataEntity;
import java.util.Map;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by László Gálosi on 23/03/16
 */
public interface RedditService<T extends BasicEntity> {

    @GET("/r/{subreddit}") @Headers({ "Content-Type: application/json; charset=utf-8" })
    Observable<RedditDataEntity> getSub(@Path("subreddit") String subbredit,
          @QueryMap Map<String, Object> params);
}
