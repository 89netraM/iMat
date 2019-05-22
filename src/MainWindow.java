import ProductList.ProductListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private Pane searchPane;

    @FXML
    private ScrollPane productsPane;

    @FXML
    private Pane receiptPane;

    @FXML
    private Pane paymentPane;

    @FXML
    private Pane deliveryPane;

    @FXML
    private ProductListComponent productListController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.productListController.setProducts(IMatDataHandler.getInstance().getProducts());
    }
}