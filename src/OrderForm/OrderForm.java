package OrderForm;

import Delivery.DeliveryComponent;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderForm extends AnchorPane implements Initializable {
    private final Model model = Model.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    CreditCard card = model.getCreditCard();
    Customer customer = model.getCustomer();

    @FXML
    private AnchorPane OrderFormComponent;
    @FXML
    private AnchorPane OrderForm;
    @FXML
    private Button changeAdress;
    @FXML
    private Button changePaymentMethod;
    @FXML
    private AnchorPane AdressForm;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private TextField postCode;
    @FXML
    private TextField postAddress;
    @FXML
    private Button saveAddressInfo;
    @FXML
    private AnchorPane PaymentForm;
    @FXML
    private Button savePaymentInfo;
    @FXML
    private TextField firstAndLastName;
    @FXML
    private ComboBox cardType;
    @FXML
    private TextField cardNumber;
    @FXML
    private ComboBox month;
    @FXML
    private ComboBox year;
    @FXML
    private TextField cvcCode;
    @FXML
    public RadioButton pickUp;
    @FXML
    public RadioButton delivery;
    @FXML
    private Label namePreview;
    @FXML
    private Label cardPreview;
    @FXML
    private Label cardTypePreview;
    @FXML
    private Label adressPreview;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView logoMini;
    @FXML
    private AnchorPane confirmOrder;
    @FXML
    private Button declineOrder;
    @FXML
    private Button completeOrder;
    @FXML
    private Label totalPrice;


    private static OrderForm instance = null;

    public static OrderForm getInstance() {
        if (instance == null) {
            instance = new OrderForm();
        }
        return instance;
    }






    private EventHandler<OrderFormEvent> onNextHandler;
    public OrderForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderForm.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup deliveryToggleGroup = new ToggleGroup();
        delivery.setToggleGroup(deliveryToggleGroup);
        pickUp.setToggleGroup(deliveryToggleGroup);
        delivery.setSelected(true);


        deliveryToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (deliveryToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) deliveryToggleGroup.getSelectedToggle();

                    if (selected == delivery) {
                        model.setDeliveryStatus2("delivery");
                        System.out.println("Hemleverans");
                        System.out.println(model.getDeliveryStatus2());
                    } else if(selected == pickUp) {
                        model.setDeliveryStatus2("pickUp");
                        System.out.println("pickup");
                        System.out.println(model.getDeliveryStatus2());

                    }
                }


            }

        });

        // iMatDataHandler.resetFirstRun();      //kallas bara för att återställa hela programmet
        if (iMatDataHandler.isFirstRun()) {
            iMatDataHandler.reset();
        }
        File file = new File("resources/images/iMatLogo.png");
        Image imageSrc = new Image(file.toURI().toString());
        logo.setImage(imageSrc);
        /*File file2 = new File("resources/images/iMatLogo.png");
        Image imageSrc2 = new Image(file2.toURI().toString());
        logoMini.setImage(imageSrc);*/
        setupPaymentPane();
        updatePreview();
        OrderForm.toFront();
    }

    private void updatePreview() {
        namePreview.setText(customer.getFirstName() + " " + customer.getLastName());
        adressPreview.setText(customer.getAddress());
        //cardPreview.setText(card.getCardNumber());
        cardTypePreview.setText(card.getCardType());
        cardPreview.setText(formatCard(card));
    }

    private void updateCreditCard() {
        card.setCardNumber(cardNumber.getText());
        card.setHoldersName(firstAndLastName.getText());
        String selectedValue = (String) cardType.getSelectionModel().getSelectedItem();
        card.setCardType(selectedValue);
        selectedValue = (String) month.getSelectionModel().getSelectedItem();
        card.setValidMonth(Integer.parseInt(selectedValue));
        selectedValue = (String) year.getSelectionModel().getSelectedItem();
        card.setValidYear(Integer.parseInt(selectedValue));
        card.setVerificationCode(Integer.parseInt(cvcCode.getText()));
    }

    private void updateCustomer() {
        customer.setFirstName(firstName.getText());
        customer.setLastName(lastName.getText());
        customer.setEmail(email.getText());
        customer.setPhoneNumber(phone.getText());
        customer.setAddress(address.getText());
        customer.setPostCode(postCode.getText());
        customer.setPostAddress(postAddress.getText());
    }

    private void updatePaymentFormPanel() {
        cardNumber.setText(card.getCardNumber());
        firstAndLastName.setText(card.getHoldersName());
        cardType.getSelectionModel().select(card.getCardType());
        month.getSelectionModel().select("" + card.getValidMonth());
        year.getSelectionModel().select("" + card.getValidYear());
        cvcCode.setText("" + card.getVerificationCode());
        // purchasesLabel.setText(model.getNumberOfOrders()+ " tidigare inköp hos iMat");
    }

    private void updateAddressFormPanel() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        phone.setText(customer.getPhoneNumber());
        address.setText(customer.getAddress());
        postCode.setText(customer.getPostCode());
        postAddress.setText(customer.getPostAddress());
    }

    private void setupPaymentPane() {
        cardType.getItems().addAll(model.getCardTypes());
        month.getItems().addAll(model.getMonths());
        year.getItems().addAll(model.getYears());
    }

    @FXML
    private void addressFormToFront() {
        AdressForm.toFront();
        OrderForm.toBack();
        updateAddressFormPanel();
    }

    @FXML
    private void paymentFormToFront() {
        PaymentForm.toFront();
        OrderForm.toBack();
        updatePaymentFormPanel();
    }

    @FXML
    private void saveAddressInfo() {
        OrderForm.toFront();
        AdressForm.toBack();
        updateCustomer();
        updatePreview();
    }

    @FXML
    private void savePaymentInfo() {
        OrderForm.toFront();
        PaymentForm.toBack();
        updateCreditCard();
        updatePreview();
    }
    @FXML
    private void confirmOrder(){
        String total = Double.toString(iMatDataHandler.getShoppingCart().getTotal());
        totalPrice.setText(total + " kr");
        confirmOrder.toFront();


    }

    @FXML
    private void declineOrder(){
        confirmOrder.toBack();
        OrderForm.toFront();
    }

    private String formatCard(CreditCard card) {
        return card.getCardNumber() != null ? card.getCardNumber().replaceAll(".{4}(?!$)", "**** ") : "";
    }

    @FXML
    private void onNextButton() {
        Order order = this.iMatDataHandler.placeOrder();

        OrderForm.OrderFormEvent onNextEvent = new OrderForm.OrderFormEvent(this, order, OrderFormEvent.ON_NEXT);
        fireEvent(onNextEvent);
        confirmOrder.toBack();

    }

    public EventHandler<OrderFormEvent> getOnNext() {
        return onNextHandler;
    }

    public void setOnNext(EventHandler<OrderFormEvent> onNextHandler) {
        if (this.onNextHandler != null) {
            removeEventHandler(OrderFormEvent.ON_NEXT, this.onNextHandler);
        }
        this.onNextHandler = onNextHandler;
        addEventHandler(OrderFormEvent.ON_NEXT, this.onNextHandler);
    }

    public static class OrderFormEvent extends Event {
        public Order order;
        public static final EventType<OrderForm.OrderFormEvent> ROOT_EVENT = new EventType<>(Event.ANY, "ORDER_FORM_ROOT_EVENT");
        public static final EventType<OrderForm.OrderFormEvent> ON_NEXT = new EventType<>(ROOT_EVENT, "ON_NEXT");

        public OrderFormEvent(OrderForm source, Order order, EventType<OrderForm.OrderFormEvent> eventType) {
            super(source, null, eventType);
            this.order = order;
        }
    }


}
