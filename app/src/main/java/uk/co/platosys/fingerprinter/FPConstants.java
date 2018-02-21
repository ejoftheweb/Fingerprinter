package uk.co.platosys.fingerprinter;

import java.io.File;

/**
 * Created by edward on 13/02/18.
 */

public class FPConstants {
    static final String HKP_PROTOCOL = "https:";
    static final String HKP_HOST ="keyserver.ubuntu.com";
    static final int HKP_PORT=11371;
    static final String HKP_FILE="";

    static final String PGP_PUBRING_FILENAME = "pubring.asc";
    static final String PGP_SECRING_FILENAME= "secring.asc";
static final String PGP_PUBRING_FOLDER="PGPKeys";

    static final File PGP_KEYRING_FILE=new File ("pgpkeyringfile");//TODO

    static final String PREFS_USERNAME_KEY="username";

    static final int MIN_PASSPHRASE_LENGTH=3;
    static final String PASSPHRASE_INTENT_KEY ="passphrase";
    static final String NAME_INTENT_KEY="name";
    static final String EMAIL_INTENT_KEY="email";
    static final long FLASHWAIT = 800;
    static final long FLASHPAUSE=  1200;
static final String PLAYSTORE_TWITTER_URL = "https://play.google.com/store/apps/details?id=com.twitter.android";
    static final float POPUP_PROPORTIONS=0.55f;
    static final float AD_POPUP_PROPORTIONS=0.95f;
}
