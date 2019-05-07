package components;

import components.ProductItem.ProductItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ProductList extends VBox {
    private ArrayList<ProductItem> productItems;

    public ProductList() {
        this.productItems = new ArrayList<ProductItem>();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/ProductList/ProductList.xml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ArrayList<ProductItem> getProductItems() {
        return productItems;
    }

    public void addProductItem(ProductItem productItem) {
        this.productItems.add(productItem);
    }

    public void removeProductItem(ProductItem productItem) {
        this.productItems.remove(productItem);
    }

    public void clearProductItems(){
        this.productItems.clear();
    }
}
