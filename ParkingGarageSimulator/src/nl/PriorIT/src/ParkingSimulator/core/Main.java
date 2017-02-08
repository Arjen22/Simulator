package nl.PriorIT.src.ParkingSimulator.core;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;


public class Main {

	private Model simulatorModel;
	private CarParkView cpView;
	private JFrame screen;
	//private CarParkView carParkView;
    /**
     * @param args
     */
	public Main () {
		screen = new JFrame();
		simulatorModel = new Model(1, 2, 10);
    	cpView = new CarParkView(simulatorModel);
    	screen.setSize(800, 600);
		screen.setResizable(false);
		screen.setLayout(null);
		screen.getContentPane().add(cpView);
		cpView.setBounds(10, 10, 200, 200);
		screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.pack();
        screen.setVisible(true);
        cpView.updateView();
	}
	
    public static void main(String[] args) {
    	new Main();
	}
    

}
