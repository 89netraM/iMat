package ProductCounter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCounterComponent extends HBox {
	@FXML
	private TextField amountTextField;

	private ShoppingItem item;
	private double amount = 0;

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

		IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this::onCartEvent);
	}

	public void setShoppingItem(ShoppingItem value) {
		item = value;
		updateUIAmount();
	}
	public ShoppingItem getShoppingItem() {
		return item;
	}

	private void onCartEvent(CartEvent e) {
		if (e.getShoppingItem() == item) {
			if (item.getAmount() != amount) {
				updateUIAmount();
			}
		}
	}

	private void updateUIAmount() {
		amount = item.getAmount();
		amountTextField.setText(Double.toString(amount));
	}
}