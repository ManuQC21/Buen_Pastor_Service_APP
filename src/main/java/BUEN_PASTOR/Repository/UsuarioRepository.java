package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<usuario, Integer> {
    Optional<usuario> findByCorreo(String correo);
}
