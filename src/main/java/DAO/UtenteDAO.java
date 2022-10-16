package DAO;

import ch.qos.logback.classic.Logger;
import models.Prestito;
import models.Utente;
import org.slf4j.LoggerFactory;
import util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class UtenteDAO {

    private static final Logger logger = ( Logger ) LoggerFactory.getLogger( UtenteDAO.class);

    public void save( Utente object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(object);

            System.out.println( "SALVATO" + object);

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();

            logger.error("Error saving object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public void refreshNome(Utente object, String nome) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Utente utente = em.find(object.getClass(), object.getNumeroTessera());

            em.getTransaction().begin();
            utente.setNome( nome );
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    public void refreshCognome(Utente object, String cognome) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Utente utente = em.find(object.getClass(), object.getNumeroTessera());

            em.getTransaction().begin();
            utente.setCognome( cognome );
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    public void refreshDataNascita(Utente object, LocalDate data) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Utente utente = em.find(object.getClass(), object.getNumeroTessera());

            em.getTransaction().begin();
            utente.setDataDiNascita( data );
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    public void delete(Utente object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(em.contains(object) ? object : em.merge(object));

            System.out.println("...Eliminato -> " + object);
            System.out.println();

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            logger.error("Error deleting object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public Utente getById(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            return em.find(Utente.class, id);

        } finally {
            em.close();
        }

    }

    public List<Utente> getAll() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            return em.createQuery("select p from Utente p").getResultList();

        } finally {
            em.close();
        }
    }

    public List<Utente> getUserByFullName( String nome, String cognome ) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "userByFullName" );

            query.setParameter( "nome", nome );
            query.setParameter( "cognome", cognome );
            return query.getResultList();

        } finally {
            em.close();
        }
    }
}
