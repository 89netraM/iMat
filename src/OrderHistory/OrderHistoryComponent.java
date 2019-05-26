package OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderHistoryComponent extends ScrollPane {
	@FXML
	private VBox ordersListBox;

	public OrderHistoryComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderHistoryComponent.fxml"));
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
