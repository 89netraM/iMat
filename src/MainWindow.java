import ProductCounter.ProductCounterComponent;
import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class MainWindow {
	@FXML
	private ProductCounterComponent counter;

	private ShoppingItem item;

	private ShoppingCart cart;

	public MainWindow() {
		IMatDataHandler IMat = IMatDataHandler.getInstance();
		cart = IMat.getShoppingCart();
		cart.addProduct(IMat.getProduct(1));
		item = cart.getItems().get(0);
	}

	@FXML
	private void plus() {
		change(1);
	}
	@FXML
	private void minus() {
		change(-1);
	}

	private void change(double change) {
		item.setAmount(item.getAmount() + change);
		cart.fireShoppingCartChanged(item, false);
	}

	@FXML
	private void load() {
		if (counter != null) {
			counter.setShoppingItem(item);
		}
	}
}