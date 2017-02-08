package nl.PriorIT.src.ParkingSimulator.controller;

import java.awt.event.*;
import javax.swing.*;

public class InitController extends JFrame {
	private static final long serialVersionUID = 8084081366423909672L;
	private JTextField size;
	private JTextField degree;
	private JButton init;
	
	
	public InitController(ParkingSimulatorLogic parkingSimulator) {
		super(parkingSimulator);
		setSize(90, 130);
		size=new JTextField();
		degree=new JTextField();
		init=new JButton("Init");
		init.addActionListener(this);
		
		this.setLayout(null);
		add(size);
		add(degree);
		add(init);
		size.setBounds(10, 10, 70, 30);
		degree.setBounds(10, 50, 70, 30);
		init.setBounds(10, 90, 70, 30);

		setVisible(true);
	}

	

}
