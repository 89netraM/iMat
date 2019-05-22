package Animations;

import javafx.animation.Transition;
import javafx.util.Duration;

public class DoubleAnimation extends Transition {
	private final IAnimationAction action;

	private double start;
	private double length;

	/**
	 * Instantiate a DoubleAnimation with an set action.
	 * @param    action    The action to take for every step of the animation.
	 */
	public DoubleAnimation(IAnimationAction action) {
		super();
		this.action = action;
	}

	/**
	 * Instantiate a DoubleAnimation with an set action, and a duration.
	 * @param    action      The action to take every step of the animation.
	 * @param    duration    The duration of the animation, can be change.
	 */
	public DoubleAnimation(IAnimationAction action, Duration duration) {
		this(action);
		super.setCycleDuration(duration);
	}

	@Override
	protected void interpolate(double frac) {
		action.Action(start + length * frac);
	}

	/**
	 * Plays the animation.
	 * The action will receive the values between 0.0 and 1.0.
	 */
	@Override
	public void play() {
		play(0.0d, 1.0d);
	}

	/**
	 * Plays the animation.
	 * The action will be called with the values between `from` and `to`.
	 * @param    from    The start value.
	 * @param    to      The end value.
	 */
	public void play(double from, double to) {
		start = from;
		length = to - start;
		super.play();
	}
}