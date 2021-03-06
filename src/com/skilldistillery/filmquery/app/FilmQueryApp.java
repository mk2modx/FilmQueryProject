package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	FilmQueryApp app;
	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//  }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		boolean cont = true;
		int selection = 0;
		while (cont) {
			Scanner kb = new Scanner(System.in);
			System.out.println("|-----------------------------------------------|");
			System.out.println("|        Welcome to VIDEO DEPOT                 |");
			System.out.println("|                                               |");
			System.out.println("|                                               |");
			System.out.println("|                                               |");
			System.out.println("|           Select an option                    |");
			System.out.println("|                                               |");
			System.out.println("|       1-Look up film by id                    |");
			System.out.println("|       2-Look up film by a search keyword      |");
			System.out.println("|       3-Exit                                  |");
			System.out.println("|-----------------------------------------------|");

			selection = kb.nextInt();
			try {
				switch (selection) {
				case 1:
					System.out.println("Enter film id 1-1000");
					int filmId = kb.nextInt();
					Film film = db.findFilmById(filmId);
					film.filmDetails();
					subMenu(input, filmId);
//			List<Actor> actors = db.findActorsByFilmId(filmId);
//			System.out.println("\n\nFeatured Cast : ");
//			for (Actor actor : actors) {
//				actor.printCast();
//			}
					break;
				case 2:
					System.out.println("Enter keyword");
					String keyWord = kb.next();
					List<Film> films = db.searchByKeyword(keyWord);
					for (Film film2 : films) {
						film2.filmDetails();

					}
					System.out.println(films.size()+" film(s) found, Containing the keyword \""+keyWord+ "\".");
					if (films.size() == 0) {
						System.out.println();
						System.out.println();
						System.out.println("----------NO RESULT FOUND-------------");
						System.out.println();
						System.out.println();
					}
					break;
				case 3:
					System.out.println("Goodbye");
					cont = false;
					break;
				default:
					System.out.println();
					System.out.println("-------------Invalid Input----------------");
					System.out.println("-------------NO SUCH FILM----------------");
					System.out.println();
					break;
				}
			} catch (Exception e) {
				System.out.println();
				System.out.println(" ---------------Invalid Input------------------- ");
				System.out.println(" ---------------NO SUCH FILM------------------- ");
				System.out.println();
			}

		}

	}
	public void subMenu(Scanner input, int filmId) {
		int subSelection = 0;
		boolean cont2 = true;
		while (cont2) {
		Scanner kb2 = new Scanner(System.in);
		System.out.println("|-------------------------------------------------|");
		System.out.println("|        Would you like to know more?             |");
		System.out.println("|                                                 |");
		System.out.println("|        1 : Show all available film info.        |");
		System.out.println("|        2 : Return to Main Menu                  |");
		System.out.println("|-------------------------------------------------|");
		subSelection = kb2.nextInt();
		switch(subSelection) {
		case 1:
			Film film = db.findFilmById(filmId);
			
			film.allFilmDetails();
			cont2 = false;
			break;
		case 2:
			System.out.println("Returning to Main Menu");
			cont2 = false;
			break;
			default:
				cont2 = false;
			break;
			
		}
	}
	}
	
}
