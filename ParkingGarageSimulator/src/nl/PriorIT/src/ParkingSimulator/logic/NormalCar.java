package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.Random;
import java.awt.*;
/**
 * Hier worden de fields gedefined
 */
public class NormalCar extends Car {

	private static final Color COLOR=Color.RED;
	/**
	 * constructor voor een normal car
	 */
    public NormalCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setTotalMinutes(stayMinutes);
    }
    /**
     * return color;
     */
    public Color getColor(){
    	return COLOR;
    }
}