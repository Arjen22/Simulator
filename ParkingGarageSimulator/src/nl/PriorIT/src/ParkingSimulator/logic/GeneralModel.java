/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The general model contains all the common classes of all the models and acts as an builder.
 */

package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.ArrayList;
import java.util.List;

import nl.PriorIT.src.ParkingSimulator.view.GeneralView;


public abstract class GeneralModel {
	private List<GeneralView> views;
	
	public GeneralModel() {
		views=new ArrayList<GeneralView>();
	}
	
	public void addView(GeneralView view) {
		views.add(view);
	}
	
	public void notifyViews() {
		for(GeneralView v: views) v.updateView();
	}
}
