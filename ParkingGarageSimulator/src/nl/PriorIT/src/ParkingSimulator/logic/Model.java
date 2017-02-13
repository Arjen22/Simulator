/**
 * @author PriorIT
 * @version 0.1 DayBuild 1
 * The abstract model contains all the common classes of all the models.
 */
package nl.PriorIT.src.ParkingSimulator.logic;

import java.awt.Color;
import java.util.Random;

import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.view.*;

public class Model extends GeneralModel {
	
	private CarQueue entranceCarQueue;
	private CarQueue entrancePassQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private CarParkView carparkview;
	
	private int numberOfFloors;
	private int numberOfRows;
	private int numberOfPlaces;
	private int numberOfOpenSpots;
	private int numberOfSpots;
	private Car[][][] cars;
	private Location lastplace;
	private static int abboplekken;
	private static int abonnementen = 200;
	Random random = new Random();
	
	private int week = 0;
	private int day = 0;
	private int hour = 0;
	private int minute = 0;
	private double totalMoney = 0.00;
	
	
	int weekDayArrivals= 100; // average number of arriving cars per hour
	int weekendArrivals = 200; // average number of arriving cars per hour
	int weekDayPassArrivals= 50; // average number of arriving cars per hour
	int weekendPassArrivals = 5; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 7; // number of cars that can pay per minute
	int exitSpeed = 5; // number of cars that can leave per minute

	private static final String NORMCAR = "1";
	private static final String PASS = "2";
        private int tickPause = 100;
	

	public Model(int numberOfFloors, int numberOfRows, int numberOfPlaces, int abboplekken) {
		this.numberOfFloors = numberOfFloors;
		this.numberOfRows = numberOfRows;
		this.numberOfPlaces = numberOfPlaces;
		this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
		numberOfSpots = numberOfOpenSpots;
		this.abboplekken = abboplekken;
		cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
		
		entranceCarQueue = new CarQueue();
	    entrancePassQueue = new CarQueue();
	    paymentCarQueue = new CarQueue();
	    exitCarQueue = new CarQueue();
	    lastplace = lastloc();
	    
	    totalMoney += abonnementen * 40;
	    
	}

	public static int getAbboPlekken(){
    	return abboplekken;
    }
	
	public void handleEntrance(){
		carsArriving();
		carsEntering(entrancePassQueue);
		carsEntering(entranceCarQueue);  	
	}

	public void run(Model simulatormodel, SimulatorController controller, CarParkView carparkview) {
		notifyViews();
		for (int i = 0; i < 1000000; i++) {
			tick(carparkview);
		}
	}
	
	public Location lastloc() {
		int floor = 0;
		int row = 0;
		int place = abboplekken;
		for(; place > numberOfPlaces; place -= numberOfPlaces) {
			row++;
		}
		for (; row > numberOfRows; row -= numberOfRows) {
			floor++;
		}
		Location location = new Location(floor, row , place);
		return location;
	}

	public void handleExit(){
		carsReadyToLeave();
		carsPaying();
		carsLeaving();
	}

	public void advanceTime(){
		// Advance the time by one minute.
		minute++;
		while (minute > 59) {
			minute -= 60;
			hour++;
		}
		while (hour > 23) {
			hour -= 24;
			day++;
		}
		while (day > 6) {
			day -= 7;
			week++;
			totalMoney += abonnementen * 40;
		}
		while(week > 51) {
			week -= 52;
		}
		System.out.println("advanceTime: "+"week: "+ week + " day: "+ day +" hour: " + hour +" minute: "+ minute+ " Money earned = " + Math.round(totalMoney));
	}

	public int getMinutes() {		
		return minute;
	}
	
	public int getHours() {
		return hour;
	}
	
	public int getDays() {
		return day;
	}
	
	public void updateViews(CarParkView carparkview){
		this.carparkview = carparkview;
		carparkview.tick();
		// Update the car park view.
		carparkview.updateView();	
	}

	private void carsArriving(){
		int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
		addArrivingCars(numberOfCars, NORMCAR);    	
		numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
		addArrivingCars(numberOfCars, PASS);    	
	}

	private void carsEntering(CarQueue queue){
		int i=0;
		// Remove car from the front of the queue and assign to a parking space.
		while (queue.carsInQueue()>0 && 
				getNumberOfOpenSpots()>0 && 
				i<enterSpeed) {
			Car car = queue.removeCar();
			Location freeLocation = getFirstFreeLocation(car.getColor());
			if (freeLocation == null) {
				System.out.println("godwhy");
			}
			setCarAt(freeLocation, car);
			i++;
		}
	}

	private void carsReadyToLeave(){
		// Add leaving cars to the payment queue.
		Car car = getFirstLeavingCar();
		while (car!=null) {
			if (car.getHasToPay()){
				car.setIsPaying(true);
				paymentCarQueue.addCar(car);
			}
			else {
				carLeavesSpot(car);
			}
			car = getFirstLeavingCar();
		}
	}

	private void carsPaying(){
		// Let cars pay.
		int i=0;
		
		while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
			Car car = paymentCarQueue.removeCar();
			// TODO Handle payment.
			// Bedragen voor de gewone auto's per uur: rood 3 per uur(0.05 per minuut)
			// De abonementshouders betalen 40euro per week
			// Reserveer auto's betalen 3euro per uur plus een reserveringsbedrag van 2.00euro
			if(car.getHasToPay()== true) {
			totalMoney += car.getTotalMinutes() * 0.05;
			}
			//System.out.println("Total money earned is: "+ totalMoney);
			carLeavesSpot(car);
			i++;
		}
	}

	private void carsLeaving(){
		// Let cars leave.
		int i=0;
		while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
			exitCarQueue.removeCar();
			i++;
		}	
	}

	private int getNumberOfCars(int weekDay, int weekend){
		Random random = new Random();
		
		// Get the average number of cars that arrive per hour.
		int averageNumberOfCarsPerHour = day < 5
				? weekDay
						: weekend;
		
		// Calculate the number of cars that arrive this minute.
		double standardDeviation = averageNumberOfCarsPerHour * 0.3;
		double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
		return (int)Math.round(numberOfCarsPerHour / 60);	
	}

	private void addArrivingCars(int numberOfCars, String type){
		// Add the cars to the back of the queue.
		switch(type) {
		case NORMCAR: 
			for (int i = 0; i < numberOfCars; i++) {
				entranceCarQueue.addCar(new NormalCar());
			}
			break;
		case PASS:
			for (int i = 0; i < numberOfCars; i++) {
				entrancePassQueue.addCar(new ParkingPassCar());
			}
			break;	            
		}
	}

	private void carLeavesSpot(Car car){
		removeCarAt(car.getLocation());
		exitCarQueue.addCar(car);
	}
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }
    
    public int getFullGarage(boolean full) {
    	int vrijeplekken = 0;
    	boolean firsttime = full;
    	
    	checkofvolis:
    	for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                	if(firsttime == false) {
                		floor = lastplace.getFloor();
                		row = lastplace.getRow();
                		place = lastplace.getPlace();
                		firsttime = true;
                	} //telt niet goed
                	
            		Location location = new Location(floor, row, place);
            		
                	if (getCarAt(location) != null) {
                		vrijeplekken++;
                	}
                	
                	if (full) { //als penis false is dan telt die niet goed
                		if(floor == lastplace.getFloor() && row == lastplace.getRow() && place == lastplace.getPlace()) {
                			break checkofvolis;
                		}
                	}
    			}
    		}
    	}
    	return vrijeplekken;
    }

    public Location getFirstFreeLocation(Color color) {
    	Location location = null;
    	int floor;
    	int row;
    	int place;
    	boolean full = getFullGarage(true) == abboplekken ? true : false;
    	while (color == Color.blue && full == false && location == null) {
    		floor = random.nextInt(numberOfFloors);
    		floor = floor <= lastplace.getFloor() ? floor : 899;
    		row = floor < lastplace.getFloor() ?
    				random.nextInt(numberOfRows) : random.nextInt(lastplace.getRow()+1);
    		place = row  == lastplace.getRow() && floor == lastplace.getFloor() ?
    				random.nextInt(lastplace.getPlace()) : random.nextInt(numberOfPlaces);
    		
        	if (floor != 899) {
        		location = new Location(floor, row, place);
        		location = getCarAt(location) == null ? location : null;
        	}
    	}
    	
    	full = getFullGarage(false) == (numberOfSpots - abboplekken) ? true : false;
    	while(full == false && location == null) {
    		floor = random.nextInt(numberOfFloors - lastplace.getFloor()) + lastplace.getFloor();
    		row = floor == lastplace.getFloor() ?
    				random.nextInt(numberOfRows - lastplace.getRow()) + lastplace.getRow() : random.nextInt(numberOfFloors) ;
    		place = row == lastplace.getRow() && floor == lastplace.getFloor() ?
    				random.nextInt(numberOfPlaces - lastplace.getPlace()) + lastplace.getPlace() : random.nextInt(numberOfPlaces);
    		//bovenstaande is ook niet goed
    		location = new Location(floor, row, place);
    		location = getCarAt(location) == null ? location : null;
    	}
    	return location;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    private void tick(CarParkView carparkview) {
    	this.carparkview = carparkview;
	advanceTime();
    	handleExit();
    	updateViews(carparkview);
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
}