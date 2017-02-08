package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import nl.PriorIT.src.ParkingSimulator.controller.GeneralController;
import nl.PriorIT.src.ParkingSimulator.logic.*;
import nl.PriorIT.src.ParkingSimulator.logic.Model;

import javax.swing.JPanel;

public class CarParkView extends GeneralView {
        
        private Dimension size;
        private Image carParkImage;
        private Model simulatormodel;
        private GeneralController controller;
        private int tickPause = 100;
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView(Model simulatormodel, GeneralController controller) {
            super(simulatormodel, controller);

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
    	  if(carParkImage == null) {
    	      return;
    	  }
    	  
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
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = this.createImage(size.width, size.height);
            }
            
            if(size == null) {
        	System.out.println("Jos eet zijn brood niet op!!!");
            }
            else {
            Graphics graphics = this.getGraphics();
            for(int floor = 0; floor < simulatormodel.getNumberOfFloors(); floor++) {
                for(int row = 0; row < simulatormodel.getNumberOfRows(); row++) {
                    for(int place = 0; place < simulatormodel.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = simulatormodel.getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
        }
        
        public void tick() {
        	simulatormodel.advanceTime();
        	simulatormodel.handleExit();
        	simulatormodel.updateViews();
        	// Pause.
            try {
                Thread.sleep(tickPause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	simulatormodel.handleEntrance();
        }
        
        public static void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
        
    }

