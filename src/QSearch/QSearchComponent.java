package QSearch;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.List;

public class QSearchComponent extends GridPane {
	@FXML
	private TextField searchText;

	private EventHandler<QSearchComponentEvent> onSearchHandler;

	public QSearchComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QSearchComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	private void doSearch() {
		Event event = new QSearchComponentEvent(
				this,
				IMatDataHandler.getInstance().findProducts(searchText.getText()),
				QSearchComponentEvent.ON_SEARCH
		);
		fireEvent(event);
	}

	public void setOnSearch(EventHandler<QSearchComponentEvent> onSelectHandler) {
		if (this.onSearchHandler != null) {
			removeEventHandler(QSearchComponentEvent.ON_SEARCH, this.onSearchHandler);
		}

		this.onSearchHandler = onSelectHandler;
		addEventHandler(QSearchComponentEvent.ON_SEARCH, this.onSearchHandler);
	}
	public EventHandler<QSearchComponentEvent> getOnSearch() {
		return onSearchHandler;
	}

	public static class QSearchComponentEvent extends Event {
		public static final EventType<QSearchComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "QSEARCHCOMPONENT_ROOT_EVENT");
		public static final EventType<QSearchComponentEvent> ON_SEARCH = new EventType<>(ROOT_EVENT, "ON_SEARCH");

		private final List<Product> products;

		public QSearchComponentEvent(QSearchComponent source, List<Product> foundProducts, EventType<QSearchComponentEvent> eventType) {
			super(source, null, eventType);
			products = foundProducts;
		}

		public List<Product> getProducts() {
			return products;
		}
	}
}
