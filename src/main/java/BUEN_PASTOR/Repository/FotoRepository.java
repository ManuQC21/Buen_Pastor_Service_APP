package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Foto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface FotoRepository extends CrudRepository<Foto, Long> {
    @Query("SELECT da FROM Foto da WHERE da.estado = 'A' AND da.eliminado = false")
    Iterable<Foto> list();

    @Query("SELECT da FROM Foto da WHERE da.fileName = :fileName AND da.estado = 'A' AND da.eliminado = false")
    Optional<Foto> findByFileName(String fileName);
}