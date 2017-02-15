package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.PriorIT.src.ParkingSimulator.logic.Car;
import nl.PriorIT.src.ParkingSimulator.logic.Location;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

public class ChartView extends JPanel{
	
	private JLabel totalCars = new JLabel();
	private JLabel blueCars = new JLabel();
	private JLabel redCars = new JLabel();
	
	private int blue;
	private int red;
	private Image piechart;
	private Dimension size;
	
	public ChartView() {
		
    	add(new JLabel("Number of cars: "));
        add(totalCars);
        add(new JLabel("ParkingpassCars (Blue): "));
        add(blueCars);
        add(new JLabel("Cars (Red): "));
        add(redCars);

		
	}
	
	
	
	public Dimension setSizePieChart() {
		
		return new Dimension(600, 600);
		
	}
	
	public void paintComponent(Graphics g) {

			if (piechart == null) {
				return;
			}
			
			Dimension currentSize = setSizePieChart();
			if (size.equals(currentSize))
			{
				g.drawImage(piechart, 0, 0, null);
			}
			else {
				g.drawImage(piechart, 0, 0, currentSize.width, currentSize.height, null);
			}
        }
	
	public void updateView() {
		int blue = 0;
		int red = 0;
		size = setSizePieChart();
		piechart = createImage(size.width, size.height);
		Graphics graphics = piechart.getGraphics();
		 for(int floor = 0; floor < Model.getNumberOfFloors(); floor++) {
	        	for(int row = 0; row < Model.getNumberOfRows(); row++) {
	        		for(int place = 0; place < Model.getNumberOfPlaces(); place++) {
	        			Location location = new Location(floor, row, place);
	        			Car car = Model.getCarAt(location);
	        			drawcircle(graphics);
	        			if(car != null && car.getColor() == Color.red){
	        				red++;
	        			}else if(car != null && car.getColor() == Color.blue){
	        				blue++;
	        			}
	        			this.setNumbers(blue,red);
	        		} 
	        	}
	       }
		 repaint();
	        
		
	}
	

	
	private void drawcircle(Graphics graphics) {

        int maxAmount=(Model.getNumberOfPlaces()*Model.getNumberOfFloors()*Model.getNumberOfRows());
		int arc1 = blue * 360 / maxAmount;
        int arc2 = red * 360 / maxAmount;
        int arc3 = 360;
        graphics.setColor(super.getBackground()); 
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight()); 
                 
        graphics.setColor(Color.WHITE); 
        graphics.fillArc(50, 50, 360, 360, 0, arc3);
        graphics.setColor(Color.BLUE); 
        graphics.fillArc(50, 50, 360, 360, 0, arc1); 
          
        graphics.setColor(Color.RED); 
        graphics.fillArc(50, 50, 360, 360, arc1, arc2); 
        

           
	}
	
	
	public void setNumbers(int numberBlue, int numberRed) {
		
		this.blue = numberBlue;
		this.red = numberRed;
		
	}
	public void update() {
		totalCars.setText(String.valueOf(Model.getNumberOfPlaces()*Model.getNumberOfFloors()*Model.getNumberOfRows()-Model.getNumberOfOpenSpots()));
        blueCars.setText(String.valueOf(blue));
        redCars.setText(String.valueOf(red));
	}



}
