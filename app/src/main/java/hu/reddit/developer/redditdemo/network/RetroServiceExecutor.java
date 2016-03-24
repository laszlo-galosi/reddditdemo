package hu.reddit.developer.redditdemo.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.gson.internal.LinkedTreeMap;
import hu.reddit.developer.data.BasicEntity;
import hu.reddit.developer.data.JsonSerializer;
import hu.reddit.developer.data.RedditDataEntity;
import hu.reddit.developer.redditdemo.RedditApp;
import hu.reddit.developer.redditdemo.exception.InternalErrorException;
import hu.reddit.developer.redditdemo.exception.NetworkConnectionException;
import hu.reddit.developer.redditdemo.exception.RestApiResponseException;
import hu.reddit.developer.redditdemo.helpers.Constants;
import hu.reddit.developer.redditdemo.helpers.KeyValuePairs;
import hu.reddit.developer.redditdemo.helpers.Leakable;
import hu.reddit.developer.redditdemo.helpers.RxUtils;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static hu.reddit.developer.redditdemo.exception.RestApiResponseException.RESP_REASON;
import static hu.reddit.developer.redditdemo.exception.RestApiResponseException.RESP_STATUS;
import static hu.reddit.developer.redditdemo.exception.RestApiResponseException.RESP_URL_FROM;
import static hu.reddit.developer.redditdemo.helpers.Constants.KEY_PARAM_SUBREDDIT;

/**
 * Created by László Gálosi on 23/03/16
 */
public class RetroServiceExecutor implements Leakable {

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private RedditService mRedditService;
    private RedditApp mApplication;

    private Client mOkClient;

    public RetroServiceExecutor(final RedditApp app) {
        mApplication = app;
        mOkClient = new OkClient(RedditApp.createOkHttpClient(app));
    }

    @Override public void clearLeakables() {
        this.subscriptions.clear();
    }

    public void unsubscribe() {
        RxUtils.unsubscribeIfNotNull(this.subscriptions);
    }

    public void resubscribe() {
        this.subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(this.subscriptions);
    }

    public void executeGetSub(Subscriber subscriber, Map<String, Object> queryMap) {
        ensureSubs().add(
              getSub(RedditDataEntity.class, queryMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        );
    }

    /**
     * Ensures if the {@link Subscription#isUnsubscribed()} false, or creates a new {@link
     * CompositeSubscription} and returns it.
     */
    protected CompositeSubscription ensureSubs() {
        this.subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(this.subscriptions);
        return this.subscriptions;
    }

    private <T extends BasicEntity> Observable<T> getSub(final Class<T> entityClass,
          Map<String, Object> queryMap) {
        if (isThereInternetConnection()) {
            RedditService redditService = createService(RedditService.class);
            String subReddit = String.format("%s.json", (String) queryMap.get(KEY_PARAM_SUBREDDIT));
            queryMap.remove(KEY_PARAM_SUBREDDIT);
            return redditService
                  .getSub(subReddit, queryMap)
                  .onErrorResumeNext(
                        new Func1<Throwable, Observable<T>>() {
                            @Override
                            public Observable<T> call(final Throwable throwable) {
                                return buildError(throwable);
                            }
                        }
                  ).flatMap(new Func1<Object, Observable<T>>() {
                      @Override public Observable<T> call(final Object o) {
                          //Neeed this becauseof Retrofit and gson: http://stackoverflow
                          // .com/a/31556363
                          JsonSerializer<T> jsonSerializer = new JsonSerializer<>();
                          T entity = null;
                          if (o instanceof LinkedTreeMap) {
                              entity =
                                    jsonSerializer.deserialize((LinkedTreeMap) o, entityClass);
                          } else if (o.getClass() == entityClass) {
                              entity = (T) o;
                          }
                          if (entity != null) {
                              return Observable.just(entity);
                          }
                          return Observable.error(new InternalErrorException(
                                String.format("Cannot serialize result: %s", o)));
                      }
                  });
        }
        return Observable.error(new NetworkConnectionException("No internet connection detected"));
    }

    public boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
              (ConnectivityManager) this.mApplication.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    public <S> S createService(final Class<S> serviceClass) {
        final RestAdapter restAdapter =
              new RestAdapter.Builder().setEndpoint(Constants.REDDIT_SERVER_ENDPOINT)
                                       .setClient(mOkClient).build();
        if (serviceClass == RedditService.class) {
            return (S) restAdapter.create(RedditService.class);
        }
        return null;
    }

    private static Observable buildError(final Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            RetrofitError retrofitError = (RetrofitError) throwable;
            Response response = retrofitError.getResponse();
            String jsonBody = "";
            String reason = "";
            if (response == null || response.getBody() == null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("error", retrofitError.getKind().name());
                    jsonObject.put("errorDescription", retrofitError.getMessage());
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                jsonBody = jsonObject.toString();
                reason = retrofitError.getMessage();
            } else {
                jsonBody = new String(((TypedByteArray) response.getBody()).getBytes());
                reason = response.getReason();
            }
            //Log.d("buildError ", jsonBody);
            KeyValuePairs<String, Object> responseInfo = new KeyValuePairs<String, Object>()
                  .put(RESP_STATUS, new Integer(response == null ? 500 : response.getStatus()))
                  .put(RESP_REASON, reason).put(RestApiResponseException.RESP_BODY, jsonBody)
                  .put(RESP_URL_FROM, retrofitError.getUrl());
            RestApiResponseException restApiException =
                  new RestApiResponseException(responseInfo, throwable);
            restApiException.initErrorResponse();
            return Observable.error(restApiException);
        }
        return Observable.error(new InternalErrorException(throwable));
    }
}

