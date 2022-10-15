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
        return "|| Libro" +
                " | ISBN= '" + this.getCodiceisbn() +
                "'| TITOLO= '" + this.getTitolo() +
                "'| AUTORE= '" + autore +
                "'| GENERE= '" + genere +
                "'| PAGINE= '" + this.getNumeroPagine() +
                "'| ANNO P.= '" + this.getAnnoPubblicazione() +
                "'||";
    }
}
