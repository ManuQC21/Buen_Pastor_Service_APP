package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "usuario")
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 500)
    private String correo;
    @Column(length = 300)
    private String clave;
    @Column
    private boolean vigencia;
    @OneToOne
    private BUEN_PASTOR.Entity.empleado empleado;

    public usuario() {
    }

    public usuario(int id) {
        this.id = id;
    }

    public usuario(int id, String correo, String clave, boolean vigencia, BUEN_PASTOR.Entity.empleado empleado) {
        this.id = id;
        this.correo = correo;
        this.clave = clave;
        this.vigencia = vigencia;
        this.empleado = empleado;
    }
}
