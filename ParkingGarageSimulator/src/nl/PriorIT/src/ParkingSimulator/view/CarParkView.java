/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The CarParkView contains all the classes of the CarParkView and acts as an view for the parking garage.
 */
package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class CarParkView extends GeneralView {

    private static final long serialVersionUID = 5085977141273471393L;
    private CarParkViewBuilder builder;
    

    public CarParkView(Model simulatormodel, GeneralController controller) {
	super(simulatormodel, controller);
	setSize(800,600);
	builder = new CarParkViewBuilder(simulatormodel, controller);
	new BorderLayout();
	JTabbedPane SimulatorPane = new JTabbedPane(); 
	JPanel buttonrow = new JPanel();
	
	//Use default FlowLayout.	
	buttonrow.add(builder.createButtonRow(true));
	SimulatorPane.addTab("SimulatorView", buttonrow);
	JPanel buttonrow2 = new JPanel();
	buttonrow2.add(builder.createButtonRow(false));
	buttonrow2.add(builder.createButtonRow(true));
	SimulatorPane.addTab("ManagementView", buttonrow2);
	 
	//Add tabbedPane to this panel.
	add(SimulatorPane, BorderLayout.CENTER);
	  }
    public void paintComponent(Graphics g) {
  	  super.paintComponent(g);
	  g.setColor(Color.WHITE);
	  g.fillRect(0, 0, 800, 600);
    }
    
    public static class drawPanel extends JPanel {

	private static final long serialVersionUID = -5034546724609528550L;
	
	public void verfmessage() {
	    System.out.println("Verfmethode aangeroepen");
	}
	
	public void paintComponent(Graphics g) {
	    	  super.paintComponent(g);
	    	  g.setColor(Color.BLUE);
		  g.fillRect(0, 0, 200, 200);
	      }
	        
    }
    
 
}
