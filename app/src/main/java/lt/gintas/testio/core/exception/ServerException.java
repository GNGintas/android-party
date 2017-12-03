package lt.gintas.testio.core.exception;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class ServerException extends Exception {

    public ServerException(int status) {
        super("server exception " + status);
    }
}
