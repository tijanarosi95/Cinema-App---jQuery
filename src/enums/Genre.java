package enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Genre {
	ACTION,
	ADVENTURE,
	COMEDY,
	CRIME,
	DRAMA,
	FANTASY,
	HISTORY,
	SCIENCE_FICTION,
	THRILLER,
	WESTERN;
	
	public static Map<String, Genre> getGenres() {
		Map<String, Genre> genres = new LinkedHashMap<String, Genre>();
		
		for(Genre genre : Genre.values()) {
			genres.put(genre.toString(), genre);
		}
		
		return genres;
	}
}
