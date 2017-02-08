package nl.PriorIT.src.ParkingSimulator.core;


import javax.swing.JFrame;
import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;


public class Main {

	private Model simulatorModel;
	private CarParkView carparkview;
	private JFrame screen;
	private SimulatorController controller;
	//private CarParkView carParkView;
    /**
     * @param args
     */
	public Main () {
	screen = new JFrame();
	simulatorModel = new Model(1, 1, 10);
	controller = new SimulatorController(simulatorModel);
    	carparkview = new CarParkView(simulatorModel,controller);
    	screen.setSize(800, 600);
	screen.setResizable(false);
	screen.setLayout(null);
	screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	screen.getContentPane().add(carparkview);
	carparkview.setBounds(0,0,800,600);
        screen.setVisible(true);
	}
}
