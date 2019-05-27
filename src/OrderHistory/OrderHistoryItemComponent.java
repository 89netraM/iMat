package OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.stream.Collectors;

public class OrderHistoryItemComponent extends AnchorPane {
	@FXML
	private Label nr;
	@FXML
	private Label date;
	@FXML
	private Label total;

	@FXML
	private Button stateButton;

	private boolean isOpen = false;
	@FXML
	private AnchorPane infoPane;
	@FXML
	private VBox productList;
	@FXML
	private Label infoTotal;

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

		//Removes the `infoPane` so that this item won't appear open from the beginning.
		this.getChildren().remove(infoPane);

		infoTotal.setText(String.format("Totalt: %.2f kr", getTotalSum()));
	}

	@FXML
	private void open() {
		setOpen(!isOpen());
		stateButton.setText(isOpen() ? "Stäng" : "Visa");
	}

	/**
	 * Calculates the sum of all items in this order.
	 * @return    The total sum.
	 */
	private double getTotalSum() {
		double sum = 0.0d;

		for (ShoppingItem item : order.getItems()) {
			sum += item.getTotal();
		}

		return sum;
	}

	/**
	 * Sets the open state. If it's the first time this order is opened, the product list is populated with the orders
	 * items.
	 * @param    open    Open or closed.
	 */
	public void setOpen(boolean open) {
		if (open != isOpen) {
			if (open) {
				this.getChildren().add(infoPane);
				populateProductList();
			}
			else {
				this.getChildren().remove(infoPane);
			}

			isOpen = open;
		}
	}
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Adds a new shopping item to the product list for each order item.
	 */
	private void populateProductList() {
		if (productList.getChildren().size() == 0) {
			for (int i = 0; i < order.getItems().size(); i++) {
				OrderHistoryShoppingItemComponent item = new OrderHistoryShoppingItemComponent(order.getItems().get(i));
				if (i % 2 == 0) {
					item.getStyleClass().add("odd");
				}
				productList.getChildren().add(item);
			}
		}
	}
}