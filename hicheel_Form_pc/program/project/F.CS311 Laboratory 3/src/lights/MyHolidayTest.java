package lights;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;
public class MyHolidayTest {
	@Test public void checkLength() {
		MyHolidayLights mhl = new MyHolidayLights(10);
		int length= mhl.urt; 
		boolean b = (length==mhl.getLength() )? true: false; 
		Assert.assertTrue(b);
		}
		
		@Test public void testRandomChangeColor() {
			MyHolidayLights mhl = new MyHolidayLights(10);
			
			Color c= new Color(6,5,5);

			boolean b= false;
			mhl.makeListOfLights();
			for (int i = 0; i < mhl.list.size(); i++) { 
				mhl.list.get(i).randomChange();
				
				if(mhl.list.get(i).getColor()!=c )
					b=true;
				Assert.assertTrue(b);
				c= mhl.list.get(i).getColor();
			}

		}
		
		@Test public void testRandomChange() {
			MyHolidayLights mhl = new MyHolidayLights(10);
			
			boolean b= false; 
			for (int i = 0; i < mhl.list.size(); i++) { 
				mhl.list.get(i).randomChange();
				if(mhl.list.get(i).isOn()!= false)
					b=true;
				
			}
			 
			Assert.assertTrue(b);
			
			

		}
}
