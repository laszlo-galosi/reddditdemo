package hu.reddit.developer.redditdemo.exception;

/**
 * Created by László Gálosi on 23/03/16
 */
public class InternalErrorException extends ErrorBundleException {
    public InternalErrorException() {
        super();
    }

    public InternalErrorException(final String message) {
        super(message);
    }

    public InternalErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InternalErrorException(final Throwable cause) {
        super(cause);
    }
}
