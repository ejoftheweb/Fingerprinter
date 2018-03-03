package uk.co.platosys.fingerprinter.cameras;
import android.media.Image;
import java.io.File;

/**
 * Created by edward on 02/03/18.
 */

public class Photo {
    private Image image;
    private File file;
    protected Photo(Image image, File file){
        this.image=image;
        this.file=file;
    }
    public File getFile() {
        return file;
    }
    public Image getImage() {
        return image;
    }
}
