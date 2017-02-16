/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 */
package nl.PriorIT.src.ParkingSimulator.controller;


import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.logic.Model;


public abstract class GeneralController extends JPanel {
	protected Model simulatormodel;
	/**
	 * Constructor Controller
	 * @param simulatormodel
	 */
	public GeneralController(Model simulatormodel) {
		this.simulatormodel=simulatormodel;
			
	}


}
