package ProductItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProductItemComponent extends VBox {
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    public ProductItemComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductItemComponent/ProductItemComponent.xml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public Label getDescription() {
        return description;
    }
}
