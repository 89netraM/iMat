package OrderForm;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

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
    private RadioButton pickUp;
    @FXML
    private RadioButton delivery;
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
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        delivery.setToggleGroup(difficultyToggleGroup);
        pickUp.setToggleGroup(difficultyToggleGroup);
        delivery.setSelected(true);
        // iMatDataHandler.resetFirstRun();      //kallas bara för att återställa hela programmet
        if (iMatDataHandler.isFirstRun()) {
            iMatDataHandler.reset();
        }
        File file = new File("resources/images/iMatLogo.png");
        Image imageSrc = new Image(file.toURI().toString());
        logo.setImage(imageSrc);
        setupPaymentPane();
        updatePreview();
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

    private String formatCard(CreditCard card) {
        String cardNumber = card.getCardNumber();
        String fc = cardNumber;
        if (fc == null) return null;
        char delimiter = ' ';
        return fc.replaceAll(".{4}(?!$)", "$0" + delimiter);
    }

    @FXML
    private void onNextButton() {
        OrderForm.OrderFormEvent onNextEvent = new OrderForm.OrderFormEvent(this, OrderFormEvent.ON_NEXT);
        fireEvent(onNextEvent);
    }

    public EventHandler<OrderFormEvent> getOnNext() {
        return onNextHandler;
    }

    /**
     * Sets the event handler for when a new category is selected.
     * Can be used from `fxml` as: `onNext="#eventHandelr"`.
     *
     * @param onNextHandler The new event handler.
     */
    public void setOnNext(EventHandler<OrderFormEvent> onNextHandler) {
        if (this.onNextHandler != null) {
            removeEventHandler(OrderFormEvent.ON_NEXT, this.onNextHandler);
        }
        this.onNextHandler = onNextHandler;
        addEventHandler(OrderFormEvent.ON_NEXT, this.onNextHandler);
    }

    public static class OrderFormEvent extends Event {
        public static final EventType<OrderForm.OrderFormEvent> ROOT_EVENT = new EventType<>(Event.ANY, "ORDER_FORM_ROOT_EVENT");
        public static final EventType<OrderForm.OrderFormEvent> ON_NEXT = new EventType<>(ROOT_EVENT, "ON_NEXT");
        public OrderFormEvent(OrderForm source, EventType<OrderForm.OrderFormEvent> eventType) {
            super(source, null, eventType);
        }
    }
}
