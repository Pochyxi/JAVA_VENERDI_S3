import DAO.CatalogoDAO;
import DAO.PrestitoDAO;
import DAO.UtenteDAO;
import models.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main( String[] args ) {
        CatalogoDAO catalogodao = new CatalogoDAO();
        PrestitoDAO prestitoDao = new PrestitoDAO();
        UtenteDAO utenteDao = new UtenteDAO();

//        saveLibro( "Rock in me", "Freddy Mercury", Genere.HORROR, 2000, 580 );
//        saveRivista( "Le profezie di Nostradamus", Periodicita.MENSILE, 2022, 100 );


        // RICERCA UTENTE PER ID
        Utente utente = utenteDao.getById( 37L );

        // RICERCA NEL CATALOGO PER ISBN
        Catalogo lib = catalogodao.getById( 1L );

        System.out.println("ELEMENTO TROVATO");
        System.out.println(lib);
        System.out.println();

        System.out.println("UTENTE TROVATO");
        System.out.println(utente);
        System.out.println();

//         CREAZIONE DI UN PRESTITO
//        savePrestito(utenteDao.getUserByFullName( "tommaso", "ranocchia" ).get( 0 ) ,
//                catalogodao.getLibroByTitolo( "stars wars" ).get( 0 ) );

        System.out.println("CERCA PRESTITI PER NOME");
       List<Prestito> prestiti =
               prestitoDao.getPrestitoByUserId( utenteDao.getUserByFullName( "cristina", "anguilotta" ).get( 0 ).getNumeroTessera() );
        System.out.println("RISULTATI: " + prestiti.size());
        for (Prestito prestito : prestiti) {
            System.out.println(prestito);
        }
        System.out.println();

        System.out.println("CERCA PRESTITI SCADUTI NON RESTITUITI");
        List<Prestito> prestitiScadutiNonRestituiti = prestitoDao.getScadutiNonRestituiti();
        System.out.println("RISULTATI: " + prestitiScadutiNonRestituiti.size());
        for (Prestito prestito : prestitiScadutiNonRestituiti) {
            System.out.println(prestito);
        }
        System.out.println();

        System.out.println("RICERCA UTENTI PER NOME");
        List<Utente> utentiFinded = utenteDao.getUserByFullName( "tommaso", "ranocchia" );
        System.out.println("RISULTATI: " + utentiFinded.size());
        for (Utente ut : utentiFinded) {
            System.out.println(ut);
        }
        System.out.println();

        System.out.println("CERCA LIBRO PER TITOLO");
        System.out.println(catalogodao.getLibroByTitolo("Angeli e demoni"  ));
        System.out.println();

        System.out.println("CERCA RIVISTA PER TITOLO");
        List<Catalogo> rivisteFinded = catalogodao.getRivistaByTitolo( "grand hotel" );
        System.out.println("RISULTATI: " + rivisteFinded.size());
        for (Catalogo ut : rivisteFinded) {
            System.out.println(ut);
        }
        System.out.println();

        System.out.println("CERCA LIBRO PER AUTORE");
        List<Catalogo> libriFinded =  catalogodao.getLibroByAutore( "Dan Brown" );
        System.out.println("RISULTATI: " + libriFinded.size());
        for (Catalogo ut : libriFinded) {
            System.out.println(ut);
        }
        System.out.println();

        System.out.println("CERCA ELEMENTO NEL CATALOGO");
        List<Catalogo> catalogoFinded = catalogodao.cercaElementoNelCatalogo( "" );
        System.out.println("RISULTATI: " + catalogoFinded.size());
        for (Catalogo ut : catalogoFinded) {
            System.out.println(ut);
        }
        System.out.println();

        System.out.println("CERCA ELEMENTO NEL CATALOGO PER ANNO DI PUBBLICAZIONE");
        List<Catalogo> annoFinded = catalogodao.cercaElementoNelCatalogoPerAnno( 2022 );
        System.out.println("RISULTATI: " + annoFinded.size());
        for (Catalogo ut : annoFinded) {
            System.out.println(ut);
        }
        System.out.println();

    }

public static void saveLibro( String nome, String autore, Genere genere, int anno, int pagine ) {
        Libro lib = new Libro();
        lib.setTitolo( nome );
        lib.setAutore( autore );
        lib.setGenere( genere );
        lib.setAnnoPubblicazione( anno );
        lib.setNumeroPagine( pagine );

    CatalogoDAO catalogo = new CatalogoDAO();
    catalogo.save( lib );
}
    public static void saveRivista( String nome, Periodicita periodicita, int anno, int pagine ) {
        Rivista riv = new Rivista();
        riv.setTitolo( nome );
        riv.setPeriodicita( periodicita );
        riv.setAnnoPubblicazione( 1967 );
        riv.setNumeroPagine( 789 );

        CatalogoDAO catalogo = new CatalogoDAO();
        catalogo.save( riv );
    }

    public static void saveUtente( String nome, String cognome, LocalDate nascita ) {
        Utente ut = new Utente();
        ut.setNome( nome );
        ut.setCognome( cognome );
        ut.setDataDiNascita( nascita );

        UtenteDAO utente = new UtenteDAO();
        utente.save( ut );
    }

    public static void savePrestito(Utente utente, Catalogo item ) {
        Prestito ut = new Prestito();
        ut.setUtente( utente );
        ut.setCatalogo( item );

        PrestitoDAO prestito = new PrestitoDAO();
        prestito.save( ut );
    }
}
