package OrderHistory;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderHistoryItemComponent extends AnchorPane {
	public OrderHistoryItemComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderHistoryItemComponent.fxml"));
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
