package Animations;

import javafx.animation.Transition;
import javafx.util.Duration;

public class ValueAnimation extends Transition {
	private final IAnimationAction action;

	private double start;
	private double length;

	public ValueAnimation(Duration duration, IAnimationAction action) {
		super();
		super.setCycleDuration(duration);
		this.action = action;
	}

	@Override
	protected void interpolate(double frac) {
		action.Action(start + length * frac);
	}

	@Override
	public void play() {
		play(0.0d, 1.0d);
	}

	public void play(double from, double to) {
		start = from;
		length = to - start;
		super.play();
	}
}