package uk.co.platosys.fingerprinter.twitter;

/**
 * Created by edward on 25/02/18.
 */
import com.twitter.sdk.android.core.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface TwitterApiInterface {



        @GET("users/show.json")
        Call<User> getUserDetails(@Query("screen_name") String screenName);


}
