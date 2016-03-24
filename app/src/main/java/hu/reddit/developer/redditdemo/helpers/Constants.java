package hu.reddit.developer.redditdemo.helpers;

/**
 * Created by LargerLife on 22/06/15.
 */
public class Constants {
    //Intent and Broadcast Receiver related constants.
    public static final String PACKAGE_NAME = "hu.reddit.developer.redditdemo";
    public static final String FETCH_DATA_ACTION = PACKAGE_NAME + ".FETCH_REDDIT_ACTION";
    public static final String FETCH_DATA_CATEGORY = PACKAGE_NAME + ".FETCH_DEFAULT_CAT";
    public static final String BROADCAST_DATA_ACTION = PACKAGE_NAME + ".BROADCAST_RESULT";
    public static final String BROADCAST_EXTRA_ERROR = PACKAGE_NAME + ".BROADCAST_ERROR";

    //Networking related constants
    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    public static final String REDDIT_SERVER_ENDPOINT = "https://www.reddit.com";
    public static final String DEFAULT_SUBREDDIT = "aww";

    public static final String KEY_PARAM_SUBREDDIT = "subreddit";
}
