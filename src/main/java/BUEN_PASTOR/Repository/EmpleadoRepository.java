package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Empleado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
}
