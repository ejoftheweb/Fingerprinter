package uk.co.platosys.fingerprinter.models;

import com.twitter.sdk.android.core.models.User;

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

    public VouchUser(com.twitter.sdk.android.core.models.User user){
        this.user=user;
        this.profileTweet=user.description;
        this.name=user.screenName;
    }
    public User getTwitterUser() {
        return user;
    }
}
