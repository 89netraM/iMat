package ValidatingTextField;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class ValidatingTextField extends TextField {
	private String validator = ".*";
	private Paint errorPaint = Paint.valueOf("#ff0000");

	private String errorText;
	private Label errorLabel;

	public ValidatingTextField() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ValidatingTextField.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.textProperty().addListener(this::onTextChanged);
	}

	private void onTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		runValidation();
	}

	private void runValidation() {
		if (isValid()) {
			this.setStyle("");
			if (errorLabel != null) {
				errorLabel.setVisible(false);
			}
		}
		else {
			this.setStyle(getErrorStyle());
			if (errorLabel != null) {
				errorLabel.setVisible(true);
			}
		}
	}

	private String getErrorStyle() {
		String color = "#" + errorPaint.toString().substring(2, 8);
		return "-fx-text-box-border: " + color + "; -fx-focus-color: " + color + "; -fx-faint-focus-color: transparent;";
	}

	private void setLabelStyle() {
		errorLabel.setStyle("-fx-font-color: #" + errorPaint.toString().substring(2, 8) + ";");
	}

	/**
	 * Used mainly through FXML to set the validation Regex string.
	 * Used in FXML like this: `validator="^\d*$"`.
	 * @param    value    The validation Regex string.
	 */
	public void setValidator(String value) {
		validator = value;

		runValidation();
	}
	public String getValidator() {
		return validator;
	}

	/**
	 * Used mainly through FXML to set the error indicating color.
	 * Used in FXML like this: `errorPaint="#09cdda"`.
	 * @param    value    The error indicating color.
	 */
	public void setErrorPaint(Paint value) {
		errorPaint = value;
		setLabelStyle();
	}
	public Paint getErrorPaint() {
		return errorPaint;
	}

	/**
	 * Used mainly through FXML to set the error text.
	 * Used in FXML like this: `errorText="Wrong format"`.
	 * @param    value    The error text.
	 */
	public void setErrorText(String value) {
		errorText = value;
		if (errorLabel != null) {
			errorLabel.setText(value);
		}
	}
	public String getErrorText() {
		return errorText;
	}

	/**
	 * Used mainly through FXML to set the label that displays the error message.
	 * Used in FXML like this: `errorLabel="#label"`.
	 * @param    value    The label.
	 */
	public void setErrorLabel(Label value) {
		errorLabel = value;
		setLabelStyle();
		errorLabel.setText(errorText);
		runValidation();
	}
	public Label getErrorLabel() {
		return errorLabel;
	}

	public boolean isValid() {
		return this.getText() == null || this.getText().matches(validator);
	}
}