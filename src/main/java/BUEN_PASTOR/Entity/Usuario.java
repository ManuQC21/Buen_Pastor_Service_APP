package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "Usuario")
public class Usuario {
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
    private Empleado empleado;

    public Usuario() {
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(int id, String correo, String clave, boolean vigencia, Empleado empleado) {
        this.id = id;
        this.correo = correo;
        this.clave = clave;
        this.vigencia = vigencia;
        this.empleado = empleado;
    }
}
