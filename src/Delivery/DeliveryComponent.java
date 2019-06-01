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
    private AnchorPane deliveryInfo;

    @FXML
    private AnchorPane pickUpInfo;

    @FXML
    private Label deliveryDescription;

    private EventHandler<DeliveryComponentEvent> onResetHandler;

    private final Model model = Model.getInstance();
    private final OrderForm orderForm = OrderForm.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
   // OrderForm orderForm = new OrderForm();

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
    }

    public void updateReceipt(final Order order) {
        int gridRow = 0;

        this.orderContentsGrid.getChildren().clear();
        this.orderContentsGrid.setGridLinesVisible(true);
        this.orderContentsGrid.addRow(gridRow++, new Label("Vara"), new Label("Antal"), new Label("Totalt"));

        Double orderTotal = new Double(0);

        for (final ShoppingItem shoppingItem : order.getItems()) {
            Label name = new Label(shoppingItem.getProduct().getName());
            Label amount = new Label(Double.toString(shoppingItem.getAmount()));
            Label totalCost = new Label(Double.toString(shoppingItem.getTotal()));
            this.orderContentsGrid.addRow(gridRow++, name, amount, totalCost);
            orderTotal += shoppingItem.getTotal();
        }

        Label orderTotalText = new Label("Ordertotal");
        Label orderTotalCost = new Label(orderTotal.toString());
        this.orderContentsGrid.addRow(gridRow, orderTotalText, new Label(), orderTotalCost);

        updateCustomerAddress();
        pickUpAdress();
        deliveryInfo();
    }

    public void updateCustomerAddress() {
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        customerAddress.setText(customer.getAddress());
        customerPostAddress.setText(customer.getPostAddress());
        customerName.setText(
            String.format("%s %s", customer.getFirstName(), customer.getLastName())
        );
    }

    private void pickUpAdress(){

        pickUpName.setText("iMat");
        pickUpAdress.setText("Chalmersplatsen 4");
        pickUpPostAdress.setText("Göteborg");
        pickUpPostCode.setText("412 58");
    }

    private void deliveryInfo(){
        if (model.getDeliveryStatus2() != null && model.getDeliveryStatus2().equals("pickUp")) {
            pickUpAdress();
            pickUpInfo.toFront();
            deliveryInfo.toBack();
            deliveryDescription.setText("Du har möjlighet att hämta dina varor hos oss tidigast två timmar efter du lagt din beställning." +
                    " Ibland går det t.o.m. snabbare än så och då får du ett SMS när dina varor är redo för upphämtning.");
        }
        else {
            updateCustomerAddress();
            deliveryInfo.toFront();
            pickUpInfo.toBack();
            deliveryDescription.setText("Dina varor kommer levereras till dig samma dag om du lagt din beställning innan 12:00." +
                    " Annars kommer leverensen imorgon - gäller även helgdagar.");
        }
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