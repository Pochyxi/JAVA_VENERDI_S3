package models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@NamedQueries({
        @NamedQuery(name = "rivistaByTitolo", query = "select r from Rivista r where upper(r.titolo) like upper(:titolo)")
})
public class Rivista extends Catalogo {

    private Periodicita periodicita;

    @Override
    public String toString() {
        return "|| Rivista" +
                " | ISBN='" + this.getCodiceisbn() +
                " | TITOLO='" + this.getTitolo() +
                "'| PERIODICITA='" + periodicita +
                "'| NUMERO PAGINE='" + this.getNumeroPagine() +
                "'| ANNO PUBBLICAZIONE='" + this.getAnnoPubblicazione() +
                "'||";
    }
}
