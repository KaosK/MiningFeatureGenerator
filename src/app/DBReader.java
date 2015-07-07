package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import app.beans.Director;
import app.beans.Movie;
import app.utils.Helper;

public class DBReader {

	private Connection connection;

	private Map<String, Movie> movies;
	private LinkedHashMap<Integer, Director> directors;

	private final int limit = 1000000;
//	private final int limit = 10000;

	public DBReader(Connection connection) throws SQLException {
		this.connection = connection;
		init();
	}

	/**
	 * @return the movies
	 */
	public Map<String, Movie> getMovies() {
		return movies;
	}

	/**
	 * @return the directors
	 */
	public LinkedHashMap<Integer, Director> getDirectors() {
		return directors;
	}

	public void init() throws SQLException {
		String query = "SELECT movie.imdbid, mname, pyear, rating, votings, runtime, "
				+ "dname, director.directorid, aname, actor.actorid, gname, genre.genreid "
				+ "FROM (((((( directs JOIN acts_in ON directs.imdbid=acts_in.imdbid) "
				+ "JOIN genre_of on acts_in.imdbid=genre_of.imdbid) " + "JOIN actor ON acts_in.actorid=actor.actorid) "
				+ "JOIN director ON directs.directorid=director.directorid) "
				+ "JOIN genre ON genre_of.genreid=genre.genreid) " + "JOIN movie ON movie.imdbid=directs.imdbid) "
				+ "ORDER BY movie.imdbid asc Limit ?";
		PreparedStatement prepstmt = connection.prepareStatement(query);
		prepstmt.setInt(1, limit);
		ResultSet rset = prepstmt.executeQuery();
		//		ResultSetMetaData rsmd = rset.getMetaData();
		//		int columnCount = rsmd.getColumnCount();

		movies = new LinkedHashMap<String, Movie>();
		directors = new LinkedHashMap<Integer, Director>();

		while (rset.next()) {
			Movie tempMovie;
			String movieId = rset.getString("imdbid");
			if (movies.containsKey(movieId)) {
				tempMovie = movies.get(movieId);
			} else {
				tempMovie = new Movie();
			}
			tempMovie.setImdbId(movieId);
			tempMovie.setMovieTitle(rset.getString("mname"));
			tempMovie.setProdYear(rset.getString("pyear"));
			tempMovie.setRating(rset.getString("rating"));
			tempMovie.setRunningTime(rset.getString("runtime"));
			tempMovie.setVotes(rset.getString("votings"));
			tempMovie.getDirector().put(rset.getInt("directorid"), rset.getString("dname"));
			tempMovie.getActor().add(rset.getString("aname"));
			tempMovie.getGenre().add(rset.getString("gname"));

			movies.put(movieId, tempMovie);

			Director tempDirector;
			Integer directorId = rset.getInt("directorid");
			if (directors.containsKey(directorId)) {
				tempDirector = directors.get(directorId);
			} else {
				tempDirector = new Director();
			}
			tempDirector.setDirectorId(directorId);
			tempDirector.setDirectorName(rset.getString("dname"));
			tempDirector.getMovies().put(rset.getString("pyear"), tempMovie);

			directors.put(directorId, tempDirector);
		}

		if (prepstmt != null) {
			prepstmt.close();
		}
	}

	public void prnDirectors() {
		for (Entry<Integer, Director> entry : directors.entrySet()) {
			Director director = entry.getValue();

			System.out.print(director.getDirectorId());
			System.out.print("/ ");
			System.out.print(director.getDirectorName());
			System.out.print("/ ");

			TreeMap<String, Movie> tempDirectorsMovies = director.getMovies();
			ArrayList<String> firstThreeRatings = new ArrayList<String>();
			int i = 0;
			for (Entry<String, Movie> entry2 : tempDirectorsMovies.descendingMap().entrySet()) {
				firstThreeRatings.add(entry2.getValue().getRating());
				if (i++ > 2) {
					break;
				}
			}

			double d = 0;
			for (String rating : firstThreeRatings) {
				d = d + Helper.tryConvertString2Double(rating);
			}
			d = d / firstThreeRatings.size();
			System.out.print("(" + d + ")");
			System.out.print("/ ");

			System.out.println(firstThreeRatings);
		}

	}

}
