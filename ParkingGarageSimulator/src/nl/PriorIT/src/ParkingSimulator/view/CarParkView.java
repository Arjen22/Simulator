package nl.PriorIT.src.ParkingSimulator.view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import nl.PriorIT.src.ParkingSimulator.logic.*;

import javax.swing.JPanel;

public class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;
        private Model model;
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView(Model model) {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
        
        /**
         * Paint a place on this car park view in a given color.
         */
        public static void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
        
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = this.createImage(size.width, size.height);
            }
            Graphics graphics = this.getGraphics();
            for(int floor = 0; floor < model.getNumberOfFloors(); floor++) {
                for(int row = 0; row < model.getNumberOfRows(); row++) {
                    for(int place = 0; place < model.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = model.getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        this.drawPlace(graphics, location, color);
                    }
                }
            }
            this.repaint();
        }
    }


