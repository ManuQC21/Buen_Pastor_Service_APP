package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "administrative")
    public class Administrative{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotNull(message = "El rol no puede ser nulo")
        @Size(max = 50, message = "El rol no puede tener más de 50 caracteres")
        @Column(name = "rol", nullable = false, length = 50)
        private String rol;

        @NotNull(message = "El nombre completo no puede ser nulo")
        @Size(max = 255, message = "El nombre completo no puede tener más de 255 caracteres")
        @Column(name = "first_name", nullable = false, length = 255)
        private String fullName;

        @NotNull(message = "El cargo no puede ser nulo")
        @Size(max = 255, message = "El cargo no puede tener más de 255 caracteres")
        @Column(name = "position", nullable = false, length = 255)
        private String position;

        @NotNull(message = "El DNI no puede ser nulo")
        @Size(max = 9, min = 9, message = "El DNI debe tener exactamente 9 caracteres")
        @Column(name = "dni", nullable = false, unique = true, length = 9)
        private String dni;

        @NotNull(message = "El email no puede ser nulo")
        @Email(message = "Debe ser un formato de correo electrónico válido")
        @Size(max = 255, message = "El email no puede tener más de 255 caracteres")
        @Column(name = "email", nullable = false, unique = true, length = 255)
        private String email;

        @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 dígitos")
        @Column(name = "phone", length = 9)
        private String phone;

        @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
        @Column(name = "address", length = 255)
        private String address;

        @NotNull(message = "La fecha de contratación no puede ser nula")
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Column(name = "hiring_date", nullable = false)
        private LocalDate hiringDate;

        @OneToMany(mappedBy = "administrative", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<TeacherPayment> teacherPaymentsAdministered;

    public Administrative(int id) {
        this.id = id;
    }

    public Administrative() {

    }
}
