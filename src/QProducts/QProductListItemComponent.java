package QProducts;

import ProductCounter.ProductCounterComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.*;

import java.awt.*;
import java.io.IOException;

public class QProductListItemComponent extends GridPane {
	private ShoppingCart cart;
	private Product product;

	@FXML
	private ImageView image;

	@FXML
	private Label nameLabel;
	@FXML
	private Label priceLabel;

	@FXML
	private Button addButton;
	@FXML
	private ProductCounterComponent counter;

	public QProductListItemComponent(Product product) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QProductListItemComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.product = product;

		IMatDataHandler dataHandler = IMatDataHandler.getInstance();

		cart = dataHandler.getShoppingCart();
		cart.addShoppingCartListener(this::onCartEvent);

		image.setImage(dataHandler.getFXImage(product, 100.0d, 100.0d));

		nameLabel.setText(product.getName());
		priceLabel.setText(product.getPrice() + "/" + product.getUnitSuffix());

		addButton.setVisible(!isProductInCart(product));
		counter.setVisible(isProductInCart(product));
		for (ShoppingItem item : cart.getItems()) {
			if (item.getProduct() == product) {
				counter.setShoppingItem(item);
			}
		}
	}

	@FXML
	private void addProduct() {
		if (!isProductInCart(product)) {
			cart.addProduct(product);
		}
	}

	private void onCartEvent(CartEvent e) {
		if (e.getShoppingItem() == null) {
			return;
		}

		if (e.getShoppingItem().getProduct() == product) {
			addButton.setVisible(!isProductInCart(product));
			counter.setVisible(isProductInCart(product));

			if (e.isAddEvent() && e.getShoppingItem() != counter.getShoppingItem()) {
				counter.setShoppingItem(e.getShoppingItem());
			}

			counter.onCartEvent(e);
		}
	}

	private boolean isProductInCart(Product p) {
		for (ShoppingItem item : cart.getItems()) {
			if (item.getProduct() == p) {
				return true;
			}
		}

		return false;
	}
}