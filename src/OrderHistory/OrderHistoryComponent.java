package OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.List;

public class OrderHistoryComponent extends ScrollPane {
	@FXML
	private VBox ordersListBox;

	private List<Order> orders;

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

		orders = IMatDataHandler.getInstance().getOrders();
		//Reverse chronological order
		orders.sort((a, b) -> (int)(b.getDate().getTime() - a.getDate().getTime()));

		for (int i = 0; i < orders.size(); i++) {
			OrderHistoryItemComponent item = new OrderHistoryItemComponent(orders.get(i));
			if (i % 2 == 0) {
				item.getStyleClass().add("odd");
			}
			ordersListBox.getChildren().add(item);
		}
	}
}