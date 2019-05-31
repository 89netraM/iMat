package CategoryCarousel;


import Animations.DoubleAnimation;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CategoryCarouselComponent extends GridPane {
	private static final double scrollLength = 3.0d;

	private final IMatDataHandler dataHandler;

	private final DoubleAnimation scrollAnimation = new DoubleAnimation(this::scrollAnimator, Duration.millis(300));

	@FXML
	private HBox box;

	@FXML
	private ScrollPane scrollBox;
	@FXML
	private ImageView arrowRight;
	@FXML
	private ImageView arrowLeft;
	private EventHandler<CategoryCarouselComponentEvent> onSelectHandler;

	private final List<CategoryCarouselItemComponent> carouselItems;
	private Categories.Category selectedCategory = Categories.get(0);

	File file2 = new File("resources/images/Arrow_-_Left-512.png");
	Image imageSrc2 = new Image(file2.toURI().toString());

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
		box.getChildren().addAll(carouselItems);

		scrollBox.widthProperty().addListener(this::widthListener);

		arrowRight.setImage(imageSrc2);
		arrowLeft.setImage(imageSrc2);
	}



	private void widthListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		calculateInnerPadding();
	}

	private void calculateInnerPadding() {
		//CategoryItem width: 100.0d. Should find better way of getting the width.
		double padding = (scrollBox.getWidth() - 100.0d) / 2.0d;
		box.setPadding(new Insets(0, padding, 0, padding));
	}

	//Called when the user clicks the left "scroll" button.
	@FXML
	private void leftClick() {
		//Scrolls `scrollLength` items to the left.
		scrollAnimation.play(scrollBox.getHvalue(), scrollBox.getHvalue() - (scrollLength / (carouselItems.size() - 1)));
	}
	//Called when the user clicks the right "scroll" button.
	@FXML
	private void rightClick() {
		//Scrolls `scrollLength` items to the left.
		scrollAnimation.play(scrollBox.getHvalue(), scrollBox.getHvalue() + (scrollLength / (carouselItems.size() - 1)));
	}

	//Is the action of the `scrollAnimator`.
	//Every step of the animation this method updates the horizontal scroll position.
	private void scrollAnimator(double value) {
		scrollBox.setHvalue(value);
	}

	private List<CategoryCarouselItemComponent> generateCarouselItems() {
		List<CategoryCarouselItemComponent> items = new LinkedList<>();

		for (Categories.Category c : Categories.values()) {
			CategoryCarouselItemComponent item = new CategoryCarouselItemComponent(c);
			item.setOnMouseClicked(m -> setSelectedCategory(c));

			items.add(item);
		}

		return items;
	}

	public void setSelectedCategory(Categories.Category selectedCategory) {
		this.selectedCategory = selectedCategory;

		clearSelection();

		int index = Categories.indexOf(selectedCategory);
		carouselItems.get(index).setIsSelected(true);
		scrollAnimation.play(scrollBox.getHvalue(), getScrollPositionOfIndex(index));

		Event event = new CategoryCarouselComponentEvent(
				this,
				getSelectedCategoryProducts(),
				CategoryCarouselComponentEvent.ON_SELECT
		);
		fireEvent(event);
	}

	/**
	 * Gets the scroll position (0.0 - 1.0) of the item with the index `index`.
	 * @param    index    The index of the intended element.
	 * @return    Returns the scroll position of the element (0.0 - 1.0).
	 */
	private double getScrollPositionOfIndex(int index) {
		return index / (carouselItems.size() - 1.0d);
	}

	public Categories.Category getSelectedCategory() {
		return selectedCategory;
	}

	//Un selects all category items.
	private void clearSelection() {
		for (CategoryCarouselItemComponent item : carouselItems) {
			item.setIsSelected(false);
		}
	}

	/**
	 * Gets the currently selected category.
	 * @return    Returns the currently selected category.
	 */
	public List<Product> getSelectedCategoryProducts() {
		return selectedCategory.getProducts();
	}

	/**
	 * Sets the event handler for when a new category is selected.
	 * Can be used from `fxml` as: `onSelect="#eventHandelr"`.
	 * @param    onSelectHandler    The new event handler.
	 */
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

	public void setSelectedIndex(Integer selectedIndex) {
		setSelectedCategory(selectedIndex != null ? Categories.get(selectedIndex) : null);
	}
	public Integer getSelectedIndex() {
		return selectedCategory != null ? Categories.indexOf(selectedCategory) : null;
	}

	public String getPreviousCategoryName() {
		if (getSelectedIndex() != null && getSelectedIndex() > 0) {
			return Categories.get(getSelectedIndex() - 1).getName();
		}
		else {
			return null;
		}
	}
	public String getNextCategoryName() {
		if (getSelectedIndex() != null && getSelectedIndex() < Categories.size() - 1) {
			return Categories.get(getSelectedIndex() + 1).getName();
		}
		else {
			return null;
		}
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