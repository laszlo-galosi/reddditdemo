package hu.reddit.developer.redditdemo;

import java.text.DecimalFormat;

/**
 * Created by LargerLife on 22/06/15.
 */
public class Constants {
    //Intent and Broadcast Receiver related constants.
    public static final String PACKAGE_NAME = "hu.reddit.developer.redditdemo";
    public static final String FETCH_DATA_ACTION = PACKAGE_NAME + ".FETCH";
    public static final String FETCH_DATA_CATEGORY = PACKAGE_NAME + ".FETCH_DEFAULT";
    public static final String BROADCAST_DATA_ACTION = PACKAGE_NAME + ".BROADCAST";
    public static final String BROADCAST_EXTRA_NEAREST = PACKAGE_NAME + ".NEAREST";
    public static final String BROADCAST_EXTRA_ERROR = PACKAGE_NAME + ".ERROR";

    /**
     * Maximum number of places within the specified location for the openweathermap request.
     */
    public static final int MAX_CLUSTER_COUNT = 5;
    /**
     * OpenWeatherMap request which returns a json string containing places in the vicinity of the
     * latitude and longitude specified by 'lat' and 'lon' parameter in the query string,
     * limited by the 'cnt' parameter.
     */
    public static final String REDDIT_BASE_URL =
          "https://www.reddit.com/r/aww.json";

    //Map related constants.
    static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
}
