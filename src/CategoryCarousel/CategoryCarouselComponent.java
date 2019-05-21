package CategoryCarousel;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.*;

public class CategoryCarouselComponent extends ScrollPane {
	private final IMatDataHandler dataHandler;

	@FXML
	private HBox box;

	private EventHandler<CategoryCarouselComponentEvent> onSelectHandler;

	private final Map<ProductCategory, CategoryCarouselItemComponent> carouselItems;
	private ProductCategory selectedCategory = ProductCategory.BREAD;

	public CategoryCarouselComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CategoryCarouselComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		dataHandler = IMatDataHandler.getInstance();

		carouselItems = generateCarouselItems();
		box.getChildren().addAll(carouselItems.values());

	}

	private Map<ProductCategory, CategoryCarouselItemComponent> generateCarouselItems() {
		Map<ProductCategory, CategoryCarouselItemComponent> items = new HashMap<>();

		for (ProductCategory pc : ProductCategory.values()) {
			items.put(pc, new CategoryCarouselItemComponent(pc));
		}

		return items;
	}

	public void setSelectedCategory(ProductCategory selectedCategory) {
		this.selectedCategory = selectedCategory;

		clearSelection();
		carouselItems.get(selectedCategory).setIsSelected(true);

		Event event = new CategoryCarouselComponentEvent(
				this,
				dataHandler.getProducts(selectedCategory),
				CategoryCarouselComponentEvent.ON_SELECT
		);
		fireEvent(event);
	}
	public ProductCategory getSelectedCategory() {
		return selectedCategory;
	}

	private void clearSelection() {
		for (CategoryCarouselItemComponent item : carouselItems.values()) {
			item.setIsSelected(false);
		}
	}

	public void setOnSelect(EventHandler<CategoryCarouselComponentEvent> onSelectHandler) {
		if (this.onSelectHandler != null) {
			removeEventHandler(CategoryCarouselComponentEvent.ON_SELECT, this.onSelectHandler);
		}

		this.onSelectHandler = onSelectHandler;
		addEventHandler(CategoryCarouselComponentEvent.ON_SELECT, this.onSelectHandler);
	}
	public EventHandler<CategoryCarouselComponentEvent> getOnSelect() {
		return onSelectHandler;
	}

	public static class CategoryCarouselComponentEvent extends Event {
		public static final EventType<CategoryCarouselComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "CATEGORYCAROUSELCOMPONENT_ROOT_EVENT");
		public static final EventType<CategoryCarouselComponentEvent> ON_SELECT = new EventType<>(ROOT_EVENT, "ON_SELECT");

		private final List<Product> products;

		public CategoryCarouselComponentEvent(CategoryCarouselComponent source, List<Product> selectedCategory, EventType<CategoryCarouselComponentEvent> eventType) {
			super(source, null, eventType);
			this.products = selectedCategory;
		}

		public List<Product> getProducts() {
			return products;
		}
	}
}