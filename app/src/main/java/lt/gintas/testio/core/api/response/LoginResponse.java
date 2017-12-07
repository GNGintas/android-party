package lt.gintas.testio.core.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gintautas on 2016-12-11.
 */

public class LoginResponse {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return this.token;
    }

}
