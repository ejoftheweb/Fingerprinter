package uk.co.platosys.fingerprinter;

import java.io.File;

/**
 * Created by edward on 13/02/18.
 */

public class FPConstants {
    public static final String HKP_PROTOCOL = "https:";
    public static final String HKP_HOST ="keyserver.ubuntu.com";
    public static final int HKP_PORT=11371;
    public static final String HKP_FILE="";

    public static final String PGP_PUBRING_FILENAME = "pubring.asc";
    public static final String PGP_SECRING_FILENAME= "secring.asc";
    public static final String PGP_PUBRING_FOLDER="PGPKeys";

    public static final File PGP_KEYRING_FILE=new File ("pgpkeyringfile");//TODO

    public static final String PREFS_USERNAME_KEY="username";

    public static final int MIN_PASSPHRASE_LENGTH=3;
    public static final String PASSPHRASE_INTENT_KEY ="passphrase";
    public static final String NAME_INTENT_KEY="name";
    public static final String EMAIL_INTENT_KEY="email";
    public static final long FLASHWAIT = 800;
    public static final long FLASHPAUSE=  1200;
    public static final String PLAYSTORE_TWITTER_URL = "https://play.google.com/store/apps/details?id=com.twitter.android";
    static final float POPUP_PROPORTIONS=0.55f;
    static final float AD_POPUP_PROPORTIONS=0.95f;
   public static final int REQUEST_CAMERA_PERMISSION = 200;
}
