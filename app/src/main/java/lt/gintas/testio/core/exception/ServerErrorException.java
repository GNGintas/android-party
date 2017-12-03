package lt.gintas.testio.core.exception;

/**
 * Created by Gintautas on 2016-11-11.
 */

public class ServerErrorException extends Exception {

    public ServerErrorException() {
        super("500 Internal Server Error");
    }
}