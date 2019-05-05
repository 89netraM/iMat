package Receipt;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReceiptItemComponent extends VBox {
	@FXML
	private Label nameLabel;

	@FXML
	private Label totalLabel;

	private EventHandler<ReceiptItemComponentEvent> onRemoveEventHandler;

	private ShoppingItem item;

	public ReceiptItemComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReceiptItemComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	void onCartEvent(CartEvent e) {
		if (e.getShoppingItem() == item) {
			updateUI();
		}
	}

	private void updateUI() {
		nameLabel.setText(item.getProduct().getName());
		totalLabel.setText(item.getTotal() + " kr");
	}

	@FXML
	private void onRemove() {
		ReceiptItemComponentEvent onRemoveEvent = new ReceiptItemComponentEvent(this, ReceiptItemComponentEvent.ON_REMOVE);
		fireEvent(onRemoveEvent);
	}

	public void setItem(ShoppingItem value) {
		item = value;
		updateUI();
	}
	public ShoppingItem getItem() {
		return item;
	}

	public void setOnRemove(EventHandler<ReceiptItemComponentEvent> value) {
		onRemoveEventHandler = value;
		addEventHandler(ReceiptItemComponentEvent.ON_REMOVE, value);
	}
	public EventHandler<ReceiptItemComponentEvent> getOnRemove() {
		return onRemoveEventHandler;
	}

	public static class ReceiptItemComponentEvent extends Event {
		public static final EventType<ReceiptItemComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "ROOT_EVENT");
		public static final EventType<ReceiptItemComponentEvent> ON_REMOVE = new EventType<>(ROOT_EVENT, "ON_REMOVE");

		public ReceiptItemComponentEvent(ReceiptItemComponent source, EventType<ReceiptItemComponentEvent> eventType) {
			super(source, null, eventType);
		}

		@Override
		public ReceiptItemComponent getSource() {
			return (ReceiptItemComponent)super.getSource();
		}
	}
}