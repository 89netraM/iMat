package ProductItem;

import ProductCounter.ProductCounterComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File image = new File(IMatDataHandler.getInstance().imatDirectory() + "/images/" + this.product.getImageName());
        this.image.setImage(new Image(image.toURI().toString(), true));
        this.price.setText(Double.toString(this.product.getPrice()));
        this.description.setText(this.product.getName());
        this.productCounter.setShoppingItem(new ShoppingItem(this.product));
    }
}
