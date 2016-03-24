package hu.reddit.developer.redditdemo.exception;

/**
 * Created by László Gálosi on 23/03/16
 */
public class NetworkConnectionException extends ErrorBundleException {
    public NetworkConnectionException() {
        super();
    }

    public NetworkConnectionException(final String message) {
        super(message);
    }

    public NetworkConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }
}
