package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.LinkedList;
import java.util.Queue;
/**
 * Maakt een nieuwe linked list aan
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();
    /**
     * hij voegt een auto toe aan de queue
     * @param car
     * @return
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }
    /**
     * hij verwijderd een auto uit de queue
     * @return
     */
    public Car removeCar() {
        return queue.poll();
    }
    /**
     * returned de lengte van de queue
     * @return
     */
    public int carsInQueue(){
    	return queue.size();
    }
   /**
    * deze methode zet de auto terug op de eerste plek van de queue
    * @param car
    */
    public void setAutoBack(Car car) {
    	((LinkedList<Car>) queue).addFirst(car);
    }
}