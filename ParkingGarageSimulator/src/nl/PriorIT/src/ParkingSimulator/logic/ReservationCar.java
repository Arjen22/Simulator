package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.Random;
import java.awt.*;
/**
 * Hier worden de fields gedefined
 */
public class ReservationCar extends Car{


		private static final Color COLOR=Color.GREEN;
		/**
		 * constructor van de reservation car
		 */
	    public ReservationCar() {
	    	Random random = new Random();
	    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
	        this.setMinutesLeft(stayMinutes);
	        this.setHasToPay(true);
	    }
	    /**
	     * returned color
	     */
	    public Color getColor(){
	    	return COLOR;
	    }
	}