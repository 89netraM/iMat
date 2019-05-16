package ProductItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProductItemComponent {
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    public ProductItemComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductItem/ProductItemComponent.xml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setDescription(final Label description) {
        this.description = description;
    }

    public void setImage(final ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public Label getDescription() {
        return description;
    }
}
