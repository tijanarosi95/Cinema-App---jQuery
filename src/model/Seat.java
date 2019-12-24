package model;

public class Seat {
	
	private int serialNumber;
	private Hall hall;
	
	
	public Seat() {}


	public Seat(int serialNumber, Hall hall) {
		super();
		this.serialNumber = serialNumber;
		this.hall = hall;
	}


	public int getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	public Hall getHall() {
		return hall;
	}


	public void setHall(Hall hall) {
		this.hall = hall;
	}
	
	
}
