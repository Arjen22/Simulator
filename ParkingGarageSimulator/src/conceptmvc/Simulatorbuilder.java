package conceptmvc;

import javax.swing.JFrame;


public class Simulatorbuilder extends JFrame {
    	private JFrame mainscreen;
	private AbstractView carparkview;
	private Model simulatormodel;
	private AbstractController simulatorcontroller;
    
    public Simulatorbuilder() {
	simulatormodel = new Model(3,6,100,50);
	carparkview = new CarParkViewmvc(simulatormodel);
	simulatorcontroller = new SimulatorController(simulatormodel);
    
	mainscreen = new JFrame();
	mainscreen.setSize(800,600);
	mainscreen.setResizable(false);
	mainscreen.setLayout(null);
	mainscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainscreen.getContentPane().add(carparkview);
	mainscreen.getContentPane().add(simulatorcontroller);
	carparkview.setBounds(0,60,800,600);
	simulatorcontroller.setBounds(0,0,800,600);
	mainscreen.setVisible(true);
    }
    
    public void DoTick() 
    {
    	Model.run(true);
    }
}
