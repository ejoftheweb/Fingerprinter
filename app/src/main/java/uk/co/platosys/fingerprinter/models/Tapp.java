package uk.co.platosys.fingerprinter.models;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * This is the vehicle that holds all the data for a single Tapp (aka Vouchr).
 *
 * Created by edward on 03/03/18.
 */

public class Tapp {
    String tappID;
    String title;
    String tweet;
    String author;



    String publisher;
    String content;
    public Tapp (String tappID){
        this.tappID=tappID;
        //lookup document from server;
        //Document document = serverlookup(tappid)
    }
    public Tapp (Document tappDoc){
        init(tappDoc);
    }
    private void init (Document tappDoc){
        Element tappElement=tappDoc.getRootElement();

    }
    public String getTappID() {
        return tappID;
    }

    public String getTitle() {
        return title;
    }

    public String getTweet() {
        return tweet;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getContent() {
        return content;
    }
}
