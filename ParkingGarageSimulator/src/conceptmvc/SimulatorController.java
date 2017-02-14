package conceptmvc;

public class SimulatorController extends AbstractController {
    private Model simulatormodel;
    private int floor;
    
    public SimulatorController(Model simulatormodel) {
	this.simulatormodel = simulatormodel;
	
	
	simulatormodel.floor = 32;
    }
    
    public void mutatefloor () {
	floor = 42;
    }
    
}
