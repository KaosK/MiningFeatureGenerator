package app;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import app.beans.Director;
import app.beans.Movie;
import app.csvwriter.CSVWriter;
import app.utils.Helper;

/**
 * 
 * <h1>MiningFeatureGenerator DBS_SS2015</h1> The program extracts Features for
 * DataMining from a simple Database and exports them as a CSV file.
 * <p>
 * 
 * @author Projektgruppe DBS2015: Sven Klaus, Alan Rachid, Sebastian Wicke
 * 
 */

public class MiningFeatureGenerator {

	public static void main(String[] args) throws SQLException {

		// connect to the database
		Path configFile = Paths.get("dbsApp.properties");
		ConnectionFactory connFactory = new ConnectionFactory(configFile);
		Connection conn = connFactory.getConnection();

		System.out.println();
		System.out.println("Starting...");
		System.out.println();
		if (calcGeometricMeanHelper(Arrays.asList(5.9, 6.9, 6.7, 6.4, 7.0)) == 0.958161006841741) {
			System.out.println("calcGeometricMeanHelper Test: Success");
			System.out.println();
		}

		// read the database
		ArrayList<ArrayList<String>> features = createFeatures(new DBReader(conn));

		// Export the CSV
		Path csvFile = Paths.get("export.csv");
		String[] headers = { "ImdbID", "Rating", "Actor1", "Actor2", "Actor3", "DirectorAverage",
				"AverageYearsBetweenMovies", "GeometricMean" };
		try {
			FileWriter fWriter = new FileWriter(csvFile.toFile());
			CSVWriter writer = new CSVWriter(fWriter, headers);
			// writer.write(sTable1.getList());
			writer.write(features);
			fWriter.close();
		} catch (IOException e) {
			System.out.println("SUMTHIN WENT HORRIBLY WRONG");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("...done");

		// close connection
		if (conn != null) {
			conn.close();
		}
	}

	private static ArrayList<ArrayList<String>> createFeatures(DBReader dbReader) throws SQLException {
		Map<String, Movie> movies = dbReader.getMovies();
		ArrayList<ArrayList<String>> features = new ArrayList<ArrayList<String>>();

		// create features for every movie
		for (Map.Entry<String, Movie> entry : movies.entrySet()) {

			Movie movie = entry.getValue();
			LinkedHashMap<Integer, String> directors = movie.getDirector();

			ArrayList<String> attributes = new ArrayList<String>();

			// 1st: IMDB_ID
			attributes.add(movie.getImdbId());

			// 2nd: Rating
			attributes.add(movie.getRating());

			// 3rd, 4th, 5th: Actor1, Actor2, Actor3
			String[] array = movie.getActor().toArray(new String[3]);
			for (int i = 0; i < 3; i++) {
				if (array[i] != null) {
					attributes.add(array[i]);
				} else {
					attributes.add("");
				}
			}

			// get Directors for this Movie
			Iterator<Integer> dIter = directors.keySet().iterator();
			Director director1 = dbReader.getDirectors().get(dIter.next());
			Director director2 = null;
			if (dIter.hasNext()) {
				director2 = dbReader.getDirectors().get(dIter.next());
			}

			// 6th: DirectorAverage Rating
			Double directorAverage = getAverageRating(director1);
			if (director2 != null) {
				directorAverage = (directorAverage + getAverageRating(director2)) / 2;
			}
			directorAverage = (double) Math.round(directorAverage * 1000) / 1000;
			attributes.add(directorAverage.toString());

			// 7th: Abstand der Filme in Jahren
			int yearsBetween = calcYearsBetweenMovies(director1);
			if (director2 != null) {
				yearsBetween = (yearsBetween + calcYearsBetweenMovies(director2)) / 2;
			}
			attributes.add(Integer.toString(yearsBetween));

			// 8th: Geometrisches Mittel der letzten fünf Filme(rating)
			double ratingsGeometricMean = calcGeometricMean(director1);
			if (director2 != null) {
				ratingsGeometricMean = Math.sqrt((ratingsGeometricMean * calcGeometricMean(director2)));
			}
			// subtract 1 from mean and round
			ratingsGeometricMean = ratingsGeometricMean - 1;
			ratingsGeometricMean = (double) Math.round(ratingsGeometricMean * 10000) / 10000;

			attributes.add(Double.toString(ratingsGeometricMean));

			features.add(attributes);
		}
		return features;
	}

	private static Double getAverageRating(Director tempDirector) {
		TreeMap<String, Movie> tempDirectorsMovies = tempDirector.getMovies();
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

		return d;
	}

	private static int calcYearsBetweenMovies(Director director) {
		Iterator<String> iterator = director.getMovies().descendingKeySet().iterator();
		ArrayList<Integer> yearSpacing = new ArrayList<>();
		int lastYear = Integer.parseInt(iterator.next().substring(0, 4));
		while (iterator.hasNext()) {
			int year = Integer.parseInt(iterator.next().substring(0, 4));
			yearSpacing.add(lastYear - year);
			lastYear = year;
		}
		int result = yearSpacing.stream().reduce(0, Integer::sum) / Math.max(1, yearSpacing.size());
		return result;
	}

	private static double calcGeometricMean(Director director) {
		ArrayList<Double> ratings = director.getMovies().descendingMap().values().stream().limit(5)
				.map(Movie::getRating).map((s) -> s.equals("NA") ? "0" : s).map(Double::parseDouble)
				.collect(Collectors.toCollection(ArrayList::new));
		return calcGeometricMeanHelper(ratings);
	}

	private static double calcGeometricMeanHelper(List<Double> ratings) {
		Iterator<Double> iterator = ratings.iterator();
		ArrayList<Double> ratingRatios = new ArrayList<>();
		Double prevRating = iterator.next();
		while (iterator.hasNext()) {
			Double rating = (Double) iterator.next();
			ratingRatios.add(prevRating / rating);
			prevRating = rating;
		}

		Double geometricMean;
		if (ratingRatios.size() > 1) {
			geometricMean = Math.pow(ratingRatios.stream().reduce((x, y) -> x * y).get(), 1.0 / ratingRatios.size());
			;
		} else {
			geometricMean = 1.0;
		}

		return geometricMean;
	}
}