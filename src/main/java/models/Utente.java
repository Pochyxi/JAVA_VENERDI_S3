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
        name = "userByFullName",
        query = "select u from Utente u where upper(u.nome) like upper(concat('%', :nome, '%')) and upper(u" +
                ".cognome) like upper(concat('%', :cognome, '%'))"
)
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_tessera", nullable = false)
    private Long numeroTessera;

    private String nome;

    private String cognome;

    private LocalDate dataDiNascita;

    @Override
    public String toString() {
        return "|| Utente" +
                " | N. TESSERA= '" + numeroTessera +
                "' | NOME= '" + nome + '\'' +
                " | COGNOME= '" + cognome + '\'' +
                " | DATA DI NASCITA= '" + dataDiNascita +
                "' ||";
    }
}
