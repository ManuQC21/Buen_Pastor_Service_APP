package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.TeacherPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<TeacherPayment, Integer> {
    // Punto 4: Puedo tener un pago para un docente específico
    List<TeacherPayment> findByTeacherId(Integer teacherId);

    // Punto 5 y 6: Listar todos los pagos, y gestionarlos (agregar, editar, eliminar)
    // Los métodos save() y delete() de JpaRepository cubren estos requerimientos.

    // Punto 8: Descargar el reporte de todos los pagos (esto se hará a nivel de servicio pero se obtendrán datos de aquí)
}
