package positronix.lantern;

import java.util.*;

public class Calculations {
	
	/*Rolls dice*/
	public static int roll(int count, int die, int mod)  {
			Random r = new Random();
			int result = 0;
    	
			for (int i = 0; i < count; i++) {
				result += r.nextInt(die) + 1;
			}
    	
			return result += mod;
    }//END OF METHOD
	
	/*Calculates ability modifiers*/
	public static int modCalc(int score){
		int mod = -5;
		for(int i = 1; i < score; i+=2){
			mod++;
		}
		return mod;
	}//END OF METHOD
	
	public static String filterInt(String s, int defaultVal) {
		String output;
		
		try {
			int i = Integer.parseInt(s);
			output = Integer.toString(i);
		}
		catch (NumberFormatException e1) {
			try {
				double d = Double.parseDouble(s);
				output = Integer.toString((int) d);
			}
			catch (NumberFormatException e2) {
				output = Integer.toString(defaultVal);
			}
		}
		
		return output;
	}//END OF METHOD
	
	public static String filterDouble(String s, double defaultVal) {
		String output;
		
		try {
			double i = Double.parseDouble(s);
			output = Double.toString(i);
		}
		catch (NumberFormatException e1) {
			output = Double.toString(defaultVal);
		}
		
		return output;
	}//END OF METHOD
	
	
	/*Method for the Point Buy system*/
	public static int PointBuy(int abilityPoints){
		int costPerLevel = 1;
		int totalCost = -2;
		if(abilityPoints == 7){
			totalCost = -4;
		} else {
			for(int i = 8; i < abilityPoints; i++){
				//Every even number after 13 the point cost per level increases by one
				if((i%2 != 0) && (i > 12)){
					costPerLevel += 1;
				}
				totalCost += costPerLevel;
			}//FOR LOOP
		}//IF ELSE
		return totalCost;
	}//END OF METHOD

}
