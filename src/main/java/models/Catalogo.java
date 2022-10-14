package models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
        @NamedQuery(name = "Catalogo.findByTitoloContainsIgnoreCase", query = "select c from Catalogo c where upper(c" +
                ".titolo) like upper(concat('%', :titolo, '%'))"),
        @NamedQuery(name = "Catalogo.findByAnno", query = "select c from Catalogo c where c.annoPubblicazione = :anno")
})
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codiceisbn", nullable = false)
    private Long codiceisbn;

    private String titolo;

    private int annoPubblicazione;

    private int numeroPagine;

    @OneToMany(mappedBy = "catalogo", orphanRemoval = true)
    private List<Prestito> prestiti = new java.util.ArrayList<>();



}
