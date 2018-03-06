package uk.co.platosys.fingerprinter.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.twitter.sdk.android.core.models.User;

import java.io.IOException;
import java.net.URL;

import uk.co.platosys.fingerprinter.services.VouchService;
import uk.co.platosys.minigma.exceptions.Exceptions;

/**
 *
 * User is a TwitterKit model. This is just a simple wrapper to it for
 * Vouch purposes, to keep the Vouch object model suitably extensible.
 *
 * Created by edward on 23/02/18.
 */

public class VouchUser {
    private User user;
    private String profileTweet;
    private String name;
    private Bitmap avatar;
    private boolean done=false;
    private URL avatarURL;
    private VouchService vouchService;

    public VouchUser(com.twitter.sdk.android.core.models.User user, VouchService vouchService){
        Log.i("VU", "creating vouch user"+user.screenName);
        this.vouchService=vouchService;
        this.user=user;
        this.profileTweet=user.description;
        this.name=user.screenName;
    }
    public User getTwitterUser() {
        return user;
    }
    public String getName(){return name;}
    public Bitmap getAvatar() {return avatar;}

    public String getProfileTweet() {
        return profileTweet;
    }

    public boolean isDone() {
        return done;
    }



    public void setAvatarURL(URL avatarURL) {
        this.avatarURL = avatarURL;
       new Thread (new LookupAvatar()).start();

    }
    private void setAvatar (Bitmap avatar){
        this.avatar = avatar;
        vouchService.notifyVouchUserCreatedListeners(this);
        done=true;
    }
    private class LookupAvatar implements Runnable{
        public void run(){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(avatarURL.openConnection().getInputStream());
                setAvatar(bitmap);
            }catch (IOException iox){
                Exceptions.dump (iox);
            }
        }
    }
}
