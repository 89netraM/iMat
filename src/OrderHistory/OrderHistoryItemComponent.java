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

		this.getChildren().remove(infoPane);

		infoTotal.setText(String.format("Totalt: %.2f kr", getTotalSum()));
	}

	@FXML
	private void open() {
		setOpen(!isOpen());
		stateButton.setText(isOpen() ? "Stäng" : "Visa");
	}

	private double getTotalSum() {
		double sum = 0.0d;

		for (ShoppingItem item : order.getItems()) {
			sum += item.getTotal();
		}

		return sum;
	}

	public void setOpen(boolean open) {
		if (open != isOpen) {
			if (open) {
				this.getChildren().add(infoPane);
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
}