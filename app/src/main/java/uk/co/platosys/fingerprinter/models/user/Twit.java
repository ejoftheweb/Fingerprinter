package uk.co.platosys.fingerprinter.models.user;

import android.graphics.Bitmap;

import com.twitter.sdk.android.core.models.User;

public interface Twit {
    public User getTwitterUser();
    public String getName();
    public Bitmap getAvatar();
    public String getProfileString();
}
