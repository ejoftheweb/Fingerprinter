package uk.co.platosys.fingerprinter.models;

import uk.co.platosys.minigma.Digester;
import uk.co.platosys.minigma.Minigma;
import uk.co.platosys.minigma.utils.MinigmaUtils;

/**
 * A Statement wraps binary data. Strings are converted to binary.
 */
public class Statement {
    String hash;
    byte[] bytes;
    public Statement(String string) {
        this.bytes=string.getBytes();
        try {
            this.hash = Digester.digest(string);
        }catch (Exception x){//TODO}
    }}
    public Statement(byte[] bytes) {
        this.bytes = bytes;
        try {
            this.hash = Digester.digest(bytes);
        }catch(Exception x){//TODO}
    }}


    public String getHash(){
            return hash;
    }
    public byte[] getBytes(){
           return bytes;

    }
    @Override
    public String toString(){
        return MinigmaUtils.encode(getBytes());
    }
}
