package BUEN_PASTOR.Repository;

import BUEN_PASTOR.Entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepository extends JpaRepository<PaymentDetails, Integer> {
    // Aunque no especificado directamente, este repositorio manejará los detalles de cada pago, que podrían ser parte de editar o visualizar pagos.
}
