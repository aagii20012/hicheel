package lights;
import java.util.List;
import java.util.ArrayList;

public class RunningHolidayLights implements HolidayLights{
	public int urt;
	public ArrayList<Light> list = new ArrayList<Light>(); 
	private int i = 0 , j = urt; 

	public RunningHolidayLights(int length) {
		urt = length;

		makeListOfLights();

		next();
	}

	public void makeListOfLights() {
		for (int i = 0; i < urt; i++) {
			Light light;
			list.add(light = new Light());
		}
	}

	public ArrayList<Light> next() {

	
			if (i == 0) {
				list.get(list.size() - 1).setOn(false);
				list.get(i).setOn(true);
				i++;
			
			} else {
				list.get(i - 1).setOn(false);
				list.get(i).setOn(true);
				i++;
			}
			i= (i==12)? 0: i;


		return list;
	}
 
	public int getLength() {
		return list.size();
	}
		
}
