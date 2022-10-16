package models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedQuery(
        name = "findLoan",
        query = "select p from Prestito p where p.utente.numeroTessera = :numero"
)
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    Utente utente;

    @ManyToOne
    @JoinColumn(name = "catalogo_codiceisbn")
    Catalogo catalogo;

    private LocalDate dataPrestito = LocalDate.now();

    private LocalDate dataScadenzaPrestito = LocalDate.now().plusMonths( 1 );

    private String restituzione;

    public void restituito() {
        this.restituzione = LocalDate.now().toString();
    }

    @Override
    public String toString() {
        return "|| Prestito" +
                "id=" + id +
                ",\n utente=" + utente +
                ", catalogo=" + catalogo +
                ", dataPrestito=" + dataPrestito +
                ", dataScadenzaPrestito=" + dataScadenzaPrestito +
                ", restuzione=" + restituzione;
    }
}
