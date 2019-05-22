package CategoryCarousel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.File;
import java.io.IOException;

public class CategoryCarouselItemComponent extends GridPane {
	private static final String SelectedClass = "selected";

	@FXML
	private ImageView image;

	@FXML
	private Label label;

	private boolean isSelected = false;

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
		label.setText(getCategoryName(category));

		File file = new File(IMatDataHandler.getInstance().imatDirectory() + "/category_icons/" + category + ".png");
		Image imageSrc = new Image(file.toURI().toString(), 100.0d, 100.0d, true, true, true);
		image.setImage(imageSrc);
	}

	public void setIsSelected(boolean selected) {
		isSelected = selected;

		if (isSelected) {
			if (!this.getStyleClass().contains(SelectedClass)) {
				this.getStyleClass().add(SelectedClass);
			}

			image.setFitHeight(100.0d);
		}
		else {
			this.getStyleClass().remove(SelectedClass);

			image.setFitHeight(50.0d);
		}
	}
	public boolean getIsSelected() {
		return isSelected;
	}

	private String getCategoryName(ProductCategory category) {
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
