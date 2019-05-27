package OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;

public class OrderHistoryShoppingItemComponent extends AnchorPane {
	@FXML
	private Label name;
	@FXML
	private Label amount;
	@FXML
	private Label total;

	public OrderHistoryShoppingItemComponent(ShoppingItem item) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderHistoryShoppingItemComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		name.setText(item.getProduct().getName());
		if (item.getAmount() != 1.0d) {
			DecimalFormat df = new DecimalFormat("#.##");
			amount.setText(String.format("%s%s * %.2f", df.format(item.getAmount()), item.getProduct().getUnitSuffix(), item.getProduct().getPrice()));
		}
		total.setText(String.format("%.2f", item.getTotal()));
	}
}
