package lt.gintas.testio.core.exception;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super("User unauthorized");
    }
}