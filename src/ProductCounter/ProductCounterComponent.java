package ProductCounter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCounterComponent extends HBox {
	@FXML
	private TextField amountTextField;

	private ShoppingCart cart;
	private ShoppingItem item;

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

		cart = IMatDataHandler.getInstance().getShoppingCart();
		cart.addShoppingCartListener(this::onCartEvent);
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
			updateUIAmount();
		}
	}

	@FXML
	private void onIncrease() {
		changeAmount(+1.0d);
	}
	@FXML
	private void onDecrease() {
		changeAmount(-1.0d);
	}
	private void changeAmount(double change) {
		item.setAmount(item.getAmount() + change);
		cart.fireShoppingCartChanged(item, false);
	}

	private void updateUIAmount() {
		amountTextField.setText(Double.toString(item.getAmount()));
	}
}