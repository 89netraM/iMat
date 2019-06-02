package ValidatingTextField;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class ValidatingTextField extends TextField {
	private String validator = ".";
	private Paint errorPaint = Paint.valueOf("#ff0000");
	private String errorText = "";

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

		super.textProperty().addListener(this::onTextChanged);
	}

	private void onTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (newValue.matches(validator)) {
			this.setStyle("");
		}
		else {
			this.setStyle(getErrorStyle());
		}
	}

	private String getErrorStyle() {
		String color = "#" + errorPaint.toString().substring(2, 8);
		return "-fx-text-box-border: " + color + "; -fx-focus-color: " + color + "; -fx-faint-focus-color: transparent;";
	}

	/**
	 * Used mainly through FXML to set the validation Regex string.
	 * Used in FXML like this: `validator="^\d*$"`.
	 * @param    value    The validation Regex string.
	 */
	public void setValidator(String value) {
		validator = value;
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
	}
	public Paint getErrorPaint() {
		return errorPaint;
	}
}