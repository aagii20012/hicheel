package lights;

public class Main {
	public static void main(String[]  args) {
		// Create HolidayLights
				//HolidayLights hl = new RunningHolidayLights(12);
				HolidayLights mhl = new MyHolidayLights(12); 				// Myholidaylight  
				
				// Create and show HolidayLightsWindow
				//HolidayLightsWindow frame = new HolidayLightsWindow(hl);
				HolidayLightsWindow frame2 = new HolidayLightsWindow(mhl); 	// Myholidaylight  
				//frame.pack();
				frame2.pack();
				//frame.setVisible(true);
				frame2.setVisible(true);
			}
}
