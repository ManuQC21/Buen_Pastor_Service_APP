package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "Empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "cargo", nullable = false, length = 255)
    private String cargo;

    // Constructor vacío
    public Empleado() {
    }

    public Empleado(int id) {
        this.id = id;
    }

    public Empleado(int id, String nombre, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
    }
}
