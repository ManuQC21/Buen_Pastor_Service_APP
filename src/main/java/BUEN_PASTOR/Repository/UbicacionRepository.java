package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends CrudRepository<Location, Integer> {
}
