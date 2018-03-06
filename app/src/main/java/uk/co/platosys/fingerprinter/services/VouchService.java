package uk.co.platosys.fingerprinter.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.network.OAuth1aInterceptor;
import com.twitter.sdk.android.core.models.User;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.platosys.fingerprinter.twitter.TwitterApiInterface;
import uk.co.platosys.fingerprinter.models.VouchUser;

/**
 * Created by edward on 25/02/18.
 */

public class VouchService extends Service {
    private IBinder iBinder = new VouchBinder();
    private TwitterSession twitterSession;
   private boolean initialised = false;
    private VouchUser vouchUser;
    List<VouchUserCreatedListener> vouchUserCreatedListenerList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
Log.d("VS", "binding to activity "+intent.getStringExtra("activity"));
        return iBinder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        lookupUserInfo();

    }
    public class VouchBinder extends Binder {
        public VouchBinder(){
            super();
            Log.i("VS-VB", "ibinder created");
        }


        public Service getService(){
            return VouchService.this;
        }
    }

    private void lookupUserInfo() {
        Log.i("VS", "startin to lookup user info");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                // Twitter interceptor
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new OAuth1aInterceptor(twitterSession, TwitterCore.getInstance().getAuthConfig()))
                        .build())
                .build();
        TwitterApiInterface apiInterface = retrofit.create(TwitterApiInterface.class);
        Call<User> call = apiInterface.getUserDetails( twitterSession.getUserName());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("VS", "lookup response received");
                vouchUser=new VouchUser(response.body());
                initialised=true;

                notifyVouchUserCreatedListeners(vouchUser);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    Log.e("VS", "error looking up user", t);
            }
        });
    }

    private void notifyVouchUserCreatedListeners(VouchUser vouchUser){
        Log.i("VS", "notifying listeners that vouchUser "+vouchUser.getName()+"has been successfully created");
        for (VouchUserCreatedListener vouchUserCreatedListener:vouchUserCreatedListenerList){
            vouchUserCreatedListener.onVouchUserCreated(vouchUser);
        }
    }
    public void addVouchUserCreatedListener(VouchUserCreatedListener vouchUserCreatedListener){
        this.vouchUserCreatedListenerList.add(vouchUserCreatedListener);
        Log.i("VS", "there are now "+vouchUserCreatedListenerList.size()+" VUCListeners");
    }
    public interface VouchUserCreatedListener {
        void onVouchUserCreated(VouchUser vouchUser);
    }
}
