package model;


import java.util.Date;

public class Projection {
	
	private int idProjection;
	private Movie movie;
	private TypeOfProjection projectionType;
	private Hall hall;
	private Date dateTimeShow;
	private double price;
	private User admin;
	boolean isActive;
	
	public Projection() {}

	public Projection(int idProjection, Movie movie, TypeOfProjection projectionType, Hall hall, Date dateTimeShow,
			double price, User admin, boolean isActive) {
		super();
		this.idProjection = idProjection;
		this.movie = movie;
		this.projectionType = projectionType;
		this.hall = hall;
		this.dateTimeShow = dateTimeShow;
		this.price = price;
		this.admin = admin;
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public Date getDateTimeShow() {
		return dateTimeShow;
	}

	public void setDateTimeShow(Date dateTimeShow) {
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
