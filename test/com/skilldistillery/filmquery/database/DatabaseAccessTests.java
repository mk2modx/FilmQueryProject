package com.skilldistillery.filmquery.database;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
  private DatabaseAccessor db;

  @BeforeEach
  void setUp() throws Exception {
    db = new DatabaseAccessorObject();
  }

  @AfterEach
  void tearDown() throws Exception {
    db = null;
  }

  @Test
  void test_getFilmById_with_invalid_id_returns_null() {
    Film f = db.findFilmById(-42);
    assertNull(f);
  }
  @Test
  void test_find_Film_By_Id() {
	  Film f = db.findFilmById(5);
	  assertEquals(f.getTitle(), "AFRICAN EGG");
  }
  @Test
  void test_find_Actor_By_Id() {
	  Actor d = db.findActorById(1);
	  assertEquals(d.getFirstName(), "Penelope");
  }
  @Test
  void test_find_Actors_By_Film_Id() {
	  List<Actor> actors = db.findActorsByFilmId(2);
	  assertEquals("Bob",actors.get(0).getFirstName());
  }
  @Test
  void test_find_Films_By_Actor_Id() {
	 List<Film> films = db.findFilmsByActorId(1);
	 
	  assertEquals("ACADEMY DINOSAUR", films.get(0).getTitle());
  }
  @Test
  void test_search_By_Keyword() {
	  List<Film> f1 = db.searchByKeyword("thrilling");
	  
	  assertEquals("ATLANTIS CAUSE", f1.get(0).getTitle());
  }
  
  
  

}
