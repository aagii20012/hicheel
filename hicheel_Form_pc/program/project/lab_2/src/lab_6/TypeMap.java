package lab_6;

import java.util.*;

public class TypeMap extends HashMap<Variable, Type> { 

// TypeMap is implemented as a Java HashMap.  
// Plus a 'display' method to facilitate experimentation.
	
	public void display()
	{
		String types = "{";
		Object m[] = this.entrySet().toArray();
		for(int i = m.length - 1; i > -1; i--)
		{
			Map.Entry<Variable, Type> entry = (Map.Entry<Variable, Type>) m[i];
			if(types.length() > 1) types += ", " + "<" + entry.getKey() + ", " + entry.getValue() + ">"; else types += "<" + entry.getKey() + ", " + entry.getValue() + ">";
		}
    	types += "}";
    	System.out.println(types);
	}
}
