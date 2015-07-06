/**
 * 
 */
package app.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * @author svensson
 *
 */
public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3821608298217964617L;

	private String imdbId;
	private String movieTitle;
	private String prodYear;
	private String rating;
	private String votes;
	private String runningTime;
	private LinkedHashMap<Integer, String> director;
	private LinkedHashSet<String> actor;
	private LinkedHashSet<String> genre;

	/**
	 * 
	 */
	public Movie() {
		director = new LinkedHashMap<Integer, String>();
		actor = new LinkedHashSet<String>();
		genre = new LinkedHashSet<String>();
	}

	/**
	 * @return the imdbId
	 */
	public String getImdbId() {
		return imdbId;
	}

	/**
	 * @param imdbId
	 *            the imdbId to set
	 */
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	/**
	 * @return the movieTitle
	 */
	public String getMovieTitle() {
		return movieTitle;
	}

	/**
	 * @param movieTitle
	 *            the movieTitle to set
	 */
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	/**
	 * @return the prodYear
	 */
	public String getProdYear() {
		return prodYear;
	}

	/**
	 * @param prodYear
	 *            the prodYear to set
	 */
	public void setProdYear(String prodYear) {
		this.prodYear = prodYear;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the votes
	 */
	public String getVotes() {
		return votes;
	}

	/**
	 * @param votes
	 *            the votes to set
	 */
	public void setVotes(String votes) {
		this.votes = votes;
	}

	/**
	 * @return the runningTime
	 */
	public String getRunningTime() {
		return runningTime;
	}

	/**
	 * @param runningTime
	 *            the runningTime to set
	 */
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	/**
	 * @return the director
	 */
	public LinkedHashMap<Integer, String> getDirector() {
		return director;
	}

	/**
	 * @param director
	 *            the director to set
	 */
	public void setDirector(LinkedHashMap<Integer, String> director) {
		this.director = director;
	}

	/**
	 * @return the actor
	 */
	public LinkedHashSet<String> getActor() {
		return actor;
	}

	/**
	 * @param actor
	 *            the actor to set
	 */
	public void setActor(LinkedHashSet<String> actor) {
		this.actor = actor;
	}

	/**
	 * @return the genres
	 */
	public LinkedHashSet<String> getGenre() {
		return genre;
	}

	/**
	 * @param genres
	 *            the genres to set
	 */
	public void setGenre(LinkedHashSet<String> genres) {
		this.genre = genres;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + imdbId + "; " + movieTitle + "; " + prodYear + "; " + rating + "; " + votes + "; " + runningTime
				+ "; " + director + "; " + actor + "; " + genre;
	}

}
