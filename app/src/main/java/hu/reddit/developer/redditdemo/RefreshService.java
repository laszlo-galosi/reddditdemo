package hu.reddit.developer.redditdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import hu.reddit.developer.data.RedditEntity;
import java.util.List;

/**
 * Created by LargerLife on 26/04/15.
 */
public class RefreshService extends IntentService {

    static final String TAG = "RefreshService";

    public RefreshService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, ".onHandleIntent");
        RedditApp app = (RedditApp) getApplication();
        try {
            app.clearEntries();
            List<RedditEntity> timeline = app.getEntries();
            //Collections.sort(timeline, new StatusComparator());
            app.setEntries(timeline);
        } catch (Exception e) {
            Log.e(TAG, "Cannot retrieve user timeline:" + twitter.getSelf().getName(), e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ".onDestroy");
    }
}
