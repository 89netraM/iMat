package CategoryCarousel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class CategoryCarouselItemComponent extends GridPane {
	private static final String SelectedClass = "selected";

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
