package hu.reddit.developer.redditdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import hu.reddit.developer.redditdemo.helpers.Constants;
import java.util.List;
import trikita.log.Log;

import static hu.reddit.developer.redditdemo.helpers.Constants.BROADCAST_DATA_ACTION;

/**
 * Created by László Gálosi on 17/03/16
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected RedditApp mApplication;
    @Bind(R.id.mainLayout) CoordinatorLayout mCoordinatorLayout;
    protected Toolbar mToolbar;

    private IntentFilter mReceiverIntentFilter;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onNewIntent(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getScreenTag(), "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(getScreenLayout());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getToolbarTitle());
        onInitView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume");
        if (mReceiverIntentFilter != null) {
            LocalBroadcastManager.getInstance(getApplicationContext())
                                 .registerReceiver(mBroadcastReceiver, mReceiverIntentFilter);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        Log.d("OnPause");
        LocalBroadcastManager.getInstance(getApplicationContext())
                             .unregisterReceiver(mBroadcastReceiver);
    }

    public abstract String getScreenTag();

    public abstract int getScreenLayout();

    public abstract String getToolbarTitle();

    public abstract void onInitView();

    protected void makeConfirmSnackBar(final String message) {
        SnackbarMaker.getInstance().setMessage(message)
                     .positiveAction(R.string.snackbar_action_ok)
                     .make(this, mCoordinatorLayout);
    }

    public RedditApp getRedditApp() {
        return (RedditApp) getApplication();
    }

    /**
     * Converts an implicit intent to an explicit intent, required since Android L.
     * see : https://commonsware.com/blog/2014/06/29/dealing-deprecations-bindservice.html
     *
     * @param context the context
     * @param implicitIntent implicit intent which to be converted.
     * @return the explicit intent from the specified implicit intent.
     */
    public static Intent convertToExplicitIntent(final Context context,
          final Intent implicitIntent) {
        //Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        //Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        //Get component info and create ComponentName
        final ResolveInfo serviceInfo = resolveInfo.get(0);
        final String packageName = serviceInfo.serviceInfo.packageName;
        final String className = serviceInfo.serviceInfo.name;
        final ComponentName component = new ComponentName(packageName, className);

        //Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        //Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    /**
     * Creates an IntentService wich connects to the openWeatherMap API.
     */
    public void startFetchRedditService() {
        Intent fetchIntent = new Intent(Constants.FETCH_DATA_ACTION);
        fetchIntent.addCategory(Constants.FETCH_DATA_CATEGORY);
        Log.d("startFetchRedditService with ", fetchIntent);
        //we need an explicit intent since Android L
        //see: https://commonsware.com/blog/2014/06/29/dealing-deprecations-bindservice.html
        startService(convertToExplicitIntent(this, fetchIntent));
    }

    protected void registerBroadcastReceiver() {
        if (mReceiverIntentFilter == null) {
            mReceiverIntentFilter = new IntentFilter(BROADCAST_DATA_ACTION);
        }
        Log.d("registering broadcast receiver for ", mReceiverIntentFilter);
        LocalBroadcastManager.getInstance(getApplicationContext())
                             .registerReceiver(mBroadcastReceiver, mReceiverIntentFilter);
    }
}
