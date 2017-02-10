package nl.PriorIT.src.ParkingSimulator.controller;

import java.awt.event.*;
import javax.swing.*;

import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class InitController extends JFrame implements ActionListener{
	private static final long serialVersionUID = 8084081366423909672L;
	private JTextField floors;
	private JTextField rows;
	private JTextField places;
	private JTextField abboplekken;
	private JButton init;
	
	public InitController(Model parkingSimulator) {
		//super(parkingSimulator);
		setSize(200, 300);
		floors=new JTextField();
		rows=new JTextField();
		places = new JTextField();
		abboplekken = new JTextField();
		init=new JButton("Init");
		init.addActionListener(this);
		
		this.setLayout(null);
		add(floors);
		add(rows);
		add(places);
		add(abboplekken);
		add(init);
		floors.setBounds(10, 10, 70, 30);
		rows.setBounds(10, 50, 70, 30);
		places.setBounds(10, 90, 70, 30);
		abboplekken.setBounds(10, 130, 70, 30);
		init.setBounds(10, 170, 70, 30);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		int floors = 0;
		int rows = 0;
		int places = 0;
		int abboplekken = 0;
		
		System.out.println(" floors = " + floors + " rows = " + rows + " places = " + places + " abboplekken = " + abboplekken);
		
		floors = Integer.parseInt(this.floors.getText());
		rows = Integer.parseInt(this.rows.getText());
		places = Integer.parseInt(this.places.getText());
		abboplekken = Integer.parseInt(this.abboplekken.getText());
		
		System.out.println(" floors = " + floors + " rows = " + rows + " places = " + places + " abboplekken = " + abboplekken);
		
	}

	

}
