package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}
