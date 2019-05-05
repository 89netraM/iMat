package ProductCounter;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ProductCounterComponent extends HBox {
	public ProductCounterComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductCounterComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}