package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue(){
    	return queue.size();
    }
    public void setAutoBack(Car car) {
    	((LinkedList<Car>) queue).addFirst(car);
    }
}