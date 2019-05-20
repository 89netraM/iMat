package ProductItem;

import ProductCounter.ProductCounterComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductItemComponent implements Initializable {
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label price;

    @FXML
    private ProductCounterComponent productCounter;

    private Product product;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.product = IMatDataHandler.getInstance().getProducts().get(0);
        this.loadFromProduct(this.product);
    }

    private void loadFromProduct(Product product) {
        File image = new File(IMatDataHandler.getInstance().imatDirectory() + "/images/" + this.product.getImageName());
        this.image.setImage(new Image(image.toURI().toString(), true));
        this.price.setText(Double.toString(product.getPrice()));
        this.description.setText(product.getName());
    }
}
