package lights;

import java.awt.Color;
public class ColoredLight extends Light{

	// private boolean isOn;
	private Color colour;

	/**
	 * Creates a new colored light.
	 * 
	 * @param color - color of this light.
	 */
	public ColoredLight(Color color) {

		super();
		colour = color;
	}

	public ColoredLight(Color color, boolean isOn) {

		super(isOn);
		colour = color;

	}
 
	public Color getColor() {
		
		return colour;
	}

	/**
	 * Changes the color of this light to be c.
	 */
	public void setColor(Color c) {

		colour = c;
		// throw new RuntimeException("ColoredLight.setColor() not yet implemented!");
	}

	/**
	 * Randomly changes this light to be on or off and its color.
	 */
	@Override
	public void randomChange() {

		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		Color color = new Color(R, G, B);
		setColor(color);
		// throw new RuntimeException("ColoredLight.randomChange() not yet
		// implemented!");
	}
}
