package lt.gintas.testio.core.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gintautas on 03/12/2017.
 */

public class ServersListResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("distance")
    private String distance;

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

}
