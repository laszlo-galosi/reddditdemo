package hu.reddit.developer.redditdemo.exception;

/**
 * Created by László Gálosi on 23/03/16
 */
public class ErrorBundleException extends Exception {
    public ErrorBundleException() {
        super();
    }

    public ErrorBundleException(final String message) {
        super(message);
    }

    public ErrorBundleException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ErrorBundleException(final Throwable cause) {
        super(cause);
    }
}
