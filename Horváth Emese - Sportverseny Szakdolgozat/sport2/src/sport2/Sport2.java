
package sport2;


import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Horváth Emese
 */
public class Sport2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        /**
         * Előhívja a grafikus felülethez tartozó stlust 
         */
        scene.getStylesheets().add("/css/style.css");
        
        
         /**
         * Összegyűjti a hat lehetséges tálca ikont és a program
         * futtatásakor mindig új, véletlenszerűt választ ki
         */
        
        Random r = new Random();
        final String[] icon_path = new String[]{
            "/icons/basketball-icon.png",
            "/icons/chess-horse-icon.png",
            "/icons/sport-icon.png",
            "/icons/Sport-shuttercock-icon.png",
            "/icons/sport-soccer-icon.png",
            "/icons/Sport-table-tennis-icon.png"
        };
        
        String random_icon = icon_path[r.nextInt(6)];       
        Image icon = new Image(random_icon);
        stage.getIcons().add(icon);

        /**
         * egadja a program címét és egakadályozza az ablak átméretezésének
         * lehetőségét
         */        
        stage.setTitle("Sportverseny");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
