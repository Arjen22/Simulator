/**
 * This is the builder for the simulator and will fit all the simulator components together.
 */

package nl.PriorIT.src.ParkingSimulator.core;

import javax.swing.JFrame;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView.drawPanel;
import nl.PriorIT.src.ParkingSimulator.view.GeneralView;

public class SimulatorBuilder extends JFrame {
    
    private JFrame simulatorview;
    private GeneralView carparkview;
    private CarParkView.drawPanel carview;
    private Model simulatormodel;
    private SimulatorController controller;
    
    /**
     * Simulatorbuilder Constructor fits all the components together.
     * @param simulatorview
     * @return nothing
     */
    
    public SimulatorBuilder () {
	/**
	 * Creates the model and adds the model to the simulatorview
	 */
	simulatormodel = new Model();
	controller = new SimulatorController(simulatormodel);
	/**
	 * Creates the view and adds the view to the simulatorview1.
	 */
	carparkview = new CarParkView(simulatormodel,controller);
	
	
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
	carparkview.setBounds(0,0,800,600);
	drawPanel carview = new drawPanel();
	carview.verfmessage();

    }
    
}
