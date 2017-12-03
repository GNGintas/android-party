package lt.gintas.testio.core.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gintautas on 2016-12-11.
 */

public class LoginResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

}
