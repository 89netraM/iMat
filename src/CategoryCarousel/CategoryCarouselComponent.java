package CategoryCarousel;

import Animations.ValueAnimation;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.*;

public class CategoryCarouselComponent extends ScrollPane {
	private final IMatDataHandler dataHandler;

	private final ValueAnimation scrollAnimation;

	@FXML
	private HBox box;

	private EventHandler<CategoryCarouselComponentEvent> onSelectHandler;

	private final List<CategoryCarouselItemComponent> carouselItems;
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

		scrollAnimation = new ValueAnimation(Duration.millis(300), this::scrollAnimator);

		carouselItems = generateCarouselItems();
		box.getChildren().addAll(carouselItems);
		//Selects the first category
		setSelectedCategory(ProductCategory.values()[0]);
	}

	private void scrollAnimator(double value) {
		this.setHvalue(value);
	}

	private List<CategoryCarouselItemComponent> generateCarouselItems() {
		List<CategoryCarouselItemComponent> items = new LinkedList<>();

		for (ProductCategory pc : ProductCategory.values()) {
			CategoryCarouselItemComponent item = new CategoryCarouselItemComponent(pc);
			item.setOnMouseClicked(m -> setSelectedCategory(pc));

			items.add(item);
		}

		return items;
	}

	public void setSelectedCategory(ProductCategory selectedCategory) {
		this.selectedCategory = selectedCategory;

		clearSelection();

		carouselItems.get(selectedCategory.ordinal()).setIsSelected(true);
		scrollAnimation.play(getHvalue(), getScrollPositionOfIndex(selectedCategory.ordinal()));

		Event event = new CategoryCarouselComponentEvent(
				this,
				getSelectedCategoryProducts(),
				CategoryCarouselComponentEvent.ON_SELECT
		);
		fireEvent(event);
	}

	private double getScrollPositionOfIndex(int index) {
		return index / (carouselItems.size() - 1.0d);
	}

	public ProductCategory getSelectedCategory() {
		return selectedCategory;
	}

	private void clearSelection() {
		for (CategoryCarouselItemComponent item : carouselItems) {
			item.setIsSelected(false);
		}
	}

	public List<Product> getSelectedCategoryProducts() {
		return dataHandler.getProducts(selectedCategory);
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