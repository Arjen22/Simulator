/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 */
package nl.PriorIT.src.ParkingSimulator.controller;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.logic.Model;


public abstract class GeneralController extends JPanel {
	protected Model simulatormodel;
	private JPanel createButtonRow;
	public GeneralController(Model simulatormodel) {
		this.simulatormodel=simulatormodel;
			
	}
	
	  public JPanel createButtonRow(boolean changeAlignment) {
	        JButton button1 = new JButton("Start Simulatie");
	        button1.setVerticalTextPosition(AbstractButton.BOTTOM);
	        button1.setHorizontalTextPosition(AbstractButton.CENTER);
	 
	        JButton button2 = new JButton("Stop Simulatie");
	        button2.setVerticalTextPosition(AbstractButton.BOTTOM);
	        button2.setHorizontalTextPosition(AbstractButton.CENTER);
	 
	        String title;
	        if (changeAlignment) {
	            title = "Simulatie Beheer";
	            button1.setAlignmentY(BOTTOM_ALIGNMENT);
	            button2.setAlignmentY(BOTTOM_ALIGNMENT);
	        } else {
	            title = "Test";
	        } 
	 
	        JPanel pane = new JPanel();
	        pane.setBorder(BorderFactory.createTitledBorder(title));
	        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
	        pane.add(button1);
	        pane.add(button2);
	        return pane;
	    } 


}
