package models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "libroByTitolo", query = "select l from Libro l where upper(l.titolo) like upper(:titolo)"),
        @NamedQuery(name = "libroByAutore", query = "select l from Libro l where upper(l.autore) like upper(:titolo)")
})
public class Libro extends Catalogo {

    private String autore;

    @Enumerated(EnumType.STRING)
    private Genere genere;

    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + this.getTitolo() +
                "', autore='" + autore +
                "', genere='" + genere +
                "', pagine='" + this.getNumeroPagine() +
                "', anno='" + this.getAnnoPubblicazione() +
                "'" +
                '}';
    }
}
