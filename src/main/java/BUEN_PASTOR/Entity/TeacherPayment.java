package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teacher_payment")
public class TeacherPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El empleado no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIgnoreProperties({"teacherPayments"})
    private Teacher teacher;

    @NotNull(message = "El admin no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrative_id", nullable = false)
    @JsonIgnoreProperties({"teacherPaymentsAdministered"})
    private Administrative administrative;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El monto no puede tener más de 10 dígitos enteros y 2 decimales")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "La fecha de pago no puede ser nula")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "El estado del pago no puede ser nulo")
    @Size(max = 50, message = "El estado del pago no puede tener más de 50 caracteres")
    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus;

    @Size(max = 255, message = "La referencia del pago no puede tener más de 255 caracteres")
    @Column(name = "payment_reference", length = 255)
    private String paymentReference;

    @NotNull(message = "Los días laborales no pueden ser nulos")
    @Min(value = 1, message = "Los días laborales deben ser al menos 1")
    @Column(name = "work_days", nullable = false)
    private int workDays;

    @Size(max = 50, message = "El nivel educativo no puede tener más de 50 caracteres")
    @Column(name = "education_level", length = 50)
    private String educationLevel;

    @NotNull(message = "El código modular no puede ser nulo")
    @Size(max = 20, message = "El código modular no puede tener más de 20 caracteres")
    @Column(name = "modular_code", nullable = false, length = 20)
    private String modularCode = "989873";

    @OneToMany(mappedBy = "teacherPayment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentDetails> paymentDetails;
}
