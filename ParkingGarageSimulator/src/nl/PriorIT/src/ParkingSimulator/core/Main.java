package nl.PriorIT.src.ParkingSimulator.core;


import javax.swing.JFrame;
import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;


public class Main {

	private Model simulatormodel;
	private CarParkView carparkview;
	private JFrame screen;
	private SimulatorController controller;
	//private CarParkView carParkView;
    /**
     * @param args
     */
	public Main () {
	screen = new JFrame();
	simulatormodel = new Model(3, 6, 60);
	controller = new SimulatorController(simulatormodel);
    	carparkview = new CarParkView(simulatormodel,controller);
    	screen.setSize(1920, 1080);
	screen.setResizable(true);
	screen.setLayout(null);
	screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	screen.getContentPane().add(carparkview);
	carparkview.setBounds(0,0,800,600);
        screen.setVisible(true);
	simulatormodel.run(simulatormodel,controller,carparkview);
	}
}
