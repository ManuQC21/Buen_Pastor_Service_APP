package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends CrudRepository<Equipment, Integer> {
    @Query(value = "SELECT * FROM equipment WHERE barcode = ?1", nativeQuery = true)
    Optional<Equipment> findByBarcode(String barcode);

    @Query(value = "SELECT e.* FROM equipment e LEFT JOIN teacher emp ON e.responsible_id = emp.id LEFT JOIN location l ON e.location_id = l.id WHERE e.id = :id", nativeQuery = true)
    Equipment findByIdWithDetails(@Param("id") int id);

    // Search for equipment by name with partial match
    @Query(value = "SELECT * FROM equipment WHERE equipment_name LIKE %:name%", nativeQuery = true)
    List<Equipment> findByEquipmentNameContaining(@Param("name") String name);

    // Search for equipment by asset code with partial match
    @Query(value = "SELECT * FROM equipment WHERE asset_code LIKE %:assetCode%", nativeQuery = true)
    List<Equipment> findByAssetCodeContaining(@Param("assetCode") String assetCode);

    // Search for equipment by purchase date range
    @Query(value = "SELECT * FROM equipment WHERE purchase_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Equipment> findByPurchaseDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
