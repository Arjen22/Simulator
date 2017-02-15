package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.*;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CarParkView extends GeneralView {
        
        private Dimension size;
        private Image carParkImage;
        private Model simulatormodel;
        private GeneralController controller;
		private int abboplekdraw;
		public int getAbboPlekken;
		private JLabel openSpots = new JLabel("");
		private JLabel timeLabel = new JLabel("Week:");
		private JLabel bezetting = new JLabel("BezettingsGraad: ");
		private JLabel carQueue = new JLabel("CarQueueLength: ");
		private JLabel carQueuePass = new JLabel("CarQueuePassLength: ");
		
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView(Model simulatormodel, GeneralController controller) {
            super(simulatormodel, controller);
            size = new Dimension(0,0);
            this.simulatormodel=simulatormodel;
            
            JPanel openSpot = new JPanel();
    		openSpot.add(openSpots);
    		add(openSpot);
    		openSpots = new JLabel();
    		
    		add(openSpots);
    		openSpots.setBounds(10, 10, 70, 30);
    		
    		JPanel time = new JPanel();
    		time.add(timeLabel);
    		add(timeLabel);
    		timeLabel = new JLabel();
    		
    		add(timeLabel);
    		timeLabel.setBounds(10, 30, 70, 30);
    		
    		JPanel bezettingsGraad = new JPanel();
    		bezettingsGraad.add(bezetting);
    		add(bezettingsGraad);
    		bezetting = new JLabel();
    		
    		add(bezetting);
    		bezetting.setBounds(10, 10, 70, 30);
    		
    		JPanel carQueueLength = new JPanel();
    		carQueueLength.add(openSpots);
    		add(carQueueLength);
    		carQueue = new JLabel();
    		
    		add(carQueue);
    		carQueue.setBounds(10, 10, 70, 30);
    		
    		JPanel carQueuePassLength = new JPanel();
    		carQueuePassLength.add(openSpots);
    		add(carQueuePassLength);
    		carQueuePass = new JLabel();
    		
    		add(carQueuePass);
    		carQueuePass.setBounds(10, 10, 70, 30);
    		

    		setVisible(true);
            
        }
    
       
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overridden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 400, 400);
            
          /*if(carParkImage == null) {
              System.out.println("CarParkImage is null!");
              return;
    	  }*/
    	  
    	  Dimension currentSize = getSize();
    	  if (size.equals(currentSize)) {
    	      g.drawImage(carParkImage, 0, 0, null);
    	      
    	  }
    	  else {
    	      // Rescale the previous image
    	      g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
    	  }
      }
        
        /**
         * Paint a place on this car park view in a given color.
         */
        
        
        public void updateView() {
        	
        	openSpots.setText("Open plaatsen: "+simulatormodel.getNumberOfOpenSpots());
        	
        	timeLabel.setText(simulatormodel.getWeeks() + " Day: "+ simulatormodel.getDays() +" Time: " 
        	+ simulatormodel.getHours() +":"+ simulatormodel.getMinutes() + " Money earned = €" 
        	+ Math.round(simulatormodel.getMoney()) + "  Earned today: €" + Math.round(simulatormodel.getMoneyToday())
        	+ "  Expected money today: €" + Math.round(simulatormodel.getExpectedMoney()));
        	
        	bezetting.setText(simulatormodel.getPercent() + "%");
        	
        	carQueue.setText("CarQueue: "+simulatormodel.getQueueSize());
        	
        	carQueuePass.setText("CarQueuePass: "+simulatormodel.getQueueSizePass());
        	
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
        	size = getSize();
                carParkImage = createImage(size.width, size.height);
                
            }
            
            if(size == null) {
        	System.out.println("Size is null!");
            }
            else {
            Graphics graphics = carParkImage.getGraphics();
           //drawTest(graphics, Color.MAGENTA);
            int abboplekken = Model.getAbboPlekken();
            for(int floor = 0; floor < Model.getNumberOfFloors(); floor++) {
                for(int row = 0; row < Model.getNumberOfRows(); row++) {
                    for(int place = 0; place < Model.getNumberOfPlaces(); place++) {
						Color color = Color.white;
                    	if(abboplekken > 0) {
                    		color = Color.yellow; //abboplekken worden hier geel gemaakt
                    		abboplekken--;
                    	}
                        Location location = new Location(floor, row, place);
                        Car car = simulatormodel.getCarAt(location);
                        color = car == null ? color : car.getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            } 
            repaint();
        }
        }
        
        public static void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
        
        public static void drawTest(Graphics graphics, Color color) {
            graphics.setColor(Color.MAGENTA);
            graphics.fillRect(0,0,400,400);
            graphics.setColor(Color.CYAN);
            graphics.fillOval(100, 0, 100, 100);
            graphics.setColor(Color.ORANGE);
            graphics.fillArc(100, 100, 100, 100, 100, 100);
        }
        
        public void tick() {
            for (int floor = 0; floor < simulatormodel.getNumberOfFloors(); floor++) {
                for (int row = 0; row < simulatormodel.getNumberOfRows(); row++) {
                    for (int place = 0; place < simulatormodel.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = simulatormodel.getCarAt(location);
                        if (car != null) {
                            car.tick();
                        }
                    }
                }
            }
        }

        
    }
