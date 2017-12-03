package lt.gintas.testio.core.exception;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class DomainNotExistException extends Exception {

    public DomainNotExistException() {
        super("404 domain not exist");
    }
}