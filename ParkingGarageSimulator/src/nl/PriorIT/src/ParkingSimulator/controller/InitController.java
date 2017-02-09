package nl.PriorIT.src.ParkingSimulator.controller;

import java.awt.event.*;
import javax.swing.*;

import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class InitController extends JFrame implements ActionListener{
	private static final long serialVersionUID = 8084081366423909672L;
	private JTextField size;
	private JTextField degree;
	private JButton init;
	
	
	public InitController(Model parkingSimulator) {
		//super(parkingSimulator);
		setSize(200, 300);
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
