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
        return "Rivista{" +
                "titolo='" + this.getTitolo() +
                "', periodicita='" + periodicita +
                "', pagine='" + this.getNumeroPagine() +
                "', anno='" + this.getAnnoPubblicazione() +
                "'" +
                '}';
    }
}
