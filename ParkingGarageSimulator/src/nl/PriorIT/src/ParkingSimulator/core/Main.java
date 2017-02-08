package nl.PriorIT.src.ParkingSimulator.core;

import java.awt.BorderLayout;
import java.awt.Container;

import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;


public class Main {

	private Model simulatorModel;
	private CarParkView cpView;
	//private CarParkView carParkView;
    /**
     * @param args
     */
	public Main () {
		simulatorModel = new Model(3, 6, 30);
    	cpView = new CarParkView(simulatorModel);
    	
    	
    	Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);

        carParkView.updateView();
	}
	
    public static void main(String[] args) {
    	new Main();
	}
    

}
