/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The general view contains all the common classes of all the views and acts as a adapter.
 */
package nl.PriorIT.src.ParkingSimulator.view;

import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public abstract class GeneralView extends JPanel {
    
    protected Model simulatormodel;
    protected GeneralController controller;
    protected ChartView piechart;

	public GeneralView(Model simulatormodel, GeneralController controller, ChartView piechart) {
		this.simulatormodel=simulatormodel;
		this.controller=controller;
		this.piechart = piechart;
		simulatormodel.addView(this);
	}
	
	public Model getModel() {
		return simulatormodel;
	}
	
	public void updateView() {
		repaint();
	}
}