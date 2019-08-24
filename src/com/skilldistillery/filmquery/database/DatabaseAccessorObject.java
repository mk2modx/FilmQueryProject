package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
//only thing touching the database
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false"; //connecting string with sql driver and host
	private final String userName = "student";  //will not change in this situation
	private final String password = "student"; 
	
	static { //as soon as you run this , you want this block to occur
		try {//register driver with driver manager
			Class.forName("com.mysql.jdbc.Driver");//don't forget to convert to maven
		} catch (ClassNotFoundException e) {//maven downloads needed dependencies
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Film findFilmById(int filmId) {//JUNIT tested
		Film film = null; //finding one film , creating an object film
		Connection conn;
		try {
			 conn = DriverManager.getConnection(URL, userName, password); //connect to database
			 String sql = "SELECT id, title, description, release_year, language_id,"
			 		+ "rental_duration, rental_rate, length, replacement_cost, "
			 		+ "rating, special_features  FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getString("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				
				
			}
			filmResult.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		return film;
	}
	@Override
	public Actor findActorById(int actorId) {//junit tested
		Actor actor = null;
		Connection conn;
		try {
			 conn = DriverManager.getConnection(URL, userName, password); //connect to database
			 String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor();
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				actor.setFilms(findFilmsByActorId(actorId));
				
			}
			actorResult.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		return actor;
	}
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List <Actor> actors = new ArrayList<>();
		Connection conn;
		try {
			 conn = DriverManager.getConnection(URL, userName, password); //connect to database
			 String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor"
			 		+ " JOIN film_actor on actor.id = film_actor.actor_id"
			 		+ " JOIN film on film_actor.film_id = film.id"
			 		+ " WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorSetResult = stmt.executeQuery();
			while (actorSetResult.next()) {
	
				int actorId = actorSetResult.getInt("id");
				String firstName = actorSetResult.getString("first_name");
				String lastName = actorSetResult.getString("last_name");
				
			      Actor actor = new Actor(actorId, firstName, lastName);
			      
			      actors.add(actor);
				
			}
			actorSetResult.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		return actors;
	}
	
	public List<Film> findFilmsByActorId(int actorId){//junit tested
		List <Film> films = new ArrayList<>();
		Connection conn;
		try {
			 conn = DriverManager.getConnection(URL, userName, password); //connect to database
			 String sql = "SELECT id, title, description, release_year, language_id, rental_duration,"
			 		+ "rental_rate, length, replacement_cost, rating, special_features"
			 		+ " FROM film JOIN film_actor ON film.id = film_actor.film_id"
			 		+ " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet filmSetResult = stmt.executeQuery();
			while (filmSetResult.next()) {
	
				int filmId = filmSetResult.getInt("id");
			      String title = filmSetResult.getString("title");
			      String desc = filmSetResult.getString("description");
			      int releaseYear = filmSetResult.getShort("release_year");
			      String langId = filmSetResult.getString("language_id");
			      int rentDur = filmSetResult.getInt("rental_duration");
			      double rate = filmSetResult.getDouble("rental_rate");
			      int length = filmSetResult.getInt("length");
			      double repCost = filmSetResult.getDouble("replacement_cost");
			      String rating = filmSetResult.getString("rating");
			      String features = filmSetResult.getString("special_features");
			      
			      Film film = new Film(filmId, title, desc, releaseYear, langId,
			                           rentDur, rate, length, repCost, rating, features);
			      films.add(film);
				
			}
			filmSetResult.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		return films;

	}
	
	public List<Film> searchByKeyword(String s) {
		List <Film> films = new ArrayList<>(); //finding one film , creating an object film
		Connection conn;
		String search = s;
		try {
			 conn = DriverManager.getConnection(URL, userName, password); //connect to database
			 String sql = "SELECT id, title, description, release_year, language_id,"
			 		+ " rental_duration, rental_rate, length, replacement_cost, "
			 		+ " rating, special_features FROM film WHERE description like ?"
			 				+ " OR title like ?";
			 
			 
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+search+"%");
			stmt.setString(2, "%"+search+"%");
			ResultSet searchResult = stmt.executeQuery();
			
			while (searchResult.next()) {
				
				int filmId = searchResult.getInt("id");
			      String title = searchResult.getString("title");
			      String desc = searchResult.getString("description");
			      int releaseYear = searchResult.getShort("release_year");
			      String langId = searchResult.getString("language_id");
			      int rentDur = searchResult.getInt("rental_duration");
			      double rate = searchResult.getDouble("rental_rate");
			      int length = searchResult.getInt("length");
			      double repCost = searchResult.getDouble("replacement_cost");
			      String rating = searchResult.getString("rating");
			      String features = searchResult.getString("special_features");
			      
			      Film film = new Film(filmId, title, desc, releaseYear, langId,
			                           rentDur, rate, length, repCost, rating, features);
			      films.add(film);
				
			}
			
			searchResult.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		
		return films;
		
	}

}
