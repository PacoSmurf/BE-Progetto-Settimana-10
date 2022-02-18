package it.film.dao;

import java.util.List;


import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCrypt;

import it.film.entity.Film;

public class FilmDaoImp implements FilmDao {
	private EntityManager em;
	
	
	/**
	 *  salva film
	 * 
	 *  @author Paco torc  
	 *  @param  f film da salvare
	 *  @return  
	 */
	public void salva(Film f) {
		String incassoCriptato = BCrypt.hashpw(f.getIncasso(), BCrypt.gensalt());
		f.setIncasso(incassoCriptato);
		getEm().getTransaction().begin();
		getEm().persist(f);
		getEm().getTransaction().commit();
		
		
	}
     
	/**
	 * aggiorna un film
	 * 
	 *  @author Paco torc  
	 *  @param  f film da aggiornare
	 *  @return  
	 * @throws Exception 
	 */
	public void aggiorna(Film f) throws Exception {
		String incassoCriptato = BCrypt.hashpw(f.getIncasso(), BCrypt.gensalt());
		f.setIncasso(incassoCriptato);
		Film fmp= getEm().find(Film.class, f.getId());
		if(fmp==null) {
			throw new Exception("film non trovato");
		}
		getEm().getTransaction().begin();
		getEm().merge(f);
		getEm().getTransaction().commit();
		
		
	}
	/**
	 *  cancella film
	 * 
	 *  @author Paco torc  
	 *  @param  f film da cancellare
	 *  @return  
	 */
	public void cancella(int id) {
		try {
			getEm().getTransaction().begin();
			getEm().remove(getEm().find(Film.class, id));
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			
		}
		//Film f= trova(id);
		
		finally {
		getEm().getTransaction().commit();
		
		}
	}
	/**
	 * trova film
	 * 
	 *  @author Paco torc  
	 *  @param  f film da trovare
	 *  @return  
	 */
	public Film trova(int id) {
		return getEm().find(Film.class, id);
		
		
	}
	/**
	 *  trova tutti i film
	 * 
	 *  @author Paco torc  
	 *  @param  f trova tutti i film
	 *  @return  
	 */
	
	@SuppressWarnings("unchecked")
	public List<Film> trovaTutti() {
		return getEm().createNamedQuery("film.trovatutti").getResultList();
		
		
	}
	/**
	 *  trova per regista
	 * 
	 *  @author Paco torc  
	 *  @param  f trova per nome regista
	 *  @return  
	 */
	
	@SuppressWarnings("unchecked")
	public List<Film> trovaByRegista(String regista) {
		return getEm().createNamedQuery("film.trovaperregista").setParameter(1, regista).getResultList();
		
		
		
		
		
	}

	public EntityManager getEm() {
		if(em==null) {
			em= EntityManagerHelp.getEntityManager();
		}
			
		
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Film trovaById(int id) {
		return getEm().find(Film.class, id);
		
		
	}

}
