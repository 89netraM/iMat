package CategoryCarousel;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class CategoryCarouselComponent extends ScrollPane {
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
	}
	public void setSelectedCategory(ProductCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	public ProductCategory getSelectedCategory() {
		return selectedCategory;
	}

}