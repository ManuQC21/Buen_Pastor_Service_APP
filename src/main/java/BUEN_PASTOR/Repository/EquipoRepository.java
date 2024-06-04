package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Equipo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends CrudRepository<Equipo, Integer> {
    @Query(value = "SELECT * FROM Equipo WHERE codigo_patrimonial = ?1", nativeQuery = true)
    Optional<Equipo> findByCodigoPatrimonial(String codigoPatrimonial);

    @Query(value = "SELECT e.* FROM Equipo e LEFT JOIN Empleado emp ON e.responsable_id = emp.id LEFT JOIN Ubicacion u ON e.ubicacion_id = u.id WHERE e.id = :id", nativeQuery = true)
    Equipo findByIdWithDetails(@Param("id") int id);

    // Búsqueda de equipos por nombre con búsqueda parcial
    @Query(value = "SELECT * FROM Equipo WHERE nombre_equipo LIKE %:nombre%", nativeQuery = true)
    List<Equipo> findByNombreEquipoContaining(@Param("nombre") String nombre);

    // Búsqueda de equipos por código patrimonial con búsqueda parcial
    @Query(value = "SELECT * FROM Equipo WHERE codigo_patrimonial LIKE %:codigoPatrimonial%", nativeQuery = true)
    List<Equipo> findByCodigoPatrimonialContaining(@Param("codigoPatrimonial") String codigoPatrimonial);

    // Búsqueda de equipos por un rango de fechas de compra
    @Query(value = "SELECT * FROM Equipo WHERE fecha_compra BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
    List<Equipo> findByFechaCompraBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    Optional<Equipo> findByCodigoBarra(String codigoBarra);
}
