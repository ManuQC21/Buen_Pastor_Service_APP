package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ubicacion")
public class ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ambiente", nullable = false, length = 255)
    private String ambiente;

    @Column(name = "ubicacion_fisica", nullable = false, length = 255)
    private String ubicacionFisica;

    public ubicacion() {
    }

    public ubicacion(Integer id) {
        this.id = id;
    }

    public ubicacion(Integer id, String ambiente, String ubicacionFisica) {
        this.id = id;
        this.ambiente = ambiente;
        this.ubicacionFisica = ubicacionFisica;
    }

}
