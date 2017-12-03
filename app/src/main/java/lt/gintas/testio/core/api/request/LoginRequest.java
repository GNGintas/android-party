package lt.gintas.testio.core.api.request;

/**
 * Created by Gintautas on 2016-12-11.
 */

public class LoginRequest {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
