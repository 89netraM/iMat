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

	private ReceiptItemComponent lastRemoved;
	private int lastIndex;
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

	private EventHandler<ReceiptComponentEvent> onBackEventHandler;
	private EventHandler<ReceiptComponentEvent> onCheckoutEventHandler;

	private final ShoppingCart cart;
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
				receiptItems.put(product.getProductId(), lastRemoved);
				receiptList.getChildren().add(lastIndex, lastRemoved);

				//It shouldn't be out of sync, but for safety's sake:
				lastRemoved.onCartEvent(e);

				lastRemoved = null;
				undoPane.setVisible(false);
			}
			else {
				//Creates new UI component for the shopping item
				ReceiptItemComponent item = new ReceiptItemComponent();
				item.setItem(e.getShoppingItem());
				item.setOnRemove(this::onRemoveItem);

				receiptItems.put(product.getProductId(), item);
				receiptList.getChildren().add(item);
			}
		}
		else {
			if (e.getShoppingItem().getAmount() <= 0.0d || !cart.getItems().contains(e.getShoppingItem())) {
				//Updates existing UI component for the shopping item
				lastRemoved = receiptItems.remove(product.getProductId());

				//If for some reason it's already gone, we can't remove it again.
				if (lastRemoved != null) {
					lastIndex = receiptList.getChildren().indexOf(lastRemoved);

					receiptList.getChildren().remove(lastRemoved);

					if (lastRemoved.getItem().getAmount() <= 0.0d) {
						lastRemoved.getItem().setAmount(1.0d);
					}

					undoPane.setVisible(true);
				}
			}
			else {
				receiptItems.get(product.getProductId()).onCartEvent(e);
			}
		}
	}

	private void onRemoveItem(ReceiptItemComponent.ReceiptItemComponentEvent e) {
		cart.removeItem(e.getSource().getItem());
	}
	@FXML
	private void onUndoButton() {
		if (lastRemoved != null) {
			cart.addItem(lastRemoved.getItem());
		}
	}

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

	public void setOnBack(EventHandler<ReceiptComponentEvent> value) {
		onBackEventHandler = value;
		addEventHandler(ReceiptComponentEvent.ON_BACK, value);
	}
	public EventHandler<ReceiptComponentEvent> getOnBack() {
		return onBackEventHandler;
	}
	public void setOnCheckout(EventHandler<ReceiptComponentEvent> value) {
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
}