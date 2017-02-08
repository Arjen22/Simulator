/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The CarParkView builder contains all the common classes of all the CarParkView and acts as an builder for this view.
 */
package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.BorderLayout;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class CarParkViewBuilder extends ViewBuilder {

    private static final long serialVersionUID = -6177925059239806757L;

    public CarParkViewBuilder(Model simulatormodel, GeneralController controller) {
	super(simulatormodel, controller);
	new BorderLayout();
	JTabbedPane SimulatorPane = new JTabbedPane(); 
	JPanel buttonrow = new JPanel();
	
	//Use default FlowLayout.	
	buttonrow.add(this.createButtonRow(true));
	SimulatorPane.addTab("SimulatorView", buttonrow);
	JPanel buttonrow2 = new JPanel();
	buttonrow2.add(this.createButtonRow(false));
	buttonrow2.add(this.createButtonRow(true));
	SimulatorPane.addTab("ManagementView", buttonrow2);
	 
	//Add tabbedPane to this panel.
	add(SimulatorPane, BorderLayout.CENTER);
	
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
