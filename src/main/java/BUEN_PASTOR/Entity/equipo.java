package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "equipo")
public class equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tipo_equipo", nullable = true, length = 255)
    private String tipoEquipo;

    @Column(name = "codigo_barra", nullable = true, length = 255)
    private String codigoBarra;

    @Column(name = "codigo_patrimonial", nullable = true,    length = 255)
    private String codigoPatrimonial;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "estado", length = 255)
    private String estado;

    @Column(name = "fecha_compra")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCompra;

    @Column(name = "marca", length = 255)
    private String marca;

    @Column(name = "modelo", length = 255)
    private String modelo;

    @Column(name = "nombre_equipo", length = 255)
    private String nombreEquipo;

    @Column(name = "numero_orden", length = 255)
    private String numeroOrden;

    @Column(name = "serie", length = 255)
    private String serie;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private empleado responsable;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private BUEN_PASTOR.Entity.ubicacion ubicacion;

    public equipo() {
    }

    public equipo(int id) {
        this.id = id;
    }

    public equipo(int id, String tipoEquipo, String codigoBarra, String codigoPatrimonial, String descripcion, String estado, LocalDate fechaCompra, String marca, String modelo, String nombreEquipo, String numeroOrden, String serie, empleado responsable, BUEN_PASTOR.Entity.ubicacion ubicacion) {
        this.id = id;
        this.tipoEquipo = tipoEquipo;
        this.codigoBarra = codigoBarra;
        this.codigoPatrimonial = codigoPatrimonial;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.marca = marca;
        this.modelo = modelo;
        this.nombreEquipo = nombreEquipo;
        this.numeroOrden = numeroOrden;
        this.serie = serie;
        this.responsable = responsable;
        this.ubicacion = ubicacion;
    }
}
