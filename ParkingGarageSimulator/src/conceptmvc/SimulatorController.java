package conceptmvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SimulatorController extends AbstractController implements ActionListener  {
    	private Model simulatormodel;
	private JButton buttonPlusOne;
	private JButton buttonPlusHunderd;
	private JButton startPause;
	public static boolean runs;
    
    public SimulatorController(Model simulatormodel) {
	super(simulatormodel);
	

	runs = true;

		
		buttonPlusOne = new JButton();
		buttonPlusOne.setToolTipText("Simuleer 1 minuut");

		buttonPlusHunderd = new JButton();
		buttonPlusHunderd.setToolTipText("Simuleer 100 minuten");
		
		startPause = new JButton();
		startPause.setToolTipText("Start/Pause simulation");

		buttonPlusOne.addActionListener(this);
		buttonPlusHunderd.addActionListener(this);
		startPause.addActionListener(this);

		buttonPlusOne.setActionCommand("Plus1");
		buttonPlusHunderd.setActionCommand("Plus100");
		startPause.setActionCommand("StartPause");

		add(buttonPlusOne);
		add(buttonPlusHunderd);
		add(startPause);

		setVisible(true);
	}
	/**
	 * gets the logic per button action event
	 * @param ActionEvent Event from a button action
	 * @return void 
	 * */
	public void actionPerformed(ActionEvent e) {
		if ("Plus1".equals(e.getActionCommand())) {
			runs=false;
			Model.simulateByMinute(1);
		} else if ("Plus100".equals(e.getActionCommand())) {
			runs=false;
			Model.simulateByMinute(100);
		} else if("StartPause".equals(e.getActionCommand())){
			if(runs){
				runs=false;
			} else{
				runs=true;
			}
		}
	}
	
    }

