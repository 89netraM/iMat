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
		label.setText(category.toString());

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
		}
		else {
			this.getStyleClass().remove(SelectedClass);
		}
	}
	public boolean getIsSelected() {
		return isSelected;
	}
}
