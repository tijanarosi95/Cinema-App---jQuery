package model;

import java.util.Date;
import java.util.ArrayList;

public class Ticket {
	
	private int idTicket;
	private Projection projection;
	private ArrayList<Seat> seats;
	private Date dateTimeofSale;
	private User user;
	private boolean isActive;
	
	public Ticket() {}

	public Ticket(int idTicket, Projection projection, ArrayList<Seat> seats, Date dateTimeofSale, User user, boolean isActive) {
		super();
		this.idTicket = idTicket;
		this.projection = projection;
		this.seats = seats;
		this.dateTimeofSale = dateTimeofSale;
		this.user = user;
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public Date getDateTimeofSale() {
		return dateTimeofSale;
	}

	public void setDateTimeofSale(Date dateTimeofSale) {
		this.dateTimeofSale = dateTimeofSale;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
