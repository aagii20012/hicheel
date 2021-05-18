package lights;

import java.awt.Color;
import org.junit.Assert;
import org.junit.Test;
public class ColorLightTest {

	@Test public void makeoffLightColor() {
		Color c= new Color(1,7,8);
		ColoredLight cl= new ColoredLight(c);
		Assert.assertFalse(cl.isOn());
		Assert.assertEquals(c,cl.getColor());
		
	}
	@Test public void makeonLightColor() {
		Color c= new Color(1,7,8);
		ColoredLight cl= new ColoredLight(c , true);
		Assert.assertTrue(cl.isOn());
		Assert.assertEquals(c,cl.getColor());
		
	}
	@Test public void turnOnLightColor() {
		Color c= new Color(1,7,8);  
		ColoredLight cl= new ColoredLight(c );
		Color c2= new Color(5,5,3); 
		
		cl.setOn(true);
		Assert.assertTrue(cl.isOn()); 
		
		cl.setColor(c2);
		Assert.assertEquals(c2,cl.getColor());
		
	}
	@Test public void turnOffLightColor() {
		Color c= new Color(1,7,8); 
		ColoredLight cl= new ColoredLight(c , true);
		Color c2= new Color(5,5,3); 
		
		cl.setOn(false);
		Assert.assertFalse(cl.isOn());
	
		cl.setColor(c2);
		Assert.assertEquals(c2,cl.getColor());
		
	}
	@Test public void testRandomChange() {
		Color c= new Color(1,7,8); 
		ColoredLight light = new ColoredLight(c,true);

		boolean b= false; 
		for (int i = 0; i < 100; i++) { 
			light.randomChange();
			if(light.getColor()!=c)
				b=true;
			
		}
		 
		Assert.assertTrue(b);
		
		

	}
	

}
