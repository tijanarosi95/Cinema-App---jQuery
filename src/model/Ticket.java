package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Ticket {
	
	private int idTicket;
	private Projection projection;
	private ArrayList<Seat> seats;
	private Timestamp dateTimeofSale;
	private User user;
	
	public Ticket() {}

	public Ticket(int idTicket, Projection projection, ArrayList<Seat> seats, Timestamp dateTimeofSale, User user) {
		super();
		this.idTicket = idTicket;
		this.projection = projection;
		this.seats = seats;
		this.dateTimeofSale = dateTimeofSale;
		this.user = user;
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

	public Timestamp getDateTimeofSale() {
		return dateTimeofSale;
	}

	public void setDateTimeofSale(Timestamp dateTimeofSale) {
		this.dateTimeofSale = dateTimeofSale;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
