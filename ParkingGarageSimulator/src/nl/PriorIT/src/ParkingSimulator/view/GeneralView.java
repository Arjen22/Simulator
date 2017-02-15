/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The general view contains all the common classes of all the views and acts as a adapter.
 */
package nl.PriorIT.src.ParkingSimulator.view;

import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
/**
 * Hiermee worden de fields gedefined
 */
public abstract class GeneralView extends JPanel {
    
    protected Model simulatormodel;
    protected GeneralController controller;
    protected ChartView piechart;
    /**
     * de constructor van GeneralView
     * @param simulatormodel
     * @param controller
     * @param piechart
     */
	public GeneralView(Model simulatormodel, GeneralController controller, ChartView piechart) {
		this.simulatormodel=simulatormodel;
		this.controller=controller;
		this.piechart = piechart;
		simulatormodel.addView(this);
	}
	/**
	 * dit returned het model
	 * @return
	 */
	public Model getModel() {
		return simulatormodel;
	}
	/**
	 * deze update de generalview
	 */
	public void updateView() {
		repaint();
	}
}