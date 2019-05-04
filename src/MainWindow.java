import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Optional;

public class MainWindow {
	private final IMatDataHandler dataHandler;
	private final ShoppingCart cart;

	public MainWindow() {
		dataHandler = IMatDataHandler.getInstance();
		cart = dataHandler.getShoppingCart();
	}

	@FXML
	public void addItem() {
		changeItem(1, 1);
	}
	@FXML
	public void addRandomItem() {
		changeItem((int)(Math.random() * 10.0f) + 1, 1); //For testing purposes we hope products 1-10 exists
	}

	@FXML
	public void removeItem() {
		changeItem(1, -1);
	}
	@FXML
	public void removeRandomItem() {
		changeItem((int)(Math.random() * 10.0f) + 1, -1); //For testing purposes we hope products 1-10 exists
	}

	private void changeItem(int id, int amount) {
		Optional<ShoppingItem> item = cart.getItems().stream().filter(x -> x.getProduct().getProductId() == id).findFirst();
		if (item.isPresent()) {
			double currentAmount = item.get().getAmount();
			if (currentAmount + amount < 0.0d) {
				cart.removeItem(item.get());
			}
			else {
				item.get().setAmount(currentAmount + amount);
			}
			cart.fireShoppingCartChanged(item.get(), false);
		}
		else if (amount > 0) {
			cart.addProduct(dataHandler.getProduct(id), amount);
		}
	}
}