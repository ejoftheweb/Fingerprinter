package uk.co.platosys.fingerprinter.models.user;

import uk.co.platosys.minigma.Key;
import uk.co.platosys.minigma.Lock;

/**General class representing a user/person*/
public abstract class Vouchor {
    protected String name;
    protected boolean done;
    public String getName(){return name;}
    public boolean isDone() {
        return done;
    }
    public abstract Key getKey();
    public abstract Lock getLock();
}