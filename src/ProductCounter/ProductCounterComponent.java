package ProductCounter;

import com.sun.javafx.tk.Toolkit;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCounterComponent extends HBox {
	@FXML
	private TextField amountTextField;
	@FXML
	private Label unitLabel;

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

		//Listen to shoppingCart updates
		cart = IMatDataHandler.getInstance().getShoppingCart();

		//Listen to focus updates of the textField
		amountTextField.focusedProperty().addListener(this::onFocusChange);
		//Formats the text to a double on enter or unfocus
		amountTextField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
	}

	//Used by the FXML Loader to set the shoppingItem property from FXML.
	public void setShoppingItem(ShoppingItem value) {
		item = value;
		updateUIAmount();
	}
	public ShoppingItem getShoppingItem() {
		return item;
	}

	/**
	 * A parent should call this when a `shoppingCart` event occurs.
	 * @param    e    Should be the same as the original `shoppingCart` event received.
	 */
	public void onCartEvent(CartEvent e) {
		if (e.getShoppingItem() == item) {
			updateUIAmount();
		}
	}

	//Triggered when the user taps the + button
	@FXML
	private void onIncrease() {
		changeAmount(+1.0d);
	}
	//Triggered when the user taps the - button
	@FXML
	private void onDecrease() {
		changeAmount(-1.0d);
	}
	private void changeAmount(double change) {
		setAmount(item.getAmount() + change);
	}
	/**
	 * Updates the shoppingItem with the new amount and fires a event at the shopping cart.
	 * This component itself listens to this event to update the amount in the textField.
	 * @param    amount    The new amount.
	 */
	private void setAmount(double amount) {
		item.setAmount(amount);
		cart.fireShoppingCartChanged(item, false);
	}

	//Updates all UI parts to reflect the current state.
	private void updateUIAmount() {
		amountTextField.setText(Double.toString(item.getAmount()));

		if (!item.getProduct().getUnitSuffix().equals(unitLabel.getText())) {
			unitLabel.setText(item.getProduct().getUnitSuffix());

			//Calculates the with of the label to offset the text in the textField by an appropriate amount
			double labelWidth = Toolkit.getToolkit().getFontLoader().computeStringWidth(unitLabel.getText(), unitLabel.getFont());
			amountTextField.setPadding(new Insets(2, unitLabel.getPadding().getLeft() + labelWidth + unitLabel.getPadding().getRight() + 2, 2, 0));
		}
	}

	private void onFocusChange(ObservableValue<? extends Boolean> observable, boolean oldValue, boolean newValue) {
		if (!newValue) {
			readTextValue();
		}
	}

	//Is triggered when the user presses `Enter` in the textField.
	//Also used when the user changes focus from the textField.
	@FXML
	private void readTextValue() {
		try {
			double d = Double.parseDouble(amountTextField.getText());
			setAmount(d);
		}
		catch (NumberFormatException ignored) { }
	}
}