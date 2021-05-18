package lights;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

public class MyHolidayLights implements HolidayLights{
	int urt;
	public ArrayList<ColoredLight> list = new ArrayList<ColoredLight>(); 
	private int i = 0;

	public  MyHolidayLights (int length) {
			urt = length;

			makeListOfLights();
			next();	 
		}

	public void makeListOfLights() {
		for (int i = 0; i < urt; i++) {
			ColoredLight light;
			Color f= new Color(urt,urt+20,urt+40);
			list.add(light = new ColoredLight(f));
		}
	}

	public ArrayList<ColoredLight> next() {

		if (i == 0) {
			list.get(list.size() - 1).setOn(false);
			list.get(i).setOn(true);
			list.get(i).randomChange();
			i++;
			
		} 
		else {
			list.get(i - 1).setOn(false);
			list.get(i).setOn(true);
			list.get(i).randomChange();
			i++;
		}
		i = (i == 12) ? 0 : i;
		
		return list;
	}

 
	public int getLength() {
		return list.size();
	}
}
