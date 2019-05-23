package CategoryCarousel;

import Animations.DoubleAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.File;
import java.io.IOException;

public class CategoryCarouselItemComponent extends GridPane {
	@FXML
	private ImageView image;

	@FXML
	private Label label;

	private boolean isSelected = false;
	private final DoubleAnimation selectedAnimation = new DoubleAnimation(this::animationAction, Duration.millis(300));

	private final ProductCategory category;

	public CategoryCarouselItemComponent(ProductCategory category) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CategoryCarouselItemComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.category = category;

		//Sets the label to a nice name.
		label.setText(getCategoryName(category));

		//Sets the image from a category icon.
		File file = new File(IMatDataHandler.getInstance().imatDirectory() + "/category_icons/" + category + ".png");
		Image imageSrc = new Image(file.toURI().toString(), 100.0d, 100.0d, true, true, true);
		image.setImage(imageSrc);
	}

	/**
	 * Sets whether or not this is category is selected.
	 * If the selection change it's shown with an animation.
	 * @param    isSelected    The new selection state.
	 */
	public void setIsSelected(boolean isSelected) {
		if (this.isSelected != isSelected) {
			selectedAnimation.play(image.getFitHeight(), isSelected ? 100.0d : 50.0d);
		}

		this.isSelected = isSelected;
	}
	public boolean getIsSelected() {
		return isSelected;
	}

	//Is the action of the `selectedAnimation`.
	//Every step of the animation this method updates the height of the image.
	private void animationAction(double value) {
		image.setFitHeight(value);
	}

	/**
	 * Turns a `ProductCategory` enum into a human readable name.
	 * @param    category    The category.
	 * @return    Returns a human readable name.
	 */
	public static String getCategoryName(ProductCategory category) {
		switch (category) {
			case POD:
				return "Baljväxter";
			case BREAD:
				return "Bröd";
			case BERRY:
				return "Bär";
			case CITRUS_FRUIT:
				return "Citrus frukter";
			case HOT_DRINKS:
				return "Varma drycker";
			case COLD_DRINKS:
				return "Kalla drycker";
			case EXOTIC_FRUIT:
				return "Exotiska frukter";
			case FISH:
				return "Fisk";
			case VEGETABLE_FRUIT:
				return "Grönsaker";
			case CABBAGE:
				return "Kol";
			case MEAT:
				return "Kött";
			case DAIRIES:
				return "Mejeri";
			case MELONS:
				return "Meloner";
			case FLOUR_SUGAR_SALT:
				return "Torra varor";
			case NUTS_AND_SEEDS:
				return "Nötter och frön";
			case PASTA:
				return "Pasta";
			case POTATO_RICE:
				return "Potatis och ris";
			case ROOT_VEGETABLE:
				return "Rotfrukter";
			case FRUIT:
				return "Frukter";
			case SWEET:
				return "Godis";
			case HERB:
				return "Örter";
		}

		return category.toString();
	}
}
