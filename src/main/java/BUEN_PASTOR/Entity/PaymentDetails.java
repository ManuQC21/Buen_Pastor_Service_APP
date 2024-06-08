package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_details")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El pago no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_payment_id", nullable = false)
    private TeacherPayment teacherPayment;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotNull(message = "La categoría del pago no puede ser nula")
    @Size(max = 50, message = "La categoría del pago no puede tener más de 50 caracteres")
    @Column(name = "payment_category", nullable = false, length = 50)
    private String paymentCategory;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El monto no puede tener más de 10 dígitos enteros y 2 decimales")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
}
