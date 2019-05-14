package OrderForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OrderForm {

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
    @FXML private ComboBox<?> cardType;
    @FXML private TextField cardNumber;
    @FXML private ComboBox<?> month;
    @FXML private ComboBox<?> year;
    @FXML private TextField cvcCode;

}
