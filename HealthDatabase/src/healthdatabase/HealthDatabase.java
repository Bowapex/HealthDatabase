
package healthdatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HealthDatabase extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Health App");
        stage.setWidth(800);
        stage.setHeight(650);
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
