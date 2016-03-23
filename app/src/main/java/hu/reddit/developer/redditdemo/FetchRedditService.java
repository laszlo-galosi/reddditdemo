package hu.reddit.developer.redditdemo;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import com.google.gson.Gson;
import hu.reddit.developer.data.EntityJsonMapper;
import hu.reddit.developer.data.RedditDataEntity;
import hu.reddit.developer.data.RedditEntity;
import hu.reddit.developer.data.RedditListingDataEntity;
import hu.reddit.developer.data.RedditListingEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import trikita.log.Log;

/**
 * Created by LargerLife on 22/06/15.
 */
public class FetchRedditService extends IntentService {

    static final String TAG = "FetchRedditService";
    private static Gson gson = new Gson();
    private static EntityJsonMapper<RedditListingEntity> sLisingMapper =
          new EntityJsonMapper<>(gson);
    private static EntityJsonMapper<RedditDataEntity> sDataMapper = new EntityJsonMapper<>(gson);

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
        Log.w("onHandleIntent ", intent);
        if (!intent.getAction().equals(Constants.FETCH_DATA_ACTION)) {
            Log.e("Invalid intent action", intent.getAction());
            return;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String weatherJsonStr = null;
        Intent broadCastIntent = new Intent(Constants.BROADCAST_DATA_ACTION);
        try {
            Uri builtUri = Uri.parse(Constants.REDDIT_BASE_URL).buildUpon()
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
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            weatherJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("Error fetching weather data sending empty broadcast", broadCastIntent, e);
            //sending vroadcast about the error.
            broadCastIntent.putExtra(Constants.BROADCAST_EXTRA_ERROR,
                                     R.string.snack_error_fetch_data);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(broadCastIntent);
            return;
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

        try {
            ArrayList<RedditEntity> redditsFromJson = extractRedditsFromJson(weatherJsonStr);
            Log.d("Broadcasting data ", intent);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(broadCastIntent);

            //Log.d("Sorting results according the distance from ", tappedLocation.toString());
            //Collections.sort(redditsFromJson, new LocationComparator(tappedLocation));
            //WeatherLocation nearest = redditsFromJson.get(0);

            RedditApp app = (RedditApp) getApplication();
            app.clearEntries();
            app.setEntries(redditsFromJson);
            //broadCastIntent.putExtra(Constants.BROADCAST_EXTRA_NEAREST, nearest);
            Log.d("Broadcasting data ", intent);
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .sendBroadcast(broadCastIntent);
        } catch (JSONException e) {
            Log.e("Error while parsing API response:", e.getMessage(), e);
        }
    }

    public static ArrayList<RedditEntity> extractRedditsFromJson(String jsonStr)
          throws JSONException {
        RedditDataEntity mainEntity =
              sDataMapper.transformEntity(jsonStr, RedditDataEntity.class);

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
}
