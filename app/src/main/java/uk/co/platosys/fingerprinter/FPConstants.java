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
    static final File PGP_KEYRING_FILE=new File ("pgpkeyringfile");//TODO

    static final String PREFS_USERNAME_KEY="username";
}
