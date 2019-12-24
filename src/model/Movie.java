package model;

import java.util.ArrayList;

import enums.Genre;

public class Movie {
	
	private int idMovie;
	private String name;
	private String director;
	private String actors;
	private ArrayList<Genre> genres;
	private int duration;
	private String distribution;
	private String originCountry;
	private int productionYear;
	private String description;
	
	public Movie() {}

	public Movie(int idMovie, String name, String director, String actors, ArrayList<Genre> genres, int duration,
			String distribution, String originCountry, int productionYear, String description) {
		super();
		this.idMovie = idMovie;
		this.name = name;
		this.director = director;
		this.actors = actors;
		this.genres = genres;
		this.duration = duration;
		this.distribution = distribution;
		this.originCountry = originCountry;
		this.productionYear = productionYear;
		this.description = description;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
