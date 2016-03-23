package hu.reddit.developer.redditdemo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import hu.reddit.developer.data.RedditEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import trikita.log.Log;

import static java.lang.String.format;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragment extends Fragment {

    public static final String TAG = "ListingFragment";
    private ItemAdapter mListingAdapter;
    private RecyclerView mRecyclerView;
    private List<RedditWrapper> mListingItems = new ArrayList<>(50);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    public static ListingFragment newInstance(String param1, String param2) {
        Log.d(TAG, "newInstance");
        ListingFragment fragment = new ListingFragment();
        Bundle args = new Bundle();
        return fragment;
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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_main_content);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                      return new RedditViewHolder(view, getActivity());
                  }
              };
        mRecyclerView.setAdapter(mListingAdapter);
    }

    private RedditApp getRedditApp() {
        return (RedditApp) ((MainActivity) getActivity()).getApplication();
    }

    @Override public void onResume() {
        super.onResume();
        RedditApp app = getRedditApp();
        app.clearEntries();
        app.setListingObserver(new ListingObserver());
        startFetchDataService();
    }

    @Override public void onPause() {
        super.onPause();
        RedditApp app = getRedditApp();
        app.setListingObserver(null);
        app.clearEntries();
    }

    static class RedditViewHolder extends ItemViewHolderBase {
        public RedditViewHolder(final View itemView, final Context context) {
            super(itemView, context);
        }

        @Override public void bind(final Object dataItem) {
            super.bind(dataItem);
            final RedditEntity reddit = (RedditEntity) dataItem;
            TextView tvItemTitle = (TextView) mItemView.findViewById(R.id.itemTitle);
            TextView tvItemDate = (TextView) mItemView.findViewById(R.id.itemDate);

            if (tvItemDate != null) {
                String postedAt = mContext.getString(R.string.posted_at);
                getCreatedText(reddit);
                tvItemTitle.setText(format("%s %s", postedAt, getCreatedText(reddit)));
            }
            if (tvItemTitle != null) {
                tvItemTitle.setText(reddit.title);
            }
        }
    }

    private static String getCreatedText(final RedditEntity reddit) {
        long ts = new Double(reddit.created).longValue();
        Date createdDate = new Date(ts);
        return SimpleDateFormat.getDateTimeInstance().format(createdDate);
    }

    static class RedditWrapper implements Bindable {
        RedditEntity mEntity;

        public RedditWrapper(final RedditEntity entity) {
            mEntity = entity;
        }

        @Override public void bind(final Object dataItem) {
            mEntity = (RedditEntity) dataItem;
        }

        @Override public Object getDatatItem() {
            return mEntity;
        }
    }

    class ListingObserver extends RecyclerView.AdapterDataObserver {
        @Override public void onChanged() {
            if (mListingAdapter == null) {
                setupAdapter();
            }
            mListingAdapter.notifyDataSetChanged();
        }

        @Override public void onItemRangeInserted(final int positionStart, final int itemCount) {
            mListingAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override public void onItemRangeRemoved(final int positionStart, final int itemCount) {
            mListingAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    /**
     * Creates an IntentService wich connects to the openWeatherMap API.
     */
    private void startFetchDataService() {
        Intent fetchIntent = new Intent(Constants.FETCH_DATA_ACTION);
        fetchIntent.addCategory(Constants.FETCH_DATA_CATEGORY);
        Log.d("startFetchDataService with ", fetchIntent.toString());
        //we need an explicit intent since Android L
        //see: https://commonsware.com/blog/2014/06/29/dealing-deprecations-bindservice.html
        getActivity().startService(
              MainActivity.convertToExplicitIntent(getActivity(), fetchIntent));
    }
}
