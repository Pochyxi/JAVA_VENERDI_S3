package Applicazione;

import DAO.CatalogoDAO;
import DAO.PrestitoDAO;
import DAO.UtenteDAO;
import models.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programma {
    CatalogoDAO catalogodao = new CatalogoDAO();
    PrestitoDAO prestitoDao = new PrestitoDAO();
    UtenteDAO utenteDao = new UtenteDAO();
    Scanner scanner = new Scanner( System.in );

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

    private void getDatabase() {
        standardMessage( "Visualizzo il catalogo libri/riviste" );
        List<Catalogo> database = catalogodao.cercaElementoNelCatalogo( "" );

        for( Catalogo data : database ) {
            printerCatalog( data );
        }
    }

    private void getPrestiti() {
        standardMessage( "Visualizzo il catalogo prestiti" );
        List<Prestito> database = prestitoDao.getAll();

        for( Prestito data : database ) {
            printerPrestito( data );
        }
    }

    private void getUtenti() {
        standardMessage( "Visualizzo il catalogo utenti" );
        List<Utente> database = utenteDao.getAll();

        for( Utente data : database ) {
            printerUtente( data );
        }
    }

    private void printerCatalog( Catalogo item ) {
        StringBuilder vuotaGiu = new StringBuilder();

        for( String s : item.toString().split( "" ) ) {
            vuotaGiu.append( "-" );
        }
        System.out.println( item );
        System.out.println( vuotaGiu );
    }

    private void printerPrestito( Prestito item ) {

        String itemC = item.getRestituzione() == null ? "NO " : "SI ";
        System.out.println( "|| PRESTITO ID: '" + item.getId() + "' | DATA DI EMISSIONE: '" + item.getDataPrestito() +
                "' |" +
                " " +
                "DATA SCADENZA: '" + item.getDataScadenzaPrestito() + "' | RESTITUITO: '" + itemC + "' ||" + "\n"
                + item.getUtente() + "\n"
                + item.getCatalogo()
        );
        System.out.println( "-".repeat( item.toString().length() / 2 ) );
    }

    private void printerUtente( Utente item ) {

        StringBuilder vuotaGiu = new StringBuilder();

        for( String s : item.toString().split( "" ) ) {
            vuotaGiu.append( "-" );
        }
        System.out.println( item );
        System.out.println( vuotaGiu );
    }

    private void standardMessage( String stringa ) {
        StringBuilder vuota = new StringBuilder( "****" );
        for( String s : stringa.split( "" ) ) {
            vuota.append( "*" );
        }

        System.out.println( vuota );
        System.out.println( "* " + stringa + " *" );
        System.out.println( vuota );
    }

    private void infoMessage( String stringa ) {
        StringBuilder vuota = new StringBuilder( "----------" );
        vuota.append( "-".repeat( Math.max( 0, stringa.length() + 1 ) ) );

        System.out.println( vuota );
        System.out.println( "| INFO | " + stringa + " |" );
        System.out.println( vuota );
    }

    private void errorMessage( String stringa ) {
        StringBuilder vuota = new StringBuilder( "-!--!--!--" );
        vuota.append( "-".repeat( Math.max( 0, stringa.length() + 1 ) ) );

        System.out.println( vuota );
        System.out.println( "| ERROR | " + stringa + " |" );
        System.out.println( vuota );
    }

    public void open() {
        boolean esegui = true;

        do {
            System.out.println();
            System.out.println( "MENU" );
            System.out.println( """
                    1. Per visualizzare il catalogo
                    2. Per aggiungere un nuovo libro/rivista
                    3. Per rimuovere un libro/rivista
                    4. Per effettuare una ricerca in base al codice ISBN, anno e nome autore
                    5. Per aprire il menu prestiti
                    6. Per aprire il menu UTENTE
                    0. Per uscire""" );

            try {
                int scelta = scanner.nextInt();

                switch (scelta) {
                    case 0 -> {
                        standardMessage( "ARRIVEDERCI!" );
                        esegui = false;
                    }
                    case 1 -> {
                        getDatabase();
                    }
                    case 2 -> {
                        infoMessage( "Hai scelto di aggiungere un nuovo libro/rivista" );
                        modaleNuovoItem();
                    }
                    case 3 -> {
                        infoMessage( "Hai scelto di rimuovere un libro/rivista" );
                        modaleRimuovi();
                    }
                    case 4 -> {
                        infoMessage( "Hai scelto di effettuare una ricerca" );
                        modaleRicerca();
                    }
                    case 5 -> {
                        infoMessage( "Hai scelto menu prestiti" );
                        modalePrestiti();
                    }
                    case 6 -> {
                        infoMessage( "Hai scelto menu utenti" );
                        modaleUtente();
                    }
                    default -> errorMessage( "Inserisci un input valido" );
                }

            } catch( InputMismatchException e ) {
                errorMessage( "Qualcosa è andato storto, riprova" );
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
                        saveRivista( titolo, determinaPeriodicita( periodicita ), anno, pagine );
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
                    Catalogo finded = catalogodao.getById( ( long ) codice );
                    catalogodao.delete( finded );
                    standardMessage( "Eliminato " + finded );
                    standardMessage( "PREMI 1 PER ELIMINARE UN'ALTRO ELEMENTO, 0 PER TORNARE AL MENU" );
                    try {
                        int scelta = scanner.nextInt();
                        if( scelta == 0 ) {
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
                            trovato = catalogodao.getById( ( long ) codice );
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
                                for( Catalogo item : trovato ) {
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
                                for( Catalogo item : trovato ) {
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
                    default -> standardMessage( "inserisci un input valido!" );
                }
            } catch( InputMismatchException e ) {
                standardMessage( "Qualcosa è andato storto! Riprova" );
                scanner.nextLine();
            }

        } while( esegui );
    }

    private void modalePrestiti() {
        boolean esegui = true;

        do {
            standardMessage( "MENU PRESTITI" );
            System.out.println( """
                    1. Visualizza tutti i prestiti
                    2. Visualizza prestiti di un utente
                    3. Visualizza prestiti scaduti e non restituiti
                    4. Emetti un prestito
                    5. Elimina un prestito
                    6. Estingui un prestito
                    0. Vai al menu principale""" );

            try {
                int scelta = scanner.nextInt();

                switch (scelta) {
                    case 0 -> {
                        infoMessage( "Hai scelto menu principale" );
                        esegui = false;
                    }

                    case 1 -> {
                        getPrestiti();
                    }

                    case 2 -> {
                        infoMessage( "Hai scelto ricerca prestiti per nome utente" );
                        scanner.nextLine();
                        try {
                            infoMessage( "Inserisci il nome dell'utente" );
                            String nome = scanner.nextLine();

                            infoMessage( "Inserisci il cognome dell'utente" );
                            String cognome = scanner.nextLine();

                            List<Prestito> finded = prestitoDao.getPrestitoByUserId( utenteDao.getUserByFullName(
                                    nome,
                                    cognome ).get( 0 ).getNumeroTessera() );

                            System.out.println( "-".repeat( finded.get( 0 ).toString().length() / 2 ) );
                            for( Prestito p : finded ) {
                                printerPrestito( p );
                            }

                        } catch( Exception e ) {
                            errorMessage( e.getMessage() );
                            scanner.nextLine();
                        }
                    }

                    case 3 -> {
                        infoMessage( "Hai scelto ricerca prestiti scaduti e non restituiti" );

                        List<Prestito> finded = prestitoDao.getScadutiNonRestituiti();

                        if( finded.size() > 0 ) {
                            System.out.println( "-".repeat( finded.get( 0 ).toString().length() / 2 ) );
                            for( Prestito p : finded ) {
                                printerPrestito( p );
                            }
                        } else {
                            errorMessage( "Non esistono al momento prestiti di questo genere" );
                        }

                    }

                    case 4 -> {
                        infoMessage( "Hai scelto emissione nuovo prestito" );
                        scanner.nextLine();

                        try {
                            infoMessage( "STEP 1" );
                            infoMessage( "Ricerca utente richiedente per nome e cognome" );

                            boolean richiedi = true;
                            long userid = 0;

                            while( richiedi )
                                try {
                                    infoMessage( "Inserisci Nome" );
                                    String nome = scanner.nextLine();

                                    infoMessage( "Inserisci Cognome" );
                                    String cognome = scanner.nextLine();

                                    infoMessage( "Ricerco richiedente..." );

                                    List<Utente> finded = utenteDao.getUserByFullName( nome, cognome );

                                    if( finded.size() == 0 ) {
                                        errorMessage( "Nome utente non trovato" );
                                        infoMessage( "Ricontrolla: " + nome + " " + cognome );
                                    } else if( finded.size() > 1 ) {
                                        infoMessage( "Trovati più utenti con lo stesso nome" );
                                        for( Utente u : finded ) {
                                            printerUtente( u );
                                        }
                                        infoMessage( "Inserisci il numero di tessera dell'utente" );
                                        userid = scanner.nextInt();
                                        infoMessage( "Hai scelto l'utente con id: " + userid );
                                        richiedi = false;
                                    } else {
                                        infoMessage( "Un solo utente trovato" );
                                        infoMessage( finded.get( 0 ).toString() );
                                        userid = finded.get( 0 ).getNumeroTessera();
                                        infoMessage( "Scelto automaticamente utente con id: " + userid );
                                        richiedi = false;
                                    }

                                } catch( Exception e ) {
                                    errorMessage( "Qualcosa è andato storto, riprova!" );
                                    scanner.nextLine();
                                }

                            boolean richiedi2 = true;
                            long itemId = 0;

                            while( richiedi2 ) {
                                try {
                                    infoMessage( "Inserisci il titolo dell'item che si vuole dare in prestito" );
                                    String titolo = scanner.nextLine();

                                    List<Catalogo> findedItems = catalogodao.cercaElementoNelCatalogo( titolo );

                                    if( findedItems.size() == 0 ) {
                                        errorMessage( "Titolo non trovato" );
                                        infoMessage( "Ricontrolla: " + titolo );
                                    } else if( findedItems.size() > 1 ) {
                                        infoMessage( "Trovati più items con lo stesso titolo" );
                                        for( Catalogo u : findedItems ) {
                                            printerCatalog( u );
                                        }
                                        infoMessage( "Inserisci il codice ISBN" );
                                        itemId = scanner.nextInt();
                                        infoMessage( "Hai scelto il codice ISBN: " + itemId );
                                        richiedi2 = false;
                                    } else {
                                        infoMessage( "Un solo item trovato" );
                                        infoMessage( findedItems.get( 0 ).toString() );
                                        itemId = findedItems.get( 0 ).getCodiceisbn();
                                        infoMessage( "Scelto automaticamente item con codice ISBN: " + itemId );
                                        richiedi2 = false;
                                    }
                                } catch( Exception e ) {
                                    infoMessage( "Qualcosa è andato storto, riprova!" );
                                    scanner.nextLine();
                                }

                                if( userid != 0 && itemId != 0 ) {
                                    infoMessage( "Emetto prestito..." );
                                    savePrestito( utenteDao.getById( userid ), catalogodao.getById( itemId ) );
                                } else {
                                    errorMessage( "Prestito non eseguito" );
                                }

                            }
                        } catch( Exception e ) {
                            errorMessage( "Qualcosa è andato storto, riprova" );
                            scanner.nextLine();
                        }
                    }

                    case 5 -> {
                        infoMessage( "Hai scelto di eliminare un prestito" );
                        long itemId = 0;
                        boolean esegui2 = true;

                        while( esegui2 ) {

                            try {
                                System.out.println( """
                                        1. Per ricercare prestito in base al nome\s
                                        2. Per eliminare prestito in base all'id\s
                                        0. Per uscire
                                        """ );
                                int scelta2 = scanner.nextInt();
                                scanner.nextLine();
                                switch (scelta2) {
                                    case 0 -> {
                                        esegui2 = false;
                                    }

                                    case 1 -> {
                                        long idForP = 0;
                                        infoMessage( "Inserisci il nome del debitore" );
                                        String nome = scanner.nextLine();
                                        infoMessage( "Inserisci il cognome del debitore" );
                                        String cognome = scanner.nextLine();

                                        infoMessage( "Ricerco..." );

                                        List<Utente> finded = utenteDao.getUserByFullName( nome, cognome );

                                        if( finded.size() == 0 ) {
                                            errorMessage( "Nome utente non trovato" );
                                            infoMessage( "Ricontrolla: " + nome + " " + cognome );
                                        } else if( finded.size() > 1 ) {
                                            infoMessage( "Trovati più utenti con lo stesso nome" );
                                            for( Utente u : finded ) {
                                                printerUtente( u );
                                            }
                                            infoMessage( "Inserisci il numero di tessera dell'utente" );
                                            idForP = scanner.nextInt();
                                            infoMessage( "Hai scelto l'utente con id: " + idForP );
                                        } else {
                                            infoMessage( "Un solo utente trovato" );
                                            infoMessage( finded.get( 0 ).toString() );
                                            idForP = finded.get( 0 ).getNumeroTessera();
                                            infoMessage( "Scelto automaticamente utente con id: " + idForP );
                                        }

                                        List<Prestito> prestitiTrovati = prestitoDao.getPrestitoByUserId( idForP );

                                        if( prestitiTrovati.size() == 0 ) {
                                            errorMessage( "Prestito non trovato" );
                                            infoMessage( "Ricontrolla" );
                                        } else if( prestitiTrovati.size() > 1 ) {
                                            infoMessage( "Trovati più prestiti dello stesso utente" );
                                            for( Prestito u : prestitiTrovati ) {
                                                printerPrestito( u );
                                            }
                                            infoMessage( "Inserisci id prestito che si vuole eliminare" );
                                            itemId = scanner.nextInt();
                                            infoMessage( "Hai scelto l'utente con id: " + itemId );
                                        } else {
                                            infoMessage( "Un solo prestito trovato" );
                                            printerPrestito( prestitiTrovati.get( 0 ) );
                                            idForP = prestitiTrovati.get( 0 ).getId();
                                            infoMessage( "Scelto automaticamente utente con id: " + itemId );
                                        }

                                        infoMessage( "Elimino prestito..." );

                                        prestitoDao.delete( prestitoDao.getById( idForP ) );
                                    }
                                    case 2 -> {
                                        try {
                                            infoMessage( "Hai scelto eliminazione per id" );
                                            infoMessage( "Inserisci l'id del prestito per eliminarlo" );
                                            long input = scanner.nextInt();
                                            infoMessage( "Elimino prestito..." );

                                            prestitoDao.delete( prestitoDao.getById( input ) );
                                        } catch( Exception e ) {
                                            infoMessage( "Qualcosa è andato storto, riprova" );
                                        }

                                    }
                                }
                            } catch( Exception e ) {
                                errorMessage( "Qualcosa è andato storto, riprova" );
                                scanner.nextLine();
                            }
                        }

                    }

                    case 6 -> {
                        infoMessage( "Hai scelto di estinguere un prestito" );
                        long itemId = 0;
                        boolean esegui2 = true;

                        while( esegui2 ) {

                            try {
                                System.out.println( """
                                        1. Per ricercare prestito in base al nome\s
                                        2. Per estinguere prestito in base all'id\s
                                        0. Per uscire
                                        """ );
                                int scelta2 = scanner.nextInt();
                                scanner.nextLine();
                                switch (scelta2) {
                                    case 0 -> {
                                        esegui2 = false;
                                    }

                                    case 1 -> {
                                        long idForP = 0;
                                        infoMessage( "Inserisci il nome del debitore" );
                                        String nome = scanner.nextLine();
                                        infoMessage( "Inserisci il cognome del debitore" );
                                        String cognome = scanner.nextLine();

                                        infoMessage( "Ricerco..." );

                                        List<Utente> finded = utenteDao.getUserByFullName( nome, cognome );

                                        if( finded.size() == 0 ) {
                                            errorMessage( "Nome utente non trovato" );
                                            infoMessage( "Ricontrolla: " + nome + " " + cognome );
                                        } else if( finded.size() > 1 ) {
                                            infoMessage( "Trovati più utenti con lo stesso nome" );
                                            for( Utente u : finded ) {
                                                printerUtente( u );
                                            }
                                            infoMessage( "Inserisci il numero di tessera dell'utente" );
                                            idForP = scanner.nextInt();
                                            infoMessage( "Hai scelto l'utente con id: " + idForP );
                                        } else {
                                            infoMessage( "Un solo utente trovato" );
                                            infoMessage( finded.get( 0 ).toString() );
                                            idForP = finded.get( 0 ).getNumeroTessera();
                                            infoMessage( "Scelto automaticamente utente con id: " + idForP );
                                        }

                                        List<Prestito> prestitiTrovati = prestitoDao.getPrestitoByUserId( idForP );

                                        if( prestitiTrovati.size() == 0 ) {
                                            errorMessage( "Prestito non trovato" );
                                            infoMessage( "Ricontrolla" );
                                        } else if( prestitiTrovati.size() > 1 ) {
                                            infoMessage( "Trovati più prestiti dello stesso utente" );
                                            for( Prestito u : prestitiTrovati ) {
                                                printerPrestito( u );
                                            }
                                            infoMessage( "Inserisci id prestito che si vuole eliminare" );
                                            itemId = scanner.nextInt();
                                            infoMessage( "Hai scelto l'utente con id: " + itemId );
                                        } else {
                                            infoMessage( "Un solo prestito trovato" );
                                            printerPrestito( prestitiTrovati.get( 0 ) );
                                            idForP = prestitiTrovati.get( 0 ).getId();
                                            infoMessage( "Scelto automaticamente prestito con id: " + idForP );
                                        }

                                        infoMessage( "Estinguo prestito..." );
                                        System.out.println( idForP );
                                        restutuitoPrestito( prestitoDao.getById( idForP ) );
                                    }

                                    case 2 -> {
                                        try {
                                            infoMessage( "Hai scelto l'eliminazione tramite id prestito" );
                                            infoMessage( "Inserisci id per estinguere il debito" );
                                            long id = scanner.nextInt();

                                            infoMessage( "Estinguo prestito..." );
                                            restutuitoPrestito( prestitoDao.getById( id ) );
                                        } catch( Exception e ) {
                                            errorMessage( "Qualcosa è andato storto, riprova" );
                                        }

                                    }
                                }
                            } catch( Exception e ) {
                                errorMessage( "Qualcosa è andato storto, riprova" );
                                scanner.nextLine();
                            }
                        }
                    }

                    default -> {
                        standardMessage( "Si accettano numeri da 0 a 6, Riprova" );
                    }

                }

            } catch( NumberFormatException e1 ) {
                standardMessage( e1.getMessage() );
            }


        } while( esegui );
    }

    private void modaleUtente() {
        boolean esegui = true;

        while( esegui ) {
            standardMessage( "MENU UTENTE" );
            System.out.println( """
                    0. Torna al menu principale\s
                    1. Visualizza lista utenti \s
                    2. Cerca per nome e cognome poi modifica utente
                    3. Aggiungi un nuovo utente
                    """ );

            try {
                int scelta = scanner.nextInt();
                long userId = 0;

                switch (scelta) {
                    case 0 -> {
                        standardMessage( "Torno al menu principale" );
                        esegui = false;
                    }

                    case 1 -> {
                        getUtenti();
                    }

                    case 2 -> {
                        boolean esegui2 = true;
                        int count = 0;

                        while( esegui2 ) {
                            if( count == 0 ) {
                                scanner.nextLine();
                            }
                            count++;

                            try {
                                standardMessage( "Ricerca per nome e cognome" );

                                infoMessage( "Inserisci il nome dell'utente" );
                                String nome = scanner.nextLine();

                                infoMessage( "Inserisci il cognome dell'utente'" );
                                String cognome = scanner.nextLine();

                                infoMessage( "ricerco utenti..." );

                                List<Utente> findedUtenti = utenteDao.getUserByFullName( nome, cognome );

                                if( findedUtenti.isEmpty() ) {
                                    errorMessage( "Nessun utente trovato! Riprova." );
                                } else if( findedUtenti.size() == 1 ) {
                                    infoMessage( "Trovata una sola corrispondenza" );
                                    printerUtente( findedUtenti.get( 0 ) );
                                    userId = findedUtenti.get( 0 ).getNumeroTessera();
                                    infoMessage( "Seleziono automaticamente il numero tessera: " + userId );
                                    esegui2 = false;
                                } else {
                                    infoMessage( "Trovati " + findedUtenti.size() + " risultati" );

                                    for( Utente ut : findedUtenti ) {
                                        printerUtente( ut );
                                    }

                                    infoMessage( "Inserisci numero tessera per selezionare l'utente" );
                                    userId = scanner.nextInt();
                                    esegui2 = false;
                                }

                                if( userId != 0 ) {
                                    boolean esegui3 = true;

                                    while( esegui3 ) {
                                        try {
                                            infoMessage( "Che azioni vuoi effettuare?" );
                                            System.out.println( """
                                                    0. Torna al menu principale\s
                                                    1. Visualizza prestiti dell'utente \s
                                                    2. Aggiorna dati utente
                                                    3. Elimina utente
                                                    """ );

                                            int sceltaAzione = scanner.nextInt();

                                            switch (sceltaAzione) {
                                                case 0 -> {
                                                    esegui3 = false;
                                                }

                                                case 1 -> {
                                                    infoMessage( "Visualizzo prestiti dell'utente" );

                                                    List<Prestito> prestitiUtente =
                                                            prestitoDao.getPrestitoByUserId( userId );

                                                    if( prestitiUtente.isEmpty() ) {
                                                        infoMessage( "L'utente non ha prestiti" );
                                                    } else {

                                                        for( Prestito p : prestitiUtente ) {
                                                            printerPrestito( p );
                                                        }
                                                    }
                                                }

                                                case 2 -> {
                                                    boolean esegui4 = true;

                                                    while( esegui4 ) {
                                                        infoMessage( "Modifica dati utente, quale campo vuoi modificare?" );
                                                        System.out.println( """
                                                                0. Annulla
                                                                1. Nome
                                                                2. Cognome
                                                                3. Data di nascita""" );

                                                        int sceltaModifica = scanner.nextInt();

                                                        Utente utenteSelezionato = utenteDao.getById( userId );
                                                        switch (sceltaModifica) {
                                                            case 0 -> {
                                                                esegui4 = false;
                                                            }
                                                            case 1 -> {
                                                                scanner.nextLine();
                                                                infoMessage( "Che nome vuoi dare a " + utenteSelezionato.getNome() + " ?" );
                                                                String nuovoNome = scanner.nextLine();

                                                                infoMessage( "Modifico in " + nuovoNome );

                                                                utenteDao.refreshNome( utenteSelezionato, nuovoNome );

                                                            }
                                                            case 2 -> {
                                                                scanner.nextLine();
                                                                infoMessage( "Che cognome vuoi dare a " + utenteSelezionato.getNome()+ " " + utenteSelezionato.getCognome() + " ?" );
                                                                String nuovoCognome = scanner.nextLine();
                                                                infoMessage( "Modifico in " + nuovoCognome );

                                                                utenteDao.refreshCognome( utenteSelezionato, nuovoCognome );
                                                            }
                                                            case 3 -> {
                                                                scanner.nextLine();

                                                                infoMessage( "Modifica data di nascita formato YYYY/M/D" );
                                                                infoMessage( "Che anno di nascita vuoi dare all' utente " + utenteSelezionato.getNome() );
                                                                int anno = scanner.nextInt();

                                                                infoMessage( "Che mese di nascita vuoi dare all' utente " + utenteSelezionato.getNome() );
                                                                int mese = scanner.nextInt();

                                                                infoMessage( "Che mese di nascita vuoi dare all' utente " + utenteSelezionato.getNome() );
                                                                int giorno = scanner.nextInt();

                                                                LocalDate dataSelezionata = LocalDate.of( anno, mese, giorno );

                                                                infoMessage( "Modifico in " + anno + "/" + mese + "/" + giorno );
                                                                utenteDao.refreshDataNascita( utenteSelezionato, dataSelezionata );

                                                            }
                                                            default -> errorMessage( "Input fuori range" );
                                                        }

                                                    }
                                                }


                                                case 3 -> {
                                                    System.out.println( """
                                                            Questa azione è irreversibile, sei sicuro?
                                                             1.SI
                                                             2.NO""" );

                                                    int sceltaEliminazione = scanner.nextInt();

                                                    if( sceltaEliminazione == 1 ) {
                                                        utenteDao.delete( utenteDao.getById( userId ) );
                                                        esegui3 = false;
                                                    } else {
                                                        infoMessage( "Annullo scelta" );
                                                        esegui3 = false;
                                                    }
                                                }
                                            }

                                        } catch( Exception e ) {
                                            errorMessage( "Qualcosa è andato storto, riprova" );
                                        }
                                    }
                                }

                            } catch( Exception e ) {
                                errorMessage( "Qualcosa è andato storto, riprova!" );
                                scanner.nextLine();
                            }
                        }

                    }

                    case 3 -> {
                        boolean eseguiAggiunta = true;

                        while( eseguiAggiunta ) {
                            scanner.nextLine();
                            try {
                                standardMessage( "AGGIUNGI NUOVO UTENTE" );
                                infoMessage( "Inserisci nome" );
                                String nome = scanner.nextLine();

                                infoMessage( "Inserisci cognome" );
                                String cognome = scanner.nextLine();

                                infoMessage( "Inserisci ANNO di nascita" );
                                int anno = scanner.nextInt();

                                infoMessage( "Inserisci MESE di nascita (numero)" );
                                int mese = scanner.nextInt();

                                infoMessage( "Inserisci GIORNO di nascita (numero)" );
                                int giorno = scanner.nextInt();

                                LocalDate dataUtente = LocalDate.of( anno, mese, giorno );

                                saveUtente( nome, cognome, dataUtente );

                                infoMessage( """
                                        Vuoi salvare un nuovo utente?
                                        1. SI
                                        2. NO""" );

                                int input = scanner.nextInt();

                                if( input == 2 ) {
                                    infoMessage( "Torno al menu utente..." );
                                    eseguiAggiunta = false;
                                }

                            } catch( Exception e ) {
                                errorMessage( "Qualcosa è andato storto, riprova!" );
                                scanner.nextLine();
                            }
                        }
                    }


                    default -> {
                        standardMessage( "Input fuori range. Riprova!" );
                    }
                }
            } catch( Exception e ) {
                errorMessage( "Qualcosa è andato storto, riprova" );
                scanner.nextLine();
            }
        }


    }

    private void saveLibro( String nome, String autore, Genere genere, int anno, int pagine ) {
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

    private void saveRivista( String nome, Periodicita periodicita, int anno, int pagine ) {
        Rivista riv = new Rivista();
        riv.setTitolo( nome );
        riv.setPeriodicita( periodicita );
        riv.setAnnoPubblicazione( anno );
        riv.setNumeroPagine( pagine );

        CatalogoDAO catalogo = new CatalogoDAO();
        catalogo.save( riv );
        standardMessage( "Aggiunto nuova Rivista" );
        printerCatalog( riv );
    }

    public void savePrestito( Utente utente, Catalogo item ) {
        Prestito ut = new Prestito();
        ut.setUtente( utente );
        ut.setCatalogo( item );

        PrestitoDAO prestito = new PrestitoDAO();
        prestito.save( ut );
        standardMessage( "Aggiunto un nuovo Prestito" );
        printerPrestito( ut );
    }

    private void restutuitoPrestito( Prestito prestito ) {
        prestito.restituito();
        prestitoDao.refresh( prestito );
    }

    public void saveUtente( String nome, String cognome, LocalDate nascita ) {
        Utente ut = new Utente();
        ut.setNome( nome );
        ut.setCognome( cognome );
        ut.setDataDiNascita( nascita );

        UtenteDAO utente = new UtenteDAO();
        utente.save( ut );
    }


}
