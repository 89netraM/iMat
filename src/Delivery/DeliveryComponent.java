package Delivery;

import OrderForm.Model;
import OrderForm.OrderForm;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryComponent extends AnchorPane implements Initializable {

    @FXML
    private GridPane orderContentsGrid;

    @FXML
    private Label customerName;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerPostAddress;

    @FXML
    private ImageView logo;

    @FXML
    private Label pickUpName;

    @FXML
    private Label pickUpAdress;

    @FXML
    private Label pickUpPostAdress;

    @FXML
    private Label pickUpPostCode;

    @FXML
    public AnchorPane deliveryInfo;

    @FXML
    public AnchorPane pickUpInfo;

    private EventHandler<DeliveryComponentEvent> onResetHandler;

    private final Model model = Model.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    Customer customer = model.getCustomer();

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

        File file = new File("resources/images/iMatLogo.png");
        Image imageSrc = new Image(file.toURI().toString());
        logo.setImage(imageSrc);

        updateCustomerAddress();
        pickUpAdress();


    }

    public void updateReceipt(final Order order) {
        //this.orderContentsGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0);

        Double orderTotal = new Double(0);

        for (final ShoppingItem shoppingItem : order.getItems()) {
            Label name = new Label(shoppingItem.getProduct().getName());
            name.getStyleClass().add("order-item");
            Label amount = new Label(Double.toString(shoppingItem.getAmount()));
            amount.getStyleClass().add("order-item");
            Label totalCost = new Label(Double.toString(shoppingItem.getTotal()));
            totalCost.getStyleClass().add("order-item");
            this.orderContentsGrid.addRow(this.getGridRowCount(this.orderContentsGrid), name, amount, totalCost);
            orderTotal += shoppingItem.getTotal();
        }

        Label orderTotalText = new Label("Totalt");
        Label orderTotalCost = new Label(orderTotal.toString());
        this.orderContentsGrid.addRow(this.getGridRowCount(this.orderContentsGrid), orderTotalText, orderTotalCost);
    }

    public void updateCustomerAddress() {
        customerAddress.setText(customer.getAddress());
        customerPostAddress.setText(customer.getPostAddress());
        customerName.setText(
            String.format("%s %s", customer.getFirstName(), customer.getLastName())
        );
    }

    public void pickUpAdress(){

        pickUpName.setText("iMat");
        pickUpAdress.setText("Chalmersplatsen 4");
        pickUpPostAdress.setText("Göteborg");
        pickUpPostCode.setText("412 58");
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
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

    @FXML
    private void onResetButtonClick() {
        DeliveryComponentEvent onResetEvent = new DeliveryComponentEvent(this, DeliveryComponentEvent.ON_RESET);
        fireEvent(onResetEvent);
    }


    public void setOnReset(EventHandler<DeliveryComponentEvent> onResetHandler) {
        if (this.onResetHandler != null) {
            removeEventHandler(DeliveryComponent.DeliveryComponentEvent.ON_RESET, this.onResetHandler);
        }
        this.onResetHandler = onResetHandler;
        addEventHandler(DeliveryComponent.DeliveryComponentEvent.ON_RESET, this.onResetHandler);
    }

    public EventHandler<DeliveryComponent.DeliveryComponentEvent> getOnReset() {
        return onResetHandler;
    }


    public static class DeliveryComponentEvent extends Event {
        public DeliveryComponentEvent(DeliveryComponent source, EventType<DeliveryComponentEvent> eventType) {
            super(source, null, eventType);
        }

        public static final EventType<DeliveryComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "DELIVERY_COMPONENTROOT_EVENT");
        public static final EventType<DeliveryComponentEvent> ON_RESET = new EventType<>(ROOT_EVENT, "ON_RESET");
    }

}