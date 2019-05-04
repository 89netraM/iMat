package Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ReceiptComponent extends VBox {
	public ReceiptComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReceiptComponent.fxml"));
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