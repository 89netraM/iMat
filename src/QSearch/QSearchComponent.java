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

	private EventHandler<SearchComponentEvent> onSearchHandler;

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
		Event event = new SearchComponentEvent(
				this,
				IMatDataHandler.getInstance().findProducts(searchText.getText()),
				SearchComponentEvent.ON_SEARCH
		);
		fireEvent(event);
	}

	public void setOnSearch(EventHandler<SearchComponentEvent> onSelectHandler) {
		if (this.onSearchHandler != null) {
			removeEventHandler(SearchComponentEvent.ON_SEARCH, this.onSearchHandler);
		}

		this.onSearchHandler = onSelectHandler;
		addEventHandler(SearchComponentEvent.ON_SEARCH, this.onSearchHandler);
	}
	public EventHandler<SearchComponentEvent> getOnSearch() {
		return onSearchHandler;
	}

	public static class SearchComponentEvent extends Event {
		public static final EventType<SearchComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "SEARCHCOMPONENT_ROOT_EVENT");
		public static final EventType<SearchComponentEvent> ON_SEARCH = new EventType<>(ROOT_EVENT, "ON_SEARCH");

		private final List<Product> products;

		public SearchComponentEvent(QSearchComponent source, List<Product> foundProducts, EventType<SearchComponentEvent> eventType) {
			super(source, null, eventType);
			products = foundProducts;
		}

		public List<Product> getProducts() {
			return products;
		}
	}
}
