package lt.gintas.testio.core.exception;

/**
 * Created by Gintautas on 2016-11-11.
 */

public class Server504ErrorException extends Exception {

    public Server504ErrorException() {
        super("504 Server Error");
    }
}