package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class CarParkView extends GeneralView {

    private static final long serialVersionUID = 5085977141273471393L;

    public CarParkView(Model simulatormodel, GeneralController controller) {
	super(simulatormodel, controller);
	setSize(800,600);
	
	new BorderLayout();
	JTabbedPane SimulatorPane = new JTabbedPane(); 
	JPanel buttonrow = new JPanel();
	//Use default FlowLayout.	
	buttonrow.add(controller.createButtonRow(true));
	SimulatorPane.addTab("SimulatorView", buttonrow);
	        
	JPanel buttonrow2 = new JPanel();
	buttonrow2.add(controller.createButtonRow(false));
	buttonrow2.add(controller.createButtonRow(true));
	SimulatorPane.addTab("ManagementView", buttonrow2);
	 
	//Add tabbedPane to this panel.
	add(SimulatorPane, BorderLayout.CENTER);
	        
    }
    
 
}
