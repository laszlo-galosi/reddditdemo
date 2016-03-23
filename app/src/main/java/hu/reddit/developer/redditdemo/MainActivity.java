package hu.reddit.developer.redditdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.List;
import trikita.log.Log;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.w("onNewIntent", intent);
        super.onNewIntent(intent);
        if (intent.getAction().equals(Constants.BROADCAST_DATA_ACTION)) {
            if (intent.hasExtra(Constants.BROADCAST_EXTRA_ERROR)) {
                @StringRes int errorResId = intent.getIntExtra(
                      Constants.BROADCAST_EXTRA_ERROR,
                      R.string.snack_general_error);
                makeWarningSnackBar(errorResId, R.string.snack_action_retry,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    });
            }
        }
    }

    /**
     * Creates an IntentService wich connects to the openWeatherMap API.
     */
    private void startFetchDataService() {
        Intent fetchIntent = new Intent(Constants.FETCH_DATA_ACTION);
        fetchIntent.addCategory(Constants.FETCH_DATA_CATEGORY);
        Log.d("startFetchDataService with ", fetchIntent);
        //we need an explicit intent since Android L
        //see: https://commonsware.com/blog/2014/06/29/dealing-deprecations-bindservice.html
        startService(convertToExplicitIntent(this, fetchIntent));
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

    private void makeWarningSnackBar(@StringRes int messageResId,
          @StringRes int actionResId,
          @Nullable View.OnClickListener clickListener) {
        Log.w(getString(messageResId));

        @ColorInt int actionColor = getResources().getColor(R.color.primary);

        Snackbar.make(mCoordinatorLayout, getString(messageResId), Snackbar.LENGTH_LONG)
                .setAction(actionResId, clickListener)
                .setActionTextColor(actionColor)
                .show();
    }
}
