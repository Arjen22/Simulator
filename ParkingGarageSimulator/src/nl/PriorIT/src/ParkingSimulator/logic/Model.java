/**
 * @author PriorIT
 * @version 0.1 DayBuild 1
 * The abstract model contains all the common classes of all the models.
 */
package nl.PriorIT.src.ParkingSimulator.logic;

import java.awt.Color;
import java.util.Random;
import java.text.DecimalFormat;

import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.view.*;

public class Model extends GeneralModel {
	
	private CarQueue entranceCarQueue;
	private CarQueue entrancePassQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private CarParkView carparkview;
	
	private static int numberOfFloors;
	private static int numberOfRows;
	private static int numberOfPlaces;
	private static int numberOfOpenSpots;
	private static int numberOfSpots;
	private static Car[][][] cars;
	private static Location lastplace;
	private static int abboplekken;
	private static int abonnementen = 200;
	private Random random;
	
	private int week = 0;
	private int day = 0;
	private int hour = 0;
	private int minute = 0;
	private double totalMoney = 0.00;
	private double moneyToday = 0.00;
	private double expectedMoney = 0.00;	
	private static int totalMinutes = 0;
	double percent;

	int weekDayArrivals= 100; // average number of arriving cars per hour
	int weekendArrivals = 200; // average number of arriving cars per hour
	int weekDayPassArrivals= abonnementen/4; // average number of arriving cars per hour
	int weekendPassArrivals = abonnementen/8; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 7; // number of cars that can pay per minute
	int exitSpeed = 5; // number of cars that can leave per minute

	private static final String NORMCAR = "1";
	private static final String PASS = "2";
	private int tickPause = 100;


	public Model(int numberOfFloors, int numberOfRows, int numberOfPlaces, int abboplekken) {
		Model.numberOfFloors = numberOfFloors;
		Model.numberOfRows = numberOfRows;
		Model.numberOfPlaces = numberOfPlaces;
		Model.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
		numberOfSpots = numberOfOpenSpots;
		Model.abboplekken = abboplekken;
		numberOfSpots = numberOfOpenSpots;
		cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
		
		entranceCarQueue = new CarQueue();
	    entrancePassQueue = new CarQueue();
	    paymentCarQueue = new CarQueue();
	    exitCarQueue = new CarQueue();
	    lastplace = lastloc();
	    random = new Random();
	    
	    
	    totalMoney += abonnementen * 40;
	    moneyToday += abonnementen * 40;
	    
	}

	public static int getAbboPlekken(){
    	return abboplekken;
    }
	
	public void handleEntrance(){
		carsArriving();
		carsEntering(entrancePassQueue);
		carsEntering(entranceCarQueue);  	
	}

	public void run(Model simulatormodel, SimulatorController controller, CarParkView carparkview, ChartView piechart) {
		notifyViews();
		for (int i = 0; i < 1000000; i++) {
			tick(carparkview, piechart);
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
			moneyToday = 0;
		}
		while (day > 6) {
			day -= 7;
			week++;
			totalMoney += abonnementen * 40;
			moneyToday += abonnementen * 40;
		}
		while(week > 51) {
			week -= 52;
		}
	}

	public String getMinutes() {		
		
		String text = (minute < 10 ? "0" : "") + minute;
		return text;
		
	}
	
	public double getPerc() {
		double totaal = numberOfSpots;
		double Bezet = numberOfSpots - numberOfOpenSpots;
		percent =(Bezet/totaal)*100;
		return percent;
	}	
	public String getPercent() {
		DecimalFormat df = new DecimalFormat("0.##");
		String antwoord = df.format(getPerc());
		return antwoord;
	}

	public String getHours() {

		String text = (hour < 10 ? "0" : "") + hour;
		return text;
		
	}
	
	public int getDays() {
		return day;
	}
	
	public int getWeeks() {
		return week;
	}
	
	public double getMoney() {
		return totalMoney;
	}
	
	public double getMoneyToday() {
		return moneyToday;
	}
	
	public double getExpectedMoney() {
		return expectedMoney;
	}
	
	public int getQueueSize() {
		int grootte = entranceCarQueue.carsInQueue();
		return grootte;
	}
	
	public int getQueueSizePass() {
		int grootte = entrancePassQueue.carsInQueue();
		return grootte;
	}
	
	public void updateViews(CarParkView carparkview, ChartView piechart){
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
			Location freeLocation = null;
			int checkingSpots = 0;
			while(freeLocation == null) {
				freeLocation = getFirstFreeLocation(car.getColor());
				checkingSpots++;
				if (checkingSpots == numberOfSpots-abboplekken) {
					break;
				}
			}
			if (freeLocation == null) {
				queue.setAutoBack(car);
				break;
			}
			else {
				setCarAt(freeLocation, car);
				i++;
				totalMinutes += car.getTotalMinutes();
			}
			
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
			// Bedragen voor de gewone auto's per uur: rood 3 per uur(0.05 per minuut)
			// De abonementshouders betalen 40euro per week
			// Reserveer auto's betalen 3euro per uur plus een reserveringsbedrag van 2.00euro
			if(car.getHasToPay()== true) {
			totalMoney += car.getTotalMinutes() * 0.05;
			if(hour >=0 &&(hour <= 23 && minute <= 59)) {
			moneyToday += car.getTotalMinutes() * 0.05;
			}
			}
			totalMinutes -= car.getTotalMinutes();
			expectedMoney = totalMinutes*0.05;
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
    
	public static int getNumberOfFloors() {
        return numberOfFloors;
    }

    public static int getNumberOfRows() {
        return numberOfRows;
    }

    public static int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public static int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    
    public static Car getCarAt(Location location) {
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
    
    public int nextInt(int min, int max) {
    	random = new Random();
    	int locatie = random.nextInt(max - min);
    	return locatie+min;
    }

    public int checkNormGarage() {
    	int numberOfNormCars = 0;
    	for(int floor = lastplace.getFloor(); floor <= getNumberOfFloors(); floor++){
    			for(int row = floor == lastplace.getFloor() ? lastplace.getRow() : 0; row < getNumberOfRows() ; row++) {
    				for(int place = floor == lastplace.getFloor() && row == lastplace.getRow() ? lastplace.getPlace() : 0; place < getNumberOfPlaces() ; place++) {
        				Location location = new Location(floor, row, place);
        				//System.out.println(location);
        				if(getCarAt(location) != null){
        					numberOfNormCars++;
        				}
        				
        			}
    			}    		
    	}
    	//System.out.println(numberOfNormCars);
		return numberOfNormCars;
    	
    }
    
    public boolean checkAbboGarage() {//kijkt of abboplekken = vol dan blauwe autos op rode plekken(true) anders false
	int openAbboPlekken=abboplekken;
    for (int floor = 0; floor <= lastplace.getFloor(); floor++) {
    	
		for (int row = 0; row <= lastplace.getRow(); row++) {
			if (row == lastplace.getRow()) {
				for (int place = 0; place < lastplace.getPlace(); place++) {
					Location location = new Location(floor, row, place);
					if(getCarAt(location) != null) { 
						openAbboPlekken--;
					}
				}
			}
			else {
				for (int place = 0; place < getNumberOfPlaces(); place++) {
					Location location = new Location(floor, row, place);
					if(getCarAt(location) != null) {
						openAbboPlekken--;
					}
				}
			}
		}
    }

		if(openAbboPlekken == 0) {
				return true;
		}
		else {return false;}			
    }
    
    public Location getFirstFreeLocation(Color color) {
    	int floor = 0;
    	int row = 0;
    	int place = 0;
    	if(color == Color.blue && checkAbboGarage()== false) {  	
    		int floor1 = lastplace.getFloor();
    		floor = floor1 == 0 ? 0 : random.nextInt(floor1);
    		floor = floor <= floor1 ? floor : -1;
    		if(row <= getNumberOfRows() && floor != -1) {
    			int row1 = lastplace.getRow();
    			int row2 =  getNumberOfRows();
    			row = floor < floor1 ? random.nextInt(row2) : random.nextInt(row1+1);
    			if(place<= lastplace.getPlace()) {
    				int place1 = lastplace.getPlace();
    				int place2 = getNumberOfPlaces();
    				place = row == row1 && floor == floor1 ? random.nextInt(place1) : random.nextInt(place2);
    			}
    		}	
    	}
    	else {
    		int floor11 = lastplace.getFloor();
    		int floor22 = getNumberOfFloors();
    		floor = nextInt(floor11,floor22);
    		floor = floor >= floor11 ? floor : -1;
    		if(floor != -1) {
    			int row11 = lastplace.getRow();
    			int row22 =  getNumberOfRows();
    			row = floor > floor11 ? random.nextInt(row22) : nextInt(row11, row22);
    			int place11 = lastplace.getPlace();
				int place22 = getNumberOfPlaces();
    			if(row == row11 && floor == floor11) {
    				place = nextInt(place11,place22);
    			}
    			else {
    				place = random.nextInt(place22);
    			}
    		}                				
    	}
    
    	Location location = new Location(floor, row, place);
    	Location check = getCarAt(location) == null ? location : null;
    	if(check != null) {
    		return location;
    	}
    	return null;
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

    private void tick(CarParkView carparkview, ChartView piechart) {
    	this.carparkview = carparkview;
    	advanceTime();
    	handleExit();
    	updateViews(carparkview, piechart);
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private static boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
}