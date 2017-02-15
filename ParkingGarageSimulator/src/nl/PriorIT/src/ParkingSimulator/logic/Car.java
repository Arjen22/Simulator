package nl.PriorIT.src.ParkingSimulator.logic;

import java.awt.Color;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int totalMinutes;

    /**
     * Constructor for objects of class Car
     */
    public Car() {
    }
    /**
     * methode om de locatie te krijgen van een auto
     * @return
     */
    public Location getLocation() {
        return location;
    }
    /**
     * methode om de locatie mee te setten 
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    /**
     * methode om het totaal aantal minuten dat een auto er staat te krijgen
     * @return
     */
    public int getTotalMinutes() {
    	return totalMinutes;
    }
    /**
     * hiermee zet je het aantal minuten vast
     * @param totalMinutes
     */
    public void setTotalMinutes(int totalMinutes) {
    	this.totalMinutes = totalMinutes;
    }
    /**
     * deze returned hoeveel minuten er nog zijn
     * @return
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }
    /**
     * veranderd minutesleft naar het aantal minuten dat er nog is
     * @param minutesLeft
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    /**
     * returned of een auto betaald of niet
     * @return
     */
    public boolean getIsPaying() {
        return isPaying;
    }
    /**
     * set of een auto betaald of niet
     * @param isPaying
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }
    /**
     * returned of een auto betaald of niet
     * @return
     */
    public boolean getHasToPay() {
        return hasToPay;
    }
    /**
     * set of de auto aan het betalen is of niet
     * @param hasToPay
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }
    /**
     * hierdoor gaat het aantal minuten naar beneden
     */
    public void tick() {
        minutesLeft--;
    }
    /**
     * returned de kleur van een auto
     * @return
     */
    public abstract Color getColor();
}