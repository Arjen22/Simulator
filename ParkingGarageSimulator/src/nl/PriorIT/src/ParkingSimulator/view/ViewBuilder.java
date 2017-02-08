/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The viewbuilder contains all the common classes of all the views and acts as an builder for these views.
 */
package nl.PriorIT.src.ParkingSimulator.view;
import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public abstract class ViewBuilder extends JPanel {
	  protected CarParkView carparkview;
	  protected ManagementView managementview;
	  protected Model simulatormodel;
	  protected GeneralController controller;
	  
	  public ViewBuilder(Model simulatormodel, GeneralController controller) {
	  this.simulatormodel=simulatormodel;
	  this.controller=controller;
	  }

	  public CarParkView getCarParkView() { return carparkview; }
	  
	  public ManagementView getManagementView() { return managementview; }
	  
	  public void createNewCarParkView() { carparkview = new CarParkView(simulatormodel, controller); }
	  
	  public void createNewMangagementView() { managementview = new ManagementView(simulatormodel, controller);}

	  /**
	   * public abstract void classes
	   */
	}

