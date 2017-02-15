package nl.PriorIT.src.ParkingSimulator.core;


import javax.swing.JFrame;
import nl.PriorIT.src.ParkingSimulator.controller.SimulatorController;
import nl.PriorIT.src.ParkingSimulator.logic.Model;
import nl.PriorIT.src.ParkingSimulator.view.CarParkView;
import nl.PriorIT.src.ParkingSimulator.view.ChartView;


public class Main {

	private Model simulatormodel;
	private CarParkView carparkview;
	private JFrame screen;
	public ChartView piechart;
	private SimulatorController controller;
	//private CarParkView carParkView;
    /**
     * @param args
     */
	public Main () {
	screen = new JFrame();
	simulatormodel = new Model(3, 6, 30, 140);
	controller = new SimulatorController(simulatormodel);
    	carparkview = new CarParkView(simulatormodel,controller,piechart);
    	screen.setSize(1498,761);
	screen.setResizable(true);
	screen.setLayout(null);
	screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	screen.getContentPane().add(carparkview);
	carparkview.setBounds(612,23,800,600);
        
	piechart = new ChartView();
	piechart.setBounds(10, 23, 600, 600);
	screen.getContentPane().add(piechart);
	
	screen.setVisible(true);
	simulatormodel.run(simulatormodel,controller,carparkview, piechart);
	}
}