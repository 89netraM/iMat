package Receipt;

import javafx.event.*;
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

	//region Undo
	private ReceiptItemComponent lastRemoved;
	private int lastIndex;
	@FXML
	private GridPane undoPane;
	@FXML
	private Label undoText;
	@FXML
	private Button undoButton;
	//endregion

	//region Navigation buttons
	@FXML
	private Button backButton;
	@FXML
	private Button checkoutButton;

	private EventHandler<ReceiptComponentEvent> onBackEventHandler;
	private EventHandler<ReceiptComponentEvent> onCheckoutEventHandler;
	//endregion

	private final ShoppingCart cart;
	//A list of all previously and currently used `ReceiptItemComponent`, so we don't have to create a new one every
	//time we add one back.
	private final Map<Integer, ReceiptItemComponent> receiptItems = new HashMap<>();

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
			if (lastRemoved != null && e.getShoppingItem() == lastRemoved.getItem()) {
				//For when this add event in fact is a undo event

				receiptItems.put(product.getProductId(), lastRemoved);
				receiptList.getChildren().add(lastIndex, lastRemoved);

				//It shouldn't be out of sync, but for safety's sake:
				lastRemoved.onCartEvent(e);
			}
			else {
				//Regular adding event

				//Creates new UI component for the shopping item
				ReceiptItemComponent item = new ReceiptItemComponent();
				item.setItem(e.getShoppingItem());
				item.setOnRemove(this::onRemoveItem);

				receiptItems.put(product.getProductId(), item);
				receiptList.getChildren().add(item);
			}

			clearUndoItem();
		}
		else {
			if (e.getShoppingItem().getAmount() <= 0.0d || !cart.getItems().contains(e.getShoppingItem())) {
				//Removing the item. Either from actually removing it, or because the amount is under zero

				//Updates existing UI component for the shopping item
				lastRemoved = receiptItems.remove(product.getProductId());

				//If for some reason it's already gone, we can't remove it again.
				if (lastRemoved != null) {
					lastIndex = receiptList.getChildren().indexOf(lastRemoved);

					receiptList.getChildren().remove(lastRemoved);

					if (lastRemoved.getItem().getAmount() <= 0.0d) {
						lastRemoved.getItem().setAmount(1.0d);
					}

					undoText.setText(String.format("\"%1s\" togs bort", lastRemoved.getItem().getProduct().getName()));
					undoPane.setVisible(true);
				}
			}
			else {
				//Just updating the amount of an item
				receiptItems.get(product.getProductId()).onCartEvent(e);
			}
		}
	}

	/**
	 * Resets the saved undo history, and hides the undo panel.
	 */
	private void clearUndoItem() {
		lastRemoved = null;
		undoPane.setVisible(false);
	}

	private void onRemoveItem(ReceiptItemComponent.ReceiptItemComponentEvent e) {
		e.getSource().getItem().setAmount(0.0d);
		cart.removeItem(e.getSource().getItem());
	}
	@FXML
	private void onUndoButton() {
		if (lastRemoved != null) {
			//If an undo is possible: add it back to the cart, and let the event do the rest.
			cart.addItem(lastRemoved.getItem());
		}
	}

	//region Navigation Buttons
	public void setBackButtonEnabled(boolean enabled) {
		backButton.setVisible(enabled);
		checkoutButton.setVisible(!enabled);
	}
	public void setCheckoutButtonEnabled(boolean enabled) {
		setBackButtonEnabled(!enabled);
	}

	//These buttons are for sending events about global navigation to the `MainController`.
	@FXML
	private void onBackButton() {
		ReceiptComponentEvent onBackEvent = new ReceiptComponentEvent(ReceiptComponentEvent.ON_BACK);
		fireEvent(onBackEvent);
	}
	@FXML
	private void onCheckoutButton() {
		ReceiptComponentEvent onCheckoutEvent = new ReceiptComponentEvent(ReceiptComponentEvent.ON_CHECKOUT);
		fireEvent(onCheckoutEvent);
	}

	/**
	 * Used mainly through FXML to set an event handler for the Back buttons action.
	 * Used in FXML like this: `onBack="#eventHandlerMethod"`
	 * @param    value    The event handler method.
	 */
	public void setOnBack(EventHandler<ReceiptComponentEvent> value) {
		if (onBackEventHandler != null) {
			removeEventHandler(ReceiptComponentEvent.ON_BACK, onBackEventHandler);
		}

		onBackEventHandler = value;
		addEventHandler(ReceiptComponentEvent.ON_BACK, value);
	}
	public EventHandler<ReceiptComponentEvent> getOnBack() {
		return onBackEventHandler;
	}

	/**
	 * Used mainly through FXML to set an event handler for the Checkout buttons action.
	 * Used in FXML like this: `onCheckout="#eventHandlerMethod"`
	 * @param    value    The event handler method.
	 */
	public void setOnCheckout(EventHandler<ReceiptComponentEvent> value) {
		if (onCheckoutEventHandler != null) {
			removeEventHandler(ReceiptComponentEvent.ON_CHECKOUT, onCheckoutEventHandler);
		}

		onCheckoutEventHandler = value;
		addEventHandler(ReceiptComponentEvent.ON_CHECKOUT, value);
	}
	public EventHandler<ReceiptComponentEvent> getOnCheckout() {
		return onCheckoutEventHandler;
	}

	public static class ReceiptComponentEvent extends Event {
		public static final EventType<ReceiptComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "RECEIPTCOMPONENT_ROOT_EVENT");
		public static final EventType<ReceiptComponentEvent> ON_BACK = new EventType<>(ROOT_EVENT, "ON_BACK");
		public static final EventType<ReceiptComponentEvent> ON_CHECKOUT = new EventType<>(ROOT_EVENT, "ON_CHECKOUT");

		public ReceiptComponentEvent(EventType<ReceiptComponentEvent> eventType) {
			super(eventType);
		}
	}
	//endregion
}