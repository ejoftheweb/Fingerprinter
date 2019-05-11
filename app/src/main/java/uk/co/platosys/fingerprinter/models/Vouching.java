package uk.co.platosys.fingerprinter.models;

import uk.co.platosys.fingerprinter.models.user.Vouchor;
import uk.co.platosys.minigma.Signature;

public class Vouching {
    private String statementHash;
    private Signature signature;
    public Vouching (Statement statement, Vouchor vouchor, char[] passphrase){
        this.statementHash = statement.getHash();
        try {
            this.signature = vouchor.getKey().sign(statement.getBytes(), passphrase);
        }catch (Exception x){}
    }
}
