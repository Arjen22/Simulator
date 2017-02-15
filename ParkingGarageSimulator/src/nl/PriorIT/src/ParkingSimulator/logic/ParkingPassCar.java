package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.Random;
import java.awt.*;
/**
 * Hier worden de fields gedefined
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.BLUE;
	/**
	 * constructor voor parkingpasscar
	 */
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}