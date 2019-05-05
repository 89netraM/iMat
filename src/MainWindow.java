import ProductCounter.ProductCounterComponent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class MainWindow {
	@FXML
	private ProductCounterComponent counter;

	private ObjectProperty<ShoppingItem> item = new SimpleObjectProperty<>();

	private ShoppingCart cart;

	public MainWindow() {
		IMatDataHandler IMat = IMatDataHandler.getInstance();
		cart = IMat.getShoppingCart();
		cart.addProduct(IMat.getProduct(1));
		item.setValue(cart.getItems().get(0));
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
		item.getValue().setAmount(item.getValue().getAmount() + change);
		cart.fireShoppingCartChanged(item.getValue(), false);
	}

	public ShoppingItem getItem() {
		return item.getValue();
	}
	public void setItem(ShoppingItem value) {
		item.setValue(value);
	}
}