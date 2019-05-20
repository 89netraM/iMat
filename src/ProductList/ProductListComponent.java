package ProductList;

import ProductItem.ProductItemComponent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

public class ProductListComponent {
    private ArrayList<ProductItemComponent> productItemComponents;

    public ProductListComponent() {
        this.productItemComponents = new ArrayList<>();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductList/ProductListComponent.xml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ArrayList<ProductItemComponent> getProductItemComponents() {
        return this.productItemComponents;
    }

    public void addProductItemComponent(final ProductItemComponent ProductItemComponent) {
        this.productItemComponents.add(ProductItemComponent);
    }

    public void clearProductItemComponents(){
        this.productItemComponents.clear();
    }
}
