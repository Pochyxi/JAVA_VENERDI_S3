package models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

   private LocalDate restuzione;

    private void restituito(){
        this.restuzione =  LocalDate.now();
    }
}
