package sport2;
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 *
 * @author Horváth Emese
 */
public class sounds {
   
    public void playSound(String sound){
        Media media = null;
        try{
        media = new Media(getClass().getResource("/mp3/"+sound+".mp3").toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        } catch (URISyntaxException e) {
        e.printStackTrace();
        }  
    }         
}
