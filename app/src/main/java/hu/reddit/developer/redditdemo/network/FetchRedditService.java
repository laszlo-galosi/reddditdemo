package hu.reddit.developer.redditdemo.network;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import hu.reddit.developer.data.EntityJsonMapperFactory;
import hu.reddit.developer.data.RedditDataEntity;
import hu.reddit.developer.data.RedditEntity;
import hu.reddit.developer.data.RedditListingDataEntity;
import hu.reddit.developer.redditdemo.R;
import hu.reddit.developer.redditdemo.RedditApp;
import hu.reddit.developer.redditdemo.helpers.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import trikita.log.Log;

/**
 * Created by LargerLife on 22/06/15.
 */
public class FetchRedditService extends IntentService {

    static final String TAG = "FetchRedditService";

    private RetroServiceExecutor mRetroServiceExecutor;

    public static ArrayList<RedditEntity> extractRedditsOf(final RedditDataEntity rootEntity)
          throws Exception {

        RedditListingDataEntity listingDataEntity =
              (RedditListingDataEntity) rootEntity.getData(RedditListingDataEntity.class);
        List<RedditDataEntity> children = listingDataEntity.getChildren();
        int len = children.size();
        ArrayList<RedditEntity> redditEntities = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            RedditEntity redditEntity = (RedditEntity) children.get(i).getData(RedditEntity.class);
            redditEntities.add(redditEntity);
        }
        return redditEntities;
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FetchRedditService() {
        super(TAG);
    }

    /**
     * The specified intent is called from an Activity, to connect to OpenWeather API.
     * Retrieves the weather data converts to object model, and sorts the collection of
     * WeatherLocation objects to find the nearest from the center point passed in an extra
     * intent parameter.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (!intent.getAction().equals(Constants.FETCH_DATA_ACTION)) {
            Log.e("Invalid intent action", intent.getAction());
            return;
        }
        Log.w("onHandleIntent ", intent);
        Intent broadCastIntent = new Intent(Constants.BROADCAST_DATA_ACTION);
        broadCastIntent.putExtra(Constants.KEY_PARAM_SUBREDDIT, Constants.DEFAULT_SUBREDDIT);
        //handleWithUrlConnection(broadCastIntent);
        handleWithRetroServiceExecutor(broadCastIntent);
    }

    private void handleWithRetroServiceExecutor(final Intent broadCastIntent) {
        Log.d("handleWithRetroServiceExecutor");
        RedditApp app = (RedditApp) getApplication();
        if (mRetroServiceExecutor == null) {
            mRetroServiceExecutor = new RetroServiceExecutor(app);
        }
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put(Constants.KEY_PARAM_SUBREDDIT, Constants.DEFAULT_SUBREDDIT);
        mRetroServiceExecutor.executeGetSub(new RedditDataSubscriber(broadCastIntent), queryMap);
    }

    private void handleWithUrlConnection(final Intent broadCastIntent) {
        try {
            ArrayList<RedditEntity> redditsFromJson = extractRedditsFromJson(loadUrl());
            Log.d("handleWithUrlConnection");
            RedditApp app = (RedditApp) getApplication();
            app.clearEntries();
            app.setEntries(redditsFromJson);
            //mBroadCastIntent.putExtra(Constants.BROADCAST_EXTRA_NEAREST, nearest);
            Log.d("Broadcasting data ", broadCastIntent);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(broadCastIntent);
        } catch (Exception e) {
            Log.e("Error while parsing API response:", e.getMessage(), e);
            broadCastIntent.putExtra(Constants.BROADCAST_EXTRA_ERROR,
                                     R.string.snack_general_error);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(broadCastIntent);
        }
    }

    public static ArrayList<RedditEntity> extractRedditsFromJson(String jsonStr) throws Exception {
        if (jsonStr == null) {
            return new ArrayList<>(0);
        }
        RedditDataEntity mainEntity = (RedditDataEntity) EntityJsonMapperFactory
              .getInstance()
              .create(RedditDataEntity.class).transformEntity(jsonStr, RedditDataEntity.class);

        RedditListingDataEntity listingDataEntity =
              (RedditListingDataEntity) mainEntity.getData(RedditListingDataEntity.class);
        List<RedditDataEntity> children = listingDataEntity.getChildren();
        int len = children.size();
        ArrayList<RedditEntity> redditEntities = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            redditEntities.add((RedditEntity) children.get(i).getData(RedditEntity.class));
        }
        return redditEntities;
    }

    private String loadUrl() throws Exception {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String listingJsonStr = null;
        try {

            String urlString = String.format("%s/r/%s.json",
                                             Constants.REDDIT_SERVER_ENDPOINT,
                                             Constants.DEFAULT_SUBREDDIT);
            Uri builtUri = Uri.parse(urlString).buildUpon()
                              .build();

            URL url = new URL(builtUri.toString());
            Log.d("Url ", builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            return buffer.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Error closing stream", e);
                }
            }
        }
    }

    public class RedditDataSubscriber extends DefaultSubscriber<RedditDataEntity> {

        Intent mBroadCastIntent;

        public RedditDataSubscriber(final Intent broadCastIntent) {
            this.mBroadCastIntent = broadCastIntent;
        }

        @Override public void onCompleted() {
            Log.d("Broadcasting data ", mBroadCastIntent);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(mBroadCastIntent);
        }

        @Override public void onError(Throwable e) {
            Log.e("Error fetching data  sending error broadcast", mBroadCastIntent, e);
            //sending vroadcast about the error.
            mBroadCastIntent.putExtra(Constants.BROADCAST_EXTRA_ERROR,
                                      R.string.snack_general_error);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(mBroadCastIntent);
        }

        @Override public void onNext(RedditDataEntity rootEntity) {
            try {
                ArrayList<RedditEntity> redditsFromJson = extractRedditsOf(rootEntity);
                RedditApp app = (RedditApp) getApplication();
                app.clearEntries();
                app.setEntries(redditsFromJson);
            } catch (Exception e) {
                onError(e);
            }
        }
    }
}
