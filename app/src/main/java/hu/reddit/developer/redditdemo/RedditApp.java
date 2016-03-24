package hu.reddit.developer.redditdemo;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import hu.reddit.developer.data.RedditEntity;
import hu.reddit.developer.redditdemo.helpers.Constants;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LargerLife on 26/04/15.
 */
public class RedditApp extends Application {
    static final String TAG = "YambaApp";
    final List<RedditEntity> mEntries = new ArrayList<>(40);
    private RecyclerView.AdapterDataObserver mListingObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    public void addEntriy(final RedditEntity reddit) {
        long ts = new Double(reddit.created).longValue();
        Date createdDate = new Date(ts);
        String createdAt =
              SimpleDateFormat.getDateTimeInstance().format(createdDate);

        Log.d("addStatus", String.format("@%s at %s", reddit.author, createdAt));
        mEntries.add(reddit);
        if (mListingObserver != null) {
            mListingObserver.onItemRangeInserted(0, 1);
        }
    }

    public void clearEntries() {
        int size = mEntries.size();
        mEntries.clear();
        if (mListingObserver != null) {
            mListingObserver.onItemRangeRemoved(0, size);
        }
    }

    public static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, Constants.DISK_CACHE_SIZE);
        client.setCache(cache);
        return client;
    }

    public List<RedditEntity> getEntries() {
        return mEntries;
    }

    public void setEntries(List<RedditEntity> entities) {
        mEntries.addAll(entities);
        if (mListingObserver != null) {
            mListingObserver.onItemRangeInserted(0, entities.size());
        }
    }

    public RedditApp setListingObserver(
          final RecyclerView.AdapterDataObserver listingObserver) {
        mListingObserver = listingObserver;
        return this;
    }
}
