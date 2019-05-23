import OrderForm.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);

		//Runtime.getRuntime().addShutdownHook(new Thread(() -> Model.getInstance().shutDown()));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		primaryStage.setScene(new Scene(root, 1280, 800));
		primaryStage.setTitle("iMat");
		primaryStage.show();
	}
}
