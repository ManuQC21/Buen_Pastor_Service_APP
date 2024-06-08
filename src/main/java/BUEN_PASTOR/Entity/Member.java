package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El correo no puede ser nulo")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    @Size(max = 500, message = "El correo no puede tener más de 500 caracteres")
    @Column(name = "email", nullable = false, length = 500)
    private String email;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, max = 300, message = "La contraseña debe tener entre 6 y 300 caracteres")
    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @NotNull(message = "La vigencia no puede ser nula")
    @Column(name = "validity", nullable = false)
    private boolean validity;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Member() {
    }

    public Member(int id) {
        this.id = id;
    }

    public Member(int id, String email, String password, boolean validity, Teacher teacher) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.validity = validity;
        this.teacher = teacher;
    }
}
