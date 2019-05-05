package Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ReceiptItemComponent extends GridPane {
	public ReceiptItemComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReceiptItemComponent.fxml"));
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