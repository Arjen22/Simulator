package conceptmvc;

import javax.swing.JPanel;


 public abstract class AbstractController extends JPanel {

    private static final long serialVersionUID = -2506190748261133902L;

	protected Model  simulatormodel;
	
	public AbstractController(Model simulatormodel) {
		this.simulatormodel=simulatormodel;
	}
}
