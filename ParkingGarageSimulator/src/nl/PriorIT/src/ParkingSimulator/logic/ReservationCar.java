package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car{


		private static final Color COLOR=Color.GREEN;
		
	    public ReservationCar() {
	    	Random random = new Random();
	    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
	        this.setMinutesLeft(stayMinutes);
	        this.setHasToPay(true);
	    }
	    
	    public Color getColor(){
	    	return COLOR;
	    }
	}