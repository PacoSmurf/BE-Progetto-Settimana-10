package it.film.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import it.film.dao.FilmDao;
import it.film.dao.FilmDaoImp;
import it.film.entity.Film;


@RestController
@RequestMapping("/film")
@Api(value="FilmRest",tags="Gestione film")
public class FilmRest {
	Logger logger = LoggerFactory.getLogger(getClass());
	FilmDao fl = new FilmDaoImp();
	
	@PostMapping
	@ApiOperation(value="inserimento film",notes = "permette di salvare")
	public ResponseEntity<String> inserisciFilm( @RequestBody Film f){
		try {
			fl.salva(f);
			return new ResponseEntity<String>("film salvato",HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		
		return new ResponseEntity<String>("film  non salvato",HttpStatus.BAD_REQUEST);
		

	}
	
	@PutMapping("/{id}")
	@ApiOperation(value="aggiornamento film", notes=" permette di aggiornare un film")
	public ResponseEntity<String> aggiorna (@RequestBody Film f,@PathVariable int id){
		f.setId(id);
		try {
			fl.aggiorna(f);
			 return new ResponseEntity<String>("film aggiornato", HttpStatus.OK);
			 
			
		} catch (Exception e) {
			 return new ResponseEntity<String>("film non aggiornato", HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/{id}")
	@ApiOperation(value="cancellazione film", notes=" permette di cancellare un film")
	public ResponseEntity<String> cancella(@PathVariable int id){
		try {
			fl.cancella(id);
			 return new ResponseEntity<String>("film cancellato", HttpStatus.OK);
			 
			
		} catch (Exception e) {
			 return new ResponseEntity<String>("film non cancellato", HttpStatus.BAD_REQUEST);
		
	}
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="trova film per id", notes="Trova unfilm per id")
	public ResponseEntity<Film> trovaPerId(@PathVariable int id){
		try {
			
			return new ResponseEntity<Film>((Film)fl.trovaById(id), HttpStatus.OK);
			
			
		} catch (Exception e) {
			return new ResponseEntity<Film>( (Film)null, HttpStatus.BAD_REQUEST);
			
		}
		
	}
    
	@GetMapping("/listafilm")
	@ApiOperation(value="Lista film", notes="visualizza lista film")
	public ResponseEntity<List<Film>> getAllFilm(){
		try {
			return new ResponseEntity<List<Film>>(fl.trovaTutti(),HttpStatus.OK);
			
			
		} catch (Exception e) {
			return new ResponseEntity<List<Film>>((List <Film>)null, HttpStatus.BAD_REQUEST);
			
			
		}
	}
@GetMapping("/regista/{regista}")
@ApiOperation(value="trova  per regista", notes="Trova un regista")
public ResponseEntity<List<Film>> trovaPerRegista(@PathVariable  String regista){
	try {
		
		
		 return new ResponseEntity<List<Film>>( fl.trovaByRegista(regista),HttpStatus.ACCEPTED);
		
		
	} catch (Exception e) {
		return new ResponseEntity<List<Film>>((List <Film>)null, HttpStatus.BAD_REQUEST);
		
	}
	
}

}
