package lights;

import java.util.ArrayList;

public interface HolidayLights {
	/**
	 * Appearence of lights at next time slice.
	 * @return appearence of lights at next time slice.
	 */
	//public List<Light> next();
	public ArrayList<ColoredLight> next();
	public int getLength();
}
