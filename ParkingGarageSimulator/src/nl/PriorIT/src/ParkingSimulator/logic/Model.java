/**
 * @author PriorIT
 * @version 0.1 DayBuild 1
 * The abstract model contains all the common classes of all the models.
 */
package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JFrame;
import nl.PriorIT.src.ParkingSimulator.view.*;

public class Model extends JFrame {
	
	private CarQueue entranceCarQueue;
	private CarQueue entrancePassQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private Model simulatorModel;
	private CarParkView carParkView;
	private Image carParkImage;
	private Dimension size2;
	
	private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    
    private int size;

	private int day = 0;
	private int hour = 0;
	private int minute = 0;

	private int tickPause = 100;

	int weekDayArrivals= 100; // average number of arriving cars per hour
	int weekendArrivals = 200; // average number of arriving cars per hour
	int weekDayPassArrivals= 50; // average number of arriving cars per hour
	int weekendPassArrivals = 5; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 7; // number of cars that can pay per minute
	int exitSpeed = 5; // number of cars that can leave per minute

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	

	public Model(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
		
		entranceCarQueue = new CarQueue();
	    entrancePassQueue = new CarQueue();
	    paymentCarQueue = new CarQueue();
	    exitCarQueue = new CarQueue();
	    simulatorModel = new Model(3, 6, 30);
	    
	    carParkView = new CarParkView();
	    
        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);

        updateView();
	}

private void handleEntrance(){
	carsArriving();
	carsEntering(entrancePassQueue);
	carsEntering(entranceCarQueue);  	
}

private void handleExit(){
    carsReadyToLeave();
    carsPaying();
    carsLeaving();
}

private void updateViews(){
	tick();
    // Update the car park view.
    updateView();	
}

private void carsArriving(){
	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
    addArrivingCars(numberOfCars, AD_HOC);    	
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
        Location freeLocation = getFirstFreeLocation();
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
	case AD_HOC: 
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


    public void updateView() {
        updateView();
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
    
    public Dimension getSize2() {
        return size2;
    }
    
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize2();
        if (size2.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }
    
    public void updateView2() {
        // Create a new car park image if the size has changed.
        if (!size2.equals(getSize2())) {
            size2 = getSize2();
            carParkImage = carParkView.createImage(size2.width, size2.height);
        }
        Graphics graphics = carParkView.getGraphics();
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    CarParkView.drawPlace(graphics, location, color);
                }
            }
        }
        carParkView.repaint();
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

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
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

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
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




