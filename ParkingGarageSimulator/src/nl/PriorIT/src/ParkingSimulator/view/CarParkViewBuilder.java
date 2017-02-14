/**
 * @author PriorIT
 * @version 0.1 Daybuild 1 Revision 1
 * The CarParkView builder contains all the common classes of all the CarParkView and acts as an builder for this view.
 */
package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Car;
import nl.PriorIT.src.ParkingSimulator.logic.Location;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class CarParkViewBuilder extends ViewBuilder {

    private static final long serialVersionUID = -6177925059239806757L;


    public CarParkViewBuilder() {
	
    }
    
    
    /**
     * Paint a place on this car park view in a given color.
     */
    
    public static void drawTest(Graphics graphics, Color color) {
        graphics.setColor(Color.MAGENTA);
        graphics.fillRect(0,0,400,400);
        graphics.setColor(Color.CYAN);
        graphics.fillOval(100, 0, 100, 100);
        graphics.setColor(Color.ORANGE);
        graphics.fillArc(100, 100, 100, 100, 100, 100);
    }
    


}
