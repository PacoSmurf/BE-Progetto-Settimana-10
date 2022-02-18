package it.film.dao;

import java.util.List;

import it.film.entity.Film;



public interface FilmDao {
	
	public void salva (Film f);
	public void aggiorna(Film f) throws Exception;
	public void cancella(int id);
	public Film trova(int id);
	public List<Film> trovaTutti();
	public List <Film> trovaByRegista( String regista);
	public Film trovaById(int id);
	

}
