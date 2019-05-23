import Animations.DoubleAnimation;
import CategoryCarousel.CategoryCarouselComponent;
import ProductList.ProductListComponent;
import QProducts.QProductListComponent;
import QSearch.QSearchComponent;
import Receipt.ReceiptComponent;
import Receipt.ReceiptItemComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private CategoryCarouselComponent categoryCarousel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productList.setProducts(categoryCarousel.getSelectedCategoryProducts());
    }

    //region Slide Animation

    @FXML
    private HBox masterBox;
    private DoubleAnimation slideAnimation = new DoubleAnimation(this::slideAction, Duration.millis(300));

    private void slideAction(double value) {
        masterBox.setLayoutX(value);
    }

    private void slideToCheckout() {
        slideAnimation.play(masterBox.getLayoutX(), -940.0d);
    }

    private void slideToStart() {
        slideAnimation.play(masterBox.getLayoutX(), 0.0d);
    }

    //endregion Slide Animation

    //region Product List
    //And the events that update it.

    @FXML
    private QProductListComponent productList;

    @FXML
    private void categorySelect(CategoryCarouselComponent.CategoryCarouselComponentEvent e) {
        productList.setProducts(e.getProducts());
    }
    @FXML
    private void onSearch(QSearchComponent.QSearchComponentEvent e) {
        productList.setProducts(e.getProducts());
        categoryCarousel.setSelectedCategory(null);
    }

    //endregion Product List

    //region Receipt

    @FXML
    private ReceiptComponent receipt;

    @FXML
    private void toCheckout() {
        slideToCheckout();
        receipt.setBackButtonEnabled(true);
    }

    @FXML
    private void toStart() {
        slideToStart();
        receipt.setCheckoutButtonEnabled(true);
    }

    //endregion Receipt Events
}