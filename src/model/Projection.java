package model;

import java.sql.Timestamp;

public class Projection {
	
	private int idProjection;
	private Movie movie;
	private TypeOfProjection projectionType;
	private Hall hall;
	private Timestamp dateTimeShow;
	private double price;
	private User admin;
	
	public Projection() {}

	public Projection(int idProjection, Movie movie, TypeOfProjection projectionType, Hall hall, Timestamp dateTimeShow,
			double price, User admin) {
		super();
		this.idProjection = idProjection;
		this.movie = movie;
		this.projectionType = projectionType;
		this.hall = hall;
		this.dateTimeShow = dateTimeShow;
		this.price = price;
		this.admin = admin;
	}

	public int getIdProjection() {
		return idProjection;
	}

	public void setIdProjection(int idProjection) {
		this.idProjection = idProjection;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public TypeOfProjection getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(TypeOfProjection projectionType) {
		this.projectionType = projectionType;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Timestamp getDateTimeShow() {
		return dateTimeShow;
	}

	public void setDateTimeShow(Timestamp dateTimeShow) {
		this.dateTimeShow = dateTimeShow;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	
}
