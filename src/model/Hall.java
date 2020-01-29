package model;

import java.util.ArrayList;

public class Hall {
	
	private int id;
	private String name;
	private ArrayList<TypeOfProjection> projectionTypes;
	
	public Hall() {}

	

	public Hall(int id, String name, ArrayList<TypeOfProjection> projectionTypes) {
		super();
		this.id = id;
		this.name = name;
		this.projectionTypes = projectionTypes;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<TypeOfProjection> getProjectionTypes() {
		return projectionTypes;
	}



	public void setProjectionTypes(ArrayList<TypeOfProjection> projectionTypes) {
		this.projectionTypes = projectionTypes;
	}

	
	
	
}
