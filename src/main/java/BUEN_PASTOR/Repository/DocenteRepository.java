package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Teacher, Integer> {
    // Punto 1: Agregar un docente, editarlo, eliminarlo, listar todos los docentes
}
