package lt.gintas.testio.core.api;

import java.util.List;

import io.reactivex.Flowable;
import lt.gintas.testio.core.api.request.LoginRequest;
import lt.gintas.testio.core.api.response.LoginResponse;
import lt.gintas.testio.core.model.ServerItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Gintautas on 2016-12-11.
 */
public interface AppService {

    String VERSION = "v1";

    @POST(VERSION + "/tokens")
    Flowable<LoginResponse> login(@Body LoginRequest request);

    @GET(VERSION + "/servers")
    Call<List<ServerItem>> getServersList(@Header("Authorization") String token);

}
