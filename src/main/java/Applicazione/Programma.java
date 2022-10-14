package Applicazione;

import DAO.CatalogoDAO;
import DAO.PrestitoDAO;
import DAO.UtenteDAO;
import models.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programma {
    CatalogoDAO catalogodao = new CatalogoDAO();
    PrestitoDAO prestitoDao = new PrestitoDAO();
    UtenteDAO utenteDao = new UtenteDAO();
    Scanner scanner = new Scanner(System.in);

    private Genere determinaGenere( String str ) {
        return switch (str) {
            case "COMMEDIA" -> Genere.COMMEDIA;
            case "HORROR" -> Genere.HORROR;
            case "AZIONE" -> Genere.AZIONE;
            case "ROMANZI" -> Genere.ROMANZI;
            default -> Genere.FANTASY;
        };
    } // MI SERVE PER LA CREAZIONE DELL'ENUM GENERE
    private Periodicita determinaPeriodicita( String str ) {
        return switch (str) {
            case "MENSILE" -> Periodicita.MENSILE;
            case "SEMESTRALE" -> Periodicita.SEMESTRALE;
            default -> Periodicita.SETTIMANALE;
        };
    } // MI SERVE PER LA CREAZIONE DELL'ENUM PERIODICITA'
    private void getDatabase(){
        standardMessage("Visualizzo il catalogo");
        List<Catalogo> database = catalogodao.cercaElementoNelCatalogo( "" );

        for(Catalogo data : database ) {
            printerCatalog( data  );
        }
    }

    private void printerCatalog(Catalogo item) {
        StringBuilder vuotaGiu = new StringBuilder();

        for( String s : item.toString().split( "" ) ) {
            vuotaGiu.append( "-" );
        }
        System.out.println(item);
        System.out.println(vuotaGiu);
    }

    private void standardMessage( String stringa) {
        StringBuilder vuota = new StringBuilder("****");
        for( String s : stringa.split( "" ) ) {
            vuota.append( "*" );
        }

        System.out.println(vuota);
        System.out.println("* " + stringa + " *");
        System.out.println(vuota);
    }
    public void open() {
        boolean esegui = true;

        do {
            System.out.println();
            System.out.println("MENU");
            System.out.println( """
                1. Per visualizzare il catalogo
                2. Per aggiungere un nuovo libro/rivista
                3. Per rimuovere un libro/rivista
                4. Per effettuare una ricerca in base al codice ISBN, anno e nome autore
                0. Per uscire""");

            try {
                int scelta = scanner.nextInt();

                switch( scelta ) {
                    case 0 -> {
                        standardMessage("ARRIVEDERCI!");
                        esegui = false;
                    }
                    case 1 -> {
                        getDatabase();
                    }
                    case 2 -> {
                        standardMessage("Hai scelto di aggiungere un nuovo libro/rivista");
                        modaleNuovoItem();
                    }
                    case 3 -> {
                        standardMessage("Hai scelto di rimuovere un libro/rivista");
                        modaleRimuovi();
                    }
                    case 4 -> {
                        standardMessage("Hai scelto di effettuare una ricerca");
                        modaleRicerca();
                    }
                    default -> standardMessage("Inserisci un input valido");
                }

            } catch( InputMismatchException e ) {
                standardMessage("Qualcosa è andato storto, riprova");
                scanner.nextLine();
            }
        } while( esegui );

        scanner.close();
    }

    private void modaleNuovoItem() {
        boolean esegui = true;
        do {
            standardMessage( "AGGIUNGI ELEMENTO AL CATALOGO" );
            System.out.println( """
                    Selezionare la tipologia
                    1 per aggiungere un libro
                    2 per aggiungere una rivista
                    0 per uscire""" );
            try {
                int scelta = scanner.nextInt();
                switch (scelta) {
                    case 1 -> {
                        standardMessage( "Hai scelto libro" );
                        scanner.nextLine();
                        standardMessage( "Inserisci il titolo del libro" );
                        String titolo = scanner.nextLine();
                        standardMessage( "Il titolo scelto è: " + titolo );
                        standardMessage( "Inserisci l'anno di pubblicazione" );
                        int anno = scanner.nextInt();
                        standardMessage( "L'anno di pubblicazione: " + anno );
                        standardMessage( "Inserisci il numero di pagine" );
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        standardMessage( "Il numero di pagine è: " + pagine );
                        standardMessage( "Inserisci il nome dell'autore" );
                        String nome = scanner.nextLine();
                        standardMessage( "Il nome dell'autore: " + nome );
                        standardMessage( "Inserisci la categoria del libro" );
                        String categoria = scanner.nextLine().toUpperCase();
                        standardMessage( "La categoria del libro: " + categoria );
                        saveLibro( titolo, nome, determinaGenere( categoria ), anno, pagine );
                    }
                    case 2 -> {
                        standardMessage( "Hai scelto rivista" );
                        scanner.nextLine();
                        standardMessage( "Inserisci il titolo della rivista" );
                        String titolo = scanner.nextLine();
                        standardMessage( "Il tipolo scelto è: " + titolo );
                        standardMessage( "Inserisci l'anno di pubblicazione" );
                        int anno = scanner.nextInt();
                        standardMessage( "L'anno di pubblicazione: " + anno );
                        standardMessage( "Inserisci il numero di pagine" );
                        int pagine = scanner.nextInt();
                        standardMessage( "L'numero di pagine: " + pagine );
                        scanner.nextLine();
                        standardMessage( "Inserisci la periodicità dela rivista" );
                        String periodicita = scanner.nextLine().toUpperCase();
                        standardMessage( "La periodicità  della rivista è: " + periodicita );
                        saveRivista( titolo, determinaPeriodicita( periodicita ), anno, pagine  );
                    }
                    case 0 -> {
                        standardMessage( "Hai scelto di uscire" );
                        esegui = false;
                    }
                    default -> standardMessage( "input non valido" );
                }
            } catch( NumberFormatException e ) {
                standardMessage( "Errore nell'inserimento dell'input" );
            }
            standardMessage( "ECCO IL TUO CATALOGO AGGIORNATO" );
        } while( esegui );
    }

    private void modaleRimuovi() {

        boolean esegui = true;

        do {
            getDatabase();
            standardMessage( "ELIMINA ELEMENTO DAL CATALOGO TRAMITE CODICE ISBN" );
            standardMessage( "Inserisci il codice ISBN per eliminare, 0 per uscire" );

            try {
                int codice = scanner.nextInt();

                if( codice == 0 ) {
                    esegui = false;
                   standardMessage( "Hai scelto di uscire" );
                } else {
                    Catalogo finded = catalogodao.getById( (long)codice );
                    catalogodao.delete( finded );
                    standardMessage( "Eliminato " + finded );
                    standardMessage( "PREMI 1 PER ELIMINARE UN'ALTRO ELEMENTO, 0 PER TORNARE AL MENU" );
                    try {
                        int scelta = scanner.nextInt();
                        if( scelta == 0  ) {
                            esegui = false;
                            standardMessage( "Hai scelto di uscire" );
                        }
                    } catch( Exception e ) {
                        standardMessage( e.getMessage() );
                    }


                }

            } catch( NumberFormatException e ) {
                standardMessage( "Si accettano solo numeri! Riprova" );
            }
        } while( esegui );

    }

    private void modaleRicerca() {
        boolean esegui = true;

        do {
            standardMessage( "MODALITA' RICERCA" );
            standardMessage( "Seleziona il tipo di ricerca" );
            System.out.println( """
                    1. Ricerca per codice ISBN
                    2. Ricerca anno di pubblicazione
                    3. Ricerca per nome autore
                    4. Visualizza catalogo
                    0. Per uscire""" );

            try {
                int scelta = scanner.nextInt();
                switch (scelta) {
                    case 1 -> {
                        standardMessage( "Hai scelto ISBN" );
                        standardMessage( "inserisci il codice ISBN: " );
                        try {
                            int codice = scanner.nextInt();
                            Catalogo trovato;
                            trovato = catalogodao.getById( (long)codice );
                            if( trovato != null ) {
                                standardMessage( "Risultato: " + trovato );
                            } else {
                                standardMessage( "Nessun elemento trovato" );
                            }

                        } catch( InputMismatchException e ) {
                            standardMessage( "Qualcosa è andato storto, Riprova" );
                            scanner.nextLine();
                        }
                    }
                    case 2 -> {
                        standardMessage( "Hai scelto ricerca per anno" );
                        standardMessage( "inserisci l'anno di pubblicazione " );
                        try {
                            int anno = scanner.nextInt();
                            List<Catalogo> trovato = catalogodao.cercaElementoNelCatalogoPerAnno( anno );
                            if( trovato.size() > 0 ) {
                                standardMessage( "Risultati: " + trovato.size() );
                                for ( Catalogo item : trovato ) {
                                    printerCatalog( item );
                                }

                            } else {
                                System.out.println( "Nessun elemento trovato" );
                            }
                            System.out.println();
                        } catch( InputMismatchException e ) {
                            System.out.println( "Qualcosa è andato storto, Riprova" );
                        }
                    }
                    case 3 -> {
                        standardMessage( "Hai scelto ricerca per nome autore" );
                        standardMessage( "inserisci il nome dell'autore" );
                        scanner.nextLine();
                        try {
                            String autore = scanner.nextLine();
                            List<Catalogo> trovato = catalogodao.getLibroByAutore( autore );
                            if( trovato.size() > 0 ) {
                                standardMessage( "Risultati: " + trovato.size() );
                                for ( Catalogo item : trovato ) {
                                    printerCatalog( item );
                                }

                            } else {
                                standardMessage( "Nessun elemento trovato" );
                            }
                            System.out.println();
                        } catch( InputMismatchException e ) {
                            standardMessage( "Qualcosa è andato storto, Riprova" );
                        }
                    }
                    case 0 -> {
                        standardMessage( "Hai scelto di uscire" );
                        esegui = false;
                    }
                    case 4 -> {
                        standardMessage( "Visualizzo il catalogo" );
                        getDatabase();
                    }
                    default -> standardMessage("inserisci un input valido!");
                }
            } catch( InputMismatchException e ) {
                standardMessage( "Qualcosa è andato storto! Riprova" );
                scanner.nextLine();
            }

        } while( esegui );
    }

    private  void saveLibro( String nome, String autore, Genere genere, int anno, int pagine ) {
        Libro lib = new Libro();
        lib.setTitolo( nome );
        lib.setAutore( autore );
        lib.setGenere( genere );
        lib.setAnnoPubblicazione( anno );
        lib.setNumeroPagine( pagine );

        CatalogoDAO catalogo = new CatalogoDAO();
        catalogo.save( lib );
        standardMessage( "Aggiunto nuovo libro" );
        printerCatalog( lib );

    }

    private static void saveRivista( String nome, Periodicita periodicita, int anno, int pagine ) {
        Rivista riv = new Rivista();
        riv.setTitolo( nome );
        riv.setPeriodicita( periodicita );
        riv.setAnnoPubblicazione( anno );
        riv.setNumeroPagine( pagine );

        CatalogoDAO catalogo = new CatalogoDAO();
        catalogo.save( riv );
    }

}
