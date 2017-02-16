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

/**
 * Hier worden de fields gedefined
 */
public class Model extends GeneralModel {
	
	private CarQueue entranceCarQueue;
	private CarQueue entrancePassQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private CarParkView carparkview;
	private ChartView piechart;
	
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

	int weekDayArrivals= 150; // average number of arriving cars per hour
	int weekendArrivals = 250; // average number of arriving cars per hour
	int weekDayPassArrivals= 50; // average number of arriving cars per hour
	int weekendPassArrivals = 25; // average number of arriving cars per hour
	int weekDayRessArrivals = 40; // average number of arriving cars per hour
	int weekendRessArrivals = 20; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 7; // number of cars that can pay per minute
	int exitSpeed = 5; // number of cars that can leave per minute

	private static final String NORMCAR = "1";
	private static final String PASS = "2";
	private static final String RESS = "3";
	private int tickPause = 100;

/**
 * Dit is de constructor van vrijwel alles.
 * @param numberOfFloors
 * @param numberOfRows
 * @param numberOfPlaces
 * @param abboplekken
 */

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
	
	/**
	 * returned abboplekken
	 * @return
	 */
	public static int getAbboPlekken(){
    	return abboplekken;
    }
	/**
	 * dit zorgt er voor dat er autos worden toegevoegd aan een queue
	 */
	public void handleEntrance(){
		carsArriving();
		carsEntering(entrancePassQueue);
		carsEntering(entranceCarQueue);  	
	}
	/**
	 * dit zorgt dat er wordt geupdate
	 * @param simulatormodel
	 * @param controller
	 * @param carparkview
	 * @param piechart
	 */
	public void run(Model simulatormodel, SimulatorController controller, CarParkView carparkview, ChartView piechart) {
		notifyViews();
		for (int i = 0; i < 1000000; i++) {
			tick(carparkview, piechart);
		}
	}
	/**
	 * dit is de laatste locatie van de abonnementshouders
	 * @return
	 */
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
	/**
	 * zorgt er voor dat autos weg gaan uit de queue
	 */
	public void handleExit(){
		carsReadyToLeave();
		carsPaying();
		carsLeaving();
	}
	/**
	 * nu gaat de tijd verder hier door
	 */
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
	/**
	 * returned de minuten
	 * @return
	 */
	public String getMinutes() {		
		
		String text = (minute < 10 ? "0" : "") + minute;
		return text;
		
	}
	/**
	 * returned het percentage openplekken
	 * @return
	 */
	public double getPerc() {
		double totaal = numberOfSpots;
		double Bezet = numberOfSpots - numberOfOpenSpots;
		percent =(Bezet/totaal)*100;
		return percent;
	}
	/**
	 * uitbreiding op getPerc()
	 * @return
	 */
	public String getPercent() {
		DecimalFormat df = new DecimalFormat("0.##");
		String antwoord = df.format(getPerc());
		return antwoord;
	}
	/**
	 * returned uren
	 * @return
	 */
	public String getHours() {

		String text = (hour < 10 ? "0" : "") + hour;
		return text;
		
	}
	/**
	 * returned dagen
	 * @return
	 */
	public int getDays() {
		return day;
	}
	/**
	 * returned week
	 * @return
	 */
	public int getWeeks() {
		return week;
	}
	/**
	 * returned hoeveel geld er al verdient is
	 * @return
	 */
	public double getMoney() {
		return totalMoney;
	}
	/**
	 * hoeveel geld er vandaag verdient is
	 * @return
	 */
	public double getMoneyToday() {
		return moneyToday;
	}
	/**
	 * hoeveel geld er verwacht wordt dat er verdient wordt
	 * @return
	 */
	public double getExpectedMoney() {
		return expectedMoney;
	}
	/**
	 * de grootte van de rij van normale autos
	 * @return
	 */
	public int getQueueSize() {
		int grootte = entranceCarQueue.carsInQueue();
		return grootte;
	}
	/**
	 * grootte van de abonnementshouders rij
	 * @return
	 */
	public int getQueueSizePass() {
		int grootte = entrancePassQueue.carsInQueue();
		return grootte;
	}
	/**
	 * hiermee worden alle views geupdated
	 * @param carparkview
	 * @param piechart
	 */
	public void updateViews(CarParkView carparkview, ChartView piechart){
		this.carparkview = carparkview;
		this.piechart = piechart;
		carparkview.tick();
		// Update the carparkview.
		carparkview.updateView();
		piechart.updateView();
		piechart.update();
	}
	/**
	 * hoeveel mensen er binnenkomen tussen bepaalde tijdstippen
	 */
	private void carsArriving(){
		if(hour >= 0 && hour< 6) {
			weekDayArrivals= 50;
			weekendArrivals = 80;
			weekDayPassArrivals = 30;
			weekendPassArrivals = 20;
			weekDayRessArrivals = 25;
			weekendRessArrivals = 20;
		}
		else if(hour >= 17 && hour < 0 ) {
			weekDayArrivals = 90;
			weekendArrivals = 190;
			weekDayPassArrivals = 40;
			weekendPassArrivals = 25;
			weekDayRessArrivals = 30;
			weekendRessArrivals = 30;
		}
		
		else {
			weekDayArrivals = 150;
			weekendArrivals = 250;
			weekDayPassArrivals = 50;
			weekendPassArrivals = 25;
			weekDayRessArrivals = 40;
			weekendRessArrivals = 20;
		}
		int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
		addArrivingCars(numberOfCars, NORMCAR);    	
		numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
		addArrivingCars(numberOfCars, PASS);
		numberOfCars=getNumberOfCars(weekDayRessArrivals, weekendRessArrivals);
		addArrivingCars(numberOfCars, RESS); 
	}
	/**
	 * hij voegt auto's aan een queue toe hier
	 * @param queue
	 */
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
	/**
	 * hij get autos die bijna uit de rij gaan
	 */
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

	/**
	 * Bedragen voor de gewone auto's per uur: rood 3 per uur(0.05 per minuut)
	 * De abonementshouders betalen 40euro per week
	 * Reserveer auto's betalen 3euro per uur plus een reserveringsbedrag van 2.00euro
	 */
	private void carsPaying(){
		int i=0;
		
		while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
			Car car = paymentCarQueue.removeCar();
			
			if(car.getHasToPay()== true && car.getColor()== Color.red) {
				totalMoney += car.getTotalMinutes() * 0.05;
				if(hour >=0 &&(hour <= 23 && minute <= 59)) {
					moneyToday += car.getTotalMinutes() * 0.05;
				}
			}
			if(car.getHasToPay()== true && car.getColor()== Color.green) {
				totalMoney += car.getTotalMinutes() * 0.05 + 2;
				if(hour >=0 &&(hour <= 23 && minute <= 59)) {
					moneyToday += car.getTotalMinutes() * 0.05 + 2;
				}
			}
			totalMinutes -= car.getTotalMinutes();
			expectedMoney = car.getColor() == Color.red ? totalMinutes*0.05: totalMinutes*0.05+2;
			carLeavesSpot(car);
			i++;
		}
	}
/**
 * verwijdert auto's uit de car queue
 */
	private void carsLeaving(){
		// Let cars leave.
		int i=0;
		while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
			exitCarQueue.removeCar();
			i++;
		}	
	}
/**
 * returned het aantal autos per uur
 * @param weekDay
 * @param weekend
 * @return
 */
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
	/**
	 * voegt auto's toe aan het einde van de rij
	 * @param numberOfCars
	 * @param type
	 */
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
		case RESS:
			for (int i = 0; i < numberOfCars; i++) {
				entrancePassQueue.addCar(new ReservationCar());
			}
			break;
		}
	}
	/**
	 * removed auto ook van de spot
	 * @param car
	 */
	private void carLeavesSpot(Car car){
		removeCarAt(car.getLocation());
		exitCarQueue.addCar(car);
	}
    /**
     * returned floors
     * @return
     */
	public static int getNumberOfFloors() {
        return numberOfFloors;
    }
	/**
	 * returned rows
	 * @return
	 */
    public static int getNumberOfRows() {
        return numberOfRows;
    }
    /**
     * returned places
     * @return
     */
    public static int getNumberOfPlaces() {
        return numberOfPlaces;
    }
    /**
     * returned het aantal open plekken
     * @return
     */
    public static int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    /**
     * returned auto op een bepaalde locatie
     * @param location
     * @return
     */
    public static Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
         return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }
    /**
     * zet een auto op een plek
     * @param location
     * @param car
     * @return
     */
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
    /**
     * remove auto van de locatie
     * @param location
     * @return
     */
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
    /**
     * afrond methode
     * @param min
     * @param max
     * @return
     */
    public int nextInt(int min, int max) {
    	random = new Random();
    	int locatie = random.nextInt(max - min);
    	return locatie+min;
    }
    /**
     * check zit het deel met normale auto's vol
     * @return
     */
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
    /**
     * is het stuk met abonnementsplekken leeg
     * @return
     */
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
    /**
     * krijg de eerste lokatie die vrij is
     * @param color
     * @return
     */
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

        

    /**
     * return de eerste auto die weggaat van de locatie            
     * @return
     */
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
    /**
     * hiermee gaat de tijd telkens verder en zorg dit dat je het ziet
     * @param carparkview
     * @param piechart
     */
    private void tick(CarParkView carparkview, ChartView piechart) {
    	this.carparkview = carparkview;
    	this.piechart = piechart;
    	advanceTime();
    	handleExit();
    	updateViews(carparkview, piechart);
    	piechart.update();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }
    /**
     * staat er geen andere auto op deze locatie
     * @param location
     * @return
     */
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