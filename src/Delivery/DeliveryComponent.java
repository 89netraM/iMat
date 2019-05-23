package Delivery;

import com.sun.javafx.scene.web.Debugger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;
import sun.awt.im.InputMethodAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DeliveryComponent extends AnchorPane implements Initializable {

    @FXML
    private GridPane orderContentsGrid;

    @FXML
    private Label customerName;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerCity;

    public DeliveryComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeliveryComponent.fxml"));
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
    }

    public void onResetButtonClick() {

    }

    public void updateReceipt(final Order order) {
        this.orderContentsGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0);

        Double orderTotal = new Double(0);
        for (final ShoppingItem shoppingItem : order.getItems()) {
            Label name = new Label(shoppingItem.toString());
            Label amount = new Label(Double.toString(shoppingItem.getAmount()));
            Label totalCost = new Label(Double.toString(shoppingItem.getTotal()));
            this.orderContentsGrid.addRow(this.getGridRowCount(this.orderContentsGrid), name, amount, totalCost);
            orderTotal += shoppingItem.getTotal();
        }

        Label orderTotalText = new Label("Totalt");
        Label orderTotalCost = new Label(orderTotal.toString());
        this.orderContentsGrid.addRow(this.getGridRowCount(this.orderContentsGrid), orderTotalText, orderTotalCost);
    }

    public void updateCustomerAddress(final Customer customer) {
        this.customerAddress.setText(customer.getAddress());
        this.customerCity.setText(customer.getPostAddress());
        this.customerName.setText(
            String.format("%s %s", customer.getFirstName(), customer.getLastName())
        );
    }

    // Shamelessly stolen from https://stackoverflow.com/questions/20766363/get-the-number-of-rows-in-a-javafx-gridpane
    private int getGridRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }

        return numRows;
    }
}