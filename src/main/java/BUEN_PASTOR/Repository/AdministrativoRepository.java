package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Administrative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativoRepository extends JpaRepository<Administrative, Integer> {
    // Este repositorio gestionará las funcionalidades administrativas básicas.
}
