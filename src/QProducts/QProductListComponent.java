package QProducts;

import Animations.DoubleAnimation;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.Product;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QProductListComponent extends GridPane {
	private static double ItemAnimationHeight = 25.0d;

	@FXML
	private FlowPane holder;

	@FXML
	private ProgressIndicator loadingCircle;

	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;
	@FXML
	private ImageView arrowRight;
	@FXML
	private ImageView arrowLeft;

	private Map<Integer, QProductListItemComponent> productComponents = new HashMap<>();

	private DoubleAnimation revealAnimation = new DoubleAnimation(this::revealAction, Duration.millis(300));

	private EventHandler<QProductListComponentEvent> onPreviousEventHandler;
	private EventHandler<QProductListComponentEvent> onNextEventHandler;

	File file2 = new File("resources/images/Arrow_-_Left-512.png");
	Image imageSrc2 = new Image(file2.toURI().toString());

	public QProductListComponent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QProductListComponent.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void setProducts(List<Product> products) {
		holder.getChildren().clear();
		loadingCircle.setVisible(true);

		Thread t = new Thread(() -> {
			for (Product p : products) {
				if (!productComponents.containsKey(p.getProductId())) {
					productComponents.put(p.getProductId(), new QProductListItemComponent(p));
				}
			}

			Platform.runLater(() -> {
				loadingCircle.setVisible(false);
				for (Product p : products) {
					holder.getChildren().add(productComponents.get(p.getProductId()));
				}
				revealAnimation.play();
			});
		});
		t.start();
	}

	private void revealAction(double value) {
		holder.setOpacity(value);
		holder.setTranslateY(ItemAnimationHeight - ItemAnimationHeight * value);
	}

	public void setPrevious(String previous) {
		if (previous == null) {
			previousButton.setText("⬅");
			previousButton.setVisible(false);
		}
		else {
			arrowLeft.setImage(imageSrc2);
			previousButton.setText(previous);
			previousButton.setVisible(true);
		}
	}
	public void setNext(String next) {
		if (next == null) {
			nextButton.setText("➡");
			nextButton.setVisible(false);
		}
		else {
			arrowRight.setImage(imageSrc2);
			nextButton.setText(next);
			nextButton.setVisible(true);

		}
	}

	@FXML
	private void onPrevious() {
		Event e = new QProductListComponentEvent(QProductListComponentEvent.ON_PREVIOUS);
		fireEvent(e);
	}
	@FXML
	private void onNext() {
		Event e = new QProductListComponentEvent(QProductListComponentEvent.ON_NEXT);
		fireEvent(e);
	}

	public void setOnPrevious(EventHandler<QProductListComponentEvent> value) {
		if (onPreviousEventHandler != null) {
			removeEventHandler(QProductListComponentEvent.ON_PREVIOUS, onPreviousEventHandler);
		}

		onPreviousEventHandler = value;
		addEventHandler(QProductListComponentEvent.ON_PREVIOUS, value);
	}
	public EventHandler<QProductListComponentEvent> getOnPrevious() {
		return onPreviousEventHandler;
	}

	public void setOnNext(EventHandler<QProductListComponentEvent> value) {
		if (onNextEventHandler != null) {
			removeEventHandler(QProductListComponentEvent.ON_NEXT, onNextEventHandler);
		}

		onNextEventHandler = value;
		addEventHandler(QProductListComponentEvent.ON_NEXT, value);
	}
	public EventHandler<QProductListComponentEvent> getOnNext() {
		return onNextEventHandler;
	}

	public static class QProductListComponentEvent extends Event {
		public static final EventType<QProductListComponentEvent> ROOT_EVENT = new EventType<>(Event.ANY, "QPRODUCTLISTCOMPONENT_ROOT_EVENT");
		public static final EventType<QProductListComponentEvent> ON_PREVIOUS = new EventType<>(ROOT_EVENT, "ON_PREVIOUS");
		public static final EventType<QProductListComponentEvent> ON_NEXT = new EventType<>(ROOT_EVENT, "ON_NEXT");

		public QProductListComponentEvent(EventType<QProductListComponentEvent> eventType) {
			super(eventType);
		}
	}
}