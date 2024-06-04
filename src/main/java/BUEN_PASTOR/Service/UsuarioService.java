package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.usuario;
import BUEN_PASTOR.Repository.UsuarioRepository;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public GenericResponse<usuario> register(usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave())); // Encripta la contraseña
        BUEN_PASTOR.Entity.usuario savedUsuario = repository.save(usuario);
        return new GenericResponse<>("SUCCESS", 1, "Usuario registrado exitosamente.", savedUsuario);
    }

    public GenericResponse<usuario> login(String correo, String clave) {
        Optional<usuario> usuarioOpt = repository.findByCorreo(correo);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(clave, usuarioOpt.get().getClave())) {
            return new GenericResponse<>("SUCCESS", 1, "Inicio de sesión exitoso.", usuarioOpt.get());
        } else {
            return new GenericResponse<>("ERROR", 0, "Credenciales incorrectas.", null);
        }
    }
}
