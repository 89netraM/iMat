package ProductItem;

import ProductCounter.ProductCounterComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductItemComponent extends AnchorPane implements Initializable {
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label price;

    @FXML
    private ProductCounterComponent productCounter;

    private Product product;

    private ShoppingCart shoppingCart;
    private ShoppingItem shoppingItem;

    public ProductItemComponent(Product product) {
        this.product = product;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItemComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        shoppingCart.addShoppingCartListener(this::onCartEvent);
        shoppingCart.addItem(shoppingItem);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File image = new File(IMatDataHandler.getInstance().imatDirectory() + "/images/" + this.product.getImageName());
        this.image.setImage(new Image(image.toURI().toString(), true));
        this.price.setText(Double.toString(this.product.getPrice()));
        this.description.setText(this.product.getName());

        shoppingItem = new ShoppingItem(this.product);
        shoppingItem.setAmount(0.0d);
        this.productCounter.setShoppingItem(shoppingItem);
    }

    private void onCartEvent(CartEvent e) {
        if (e.getShoppingItem() == shoppingItem) {
            productCounter.onCartEvent(e);

            /*if (shoppingItem.getAmount() > 0.0d && !shoppingCart.getItems().contains(shoppingItem)) {
                shoppingCart.addItem(shoppingItem);
            }*/
        }
    }
}
