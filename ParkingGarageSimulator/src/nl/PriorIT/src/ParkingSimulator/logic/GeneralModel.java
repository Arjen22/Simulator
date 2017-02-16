/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The general model contains all the common classes of all the models and acts as an builder.
 */

package nl.PriorIT.src.ParkingSimulator.logic;

import java.util.ArrayList;
import java.util.List;

import nl.PriorIT.src.ParkingSimulator.view.GeneralView;

/**
 * Hier worden de fields gedefined
 */
public abstract class GeneralModel {
	private List<GeneralView> views;
	/**
	 * maakt een nieuwe list aan
	 */
	public GeneralModel() {
		views=new ArrayList<GeneralView>();
	}
	/**
	 * add een view in de list
	 * @param view
	 */
	public void addView(GeneralView view) {
		views.add(view);
	}
	/**
	 * voor elke GeneralView in lijst view, doe updateview
	 */
	public void notifyViews() {
		for(GeneralView v: views) v.updateView();
	}
}
