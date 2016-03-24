package hu.reddit.developer.redditdemo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import hu.reddit.developer.data.RedditEntity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import trikita.log.Log;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragment extends Fragment {

    public static final String TAG = "ListingFragment";
    @Bind(R.id.rv_main_content) RecyclerView mRecyclerView;
    private ItemAdapter mListingAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TimelineFragment.
     */
    public static ListingFragment newInstance(Bundle bundle) {
        ListingFragment listingFragment = new ListingFragment();
        Log.w("newInstance ", bundle);
        if (bundle != null) {
            listingFragment.setArguments(bundle);
        } else {
            listingFragment.setArguments(new Bundle());
        }
        return listingFragment;
    }

    public ListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return rootView;
    }

    private void setupAdapter() {
        mListingAdapter =
              new ItemAdapter(getActivity(), mRecyclerView, R.layout.list_item_reddit,
                              getRedditApp().getEntries()) {
                  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                        int viewType) {
                      View view = LayoutInflater.from(mContext)
                                                .inflate(R.layout.list_item_reddit, parent,
                                                         false);
                      return new ListingViewHolder(view, getActivity());
                  }
              };
        mRecyclerView.setAdapter(mListingAdapter);
    }

    private RedditApp getRedditApp() {
        return ((BaseActivity) getActivity()).getRedditApp();
    }

    @Override public void onResume() {
        super.onResume();
        BaseActivity activity = (BaseActivity) getActivity();
        RedditApp app = activity.getRedditApp();
        app.clearEntries();
        app.setListingObserver(new ListingObserver());
        activity.startFetchRedditService();
    }

    @Override public void onPause() {
        super.onPause();
        RedditApp app = getRedditApp();
        app.setListingObserver(null);
        app.clearEntries();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static String getCreatedText(final RedditEntity reddit) {
        long ts = new Double(reddit.created_utc).longValue();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        cal.setTimeZone(TimeZone.getDefault());
        return SimpleDateFormat.getDateTimeInstance().format(cal.getTime());
    }

    static class ListingViewHolder extends ItemViewHolderBase implements Target {

        @Bind(R.id.itemText) TextView mItemTextView;
        //@Bind(R.id.itemDate) TextView mItemDateView;
        @Bind(R.id.itemInfo) TextView mItemInfoView;
        @Bind(R.id.btnComments) Button mNumOfCommentsView;
        @Bind(R.id.itemThumb) ImageView mThumbView;

        public ListingViewHolder(final View itemView, final Context context) {
            super(itemView, context);
            ButterKnife.bind(this, itemView);
        }

        @Override public void bind(final Object dataItem) {
            super.bind(dataItem);
            final RedditEntity reddit = (RedditEntity) dataItem;

            mItemTextView.setText(reddit.title);
            //mItemDateView.setText(
            //      String.format("%s %s", mContext.getString(R.string.posted_at),
            //                    getCreatedText(reddit))
            //);
            mItemInfoView.setText(
                  String.format("%s %s", mContext.getString(R.string.by_author), reddit.author)
            );
            mNumOfCommentsView.setText(String.format("%d", reddit.num_comments));

            try {
                Uri builtUri = Uri.parse(reddit.thumbnail).buildUpon().build();

                Picasso picasso = Picasso.with(mContext);
                picasso.setIndicatorsEnabled(true);
                picasso.load(builtUri)
                       .centerCrop()
                       .resizeDimen(R.dimen.thumb_width, R.dimen.thumb_height)
                       .error(R.drawable.reddit_logo)
                       .placeholder(R.drawable.reddit_logo)
                       .into(this);
            } catch (final Exception ex) {
                Log.e("Failed to load Bitmap from resource", reddit.thumbnail, ex);
            }
        }

        @Override public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
            mThumbView.setImageBitmap(bitmap);
        }

        @Override public void onBitmapFailed(final Drawable errorDrawable) {
            Log.e("Failed to load Bitmap from resource", ((RedditEntity) getDatatItem()).thumbnail);
            mThumbView.setImageDrawable(errorDrawable);
        }

        @Override public void onPrepareLoad(final Drawable placeHolderDrawable) {
            mThumbView.setImageDrawable(placeHolderDrawable);
        }
    }

    class ListingObserver extends RecyclerView.AdapterDataObserver {
        @Override public void onChanged() {
            mListingAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(final int positionStart, final int itemCount) {
            mListingAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override public void onItemRangeRemoved(final int positionStart, final int itemCount) {
            mListingAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }
}
