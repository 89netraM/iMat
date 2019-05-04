package Receipt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReceiptComponent extends GridPane {
	@FXML
	private VBox receiptList;

	@FXML
	private GridPane undoPane;
	@FXML
	private Label undoText;
	@FXML
	private Button undoButton;

	@FXML
	private Button backButton;
	@FXML
	private Button checkoutButton;

	private final ShoppingCart cart;
	private final Map<Integer, Label> receiptItems = new HashMap<>();

	public ReceiptComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReceiptComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		cart = IMatDataHandler.getInstance().getShoppingCart();
		cart.addShoppingCartListener(this::shoppingCartListener);
	}

	private void shoppingCartListener(CartEvent e) {
		Product product = e.getShoppingItem().getProduct();

		if (e.isAddEvent()) {
			//Creates new UI component for the shopping item
			Label label = new Label(product.getName() + ": " + e.getShoppingItem().getAmount());

			receiptItems.put(product.getProductId(), label);
			receiptList.getChildren().add(label);
		}
		else {
			if (e.getShoppingItem().getAmount() > 0.0d) {
				//Updates existing UI component for the shopping item
				receiptItems.get(product.getProductId()).setText(product.getName() + ": " + e.getShoppingItem().getAmount());
			}
			else {
				receiptList.getChildren().remove(receiptItems.remove(product.getProductId()));
			}
		}
	}

	@FXML
	private void onUndoButton() {

	}

	@FXML
	private void onBackButton() {

	}
	@FXML
	private void onCheckoutButton() {

	}
}