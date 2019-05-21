package ProductList;

import ProductItem.ProductItemComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListComponent extends VBox {
    private ArrayList<ProductItemComponent> productItemComponents;

    @FXML
    private VBox vbox;

    public ProductListComponent() {
        this.productItemComponents = new ArrayList<>();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addProduct(Product product) {
        ProductItemComponent productItem = new ProductItemComponent(product);
        this.vbox.getChildren().add(productItem);
    }

    public void setProducts(List<Product> products) {
        this.clear();

        for (Product product : products) {
            this.addProduct(product);
        }
    }

    public void clear() {
        this.vbox.getChildren().clear();
    }


}
