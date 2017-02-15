package conceptmvc;

import java.util.Random;
import java.awt.*;

public class NormalCar extends Car {

	private static final Color COLOR=Color.RED;
	
    public NormalCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setTotalMinutes(stayMinutes);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}