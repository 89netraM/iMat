package Delivery;

import OrderForm.Model;
import OrderForm.OrderForm;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryComponent extends AnchorPane implements Initializable {
    private final Model model = Model.getInstance();
    private final OrderForm orderForm = OrderForm.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private TableView orderContents;
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
    @FXML
    private TableColumn<Row, String> colProduct;
    @FXML
    private TableColumn<Row, String> colAmount;
    @FXML
    private TableColumn<Row, String> colTotal;
    private EventHandler<DeliveryComponentEvent> onResetHandler;
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
        this.colAmount.setCellValueFactory(new PropertyValueFactory<Row, String>("amount"));
        this.colProduct.setCellValueFactory(new PropertyValueFactory<Row, String>("name"));
        this.colTotal.setCellValueFactory(new PropertyValueFactory<Row, String>("total"));
    }

    public void updateReceipt(final Order order) {
        int gridRow = 0;
        this.orderContents.getItems().clear();
        double orderTotal = 0;
        for (final ShoppingItem shoppingItem : order.getItems()) {
            orderContents.getItems().add(new Row(
                    shoppingItem.getProduct().getName(),
                    String.format("%.2f", shoppingItem.getAmount()),
                    String.format("%.2f", shoppingItem.getTotal())
                )
            );
            orderTotal += shoppingItem.getTotal();
        }
        this.orderContents.getItems().add(new Row("Ordertotal", "", String.format("%.2f", orderTotal)));
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

    private void pickUpAdress() {
        pickUpName.setText("iMat");
        pickUpAdress.setText("Chalmersplatsen 4");
        pickUpPostAdress.setText("Göteborg");
        pickUpPostCode.setText("412 58");
    }

    private void deliveryInfo() {
        if (model.getDeliveryStatus2() != null && model.getDeliveryStatus2().equals("pickUp")) {
            pickUpAdress();
            pickUpInfo.toFront();
            deliveryInfo.toBack();
            deliveryDescription.setText("Du har möjlighet att hämta dina varor hos oss tidigast två timmar efter du lagt din beställning." +
                " Ibland går det t.o.m. snabbare än så och då får du ett SMS när dina varor är redo för upphämtning.");
        } else {
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

    public EventHandler<DeliveryComponent.DeliveryComponentEvent> getOnReset() {
        return onResetHandler;
    }

    public void setOnReset(EventHandler<DeliveryComponentEvent> onResetHandler) {
        if (this.onResetHandler != null) {
            removeEventHandler(DeliveryComponent.DeliveryComponentEvent.ON_RESET, this.onResetHandler);
        }
        this.onResetHandler = onResetHandler;
        addEventHandler(DeliveryComponent.DeliveryComponentEvent.ON_RESET, this.onResetHandler);
    }

    public static class DeliveryComponentEvent extends Event {
        public static final EventType<DeliveryComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "DELIVERY_COMPONENTROOT_EVENT");
        public static final EventType<DeliveryComponentEvent> ON_RESET = new EventType<>(ROOT_EVENT, "ON_RESET");
        public DeliveryComponentEvent(DeliveryComponent source, EventType<DeliveryComponentEvent> eventType) {
            super(source, null, eventType);
        }
    }

    public class Row {
        private String name;
        private String amount;
        private String total;

        public Row(String name, String amount, String total) {
            this.name = name;
            this.amount = amount;
            this.total = total;
        }

        public String getName() {
            return this.name;
        }

        public String getAmount() {
            return amount;
        }

        public String getTotal() {
            return total;
        }
    }
}