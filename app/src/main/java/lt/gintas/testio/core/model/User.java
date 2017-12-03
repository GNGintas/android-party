package lt.gintas.testio.core.model;

import lt.gintas.testio.core.api.response.LoginResponse;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class User {

    private String token;

    public User(LoginResponse loginResponse) {
        token = loginResponse.getToken();
    }

    public String getToken() {
        return token;
    }

}
