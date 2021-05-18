package lights;

public interface HolidayLights {
	/**
	 * Appearence of lights at next time slice.
	 * @return appearence of lights at next time slice.
	 */
	//public List<Light> next();
	public Light next();
	public int getLength();
}
