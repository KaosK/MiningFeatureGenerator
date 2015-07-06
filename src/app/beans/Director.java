/**
 * 
 */
package app.beans;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * @author svensson
 *
 */
public class Director implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342019385940651163L;
	
	private String directorName;
	private Integer directorId;
	private TreeMap<String, Movie> movies;
	
	/**
	 * 
	 */
	public Director() {
		this.movies = new TreeMap<String, Movie>();
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public Integer getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Integer directorId) {
		this.directorId = directorId;
	}

	public TreeMap<String, Movie> getMovies() {
		return movies;
	}

	public void setMovies(TreeMap<String, Movie> movies) {
		this.movies = movies;
	}

}
