/**
 * This is the builder for the simulator and will fit all the simulator components together.
 */

package nl.PriorIT.src.ParkingSimulator.core;

import java.awt.Dimension;

import javax.swing.JFrame;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.controller.InitController;
import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;
import nl.PriorIT.src.ParkingSimulator.view.GeneralView;
import nl.PriorIT.src.ParkingSimulator.view.TextView;

public class SimulatorBuilder extends JFrame {
    
    private static JFrame simulatorview;
    private CarParkView carparkview;
    private Model simulatormodel;
    private SimulatorController controller;
    private InitController init;
    private TextView textview;
    
    /**
     * Simulatorbuilder Constructor fits all the components together.
     * @param simulatorview
     * @return nothing
     */
    
    public SimulatorBuilder () {
	/**
	 * Creates the model and adds the model to the simulatorview
	 */
	simulatormodel = new Model(3,6,30,125);
	carparkview = new CarParkView(simulatormodel);
	controller = new SimulatorController(simulatormodel);
	init = new InitController(simulatormodel);
	textview = new TextView(simulatormodel);
	
	
	
	
	/**
	 * Creates the view and adds the view to the simulatorview1.
	 */
	
	
	/**
	 * Builds the Main Window. 
	 */
	
	simulatorview = new JFrame();
	simulatorview.setSize(800,600);
	simulatorview.setResizable(false);
	simulatorview.setLayout(null);
	simulatorview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	simulatorview.getContentPane().add(carparkview);
	simulatorview.setVisible(true);
	carparkview.setBounds(0,60,800,600);
	simulatormodel.run();
	this.getParkingGaragesize();
    }
    
    /** Checking Classes for testing purposes and check evaluations */
    
    public int getParkingGaragesize () {
	System.out.println(simulatormodel.getFloors() + " " + simulatormodel.getRows() + " " + simulatormodel.getPlaces());
	return simulatormodel.getFloors() + simulatormodel.getRows() + simulatormodel.getPlaces();
    }
    
    public JFrame getwindowsize() {
	simulatorview.getSize();
	System.out.println("Het Simulatormodel maakt gebruik van het volgende frame " + simulatorview);
	System.out.println("En heeft de volgende size" );
	return simulatorview;
    }
    
    public Model simcheck(Model simulatormodel) {
	System.out.println("Simulatormodel is aanwezig " + " " + simulatormodel);
	return simulatormodel;
    }
    
    public SimulatorController controllercheck(SimulatorController controller) {
	System.out.println("SimulatorController is aanwezig " + " " + controller);
	return controller;
    }
    
    public CarParkView getcpview (CarParkView cpview) {
	return cpview;
    }
    
    
}
