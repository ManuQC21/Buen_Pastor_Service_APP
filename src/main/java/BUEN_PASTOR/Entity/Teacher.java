package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @NotNull(message = "El estado de vigencia no puede ser nulo")
    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeacherPayment> teacherPayments;

    public Teacher(int id) {
        this.id = id;
    }

    public Teacher() {
    }

    public Teacher(int id, String fullName, String position, String dni, String phone, String address, LocalDate hiringDate, boolean active, List<TeacherPayment> teacherPayments) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.dni = dni;
        this.phone = phone;
        this.address = address;
        this.hiringDate = hiringDate;
        this.active = active;
        this.teacherPayments = teacherPayments;
    }
}
