package OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderHistoryItemComponent extends AnchorPane {
	@FXML
	private Label nr;
	@FXML
	private Label date;
	@FXML
	private Label total;

	private Order order;

	public OrderHistoryItemComponent(Order order) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderHistoryItemComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.order = order;

		nr.setText(String.format("#%d", this.order.getOrderNumber()));
		date.setText(String.format("%1$tF %1$tR", this.order.getDate()));
		total.setText(String.format("Totalt: %.2f kr", getTotalSum()));
	}

	@FXML
	private void open() {
		System.out.println("Open!");
	}

	private double getTotalSum() {
		double sum = 0.0d;

		for (ShoppingItem item : order.getItems()) {
			sum += item.getTotal();
		}

		return sum;
	}
}