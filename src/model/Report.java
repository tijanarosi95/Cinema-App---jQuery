package model;

public class Report {

	private Movie movie;
	private int projectionsNum;
	private int ticketsNum;
	private double totalPrice;
	
	public Report() {}
	
	
	public Report(Movie movie, int projectionsNum, int ticketsNum, double totalPrice) {
		super();
		this.movie = movie;
		this.projectionsNum = projectionsNum;
		this.ticketsNum = ticketsNum;
		this.totalPrice = totalPrice;
	}
	
	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public int getProjectionsNum() {
		return projectionsNum;
	}


	public void setProjectionsNum(int projectionsNum) {
		this.projectionsNum = projectionsNum;
	}


	public int getTicketsNum() {
		return ticketsNum;
	}


	public void setTicketsNum(int ticketsNum) {
		this.ticketsNum = ticketsNum;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	
	
}
