package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.Usuario;
import BUEN_PASTOR.Service.UsuarioService;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/registro")
    public GenericResponse<Usuario> register(@RequestBody Usuario usuario) {
        return service.register(usuario);
    }

    @PostMapping("/login")
    public GenericResponse<Usuario> login(@RequestParam("correo") String correo, @RequestParam("clave") String clave) {
        return service.login(correo, clave);
    }
}
