import Animations.DoubleAnimation;
import CategoryCarousel.CategoryCarouselComponent;
import Delivery.DeliveryComponent;
import OrderForm.OrderForm;
import ProductList.ProductListComponent;
import QProducts.QProductListComponent;
import QSearch.QSearchComponent;
import Receipt.ReceiptComponent;
import Receipt.ReceiptItemComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private CategoryCarouselComponent categoryCarousel;

    @FXML
    private DeliveryComponent delivery;

    @FXML private ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	    categoryCarousel.setSelectedIndex(0);

        productList.setProducts(categoryCarousel.getSelectedCategoryProducts());
	    productList.setPrevious(categoryCarousel.getPreviousCategoryName());
	    productList.setNext(categoryCarousel.getNextCategoryName());

        File file = new File("resources/images/iMatLogo.png");
        Image imageSrc = new Image(file.toURI().toString());
        logo.setImage(imageSrc);
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

    private void slideToDelivery() {
        slideAnimation.play(masterBox.getLayoutX(), -2220.0d);
    }

    //endregion Slide Animation

    //region Product List
    //And the events that update it.

    @FXML
    private QProductListComponent productList;

    @FXML
    private void categorySelect(CategoryCarouselComponent.CategoryCarouselComponentEvent e) {
        productList.setProducts(e.getProducts());
	    productList.setPrevious(categoryCarousel.getPreviousCategoryName());
	    productList.setNext(categoryCarousel.getNextCategoryName());
    }
    @FXML
    private void onSearch(QSearchComponent.QSearchComponentEvent e) {
        productList.setProducts(e.getProducts());
	    productList.setPrevious(null);
	    productList.setNext(null);

        categoryCarousel.setSelectedCategory(null);
    }

    @FXML
    private void listPrevious() {
    	categoryCarousel.setSelectedIndex(categoryCarousel.getSelectedIndex() - 1);
    }
    @FXML
    private void listNext() {
	    categoryCarousel.setSelectedIndex(categoryCarousel.getSelectedIndex() + 1);
    }

    //endregion Product List

    //region Receipt

    @FXML
    private ReceiptComponent receipt;

    @FXML
    private OrderForm orderForm;

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

    @FXML
    private void toDelivery() {
        slideToDelivery();
        receipt.setCheckoutButtonEnabled(true);
        this.delivery.updateCustomerAddress();
        this.delivery.updateReceipt(IMatDataHandler.getInstance().getOrders().iterator().next());
    }

    //endregion Receipt Events
}