package uk.co.platosys.fingerprinter.models.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.twitter.sdk.android.core.models.User;

import java.io.IOException;
import java.net.URL;

import uk.co.platosys.fingerprinter.services.VouchService;
import uk.co.platosys.minigma.Key;
import uk.co.platosys.minigma.Lock;
import uk.co.platosys.minigma.exceptions.Exceptions;

/**
 *
 * User is a TwitterKit model. This is just a simple wrapper to it for
 * Vouch purposes, to keep the Vouch object model suitably extensible.
 *
 * Created by edward on 23/02/18.
 */

public  class VouchUser extends Vouchor implements Twit {
    private User twitterUser;
    private String profileString;
    private Bitmap avatar;
    private URL avatarURL;
    private VouchService vouchService;

    public VouchUser(com.twitter.sdk.android.core.models.User twitterUser, VouchService vouchService){
        Log.i("VU", "creating vouch user"+twitterUser.screenName);
        this.vouchService=vouchService;
        this.twitterUser=twitterUser;
        this.profileString=twitterUser.description;
        this.name=twitterUser.screenName;
    }

    public User getTwitterUser() {return twitterUser;}
    public Bitmap getAvatar() {return avatar;}
    public String getProfileString() {
        return profileString;
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

    @Override
    public Key getKey() {
        return null;
    }

    @Override
    public Lock getLock() {
        return null;
    }
}
