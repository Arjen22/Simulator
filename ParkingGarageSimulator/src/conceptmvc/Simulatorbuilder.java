package conceptmvc;

import javax.swing.JFrame;

public class Simulatorbuilder extends JFrame {
    private Model simulatormodel;
    private CarParkView carparkview;
    private static JFrame mainframe;
    
    public Simulatorbuilder() {
    	
    mainframe = new JFrame();
    mainframe.setSize(800, 600);
    mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainframe.setVisible(true);
    
    }
    
    
}
