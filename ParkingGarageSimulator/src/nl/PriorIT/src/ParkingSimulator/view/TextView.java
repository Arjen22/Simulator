package nl.PriorIT.src.ParkingSimulator.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class TextView extends JFrame{

	private static final long serialVersionUID = 8084081366423909672L;
	private JLabel openSpots = new JLabel("Free spots:");
	
	public TextView(Model parkingSimulator) {
		//super(parkingSimulator);
		setSize(500, 800);
		JPanel openSpot = new JPanel();
		openSpot.add(openSpots);
		add(openSpot);
		openSpots = new JLabel();
		
		add(openSpots);
		openSpots.setBounds(10, 10, 70, 30);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		openSpots.setText("There are " + String.valueOf(Model.getNumberOfOpenSpots()) + " open parking spots.");

		setVisible(true);
	}

		/*
		 * Dit moet naar updateView gezet worden
		 * 
		 * public void updateView(){
			
			openSpots.setText("There are" + String.valueOf(Model.getNumberOfOpenSpots()) + " open parking spots.");
			
		}*/
	
}
