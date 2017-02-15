package conceptmvc;

import javax.swing.*;

public abstract class AbstractView extends JPanel {

    private static final long serialVersionUID = 8663323186907709600L;

	protected Model simulatormodel;

	public AbstractView(Model  simulatormodel) {
		this.simulatormodel=simulatormodel;
		simulatormodel.addView(this);
	}
	
	public Model getModel() {
		return simulatormodel;
	}
	
	public void updateView() {
		repaint();
	}
}
