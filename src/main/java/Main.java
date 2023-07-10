import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); // reflection are here
        Scene scene = new Scene(root, 424, 311);
        stage.setTitle("Merge PDF");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
