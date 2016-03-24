package hu.reddit.developer.redditdemo;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.reddit.developer.redditdemo.helpers.Constants;
import trikita.log.Log;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override public String getScreenTag() {
        return TAG;
    }

    @Override public int getScreenLayout() {
        return R.layout.activity_main;
    }

    @Override public String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    public void setToolbarTitle(final String title) {
        mToolbar.setTitle(title);
    }

    @Override public void onInitView() {
        ButterKnife.bind(this);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        registerBroadcastReceiver();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction().equals(Constants.BROADCAST_DATA_ACTION)) {
            Log.w("onNewIntent", intent);
            if (intent.hasExtra(Constants.BROADCAST_EXTRA_ERROR)) {
                @StringRes int errorResId = intent.getIntExtra(
                      Constants.BROADCAST_EXTRA_ERROR,
                      R.string.snack_general_error);
                SnackbarMaker.getInstance().setMessageRes(errorResId)
                             .positiveAction(R.string.snack_action_retry)
                             .positiveActionClicked(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     startFetchRedditService();
                                 }
                             }).make(this, mCoordinatorLayout);
            } else if (intent.hasExtra(Constants.KEY_PARAM_SUBREDDIT)) {
                String title =
                      String.format("#%s", intent.getStringExtra(Constants.KEY_PARAM_SUBREDDIT)
                                                 .toLowerCase());
                mToolbar.setTitle(title);
            }
        }
    }

    @OnClick(R.id.actionFab)
    public void refresh() {
        startFetchRedditService();
    }
}
