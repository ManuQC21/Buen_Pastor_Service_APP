package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.Member;
import BUEN_PASTOR.Repository.UsuarioRepository;
import BUEN_PASTOR.utils.GenericResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public GenericResponse<Member> register(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword())); // Encripta la contraseña
        Member savedMember = repository.save(member);
        return new GenericResponse<>("SUCCESS", 1, "Usuario registrado exitosamente.", savedMember);
    }

    public GenericResponse<Member> login(String email, String password) {
        Optional<Member> usuarioOpt = repository.findByEmail(email);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(password, usuarioOpt.get().getPassword())) {
            return new GenericResponse<>("SUCCESS", 1, "Inicio de sesión exitoso.", usuarioOpt.get());
        } else {
            return new GenericResponse<>("ERROR", 0, "Credenciales incorrectas.", null);
        }
    }
}
