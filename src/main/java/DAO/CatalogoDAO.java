package DAO;

import ch.qos.logback.classic.Logger;
import models.Catalogo;
import org.slf4j.LoggerFactory;
import util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class CatalogoDAO {
    private static final Logger logger = ( Logger ) LoggerFactory.getLogger(CatalogoDAO.class);

    public void save( Catalogo object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(object);

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();

            logger.error("Error saving object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public void refresh(Catalogo object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            em.refresh(object);

        } finally {
            em.close();
        }

    }

    public void delete(Catalogo object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(object);

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            logger.error("Error deleting object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public Catalogo getById(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            return em.find(Catalogo.class, id);

        } finally {
            em.close();
        }

    }

    public List<Catalogo> getLibroByTitolo( String titolo) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "libroByTitolo" );

            query.setParameter( "titolo", titolo );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Catalogo> getLibroByAutore(String titolo) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "libroByAutore" );

            query.setParameter( "titolo", titolo );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Catalogo> getRivistaByTitolo( String titolo) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "rivistaByTitolo" );

            query.setParameter( "titolo", titolo );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Catalogo> cercaElementoNelCatalogo(String titolo) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "Catalogo.findByTitoloContainsIgnoreCase" );

            query.setParameter( "titolo", titolo );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Catalogo> cercaElementoNelCatalogoPerAnno(int anno) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "Catalogo.findByAnno" );

            query.setParameter( "anno", anno );
            return query.getResultList();

        } finally {
            em.close();
        }
    }


}
