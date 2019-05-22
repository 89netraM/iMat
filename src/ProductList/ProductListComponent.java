package ProductList;

import ProductItem.ProductItemComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListComponent extends FlowPane {
    @FXML
    private FlowPane flowPane;

    public ProductListComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addProduct(final Product product) {
        ProductItemComponent productItem = new ProductItemComponent(product);
        this.flowPane.getChildren().add(productItem);
    }

    public void setProducts(final List<Product> products) {
        this.clear();
        for (Product product : products) {
            this.addProduct(product);
        }
    }

    public void clear() {
        this.flowPane.getChildren().clear();
    }
}
