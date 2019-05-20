package OrderForm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OrderForm extends AnchorPane implements Initializable {

    @FXML private AnchorPane OrderFormComponent;
    @FXML private AnchorPane OrderForm;
    @FXML private Button changeAdress;
    @FXML private Button changePaymentMethod;
    @FXML private AnchorPane AdressForm;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phone;
    @FXML private TextField address;
    @FXML private TextField postCode;
    @FXML private TextField postAddress;
    @FXML private Button saveAddressInfo;
    @FXML private AnchorPane PaymentForm;
    @FXML private Button savePaymentInfo;
    @FXML private TextField firstAndLastName;
    @FXML private ComboBox cardType;
    @FXML private TextField cardNumber;
    @FXML private ComboBox month;
    @FXML private ComboBox year;
    @FXML private TextField cvcCode;



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


    private final Model model = Model.getInstance();
    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle rb){

       // iMatDataHandler.resetFirstRun();      //kallas bara för att återställa hela programmet
        if(iMatDataHandler.isFirstRun()){
            iMatDataHandler.reset();
        }
        setupAccountPane();

    }

    private void updateCreditCard() {

        CreditCard card = model.getCreditCard();

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

    private void updateCustomer(){

        Customer customer = model.getCustomer();

        customer.setFirstName(firstName.getText());
        customer.setLastName(lastName.getText());
        customer.setEmail(email.getText());
        customer.setPhoneNumber(phone.getText());
        customer.setAddress(address.getText());
        customer.setPostCode(postCode.getText());
        customer.setPostAddress(postAddress.getText());

    }

    private void updatePaymentFormPanel() {

        CreditCard card = model.getCreditCard();

        cardNumber.setText(card.getCardNumber());
        firstAndLastName.setText(card.getHoldersName());

        cardType.getSelectionModel().select(card.getCardType());
        month.getSelectionModel().select(""+card.getValidMonth());
        year.getSelectionModel().select(""+card.getValidYear());

        cvcCode.setText(""+card.getVerificationCode());

        // purchasesLabel.setText(model.getNumberOfOrders()+ " tidigare inköp hos iMat");
    }

    private void updateAddressFormPanel(){

        Customer customer = model.getCustomer();

        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        phone.setText(customer.getPhoneNumber());
        address.setText(customer.getAddress());
        postCode.setText(customer.getPostCode());
        postAddress.setText(customer.getPostAddress());

    }

    private void setupAccountPane() {

        cardType.getItems().addAll(model.getCardTypes());

        month.getItems().addAll(model.getMonths());

        year.getItems().addAll(model.getYears());
    }

    @FXML
    private void addressFormToFront (){
        AdressForm.toFront();
        OrderForm.toBack();
        updateAddressFormPanel();
    }

    @FXML
    private void paymentFormToFront (){
        PaymentForm.toFront();
        OrderForm.toBack();
        updatePaymentFormPanel();
    }

    @FXML
    private void saveAddressInfo (){
        OrderForm.toFront();
        AdressForm.toBack();
        updateCustomer();

    }

    @FXML
    private void savePaymentInfo (){
        OrderForm.toFront();
        PaymentForm.toBack();
        updateCreditCard();
    }


}
