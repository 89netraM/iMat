package Receipt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReceiptItemComponent extends GridPane {
	@FXML
	private Label nameLabel;

	@FXML
	private Label totalLabel;

	private ShoppingItem item;

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

		IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this::onCartEvent);
	}

	private void onCartEvent(CartEvent e) {
		if (e.getShoppingItem() == item) {
			updateUI();
		}
	}

	private void updateUI() {
		nameLabel.setText(item.getProduct().getName());
		totalLabel.setText(item.getTotal() + " kr");
	}

	public void setItem(ShoppingItem value) {
		item = value;
		updateUI();
	}
	public ShoppingItem getItem() {
		return item;
	}
}